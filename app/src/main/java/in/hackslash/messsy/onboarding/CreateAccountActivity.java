package in.hackslash.messsy.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import in.hackslash.messsy.R;
import in.hackslash.messsy.home.HomeActivity;

public class CreateAccountActivity extends AppCompatActivity {
    //  Declare instances for all UI elements
    EditText name,email,roomno,password;
    TextView login;
    Button create_account;
    ProgressBar progress;
    AccountsUtil accountsUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        assignVariables();
        setup();
    }


    public void assignVariables() {
        //  initialize all fields here
        name=findViewById(R.id.ca_name);
        email=findViewById(R.id.ca_email);
        roomno=findViewById(R.id.ca_roomno);
        password=findViewById(R.id.ca_password);
        login=findViewById(R.id.log_in);
        create_account=findViewById(R.id.create_acc_button);
        progress=findViewById(R.id.create_acc_progress);
        accountsUtil=new AccountsUtil();
    }

    public void setup(){
        handleCreateAccount();
        handleLogin();
    }

    private void handleLogin() {
        //  define onclicklistener on Sign in button to navigate to LoginActivity and close this activity
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to login activity
                Intent intent=new Intent(CreateAccountActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void handleCreateAccount() {
        //  define onclicklistener on Save Details button to collect data from all edittexts and call ComplaintsUtil.createAccount

        //  Show an indefinite progress bar on the screen as soon as the submit button is clicked to denote that data is being processed

        //  Pass a callback by createAccount().setOnCompleteListener() to handle the result from the update operation. Show a toast according to the result and also hide the progress bar.

        //  NOTE: If the result is SUCCESS, then close this activity and navigate to HomeActivity
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                String username=name.getText().toString();
                String email_id=email.getText().toString();
                String room_no=roomno.getText().toString();
                String password_=password.getText().toString();
                User user=new User();
                user.setEmail(email_id);
                user.setName(username);
                user.setRoomno(room_no);
                accountsUtil.createAccount(user,password_).setOnCompleteListener(new AccountsUtil.onCompleteListener() {
                    @Override
                    public void onComplete(AccountsUtil.Status status, User user) {
                        if(status== AccountsUtil.Status.ALREADY_LOGGED_IN){
                            progress.setVisibility(View.INVISIBLE);
                            Toast.makeText(CreateAccountActivity.this, "Already Logged in", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateAccountActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(status== AccountsUtil.Status.WRONG_INPUT){
                            //wrong input
                            progress.setVisibility(View.INVISIBLE);
                            Toast.makeText(CreateAccountActivity.this, "wrong input", Toast.LENGTH_SHORT).show();
                        }
                        else if(status== AccountsUtil.Status.DUPLICATE_USER){
                            progress.setVisibility(View.INVISIBLE);
                            Toast.makeText(CreateAccountActivity.this, "User already exist", Toast.LENGTH_SHORT).show();
                        }
                        else if(status== AccountsUtil.Status.SUCCESS){
                            progress.setVisibility(View.INVISIBLE);
                            Toast.makeText(CreateAccountActivity.this, "account created successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateAccountActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });

    }
}
