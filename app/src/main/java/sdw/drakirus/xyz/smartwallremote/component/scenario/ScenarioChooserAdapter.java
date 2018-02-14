package sdw.drakirus.xyz.smartwallremote.component.scenario;

/**
 * Created by drakirus (p.champion) on 26/01/18.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sdw.drakirus.xyz.smartwallremote.R;
import sdw.drakirus.xyz.smartwallremote.json.Layout;

public class ScenarioChooserAdapter extends BaseAdapter {

    private final List<Layout> scenarioDataList;
    private LayoutInflater layoutInflater;

    public ScenarioChooserAdapter(Context context, List<Layout> scenarioDataList) {
        layoutInflater = LayoutInflater.from(context);
        this.scenarioDataList = scenarioDataList;
    }

    @Override
    public int getCount() {
        return scenarioDataList.size();
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
        ScenarioViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_scenario, parent, false);
            viewHolder = new ScenarioViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ScenarioViewHolder) view.getTag();
        }

        int width = 100;
        int height = 50;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawRect(0F, 0F, (float) width, (float) height, paint);
        // data bind to viewHolder
//        viewHolder.imageView.setImageResource(R.drawable.ic_launcher_background);
        viewHolder.imageView.setImageBitmap(bitmap);
        viewHolder.textView.setText(scenarioDataList.get(position).getName());

        return view;
    }
}
