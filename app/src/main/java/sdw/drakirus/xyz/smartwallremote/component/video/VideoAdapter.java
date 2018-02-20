package sdw.drakirus.xyz.smartwallremote.component.video;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import sdw.drakirus.xyz.smartwallremote.R;

/**
 * Created by remi on 17/02/18.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder>
        implements Filterable{

    private List<VideoModel> videoList;
    private List<VideoModel> videoListFiltered;
    private VideosAdapterListener listener;

    //ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleView, durationView, errorTextView;
        ImageView imageView;
        ProgressBar progressBar;

        //constructor just bind all the view
        MyViewHolder(View itemView) {
            super(itemView);

            //Bind view
            titleView = itemView.findViewById(R.id.title);
            durationView = itemView.findViewById(R.id.duration);
            imageView = itemView.findViewById(R.id.image);
            progressBar = itemView.findViewById(R.id.progressBar);
            errorTextView = itemView.findViewById(R.id.errorTextView);

            //get the listener for the holder
            itemView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        listener.onVideoSelected(videoListFiltered.get(getAdapterPosition()));
                    }
                }
            );
        }
    }

    //Constructor of the adapter
    VideoAdapter(List<VideoModel> videoList, VideosAdapterListener listener){
        this.listener = listener;
        this.videoList = videoList;
        this.videoListFiltered = videoList;
    }


    //Call to create a viewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_cards, parent, false);
        return new MyViewHolder(itemView);
    }

    //initialise all the view of the holder
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final VideoModel video = videoListFiltered.get(position);
        holder.titleView.setText(video.getTitle());
        holder.durationView.setText(video.getStringDuration());

        holder.imageView.setVisibility(View.GONE);
        holder.progressBar.setVisibility(View.VISIBLE);


        //TODO: init image
        //holder.imageView.setImageResource(R.drawable.loading);


        new Thread(() -> {
            try {
                URL url = new URL(video.getImageUrl());
                final Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                new Handler(Looper.getMainLooper()).post(() -> {
                    holder.imageView.setImageBitmap(bmp);
                    holder.imageView.setVisibility(View.VISIBLE);
                    holder.progressBar.setVisibility(View.GONE);
                });
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    holder.progressBar.setVisibility(View.GONE);
                    holder.errorTextView.setText("Oops, preview couldn't load.");
                    holder.errorTextView.setVisibility(View.VISIBLE);
                });
                Log.e("VideoException", "ImageVideo couldn't load",e);
            }
        }).start();
    }

    @Override
    public int getItemCount() {
        return videoListFiltered.size();
    }

    //filter management
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    videoListFiltered = videoList;
                } else {
                    List<VideoModel> filteredList = new ArrayList<>();
                    for (VideoModel row : videoList) {

                        // match conditions
                        // if the title contains the charSequence
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    videoListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = videoListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                videoListFiltered = (ArrayList<VideoModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface VideosAdapterListener {
        void onVideoSelected(VideoModel videoModel);
    }
}
