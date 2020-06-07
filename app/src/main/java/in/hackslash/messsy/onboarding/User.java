package in.hackslash.messsy.onboarding;

public class User {

    // TODO add user fields here
    private String email,name,roomno;

    public User(){

    }
    public User(String email, String name, String roomno) {
        this.email = email;
        this.name = name;
        this.roomno = roomno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }
}
