package sdw.drakirus.xyz.smartwallremote.component.helpers

import android.content.res.ColorStateList
import android.support.v4.widget.CompoundButtonCompat
import android.widget.CheckBox
import sdw.drakirus.xyz.smartwallremote.R

/**
 * Created by drakirus (p.champion) on 16/02/18.
 */

// extension function to update the color theme of a CheckBox

fun CheckBox.setColor(color: Int) {
    val states = arrayOf<IntArray>(intArrayOf(android.R.attr.state_checked), intArrayOf())
    val colors = intArrayOf(color, color)
    this.apply { CompoundButtonCompat.setButtonTintList(this, ColorStateList(states, colors)) }
}

fun CheckBox.resetColor() {
    this.setColor(R.color.colorAccent)
}

