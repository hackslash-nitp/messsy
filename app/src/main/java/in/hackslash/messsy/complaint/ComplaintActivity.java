package in.hackslash.messsy.complaint;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import in.hackslash.messsy.R;

public class ComplaintActivity extends AppCompatActivity {

    // TODO Declare instances for all UI elements
    EditText issue;
    EditText des;
    ImageView imgView;
    private static final int IMAGE_CODE=1;
    Button upload,submit;
    Uri downloadURL;
    StorageReference mstorageReference;
    FirebaseFirestore dbs = FirebaseFirestore.getInstance();
    private final CollectionReference complaintRef = dbs.collection("ComplaintSection");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        issue=findViewById(R.id.line_1);
        des=findViewById(R.id.line_2);
        upload=findViewById(R.id.upload_button);
        submit=findViewById(R.id.submit_button);
        mstorageReference= FirebaseStorage.getInstance().getReference().child("images");


        setup();
    }



    public void setup() {
        // TODO define onclicklistener on File Complaints button to collect data from all edittexts and call ComplaintsUtil.createComplaint
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            f(v);

            }
        });
        // TODO Show an indefinite progress bar on the screen as soon as the submit button is clicked to denote that data is being processed
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent().setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Complete action using"), IMAGE_CODE);


            }
        });
        // TODO Pass a callback by createComplaint().setOnCompleteListener() to handle the result from the create operation. Show a toast according to the result and also hide the progress bar.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_CODE&& resultCode==RESULT_OK){
            assert data != null;
            Uri selected=data.getData();
            try {
                Bitmap currentImage = MediaStore.Images.Media.getBitmap
                        (this.getContentResolver(), selected);


                imgView.setImageBitmap(currentImage);
            }
            catch (Exception e){
                e.printStackTrace();
            }
                StorageReference photoRef = mstorageReference.child(selected.getLastPathSegment());
            Toast.makeText(ComplaintActivity.this,"Loading",Toast.LENGTH_SHORT).show();

            photoRef.putFile(selected).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                     downloadURL = urlTask.getResult();



                }

            });
        }
    }
    public void f(View v){
        String s = String.valueOf(downloadURL);
        String s1=issue.getText().toString();
        String s2=des.getText().toString();
        Complaint complaint=new Complaint(s1,s2,s);
        complaintRef.add(complaint);

    }
}
