package creeper_san.weather.Item;

public class CityItem {
    private final String city;
    private final String cnty;
    private final String id;
    private final String lat;
    private final String lon;
    private final String prov;

    public CityItem(String city, String cnty, String id, String lat, String lon, String prov) {
        this.city = city;
        this.cnty = cnty;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.prov = prov;
    }

    public String getCity() {
        return city;
    }

    public String getCnty() {
        return cnty;
    }

    public String getId() {
        return id;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getProv() {
        return prov;
    }
}
