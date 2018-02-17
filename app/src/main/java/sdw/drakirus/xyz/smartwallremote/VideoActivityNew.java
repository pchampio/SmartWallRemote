package sdw.drakirus.xyz.smartwallremote;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sdw.drakirus.xyz.smartwallremote.component.videoNew.VideoModel;
import sdw.drakirus.xyz.smartwallremote.component.videoNew.VideosAdapter;

public class VideoActivityNew extends AppCompatActivity implements VideosAdapter.VideosAdapterListener {

    private RecyclerView recyclerView;
    private List<VideoModel> videoList;
    private VideosAdapter videosAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_new);

        recyclerView = findViewById(R.id.recycler_view);
        videoList = new ArrayList<>();
        videosAdapter = new VideosAdapter(this,videoList, this);
        recyclerView.setAdapter(videosAdapter);

        //GridLayout with 2 column
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

        //associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.search_view);
        //setup the manager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //open the searchView by default
        searchView.setIconified(false);
        //close the keyboard
        searchView.clearFocus();

        //initialize the data inside the adapter
        initializeVideos();

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                videosAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                videosAdapter.getFilter().filter(query);
                return false;
            }
        });

        //disable the closeListener
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //open the searchView by default
                //searchView.setIconified(false);
                //close the keyboard
                //searchView.clearFocus();

                //true to override completely
                return true;
            }
        });



        //get the initial search ..
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            videosAdapter.getFilter().filter(query);
        }
    }

    private void initializeVideos(){

        final List<VideoModel> listVideo = new ArrayList<VideoModel>();

        listVideo.add(new VideoModel("name 1", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg",1000));
        listVideo.add(new VideoModel("name 2",  "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg",1000));
        listVideo.add(new VideoModel("name 3",  "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg",1000));
        listVideo.add(new VideoModel("name 4", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg",1000));
        listVideo.add(new VideoModel("name 5",  "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg",1000));
        listVideo.add(new VideoModel("name 6", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg",1000));
        listVideo.add(new VideoModel("name 7",  "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg",1000));
        listVideo.add(new VideoModel("name 8",  "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg",1000));
        listVideo.add(new VideoModel("name 9",  "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg",1000));
        listVideo.add(new VideoModel("name 10",  "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg",1000));
        listVideo.add(new VideoModel("name 11", "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg",1000));
        listVideo.add(new VideoModel("name 12",  "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg",1000));
        listVideo.add(new VideoModel("name 13",  "http://image.jeuxvideo.com/medias-md/151750/1517500592-857-card.jpg",1000));

        videoList.clear();
        videoList.addAll(listVideo);

        //refresh the view
        videosAdapter.notifyDataSetChanged();
    }

    @Override
    public void onContactSelected(VideoModel videoModel) {
        Toast.makeText(getApplicationContext(), "Selected: "+ videoModel.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
