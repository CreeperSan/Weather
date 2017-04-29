package creeper_san.weather.Event;

public class ErrorCodeEvent {
    private final String code;//
    private final String extra;

    public ErrorCodeEvent(String code, String extra) {
        this.code = code;
        this.extra = extra;
    }

    public String getCode() {
        return code;
    }

    public String getExtra() {
        return extra;
    }
}
