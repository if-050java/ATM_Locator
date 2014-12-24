package com.ss.atmlocator.utils;

import com.ss.atmlocator.entity.GeoPosition;

import static java.lang.Math.*;

/**
 * Created by Vasyl Danylyuk on 02.12.2014.
 */
public class GeoUtil {

    private static final int AVERAGE_LENGTH_ONE_DEGREE = 111200;//Average length of one degree in meters
    private static final double RAD_TO_DEGREE = 180/PI;

    /**
     * @return distance between two positions on map
     */
    private static long getDistance(GeoPosition point1, GeoPosition point2){
        //angle between two points and ears center
        double angle = sin(point1.getLatInRad()) * sin(point2.getLatInRad())
                        + cos(point1.getLatInRad()) * cos(point2.getLatInRad()) * cos(point2.getLngInRad()
                - point1.getLngInRad());

        return round(AVERAGE_LENGTH_ONE_DEGREE * acos(angle) * RAD_TO_DEGREE);
    }

    public static boolean inRadius(GeoPosition basePoint, GeoPosition checkedPoint, int radius){
        if(checkedPoint == null || basePoint == null) {
            return false;
        };
        return getDistance(basePoint, checkedPoint) <= radius;
    }
}
