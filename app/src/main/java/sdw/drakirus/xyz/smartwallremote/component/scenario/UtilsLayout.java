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


        for(int i = 1 ; i <= 2 ; i++)
        {

        }

        int widthRed = 500;
        int heightRed = 500;
        Paint paintRed = new Paint();
        paintRed.setColor(Color.RED);
        canvas.drawRect(0F, 0F, (float) widthRed, (float) heightRed, paintRed);

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
