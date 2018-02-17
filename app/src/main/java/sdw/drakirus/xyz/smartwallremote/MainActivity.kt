package sdw.drakirus.xyz.smartwallremote

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.orhanobut.dialogplus.DialogPlus
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import petrov.kristiyan.colorpicker.ColorPicker
import sdw.drakirus.xyz.smartwallremote.component.scenario.ScenarioChooserAdapter
import sdw.drakirus.xyz.smartwallremote.json.*
import sdw.drakirus.xyz.smartwallremote.mainActivityUI.MainActivityUi
import java.util.*


class MainActivity : AppCompatActivity(), AnkoLogger {

    lateinit var wall: WallItem
    private var tmpGrpCreatedByUser = mutableListOf<GrpScreen>()

    private var layoutConfig: LayoutConfig? = null
    var imageSaveLayout: FloatingActionButton? = null
    var slidingUpPanelLayout: SlidingUpPanelLayout? = null

    var layoutConfigInUse: Int = 0


    companion object {
        var baseUrl = "https://gif.drakirus.xyz"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FuelManager.instance.basePath = baseUrl

        getLayout()
        getAndChooseWall(firstRun = true)

    }

    fun getLayoutConfig() = layoutConfig?.getForWall(wall) ?: listOf<Layout>() // get list of layouts or return an empty one

    private fun getLayout() {
        "layout.json".httpGet().responseObject<LayoutConfig> { request, response, result ->
            when(result) {
                is Result.Success -> {
                    layoutConfig = result.value
                    info(result.value)
                }
                is Result.Failure -> {
                    toast("Error while fetching the layout information!")
                    error(result.error)
                }
            }

        }
    }

    fun getAndChooseWall(firstRun: Boolean = false) {
        val getConfigDialog = indeterminateProgressDialog(R.string.get_config)
        getConfigDialog.setCancelable(false)
        getConfigDialog.show()

        "wall.json".
                httpGet().responseObject<WallConfig> { request, response, result ->
            when(result) {
                is Result.Success -> {
                    getConfigDialog.cancel()
                    wall = result.value.wall[0]

                    selector("Multiple Walls are available", result.value.wall.map {it.name}, { _, i ->
                        wall = result.value.wall[i]
                        MainActivityUi().setContentView(this)
                    })
                    if (firstRun)
                        MainActivityUi().setContentView(this)
                }
                is Result.Failure -> {
                    Thread.sleep(500) // less spam
                    getConfigDialog.cancel()
                    warn(result.error)
                    alert(Appcompat, "\nWould you like to try again ?\n" , result.error.exception.message) {
                        positiveButton("Yes") { getAndChooseWall() }
                        negativeButton("Exit") { finish() }
                    }.show()
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

        tmpGrpCreatedByUser = mutableListOf<GrpScreen>()

        val config = getLayoutConfig()
        layoutConfigInUse = config.lastIndex
        wall.updateColorGroup(config.last())

        Toasty.info(this, "Post to REST API", Toast.LENGTH_SHORT, true).show()

        // simulate a post
        doAsync {
            Thread.sleep(1000)
            uiThread {
                Toasty.info(it, "Post result to REST API ?", Toast.LENGTH_SHORT, true).show()
                imageSaveLayout?.visibility = View.GONE
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
                val selected = wall.screen.filter { it.checkBox.isChecked }.toMutableList()

                tmpGrpCreatedByUser.forEach{ grp -> // delete on other grp
                    grp.listScreen.removeAll(selected)
                }

                tmpGrpCreatedByUser.add(GrpScreen(selected, colors.get(position)))
                val newLayout = Layout(wall.cols, wall.rows, tmpGrpCreatedByUser, "_tmp")
                wall.updateColorGroup(newLayout)
                imageSaveLayout?.visibility = View.VISIBLE

                wall.screen.forEach { it.checkBox.isChecked = false }

            }

            override fun onCancel() {
            }
        })
    }


    // https://github.com/orhanobut/dialogplus
    val dialog_scenario = DialogPlus.newDialog(this)
            .setGravity(Gravity.CENTER)
            .setOnItemClickListener { dialog, item, view, position ->
                wall.updateColorGroup(getLayoutConfig().get(position))
                layoutConfigInUse = position
                tmpGrpCreatedByUser.clear()
                imageSaveLayout?.visibility = View.GONE
                dialog.dismiss()
            }
            .setHeader(R.layout.scenario_header)
            .setExpanded(false)

    fun dialogChooseGrp() {
        dialog_scenario.setAdapter(ScenarioChooserAdapter(this, getLayoutConfig()))
        dialog_scenario.create().show()

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


    }

    override fun onBackPressed() {
        slidingUpPanelLayout?.let {
            if (it.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED
                    || it.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED) {

            }
            it.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            return
        }
        super.onBackPressed()
    }

    fun hideKeyboard(activity: Activity = this) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}

