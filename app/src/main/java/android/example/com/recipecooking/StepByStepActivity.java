package android.example.com.recipecooking;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class StepByStepActivity extends AppCompatActivity {

    ViewPager viewPager;
    private Recipe currentRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_by_step);

        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("recipeName");
        currentRecipe = RecipeManager.getRecipeByName(recipeName);

        setTitle(recipeName);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(this, currentRecipe.getInstruction()));
    }
}
