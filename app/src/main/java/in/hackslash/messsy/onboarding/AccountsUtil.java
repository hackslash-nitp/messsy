package in.hackslash.messsy.onboarding;


import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AccountsUtil {

    private onCompleteListener onCompleteListener;
    FirebaseUser currentUser;

    public AccountsUtil createAccount(final User user, String password) {

        String email = user.getEmail();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        //  Check if a user is logged in and return ALREADY_LOGGED_IN if a logged in user is found
        if (currentUser != null) {
            onCompleteListener.onComplete(Status.ALREADY_LOGGED_IN, user);
        }
        //  Validate user here and return WRONG_INPUT if anything is wrong
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || password.length() < 6
                               || user.getName().length()==0 || user.getRoomno().length()==0) {
            onCompleteListener.onComplete(Status.WRONG_INPUT, user);
        }
        //  Check DB if the user exists and return DUPLICATE_USER if user exists

        //  Create new account using createUserWithEmailAndPassword and return SUCCESS if successful
        else {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //user account created
                                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                String userid=currentUser.getUid();
                                String name=user.getName();
                                String rooomno=user.getRoomno();
                                String email=user.getEmail();
                                HashMap<String,Object> map=new HashMap<>();
                                map.put("name",name);
                                map.put("roomno",rooomno);
                                map.put("email",email);
                                map.put("balance",2000);
                                firestore.collection("users").document(userid)
                                        .set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            // user data written on DB
                                            onCompleteListener.onComplete(Status.SUCCESS, user);
                                        }
                                    }
                                });
                            } else {
                                String error_code = ((FirebaseAuthException) task.getException()).getErrorCode();
                                if (error_code.equals("ERROR_EMAIL_ALREADY_IN_USE")) {
                                    onCompleteListener.onComplete(Status.DUPLICATE_USER, user);
                                }
                            }
                        }
                    });
        }


        // Leave as it is
        return this;
    }

    public AccountsUtil loginUser(final User user, final String password) {

        final String email = user.getEmail();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        //  Check if a user is logged in and return ALREADY_LOGGED_IN if a logged in user is found
        if (currentUser != null) {
            onCompleteListener.onComplete(Status.ALREADY_LOGGED_IN, user);
        }
        //  Validate user here and return WRONG_INPUT if anything is wrong
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || password.length() < 6) {
            onCompleteListener.onComplete(Status.WRONG_INPUT, user);
        }
        //  Check DB if the user exists and return USER_DOES_NOT_EXIST if user is not found

        //  Login using the credentials and return SUCCESS if successful

        else{
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        // user logged in
                        // checking user data in DB
                        String user_id=FirebaseAuth.getInstance().getCurrentUser().getUid();
                        firestore.collection("users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot doc=task.getResult();
                                    if(doc.exists()){
                                        //user exist
                                        onCompleteListener.onComplete(Status.SUCCESS,user);
                                    }
                                    else{
                                        onCompleteListener.onComplete(Status.USER_DOES_NOT_EXIST,user);
                                    }
                                }
                            }
                        });
                    }
                    else{
                        Log.d("AccountsUtil", "onComplete: "+"login failed");
                    }
                }
            });
        }

        // Leave as it is
        return this;
    }

    public AccountsUtil updateUser(final User user, final String password) {

        // TODO Check DB if the user exists and return USER_DOES_NOT_EXIST if user is not found

        // TODO Check if the new details match with the DB and return DUPLICATE_USER if found same

        // TODO Validate user here and return WRONG_INPUT if anything is wrong

        // TODO Update the user data on DB as well as Auth if required and return SUCCESS
        final FirebaseUser currentuser=FirebaseAuth.getInstance().getCurrentUser();
        final String user_id=currentuser.getUid();
        final FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        FirebaseFirestore.getInstance().collection("users").document(user_id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc=task.getResult();
                            if(doc.exists()){
                                //user exist
                                String name_n=user.getName();
                                String roomno_n=user.getRoomno();
                                String email_n=user.getEmail();
                                String name_old=doc.getString("name");
                                String roomno_old=doc.getString("roomno");
                                String email_old=currentuser.getEmail();
                                if(roomno_n.length()==0 || name_n.length()==0){
                                    onCompleteListener.onComplete(Status.WRONG_INPUT,user);
                                }
                                else if(roomno_n.equals(roomno_old) || name_n.equals(name_old)){
                                    onCompleteListener.onComplete(Status.DUPLICATE_USER,user);
                                }
                                else{
                                    HashMap<String,Object> map=new HashMap<>();
                                    map.put("name",name_n);
                                    map.put("roomno",roomno_n);
                                    firestore.collection("users").document(user_id).update(map);
                                    // if new email provided then updating it
                                    if(!email_n.equals(email_old)){
                                        currentuser.updateEmail(email_n).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Log.d("AccountsUtil", "email updated ");
                                                }
                                            }
                                        });
                                    }
                                    if(password.length()>=6){
                                        currentuser.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Log.d("AccountsUtil", "password updated");
                                            }
                                        });
                                    }
                                    onCompleteListener.onComplete(Status.SUCCESS,user);
                                }
                            }
                            else{
                                onCompleteListener.onComplete(Status.USER_DOES_NOT_EXIST,user);
                            }
                        }
                    }
                });
        // Leave as it is
        return this;

    }

    public AccountsUtil fetchUser(final String userID) {

        // TODO Check DB if the user exists and return USER_DOES_NOT_EXIST if user is not found

        // TODO Return the user in a User object if found with a SUCCESS Status

        FirebaseFirestore.getInstance().collection("users").document(userID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc=task.getResult();
                            if(doc.exists()){
                                //user exist
                                User user=doc.toObject(User.class);
                                onCompleteListener.onComplete(Status.SUCCESS,user);
                            }
                            else{
                                User user=new User();
                                onCompleteListener.onComplete(Status.USER_DOES_NOT_EXIST,user);
                            }
                        }
                    }
                });
        return this;
    }

    public void setOnCompleteListener(AccountsUtil.onCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public interface onCompleteListener {
        void onComplete(Status status, User user);
    }

    public enum Status {
        SUCCESS, DUPLICATE_USER, WRONG_INPUT, ALREADY_LOGGED_IN, USER_DOES_NOT_EXIST
    }
}

