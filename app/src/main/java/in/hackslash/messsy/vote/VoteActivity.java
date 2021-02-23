package in.hackslash.messsy.vote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import in.hackslash.messsy.AbsenceNotification;
import in.hackslash.messsy.R;
import in.hackslash.messsy.home.HomeActivity;

public class VoteActivity extends AppCompatActivity {

    // TODO Declare instances for all UI elements
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        assignVariables();
        setup();
        imageView=findViewById(R.id.arrow_3);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VoteActivity.this, HomeActivity.class));
            }
        });
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
