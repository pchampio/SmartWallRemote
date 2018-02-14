package sdw.drakirus.xyz.smartwallremote.component.scenario;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import sdw.drakirus.xyz.smartwallremote.json.Layout;

/**
 * Created by Hactogeek on 14/02/2018.
 */

public class UtilsLayout {
    public static void makeBitmap(Layout layout){

        System.out.println(layout.getGrpScreen());


        Bitmap scenarioImage = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(scenarioImage);

        int widthRed = layout.getWidth();
        int heightRed = layout.getHeight();
        Paint paintRed = new Paint();
        paintRed.setColor(Color.RED);
        canvas.drawRect(0F, 0F, (float) widthRed, (float) heightRed, paintRed);


        for(int i = 1 ; i <= layout.getGrpScreen().size() ; i++)
        {

        }

        int widthBlue = 100;
        int heightBlue = 100;
        Paint paintBlue = new Paint();
        paintBlue.setColor(Color.BLUE);
        canvas.drawRect(0F, 0F, (float) widthBlue, (float) heightBlue, paintBlue);

        canvas.drawRect(500F-100F, 0F, 500F, 100F, paintBlue);

        layout.setBitmap(scenarioImage);

        return;
    }
}
