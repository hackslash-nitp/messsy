package in.hackslash.messsy.onboarding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import in.hackslash.messsy.R;

public class ForgotPassword extends AppCompatActivity {

    private static final String TAG = "ForgotPassword";
    private EditText email_et;
    private Button btnForgotPass;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        
        email_et = findViewById(R.id.email_edit_text);
        btnForgotPass = findViewById(R.id.btn_forgot_pass);
        auth = FirebaseAuth.getInstance();

        handleForgotPassword();
    }

    private void handleForgotPassword() {
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_et.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter your email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendPasswordResetLink(email);
            }
        });
    }

    private void sendPasswordResetLink(String email){
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this, "Password Reset link has been sent to the email", Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"Reset link sent to email");
                        } else {
                            String exc = task.getException().getMessage();
                            Toast.makeText(ForgotPassword.this, "Invalid Email: "+ exc, Toast.LENGTH_SHORT).show();
                            Log.d(TAG,exc);
                        }
                    }
                });
    }
}