package sdw.drakirus.xyz.smartwallremote.component.helpers

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.support.v7.widget.CardView

/**
 * Created by drakirus (p.champion) on 14/02/18.
 */


// extension function to update the border of a card from a position

fun CardView.colorBorderReset() {
    this.background = null
}

fun CardView.colorBorderFromPos(color: Int, position: String) {

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
        else -> layerDrawable.setLayerInset(0, 15, 15, 15, 15)
    }

    this.background = layerDrawable
}
