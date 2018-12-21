package weatherStation;

import com.google.gson.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The class getting the response from the server
 */

public class UnderTheWeather {
    private String miasto;
    private double hum;
    private  double temp;
    private double pres;
    private double tMax;
    private double tMin;

    public double getHum() {
        return hum;
    }

    public double getTemp() {
        return temp;
    }

    public double getPres() {
        return pres;
    }

    public UnderTheWeather(String miasto) {
        this.miasto = miasto;
    }

    public double gettMax() {
        return tMax;
    }

    public double gettMin() {
        return tMin;
    }

    /**
     * Method summerInTheCity() takes a parameter of miasto and adds it to correct URL
     * It then tries to resolve the URL and GET the information given is server response
     * Then it creates an Object of CurrentWeather class which allows to store data in a more comfortable way
     */

    public void summerInTheCity(){
        StringBuffer response = new StringBuffer();
        String miasto1 = miasto.replace(" ","+");
        String id = "fd0035196b173478c8cbe4ed29600a4e";
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+miasto1+"&units=metric&APPID="+id;
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("Weather for: " + miasto);
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(String.valueOf(response)).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Object main = json.getAsJsonObject("main");
        CurrentWeather e3 = gson.fromJson((JsonElement) main,CurrentWeather.class);
        hum = e3.getHumidity();
        temp = e3.getTemp();
        pres = e3.getPressure();
        tMax = e3.getTemp_max();
        tMin = e3.getTemp_min();
        System.out.println(e3);

    }
}

