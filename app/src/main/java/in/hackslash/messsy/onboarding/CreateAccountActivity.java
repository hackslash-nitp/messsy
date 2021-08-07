package in.hackslash.messsy.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import in.hackslash.messsy.R;
import in.hackslash.messsy.home.HomeActivity;

public class CreateAccountActivity extends AppCompatActivity {
    // Declare instances for all UI elements
    private EditText name_ct, email_ct, room_no_ct, password_ct;
    private Button create_acc;
    private TextView log_in;
    private AccountsUtil accountsUtil;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        assignVariables();
        setup();
    }


    public void assignVariables() {
        // initialize all fields here
        name_ct = findViewById(R.id.name_input);
        email_ct = findViewById(R.id.email_input);
        room_no_ct = findViewById(R.id.room_no_input);
        password_ct = findViewById(R.id.password_input);
        create_acc = findViewById(R.id.create_acc);
        log_in = findViewById(R.id.log_in);
        progressBar=findViewById(R.id.signup_progress);
        accountsUtil = new AccountsUtil();
    }

    public void setup(){
        handleCreateAccount();
        handleLogin();

    }

    private void handleLogin() {
        // define onclicklistener on Sign in button to navigate to LoginActivity and close this activity
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void handleCreateAccount() {
        // define onclicklistener on Save Details button to collect data from all edittexts and call ComplaintsUtil.createAccount

        // Show an indefinite progress bar on the screen as soon as the submit button is clicked to denote that data is being processed

        // Pass a callback by createAccount().setOnCompleteListener() to handle the result from the update operation. Show a toast according to the result and also hide the progress bar.

        // NOTE: If the result is SUCCESS, then close this activity and navigate to HomeActivity
        create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = name_ct.getText().toString();
                String email = email_ct.getText().toString();
                String roomno = room_no_ct.getText().toString();
                String password = password_ct.getText().toString();
                progressBar.setVisibility(View.VISIBLE);

                if(TextUtils.isEmpty(email)){
                    email_ct.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    password_ct.setError("Password is required");
                    return;
                }
                if(TextUtils.isEmpty(roomno)){
                    room_no_ct.setError("Room No. is required");
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    name_ct.setError("Name is required");
                    return;
                }
                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setRoomno(roomno);
                user.setBalance(3000);



                accountsUtil.createAccount(user, password).setOnCompleteListener(new AccountsUtil.onCompleteListener() {
                    @Override
                    public void onComplete(AccountsUtil.Status status, User user) {
                       if (status == AccountsUtil.Status.ALREADY_LOGGED_IN) {
                            //user already logged in
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(CreateAccountActivity.this, "Already Logged in", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateAccountActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (status == AccountsUtil.Status.WRONG_INPUT) {
                            //wrong input
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(CreateAccountActivity.this, "Wrong input", Toast.LENGTH_SHORT).show();
                        }  else if (status == AccountsUtil.Status.SUCCESS) {
                            //account created successfully
                            progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(CreateAccountActivity.this, "New account created successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateAccountActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }  else if (status == AccountsUtil.Status.DUPLICATE_USER) {
                            //user already exist
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(CreateAccountActivity.this, "User with this email already exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
