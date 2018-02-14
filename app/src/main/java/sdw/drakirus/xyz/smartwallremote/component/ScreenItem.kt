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
                radius = 7f

                val screenItem = ui.owner.wall.getCheckBoxAt(col, row)?.copy(col = col-1, row = row-1)

                if (screenItem != null) {
                    ui.owner.listLayout.get(1).grpScreen.forEach { grpScreen ->
                        val position = grpScreen.getPosition(screenItem)
                        if (position != grpScreen.NOPOS) {
                            background = colorBorderBuilder(grpScreen.color, position)
                        }
                    }
                }

                verticalLayout {
                    linearLayout {
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


private fun colorBorderBuilder(color: Int, position: String): LayerDrawable {

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
        "horizontal" ->layerDrawable.setLayerInset(0, 0, 15, 0, 15)

        "bottom-only" -> layerDrawable.setLayerInset(0, 15, 0, 15, 15)
        "top-only" -> layerDrawable.setLayerInset(0, 15, 15, 15, 0)

        "left-only" -> layerDrawable.setLayerInset(0, 0, 15, 15, 15)
        "right-only" -> layerDrawable.setLayerInset(0, 15, 15, 0, 15)

    }

    return layerDrawable
}