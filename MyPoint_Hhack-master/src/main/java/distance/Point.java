package distance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

public class Point {
    private transient static final String GEO_REQ = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private transient static final String API_KEY = "AIzaSyCeIxuyZnQ2Ck3XbauOEZXLwIpyOI1Thn8";
    private double lat;
    private double lon;
    private String placeName;
    private transient long id;

    private Point(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public Point(double lat, double lon, long id) {
        this(lat, lon);
        this.id = id;
    }

    private static JsonDeserializer<Point> pointJsonDeserializer = (jsonElement, type, jsonDeserializationContext) -> {
        JsonObject firstEl = jsonElement.getAsJsonObject().getAsJsonArray("results").get(0).
                getAsJsonObject();
        JsonObject location = firstEl.getAsJsonObject("geometry").getAsJsonObject("location");
        return new Point(location.getAsJsonPrimitive("lat").getAsDouble(),
                location.getAsJsonPrimitive("lng").getAsDouble()).
                setPlaceName(firstEl.getAsJsonPrimitive("formatted_address").getAsString());
    };

    /**
     * From place name to coordinates
     * **/
    public static Point geoCodePoint(String placeName, long id){
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Point.class, pointJsonDeserializer).create();
        Point point = gson.fromJson(Objects.requireNonNull(readJson(placeName)).orElseThrow(GoogleRequestException::new),
                Point.class);
        point.id = id;
        return point;
    }

    private static Optional<Reader> readJson(String placeName){
        try {
             HttpURLConnection http = (HttpURLConnection) new URL(String.format("%s%s&language=ru&key=%s", GEO_REQ,
                    placeName.replaceAll(",|\\.|\\s", "+"), API_KEY)).openConnection();
            http.disconnect();
            return Optional.of(new InputStreamReader(http.getInputStream()));
        } catch (IOException e) {
            return Optional.empty();
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

    public Point setPlaceName(String placeName) {
        this.placeName = placeName;
        return this;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%f%s%f",lat,"%2C", lon).replaceAll(",", ".");
    }
}
