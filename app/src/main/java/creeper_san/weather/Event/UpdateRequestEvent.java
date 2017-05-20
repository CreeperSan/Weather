package creeper_san.weather.Event;



public class UpdateRequestEvent {
    public final static int TYPE_CHECK_UPDATE = 1;
    public final static int TYPE_CHECK_UPDATE_HISTORY = 2;

    private final int type;

    public UpdateRequestEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
