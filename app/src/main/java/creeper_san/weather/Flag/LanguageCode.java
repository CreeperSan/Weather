package creeper_san.weather.Flag;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LanguageCode {
    public static final String CHINESE = "zh";
    public static final String ENGLISH = "en";

    @StringDef({CHINESE,ENGLISH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Language_ {}
}
