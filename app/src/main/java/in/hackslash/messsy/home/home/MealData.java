package in.hackslash.messsy.home.home;

public class MealData {
    String imageUrl;
    String name;
    String description;
    int cardColor;
    int textColor;

    public MealData(String imageUrl, String description, String name,int cardColor,int textColor) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.name = name;
        this.cardColor = cardColor;
        this.textColor=textColor;
    }

    public MealData() {
    }
    public int getCardColor(){return cardColor;}
    public void setCardColor(int cardColor){
        this.cardColor = cardColor;
    }
    public int getTextColor(){return textColor;}
    public void setTextColor(int textColor){this.textColor=textColor;}
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