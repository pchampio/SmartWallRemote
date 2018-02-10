package sdw.drakirus.xyz.smartwallremote.view;

/**
 * Created by drakirus (p.champion) on 26/01/18.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import sdw.drakirus.xyz.smartwallremote.R;

public class SimpleAdapter extends BaseAdapter {

  private LayoutInflater layoutInflater;

  public SimpleAdapter(Context context) {
    layoutInflater = LayoutInflater.from(context);
  }

  @Override
  public int getCount() {
    return 6;
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
    ViewHolder viewHolder;
    View view = convertView;

    if (view == null) {
      view = layoutInflater.inflate(R.layout.list_scenario, parent, false);
      viewHolder = new ViewHolder();
      viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
      viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }

    Context context = parent.getContext();
    switch (position) {
      case 0:
        viewHolder.textView.setText(context.getString(R.string.google_plus_title));
        viewHolder.imageView.setImageResource(R.drawable.ic_launcher_background);
        break;
      case 1:
        viewHolder.textView.setText(context.getString(R.string.google_maps_title));
        viewHolder.imageView.setImageResource(R.drawable.ic_launcher_background);
        break;
      default:
        viewHolder.textView.setText(context.getString(R.string.google_messenger_title));
        viewHolder.imageView.setImageResource(R.drawable.ic_launcher_background);
        break;
    }

    return view;
  }

  static class ViewHolder {
    TextView textView;
    ImageView imageView;
  }
}
