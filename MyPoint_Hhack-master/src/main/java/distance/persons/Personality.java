package distance.persons;

import distance.DistanceProperties;
import distance.Trial;

import java.util.List;
import java.util.stream.Stream;

import static distance.DistanceProperties.DRIVING;

public abstract class Personality<T extends Personality<T>>{
    String firstName = "unknown";
    String lastName = "unknown";
    DistanceProperties rideMode;
    double lat;
    double lon;
    long id;
    Trial trial;

    public Personality(){
        rideMode = DRIVING;
    }

    public Personality(double lat, double lon, long id) {
        this.lat = lat;
        this.lon = lon;
        this.id = id;
    }

    public abstract T initTrial(Trial value);

    /**
     * @return a stream with all parameters from <T extends Personality<T>> class for URL Request
     */
    public abstract Stream<String> parameters();

}
