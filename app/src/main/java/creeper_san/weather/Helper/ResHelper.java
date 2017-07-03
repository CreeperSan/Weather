package creeper_san.weather.Helper;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;

import creeper_san.weather.R;

public class ResHelper {

    public static int getStringIDFromWeatherCode(String code){
        switch (code){
            case "100": return R.string.weatherCode100Sunny;
            case "101": return R.string.weatherCode101Cloudy;
            case "102": return R.string.weatherCode102FewClouds;
            case "103": return R.string.weatherCode103PartlyClouds;
            case "104": return R.string.weatherCode104Overcast;
            case "200": return R.string.weatherCode200Windy;
            case "201": return R.string.weatherCode201Calm;
            case "202": return R.string.weatherCode202LightBreeze;
            case "203": return R.string.weatherCode203GentleBreeze;
            case "204": return R.string.weatherCode204FreshBreeze;
            case "205": return R.string.weatherCode205StrongBreeze;
            case "206": return R.string.weatherCode206NearGale;
            case "207": return R.string.weatherCode207Gale;
            case "208": return R.string.weatherCode208StrongGale;
            case "209": return R.string.weatherCode209Storm;
            case "210": return R.string.weatherCode210ViolentStorm;
            case "211": return R.string.weatherCode211Hurricane;
            case "212": return R.string.weatherCode212Tornado;
            case "213": return R.string.weatherCode213TropicalStorm;
            case "300": return R.string.weatherCode300ShowerRain;
            case "301": return R.string.weatherCode301HeavyShowerRain;
            case "302": return R.string.weatherCode302ThunderShower;
            case "303": return R.string.weatherCode303HeavyThunderstorm;
            case "304": return R.string.weatherCode304Hail;
            case "305": return R.string.weatherCode305LightRain;
            case "306": return R.string.weatherCode306ModerateRain;
            case "307": return R.string.weatherCode307HeavyRain;
            case "308": return R.string.weatherCode308ExtremeRain;
            case "309": return R.string.weatherCode309DrizzleRain;
            case "310": return R.string.weatherCode310Storm;
            case "311": return R.string.weatherCode311HeavyStorm;
            case "312": return R.string.weatherCode312SevereStorm;
            case "313": return R.string.weatherCode313FreezingRain;
            case "400": return R.string.weatherCode400LightSnow;
            case "401": return R.string.weatherCode401ModerateSnow;
            case "402": return R.string.weatherCode402HeavySnow;
            case "403": return R.string.weatherCode403Snowstorm;
            case "404": return R.string.weatherCode404Sleet;
            case "405": return R.string.weatherCode405RainAndSnow;
            case "406": return R.string.weatherCode406ShowerShow;
            case "407": return R.string.weatherCode407SnowFlurry;
            case "500": return R.string.weatherCode500Mist;
            case "501": return R.string.weatherCode501Foggy;
            case "502": return R.string.weatherCode502Haze;
            case "503": return R.string.weatherCode503Sand;
            case "504": return R.string.weatherCode504Dust;
            case "507": return R.string.weatherCode507DustStorm;
            case "508": return R.string.weatherCode508SandStorm;
            case "900": return R.string.weatherCode900Hot;
            case "901": return R.string.weatherCode901Cold;
            default:return R.string.weatherCode999Unknown;
        }
    }
    public static int getWeatherImageWeatherCode(String code){
        if (code.equals("") || code==null){
            return R.drawable.ic_unknown;
        }
        switch (code){
            case "100": return R.drawable.ic_sunny;
            case "101": return R.drawable.ic_cloudy;
            case "102": return R.drawable.ic_few_clouds;
            case "103": return R.drawable.ic_partly_cloudy;
            case "104": return R.drawable.ic_overcast;
            case "200": return R.drawable.ic_windy;
            case "201": return R.drawable.ic_calm;
            case "202": return R.drawable.ic_light_breeze;
            case "203": return R.drawable.ic_gentle_breeze;
            case "204": return R.drawable.ic_fresh_breeze;
            case "205": return R.drawable.ic_strong_breeze;
            case "206": return R.drawable.ic_near_gale;
            case "207": return R.drawable.ic_gale;
            case "208": return R.drawable.ic_strong_gale;
            case "209": return R.drawable.ic_storm_wind;
            case "210": return R.drawable.ic_violent_storm;
            case "211": return R.drawable.ic_hurricane;
            case "212": return R.drawable.ic_tornado;
            case "213": return R.drawable.ic_tropical_storm;
            case "300": return R.drawable.ic_shower_rain;
            case "301": return R.drawable.ic_heavy_shower_rain;
            case "302": return R.drawable.ic_thunder_shower;
            case "303": return R.drawable.ic_heavy_thunder_storm;
            case "304": return R.drawable.ic_hail;
            case "305": return R.drawable.ic_light_rain;
            case "306": return R.drawable.ic_moderate_rain;
            case "307": return R.drawable.ic_heavy_rain;
            case "308": return R.drawable.ic_extreme_rain;
            case "309": return R.drawable.ic_drizzle_rain;
            case "310": return R.drawable.ic_storm;
            case "311": return R.drawable.ic_heavy_storm;
            case "312": return R.drawable.ic_severe_storm;
            case "313": return R.drawable.ic_freezing_rain;
            case "400": return R.drawable.ic_light_snow;
            case "401": return R.drawable.ic_moderate_snow;
            case "402": return R.drawable.ic_heavy_snow;
            case "403": return R.drawable.ic_snow_storm;
            case "404": return R.drawable.ic_sleet;
            case "405": return R.drawable.ic_rain_and_snow;
            case "406": return R.drawable.ic_shower_snow;
            case "407": return R.drawable.ic_snow_flurry;
            case "500": return R.drawable.ic_mist;
            case "501": return R.drawable.ic_foggy;
            case "502": return R.drawable.ic_haze;
            case "503": return R.drawable.ic_sand;
            case "504": return R.drawable.ic_dust;
            case "507": return R.drawable.ic_duststorm;
            case "508": return R.drawable.ic_sand_storm;
            case "900": return R.drawable.ic_hot;
            case "901": return R.drawable.ic_cold;
            default:return R.drawable.ic_unknown;
        }
    }

    public static String getPartNameFromCode(Context context,int code){
        switch (code){
            case -1:return context.getString(R.string.partNameBase);
            case 0:return context.getString(R.string.partNameHeader);
            case 1:return context.getString(R.string.partNameAQI);
            case 2:return context.getString(R.string.partNameDaily);
            case 3:return context.getString(R.string.partNameWind);
            case 4:return context.getString(R.string.partNameSuggestion);
            case 5:return context.getString(R.string.partNameCity);
            case 6:return context.getString(R.string.partNameOther);
            case 7:return context.getString(R.string.partNameBackground);
            default:return context.getString(R.string.unknown);
        }
    }

    public static @ColorInt int getColorFromWeatherCode(String code){

        switch (code){
            case "100":
                return Color.parseColor("#63a1d7");
            case "101":
            case "102":
            case "103":
            case "104":
                return Color.parseColor("#6f8fc0");
            case "200":
            case "201":
            case "202":
            case "203":
            case "204":
            case "205":
                return Color.parseColor("#E7E7E7");
            case "206":
            case "207":
            case "208":
                return Color.parseColor("#dbdbdb");
            case "209":
            case "210":
            case "211":
            case "212":
            case "213":
                return Color.parseColor("#c0c0c0");
            case "300":
            case "301":
            case "302":
            case "303":
            case "304":
            case "305":
            case "306":
            case "307":
            case "308":
            case "309":
            case "310":
            case "311":
            case "312":
            case "313":
                return Color.parseColor("#9db4c5");
            case "400":
            case "401":
            case "402":
            case "403":
            case "404":
            case "405":
            case "406":
            case "407":
                return Color.parseColor("#6f8fc0");
            case "500":
            case "501":
            case "502":
                return Color.parseColor("#6f8fc0");
            case "503":
            case "504":
            case "505":
            case "506":
            case "507":
            case "508":
                return Color.parseColor("#e19e36");
            case "900":
                return Color.parseColor("#ff7d29");
            case "901":
                return Color.parseColor("#afdee4");
            default:
                return Color.parseColor("#ffffff");
        }
    }
    public static @ColorInt int getContentColorFromWeatherCode(String code){

        switch (code){
            case "100":
                return Color.WHITE;
            case "101":
            case "102":
            case "103":
            case "104":
                return Color.WHITE;
            case "200":
            case "201":
            case "202":
            case "203":
            case "204":
            case "205":
                return Color.BLACK;
            case "206":
            case "207":
            case "208":
                return Color.BLACK;
            case "209":
            case "210":
            case "211":
            case "212":
            case "213":
                return Color.WHITE;
            case "300":
            case "301":
            case "302":
            case "303":
            case "304":
            case "305":
            case "306":
            case "307":
            case "308":
            case "309":
            case "310":
            case "311":
            case "312":
            case "313":
                return Color.WHITE;
            case "400":
            case "401":
            case "402":
            case "403":
            case "404":
            case "405":
            case "406":
            case "407":
                return Color.WHITE;
            case "500":
            case "501":
            case "502":
                return Color.WHITE;
            case "503":
            case "504":
            case "505":
            case "506":
            case "507":
            case "508":
                return Color.WHITE;
            case "900":
                return Color.WHITE;
            case "901":
                return Color.BLACK;
            default:
                return Color.BLACK;
        }
    }
}
