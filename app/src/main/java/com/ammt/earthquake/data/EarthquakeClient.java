package com.ammt.earthquake.data;

import com.ammt.earthquake.model.EarthquakesFeatures;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EarthquakeClient {
    private static final String url = "https://earthquake.usgs.gov/";
    private EarthquakeApi earthquakeApi;
    private static EarthquakeClient INSTANCE;

    private EarthquakeClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        earthquakeApi = retrofit.create(EarthquakeApi.class);
    }

    public static EarthquakeClient getINSTANCE() {
        if (null == INSTANCE) {
            INSTANCE = new EarthquakeClient();
        }
        return INSTANCE;
    }

    public Call<EarthquakesFeatures> getEarthquakes() {
        return earthquakeApi.getEarthquakes();
    }
}
