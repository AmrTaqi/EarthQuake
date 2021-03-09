package com.ammt.earthquake.model;

import com.ammt.earthquake.util.Constants;
import com.google.gson.JsonArray;

public class EarthquakesFeatures {
    JsonArray features;

    public JsonArray getFeatures() {
        return features;
    }

    public void setFeatures(JsonArray features) {
        this.features = features;
    }

    public Earthquakes getEarthquakesFeatures(int index) {
         String place = this.features.get(index).getAsJsonObject().get(Constants.PROPERTIES).getAsJsonObject().get(Constants.PLACE).getAsString();
         double magnitude = this.features.get(index).getAsJsonObject().get(Constants.PROPERTIES).getAsJsonObject().get(Constants.MAGNITUDE).getAsDouble();
         long time = this.features.get(index).getAsJsonObject().get(Constants.PROPERTIES).getAsJsonObject().get(Constants.TIME).getAsLong();
         double lat = this.features.get(index).getAsJsonObject().get(Constants.GEOMETRY).getAsJsonObject().get(Constants.COORDINATES).getAsJsonArray().get(0).getAsDouble();
         double lon = this.features.get(index).getAsJsonObject().get(Constants.GEOMETRY).getAsJsonObject().get(Constants.COORDINATES).getAsJsonArray().get(1).getAsDouble();

         return new Earthquakes(place, magnitude, time, lat, lon);
    }
}