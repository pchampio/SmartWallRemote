package sdw.drakirus.xyz.smartwallremote

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import sdw.drakirus.xyz.smartwallremote.component.video.VideoData
import sdw.drakirus.xyz.smartwallremote.json.LayoutConfig
import sdw.drakirus.xyz.smartwallremote.json.Screen
import sdw.drakirus.xyz.smartwallremote.json.WallConfig
import sdw.drakirus.xyz.smartwallremote.json.WallItem


class MainActivity : AppCompatActivity(), AnkoLogger {

    lateinit var wall: WallItem
    lateinit var layoutConfig: LayoutConfig

    var layoutConfigInUse: Int = 0

    val videoList =
            listOf(
                    VideoData("name 1", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                    VideoData("name 2", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                    VideoData("name 3", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                    VideoData("name 4", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                    VideoData("name 5 extrèmement ; ccdd,dc,dc,d,ckldckdc,kdc,kdct", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                    VideoData("name 6", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                    VideoData("name 7", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                    VideoData("name 8", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                    VideoData("name 9", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                    VideoData("name 10", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                    VideoData("name 11", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                    VideoData("name 12", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                    VideoData("name 13", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg")
            )


    companion object {
        const val baseUrl = "https://gif.drakirus.xyz"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FuelManager.instance.basePath = baseUrl

        getLayout()
        getAndChooseWall()

    }

    fun getLayoutConfig() = layoutConfig.getForWall(wall)

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

    private fun getAndChooseWall() {
        val getConfigDialog = indeterminateProgressDialog(R.string.get_config)
        getConfigDialog.setCancelable(false)
        getConfigDialog.show()

        "wall.json".
                httpGet().responseObject<WallConfig> { request, response, result ->
            when(result) {
                is Result.Success -> {
                    getConfigDialog.cancel()
                    if (result.value.wall.size > 1) {
                        selector("Multiple Walls are available", result.value.wall.map {it.name}, { _, i ->
                            wall = result.value.wall[i]
                            MainActivityUi().setContentView(this)
                        })
                    } else {
                        wall = result.value.wall[0]
                        MainActivityUi().setContentView(this)
                    }
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

    fun toggleGroup(screen: Screen) {
        wall.screen.filter { it.color == screen.color }.forEach { println(it.checkBox); it.checkBox?.isChecked = it.checkBox?.isChecked?.not() ?: false }

//        getLayoutConfig().get(layoutConfigInUse).grpScreen.
//                forEach { item: GrpScreen ->
//                    val test = item.listScreen.all { item.color == screen.color }
//                    println(test)
//                    if (test)

//                    if (item.listScreen.get(0).color == screen.color) {
//                        println("${item.listScreen.get(0).color} == ${screen.color}")
//                        wall.screen.union(item.listScreen).forEach {
//                            it.checkBox?.isChecked = !it.checkBox?.isChecked!!
//                        }
//                    }

//                }
    }

}

