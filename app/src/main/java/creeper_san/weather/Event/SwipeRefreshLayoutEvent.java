package creeper_san.weather.Event;



public class SwipeRefreshLayoutEvent {
    private final boolean enable;

    public SwipeRefreshLayoutEvent(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }
}
