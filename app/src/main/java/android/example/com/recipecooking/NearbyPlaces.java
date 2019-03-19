package android.example.com.recipecooking;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class NearbyPlaces extends AsyncTask<Object, String, String> {

    private String googleData, url;
    private GoogleMap mMap;
    @Override
    protected String doInBackground(Object... objects) {

        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];

        HttpHandler httpHandler = new HttpHandler();
        try {
            googleData = httpHandler.DataReader(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyPlacesList = null;
        DataParser dataParser = new DataParser();
        nearbyPlacesList = dataParser.parse(s);

        displayNearbyPlaces(nearbyPlacesList);
    }

    private void displayNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googleNearbyPlaces = nearbyPlacesList.get(i);
            String name = googleNearbyPlaces.get("place_name");
            String vicinity = googleNearbyPlaces.get("vicinity");
            String rating = googleNearbyPlaces.get("rating");
            double lat = Double.parseDouble(googleNearbyPlaces.get("lat"));
            double lng = Double.parseDouble(googleNearbyPlaces.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(name + "," + " Rating: " + rating);
            markerOptions.snippet(vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        }
    }
}
