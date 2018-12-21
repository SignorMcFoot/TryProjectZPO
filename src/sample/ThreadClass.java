package sample;

import JsonCreate.AndJsonWasGood;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import weatherStation.WeatherStation;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the class running and operating the background thread, as well as handling the observers and reading from file
 */

public class ThreadClass implements Observable {


    private  volatile ArrayList<Observer>  observers = new ArrayList<>();
    protected volatile boolean isRunning;
    private String miasto;

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    AndJsonWasGood aj = new AndJsonWasGood();
    WeatherStation ws;

    /**
     * Thread definition instead of using Runnable it uses lambda which allows pretty much the same thing
     * The thread is tasked with getting response data from the openweathermap server
     * It also calls upon update method which adds new elements to the data collecting JsonArray
     * After the data has been streamed and saved the method calls upon updateObservers()
     */

    Thread k = new Thread(() -> {

        setRunning(true);
        while (isRunning()) {
            try {

                ws.summerInTheCity();
                aj.update(aj.jo(ws.getTemp(), ws.getHum(), ws.getPres(), ws.gettMax(), ws.gettMin()));
                System.out.println(aj.getJsonArray().toString());
                updateObservers();
                Thread.sleep(10000);


            } catch (InterruptedException e) {
            }
        }

    });

    /**
     * The method which allows to add observer which are called upon the ongoing of the Thread
     * If the Observer ArrayList already contains the observer it will not be added and notified twice
     * @param observer
     */

    @Override
    public void addObserver(Observer observer) {
        if(!observers.contains(observer))
            observers.add(observer);
    }

    /**
     * The method removing the observer Object from observers ArrayList
     * It will not be removed if there isn't any on the List
     * @param observer
     */

    @Override
    public void removeObserver(Observer observer) {
        if(observers.contains(observer))
            observers.remove(observer);
    }

    /**
     * The method updateObservers is called upon in the ongoing background thread
     * This method allows to update GUI
     */

    @Override
    public void updateObservers() {
        for(Observer o : observers){
            o.disp(getMiasto(),ws,aj.getJsonArray());
        }
    }

    /**
     * ToFile method is called upon only when the background thread has been manually interrupted
     * It allows to save the JsonArray created during the work of the program to .txt file which name will be the name of the city researched
     */

    public void toFile(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\uaxau\\Desktop\\"+miasto+".txt"));
            bw.write(String.valueOf(aj.getJsonArray()));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * FromFile method allowing to read a txt file that has been made earlier by this program if it exists
     * It returns a JsonArray that will be a parameter later on in displaying stored data methods
     * @param city
     * @return
     * @throws FileNotFoundException
     */

    public JsonArray fromFile(String city) throws FileNotFoundException {

        File f = new File("C:\\Users\\uaxau\\Desktop\\"+city+".txt");
        Scanner klav = new Scanner(f);
        String kek = klav.next();
        klav.close();
        JsonParser parser = new JsonParser();
        JsonArray jo = (JsonArray) parser.parse(String.valueOf(kek));
        return jo;
    }
}