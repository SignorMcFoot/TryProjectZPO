package sample;

import JsonCreate.MathMethods;
import com.google.gson.JsonArray;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.decimal4j.util.DoubleRounder;
import weatherStation.WeatherStation;

/**
 * The class which allows on overwriting and changing current values given at GUI
 */

public class BoxSetting implements Observer {
    private TextField tempBox;
    private TextField humidBox;
    private TextField presBox;
    private TextField maxTempBox;
    private TextField minTempBox;
    private TextField meanTemp;
    private TextField meanHum;
    private TextField meanPres;
    private TextField meanMaxTempBox;
    private TextField meanMinTempBox;
    private TextField meanTempBox;
    private TextField meanHumidBox;
    private TextField meanPresBox;
    private TextField devTemp;
    private TextField devHum;
    private TextField devPres;
    private TextField devMax;
    private TextField devMin;
    private LineChart<String, Double> tempChart;
    private LineChart<String, Double> humChart;
    private LineChart<String, Double> presChart;
    private Text measTxt;
    private XYChart.Series<String, Double> series = new XYChart.Series<>();
    private XYChart.Series<String, Double> seriesHum = new XYChart.Series<>();
    private XYChart.Series<String, Double> seriesPres = new XYChart.Series<>();



    public BoxSetting(TextField devTemp, TextField devHum, TextField devPres, TextField devMax, TextField devMin, Text measTxt, LineChart<String, Double> presChart, LineChart<String, Double> humChart, LineChart<String, Double> tempChart, TextField tempBox, TextField humidBox, TextField presBox, TextField maxTempBox, TextField minTempBox, TextField meanTemp, TextField meanHum, TextField meanPres, TextField meanMaxTempBox, TextField meanMinTempBox, TextField meanTempBox, TextField meanHumidBox, TextField meanPresBox) {
        this.tempBox = tempBox;
        this.humidBox = humidBox;
        this.presBox = presBox;
        this.maxTempBox = maxTempBox;
        this.minTempBox = minTempBox;
        this.meanTemp = meanTemp;
        this.meanHum = meanHum;
        this.meanPres = meanPres;
        this.meanMaxTempBox = meanMaxTempBox;
        this.meanMinTempBox = meanMinTempBox;
        this.meanTempBox = meanTempBox;
        this.meanHumidBox = meanHumidBox;
        this.meanPresBox = meanPresBox;
        this.measTxt = measTxt;
        this.tempChart = tempChart;
        this.presChart = presChart;
        this.humChart = humChart;
        this.devTemp = devTemp;
        this.devHum = devHum;
        this.devPres = devPres;
        this.devMax = devMax;
        this.devMin = devMin;

    }

    /**
     * The updateValues methods directly corresponds to the elements of GUI and sets them with a proper value
     * It uses Platform.runLater function which is used to make sure the only thread having access to GUI is the JavaFX thread
     * It checks if the parameters of the JsonArray aren't nullable which could interrupt the fluent work of plots
     * @param miasto
     * @param ws
     * @param aj1
     */
    @Override
    public void updateValues(String miasto, WeatherStation ws, JsonArray aj1) {
        MathMethods mm = new MathMethods();
        Platform.runLater(() -> {
            int number = aj1.size() - 1;
            int showOff = number+1;
            tempBox.setText(Double.toString(aj1.get(number).getAsJsonObject().get("Temperature").getAsDouble())+"°C");
            humidBox.setText(Double.toString(aj1.get(number).getAsJsonObject().get("Humidity").getAsDouble())+"%");
            presBox.setText(Double.toString(aj1.get(number).getAsJsonObject().get("Pressure").getAsDouble())+" hPa");
            minTempBox.setText(Double.toString(aj1.get(number).getAsJsonObject().get("MinTemp").getAsDouble())+"°C");
            maxTempBox.setText(Double.toString(aj1.get(number).getAsJsonObject().get("MaxTemp").getAsDouble())+"°C");
            meanMaxTempBox.setText(Double.toString(DoubleRounder.round(mm.mean(aj1)[3], 2))+"°C");
            meanMinTempBox.setText(Double.toString(DoubleRounder.round(mm.mean(aj1)[4], 2))+"°C");
            meanTempBox.setText(Double.toString(DoubleRounder.round(mm.mean(aj1)[0], 2))+"°C");
            meanHumidBox.setText(Double.toString(DoubleRounder.round(mm.mean(aj1)[1], 2))+"%");
            meanPresBox.setText(Double.toString(DoubleRounder.round(mm.mean(aj1)[2], 2))+" hPa");
            devTemp.setText(Double.toString(DoubleRounder.round(mm.deviation(aj1)[0], 2))+"°C");
            devHum.setText(Double.toString(DoubleRounder.round(mm.deviation(aj1)[1], 2))+"%");
            devPres.setText(Double.toString(DoubleRounder.round(mm.deviation(aj1)[2], 2))+" hPa");
            devMax.setText(Double.toString(DoubleRounder.round(mm.deviation(aj1)[3], 2))+"°C");
            devMin.setText(Double.toString(DoubleRounder.round(mm.deviation(aj1)[4], 2))+"°C");
            measTxt.setText(Integer.toString(showOff));
            if (aj1.get(number).getAsJsonObject() != null) {
                series.getData().add(new XYChart.Data<>(String.valueOf(number), aj1.get(number).getAsJsonObject().get("Temperature").getAsDouble()));
                seriesHum.getData().add(new XYChart.Data<>(String.valueOf(number), aj1.get(number).getAsJsonObject().get("Humidity").getAsDouble()));
                seriesPres.getData().add(new XYChart.Data<>(String.valueOf(number), aj1.get(number).getAsJsonObject().get("Pressure").getAsDouble()));
                tempChart.setAnimated(false);
                humChart.setAnimated(false);
                presChart.setAnimated(false);
                tempChart.getData().clear();
                humChart.getData().clear();
                presChart.getData().clear();
                tempChart.getData().addAll(series);
                humChart.getData().addAll(seriesHum);
                presChart.getData().addAll(seriesPres);


            }
        });
    }

    /**
     * An overriden method from Observer interface
     * It is executed while the observer is notified with updateObservers() method
     * @param miasto
     * @param ws
     * @param aj1
     */

    @Override
    public void disp(String miasto, WeatherStation ws, JsonArray aj1) {
        updateValues(miasto, ws, aj1);
        int number = aj1.size() - 1;
        measTxt.setText(Integer.toString((number + 1)));

    }

    /**
     * A method very similar to updateValues method, but changed in order to handle the data read from txt file
     * The most major difference is usage of for loop in order to create the chart series
     * @param aj1
     */


    public void fromFileDisp(JsonArray aj1) {
        MathMethods mm = new MathMethods();
        for (int i = 0; i < aj1.size(); i++) {
            if (aj1.get(i).getAsJsonObject() != null) {
                series.getData().add(new XYChart.Data<>(String.valueOf(i), aj1.get(i).getAsJsonObject().get("Temperature").getAsDouble()));
                seriesHum.getData().add(new XYChart.Data<>(String.valueOf(i), aj1.get(i).getAsJsonObject().get("Humidity").getAsDouble()));
                seriesPres.getData().add(new XYChart.Data<>(String.valueOf(i), aj1.get(i).getAsJsonObject().get("Pressure").getAsDouble()));
            }

            Platform.runLater(() -> {
                int number = aj1.size()-1;
                int showOff = number+1;
                tempBox.setText(Double.toString(aj1.get(number).getAsJsonObject().get("Temperature").getAsDouble())+"°C");
                humidBox.setText(Double.toString(aj1.get(number).getAsJsonObject().get("Humidity").getAsDouble())+"%");
                presBox.setText(Double.toString(aj1.get(number).getAsJsonObject().get("Pressure").getAsDouble())+" hPa");
                minTempBox.setText(Double.toString(aj1.get(number).getAsJsonObject().get("MinTemp").getAsDouble())+"°C");
                maxTempBox.setText(Double.toString(aj1.get(number).getAsJsonObject().get("MaxTemp").getAsDouble())+"°C");
                meanMaxTempBox.setText(Double.toString(DoubleRounder.round(mm.mean(aj1)[3], 2))+"°C");
                meanMinTempBox.setText(Double.toString(DoubleRounder.round(mm.mean(aj1)[4], 2))+"°C");
                meanTempBox.setText(Double.toString(DoubleRounder.round(mm.mean(aj1)[0], 2))+"°C");
                meanHumidBox.setText(Double.toString(DoubleRounder.round(mm.mean(aj1)[1], 2))+"%");
                meanPresBox.setText(Double.toString(DoubleRounder.round(mm.mean(aj1)[2], 2))+" hPa");
                devTemp.setText(Double.toString(DoubleRounder.round(mm.deviation(aj1)[0], 2))+"°C");
                devHum.setText(Double.toString(DoubleRounder.round(mm.deviation(aj1)[1], 2))+"%");
                devPres.setText(Double.toString(DoubleRounder.round(mm.deviation(aj1)[2], 2))+" hPa");
                devMax.setText(Double.toString(DoubleRounder.round(mm.deviation(aj1)[3], 2))+"°C");
                devMin.setText(Double.toString(DoubleRounder.round(mm.deviation(aj1)[4], 2))+"°C");
                measTxt.setText("Number of measurements in file: " + Integer.toString(showOff));

                tempChart.setAnimated(false);
                humChart.setAnimated(false);
                presChart.setAnimated(false);
                tempChart.getData().clear();
                humChart.getData().clear();
                presChart.getData().clear();
                tempChart.getData().addAll(series);
                humChart.getData().addAll(seriesHum);
                presChart.getData().addAll(seriesPres);


            });
        }
    }
}
