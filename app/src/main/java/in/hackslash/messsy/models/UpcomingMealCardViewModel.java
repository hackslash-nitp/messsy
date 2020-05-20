package in.hackslash.messsy.home;

public class CardViewModel {
    int foodImage;
    String nameFood;

    public CardViewModel(int foodImage, String nameFood) {
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
