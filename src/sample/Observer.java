package sample;

import com.google.gson.JsonArray;
import weatherStation.WeatherStation;

/**
 * An interface which has methods for displaying the values on GUI
 */

public interface Observer {
    void updateValues(String miasto, WeatherStation ws, JsonArray aj1);
    void disp(String miasto, WeatherStation ws, JsonArray aj1);
}
