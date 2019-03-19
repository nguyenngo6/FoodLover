package android.example.com.recipecooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

//https://www.androidhive.info/2016/10/android-working-with-firebase-realtime-database/
public class FoodRecipeActivity extends AppCompatActivity {

    FoodActivity.FoodType currentType;
    HomePageActivity.TemType currentTemType;
    List<Recipe> currentRecipes;
    RecyclerView MyRecyclerView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_recipe);

        Intent intent = getIntent();
        currentType = (FoodActivity.FoodType) intent.getSerializableExtra("foodType");
        currentTemType = (HomePageActivity.TemType) intent.getSerializableExtra("temType");




        if(currentType == FoodActivity.FoodType.SAVORAY) {
            setTitle("SAVORY");
            currentRecipes = RecipeManager.getRecipeByType(currentType.ordinal());

        }else if(currentType == FoodActivity.FoodType.VEGETARIAN) {
            setTitle("VEGETARIAN");
            currentRecipes = RecipeManager.getRecipeByType(currentType.ordinal());

        }else if(currentType == FoodActivity.FoodType.COLDMENU) {
            setTitle("DESSERTS AND DRINKS");
            currentRecipes = RecipeManager.getRecipeByType(currentType.ordinal());

        }
        else if(currentTemType == HomePageActivity.TemType.HOT) {
        setTitle("HOT FOODS");
        currentRecipes = RecipeManager.getRecipeByTem(currentTemType.ordinal());

        }else if(currentTemType == HomePageActivity.TemType.COLD) {
            setTitle("COLD STUFF");
            currentRecipes = RecipeManager.getRecipeByTem(currentTemType.ordinal());

        }
        else {
            setTitle("FAVORITE LIST");
            currentRecipes = FavoriteManager.getFavList(FoodRecipeActivity.this);
        }


//        loadData();

    }

    private void loadData() {
        MyRecyclerView = findViewById(R.id.cardView);

        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(FoodRecipeActivity.this);
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new MyAdapter(FoodRecipeActivity.this, currentRecipes));
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(currentType == FoodActivity.FoodType.FAVORITE) {
            currentRecipes = FavoriteManager.getFavList(FoodRecipeActivity.this);
        }
        loadData();
    }
}
