package in.hackslash.messsy.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import in.hackslash.messsy.R;
import in.hackslash.messsy.home.HomeActivity;

public class NoticeActivity extends AppCompatActivity {

    // TODO Declare instances for all UI elements

    RecyclerView r;
    ImageView imageView;

    String date, description, subject, time, user, hostel, designation;


    private final String TAG = "abcd";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference notebookRef = db.collection("MessssyNotice");
    Notice madapter = new Notice(this);
    ArrayList<NoticeBoxAdapterClass> notice = new ArrayList<NoticeBoxAdapterClass>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        r = findViewById(R.id.recycler_view);
        imageView=findViewById(R.id.arrow_1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoticeActivity.this, HomeActivity.class));
            }
        });
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        r.setLayoutManager(layoutManager);
        load();
        r.setAdapter(madapter);

    }


    public void load() {
        notebookRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    date = documentSnapshot.getString("date");
                    description = documentSnapshot.getString("description");
                    subject = documentSnapshot.getString("subject");
                    time = documentSnapshot.getString("timestamp");
                    user = documentSnapshot.getString("userInfo");
                    hostel = documentSnapshot.getString("hostel");
                    designation = documentSnapshot.getString("designation");

                    notice.add(new NoticeBoxAdapterClass(subject, description, date, time, designation, hostel));
                }
                madapter.updatefood(notice);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.toString());

            }
        });
    }
}