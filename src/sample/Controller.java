package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import weatherStation.*;
import java.io.FileNotFoundException;



public class Controller {

    /**
     * Fx:ids used in coding the program
     * All belong to certain object in GUI
     */

    @FXML
    private TextField cityBox;

    @FXML
    private AnchorPane myPane;

    @FXML
    private TextField tempBox;

    @FXML
    private TextField humidBox;

    @FXML
    private TextField presBox;

    @FXML
    private TextField maxTempBox;

    @FXML
    private TextField meanPresBox;

    @FXML
    private TextField meanTempBox;

    @FXML
    private TextField meanHumidBox;

    @FXML
    private TextField meanMinTempBox;

    @FXML
    private TextField meanMaxTempBox;

    @FXML
    private Text statusTxt;

    @FXML
    private TextField minTempBox;

    @FXML
    private TextField presDevBox;

    @FXML
    private TextField devTempBox;

    @FXML
    private TextField devHumBox;

    @FXML
    private TextField minTempDevBox;

    @FXML
    private TextField maxTempDevBox;

    @FXML
    private LineChart<String,Double> tempChart;

    @FXML
    private LineChart<String,Double> humidChart;

    @FXML
    private LineChart<String,Double> presChart;

    @FXML
    private Button startBtn;

    @FXML
    private Button stopBtn;

    @FXML
    private Button interruptBtn;

    @FXML
    private Button resumeBtn;

    @FXML
    private Button readFromFileBtn;

    @FXML
    private Text measTxt;

    @FXML
    private TextField readNameBox;

    @FXML
    private Text readTxt;


    /**
     * ThreadClass Object creation - outside every method in order to make sure all calls are up to this particular one
     */

    ThreadClass tc = new ThreadClass();

    /**
     * The OnAction method for start button
     * Sets text on status of a program, and turns all unnecessary elements of GUI invisible
     * Reads the parameter given in City Text box, and passes it to all other parts of the program
     * Starts a data streaming thread going in the background and adds an observer which will be essential to update GUI in real time
     * @param event
     */

    @FXML
    public void useWeather(ActionEvent event){
        statusTxt.setText("Ongoing");
        readFromFileBtn.setVisible(false);
        resumeBtn.setVisible(false);
        readNameBox.setVisible(false);
        tc.setMiasto(cityBox.getText());
        tc.ws = new WeatherStation(tc.getMiasto());
        BoxSetting bx = new BoxSetting(devTempBox,devHumBox,presDevBox,maxTempDevBox,minTempDevBox,measTxt,presChart,humidChart,tempChart,tempBox,humidBox,presBox,maxTempBox,minTempBox,meanTempBox,meanHumidBox,meanPresBox,meanMaxTempBox,meanMinTempBox,meanTempBox,meanHumidBox,meanPresBox);
        tc.addObserver(bx);
        tc.k.start();


    }

    /**
     * The OnAction method for pause button
     * Sets the running boolean value for backgorund thread as false
     * Changes the Status text
     * Suspends the thread, even though the "Suspend" function has been deprecated it still works for JDK 11
     * @param event
     */

    @FXML
    public void stopRightThere(ActionEvent event){
        resumeBtn.setVisible(true);
        statusTxt.setText("Paused");
        tc.k.suspend();

    }

    /**
     * The OnAction method for end button
     * Completely interrupts the background thread allowing other functions to tak place e.g. reading from file
     * Sets visible all elements that can be used to operate the program after stopping the thread
     * @param event
     */

    @FXML
    void endAsInterrupt(ActionEvent event) {
        readFromFileBtn.setVisible(true);
        readTxt.setVisible(true);
        readNameBox.setVisible(true);
        statusTxt.setText("Stopped");
        tc.setRunning(false);
        tc.k.interrupt();
        tc.toFile();

    }

    /**
     * The OnAction method for file reading button
     * Allows to read the file by putting the name of the file in which case is the name of the city
     * @param event
     * @throws FileNotFoundException
     */

    @FXML
    void readFromFile(ActionEvent event)throws FileNotFoundException {
        BoxSetting bx = new BoxSetting(devTempBox,devHumBox,presDevBox,maxTempDevBox,minTempDevBox,measTxt,presChart,humidChart,tempChart,tempBox,humidBox,presBox,maxTempBox,minTempBox,meanTempBox,meanHumidBox,meanPresBox,meanMaxTempBox,meanMinTempBox,meanTempBox,meanHumidBox,meanPresBox);
        cityBox.setText(readNameBox.getText());
        bx.fromFileDisp(tc.fromFile(readNameBox.getText()));
    }

    /**
     * The OnAction method for resume button
     * It allows to resume the background thread after it has been suspended
     * Even though the "resume" function has been deprecated it still works for JDK 11
     * @param event
     */

    @FXML
    void resumeThread(ActionEvent event) {
        resumeBtn.setVisible(false);
        tc.setRunning(true);
        tc.k.resume();

    }

}
