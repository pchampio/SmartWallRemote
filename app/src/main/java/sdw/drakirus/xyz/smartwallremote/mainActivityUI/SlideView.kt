package sdw.drakirus.xyz.smartwallremote.mainActivityUI

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import es.dmoral.toasty.Toasty
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
            val bundle = Bundle()
            bundle.putString("videos", Gson().toJson(ui.owner.videoConfig))
            fragmentChooser.arguments = bundle
            val miniPlayerFragment = MiniPlayerFragment()

            // bind the on click
            fragmentChooser.setOnClick (object : VideoChooserFragment.OnClick() {
                override fun run() {
                    ui.ctx.toast("Selected: " + videoModel.title)
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

                        if (ui.owner.videoConfig?.videos?.isEmpty() ?: true) {
                            Toasty.warning(ui.ctx, "No Videos are available", Toast.LENGTH_SHORT).show()
                        }

                        // if the recyclerView is scrollable
                        if (re.computeHorizontalScrollRange() > re.getWidth() || re.computeVerticalScrollRange() > re.getHeight()) {
                            isTouchEnabled = newState != SlidingUpPanelLayout.PanelState.EXPANDED
                            re.scrollBy(0, 1)
                        }

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

