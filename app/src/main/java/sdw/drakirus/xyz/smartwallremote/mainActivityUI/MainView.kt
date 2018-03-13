package sdw.drakirus.xyz.smartwallremote.mainActivityUI

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.view.Gravity
import android.webkit.URLUtil
import android.widget.TextView
import android.widget.Toast
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.sdk25.coroutines.onClick
import sdw.drakirus.xyz.smartwallremote.MainActivity
import sdw.drakirus.xyz.smartwallremote.R
import sdw.drakirus.xyz.smartwallremote.component.layout.UtilsLayout


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

                    menu.add(context.getString(R.string.choose_wall)).setOnMenuItemClickListener {
                        ui.owner.getWallAllConfig()
                        true
                    }

                    menu.add(context.getString(R.string.edit_url_server)).setOnMenuItemClickListener {
                        askForBaseUrl(ui.owner) { url ->
                           ui.owner.putBaseUrl(url)
                        }
                        true
                    }

                    menu.add(context.getString(R.string.about)).setOnMenuItemClickListener {
                        aboutTheApp(ui.owner)
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

                button(context.getString(R.string.choose_grp)) {
                    onClick {
                        if (ui.owner.getLayoutConfig().isEmpty()) {
                            Toasty.info(ui.ctx, ui.ctx.getString(R.string.no_grp), Toast.LENGTH_LONG, true).show();
                        } else {
                            ui.owner.dialogChooseLayout()
                        }
                    }
                }
                button(context.getString(R.string.choose_sce)) {
                    onClick {
                        if (ui.owner.getScenarioConfig().isEmpty()) {
                            Toasty.info(ui.ctx, ui.ctx.getString(R.string.no_sce), Toast.LENGTH_LONG, true).show();
                        } else {
                            ui.owner.dialogChooseScenario()
                        }
                    }
                }
            }.lparams{
                width = matchParent;
                height = wrapContent
            }

            ui.owner.saveFAB = FabButtonLoader() {

                ring.setProgressColor(resources.getColor(R.color.colorSecondLoading))
                circle.setShowEndBitmap(true)
                showShadow(true)
                setIcon(R.drawable.save, R.drawable.ok)
                setColor(resources.getColor(R.color.colorSecond))
                setIndeterminate(true)
                alpha = 0F

                onClick {
                    if (isShow)
                        askForLayoutName(ui) { text ->
                            ui.owner.saveLayout(text)
                        }
                }
            }.lparams {
                margin = dip(15)
                height = dip(50)
                width = dip(50)

                bottomMargin = 225
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
                        title = context.getString(R.string.ask_name_layout)
                        setTitleTextColor(ContextCompat.getColor(ctx, android.R.color.white))
                    }
                    imageView() {
                        verticalPadding = 20

                        val newLayout = ui.owner.createNewLayout("_tmp")
                        imageBitmap = UtilsLayout.makeBitmap(newLayout)
                    }
                    val task = editText {
                        setText(ui.owner.faker?.commerce?.productName(), TextView.BufferType.EDITABLE)
                        padding = dip(20)
                    }
                    positiveButton(context.getString(R.string.save)) {
                        if(task.text.toString().isEmpty()) {
                            Toasty.info(ui.ctx, context.getString(R.string.no_name), Toast.LENGTH_LONG, true).show();
                        }
                        else {
                            onAdd(task.text.toString())
                        }
                    }
                    negativeButton(context.getString(R.string.cancel)) {}
                }
            }
        }.show()
fun askForBaseUrl(ui: MainActivity, onAdd: (name :String) -> Unit) =
        ui.ctx.alert {
            customView {
                verticalLayout {
                    //Dialog Title
                    toolbar {
                        lparams(width = matchParent, height = wrapContent)
                        backgroundColor = ContextCompat.getColor(ctx, R.color.colorAccent)
                        title = context.getString(R.string.ask_server_name)
                        setTitleTextColor(ContextCompat.getColor(ctx, android.R.color.white))
                    }
                    val task = editText {
                        setText(ui.getBaseUrl())
                        padding = dip(20)
                    }
                    positiveButton(context.getString(R.string.save)) {
                        if(task.text.toString().isEmpty()) {
                            Toasty.info(ui.ctx, ui.ctx.getString(R.string.no_name), Toast.LENGTH_LONG, true).show();
                        }
                        else if(!URLUtil.isValidUrl(task.text.toString())) {
                            Toasty.warning(ui.ctx, ui.ctx.getString(R.string.oops_url), Toast.LENGTH_LONG, true).show();
                        }
                        else {
                            onAdd(task.text.toString())
                        }
                    }
                    negativeButton(context.getString(R.string.cancel)) {}
                }
            }
        }.show()

fun aboutTheApp(ui: MainActivity) =
        ui.ctx.alert {
            customView {
                verticalLayout {

                    backgroundColor = ContextCompat.getColor(ctx, R.color.material_grey_800)

                    //Dialog Title
                    toolbar {
                        lparams(width = matchParent, height = wrapContent)
                        backgroundColor = ContextCompat.getColor(ctx, R.color.colorAccent)
                        title = "About " + resources.getText(R.string.app_name)
                        setTitleTextColor(ContextCompat.getColor(ctx, android.R.color.white))
                    }

                    imageView() {
                        setImageResource(R.mipmap.ic_launcher)
                        topPadding = dip(10)
                    }

                    textView() {
                        text = context.getString(R.string.about_description)
                        padding = dip(20)
                        textColor = ContextCompat.getColor(ctx, R.color.white)
                    }

                    textView() {
                        text = context.getString(R.string.github_repository)
                        padding = dip(20)
                        textColor = ContextCompat.getColor(ctx, R.color.lite_blue)

                        onClick {
                            val browser = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Drakirus/SmartWallRemote"))

                            startActivity(ui.ctx, browser, null)
                        }
                    }
                    //positiveButton("Close") {}
                }
            }
        }.show()


