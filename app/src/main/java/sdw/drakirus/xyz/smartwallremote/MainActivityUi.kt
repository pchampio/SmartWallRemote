package sdw.drakirus.xyz.smartwallremote

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.ViewManager
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.custom.ankoView
import sdw.drakirus.xyz.smartwallremote.component.mainView
import sdw.drakirus.xyz.smartwallremote.component.slideView


/**
 * Created by drakirus (p.champion) on 28/01/18.
 */

// needed to make the slidingUpPanelLayout work with anko (map xml dsl to the anko dsl ;) )
inline fun ViewManager.slidingUpPanelLayout(ctx: Context, init: SlidingUpPanelLayout.() -> Unit): SlidingUpPanelLayout {
    return ankoView({ SlidingUpPanelLayout(it) }, theme = 0, init = init)
}

class MainActivityUi() : AnkoComponent<MainActivity> {

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {

        // https://github.com/umano/AndroidSlidingUpPanel
        slidingUpPanelLayout(ctx) {

            // config the sliding Panel
            setGravity(Gravity.BOTTOM)
            backgroundColor = Color.WHITE
            shadowHeight = 0

            // ONLY 2 ITEM IN A slidingUpPanelLayout
            mainView(ui)
            slideView(ui)

        }

    }
}
