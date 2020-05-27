package in.hackslash.messsy.complaint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import in.hackslash.messsy.R;

public class NoticeActivity extends AppCompatActivity {

    // TODO Declare instances for all UI elements

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        assignVariables();
        setup();
    }

    public void assignVariables() {
        // TODO initialize all fields here
    }

    public void setup() {
        // TODO show progress bar initially to demonstrate that data is loading

        // TODO call ComplaintsUtil.fetchNotice here and populate the elements with data as soon as it is received & hide progress bar
    }
}
