package distance.persons;

import distance.DistanceProperties;
import distance.GoogleRequestException;
import distance.Point;
import distance.Trial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static distance.DistanceProperties.*;

public class Courier extends Personality<Courier> {
    private static Logger logger = LoggerFactory.getLogger(Courier.class);
    //unnecessary/default=now
    private long departureTime;
    private boolean isBusy;
    private boolean onlineStatus;
    private List<Object> pars;
    {
        pars = new ArrayList<>();
    }

    public Courier(double lat, double lon, long id) {
        super(lat, lon, id);
    }

    public Courier(String currentPlace, long id){
        Point curP = Point.geoCodePoint(currentPlace, id);
        this.id = id;
        lat = curP.getLat();
        lon = curP.getLon();
    }

    public Courier initOnline(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
        return this;
    }
    @Override
    public Courier initTrial(Trial trial) {
        if (!onlineStatus) logger.error("Courier is not online", new IllegalStateException());
        this.trial = trial;
        isBusy = true;
        pars.add("origin=" + lat + "%2C" + lon + trial);
        return this;
    }

    @Override
    public Stream<String> parameters() {
        return pars.stream().map(Object::toString);
    }

    public void updatePosition(long lat, long lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Courier firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Courier lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Courier rideMode(DistanceProperties rideMode) {
        if(rideMode == TRANSIT && trial.interSize() > 0){
            throw new GoogleRequestException("Exactly two waypoints required in transit requests");
        }
        this.rideMode = rideMode;
        pars.add("mode=" + rideMode);
        return this;
    }

    /**
     * @param departure time of start executing an order
     *                  Google API doesn't let provide start time on not TRANSIT ride mode
     * @return this object of Courier
     */
    public Courier departureTime(LocalDateTime departure) throws IllegalArgumentException {
        if (rideMode == TRANSIT) {
            System.out.println(rideMode);
            throw new IllegalArgumentException("Ride mode is not 'TRANSIT'");
        }
        this.departureTime = departure.toInstant(ZoneOffset.UTC).toEpochMilli();
        pars.add("departure_time=" + departure.toInstant(ZoneOffset.UTC).toEpochMilli());
        return this;
    }
}
