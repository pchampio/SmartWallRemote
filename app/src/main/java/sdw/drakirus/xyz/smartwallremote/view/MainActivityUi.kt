package sdw.drakirus.xyz.smartwallremote.view

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
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
import org.jetbrains.anko.support.v4.space
import sdw.drakirus.xyz.smartwallremote.MainActivity
import sdw.drakirus.xyz.smartwallremote.R
import sdw.drakirus.xyz.smartwallremote.json.WallItem
import sdw.drakirus.xyz.smartwallremote.view.scenario.ScenarioChooserAdapter
import sdw.drakirus.xyz.smartwallremote.view.scenario.ScenarioData
import sdw.drakirus.xyz.smartwallremote.view.video.VideoChooserAdapter
import sdw.drakirus.xyz.smartwallremote.view.video.VideoData


/**
 * Created by drakirus (p.champion) on 28/01/18.
 */

inline fun ViewManager.slidingUpPanelLayout(ctx: Context, init: SlidingUpPanelLayout.() -> Unit): SlidingUpPanelLayout {
    return ankoView({ SlidingUpPanelLayout(it) }, theme = 0, init = init)
}

class MainActivityUi(val wallItem: WallItem) : AnkoComponent<MainActivity> {

    private val rows = wallItem.rows
    private val cols = wallItem.cols

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {


        val adapter_scenario = ScenarioChooserAdapter(ctx,
                listOf(
                        ScenarioData("test"),
                        ScenarioData("test"),
                        ScenarioData("test"),
                        ScenarioData("test"),
                        ScenarioData("test")
                ))

        var re :RecyclerView? = null

        // https://github.com/orhanobut/dialogplus
        val dialog_scenario = DialogPlus.newDialog(ctx)
                .setAdapter(adapter_scenario)
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

            val adapterVid = VideoChooserAdapter(
                    listOf(
                            VideoData("name 1", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                            VideoData("name 2", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                            VideoData("name 3", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                            VideoData("name 4", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                            VideoData("name 5 extrèmement t", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                            VideoData("name 6", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                            VideoData("name 7", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                            VideoData("name 8", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                            VideoData("name 9", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                            VideoData("name 10", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                            VideoData("name 11", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                            VideoData("name 12", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"),
                            VideoData("name 13", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg")
                    ),
                    object : VideoChooserAdapter.OnItemClickListener {
                        override fun onItemClick(item: VideoData) {
                            toast("Item Clicked" + item.text)
                            panelState = PanelState.COLLAPSED
                        }
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
                    re?.scrollBy(0, 1)
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
                                        ui.owner.selectedScreen(ui, row, col)
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
                        textColor = Color.RED
                        textSize = 24f
                        padding = dip(10)
                        gravity = Gravity.CENTER
                    }

                    button("test"){
                        onClick {
                            toast("test")
                        }
                    }
                }


                // http://tutos-android-france.com/material-design-recyclerview-et-cardview/
                re = recyclerView {
                    //                     layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = adapterVid
                }

                // disable the slidingUpPanelLayout if the user is not at the top
                re?.addOnScrollListener(object: RecyclerView.OnScrollListener() {
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
