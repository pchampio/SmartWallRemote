package sdw.drakirus.xyz.smartwallremote.component.video;

import android.graphics.Bitmap;

import java.net.MalformedURLException;
import java.net.URL;

import sdw.drakirus.xyz.smartwallremote.R;

public class VideoModel {


    private final String title;
    private final String imageUrl;
    // In seconds
    private final int duration;
    private final Bitmap image;


    public VideoModel(String title,String imageUrl, int duration) {
        this.title = title;
        this.duration = duration;
        this.imageUrl = imageUrl;

        //TODO : initialise img
        this.image = null;
        try {
            URL url = new URL(imageUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }
    public String getStringDuration() {
        return String.format("%d:%02d:%02d", duration / 3600, (duration % 3600) / 60, (duration % 60));
    }

    public int getImage() {return R.drawable.video_preview;}

}
