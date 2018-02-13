package sdw.drakirus.xyz.smartwallremote.component.video;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sdw.drakirus.xyz.smartwallremote.R;

/**
 * Created by drakirus (p.champion) on 28/01/18.
 */

public class VideoChooserAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    List<VideoData> list;
    private final OnItemClickListener listener;

    //ajouter un constructeur prenant en entrée une liste
    public VideoChooserAdapter(List<VideoData> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(VideoData item);
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_cards,viewGroup,false);
        return new VideoViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(VideoViewHolder myViewHolder, int position) {
        VideoData videoData = list.get(position);
        myViewHolder.bind(videoData, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}


