package android.example.com.recipecooking;

import java.util.List;

public class Recipe {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTemType() {
        return temType;
    }

    public void setTemType(int temType) {
        this.temType = temType;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public List<CookingStep> getInstruction() {
        return instruction;
    }

    public void setInstruction(List<CookingStep> instruction) {
        this.instruction = instruction;
    }

    public String getTipandtrick() {
        return tipandtrick;
    }

    public void setTipandtrick(String tipandtrick) {
        this.tipandtrick = tipandtrick;
    }

    public Recipe(String id, String name, String description, int type, int temType, String imageURL,
                  String videoURL, String ingredients, List<CookingStep> instruction,
                  String tipandtrick) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.temType = temType;
        this.imageURL = imageURL;
        this.videoURL = videoURL;
        this.ingredients = ingredients;
        this.instruction = instruction;
        this.tipandtrick = tipandtrick;
    }

    String id;
    String name;
    String description;
    int type; //0: vegetarian; 1: savory
    int temType; //0:hot ;1:cold
    String imageURL;
    String videoURL;
    String ingredients;
    List<CookingStep> instruction;
    String tipandtrick;
}
