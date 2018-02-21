package sdw.drakirus.xyz.smartwallremote.component.miniPlayer;

import android.app.Fragment;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import sdw.drakirus.xyz.smartwallremote.R;

/**
 * Created by drakirus (p.champion) on 20/02/18.
 */

public class MiniPlayerFragment extends Fragment {

    TextView miniPlayerTitle;
    ImageView miniPlayerPlayPauseButton;
    ProgressBar progressBar;

    boolean isPlaying = false;

    private PlayPauseDrawable miniPlayerPlayPauseDrawable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.component_mini_player, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        miniPlayerTitle = view.findViewById(R.id.mini_player_title);
        miniPlayerPlayPauseButton = view.findViewById(R.id.mini_player_play_pause_button);
        progressBar = view.findViewById(R.id.mini_player_progress);


        view.setOnTouchListener(new FlingPlayBackController(getActivity()));
        setUpMiniPlayer();

        new Thread(() -> {
            int i = 0;
            while (true) {
                i++;
                i%=100;
                int finalI = i;
                new Handler(Looper.getMainLooper()).post(() -> {
                    progressBar.setProgress(finalI);
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        miniPlayerTitle.setText("Text de la mffffffffffffffffffffffffffusic...");
    }

    private void setUpMiniPlayer() {
        setUpPlayPauseButton();
        progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
    }

    private void setUpPlayPauseButton() {
        miniPlayerPlayPauseDrawable = new PlayPauseDrawable(getActivity());
        miniPlayerPlayPauseButton.setImageDrawable(miniPlayerPlayPauseDrawable);

        miniPlayerPlayPauseButton.setOnClickListener(view -> {
            if (isPlaying) {
                miniPlayerPlayPauseDrawable.setPause(true);
            } else {
                miniPlayerPlayPauseDrawable.setPlay(true);
            }
            isPlaying = !isPlaying;

        });
    }

    private class FlingPlayBackController implements View.OnTouchListener {

        GestureDetector flingPlayBackController;

        public FlingPlayBackController(Context context) {
            flingPlayBackController = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    if (Math.abs(velocityX) > Math.abs(velocityY)) {
                        if (velocityX < 0) {
                            MiniPlayerFragment.this.miniPlayerTitle.setText("previous Song");
                            System.out.println("previous Song");
                            return true;
                        } else if (velocityX > 0) {
                            MiniPlayerFragment.this.miniPlayerTitle.setText("next Song");
                            System.out.println("next Song");
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    System.out.println("distanceX " + distanceX);
                    System.out.println("distanceY " + distanceY);
                    return false;
                }
            });
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return flingPlayBackController.onTouchEvent(event);
        }
    }
}
