package android.example.com.recipecooking;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class RecipeDetailActivity extends YouTubeBaseActivity {

    private Recipe currentRecipe;
    TextView txtRecipeName;
    TextView txtRecipeDescription;
    TextView txtRecipeIngredients;
    TextView txtRecipeTipAndTricks;
    Button btnStepByStepMode;
    ImageButton addDiaryButton;
    String recipeName;

    ImageButton favImageButton;
    private TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);

        header = findViewById(R.id.headerText);
        header.setText(formattedDate);



        Intent intent = getIntent();
        recipeName = intent.getStringExtra("recipeName");

        currentRecipe = RecipeManager.getRecipeByName(recipeName);
        loadVideoToYoutubePlayer(currentRecipe.getVideoURL());

        txtRecipeName = findViewById(R.id.txtRecipeName);
        txtRecipeName.setText(currentRecipe.getName());

        txtRecipeDescription = findViewById(R.id.txtRecipeDescription);
        txtRecipeDescription.setText(currentRecipe.getDescription());

        txtRecipeIngredients = findViewById(R.id.txtRecipeIngredients);
        txtRecipeIngredients.setText(currentRecipe.ingredients);

        txtRecipeTipAndTricks = findViewById(R.id.txtRecipeTipAndTricks);
        txtRecipeTipAndTricks.setText(currentRecipe.getTipandtrick());

        btnStepByStepMode = findViewById(R.id.btnStepByStepMode);
        btnStepByStepMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeDetailActivity.this,
                        StepByStepActivity.class);
                intent.putExtra("recipeName", recipeName);
                startActivity(intent);
            }
        });

        addDiaryButton = findViewById(R.id.addDiaryButton);
        addDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeDetailActivity.this,
                        addMeal.class);
                intent.putExtra("recipeName", recipeName);
                intent.putExtra("date", header.getText().toString());
                startActivity(intent);
            }
        });


        favImageButton = findViewById(R.id.favImageButton);
        if(FavoriteManager.checkInFav(RecipeDetailActivity.this, currentRecipe.getName())) {
            favImageButton.setImageResource(R.drawable.like);

        } else {
            favImageButton.setImageResource(R.drawable.unlike);

        }
        favImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(FavoriteManager.checkInFav(RecipeDetailActivity.this, currentRecipe.getName())) {
                    FavoriteManager.removeFav(RecipeDetailActivity.this, currentRecipe.getName());
                    favImageButton.setImageResource(R.drawable.unlike);

                } else {
                    FavoriteManager.addFav(RecipeDetailActivity.this, currentRecipe.getName());
                    favImageButton.setImageResource(R.drawable.like);
                }
            }
        });

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

                        AlertDialog.Builder dialog = new AlertDialog.Builder(RecipeDetailActivity.this);
                        dialog.setCancelable(true);
                        dialog.setTitle("Error");
                        dialog.setMessage("Check your internet OR make sure YouTube App is installed!");
                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                        final AlertDialog alert = dialog.create();
                        alert.show();

                    }
                });
    }
}
