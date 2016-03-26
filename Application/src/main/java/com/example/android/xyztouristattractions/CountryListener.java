package com.example.android.xyztouristattractions;

import java.util.ArrayList;

/**
 * Created by Alex on 26.03.2016.
 */
public interface CountryListener {
    public void addCountry(Country country);
    public ArrayList<Country> getAllCountry();
    public int getCountryCount();
}
