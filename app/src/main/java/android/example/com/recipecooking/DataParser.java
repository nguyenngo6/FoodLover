package android.example.com.recipecooking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    private HashMap<String, String> googlePlaces(JSONObject googlePlacesJSON){
        HashMap<String, String> googlePlacesMap = new HashMap<>();
        String name = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String rating = "";

        try {
            if (!googlePlacesJSON.isNull("name")){
                name = googlePlacesJSON.getString("name");
            }
            if (!googlePlacesJSON.isNull("vicinity")){
                vicinity = googlePlacesJSON.getString("vicinity");
            }
            latitude = googlePlacesJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlacesJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");
            rating = googlePlacesJSON.getString("rating");

            googlePlacesMap.put("place_name", name);
            googlePlacesMap.put("vicinity", vicinity);
            googlePlacesMap.put("lat", latitude);
            googlePlacesMap.put("lng", longitude);
            googlePlacesMap.put("rating", rating);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return googlePlacesMap;
    }

    private List<HashMap<String, String>> getAll(JSONArray jsonArray){
        int count = jsonArray.length();

        List<HashMap<String, String>> NearbyPlacesList = new ArrayList<>();

        HashMap<String, String> NearbyPlacesMap = null;

        for (int i = 0; i < count; i++) {
            try {
                NearbyPlacesMap = googlePlaces((JSONObject) jsonArray.get(i));
                NearbyPlacesList.add(NearbyPlacesMap);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return NearbyPlacesList;
    }


    public List<HashMap<String, String>> parse(String JSONdata) {
        JSONArray jsonArray = null;
        JSONObject jsonObject;


        try {
            jsonObject = new JSONObject(JSONdata);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getAll(jsonArray);

    }
}
