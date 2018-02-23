package sdw.drakirus.xyz.smartwallremote.mainActivityUI

import android.graphics.Color
import android.view.Gravity
import android.view.ViewManager
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import sdw.drakirus.xyz.smartwallremote.MainActivity
import sdw.drakirus.xyz.smartwallremote.R
import sdw.drakirus.xyz.smartwallremote.component.helpers.FabButtonPerso


/**
 * Created by drakirus (p.champion) on 28/01/18.
 */

// needed to make the slidingUpPanelLayout work with anko (map xml dsl to the anko dsl ;) )
inline fun ViewManager.slidingUpPanelLayout(init: SlidingUpPanelLayout.() -> Unit): SlidingUpPanelLayout {
    return ankoView({ SlidingUpPanelLayout(it) }, theme = 0, init = init)
}

inline fun ViewManager.FabButtonLoader(init: FabButtonPerso.() -> Unit): FabButtonPerso {
    return ankoView({ FabButtonPerso(it) }, theme = 0, init = init)
}

class MainActivityUi() : AnkoComponent<MainActivity> {

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {

        // https://github.com/umano/AndroidSlidingUpPanel
        slidingUpPanelLayout() {

            ui.owner.slidingUpPanelLayout = this
            // config the sliding Panel
            setGravity(Gravity.BOTTOM)
            backgroundColor = Color.WHITE
            shadowHeight = 0

            // ONLY 2 ITEM IN A slidingUpPanelLayout
            mainView(ui)
            slideView(ui)

        }


    }

    fun initLayout(ui: MainActivity) = with(ui) {
        verticalLayout {
            backgroundResource = R.drawable.bg
            toolbar {
                horizontalPadding = 10
                overflowIcon.setTint(Color.WHITE)
                menu.add("Choose a Wall").setOnMenuItemClickListener {
                    ui.getWallAllConfig()
                    true
                }

                menu.add("Edit URL server").setOnMenuItemClickListener {
                    askForBaseUrl(ui) { url ->
                        ui.putBaseUrl(url)
                    }
                    true
                }
            }
        }
    }
}


