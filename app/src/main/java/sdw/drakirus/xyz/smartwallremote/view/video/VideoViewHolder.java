package sdw.drakirus.xyz.smartwallremote.view.video;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

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

//        imageView.setImageResource(videoItem.getImage());

        Bitmap bmp = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bmp);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(videoItem);
            }
        });
    }
}
