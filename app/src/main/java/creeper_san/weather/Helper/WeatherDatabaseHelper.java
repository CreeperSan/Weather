package creeper_san.weather.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import creeper_san.weather.Item.CityItem;

public class WeatherDatabaseHelper {
    private final static String DATABASE_NAME = "Weather.db";
    private final static String TABLE_CITY = "City";
    private final static String KEY_CITY = "City";
    private final static String KEY_PROV = "Province";
    private final static String KEY_COUNTRY = "Country";
    private final static String KEY_LAT = "Lat";
    private final static String KEY_LON = "Lon";
    private final static String KEY_ID = "ID";
    private final static String KEY_NUM = "Num";

    private static WeatherDatabaseHelper instance;
    private SQLiteDatabase database;

    private WeatherDatabaseHelper(Context context) {
        initDatabase(context);
        initTable();
    }

    /**
     *      数据库操作
     */
    private void initDatabase(Context context) {
        database  = context.openOrCreateDatabase(DATABASE_NAME,Context.MODE_PRIVATE,null);
    }
    private void initTable() {
        if (database!=null){
            //创建保存的城市表
            database.execSQL("create table if not exists "+TABLE_CITY+" (" +
                    KEY_ID+" text not null,"+KEY_CITY+" text not null,"+ KEY_NUM +" text not null,"
                    +KEY_COUNTRY+" text,"+KEY_PROV+" text,"+KEY_LAT+" text,"+KEY_LON+" text)");
        }
    }
    public void closeDatabaseHelper(){
        if (database.isOpen()){
            database.close();
        }
        if (instance!=null){
            instance = null;
        }
        System.gc();
    }

    /**
     *      快速数据库增删改查
     */
    private Cursor query(String tableName,String order, String selection,  String... selectArg){
        return database.query(tableName,null,selection,selectArg,null,null,order);
    }
    private void insert(String tableName, ContentValues value){
        database.insert(tableName,null,value);
    }
    private void delete(String tableName,String selection,String... args){
        database.delete(tableName,selection,args);
    }
    private void update(String tableName,ContentValues values,String selection,String... args){
        database.update(tableName,values,selection,args);
    }

    /**
     *      城市操作
     */
    public static List<CityItem> getCityList(Context context){
        List<CityItem> cityList = new ArrayList<>();
        Cursor cursor = getInstance(context).query(TABLE_CITY, KEY_NUM,null, (String[]) null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                String cityName = cursor.getString(cursor.getColumnIndex(KEY_CITY));
                String cnty = cursor.getString(cursor.getColumnIndex(KEY_COUNTRY));
                String prov = cursor.getString(cursor.getColumnIndex(KEY_PROV));
                String lat = cursor.getString(cursor.getColumnIndex(KEY_LAT));
                String lon = cursor.getString(cursor.getColumnIndex(KEY_LON));
                String id = cursor.getString(cursor.getColumnIndex(KEY_ID));
                CityItem item = new CityItem(cityName,cnty,id,lat,lon,prov);
                cityList.add(item);
            }
            cursor.close();
        }
        return cityList;
    }
    public static boolean insert(Context context,CityItem item){
        Cursor cursor = getInstance(context).query(TABLE_CITY,null,KEY_ID+"=?",item.getId());
        if (cursor.getCount()>0){
            return false;//已经有这个城市了
        }
        cursor.close();
        cursor = getInstance(context).query(TABLE_CITY,null,null, (String[]) null);
        ContentValues value = new ContentValues();
        value.put(KEY_NUM,cursor.getCount());
        value.put(KEY_CITY,item.getCity());
        value.put(KEY_COUNTRY,item.getCnty());
        value.put(KEY_ID,item.getId());
        value.put(KEY_LAT,item.getLat());
        value.put(KEY_LON,item.getLon());
        cursor.close();
        log("序号为 "+value.getAsString(KEY_NUM));
        if (item.getProv()!=null){
            if (!item.getProv().equals("")){
                value.put(KEY_PROV,item.getProv());
            }
        }
        getInstance(context).insert(TABLE_CITY,value);
        return true;
    }
    public static void delete(Context context,CityItem item){
        getInstance(context).delete(TABLE_CITY,KEY_ID+"=?",item.getId());
    }
    public static void updateNum(Context context,String cityID,int order){
        ContentValues values = new ContentValues();
        values.put(KEY_NUM,order);
        getInstance(context).update(TABLE_CITY,values,KEY_ID+"=?",cityID);
    }


    public static WeatherDatabaseHelper getInstance(Context context){
        if (instance==null){
            instance = new WeatherDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }


    private static void log(String content){
        Log.i("DatabaseHelper",content);
    }
}
