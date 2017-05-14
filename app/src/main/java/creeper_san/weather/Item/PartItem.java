package creeper_san.weather.Item;

import android.content.Context;
import static creeper_san.weather.Helper.ResHelper.*;

public class PartItem {
    private int type;
    private String name;
    private String timeStamp;

    public PartItem (Context context,int type){
        this.type = type;
        name = getPartNameFromCode(context,type);
    }

    public PartItem(Context context,int type, String timeStamp) {
        this(context,type);
        this.timeStamp = timeStamp;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
