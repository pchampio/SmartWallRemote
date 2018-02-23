package sdw.drakirus.xyz.smartwallremote.component.layout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import sdw.drakirus.xyz.smartwallremote.model.GrpScreen;
import sdw.drakirus.xyz.smartwallremote.model.Layout;
import sdw.drakirus.xyz.smartwallremote.model.Screen;

/**
 * Created by Hactogeek on 14/02/2018.
 */

public class UtilsLayout {
    public static Bitmap makeBitmap(Layout layout){

        int widthTotal = 125 * layout.getCols();
        int heightTotal = 75 * layout.getRows();

        Bitmap scenarioImage = Bitmap.createBitmap(widthTotal, heightTotal, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(scenarioImage);

        int width = widthTotal - 5;
        int height = heightTotal - 5;
        int widthScreen = (width - layout.getCols()) / layout.getCols();
        int heightScreen = (height - layout.getRows()) / layout.getRows();
        Paint paintBackground = new Paint();
        paintBackground.setColor(Color.parseColor("#d6d6d4"));
        canvas.drawRect(0F, 0F, (float) width, (float) height, paintBackground);

        Paint paint = new Paint();

        for (GrpScreen grpScreen : layout.getGrpScreen()){
            paint.setColor(grpScreen.getColor());
            paint.setAlpha(210);

            for (Screen screen : grpScreen.getListScreen()){
                canvas.drawRect((float)(widthScreen * screen.getCol()+5),(float)(heightScreen * screen.getRow()+5), (float)(widthScreen * (screen.getCol()+1)), (float)(heightScreen * (screen.getRow()+1)), paint);
            }

        }

        return scenarioImage;
    }
}
