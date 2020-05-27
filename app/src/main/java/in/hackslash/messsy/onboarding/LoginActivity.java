package in.hackslash.messsy.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import in.hackslash.messsy.R;

public class LoginActivity extends AppCompatActivity {
    // TODO Declare instances for all UI elements

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        assignVariables();
        setup();
    }


    public void assignVariables() {
        // TODO initialize all fields here
    }

    public void setup() {
        handleLogin();
        handleForgotPassword();
        handleCreateAccount();
    }

    private void handleCreateAccount() {
        // TODO define onclicklistener on Create Account button to navigate to CreateAccountActivity and close this activity
    }

    private void handleForgotPassword() {
        // TODO Later
    }

    public void handleLogin(){
        // TODO define onclicklistener on Sign In button to collect data from all edittexts and call ComplaintsUtil.loginUser

        // TODO Show an indefinite progress bar on the screen as soon as the submit button is clicked to denote that data is being processed

        // TODO Pass a callback by loginUser().setOnCompleteListener() to handle the result from the update operation. Show a toast according to the result and also hide the progress bar.

        // TODO NOTE: If the result is SUCCESS, then close this activity and navigate to HomeActivity

    }
}
