package in.hackslash.messsy.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import in.hackslash.messsy.R;
import in.hackslash.messsy.onboarding.EditProfileActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView profileImage;
    private TextView name,email,roomNo;
    private Button editProfileBtn, viewCouponBtn;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View item = inflater.inflate(R.layout.fragment_profile, container, false);
        // Assigning Variables
        profileImage = item.findViewById(R.id.profile_img);
        name = item.findViewById(R.id.name);
        email = item.findViewById(R.id.email);
        roomNo = item.findViewById(R.id.Room_no);
        editProfileBtn = item.findViewById(R.id.edit_profile_btn);
        viewCouponBtn = item.findViewById(R.id.view_coupon_btn);
        //
        editProfileBtn.setOnClickListener(this);
        viewCouponBtn.setOnClickListener(this);
        loadUserDetails();
        return item;
    }

    private void loadUserDetails() {
        if(currUser != null) {
            final String user_id = currUser.getUid();
            Task<DocumentSnapshot> userDetails = db.collection("users").document(user_id).get();
            userDetails.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String uName,uEmail,uRoomNo,urlProfileImage;
                    urlProfileImage = documentSnapshot.contains("profileImgUrl") ? documentSnapshot.get("profileImgUrl").toString(): "";
                    if(isValidUrl(urlProfileImage)){
                        Glide.with(getActivity())
                                .load(urlProfileImage)
                                .error(R.mipmap.ic_launcher)
                                .apply(RequestOptions.circleCropTransform())
                                .into(profileImage);
                    }else Toast.makeText(getActivity(),"Profile Pic Not Found",Toast.LENGTH_LONG).show();
                    uName = documentSnapshot.contains("name") ? documentSnapshot.get("name").toString(): "NOT FOUND";
                    uEmail = documentSnapshot.contains("email") ? documentSnapshot.get("email").toString() : "NOT FOUND" ;
                    uRoomNo =  "Room No. " + (documentSnapshot.contains("roomno") ? documentSnapshot.get("roomno").toString() : "NOT FOUND");
                    name.setText(uName);
                    email.setText(uEmail);
                    roomNo.setText(uRoomNo);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_profile_btn:
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
                break;
            case R.id.view_coupon_btn:
                Toast.makeText(getActivity(), "Not Available Right Now", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}