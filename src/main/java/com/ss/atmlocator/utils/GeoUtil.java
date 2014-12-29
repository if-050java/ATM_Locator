package com.ss.atmlocator.utils;

import com.ss.atmlocator.entity.GeoPosition;

import java.util.Locale;

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

    public static boolean inRadius(final GeoPosition basePoint, final GeoPosition checkedPoint, int radius) {
        if (checkedPoint == null || basePoint == null) {
            return false;
        }
        return getDistance(basePoint, checkedPoint) <= radius;
    }


    private static int degrees(final double coordinate) {
        return (int) coordinate;
    }

    private static double minutes(final double coordinate) {
        final double minutesInDegree = 60.0;
        return (coordinate - degrees(coordinate)) * minutesInDegree;
    }

    public static String geoLocationString(final GeoPosition position) {
        if (position.getLongitude() == 0 || position.getLatitude() == 0) {
            return "undefined";
        }

        char southOrNorth = position.getLatitude() > 0 ? 'N' : 'S';
        char westOrEast = position.getLongitude() > 0 ? 'E' : 'W';

        // sample: N48°42.2491' E23°50.4972'
        return String.format(Locale.US, "%c%02d\u00B0%02.4f' %c%02d\u00B0%02.4f'",
                southOrNorth, degrees(position.getLatitude()), minutes(position.getLatitude()),
                westOrEast, degrees(position.getLongitude()), minutes(position.getLongitude()));
    }

}
