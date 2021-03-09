package com.ammt.earthquake.util;

import java.util.Random;

public class Constants {
    public static final String PROPERTIES = "properties";
    public static final String PLACE = "place";
    public static final String MAGNITUDE = "mag";
    public static final String TIME = "time";
    public static final String GEOMETRY = "geometry";
    public static final String COORDINATES = "coordinates";
    public static final int LIMIT = 30;
    public static final String LAT_POS = "lat";
    public static final String LON_POS = "lon";
    public static final int REQUEST_CODE = 2;

    public static int randomNum (int min, int max) {
        return new Random().nextInt(max - min) + min;
    }
}
