package sdw.drakirus.xyz.smartwallremote.component.video;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sdw.drakirus.xyz.smartwallremote.R;

public class VideoChooserFragment extends Fragment implements VideoAdapter.VideosAdapterListener {

    private List<VideoModel> videoList;
    private VideoAdapter videosAdapter;

    private RecyclerView recyclerView;
    private Runnable onCreate;
    private OnClick onClick;

    public void setOnCreateEvent(Runnable runnable) {
        onCreate = runnable;
    }

    public static class OnClick implements Runnable {
        protected VideoModel videoModel;

        void setVideoModel(VideoModel videoModel) {
            this.videoModel = videoModel;
        }

        @Override
        public void run() {

        }
    }
    public void setOnClick(OnClick runnable) { onClick = runnable; }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v =  inflater.inflate(R.layout.activity_video_new, container, false);

        recyclerView = v.findViewById(R.id.recycler_view);
        videoList = new ArrayList<>();
        videosAdapter = new VideoAdapter(videoList, this);
        recyclerView.setAdapter(videosAdapter);

        //GridLayout with 2 column
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        //associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = v.findViewById(R.id.search_view);
        //setup the manager
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

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


        onCreate.run();
        return v;
    }

    private void initializeVideos(){

        final List<VideoModel> listVideo = new ArrayList<>();

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

    // bind to parent for the onclick
    @Override
    public void onContactSelected(VideoModel videoModel) {
        onClick.setVideoModel(videoModel);
        onClick.run();
    }
}
