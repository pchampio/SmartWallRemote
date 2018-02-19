package sdw.drakirus.xyz.smartwallremote.mainActivityUI

import android.graphics.Color
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick
import sdw.drakirus.xyz.smartwallremote.MainActivity
import sdw.drakirus.xyz.smartwallremote.component.helpers.resetColor

/**
 * Created by drakirus (p.champion) on 12/02/18.
 */

fun _LinearLayout.screenItem(ui: AnkoContext<MainActivity>, col: Int, row: Int) =
        verticalLayout {
            cardView {

                cardElevation = 5F
                useCompatPadding = true
                radius = 7f

                val screenItem = ui.owner.wall.getCheckBoxAt(col, row)
                screenItem?.cardView = this

                verticalLayout {
                    linearLayout {
                        if (screenItem != null) {
                            screenItem.checkBox = checkBox() {
                                onClick {
                                   ui.owner.hideShowPaintFAB()
                                }
                            }
                            screenItem.checkBox.resetColor()
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
                    if(screenItem != null)
                        ui.owner.toggleGroup(screenItem)
                }
            }.lparams(width = matchParent, weight = 1F)
        }.lparams(width = matchParent, weight = 1F)
