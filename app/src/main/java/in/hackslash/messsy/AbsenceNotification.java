package in.hackslash.messsy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.print.PrinterId;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import in.hackslash.messsy.home.HomeActivity;
import in.hackslash.messsy.onboarding.User;

public class AbsenceNotification extends AppCompatActivity {

    String START_DATE="START DATE";
    String END_DATE="END DATE";
    String hostel = "Brahmputra";
    ImageView imageView;
    EditText startDate;
    EditText endDate;
    Button okbtn,cancelbtn;

    String sName, sRoll;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference documentReference, documentReference2;
    FirebaseAuth firebaseAuth ;
    FirebaseUser currentUser;
    FirebaseStorage storage = FirebaseStorage.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence_notification);
        startDate = findViewById(R.id.start_date_input);
        endDate = findViewById(R.id.end_date_input);
        okbtn = findViewById(R.id.OK);
        cancelbtn = findViewById(R.id.cancel);
        imageView=findViewById(R.id.arrow_2);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        documentReference = FirebaseFirestore.getInstance().collection("AbsenceNotification").document();
        documentReference2 = FirebaseFirestore.getInstance().collection("users").document(currentUser.getUid());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AbsenceNotification.this, HomeActivity.class));
            }
        });


        documentReference2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists())
                {
                    User user = documentSnapshot.toObject(User.class);
                    sName = user.getName();
                    sRoll = user.getRoll();
                }

                else
                {
                    sName = "Unnown";
                    sRoll = "Unknown";
                    Toast.makeText(AbsenceNotification.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                sName = "Unnown";
                sRoll = "Unknown";
                Toast.makeText(AbsenceNotification.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            }
        });


        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                START_DATE = startDate.getText().toString();
                END_DATE = endDate.getText().toString();

                if(START_DATE.isEmpty())
                {
                    startDate.setError("Date Required!");
                    return;
                }

                if(END_DATE.isEmpty())
                {
                    endDate.setError("Date Required!!");
                    return;
                }

                AbsenceModelClass data = new AbsenceModelClass(START_DATE,END_DATE,sName,sRoll,hostel);

                firebaseFirestore.collection("AbsenceNotification").document().set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(AbsenceNotification.this, "Done.", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(AbsenceNotification.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

}
