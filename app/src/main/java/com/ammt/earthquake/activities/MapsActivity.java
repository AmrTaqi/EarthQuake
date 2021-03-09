package com.ammt.earthquake.activities;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ammt.earthquake.R;
import com.ammt.earthquake.adapters.CustomInfoWindow;
import com.ammt.earthquake.data.EarthquakeClient;
import com.ammt.earthquake.util.Constants;
import com.ammt.earthquake.model.Earthquakes;
import com.ammt.earthquake.model.EarthquakesFeatures;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    private BitmapDescriptor[] iconColors;
    private Button showList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        showList = findViewById(R.id.showListBtn);
        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MapsActivity.this, QuakesListActivity.class), Constants.REQUEST_CODE);
            }
        });
        iconColors = new BitmapDescriptor[] {
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE),
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW),
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE),
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN),
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN),
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA),
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE),
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE),
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)};

        getEarthquakes();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            double lat = data.getDoubleExtra(Constants.LAT_POS, 0);
            double lon = data.getDoubleExtra(Constants.LON_POS, 0);
            if (lat != 0 && lon != 0) {

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 3));

                //Toast.makeText(MapsActivity.this, lat + ", " + lon, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getEarthquakes() {
        EarthquakeClient earthquakeClient = EarthquakeClient.getINSTANCE();

        earthquakeClient.getEarthquakes().enqueue(new Callback<EarthquakesFeatures>() {
            @Override
            public void onResponse(Call<EarthquakesFeatures> call, Response<EarthquakesFeatures> response) {

                assert response.body() != null;
                for (int i = 0; i <= Constants.LIMIT; i++) {
                    Earthquakes earthquakes = response.body().getEarthquakesFeatures(i);
                    String formattedDate = DateFormat.getDateInstance().format(new Date(earthquakes.getTime()));
                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(earthquakes.getLat(),
                            earthquakes.getLon())).title(earthquakes.getPlace()).
                            snippet("Magnitude: " +
                                    earthquakes.getMagnitude() +
                                    "\n" + "Date: " + formattedDate)
                            .icon(iconColors[Constants.randomNum(0, iconColors.length -1)]);

                    if (earthquakes.getMagnitude() >= 4.0) {
                        CircleOptions circleOptions = new CircleOptions()
                                .center(new LatLng(earthquakes.getLat(), earthquakes.getLon()))
                                .fillColor(Color.RED)
                                .radius(30000d).strokeWidth(3.5f);
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        mMap.addCircle(circleOptions);
                    }

                    mMap.addMarker(markerOptions);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(earthquakes.getLat(),
                            earthquakes.getLon()), 3));
                }

            }

            @Override
            public void onFailure(Call<EarthquakesFeatures> call, Throwable t) {

                Log.d("myPlace", "onPlace: AAA");
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoWindow(this));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}