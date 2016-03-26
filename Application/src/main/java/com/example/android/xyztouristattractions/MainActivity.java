package com.example.android.xyztouristattractions;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
//import org.json.simple.parser.ParseException;
//import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Alex on 26.03.2016.
 */
public class MainActivity extends Activity {
    ListView listView;
    CityAdapter cityAdapter;
    CountryAdapter countryAdapter;
    ArrayList<City> cityArrayList;
    ArrayList<Country> countryArrayList;
    DBCityHandler cityHandler;
    DBCountryHandler countryHandler;

    Button mStartMapButton;

    public void Start(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    private static final String filePath = "D:\\AndroidStudioProjects\\HackathonApp-1\\TakeMeToTrip.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartMapButton = (Button)findViewById(R.id.startMapButton);

        mStartMapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Start(v);
            }
        });

        listView = (ListView) findViewById(R.id.listview);
        cityHandler = new DBCityHandler(this);
        countryHandler = new DBCountryHandler(this);
        //NetworkUtils utils = new NetworkUtils(MainActivity.this);
        if(cityHandler.getCityCount() == 0 && countryHandler.getCountryCount() == 0)
        {
            new DataFetcherTask().execute();
        }
        else
        {
            ArrayList<City> cityList = cityHandler.getAllCity();
            //ArrayList<Country> countryList = countryHandler.getAllCountry();
            //countryAdapter = new CountryAdapter(MainActivity.this,countryList);
            cityAdapter = new CityAdapter(MainActivity.this,cityList);
            listView.setAdapter(cityAdapter);
        }
    }

    class DataFetcherTask extends AsyncTask<Void,Void,Void> {

        public String loadJSONFromAsset() {
            String json = null;
            try {
                InputStream is = getAssets().open("TakeMeToTrip.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
// Json Parsing Code Start
            try {
                cityArrayList = new ArrayList<City>();
                //JSONParser parser = new JSONParser();
                //Object obj = parser.parse(new FileReader("D:\\AndroidStudioProjects\\HackathonApp-1\\TakeMeToTrip.json"));
                JSONObject jsonObject = new JSONObject(loadJSONFromAsset()) ;
                JSONArray allCountries = (JSONArray) jsonObject.get("countries");
                JSONArray allCities = (JSONArray) jsonObject.get("cities");
                for(int i=0; i < allCountries.length(); i++) {
                    JSONObject currentCountry = (JSONObject) allCountries.get(i);
                    int id = ((Number) currentCountry.get("id")).intValue();
                    String name = (String) currentCountry.get("name");
                    Country country = new Country();
                    country.setId(id);
                    country.setName(name);
                    // countryHandler.addCountry(country);
                }
                for(int i=0; i < allCities.length(); i++) {
                    JSONObject currentCity = (JSONObject) allCities.get(i);
                    int id = ((Number) currentCity.get("id")).intValue();
                    int countryId = ((Number) currentCity.get("countryId")).intValue();
                    double latitude = ((Number) currentCity.get("latitude")).doubleValue();
                    double longitude = ((Number) currentCity.get("longitude")).doubleValue();
                    String name = (String) currentCity.get("name");
                    City city = new City();
                    city.setId(id);
                    city.setCountryId(countryId);
                    city.setLatitude(latitude);
                    city.setLongitude(longitude);
                    city.setName(name);
                    // cityHandler.addCity(city);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//Json Parsing code end
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<City> cityList = cityHandler.getAllCity();
            //ArrayList<Country> countryList = countryHandler.getAllCountry();
            //countryAdapter = new CountryAdapter(MainActivity.this,countryList);
            cityAdapter = new CityAdapter(MainActivity.this,cityList);
            listView.setAdapter(cityAdapter);
        }
    }

}
