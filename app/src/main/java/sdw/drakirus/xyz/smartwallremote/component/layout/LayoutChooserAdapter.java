package sdw.drakirus.xyz.smartwallremote.component.layout;

/**
 * Created by drakirus (p.champion) on 26/01/18.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sdw.drakirus.xyz.smartwallremote.R;
import sdw.drakirus.xyz.smartwallremote.model.Layout;

public class LayoutChooserAdapter extends BaseAdapter {

    private final List<Layout> layoutList;
    private LayoutInflater layoutInflater;

    public LayoutChooserAdapter(Context context, List<Layout> dataList) {
        layoutInflater = LayoutInflater.from(context);
        this.layoutList = dataList;
    }

    @Override
    public int getCount() {
        return layoutList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_layout, parent, false);
            viewHolder = new LayoutViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (LayoutViewHolder) view.getTag();
        }

        Bitmap bitmap = UtilsLayout.makeBitmap(layoutList.get(position));

        // data bind to viewHolder
        // viewHolder.imageView.setImageResource(R.drawable.ic_launcher_background);
        viewHolder.imageView.setImageBitmap(bitmap);
        viewHolder.textView.setText(layoutList.get(position).getName());


        return view;
    }
}
