package sdw.drakirus.xyz.smartwallremote

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import sdw.drakirus.xyz.smartwallremote.component.video.VideoData
import sdw.drakirus.xyz.smartwallremote.json.*


class MainActivity : AppCompatActivity(), AnkoLogger {

    val screensWall = mutableListOf<WallConfig>()
    lateinit var wall: WallItem

    val listLayout =
            listOf(
                    Layout(listOf(GrpScreen(
                            listOf(
                                    Screen(1, 1, null)), Color.RED
                            )), "Name layout 1"
                    ),
                    Layout(listOf(
                            GrpScreen(listOf(
                                    Screen(0, 0, null),
                                    Screen(0, 1, null),
                                    Screen(0, 2, null),

                                    Screen(1, 0, null),
                                    Screen(1, 1, null),
                                    Screen(1, 2, null),

                                    Screen(2, 0, null),
                                    Screen(2, 1, null),
                                    Screen(2, 2, null)
                            ), Color.RED),
                            GrpScreen(listOf(
                                    Screen(0, 3, null),
                                    Screen(1, 3, null),
                                    Screen(2, 3, null)
                            ), Color.BLUE),
                            GrpScreen(listOf(
                                    Screen(3, 0, null),
                                    Screen(3, 1, null),
                                    Screen(3, 2, null)
                            ), Color.YELLOW),
                            GrpScreen(listOf(
                                    Screen(3, 3, null)
                            ), Color.YELLOW)
                    ), "Name layout 2"
                    )
            )

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

        getAndChooseWall()
    }


    fun getAndChooseWall() {
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
                            info("Chosen Wall" + result.value.wall[i])
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

    fun selectedScreen( name: Int?, password: Int?) {
        doAsync {
            Thread.sleep(500)
            activityUiThreadWithContext {
                toast(name.toString() + " " + password.toString())
            }
        }
    }

    fun searchVideo(query: String){

    }
}

