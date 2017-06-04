package creeper_san.weather.Helper;

import creeper_san.weather.R;

public class ThemeDecodeHelper {

    public static int getThemeID(String themeStr){
        switch (themeStr){
            case "0":return R.style.themeDefault;
            case "1":return R.style.themeRed;
            case "2":return R.style.themePink;
            case "3":return R.style.themePurple;
            case "4":return R.style.themeDeepPurple;
            case "5":return R.style.themeIndigo;
            case "6":return R.style.themeBlue;
            case "7":return R.style.themeLightBlue;
            case "8":return R.style.themeCyan;
            case "9":return R.style.themeTeal;
            case "10":return R.style.themeGreen;
            case "11":return R.style.themeLightGreen;
            case "12":return R.style.themeLightLime;
            case "13":return R.style.themeLightYellow;
            case "14":return R.style.themeLightAmber;
            case "15":return R.style.themeLightOrange;
            case "16":return R.style.themeDeepOrange;
            case "17":return R.style.themeBrown;
            case "18":return R.style.themeGrey;
            case "19":return R.style.themeBlueGrey;
            default:return R.style.themeRed;
        }
    }

}
