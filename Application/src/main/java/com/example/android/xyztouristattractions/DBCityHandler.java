package com.example.android.xyztouristattractions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alex on 25.03.2016.
 */
public class DBCityHandler extends SQLiteOpenHelper implements CityListener {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "CitDatabase.db";
    private static final String TABLE_NAME = "city_table";
    private static final String KEY_ID = "id";
    private static final String KEY_COUNTRYID = "countryId";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_NAME = "name";

    String CREATE_TABLE =
            "CREATE TABLE "+TABLE_NAME+ "("
                    +KEY_ID+" INTEGER PRIMARY KEY,"
                    +KEY_COUNTRYID +" INTEGER,"
                    +KEY_LATITUDE+" INTEGER,"
                    +KEY_LONGITUDE+" INTEGER,"
                    +KEY_NAME +" TEXT " + ")";
    String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public DBCityHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }


    @Override
    public void addCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_COUNTRYID,city.getCountryId());
            values.put(KEY_LATITUDE, city.getLatitude());
            values.put(KEY_LONGITUDE, city.getLongitude());
            values.put(KEY_NAME,city.getName());
            db.insert(TABLE_NAME, null, values);
            db.close();
        }catch (Exception e){
            Log.e("problem",e+"");
        }
    }

    @Override
    public ArrayList<City> getAllCity() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<City> cityList = null;
        try{
            cityList = new ArrayList<City>();
            String QUERY = "SELECT * FROM "+TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            if(!cursor.isLast())
            {
                while (cursor.moveToNext())
                {
                    City city = new City();
                    city.setId(cursor.getInt(0));
                    city.setCountryId(cursor.getInt(1));
                    city.setLatitude(cursor.getInt(2));
                    city.setLongitude(cursor.getInt(3));
                    city.setName(cursor.getString(4));
                    cityList.add(city);
                }
            }
            db.close();
        }catch (Exception e){
            Log.e("error",e+"");
        }
        return cityList;
    }

    @Override
    public int getCityCount() {
        int num = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String QUERY = "SELECT * FROM "+TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            num = cursor.getCount();
            db.close();
            return num;
        }catch (Exception e){
            Log.e("error",e+"");
        }
        return 0;
    }
}
