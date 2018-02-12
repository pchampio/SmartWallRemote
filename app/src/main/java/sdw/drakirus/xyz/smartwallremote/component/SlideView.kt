package sdw.drakirus.xyz.smartwallremote.component

import android.content.ContentValues.TAG
import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onQueryTextListener
import sdw.drakirus.xyz.smartwallremote.MainActivity
import sdw.drakirus.xyz.smartwallremote.component.video.VideoChooserAdapter

/**
 * Created by drakirus (p.champion) on 12/02/18.
 */

fun SlidingUpPanelLayout.slideView(ui: AnkoContext<MainActivity>) =
        verticalLayout {

            linearLayout {
                gravity = Gravity.CENTER

                textView("Get Video") {
                    textColor = Color.BLUE
                    textSize = 24f
                    padding = dip(10)
                    gravity = Gravity.CENTER
                    onClick {
                    }
                }
                button("test") {
                    onClick {
                        ui.ctx.toast("test")
                    }
                }
            }
            searchView {
                onQueryTextListener {
                    onQueryTextSubmit(listener = { s ->
                        Log.d(TAG, "submit= " + s)
                        true
                    })
                }
            }


            // http://tutos-android-france.com/material-design-recyclerview-et-cardview/
            val re = recyclerView {
                //                     layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
                layoutManager = GridLayoutManager(context, 2)

                adapter = VideoChooserAdapter(ui.owner.videoList,
                    VideoChooserAdapter.OnItemClickListener { item ->
                        ui.ctx.toast("Item Clicked" + item.text)
                        panelState = PanelState.COLLAPSED
                    })
            }
            re.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                // disable the slidingUpPanelLayout if the user is not at the top
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (panelState == PanelState.EXPANDED) {
                        isTouchEnabled = recyclerView != null && recyclerView.computeVerticalScrollOffset() == 0
                    }
                }
            })

            addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
                override fun onPanelSlide(panel: View?, slideOffset: Float) {
                }

                override fun onPanelStateChanged(_1: View?, _2: PanelState?, newState: PanelState?) {
                    isTouchEnabled = newState != PanelState.EXPANDED
                    re.scrollBy(0, 1)
                }

            })
        }
