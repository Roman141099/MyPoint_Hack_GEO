package distance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

public class Point {
    private static final String GEO_REQ = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String API_KEY = "AIzaSyCeIxuyZnQ2Ck3XbauOEZXLwIpyOI1Thn8";
    private double lat;
    private double lon;
    private String placeName;
    private long id;

    private Point(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public Point(double lat, double lon, long id) {
        this(lat, lon);
        this.id = id;
    }

    /**
     * From place name to coordinates
     * **/
    public static Point geoCodePoint(String placeName, long id){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Point point = gson.fromJson(Objects.requireNonNull(readJson(placeName)).orElseThrow(GoogleRequestException::new),
                Point.class);
        point.id = id;
        //JsonDeserializer...
        return point;
    }

    private static Optional<Reader> readJson(String placeName){
        HttpURLConnection http = null;
        try {
             http = (HttpURLConnection) new URL(String.format("%s%s&key=%s", GEO_REQ,
                    placeName.replaceAll(",|\\.|\\s", "+"), API_KEY)).openConnection();
            return Optional.of(new InputStreamReader(http.getInputStream()));
        } catch (IOException e) {
            return Optional.empty();
        }finally {
            assert http != null;
            http.disconnect();
        }
    }

    public double getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public long getId() {
        return id;
    }
}
