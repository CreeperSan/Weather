package creeper_san.weather.Event;

import creeper_san.weather.Json.WeatherItem;

public class WeatherResultEvent {
    private final boolean isSuccess;
    private final WeatherItem weatherItem;

    public WeatherResultEvent(boolean isSuccess, WeatherItem weatherItem) {
        this.isSuccess = isSuccess;
        this.weatherItem = weatherItem;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public WeatherItem getWeatherItem() {
        return weatherItem;
    }
}
