package sdw.drakirus.xyz.smartwallremote.component.video;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView titleView, durationView;
        ImageView imageView;

        //constructor just bind all the view
        MyViewHolder(View itemView) {
            super(itemView);

            //Bind view
            titleView = itemView.findViewById(R.id.title);
            durationView = itemView.findViewById(R.id.duration);
            imageView = itemView.findViewById(R.id.image);


            //get the listener for the holder
            itemView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        listener.onContactSelected(videoListFiltered.get(getAdapterPosition()));
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
                .inflate(R.layout.video_cards_new, parent, false);
        return new MyViewHolder(itemView);
    }

    //initialise all the view of the holder
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final VideoModel video = videoListFiltered.get(position);
        holder.titleView.setText(video.getTitle());
        holder.durationView.setText(video.getStringDuration());

        //TODO: init image
        holder.imageView.setImageResource(video.getImage());
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
        void onContactSelected(VideoModel videoModel);
    }
}
