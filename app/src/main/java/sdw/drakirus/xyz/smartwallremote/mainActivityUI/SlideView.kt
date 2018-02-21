package sdw.drakirus.xyz.smartwallremote.mainActivityUI

import android.support.v7.widget.RecyclerView
import android.view.View
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.toast
import sdw.drakirus.xyz.smartwallremote.MainActivity
import sdw.drakirus.xyz.smartwallremote.component.miniPlayer.MiniPlayerFragment
import sdw.drakirus.xyz.smartwallremote.component.video.VideoChooserFragment


/**
 * Created by drakirus (p.champion) on 12/02/18.
 */

fun SlidingUpPanelLayout.slideView(ui: AnkoContext<MainActivity>) =
        frameLayout {
            val fragment_miniPlayer = 14
            val fragment_video_chooser = 15

            lateinit var frameMiniPlayerFragment: View
            lateinit var frameIncide: View


            frameIncide = frameLayout {
                id = fragment_video_chooser
                alpha = 0F
            }

            frameMiniPlayerFragment = frameLayout {
                id = fragment_miniPlayer
            }


            val fragmentChooser = VideoChooserFragment()
            val miniPlayerFragment = MiniPlayerFragment()

            // bind the on click
            fragmentChooser.setOnClick (object : VideoChooserFragment.OnClick() {
                override fun run() {
                    ui.ctx.toast("Selected: " + videoModel.getTitle())
                    panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                }
            } )


            // setup the biding with this SlidingUpPanelLayout to get the desired scroll behaviour
            fragmentChooser.setOnCreateEvent {
                val re = fragmentChooser.recyclerView
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
                        frameMiniPlayerFragment.alpha = 1 - slideOffset
                        frameIncide.alpha = slideOffset
                    }
                    override fun onPanelStateChanged(_1: View?, _2: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
                        isTouchEnabled = newState != SlidingUpPanelLayout.PanelState.EXPANDED
                        re.scrollBy(0, 1)

                        if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                            frameMiniPlayerFragment.visibility = View.GONE
                        } else {
                            frameMiniPlayerFragment.visibility = View.VISIBLE
                        }
                    }
                })

            }

            ui.owner.fragmentManager
                    .beginTransaction()
                    .add(fragment_miniPlayer, miniPlayerFragment)
                    .add(fragment_video_chooser, fragmentChooser)
                    .commit()
        }

