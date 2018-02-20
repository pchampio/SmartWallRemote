package sdw.drakirus.xyz.smartwallremote.mainActivityUI

import android.support.v7.widget.RecyclerView
import android.view.View
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.toast
import sdw.drakirus.xyz.smartwallremote.MainActivity
import sdw.drakirus.xyz.smartwallremote.component.helpers.KeyboardUtils
import sdw.drakirus.xyz.smartwallremote.component.video.VideoChooserFragment


/**
 * Created by drakirus (p.champion) on 12/02/18.
 */

fun SlidingUpPanelLayout.slideView(ui: AnkoContext<MainActivity>) =
        linearLayout {
            val idlayout = 44
            id = idlayout



            val fragment = VideoChooserFragment()

            // bind the on click
            fragment.setOnClick (object : VideoChooserFragment.OnClick() {
                override fun run() {
                    ui.ctx.toast("Selected: " + videoModel.getTitle())
                    panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                    KeyboardUtils.hideKeyboard(ui.owner)
                }
            } )


            // setup the biding with this SlidingUpPanelLayout to get the desired scroll behaviour
            fragment.setOnCreateEvent {
                val re = fragment.recyclerView
                re.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    // disable the slidingUpPanelLayout if the user is not at the top
                    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                            isTouchEnabled = recyclerView != null && recyclerView.computeVerticalScrollOffset() == 0
                        }
                    }
                })
                addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
                    override fun onPanelSlide(panel: View?, slideOffset: Float) {
                    }
                    override fun onPanelStateChanged(_1: View?, _2: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
                        isTouchEnabled = newState != SlidingUpPanelLayout.PanelState.EXPANDED
                        re.scrollBy(0, 1)
                    }
                })

            }

            // replace the current layout with the fragment
            ui.owner.fragmentManager
                    .beginTransaction()
                    .add(idlayout, fragment)
                    .commit()
        }

