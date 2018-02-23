package sdw.drakirus.xyz.smartwallremote.component.miniPlayer

import android.app.Fragment
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import org.jetbrains.anko.runOnUiThread
import sdw.drakirus.xyz.smartwallremote.R

/**
 * Created by drakirus (p.champion) on 20/02/18.
 */

class MiniPlayerFragment : Fragment() {

    var miniPlayerTitle: TextView? = null
        private set
    var miniPlayerPlayPauseButton: ImageView? = null
        private set
    var progressBar: ProgressBar? = null
        private set

    var onClickPlay: Runnable? = null
    var onNext: Runnable? = null
    var onPrevious: Runnable? = null
    var onExpand: Runnable? = null

    var onCreate: Runnable? = null

    internal var isPlaying = false

    private var miniPlayerPlayPauseDrawable: PlayPauseDrawable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.component_mini_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        miniPlayerTitle = view.findViewById(R.id.mini_player_title)
        miniPlayerPlayPauseButton = view.findViewById(R.id.mini_player_play_pause_button)
        progressBar = view.findViewById(R.id.mini_player_progress)


        view.setOnTouchListener(FlingPlayBackController(activity))
        setUpMiniPlayer()

        Thread {
            var i = 0
            while (true) {
                i++
                i %= 100
                runOnUiThread {
                    progressBar?.progress = i
                }
                Thread.sleep(100)

            }
        }.start()
        miniPlayerTitle?.text = "Text de la mffffffffffffffffffffffffffusic..."
    }

    private fun setUpMiniPlayer() {
        setUpPlayPauseButton()
        progressBar?.progressTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
    }

    private fun setUpPlayPauseButton() {
        miniPlayerPlayPauseDrawable = PlayPauseDrawable(activity)
        miniPlayerPlayPauseButton?.setImageDrawable(miniPlayerPlayPauseDrawable)

        miniPlayerPlayPauseButton?.setOnClickListener { view ->

        }
    }

    private inner class FlingPlayBackController(context: Context) : View.OnTouchListener {

        internal var flingPlayBackController: GestureDetector

        init {
            flingPlayBackController = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                    if (Math.abs(velocityX) > Math.abs(velocityY)) {
                        if (velocityX < 0) {
                            miniPlayerTitle?.text = "next Song"
                            return true
                        } else if (velocityX > 0) {
                            miniPlayerTitle?.text = "previous Song"
                            return true
                        }
                    }
                    return false
                }

            })
        }

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            return flingPlayBackController.onTouchEvent(event)
        }
    }
}
