package in.hackslash.messsy.home;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.hackslash.messsy.R;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bnv=findViewById(R.id.btm_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new HomeFragment()).commit();

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment temp=null;
                switch (item.getItemId())
                {
                    case R.id.menu_home:
                        temp= new HomeFragment();
                        break;
                    case R.id.menu_qrCode:
                        temp= new QRFragment();
                        break;
                    case R.id.menu_complaint:
                        temp= new ComplaintFragment();
                        break;
                    case R.id.menu_profile:
                        temp= new ProfileFragment();

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,temp).commit();

                return true;
            }
        });
    }
}
