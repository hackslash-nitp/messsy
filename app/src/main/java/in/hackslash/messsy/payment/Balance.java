package in.hackslash.messsy.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.Locale;

import in.hackslash.messsy.R;

public class Balance extends AppCompatActivity {
    private TextView curBalance;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseUser currUser  = FirebaseAuth.getInstance().getCurrentUser();;
    private final String user_id = currUser.getUid();
    private final DocumentReference balRef =  db.collection("users")
            .document(user_id).collection("Payment").document("Balance");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        curBalance = findViewById(R.id.balance);
        Task<DocumentSnapshot> balDocSnapShot = balRef.get();
        balDocSnapShot.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Double number;
                if(documentSnapshot.contains("balance")){
                    number = Double.parseDouble(documentSnapshot.get("balance").toString());
                }else {
                    number = 0.0;
                }
                String str = NumberFormat.getCurrencyInstance(new Locale("en", "in")).format(number);
                curBalance.setText(str);
            }
        });
    }
}