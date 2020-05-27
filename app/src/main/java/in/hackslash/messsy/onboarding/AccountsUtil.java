package in.hackslash.messsy.onboarding;


public class AccountsUtil {

    private onCompleteListener onCompleteListener;

    public AccountsUtil createAccount(User user, String password){

        // TODO Check if a user is logged in and return ALREADY_LOGGED_IN if a logged in user is found

        // TODO Validate user here and return WRONG_INPUT if anything is wrong

        // TODO Check DB if the user exists and return DUPLICATE_USER if user exists

        // TODO Create new account using createUserWithEmailAndPassword and return SUCCESS if successful

        // Leave as it is
        return this;
    }

    public AccountsUtil loginUser(User user, String password) {

        // TODO Check if a user is logged in and return ALREADY_LOGGED_IN if a logged in user is found

        // TODO Validate user here and return WRONG_INPUT if anything is wrong

        // TODO Check DB if the user exists and return USER_DOES_NOT_EXIST if user is not found

        // TODO Login using the credentials and return SUCCESS if successful

        // Leave as it is
        return this;
    }

    public AccountsUtil updateUser(User user, String password) {

        // TODO Check DB if the user exists and return USER_DOES_NOT_EXIST if user is not found

        // TODO Check if the new details match with the DB and return DUPLICATE_USER if found same

        // TODO Validate user here and return WRONG_INPUT if anything is wrong

        // TODO Update the user data on DB as well as Auth if required and return SUCCESS

        // Leave as it is
        return this;

    }

    public AccountsUtil fetchUser(String userID) {

        // TODO Check DB if the user exists and return USER_DOES_NOT_EXIST if user is not found

        // TODO Return the user in a User object if found with a SUCCESS Status

        // Leave as it is
        return this;
    }

    public void setOnCompleteListener(AccountsUtil.onCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public interface onCompleteListener {
        void onComplete(Status status, User user);
    }

    public enum Status {
        SUCCESS, DUPLICATE_USER, WRONG_INPUT, ALREADY_LOGGED_IN, USER_DOES_NOT_EXIST
    }
}

