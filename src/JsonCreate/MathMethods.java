package JsonCreate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * This is a class that contains methods calculating the mean values and standard deviation for used parameters
 */

public class MathMethods {

    /**
     * The method counting mean values of every parameters
     * Returns a double[] containing calculated values, and is slightly unhandy because of necessity to memorize which position in it corresponds to wanted parameter
     * @param ja
     * @return
     */

    public double[] mean(JsonArray ja){
        double[] meanAr = new double[5];
        double tMean = 0;
        double hMean = 0;
        double pMean = 0;
        double maxMean = 0;
        double minMean = 0;
        for(int i = 0; i < ja.size(); i++){
            JsonElement k = ja.get(i);
            tMean += k.getAsJsonObject().get("Temperature").getAsDouble();
            hMean += k.getAsJsonObject().get("Humidity").getAsDouble();
            pMean += k.getAsJsonObject().get("Pressure").getAsDouble();
            maxMean += k.getAsJsonObject().get("MaxTemp").getAsDouble();
            minMean += k.getAsJsonObject().get("MinTemp").getAsDouble();
        }
        meanAr[0] = tMean/ja.size();
        meanAr[1] = hMean/ja.size();
        meanAr[2] = pMean/ja.size();
        meanAr[3] = maxMean/ja.size();
        meanAr[4] = minMean/ja.size();
        return meanAr;
    }


    /**
     * The method to calculate the standard deviation for given parameters
     * It calls upon the mean calculating method in order to get the values from it instead of counting it on its own
     * Returns a double[] containing calculated values, and is slightly unhandy because of necessity to memorize which position in it corresponds to wanted parameter
     * @param ja
     */

    public double[] deviation(JsonArray ja){
        double[] deviAr = new double[5];
        double[] meanAr = mean(ja);
        double devTemp =0;
        double devHum = 0;
        double devPres = 0;
        double devMax = 0;
        double devMin =0;
        for(int i = 0; i<ja.size();i++){
            JsonElement k = ja.get(i);
            devTemp += Math.pow((meanAr[0]-k.getAsJsonObject().get("Temperature").getAsDouble()),2);
            devHum += Math.pow((meanAr[1]-k.getAsJsonObject().get("Humidity").getAsDouble()),2);
            devPres += Math.pow((meanAr[2]-k.getAsJsonObject().get("Pressure").getAsDouble()),2);
            devMax += Math.pow((meanAr[3]-k.getAsJsonObject().get("MaxTemp").getAsDouble()),2);
            devMin += Math.pow((meanAr[4]-k.getAsJsonObject().get("MinTemp").getAsDouble()),2);
        }
        deviAr[0] = Math.sqrt(devTemp)/ja.size();
        deviAr[1] = Math.sqrt(devHum)/ja.size();
        deviAr[2] = Math.sqrt(devPres)/ja.size();
        deviAr[3] = Math.sqrt(devMax)/ja.size();
        deviAr[4] = Math.sqrt(devMin)/ja.size();

        return deviAr;
    }
}
