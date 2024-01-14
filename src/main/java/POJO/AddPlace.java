package POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AddPlace extends Object{
    @JsonProperty("accuracy")
    private Object accuracy;
    @JsonProperty("name")
    private Object name;
    @JsonProperty("phone_number")
    private Object phone_number;
    @JsonProperty("address")
    private Object address;
    @JsonProperty("website")
    private Object website;
    @JsonProperty("language")
    private Object language;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("types")
    private List<Object> types;

    public Object getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Object accuracy) {
        this.accuracy = accuracy;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(Object phone_number) {
        this.phone_number = phone_number;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getWebsite() {
        return website;
    }

    public void setWebsite(Object website) {
        this.website = website;
    }

    public Object getLanguage() {
        return language;
    }

    public void setLanguage(Object language) {
        this.language = language;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Object> getTypes() {
        return types;
    }

    public void setTypes(List<Object> types) {
        this.types = types;
    }

    @Override
    public String toString()
    {
        return "AddPlace{" +
                "accuracy=" +accuracy +
                ", name=" + name +
                ", phone_number=" + phone_number +
                ", address=" + address +
                ", website=" + website +
                ", language=" + language +
                ", location=" + location +
                ", types=" + types +
                '}';
    }
}
