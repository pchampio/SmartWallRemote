package sdw.drakirus.xyz.smartwallremote.component

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick
import sdw.drakirus.xyz.smartwallremote.MainActivity

/**
 * Created by drakirus (p.champion) on 12/02/18.
 */

fun _LinearLayout.screenItem(ui: AnkoContext<MainActivity>, col: Int, row: Int) =
        verticalLayout {
            cardView {

                cardElevation = 5F
                useCompatPadding = true
//                cardBackgroundColor = Color.BLUE
                radius = 7f

                if (col == 1 && row == 1) {
                    background = colorBorderBuilder(ui, Color.RED, "top-left")
                }
                if (col == 1 && row == 2) {
                    background = colorBorderBuilder(ui, Color.RED, "left")
                }
                if (col == 1 && row == 3) {
                    background = colorBorderBuilder(ui, Color.RED, "bottom-left")
                }

                if (col == 2 && row ==1) {
                    background = colorBorderBuilder(ui, Color.RED, "top")
                }
                if (col == 2 && row ==2) {
                    background = colorBorderBuilder(ui, Color.RED, "all")
                }
                if (col == 2 && row ==3) {
                    background = colorBorderBuilder(ui, Color.RED, "bottom")
                }

                if (col == 3 && row ==1) {
                    background = colorBorderBuilder(ui, Color.RED, "top-right")
                }
                if (col == 3 && row ==2) {
                    background = colorBorderBuilder(ui, Color.RED, "right")
                }
                if (col == 3 && row ==3) {
                    background = colorBorderBuilder(ui, Color.RED, "bottom-right")
                }

                if (col == 4 && row in 1..3) {
                    if (row == 1)
                        background = colorBorderBuilder(ui, Color.BLUE, "top-only")
                    if (row == 2)
                        background = colorBorderBuilder(ui, Color.BLUE, "vertical")
                    if (row == 3)
                        background = colorBorderBuilder(ui, Color.BLUE, "bottom-only")
                }

                if (col in 1..3 && row == 4) {
                    if (col == 1)
                        background = colorBorderBuilder(ui, Color.YELLOW, "right-only")
                    if (col == 2)
                        background = colorBorderBuilder(ui, Color.YELLOW, "horizontal")
                    if (col == 3)
                        background = colorBorderBuilder(ui, Color.YELLOW, "left-only")
                }

//                if (col == 4 && row == 4)
//                    background = colorBorderBuilder(ui, Color.GREEN, "all")

                verticalLayout {
                    linearLayout {
                        val screenItem = ui.owner.wall.getCheckBoxAt(col, row)
                        if (screenItem != null) {
                            screenItem.checkBox == checkBox()
                            backgroundColor = Color.WHITE
                        } else {
                            backgroundColor = Color.DKGRAY
                        }
                        space()
                    }.lparams(height = matchParent, width = matchParent)
                }.lparams {
                    width = matchParent
                    height = dip(60)
                }

                onClick {
                    ui.owner.selectedScreen(row, col)
                }
            }.lparams(width = matchParent, weight = 1F)
        }.lparams(width = matchParent, weight = 1F)


private fun colorBorderBuilder(ui: AnkoContext<MainActivity>, color: Int, position: String): LayerDrawable {

    val overBorder = GradientDrawable()
    overBorder.setShape(GradientDrawable.RECTANGLE);
    overBorder.alpha = 210
    overBorder.setColor(color) // test == 0

    val layers = arrayOf(overBorder)
    val layerDrawable = LayerDrawable(layers)

    when(position){
        // index of GradientDrawable, left, top, right, bottom
        "top-left" -> layerDrawable.setLayerInset(0, 15, 15, 0, 0)
        "bottom-left" -> layerDrawable.setLayerInset(0, 15, 0, 0, 15)
        "top-right" -> layerDrawable.setLayerInset(0, 0, 15, 15, 0)
        "bottom-right" -> layerDrawable.setLayerInset(0, 0, 0, 15, 15)
        "bottom" -> layerDrawable.setLayerInset(0, 0, 0, 0, 15)
        "top" -> layerDrawable.setLayerInset(0, 0, 15, 0, 0)
        "right" -> layerDrawable.setLayerInset(0, 0, 0, 15, 0)
        "left" -> layerDrawable.setLayerInset(0, 15, 0, 0, 0)
        "all" ->layerDrawable.setLayerInset(0, 0, 0, 0, 0)
        "vertical" ->layerDrawable.setLayerInset(0, 15, 0, 15, 0)

        "bottom-only" -> layerDrawable.setLayerInset(0, 15, 0, 15, 15)
        "top-only" -> layerDrawable.setLayerInset(0, 15, 15, 15, 0)

        "left-only" -> layerDrawable.setLayerInset(0, 0, 15, 15, 15)
        "right-only" -> layerDrawable.setLayerInset(0, 15, 15, 0, 15)

        "horizontal" ->layerDrawable.setLayerInset(0, 0, 15, 0, 15)
    }

    return layerDrawable
}