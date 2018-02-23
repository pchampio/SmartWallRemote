package sdw.drakirus.xyz.smartwallremote.component.scenario;

/**
 * Created by drakirus (p.champion) on 26/01/18.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sdw.drakirus.xyz.smartwallremote.R;
import sdw.drakirus.xyz.smartwallremote.model.Scenario;

public class ScenarioChooserAdapter extends BaseAdapter {

    private final List<Scenario> scenarioList;
    private LayoutInflater layoutInflater;

    public ScenarioChooserAdapter(Context context, List<Scenario> dataList) {
        layoutInflater = LayoutInflater.from(context);
        this.scenarioList = dataList;
    }

    @Override
    public int getCount() {
        return scenarioList.size();
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
            viewHolder.textView = (TextView) view.findViewById(R.id.title);
            viewHolder.textView2 = (TextView) view.findViewById(R.id.videoList);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ScenarioViewHolder) view.getTag();
        }

        Bitmap bitmap = UtilsScenario.makeBitmap(scenarioList.get(position).getLayout());

        // data bind to viewHolder
        // viewHolder.imageView.setImageResource(R.drawable.ic_launcher_background);
        viewHolder.imageView.setImageBitmap(bitmap);
        viewHolder.textView.setText(scenarioList.get(position).getName());

        for ( int i  = 0 ; i < scenarioList.get(position).getLayout().getGrpScreen().size() ; i++){
            try {
                viewHolder.textView2.append(Html.fromHtml("<font color=" + scenarioList.get(position).getLayout().getGrpScreen().get(i).getHexColor() + ">" + scenarioList.get(position).getVideo().get(i).getTitle() + "</font><br>"));
            } catch (Exception e) {
                System.out.print(e);
            }
        }


        return view;
    }
}
