package sdw.drakirus.xyz.smartwallremote.component.scenario;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by drakirus (p.champion) on 10/02/18.
 */

public class ScenarioData {
    private String scenarioName;
    private int scenarioNumber;
    private int screenRows;
    private int screenCols;
    private Bitmap scenarioImage;
    private ArrayList scenarioList = new ArrayList();

    public ScenarioData(String scenarioName, int scenarioNumber, int screenCols, int screenRows) {
        this.scenarioName = scenarioName;
        this.scenarioNumber = scenarioNumber;
        this.screenCols = screenCols;
        this.screenRows = screenRows;

        scenarioImage = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(scenarioImage);


        for(int i = 1 ; i <= scenarioNumber ; i++)
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
    }

    public String getScenarioName() { return scenarioName; }
    public void setScenarioName(String scenarioName) { this.scenarioName = scenarioName; }

    public int getScreenCols() { return screenCols; }
    public void setScreenCols(int screenCols) { this.screenCols = screenCols; }

    public int getScreenRows() { return screenRows; }
    public void setScreenRows(int screenRows) { this.screenRows = screenRows; }

    public Bitmap getScenarioImage() { return scenarioImage; }
    public void setScenarioImage(Bitmap scenarioImage) { this.scenarioImage = scenarioImage; }
}
