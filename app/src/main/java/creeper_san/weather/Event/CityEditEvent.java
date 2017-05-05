package creeper_san.weather.Event;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import creeper_san.weather.Item.CityItem;

import static creeper_san.weather.Event.CityEditEvent.*;

public class CityEditEvent {
    public final static int TYPE_ADD = 0;
    public final static int TYPE_DELETE = 1;
    public final static int TYPE_ORDER = 10;

    private final String from;
    private @CityEditType_ final int type;
    private final CityItem item;


    public CityEditEvent(String from, @CityEditType_ int type, CityItem item) {
        this.from = from;
        this.type = type;
        this.item = item;
    }

    public @CityEditType_ int getType() {
        return type;
    }

    public CityItem getItem() {
        return item;
    }

    public String getFrom() {
        return from;
    }

    @IntDef({TYPE_ADD,TYPE_DELETE,TYPE_ORDER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CityEditType_{};

}
