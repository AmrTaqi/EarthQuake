package com.ammt.earthquake.data;

import com.ammt.earthquake.model.EarthquakesFeatures;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EarthquakeApi {
    @GET("earthquakes/feed/v1.0/summary/2.5_week.geojson")
    Call<EarthquakesFeatures> getEarthquakes();
}