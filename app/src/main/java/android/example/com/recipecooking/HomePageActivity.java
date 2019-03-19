package android.example.com.recipecooking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class HomePageActivity extends YouTubeBaseActivity implements LocationListener {


    TextView selectCity,getCurrent,buttonHot,buttonRecipes,buttonNearby,buttonDiary, buttonCold, locationText, cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;
    ProgressBar loader;
    Typeface weatherFont;
    String city = "Ho Chi Minh City";
    /* Please Put your API KEY here */
    String OPEN_WEATHER_MAP_API = "edaea1a8014983a500603c639ddd9b8a";
    /* Please Put your API KEY here */
    LocationManager locationManager;
    //    String path  = "android.resource://" + getPackageName() + "/" + R.raw.food;

    public enum TemType {HOT, COLD};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        isLocationEnabled();

        RecipeManager.loadRecipeFromFirebase();

        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weathericons-regular-webfont.ttf");
        weatherIcon = (TextView) findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        loader = (ProgressBar) findViewById(R.id.loader);
        selectCity = (TextView) findViewById(R.id.selectCity);
        cityField = (TextView) findViewById(R.id.city_field);
        updatedField = (TextView) findViewById(R.id.updated_field);
        getCurrent = (TextView) findViewById(R.id.getCurrent);
        locationText = (TextView) findViewById(R.id.locationText);
        detailsField = (TextView) findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        humidity_field = (TextView) findViewById(R.id.humidity_field);
        pressure_field = (TextView) findViewById(R.id.pressure_field);
        weatherIcon = (TextView) findViewById(R.id.weather_icon);
        buttonHot = findViewById(R.id.buttonHot);
        buttonHot.setVisibility(View.INVISIBLE);

        buttonCold = findViewById(R.id.buttonCold);
        buttonCold.setVisibility(View.INVISIBLE);

        buttonRecipes = findViewById(R.id.buttonRecipes);
        buttonNearby = findViewById(R.id.buttonNearby);
        buttonDiary = findViewById(R.id.buttonDiary);
        taskLoadUp(city);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        final FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference();
//        loadVideoToYoutubePlayer(mFirebaseDatabase.push().getKey());

        mFirebaseDatabase.child("videos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                loadVideoToYoutubePlayer(snapshot.getValue().toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        buttonHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, FoodRecipeActivity.class);
                intent.putExtra("temType", HomePageActivity.TemType.HOT);
                startActivity(intent);

            }
        });



        buttonCold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, FoodRecipeActivity.class);
                intent.putExtra("temType", HomePageActivity.TemType.COLD);
                startActivity(intent);

            }
        });

        getCurrent.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              getLocation();
//                Intent intent = new Intent();
//                intent.setComponent(new ComponentName("com.android.phone", "com.android.phone.MobileNetworkSettings"));
//                startActivity(intent);
                                          }
                                      }
        );

        buttonRecipes.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent i = new Intent(HomePageActivity.this, FoodActivity.class);
                                              startActivity(i);
                                          }
                                      }
        );

        buttonNearby.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 getLocation();
                                                 Intent i = new Intent(HomePageActivity.this, GoogleMapsActivity.class);
                                                 startActivity(i);
                                             }
                                         }
        );

        buttonDiary.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent i = new Intent(HomePageActivity.this, DiaryActivity.class);
                                                 startActivity(i);
                                             }
                                         }
        );

        selectCity.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomePageActivity.this);
                                              alertDialog.setTitle("Change City");
                                              final EditText input = new EditText(HomePageActivity.this);
                                              input.setText("");
                                              LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                      LinearLayout.LayoutParams.MATCH_PARENT,
                                                      LinearLayout.LayoutParams.MATCH_PARENT);
                                              input.setLayoutParams(lp);
                                              alertDialog.setView(input);

                                              alertDialog.setPositiveButton("Change",
                                                      new DialogInterface.OnClickListener() {
                                                          public void onClick(DialogInterface dialog, int which) {
                                                              city = input.getText().toString();
                                                              taskLoadUp(city);
                                                          }
                                                      });
                                              alertDialog.setNegativeButton("Cancel",
                                                      new DialogInterface.OnClickListener() {
                                                          public void onClick(DialogInterface dialog, int which) {
                                                              dialog.cancel();
                                                          }
                                                      });
                                              alertDialog.show();

                                          }
                                      }
        );

    }




    public void taskLoadUp(String query) {
        if (WeatherFunction.isNetworkAvailable(getApplicationContext())) {
            DownloadWeather task = new DownloadWeather();
            task.execute(query);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }





    class DownloadWeather extends AsyncTask < String, Void, String > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loader.setVisibility(View.VISIBLE);

        }
        protected String doInBackground(String...args) {
            String xml = WeatherFunction.excuteGet("http://api.openweathermap.org/data/2.5/weather?q=" + args[0] +
                    "&units=metric&appid=" + OPEN_WEATHER_MAP_API);

            return xml;
        }
        @Override
        protected void onPostExecute(String xml) {
//            final VideoView video = (VideoView)findViewById(R.id.video);
            try {
                JSONObject json = new JSONObject(xml);
                if (json != null) {
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONObject("main");
                    DateFormat df = DateFormat.getDateTimeInstance();

                    cityField.setText(json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country"));
                    currentTemperatureField.setText(String.format("%.2f", main.getDouble("temp")) + "°C - " + details.getString("description").toUpperCase(Locale.US) + " - HUMIDITY " + main.getString("humidity") + "%" );
//                    humidity_field.setText("Humidity: " + main.getString("humidity") + "%");
//                    pressure_field.setText("Pressure: " + main.getString("pressure") + " hPa");
//                    pressure_field.setText(currentTemperatureField.getText() + " " + detailsField.getText());
                    loader.setVisibility(View.GONE);

                    String desiredString = currentTemperatureField.getText().toString().substring(0,2);
                    double newDouble = Double.valueOf(desiredString);
                    if(newDouble>18){
//                        String path  = "android.resource://" + getPackageName() + "/" + R.raw.food;
//                        video.setVideoURI(Uri.parse(path));
                    buttonCold.setVisibility(View.VISIBLE);
                    buttonHot.setVisibility(View.INVISIBLE);
                    } else {

//                        String path  = "android.resource://" + getPackageName() + "/" + R.raw.food;

//                        video.setVideoURI(Uri.parse(path));
                        buttonHot.setVisibility(View.VISIBLE);
                        buttonCold.setVisibility(View.INVISIBLE);
                    }
//                    video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                        @Override
//                        public void onPrepared(MediaPlayer mp) {
//                            mp.setLooping(true);
//                        }
//                    });
//                    video.start();


                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Initializing data", Toast.LENGTH_SHORT).show();
            }


        }



    }

    public void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
            taskLoadUp(locationText.getText().toString());
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationText.setText(addresses.get(0).getLocality());
            taskLoadUp(locationText.getText().toString());
        }catch(Exception e)
        {

        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(HomePageActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    private void isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            android.support.v7.app.AlertDialog.Builder alertDialog=new android.support.v7.app.AlertDialog.Builder(this);
            alertDialog.setTitle("Enable Location");
                    alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
                    alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                    Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            android.support.v7.app.AlertDialog alert=alertDialog.create();
            alert.show();
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        getLocation();

    }

    @Override
    public void onStart(){
        super.onStart();
        getLocation();

    }


    private void loadVideoToYoutubePlayer(final String videoURL) {
        YouTubePlayerView youTubePlayerView = findViewById(R.id.player);

        youTubePlayerView.initialize("AIzaSyAs-TCLVlDpXhRTEApvh_bJ6I5cDhs89Rw", //API Key
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        //inside cueVideo
                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(videoURL);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(HomePageActivity.this);
                        dialog.setCancelable(true);
                        dialog.setTitle("Error");
                        dialog.setMessage("Check your internet OR make sure YouTube App is installed!");
                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                        final android.support.v7.app.AlertDialog alert = dialog.create();
                        alert.show();

                    }
                });
    }

}