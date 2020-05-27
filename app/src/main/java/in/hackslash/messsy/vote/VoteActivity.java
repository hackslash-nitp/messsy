package in.hackslash.messsy.vote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import in.hackslash.messsy.R;

public class VoteActivity extends AppCompatActivity {

    // TODO Declare instances for all UI elements

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        assignVariables();
        setup();
    }

    public void assignVariables() {
        // TODO initialize all fields here
    }

    public void setup() {
        // TODO define onclicklistener on File User button to collect data from all edittexts and call VoteUtil.createVote

        // TODO Show an indefinite progress bar on the screen as soon as the submit button is clicked to denote that data is being processed

        // TODO Pass a callback by createVote().setOnCompleteListener() to handle the result from the create operation. Show a toast according to the result and also hide the progress bar.
    }
}
