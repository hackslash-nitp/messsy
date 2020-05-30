package in.hackslash.messsy.models;

public class UpcomingMealCardViewModel {
    int foodImage;
    String nameFood;

    public UpcomingMealCardViewModel(int foodImage, String nameFood) {
        this.foodImage = foodImage;
        this.nameFood = nameFood;
    }

    public int getFoodImage() {
        return foodImage;
    }

    public String getNameFood() {
        return nameFood;
    }
}
