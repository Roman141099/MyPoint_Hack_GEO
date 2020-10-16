package distance.persons;

import distance.DistanceProperties;
import distance.Trial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Stream;

import static distance.DistanceProperties.DRIVING;
import static distance.DistanceProperties.TRANSIT;

public class Courier extends Personality<Courier> {
    private static Logger logger = LoggerFactory.getLogger(Courier.class);
    //unnecessary/default=now
    private long departureTime;
    private boolean isBusy;
    private boolean onlineStatus;

    public Courier(long lat, long lon, long id) {
        super(lat, lon, id);
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
        return this;
    }

    @Override
    public Stream<String> parameters() {
        return null;
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
        this.rideMode = rideMode;
        return this;
    }

    /**
     * @param departure time of start executing an order
     *                  Google API doesn't let provide start time on not TRANSIT ride mode
     * @return this object of Courier
     */
    public Courier departureTime(LocalDateTime departure) throws IllegalArgumentException {
        if (rideMode != TRANSIT) {
            throw new IllegalArgumentException("Ride mode is not 'TRANSIT'");
        }
        this.departureTime = departure.toInstant(ZoneOffset.UTC).toEpochMilli();
        return this;
    }

//    public String getFirstName() {
//        return firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public DistanceProperties getRideMode() {
//        return rideMode;
//    }
//
//    public long getLat() {
//        return lat;
//    }
//
//    public long getLon() {
//        return lon;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public Trial getTrial() {
//        return trial;
//    }
//
//    public long getDepartureTime() {
//        return departureTime;
//    }
//
//    public boolean isBusy() {
//        return isBusy;
//    }
}
