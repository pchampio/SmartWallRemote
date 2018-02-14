package sdw.drakirus.xyz.smartwallremote.component

import android.graphics.Color
import android.view.Gravity
import android.widget.Toast
import com.orhanobut.dialogplus.DialogPlus
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import es.dmoral.toasty.Toasty
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

            val wall = ui.owner.wall // reference
            val layoutOption = ui.owner.getLayoutConfig() // compute

            backgroundResource = R.drawable.bg

            textView(wall.name) {
                textColor = Color.WHITE
                textSize = 24f
                padding = dip(10)
            }.lparams(width = wrapContent, height = wrapContent) {
                gravity = Gravity.CENTER
            }

            for (row in 0 until wall.rows) {
                linearLayout {
                    horizontalPadding = dip(10)

                    for (col in 0 until wall.cols) {
                        screenItem(ui, col, row)
                    }

                }.lparams(width = matchParent)

            }

            // once the UI is init update the Group's colors
            if (!layoutOption.isEmpty())
                wall.updateColorGroup(layoutOption.get(0))

            // https://github.com/orhanobut/dialogplus
            val dialog_scenario = DialogPlus.newDialog(context)
                    .setAdapter(ScenarioChooserAdapter(context, layoutOption))
                    .setGravity(Gravity.CENTER)
                    .setOnItemClickListener { dialog, item, view, position ->
                        wall.updateColorGroup(layoutOption.get(position))
                        ui.owner.layoutConfigInUse = position
                        dialog.dismiss()
                    }
                    .setHeader(R.layout.scenario_header)
                    .setExpanded(false)
                    .create()

            button("Choose a regroupement") {
                onClick {
                    if (ui.owner.getLayoutConfig().isEmpty()) {
                        Toasty.error(ui.ctx, "Il n'y a pas de re-groupement\npour cette disposition", Toast.LENGTH_LONG, true).show();
                    } else {
                        dialog_scenario.show()
                    }
                }
            }

        }
