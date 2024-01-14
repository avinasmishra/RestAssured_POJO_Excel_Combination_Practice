package MapAPIs;

import POJO.AddPlace;
import POJO.Location;
import Processors.AddPlaceAPI;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AddPlace_API {
    public static void main(String[] args) throws IOException {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        AddPlaceAPI addPlaceAPI = new AddPlaceAPI();

        List<Object> list = new ArrayList<>();

     //   list.add("excelFilePath:");
        list.add("Customers:row2");
        list.add("Locations:row3");
        list.add("Types:row2;row3");

        String payload = addPlaceAPI.constructAddPlace(list);

        String response = given().log().all().relaxedHTTPSValidation()
                .queryParam("key","qaclick123")
                .contentType(ContentType.JSON)
                .body(payload)
                .when().post("/maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).extract().asString();

        JsonPath path = new JsonPath(response);
        String Status = path.get("status");
        String placeID = path.get("place_id");
        String ID = path.get("id");
        System.out.println("Status: "+Status);
        System.out.println("Place ID: "+placeID);
        System.out.println("ID: "+ID);

    }

    public static Object getAPIDetails()
    {
        AddPlace addPlace = new AddPlace();
        addPlace.setPhone_number("9876543212");
        addPlace.setAccuracy(50);
        addPlace.setAddress("Ram Mandir, Ayodhya, UP, 345678");
        addPlace.setLanguage("Hindi");
        addPlace.setName("Jai Shree Ram");
        addPlace.setWebsite("http://google.com");

        List<Object> types = new ArrayList<Object>();
        types.add("Ram park");
        types.add("shop");
        addPlace.setTypes(types);

        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        addPlace.setLocation(location);

        return addPlace;
    }
}