package com.example.android.xyztouristattractions;

import java.util.ArrayList;

/**
 * Created by Alex on 25.03.2016.
 */
public interface CityListener {
    public void addCity(City city);
    public ArrayList<City> getAllCity();
    public int getCityCount();
}
