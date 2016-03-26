package com.example.android.xyztouristattractions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alex on 26.03.2016.
 */
public class DBCountryHandler extends SQLiteOpenHelper implements CountryListener {
    private static final int DB_VERSION = 3;
    private static final String DB_NAME = "CountryDatabase.db";
    private static final String TABLE_NAME = "country_table";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME+ "("
            +KEY_ID+" INTEGER PRIMARY KEY,"
            +KEY_NAME + "TEXT " + ")";
    String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public DBCountryHandler(Context context) {
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
    public void addCountry(Country country) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_NAME,country.getName());
            db.insert(TABLE_NAME, null, values);
            db.close();
        }catch (Exception e){
            Log.e("problem", e + "");
        }
    }

    @Override
    public ArrayList<Country> getAllCountry() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Country> countryList = null;
        try{
            countryList = new ArrayList<Country>();
            String QUERY = "SELECT * FROM "+TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            if(!cursor.isLast())
            {
                while (cursor.moveToNext())
                {
                    Country country = new Country();
                    country.setId(cursor.getInt(0));
                    country.setName(cursor.getString(1));
                    countryList.add(country);
                }
            }
            db.close();
        }catch (Exception e){
            Log.e("error",e+"");
        }
        return countryList;
    }

    @Override
    public int getCountryCount() {
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
