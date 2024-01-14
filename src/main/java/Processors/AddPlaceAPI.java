package Processors;

import POJO.AddPlace;
import POJO.Location;
import Utiles.ExcelReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddPlaceAPI {

    public String constructAddPlace(List<Object> rawInputValue) throws IOException {
        String dataFileSourceString = (String) rawInputValue.get(0);
        String[] modelDataSource = rawInputValue.get(0).toString().split(":");
        String modelName = modelDataSource[0];
        String modelDataRows = modelDataSource[1];
        if(("null").equals(modelDataRows))
        {
            return null;
        }
        ExcelReader excelReader = new ExcelReader(dataFileSourceString,modelName,modelDataRows);

        List<List<Object>> addPlaceValue = excelReader.readMultipleRows();
        List<Object> addPlaceDetails = addPlaceValue.get(0);
        ObjectMapper mapper = new ObjectMapper();

        AddPlace addPlacePayload = new AddPlace();
        addPlacePayload.setAccuracy(addPlaceDetails.get(0));
        addPlacePayload.setName(addPlaceDetails.get(1));
        addPlacePayload.setPhone_number(addPlaceDetails.get(2));
        addPlacePayload.setAddress(addPlaceDetails.get(3));
        addPlacePayload.setWebsite(addPlaceDetails.get(4));
        addPlacePayload.setLanguage(addPlaceDetails.get(5));
        addPlacePayload.setLocation(getLocation(rawInputValue));
        addPlacePayload.setTypes(getTypes(rawInputValue));

        try {
            String jsonPayload = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(addPlacePayload);
            return StringEscapeUtils.unescapeJava(jsonPayload);
        }catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return "Invalid";
    }

    public Location getLocation(List<Object> rawInputValue) throws IOException {
        String dataFileSourceString = (String) rawInputValue.get(0);
        String[] modelDataSource = rawInputValue.get(1).toString().split(":");
        String modelName = modelDataSource[0];
        String modelDataRows = modelDataSource[1];
        if(("null").equals(modelDataRows))
        {
            return null;
        }
        ExcelReader excelReader = new ExcelReader(dataFileSourceString,modelName,modelDataRows);

        List<Object> locationDetails = excelReader.readMultipleRows().get(0);
        Location location = new Location();
        location.setLat(locationDetails.get(0));
        location.setLng(locationDetails.get(1));
        return location;
    }
    public List<Object> getTypes(List<Object> rawInputValue) throws IOException {
        String dataFileSourceString = (String) rawInputValue.get(0);
        String[] modelDataSource = rawInputValue.get(2).toString().split(":");
        String modelName = modelDataSource[0];
        String modelDataRows = modelDataSource[1];
        if(("null").equals(modelDataRows))
        {
            return null;
        }
        List<Object> types = new ArrayList<>();
        ExcelReader excelReader = new ExcelReader(dataFileSourceString,modelName,modelDataRows);
        List<List<Object>> typeDetails = excelReader.readMultipleRows();

        for (List<Object> typeDetail:typeDetails)
        {
            types.add(typeDetail.get(0));
        }
        return types;
    }
    }
