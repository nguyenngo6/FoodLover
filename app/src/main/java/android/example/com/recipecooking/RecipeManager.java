package android.example.com.recipecooking;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



public class RecipeManager {

    public static List<Recipe> allRecipes = null;

    public static Recipe getRecipeByName(String recipeName) {

        if(allRecipes == null)
            return null;
        else {
            for (Recipe item: allRecipes) {
                if(item.getName().equals(recipeName)) {
                    return item;
                }
            }
            return null;
        }
    }

    public static List<Recipe> getRecipeByType(int type) {

        if(allRecipes == null)
            return null;
        else {
            ArrayList<Recipe> recipes = new ArrayList<>();

            for (Recipe item: allRecipes) {
                if(item.getType() == type) {
                    recipes.add(item);
                }
            }

            return recipes;
        }
    }

    public static List<Recipe> getRecipeByTem(int temType) {

        if(allRecipes == null)
            return null;
        else {
            ArrayList<Recipe> recipes = new ArrayList<>();

            for (Recipe item: allRecipes) {
                if(item.getTemType() == temType) {
                    recipes.add(item);
                }
            }

            return recipes;
        }
    }



    public static List<Recipe> getRecipesFromJson(String json) {
        Gson gson = new Gson();
        List<Recipe> list = gson.fromJson(json,
                new TypeToken<List<Recipe>>(){}.getType());
        return list;
    }

    public static String loadJSONFromAsset(Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open("recipe.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    //Get data from FireBase
    public static void loadRecipeFromFirebase() {
        final FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference("recipes");

        // Attach a listener to read the data at our posts reference
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Object object = dataSnapshot.getValue(Object.class);
                String json = new Gson().toJson(object);
                RecipeManager.allRecipes = getRecipesFromJson(json);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

}