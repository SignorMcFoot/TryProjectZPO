package weatherStation;



public class WeatherStation extends UnderTheWeather{
    private String miasto;

    public WeatherStation(String miasto) {
        super(miasto);
        this.miasto = miasto;

    }
    public void weather(){
        UnderTheWeather utw = new UnderTheWeather(miasto);
        utw.summerInTheCity();
    }
}