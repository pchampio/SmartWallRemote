package sdw.drakirus.xyz.smartwallremote.view.scenario;

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

import java.util.List;

import sdw.drakirus.xyz.smartwallremote.R;

public class ScenarioChooserAdapter extends BaseAdapter {

    private final List<ScenarioData> scenarioDataList;
    private LayoutInflater layoutInflater;

    public ScenarioChooserAdapter(Context context, List<ScenarioData> scenarioDataList) {
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
        Context context = parent.getContext();


        // data bind to viewHolder
        viewHolder.imageView.setImageResource(R.drawable.ic_launcher_background);
        viewHolder.textView.setText(scenarioDataList.get(position).getScenarioName());

        return view;
    }
}
