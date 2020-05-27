package in.hackslash.messsy.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import in.hackslash.messsy.R;

public class EditProfileActivity extends AppCompatActivity {

    // TODO Declare instances for all UI elements

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        assignVariables();
        setup();
    }

    public void assignVariables() {
        // TODO initialize all fields here
    }

    public void setup() {
        // TODO define onclicklistener on Save Details button to collect data from all edittexts and call ComplaintsUtil.updateUser

        // TODO Show an indefinite progress bar on the screen as soon as the submit button is clicked to denote that data is being processed

        // TODO Pass a callback by updateUser().setOnCompleteListener() to handle the result from the update operation. Show a toast according to the result and also hide the progress bar.

        // TODO NOTE: If the result is SUCCESS, then close this activity and navigate to HomeActivity

    }
}
