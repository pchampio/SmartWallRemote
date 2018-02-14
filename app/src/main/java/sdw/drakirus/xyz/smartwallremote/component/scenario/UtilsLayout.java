package sdw.drakirus.xyz.smartwallremote.component.scenario;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import sdw.drakirus.xyz.smartwallremote.json.GrpScreen;
import sdw.drakirus.xyz.smartwallremote.json.Layout;
import sdw.drakirus.xyz.smartwallremote.json.Screen;

/**
 * Created by Hactogeek on 14/02/2018.
 */

public class UtilsLayout {
    public static void makeBitmap(Layout layout){

        System.out.println(layout.getGrpScreen());


        Bitmap scenarioImage = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(scenarioImage);

        int width = 500;
        int height = 500;
        int widthScreen = (width - layout.getCols()) / layout.getCols();
        int heightScreen = (height - layout.getRows()) / layout.getRows();
        Paint paintBackground = new Paint();
        paintBackground.setColor(Color.WHITE);
        canvas.drawRect(0F, 0F, (float) width, (float) height, paintBackground);

        Paint paint = new Paint();

        for (GrpScreen grpScreen : layout.getGrpScreen()){
            paint.setColor(grpScreen.getColor());
            paint.setAlpha(210);

            for (Screen screen : grpScreen.getListScreen()){
                canvas.drawRect((float)(widthScreen * screen.getCol()),(float)(heightScreen * screen.getRow()), (float)(widthScreen * (screen.getCol()+1)), (float)(heightScreen * (screen.getRow()+1)), paint);
            }

        }

        layout.setBitmap(scenarioImage);

        return;
    }
}
