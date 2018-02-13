package sdw.drakirus.xyz.smartwallremote

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import sdw.drakirus.xyz.smartwallremote.json.WallConfig
import sdw.drakirus.xyz.smartwallremote.view.MainActivityUi
import android.app.SearchManager
import android.content.Intent




class MainActivity : AppCompatActivity(), AnkoLogger {

    val screensWall = mutableListOf<WallConfig>()

    companion object {
        const val baseUrl = "https://gif.drakirus.xyz"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FuelManager.instance.basePath = baseUrl
        initView()
    }

    fun initView() {
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
                            MainActivityUi(result.value.wall[i]).setContentView(this)
                        })
                    } else {
                        MainActivityUi(result.value.wall[0]).setContentView(this)
                    }
                }
                is Result.Failure -> {
                    Thread.sleep(500) // less spam
                    getConfigDialog.cancel()
                    warn(result.error)
                    alert(Appcompat, "\nWould you like to try again ?\n" , result.error.exception.message) {
                        positiveButton("Yes") { initView() }
                        negativeButton("No") { finish() }
                    }.show()
                }
            }
        }


    }

    fun selectedScreen(ui: AnkoContext<MainActivity>, name: Int?, password: Int?) {
        ui.doAsync {
            Thread.sleep(500)
            activityUiThreadWithContext {
                toast(name.toString() + " " + password.toString())
            }
        }
    }

    fun searchVideo(query: String){

    }
}

