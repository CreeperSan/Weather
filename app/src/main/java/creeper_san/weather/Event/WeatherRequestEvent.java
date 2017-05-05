package creeper_san.weather.Event;



public class WeatherRequestEvent {

    private final String cityID;
    private final String cityName;

    public WeatherRequestEvent(String cityID, String cityName) {
        this.cityID = cityID;
        this.cityName = cityName;
    }

    public String getCityID() {
        return cityID;
    }

    public String getCityName() {
        return cityName;
    }
}
