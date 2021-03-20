package in.hackslash.messsy.home.home;

public class MealData {
    String imageUrl;

    public MealData(String imageUrl, String description) {
        this.imageUrl = imageUrl;
        this.description = description;
    }

    String description;

    public MealData() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
