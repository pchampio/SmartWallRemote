package sdw.drakirus.xyz.smartwallremote.view;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sdw.drakirus.xyz.smartwallremote.R;

/**
 * Created by drakirus (p.champion) on 28/01/18.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder{

    private TextView durationView;
    private TextView textViewView;
    private ImageView imageView;
    private LinearLayout layout;

    //itemView est la vue correspondante à 1 cellule
    public VideoViewHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView
        durationView = (TextView) itemView.findViewById(R.id.duration);
        textViewView = (TextView) itemView.findViewById(R.id.text);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        layout = (LinearLayout) itemView.findViewById(R.id.layout);

    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un Video
    public void bind(final VideoData videoItem, final VideoChooserAdapter.OnItemClickListener listener){
        textViewView.setText(videoItem.getText());
        durationView.setText(videoItem.getDuration());
        //imageView.setImageURI(Uri.parse(videoItem.getUrl()));

        layout.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(videoItem);
            }
        });
    }
}
