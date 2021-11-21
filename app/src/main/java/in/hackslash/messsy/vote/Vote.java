package in.hackslash.messsy.vote;

public class Vote {

    String mealTime, foodItem, addOrRemove;

    Vote() {}

    public Vote(String sMealTime, String sFoodItem, String sAddOrRemove) {
        this.mealTime = sMealTime;
        this.foodItem = sFoodItem;
        this.addOrRemove = sAddOrRemove;
    }

    public String getMealTime() {
        return mealTime;
    }

    public void setMealTime(String mealTime) {
        this.mealTime = mealTime;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public String getAddOrRemove() {
        return addOrRemove;
    }

    public void setAddOrRemove(String addOrRemove) {
        this.addOrRemove = addOrRemove;
    }

}
