package sdw.drakirus.xyz.smartwallremote

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.Toast
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.orhanobut.dialogplus.DialogPlus
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import es.dmoral.toasty.Toasty
import io.bloco.faker.Faker
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import petrov.kristiyan.colorpicker.ColorPicker
import sdw.drakirus.xyz.smartwallremote.component.helpers.FabButtonPerso
import sdw.drakirus.xyz.smartwallremote.component.layout.LayoutChooserAdapter
import sdw.drakirus.xyz.smartwallremote.component.scenario.ScenarioChooserAdapter
import sdw.drakirus.xyz.smartwallremote.mainActivityUI.MainActivityUi
import sdw.drakirus.xyz.smartwallremote.model.*
import java.util.*


class MainActivity : AppCompatActivity(), AnkoLogger {

    lateinit var wall: WallItem
    private var tmpGrpCreatedByUser = mutableListOf<GrpScreen>()

    private var layoutConfig: LayoutConfig? = null
    private var scenarioConfig: ScenarioConfig? = null


    var videoConfig: VideoConfig? = null

    var saveFAB: FabButtonPerso? = null
    var paintFAB: FloatingActionButton? = null
    var slidingUpPanelLayout: SlidingUpPanelLayout? = null

    var faker: Faker? = null

    var layoutConfigInUse: Int = -1


    companion object {
        var baseUrl = "https://raw.githubusercontent.com/Drakirus/SmartWallRemote/master/json/"
        val layoutUrl = "layout.json"
        val wallUrl = "wall.json"
        val videoUrl = "videos.json"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBaseUrl()

        setContentView(MainActivityUi().initLayout(this))

        getWallAllConfig()

        doAsync {
            faker = Faker()
        }

    }

    fun getLayoutConfig() = layoutConfig?.getForWall(wall) ?: listOf() // get list of layouts or return an empty one
    fun getScenarioConfig() = scenarioConfig?.getForWall(wall) ?: listOf() // get list of layouts or return an empty one


    private fun getLayout() {
        layoutUrl.httpGet().responseObject<LayoutConfig> { _, _, result ->
            when(result) {
                is Result.Success -> {
                    layoutConfig = result.value


                    val allLayout: MutableList<Layout> = result.value.layouts

                    val toScenario: List<Scenario> = allLayout.map {
                        Scenario(name = "test", isDistributed = false, layout = it,
                                video = listOf(
                                        VideoModel("test", "urlImage", 1000))
                                , timeStart = 0)
                    }

                    scenarioConfig = ScenarioConfig(toScenario.toMutableList())


                }
                is Result.Failure -> {
                    Thread.sleep(100)

                    alert(Appcompat, "\nWould you like to try again ?\n" , "Error while fetching the layout information!") {
                        positiveButton("Yes") { getLayout() }
                        negativeButton("no") { }
                    }.show()

                    error(result.error)
                }
            }
        }
    }

    fun getWallAllConfig() {
        val getConfigDialog = indeterminateProgressDialog(R.string.get_config)
        getConfigDialog.setCancelable(false)
        getConfigDialog.show()
        getVideos()
        getLayout()

        wallUrl.httpGet().responseObject<WallConfig> { _, _, result ->
            when(result) {
                is Result.Success -> {
                    getConfigDialog.cancel()
                    wall = result.value.wall[0]

                    selector("Multiple Walls are available", result.value.wall.map { "(${it.rows}x${it.cols}) - " + it.name }, { _, i ->
                        wall = result.value.wall[i]
                        MainActivityUi().setContentView(this)
                    })
                }
                is Result.Failure -> {
                    Thread.sleep(500) // less spam
                    getConfigDialog.cancel()
                    warn(result.error)
                    alert(Appcompat, "\nWould you like to try again ?\n" , result.error.exception.message) {
                        positiveButton("Yes") { getWallAllConfig() }
                        negativeButton("Exit") { finish() }
                    }.show()
                }
            }
        }
    }

    fun getVideos() {
        videoUrl.httpGet().responseObject<VideoConfig> { _, _, result ->
            when(result) {
                is Result.Success -> {
                    videoConfig = result.value
                }
                is Result.Failure -> {
                    Thread.sleep(100)

                    alert(Appcompat, "\nWould you like to try again ?\n" , "Error while fetching the videos!") {
                        positiveButton("Yes") { getVideos() }
                        negativeButton("no") { }
                    }.show()

                    error(result.error)
                }
            }
        }
    }

    fun createNewLayout(name: String) = Layout(wall.cols, wall.rows, tmpGrpCreatedByUser, name)

    fun saveLayout(name: String) {

        val newLayout = createNewLayout(name)
        if(layoutConfig != null) {
            layoutConfig?.layouts?.add(newLayout)
        } else {
            layoutConfig = LayoutConfig(mutableListOf(newLayout))
        }

        tmpGrpCreatedByUser = mutableListOf()

        val config = getLayoutConfig()
        layoutConfigInUse = config.lastIndex
        wall.updateColorGroup(config.last())

        // simulate a post
//        Toasty.info(this, "Post to REST API", Toast.LENGTH_SHORT, true).show()

        saveFAB?.showProgress(true)
        saveFAB?.isClickable = false
        doAsync {
            Thread.sleep(1400)
            uiThread {
                //                Toasty.info(it, "Post result to REST API ?", Toast.LENGTH_SHORT, true).show()
                saveFAB?.showEndBitmap = true
                saveFAB?.showProgress(false)
                saveFAB?.onProgressCompleted()
                doAsync {
                    Thread.sleep(1400)
                    uiThread {
                        saveFAB?.hide()
                    }
                }
            }
        }


    }


    fun createAGroup() {
        if (wall.screen.none { it.checkBox.isChecked }) {
            Toasty.info(this, "No screen are selected", Toast.LENGTH_SHORT, true).show()
            return
        }

        // create random colors
        val colors = arrayListOf<String>()
        val random = Random()
        for (i in 1..25) {
            val nextInt = random.nextInt(256 * 256 * 256)
            colors.add(String.format("#%06x", nextInt))
        }

        val colorPicker = ColorPicker(this)
        colorPicker.setColors(colors)
        colorPicker.show()
        colorPicker.setOnChooseColorListener(object : ColorPicker.OnChooseColorListener {
            override fun onChooseColor(position: Int, color: Int) {
                if (position == -1) return
                val selected = wall.screen.filter { it.checkBox.isChecked }.toMutableList()

                tmpGrpCreatedByUser.forEach{ grp -> // delete on other grp
                    grp.listScreen.removeAll(selected)
                }

                tmpGrpCreatedByUser.add(GrpScreen(selected, colors.get(position)))
                val newLayout = Layout(wall.cols, wall.rows, tmpGrpCreatedByUser, "_tmp")
                wall.updateColorGroup(newLayout)
                saveFAB?.show()

                wall.screen.forEach { it.checkBox.isChecked = false }
                paintFAB?.hide()

            }

            override fun onCancel() {
            }
        })
    }


    // https://github.com/orhanobut/dialogplus
    private val dialogPlusBuilder = DialogPlus.newDialog(this)
            .setGravity(Gravity.CENTER)
            .setOnItemClickListener { dialog, _, _, position ->
                wall.updateColorGroup(getLayoutConfig()[position])
                layoutConfigInUse = position
                tmpGrpCreatedByUser.clear()
                saveFAB?.hide()
                paintFAB?.hide()
                dialog.dismiss()
            }
            .setHeader(R.layout.scenario_header)
            .setExpanded(false)!!

    fun dialogChooseLayout() {
        dialogPlusBuilder.adapter = LayoutChooserAdapter(this, getLayoutConfig())
        dialogPlusBuilder.create().show()
    }

    fun dialogChooseScenario() {
        dialogPlusBuilder.adapter = ScenarioChooserAdapter(this, getScenarioConfig())
        dialogPlusBuilder.create().show()
    }

    fun toggleGroup(screen: Screen) {

        val listToUse = if (tmpGrpCreatedByUser.isEmpty()) {
            getLayoutConfig().getOrNull(layoutConfigInUse)?.grpScreen
        } else {
            tmpGrpCreatedByUser
        }

        val hasMakeUpdate = listToUse
                ?.fold(false) {acc, item: GrpScreen ->
                    if (item.listScreen.any { it == screen }) {
                        val uiGrpScreen = wall.screen.intersect(item.listScreen)

                        val allCheck = uiGrpScreen.all { it.checkBox.isChecked }
                        val someCheck = uiGrpScreen.any { it.checkBox.isChecked }

                        if (!allCheck && someCheck) {
                            val initSate = screen.checkBox.isChecked
                            uiGrpScreen.forEach { it.checkBox.isChecked = initSate.not() }
                        } else {
                            uiGrpScreen.forEach {
                                it.checkBox.isChecked = it.checkBox.isChecked.not()
                            }
                        }
                        acc || true
                    } else acc || false
                } ?: false

        if (hasMakeUpdate.not()) {
            screen.checkBox.isChecked = screen.checkBox.isChecked.not()
        }
        hideShowPaintFAB()
    }

    fun hideShowPaintFAB(){
        if (wall.screen.any { it.checkBox.isChecked }) {
            paintFAB?.show()
        } else {
            paintFAB?.hide()
        }
    }

    override fun onBackPressed() {
        slidingUpPanelLayout?.let {
            if (it.panelState == SlidingUpPanelLayout.PanelState.EXPANDED
                    || it.panelState == SlidingUpPanelLayout.PanelState.ANCHORED) {

            }
            it.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            return
        }
        super.onBackPressed()
    }

    fun getBaseUrl(): String {
        val settings = PreferenceManager.getDefaultSharedPreferences(this);
        baseUrl = settings.getString("baseUrl", baseUrl)
        FuelManager.instance.basePath = baseUrl
        return baseUrl
    }

    fun putBaseUrl(url: String) {
        val edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
        edit.putString("baseUrl", url);
        FuelManager.instance.basePath = url
        edit.apply();
    }
}

