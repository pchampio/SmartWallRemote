package sdw.drakirus.xyz.smartwallremote

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewManager
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.OnItemClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onQueryTextListener
import sdw.drakirus.xyz.smartwallremote.component.scenario.ScenarioChooserAdapter
import sdw.drakirus.xyz.smartwallremote.component.scenario.ScenarioData
import sdw.drakirus.xyz.smartwallremote.component.video.VideoChooserAdapter
import sdw.drakirus.xyz.smartwallremote.component.video.VideoData
import sdw.drakirus.xyz.smartwallremote.json.WallItem


/**
 * Created by drakirus (p.champion) on 28/01/18.
 */

inline fun ViewManager.slidingUpPanelLayout(ctx: Context, init: SlidingUpPanelLayout.() -> Unit): SlidingUpPanelLayout {
    return ankoView({ SlidingUpPanelLayout(it) }, theme = 0, init = init)
}

class MainActivityUi(val wallItem: WallItem) : AnkoComponent<MainActivity> {

    private val rows = wallItem.rows
    private val cols = wallItem.cols

    private val scenarioList =
            listOf(
                    ScenarioData("test"),
                    ScenarioData("test"),
                    ScenarioData("test"),
                    ScenarioData("test"),
                    ScenarioData("test")
            )

    private val videoList =
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



    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {

        // https://github.com/orhanobut/dialogplus
        val dialog_scenario = DialogPlus.newDialog(ctx)
                .setAdapter(ScenarioChooserAdapter(ctx, scenarioList))
                .setGravity(Gravity.CENTER)
                .setOnItemClickListener(object : OnItemClickListener {
                    override fun onItemClick(dialog: DialogPlus, item: Any, view: View, position: Int) {
                        dialog.dismiss()
                    }
                })
                .setFooter(R.layout.scenario_footer)
                .setHeader(R.layout.scenario_header)
                .setExpanded(false)
                .create()

        // https://github.com/umano/AndroidSlidingUpPanel
        slidingUpPanelLayout(ctx) {

            val adapterVid = VideoChooserAdapter(videoList,
                    VideoChooserAdapter.OnItemClickListener { item ->
                        toast("Item Clicked" + item.text)
                        panelState = PanelState.COLLAPSED
                    }
            )

            setGravity(Gravity.BOTTOM)
            backgroundColor = Color.WHITE
            shadowHeight = 0

            addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
                override fun onPanelSlide(panel: View?, slideOffset: Float) {
                }

                override fun onPanelStateChanged(_1: View?, _2: PanelState?, newState: PanelState?) {
                    isTouchEnabled = newState != PanelState.EXPANDED
                }

            })

            verticalLayout {
                backgroundResource = R.drawable.bg

                textView(wallItem.name + "'s Remote") {
                    textColor = Color.WHITE
                    textSize = 24f
                    padding = dip(10)
                }.lparams(width = wrapContent, height = wrapContent){
                    gravity = Gravity.CENTER
                }

                for (row in 1..rows) {
                    linearLayout {
                        horizontalPadding = dip(10)

                        for (col in 1..cols) {
                            verticalLayout {

                                cardView {
                                    cardElevation = dip(6).toFloat()
                                    useCompatPadding = true
                                    radius = 7f

                                    background = resources.getDrawable(R.drawable.test)

                                    verticalLayout {
                                        linearLayout {

                                            val screenItem = wallItem.getCheckBoxAt(col, row)
                                            if (screenItem != null){
                                                screenItem.checkBox == checkBox()
                                                backgroundColor = Color.WHITE
                                            } else {
                                                backgroundColor = Color.DKGRAY
                                            }

                                            space()
                                        }.lparams(height = matchParent, width = matchParent)

                                    }.lparams{
                                        width = matchParent
                                        height = dip(60)
                                    }

                                    onClick {
                                        ui.owner.selectedScreen(row, col)
                                    }

                                }.lparams (width = matchParent, weight = 1F)

                            }.lparams(width = matchParent, weight = 1F)
                        }

                    }.lparams(width = matchParent)

                }

                button("Choose a scenario") {
                    onClick {
                        dialog_scenario.show()
                    }
                }

            }

            // Slider Content
            verticalLayout {

                linearLayout {

                    gravity = Gravity.CENTER

                    textView("Get Video"){
                        textColor = Color.BLUE
                        textSize = 24f
                        padding = dip(10)
                        gravity = Gravity.CENTER
                        onClick {

                        }
                    }

                    button("test"){
                        onClick {
                            toast("test")
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
                recyclerView {
                    //                     layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = adapterVid
                }
                .addOnScrollListener(object: RecyclerView.OnScrollListener() {
                    // disable the slidingUpPanelLayout if the user is not at the top
                    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (panelState == PanelState.EXPANDED){
                            isTouchEnabled = recyclerView != null && recyclerView.computeVerticalScrollOffset() == 0
                        }
                    }
                })

            }



        }

    }



}
