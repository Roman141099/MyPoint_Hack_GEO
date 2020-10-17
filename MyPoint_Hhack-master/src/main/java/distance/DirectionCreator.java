package distance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.util.ISO8601Utils;
import distance.persons.Client;
import distance.persons.Courier;
import distance.persons.Personality;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static distance.DistanceProperties.*;

public class DirectionCreator {
    /*
    * 1. origin
    * 2. destination
    * 3. language
    * 4. waypoints
    * 5. units
    * 6. mode
    * 7. api key
    * */
    static final String APIKEY = "&key=AIzaSyCeIxuyZnQ2Ck3XbauOEZXLwIpyOI1Thn8";
    static String via = "&waypoints=via:53.186506%2C50.126170";
    static String URL_QUERY = "https://maps.googleapis.com/maps/api/directions/json?origin=53.210568%2C50.234922&destination=" +
            "53.209033%2C50.124874&language=ru" + via + "&units=metric&mode=" + DRIVING + "&key=" + APIKEY;
    static final String GEO_REQ = "https://maps.googleapis.com/maps/api/geocode/json?address=Samara&key=AIzaSyCeIxuyZnQ2Ck3XbauOEZXLwIpyOI1Thn8";
    static final String BEGIN_URL = "https://maps.googleapis.com/maps/api/directions/json?";
    public static void main(String[] args){
        Trial trial = Trial.geoCodeObject("Самара Ново-садовая 8а", 1);
//        trial.addWayPoint(Point.geoCodePoint("Самара, Рубежное кладбище", 12456));
        Personality<Courier> c = new Courier("Самара свободы 9", 2352).
                initTrial(trial).
                initOnline(true).
                rideMode(TRANSIT).
                firstName("Вася").
                lastName("Пупкин");
        try {
            ResultTrial exit = getResult(createParameters(c));
            System.out.println(exit.getDistance().getMetres() + " м, " + exit.getDistance().getTextDistance() + "\n" + exit.getDuration().getSeconds() + " с, " + exit.getDuration().getTextTime());
        }catch (IOException e){
            Logger.getLogger("My log");
        }
    }

    /**
     * Creation pattern
     * ...
     * rideMode
     * departure time
     * ...
     * @return
     */
    public static String createParameters(Personality<? extends Personality> c) {
        return c.parameters().collect(Collectors.joining("&", BEGIN_URL, APIKEY));
    }

    public static ResultTrial getResult(String query)  throws IOException {
        HttpURLConnection http = (HttpURLConnection) new URL(query).openConnection();
        Reader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
        Gson gson = new GsonBuilder().registerTypeAdapter(ResultTrial.class, ResultTrial.resTrialJsonDeser).create();
        ResultTrial resTrial = gson.fromJson(reader, ResultTrial.class);
        http.disconnect();
        return resTrial;
    }
}
