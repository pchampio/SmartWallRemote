package sdw.drakirus.xyz.smartwallremote.component.miniPlayer

import android.app.Fragment
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import org.jetbrains.anko.sdk25.coroutines.onClick
import sdw.drakirus.xyz.smartwallremote.R

/**
 * Created by drakirus (p.champion) on 20/02/18.
 */

class MiniPlayerFragment : Fragment() {

    var miniPlayerTitle: TextView? = null
    private var miniPlayerPlayPauseButton: ImageView? = null
    var progressBar: ProgressBar? = null

    var onCreateEvent: Runnable? = null
    var onClickPlay: Runnable? = null
    var onNext: Runnable? = null
    var onPrevious: Runnable? = null
    var onExpand: Runnable? = null


    var miniPlayerPlayPauseDrawable: PlayPauseDrawable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.component_mini_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        miniPlayerTitle = view.findViewById(R.id.mini_player_title)
        miniPlayerPlayPauseButton = view.findViewById(R.id.mini_player_play_pause_button)
        progressBar = view.findViewById(R.id.mini_player_progress)

        val miniPlayerImageExpand: ImageView = view.findViewById(R.id.mini_player_image)
        miniPlayerImageExpand.onClick {
            onExpand?.run()
        }
        view.setOnTouchListener(FlingPlayBackController(activity))
        setUpPlayPauseButton()
        progressBar?.progressTintList = ColorStateList.valueOf(Color.BLACK)
        onCreateEvent?.run()
    }

    private fun setUpPlayPauseButton() {
        miniPlayerPlayPauseDrawable = PlayPauseDrawable(activity)
        miniPlayerPlayPauseButton?.setImageDrawable(miniPlayerPlayPauseDrawable)

        miniPlayerPlayPauseButton?.setOnClickListener { _ ->
            onClickPlay?.run()
        }
    }

    fun setColor(color: Int) {
        progressBar?.progressTintList = ColorStateList.valueOf(color)
        miniPlayerTitle?.setTextColor(ColorStateList.valueOf(color))
    }


    private inner class FlingPlayBackController(context: Context) : View.OnTouchListener {

        internal var flingPlayBackController: GestureDetector

        init {
            flingPlayBackController = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                    if (velocityX < 0) {
                        onPrevious?.run()
                        return true
                    } else if (velocityX > 0) {
                        onNext?.run()
                        return true
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
