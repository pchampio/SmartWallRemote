package sdw.drakirus.xyz.smartwallremote.component.scenario;

import android.graphics.Bitmap;

/**
 * Created by drakirus (p.champion) on 10/02/18.
 */

public class ScenarioData {
    private String scenarioName;
    private int scenarioRows;
    private int scenarioCols;
    private Bitmap scenarioImage;

    public ScenarioData(String scenarioName, int scenarioCols, int scenarioRows) {
        this.scenarioName = scenarioName;
        this.scenarioCols = scenarioCols;
        this.scenarioRows = scenarioRows;
    }

    public String getScenarioName() { return scenarioName; }
    public void setScenarioName(String scenarioName) { this.scenarioName = scenarioName; }

    public int getScenarioCols() { return scenarioCols; }
    public void setScenarioCols(int scenarioCols) { this.scenarioCols = scenarioCols; }

    public int getScenarioRows() { return scenarioRows; }
    public void setScenarioRows(int scenarioRows) { this.scenarioRows = scenarioRows; }

    public Bitmap getScenarioImage() { return scenarioImage; }
    public void setScenarioImage(Bitmap scenarioImage) { this.scenarioImage = scenarioImage; }
}
