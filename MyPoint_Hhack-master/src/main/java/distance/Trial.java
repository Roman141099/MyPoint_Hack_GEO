package distance;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Trial {
    private final Point startPoint, finishPoint;
    private final List<Point> intermediate;

    public Trial(Point startPoint, Point finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        intermediate = new ArrayList<>();
    }

    /**
     * @param startName start location
     * @param startId id of start location
     * @param finishName end location
     * @param finishId id of end location
     * @return object that represents Trial from start to end with using Google Geocoding API
     */
    public static Trial geoCodeObject(String startName, long startId, String finishName, long finishId){
        Point p1 = Point.geoCodePoint(startName, startId);
        Point p2 = Point.geoCodePoint(finishName, finishId);
        return new Trial(p1, p2);
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
        //startPoint -> Points... -> finishPoint

        return "";
    }
}
