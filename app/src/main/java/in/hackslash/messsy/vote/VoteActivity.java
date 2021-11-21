package in.hackslash.messsy.vote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import in.hackslash.messsy.AbsenceNotification;
import in.hackslash.messsy.R;
import in.hackslash.messsy.home.HomeActivity;

public class VoteActivity extends AppCompatActivity {

    // TODO Declare instances for all UI elements
    ImageView imageView;

    RadioGroup radioGroup;
    RadioButton radioButton;

    EditText FoodItem;

    String addOrRemove = "add";




    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    FirebaseAuth firebaseAuth ;
    FirebaseUser currentUser;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    Bitmap bitmap;






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


        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        documentReference = FirebaseFirestore.getInstance().collection("Menu Change").document();


        radioGroup = findViewById(R.id.radio_buttom_group);
        FoodItem = findViewById(R.id.enter_food_item);

        Button add = findViewById(R.id.vote_for_ad);
        Button remove = findViewById(R.id.vote_for_re);

        Button submit = findViewById(R.id.submit_button);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrRemove = "add";
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrRemove = "remove";
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food = FoodItem.getText().toString();

                if(food.isEmpty())
                {
                    FoodItem.setError("Specify Food Item.");
                    return;
                }

                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                String foodTime = radioButton.getText().toString();

                Vote voteForChange = new Vote(foodTime, food, addOrRemove);


                firebaseFirestore.collection("Menu Change").document().set(voteForChange).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(VoteActivity.this, "Request sent to " + addOrRemove + " " + food + " in " + foodTime + ".", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(VoteActivity.this, "Request Failed.", Toast.LENGTH_SHORT).show();

                    }
                });




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
