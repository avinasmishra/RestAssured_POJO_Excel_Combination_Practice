package POJO;

public class Location extends Object{
    private Object lat;
    private Object lng;

    public Object getLat() {
        return lat;

    }
    public void setLat(Object lat) {
        this.lat = lat;
    }

    public Object getLng() {
        return lng;
    }

    public void setLng(Object lng) {
        this.lng = lng;
    }

    @Override
    public String toString()
    {
        return "Location{" +
                "lat=" +lat +
                ", lng=" + lng +
                '}';

    }

}
