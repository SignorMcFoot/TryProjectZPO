package JsonCreate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the class with methods creating JsonObject from streamed data as well as ones putting it in a JsonArray
 */
public class AndJsonWasGood implements ResultList{

    private JsonArray jsonArray = new JsonArray();

    public JsonArray getJsonArray() {
        return jsonArray;
    }

    /**
     * This methods has parameters being: Temperature, humidity, pressure, max Temperature and min Temperature
     * It puts these parameters under corresponding names in a HashMap which is then converted to a JsonObject
     * It then returns the given JsonObject
      * @param t
     * @param h
     * @param p
     * @param tMax
     * @param tMin
     * @return
     */

    public JsonObject jo(double t, double h, double p, double tMax, double tMin){
        Gson gson = new Gson();
        Map<String,Double> weatherState = new HashMap<>();
        weatherState.put("Temperature",t);
        weatherState.put("Humidity",h);
        weatherState.put("Pressure",p);
        weatherState.put("MaxTemp",tMax);
        weatherState.put("MinTemp",tMin);
        JsonObject jo1 =  new JsonParser().parse(String.valueOf(weatherState)).getAsJsonObject();

        return jo1;
    }

    /**
     * A method overriden from ResultList interface
     * It adds the created JsonObject to JsonArray
     * @param json
     */

    @Override
    public void update(JsonObject json) {
        jsonArray.add(json);
    }
}
