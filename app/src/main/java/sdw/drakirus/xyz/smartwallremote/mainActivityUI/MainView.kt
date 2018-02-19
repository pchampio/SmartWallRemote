package sdw.drakirus.xyz.smartwallremote.mainActivityUI

import android.content.res.ColorStateList
import android.graphics.Color
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.widget.Toast
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.sdk25.coroutines.onClick
import sdw.drakirus.xyz.smartwallremote.MainActivity
import sdw.drakirus.xyz.smartwallremote.R
import sdw.drakirus.xyz.smartwallremote.component.scenario.UtilsLayout

/**
 * Created by drakirus (p.champion) on 12/02/18.
 */

fun SlidingUpPanelLayout.mainView(ui: AnkoContext<MainActivity>) =

        relativeLayout() {
            backgroundResource = R.drawable.bg

            verticalLayout {
                val wall = ui.owner.wall // referenced

                toolbar {

                    horizontalPadding = 10
                    overflowIcon.setTint(Color.WHITE)

                    textView(wall.name) {
                        textColor = Color.WHITE
                        textSize = 24f
                        padding = dip(10)
                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER
                    }

                    menu.add("Choose Another Wall").setOnMenuItemClickListener {
                        ui.owner.getAndChooseWall()
                        true
                    }

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
                val layoutOption = ui.owner.getLayoutConfig()
                if (!layoutOption.isEmpty()) {
                    ui.owner.layoutConfigInUse = 0
                    wall.updateColorGroup(layoutOption.get(0))

                }

                button("Choose a regroupement") {
                    onClick {
                        if (ui.owner.getLayoutConfig().isEmpty()) {
                            Toasty.info(ui.ctx, "Il n'y a pas de re-groupement\npour cette disposition", Toast.LENGTH_LONG, true).show();
                        } else {
                            ui.owner.dialogChooseGrp()
                        }
                    }
                }
            }.lparams{
                width = matchParent;
                height = wrapContent
            }

            ui.owner.saveFAB = floatingActionButton() {
                size = FloatingActionButton.SIZE_MINI
                imageResource = R.drawable.save
                this.hide()
                backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorSecond))
                onClick {
                    askForLayoutName(ui) { text ->
                        ui.owner.saveLayout(text)
                    }
                }
            }.lparams {
                margin = dip(15)

                bottomMargin = 250
                alignParentBottom()
                alignParentEnd()
                alignParentRight()

                gravity = Gravity.BOTTOM or Gravity.END
            }

            ui.owner.paintFAB = floatingActionButton() {
                imageResource = R.drawable.brush
                this.hide()
                onClick {
                    ui.owner.createAGroup()
                }
            }.lparams{
                //setting button to bottom right of the screen
                margin = dip(15)

                alignParentBottom()
                alignParentEnd()
                alignParentRight()

                gravity = Gravity.BOTTOM or Gravity.END
            }

            space().lparams {
                margin = dip(15)

                alignParentBottom()
                alignParentEnd()
                alignParentRight()

                gravity = Gravity.BOTTOM or Gravity.END
            }

        }

fun askForLayoutName(ui: AnkoContext<MainActivity>, onAdd: (name :String) -> Unit) =
        ui.ctx.alert {
            customView {
                verticalLayout {
                    //Dialog Title
                    toolbar {
                        lparams(width = matchParent, height = wrapContent)
                        backgroundColor = ContextCompat.getColor(ctx, R.color.colorAccent)
                        title = "What's the name of this layout"
                        setTitleTextColor(ContextCompat.getColor(ctx, android.R.color.white))
                    }
                    imageView() {
                        verticalPadding = 20

                        val newLayout = ui.owner.createNewLayout("_tmp")
                        imageBitmap = UtilsLayout.makeBitmap(newLayout)
                    }
                    val task = editText {
                        hint = "Name"
                        padding = dip(20)
                    }
                    positiveButton("Save") {
                        if(task.text.toString().isEmpty()) {
                            Toasty.info(ui.ctx, "Oops!! Your name is empty", Toast.LENGTH_LONG, true).show();
                        }
                        else {
                            onAdd(task.text.toString())
                        }
                    }
                    negativeButton("Cancel") {}
                }
            }
        }.show()


