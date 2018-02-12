package sdw.drakirus.xyz.smartwallremote.view.video;

/**
 * Created by drakirus (p.champion) on 28/01/18.
 */

public class VideoData {
    private String text;
    private String duration;
    private String imageUrl;

    public VideoData(String text,String duration, String imageUrl) {
        this.text = text;
        this.duration = duration;
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }
    public String getDuration() { return duration; }
    public String getUrl() { return imageUrl;}
}

