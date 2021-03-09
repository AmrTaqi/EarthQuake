package com.ammt.earthquake.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ammt.earthquake.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
    private View view;
    private Context context;

    public CustomInfoWindow(Context context) {
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        TextView title = view.findViewById(R.id.windowTitle);
        TextView magnitude = view.findViewById(R.id.magnitudeInfo);
        title.setText(marker.getTitle());
        magnitude.setText(marker.getSnippet());
        return view;
    }
}
