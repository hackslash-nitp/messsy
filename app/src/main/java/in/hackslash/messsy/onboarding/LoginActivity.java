package in.hackslash.messsy.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import in.hackslash.messsy.R;
import in.hackslash.messsy.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {
    // TODO Declare instances for all UI elements
    private EditText email_et, password_et;
    private Button login;
    private TextView create_account, forgot_pass;
    private AccountsUtil accountsUtil;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        assignVariables();
        setup();
    }


    public void assignVariables() {
        // TODO initialize all fields here
        email_et = findViewById(R.id.line_1);
        password_et = findViewById(R.id.line_2);
        login = findViewById(R.id.rectangle_1);
        create_account = findViewById(R.id.create_account);
        forgot_pass = findViewById(R.id.forgot_password);
        progressBar=findViewById(R.id.login_progress);
        accountsUtil = new AccountsUtil();
    }

    public void setup() {
        handleLogin();
        handleForgotPassword();
        handleCreateAccount();
    }

    private void handleCreateAccount() {
        // TODO define onclicklistener on Create Account button to navigate to CreateAccountActivity and close this activity
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void handleForgotPassword() {
        // TODO Later
        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void handleLogin() {

        //  define onclicklistener on Sign In button to collect data from all edittexts and call AccountUtil.loginUser


        //  Show an indefinite progress bar on the screen as soon as the submit button is clicked to denote that data is being processed


        //  Pass a callback by loginUser().setOnCompleteListener() to handle the result from the update operation. Show a toast according to the result and also hide the progress bar.


        //  NOTE: If the result is SUCCESS, then close this activity and navigate to HomeActivity

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = email_et.getText().toString();
                String password = password_et.getText().toString();
                User user = new User();
                user.setEmail(email);
                accountsUtil.loginUser(user, password).setOnCompleteListener(new AccountsUtil.onCompleteListener() {
                    @Override
                    public void onComplete(AccountsUtil.Status status, User user) {
                        if (status == AccountsUtil.Status.ALREADY_LOGGED_IN) {
                            //user already logged in
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "Already Logged in", Toast.LENGTH_SHORT).show();
                        } else if (status == AccountsUtil.Status.WRONG_INPUT) {
                            //wrong input
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "wrong input", Toast.LENGTH_SHORT).show();
                        } else if (status == AccountsUtil.Status.DUPLICATE_USER) {
                            //user already exist
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "user with this email already exist", Toast.LENGTH_SHORT).show();
                        } else if (status == AccountsUtil.Status.SUCCESS) {
                            //logged in successful
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        });


    }
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
        }
    }
}
