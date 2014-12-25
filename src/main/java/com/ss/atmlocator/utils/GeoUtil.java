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
        return String.format("%c%02d\u00B0%02.4f' %c%02d\u00B0%02.4f'",
                southOrNorth, degrees(position.getLatitude()), minutes(position.getLatitude()),
                westOrEast, degrees(position.getLongitude()), minutes(position.getLongitude()));
    }

}
