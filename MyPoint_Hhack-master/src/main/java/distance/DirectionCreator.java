package distance;

import distance.persons.Courier;
import distance.persons.Personality;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
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
    static final String APIKEY = "AIzaSyCeIxuyZnQ2Ck3XbauOEZXLwIpyOI1Thn8";
    static String via = "&waypoints=via:53.186506%2C50.126170";
    static String URL_QUERY = "https://maps.googleapis.com/maps/api/directions/json?origin=53.210568%2C50.234922&destination=" +
            "53.209033%2C50.124874&language=ru" + via + "&units=metric&mode=" + TRANSIT + "&key=" + APIKEY;
    static String GEO_REQ = "https://maps.googleapis.com/maps/api/geocode/json?address=Samara&key=AIzaSyCeIxuyZnQ2Ck3XbauOEZXLwIpyOI1Thn8";
    public static void main(String[] args) throws IOException {
        HttpURLConnection http = (HttpURLConnection) new URL(URL_QUERY).openConnection();
        new BufferedReader(new InputStreamReader(http.getInputStream())).lines().forEach(System.out::println);
        http.disconnect();
    }

    public String createParameters() {
        Trial trial = Trial.geoCodeObject("Самара, Свободы, 9", 1, "Самара, СамГТУ", 2);
        Personality<Courier> c = new Courier(123,1231,123).
                initOnline(true).
                departureTime(LocalDateTime.now()).
                firstName("Вася").
                lastName("Пупкин").
                initTrial(trial).
                rideMode(TRANSIT);
        return c.parameters().collect(Collectors.joining("&")).concat(APIKEY);
    }

    public Object getResult(String query){
        return new Object();
    }

}
