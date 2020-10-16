package distance.persons;

import distance.DistanceProperties;
import distance.Trial;

import java.util.List;
import java.util.stream.Stream;

import static distance.DistanceProperties.DRIVING;

public abstract class Personality<T extends Personality<T>>{
    protected String firstName = "unknown";
    protected String lastName = "unknown";
    protected DistanceProperties rideMode = DRIVING;
    protected long lat;
    protected long lon;
    protected long id;
    protected Trial trial;

    public Personality(long lat, long lon, long id) {
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
