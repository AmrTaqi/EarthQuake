package com.ammt.earthquake.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ammt.earthquake.R;
import com.ammt.earthquake.data.EarthquakeClient;
import com.ammt.earthquake.model.Earthquakes;
import com.ammt.earthquake.model.EarthquakesFeatures;
import com.ammt.earthquake.util.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuakesListActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Earthquakes> quakes;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quakes_list);
        quakes = new ArrayList<>();
        listView = findViewById(R.id.listOfItems);
        getEarthquakes();
        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            Intent intent = getIntent();
            double lat = quakes.get(position).getLat();
            double lon = quakes.get(position).getLon();
            intent.putExtra(Constants.LAT_POS, lat);
            intent.putExtra(Constants.LON_POS, lon);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void getEarthquakes() {
        EarthquakeClient earthquakeClient = EarthquakeClient.getINSTANCE();

        earthquakeClient.getEarthquakes().enqueue(new Callback<EarthquakesFeatures>() {
            @Override
            public void onResponse(Call<EarthquakesFeatures> call, Response<EarthquakesFeatures> response) {

                assert response.body() != null;
                for (int i = 0; i <= Constants.LIMIT; i++) {
                    Earthquakes earthquakes = response.body().getEarthquakesFeatures(i);
                    quakes.add(earthquakes);

                }

                arrayAdapter = new ArrayAdapter(QuakesListActivity.this, android.R.layout.simple_list_item_1,
                        android.R.id.text1, quakes);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<EarthquakesFeatures> call, Throwable t) {

                Log.d("myPlace", "onPlace: AAA");
            }
        });
    }


}