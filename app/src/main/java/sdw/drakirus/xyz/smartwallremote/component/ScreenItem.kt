package sdw.drakirus.xyz.smartwallremote.component

import android.graphics.Color
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick
import sdw.drakirus.xyz.smartwallremote.MainActivity
import sdw.drakirus.xyz.smartwallremote.R

/**
 * Created by drakirus (p.champion) on 12/02/18.
 */

fun _LinearLayout.screenItem(ui: AnkoContext<MainActivity>, col: Int, row: Int) =
        verticalLayout {
            cardView {

                cardElevation = dip(6).toFloat()
                useCompatPadding = true
                radius = 7f
                background = resources.getDrawable(R.drawable.test)

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
