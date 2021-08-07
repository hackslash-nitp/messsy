package in.hackslash.messsy.payment;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.*;

import in.hackslash.messsy.R;
import in.hackslash.messsy.home.HomeActivity;

public class PaymentDetails extends AppCompatActivity{
    private static final String TAG = "PaymentDetails";
    private ImageView arrow;
    private FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String user_id = currUser.getUid();
    private final CollectionReference paymentDetails =  db.collection("users").document(user_id).collection("PaymentDetails");
    final ArrayList<Map<String,Object>> paymentList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        arrow = (ImageView) findViewById(R.id.arrow_1);
        recyclerView =(RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        load();
        handleArrow();
    }
    void load(){
        final Task<QuerySnapshot> querySnapshotTask = paymentDetails.orderBy("DATE", Query.Direction.valueOf("DESCENDING")).get();
        querySnapshotTask.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                    paymentList.add(queryDocumentSnapshot.getData());
                }
                recyclerView.setAdapter(new PaymentHistoryAdapter(paymentList));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,e.toString());
            }
        });
    }

    private void handleArrow() {
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentDetails.this, HomeActivity.class));
                finish();
            }
        });
    }
}