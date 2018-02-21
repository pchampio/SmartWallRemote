package sdw.drakirus.xyz.smartwallremote.component.video;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.MalformedURLException;
import java.net.URL;

import sdw.drakirus.xyz.smartwallremote.R;

/**
 * Created by remi on 17/02/18.
 */

public class VideoModel {

    private final String title;
    private final String imageUrl;
    // In seconds
    private final int duration;
    private Bitmap image;

    public VideoModel(String title,String imageUrl, int duration) {
        this.title = title;
        this.duration = duration;
        this.imageUrl = imageUrl;
        this.image = null;

    }

    public String getTitle() {
        return title;
    }

    public String getStringDuration() {
        return String.format("%d:%02d:%02d", duration / 3600, (duration % 3600) / 60, (duration % 60));
    }

    //throw an exception to indicate the view that we couldn't retrieve the image
    public Bitmap getImage() throws Exception {
        if(image == null){
            URL url = new URL(imageUrl);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }
        return image;
    }

}
