package sdw.drakirus.xyz.smartwallremote.view.video;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sdw.drakirus.xyz.smartwallremote.R;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }

        final List<VideoData> listVideo = new ArrayList<VideoData>();

        listVideo.add(new VideoData("name 1", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"));
        listVideo.add(new VideoData("name 2", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"));
        listVideo.add(new VideoData("name 3", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"));
        listVideo.add(new VideoData("name 4", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"));
        listVideo.add(new VideoData("name 5", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"));
        listVideo.add(new VideoData("name 6", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"));
        listVideo.add(new VideoData("name 7", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"));
        listVideo.add(new VideoData("name 8", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"));
        listVideo.add(new VideoData("name 9", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"));
        listVideo.add(new VideoData("name 10", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"));
        listVideo.add(new VideoData("name 11", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"));
        listVideo.add(new VideoData("name 12", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"));
        listVideo.add(new VideoData("name 13", "00:00:00", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg"));

        final VideoChooserAdapter.OnItemClickListener listener = new VideoChooserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VideoData item) {
                Context context = getApplicationContext();
                Toast.makeText(context,"Item Clicked" + item.getText(), Toast.LENGTH_LONG).show();
            }
        };

        VideoChooserAdapter adapterVideo = new VideoChooserAdapter(listVideo,listener);

    }

    private void doMySearch(String query){

    }
}
