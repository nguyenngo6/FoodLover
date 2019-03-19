package android.example.com.recipecooking;

public class CookingStep {

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    String step;
    String imageURL;

    public CookingStep(String step, String imageURL) {
        this.step = step;
        this.imageURL = imageURL;
    }
}
