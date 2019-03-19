package android.example.com.recipecooking;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoriteManager {

    public static List<String> getFavNameList(Context context) {

        SharedPreferences pref=context.getSharedPreferences("RecipePref", Context.MODE_PRIVATE);
        List<String> favList = null;
        String jsonStringOfTasks = pref.getString("favList", "");

        if(jsonStringOfTasks.length() > 0) {
            Gson gson = new Gson();

            Type type = new TypeToken<List<String>>() {}.getType();
            //get list of task objects from jsonString obtained by accessing SharedPreferences
            favList = gson.fromJson(jsonStringOfTasks, type);

            return favList;
        } else  {
            return null;
        }
    }

    public static List<Recipe> getFavList(Context context) {

        List<String> favNameList = getFavNameList(context);
        List<Recipe> favRecipes = new ArrayList<>();
        if(RecipeManager.allRecipes != null && favNameList!= null) {

            for (String recipeName: favNameList) {
                Recipe recipe = RecipeManager.getRecipeByName(recipeName);
                if(recipe != null) {
                    favRecipes.add(recipe);
                }

            }
        }

        return favRecipes;
    }

    public static void addFav(Context context, String recipeName) {
        List<String> favNameList = getFavNameList(context);
        if(favNameList == null) favNameList = new ArrayList<>();

        boolean contained = false;

        for (String item: favNameList) {
            if(item.equals(recipeName))
            {
                contained = true;
                break;
            }
        }



        if(contained == false) {
            favNameList.add(recipeName);
            saveToPref(context, favNameList);
        }
    }
    public static void removeFav(Context context, String recipeName) {
        List<String> favNameList = getFavNameList(context);
        if(favNameList == null) return;

        boolean contained = false;
        int indexToRemove = 0;
        for (String item: favNameList) {

            if(item.equals(recipeName))
            {
                contained = true;
                break;
            }
            indexToRemove++;
        }

        if(contained == true) {
            favNameList.remove(indexToRemove);
            saveToPref(context, favNameList);
        }

    }

    public static boolean checkInFav(Context context, String recipeName) {
        List<String> favNameList = getFavNameList(context);

        if(favNameList == null) return false;
        boolean contained = false;
        for (String item: favNameList) {

            if(item.equals(recipeName))
            {
                contained = true;
                break;
            }
        }
        return contained;

    }

    public static void saveToPref(Context context, List<String> favNameList) {
        SharedPreferences pref=context.getSharedPreferences("RecipePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        Gson gson = new Gson();
        //convert list of Task objects to Json String
        String jsonString = gson.toJson(favNameList);
        editor = pref.edit();
        editor.putString("favList", jsonString);
        editor.commit();
    }

}
