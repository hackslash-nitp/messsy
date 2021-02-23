package in.hackslash.messsy;

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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import in.hackslash.messsy.home.HomeActivity;

public class AbsenceNotification extends AppCompatActivity {

    private final String START_DATE="START DATE";
    private final String END_DATE="END DATE";
    private final String MISSED_MEAL="MISSING MEAL";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference mealRef=db.collection("AbsenceNotification");
    ImageView imageView;
    EditText startDate;
    EditText endDate;
    Button okbtn,cancelbtn;
    RadioGroup rg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence_notification);
        startDate = findViewById(R.id.start_date_input);

        endDate = findViewById(R.id.end_date_input);
        okbtn = findViewById(R.id.OK);
        cancelbtn = findViewById(R.id.cancel);
        rg=findViewById(R.id.radio_buttom_group_a);
        imageView=findViewById(R.id.arrow_2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AbsenceNotification.this, HomeActivity.class));
            }
        });
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload(v);
            }
        });

    }
    public void upload(View v){
        Map<String,Object> meal=new HashMap<>();
        String missedMeal=((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        String Start_date= startDate.getText().toString();
        String end_date=endDate.getText().toString();
        meal.put(START_DATE,Start_date);
        meal.put(END_DATE,end_date);
        meal.put(MISSED_MEAL,missedMeal);
        mealRef.add(meal);
    }
}
