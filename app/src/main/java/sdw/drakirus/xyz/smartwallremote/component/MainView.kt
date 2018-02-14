package sdw.drakirus.xyz.smartwallremote.component

import android.graphics.Color
import android.view.Gravity
import com.orhanobut.dialogplus.DialogPlus
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import sdw.drakirus.xyz.smartwallremote.MainActivity
import sdw.drakirus.xyz.smartwallremote.R
import sdw.drakirus.xyz.smartwallremote.component.scenario.ScenarioChooserAdapter

/**
 * Created by drakirus (p.champion) on 12/02/18.
 */

fun SlidingUpPanelLayout.mainView(ui: AnkoContext<MainActivity>) =
        verticalLayout {
            backgroundResource = R.drawable.bg

            textView(ui.owner.wall.name + "'s Remote") {
                textColor = Color.WHITE
                textSize = 24f
                padding = dip(10)
            }.lparams(width = wrapContent, height = wrapContent) {
                gravity = Gravity.CENTER
            }

            for (row in 1..ui.owner.wall.rows) {
                linearLayout {
                    horizontalPadding = dip(10)

                    for (col in 1..ui.owner.wall.cols) {
                        screenItem(ui, col, row)
                    }

                }.lparams(width = matchParent)

            }

            // https://github.com/orhanobut/dialogplus
            val dialog_scenario = DialogPlus.newDialog(context)
                    .setAdapter(ScenarioChooserAdapter(context, ui.owner.listLayout))
                    .setGravity(Gravity.CENTER)
                    .setOnItemClickListener { dialog, item, view, position -> dialog.dismiss() }
                    .setFooter(R.layout.scenario_footer)
                    .setHeader(R.layout.scenario_header)
                    .setExpanded(false)
                    .create()

            button("Choose a scenario") {
                onClick {
                    dialog_scenario.show()
                }
            }

        }
