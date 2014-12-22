package com.ss.atmlocator.utils;

import com.ss.atmlocator.entity.GeoPosition;

/**
 * Created by Vasyl Danylyuk on 02.12.2014.
 */
public class GeoUtil {

    public static final int AVERAGE_LENGTH_ONE_DEGREE = 111200;//Average length of one degree in meters

    /**
     * @return distance between two positions on map
     */
    private static long getDistance(GeoPosition point1, GeoPosition point2){
        //angle between two points and ears center
        double angle = Math.sin(point1.getLatInRad()) * Math.sin(point2.getLatInRad())
                        + Math.cos(point1.getLatInRad()) * Math.cos(point2.getLatInRad()) * Math.cos(point2.getLngInRad()
                        - point1.getLngInRad());

        return Math.round(AVERAGE_LENGTH_ONE_DEGREE*Math.acos(angle)*180/Math.PI);
    }

    public static boolean inRadius(GeoPosition basePoint, GeoPosition checkedPoint, int radius){
        if(checkedPoint == null || basePoint == null) return false;
        return getDistance(basePoint, checkedPoint) <= radius;
    }
}
