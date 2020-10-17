package distance;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Trial {
    private Point startPoint;
    private final List<Point> intermediate;

    public Trial(Point startPoint){
        this.startPoint = startPoint;
        intermediate = new ArrayList<>();
    }

    public int interSize(){
        return intermediate.size();
    }

    /**
     * @param startName start location
     * @param startId id of start location
//     * @param finishName end location
//     * @param finishId id of end location
     * @return object that represents Trial from start to end with using Google Geocoding API
     */

    public static Trial geoCodeObject(String startName, long startId){
        Point p1 = Point.geoCodePoint(startName, startId);
        return new Trial(p1);
    }

    /**
     *
     * @param places add way points to Trial(array count can't be more than 25)
     * @return the size of all added places
     */
    public int addWayPoint(Point... places) {
        if(intermediate.size() + places.length > 25)throw new GoogleRequestException("No more 25 waypoints must be in query");
        intermediate.addAll(Arrays.asList(places));
        return intermediate.size();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        //origin=53.210568%2C50.234922&destination=53.209033%2C50.124874&waypoints=via:53.186506%2C50.126170
        //startPoint -> Points... -> finishPoint
        str.append("&destination=").append(startPoint).append("&language=ru");
        if(intermediate.size() != 0){
            String ways = intermediate.stream().map(Point::toString).collect(Collectors.joining("%7C", "via:", ""));
            str.append("&waypoints=").append(ways);
        }

        return str.toString();
    }
}
