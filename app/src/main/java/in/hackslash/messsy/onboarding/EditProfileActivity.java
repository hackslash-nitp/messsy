package in.hackslash.messsy.onboarding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import in.hackslash.messsy.R;
import in.hackslash.messsy.complaint.ComplaintActivity;
import in.hackslash.messsy.complaint.ComplaintsUtil;
import in.hackslash.messsy.home.HomeActivity;

public class EditProfileActivity extends AppCompatActivity {
    public static final int IMAGE_CODE = 1;
    private static final String TAG = "EditProfileActivity";
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextRoomNo;
    private EditText editTextPassword;
    private Button saveButton,setProfileImg;
    private ImageButton uploadProfileImag;
    private ProgressBar progressBar;
    private ImageView profileImg;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
    StorageReference mstorageReference;
    Uri profileImgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        assignVariables();
        showProfileImg();
        progressBar.setVisibility(View.INVISIBLE);
        setup();
    }

    public void assignVariables() {
        editTextName = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);
        editTextRoomNo = findViewById(R.id.room_no);
        editTextPassword = findViewById(R.id.password);
        saveButton = findViewById(R.id.save_button);
        progressBar = findViewById(R.id.progressBar);
        profileImg = findViewById(R.id.profile_img);
        uploadProfileImag = findViewById(R.id.profile_img_upload_button);
        setProfileImg = findViewById(R.id.profile_img_set_button);
        mstorageReference= FirebaseStorage.getInstance().getReference().child("images");
    }
    private void showProfileImg(){
        if(currUser != null) {
            final String user_id = currUser.getUid();
            Task<DocumentSnapshot> userDetails = db.collection("users").document(user_id).get();
            userDetails.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String imgUrl;
                    imgUrl = documentSnapshot.contains("profileImgUrl") ? documentSnapshot.get("profileImgUrl").toString(): "";
                    if(isValidUrl(imgUrl)){
                        Glide.with(EditProfileActivity.this)
                                .load(imgUrl)
                                .error(R.mipmap.ic_launcher)
                                .apply(RequestOptions.circleCropTransform())
                                .into(profileImg);
                    }else {
                        profileImg.setImageDrawable(getDrawable(R.drawable.undraw_profile_pic));
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                    Log.d("PROFILE_FRAGMENT",e.toString());
                }
            });
        }else{
            Log.d("PROFILE_FRAGMENT","User Not Found");
        }
    }
    private static boolean isValidUrl(String url){
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }
        // If there was an Exception while creating URL object
        catch (Exception e) {
            return false;
        }
    }
    public void setup() {
        /* NOT REQUIRED TO THESE STEPS BECAUSE TASK IS DONE IN ALTERNATIVE WAY
        //  define onclicklistener on Save Details button to collect data from all edittexts and call ComplaintsUtil.updateUser
        //  Show an indefinite progress bar on the screen as soon as the submit button is clicked to denote that data is being processed

        //  Pass a callback by updateUser().setOnCompleteListener() to handle the result from the update operation. Show a toast according to the result and also hide the progress bar.

        //  NOTE: If the result is SUCCESS, then close this activity and navigate to HomeActivity
        */


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                final String name = editTextName.getText().toString();
                final String email = editTextEmail.getText().toString();
                final String roomNo = editTextRoomNo.getText().toString();
                final String password = editTextPassword.getText().toString();

                if(name.length() == 0 || roomNo.length() == 0 || email.length()==0){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(EditProfileActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"NOTHING CHANGED");
                    return;
                }
                if(password.length() < 6){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(EditProfileActivity.this, "Password's length cannot be less than 6", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"NOTHING CHANGED");
                    return;
                }

                final User user = new User(email,name,roomNo,password);

                if(currUser != null) {
                    final String user_id = currUser.getUid();
                    String oldEmail = currUser.getEmail();
                    if(!email.equals(oldEmail)){
                        // checking if new email entered is available or not
                        currUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    db.collection("users").document(user_id).set(user);
                                    progressBar.setVisibility(View.GONE);
                                    Log.d(TAG, "EMAIL UPDATED WITH OTHER DETAILS");
                                    Toast.makeText(EditProfileActivity.this, "Details Changed", Toast.LENGTH_SHORT).show();
                                    intentToHomeActivity();
                                }else{
                                    String error_code = ((FirebaseAuthException) task.getException()).getErrorCode();
                                    progressBar.setVisibility(View.GONE);
                                    if (error_code.equals("ERROR_EMAIL_ALREADY_IN_USE")) {
                                        Toast.makeText(EditProfileActivity.this, "Email Already In Use", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(EditProfileActivity.this, error_code, Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, error_code);
                                    }
                                }
                            }
                        });
                    }else{
                        db.collection("users").document(user_id).set(user);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(EditProfileActivity.this, "Details Changed", Toast.LENGTH_SHORT).show();
                        intentToHomeActivity();
                    }
                }else{
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        db.collection("users").document().set(user);
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(EditProfileActivity.this, "New User Created", Toast.LENGTH_SHORT).show();
                                        intentToHomeActivity();
                                    }else{
                                        String error_code = ((FirebaseAuthException) task.getException()).getErrorCode();
                                        progressBar.setVisibility(View.GONE);
                                        if (error_code.equals("ERROR_EMAIL_ALREADY_IN_USE")) {
                                            Toast.makeText(EditProfileActivity.this, "Email Already In Use", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(EditProfileActivity.this, error_code, Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, error_code);
                                        }
                                    }
                                }
                            });
                }
            }
        });
        setProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateImg();
                finish();
            }
        });
        uploadProfileImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent().setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Complete action using"), IMAGE_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_CODE && resultCode == RESULT_OK){
            progressBar.setVisibility(View.VISIBLE);
            assert data != null;
            Uri selected = data.getData();
            StorageReference photoRef = mstorageReference.child(selected.getLastPathSegment());

            photoRef.putFile(selected).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                     profileImgUrl = urlTask.getResult();
                    String imgUrl = String.valueOf(profileImgUrl);
                    if(isValidUrl(imgUrl)){
                        Glide.with(EditProfileActivity.this)
                                .load(imgUrl)
                                .error(R.mipmap.ic_launcher)
                                .apply(RequestOptions.circleCropTransform())
                                .into(profileImg);
                    }
                    setProfileImg.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }
    private void updateImg(){
        final String user_id = currUser.getUid();
        DocumentReference docRef = db.collection("users").document(user_id);
        Map<String, Object> map =new HashMap<>();
        map.put("profileImgUrl",String.valueOf(profileImgUrl));
        docRef.update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditProfileActivity.this, "Profile Image Updated", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.d(TAG, "onFailure: ",e);
                    }
                });
    }
    void intentToHomeActivity(){
        Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
