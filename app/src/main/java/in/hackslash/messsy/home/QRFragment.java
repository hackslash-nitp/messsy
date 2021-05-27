package in.hackslash.messsy.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.type.DateTime;
import com.google.zxing.WriterException;

import java.io.ByteArrayOutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import in.hackslash.messsy.R;

public class QRFragment extends Fragment {
    StorageReference QRstorageReference;
    ImageView img;
    TextView txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_q_r, container, false);
        txt=rootView.findViewById(R.id.nameID);
        txt.setText(HomeFragment.userName);
        img=rootView.findViewById(R.id.imageViewQR);
        QRstorageReference= FirebaseStorage.getInstance().getReference().child("QRcode");
        gen();

        return rootView;
    }
    private void gen(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        if(img.getDrawable()==null) {
            QRGEncoder qrgEncoder = new QRGEncoder(email, null, QRGContents.Type.TEXT, 300);
            Bitmap QRBits = qrgEncoder.getBitmap();
            img.setImageBitmap(QRBits);
        }

    }
}