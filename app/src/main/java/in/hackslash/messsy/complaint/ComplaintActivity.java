package in.hackslash.messsy.complaint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import in.hackslash.messsy.R;

public class ComplaintActivity extends AppCompatActivity {

    // TODO Declare instances for all UI elements

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        assignVariables();
        setup();
    }

    public void assignVariables() {
        // TODO initialize all fields here
    }

    public void setup() {
        // TODO define onclicklistener on File Complaints button to collect data from all edittexts and call ComplaintsUtil.createComplaint

        // TODO Show an indefinite progress bar on the screen as soon as the submit button is clicked to denote that data is being processed

        // TODO Pass a callback by createComplaint().setOnCompleteListener() to handle the result from the create operation. Show a toast according to the result and also hide the progress bar.
    }
}
