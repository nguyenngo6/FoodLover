package android.example.com.recipecooking;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_TIME = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            public void run() {

                Intent intent = new Intent();
                intent.setClass(SplashScreenActivity.this,
                        HomePageActivity.class);

                SplashScreenActivity.this.startActivity(intent);
                SplashScreenActivity.this.finish();

            }
        }, SPLASH_DISPLAY_TIME);
    }
}