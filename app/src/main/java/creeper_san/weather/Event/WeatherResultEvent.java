package creeper_san.weather.Event;

import creeper_san.weather.Json.WeatherJson;

public class WeatherResultEvent {
    private final boolean isSuccess;
    private final WeatherJson weatherJson;

    public WeatherResultEvent(boolean isSuccess, WeatherJson weatherJson) {
        this.isSuccess = isSuccess;
        this.weatherJson = weatherJson;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public WeatherJson getWeatherJson() {
        return weatherJson;
    }
}
