package android.example.com.recipecooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class FoodActivity extends AppCompatActivity {

    private Button btnVegetarian;
    private Button btnSavoryFood;
    private Button btnColdMenu;

    public enum FoodType {VEGETARIAN, SAVORAY,COLDMENU, FAVORITE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_activity);

        btnVegetarian = findViewById(R.id.button);
        btnSavoryFood = findViewById(R.id.button1);
        btnColdMenu = findViewById(R.id.button2);

//        RecipeManager.allRecipes = RecipeManager.getRecipesFromJson(
//                RecipeManager.loadJSONFromAsset(getApplicationContext()));

        RecipeManager.loadRecipeFromFirebase();

        btnVegetarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, FoodRecipeActivity.class);
                intent.putExtra("foodType", FoodType.VEGETARIAN);
                startActivity(intent);

            }
        });

        btnSavoryFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, FoodRecipeActivity.class);
                intent.putExtra("foodType", FoodType.SAVORAY);
                startActivity(intent);

            }
        });

        btnColdMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, FoodRecipeActivity.class);
                intent.putExtra("foodType", FoodType.COLDMENU);
                startActivity(intent);

            }
        });
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        MenuItem favBtn = menu.findItem(R.id.mybutton);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mybutton) {

            Intent intent = new Intent(FoodActivity.this, FoodRecipeActivity.class);
            intent.putExtra("foodType", FoodType.FAVORITE);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
