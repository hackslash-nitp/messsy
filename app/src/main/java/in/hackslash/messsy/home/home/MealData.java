package in.hackslash.messsy.home.home;

public class MealData {
    String imageUrl;
    String name;
    String description;


    public MealData(String imageUrl, String description, String name) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.name = name;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}