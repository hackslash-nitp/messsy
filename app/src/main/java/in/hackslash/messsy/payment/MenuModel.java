package in.hackslash.messsy.payment;

public class MenuModel {
    private String name,image;
    private int quantity,price;

    public MenuModel(String name, String image, int quantity, int price) {
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
    }

    public MenuModel() {
        //needed
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
