package in.hackslash.messsy.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import in.hackslash.messsy.AbsenceNotification;
import in.hackslash.messsy.R;
import in.hackslash.messsy.complaint.Notice;
import in.hackslash.messsy.complaint.NoticeActivity;
import in.hackslash.messsy.onboarding.LoginActivity;
import in.hackslash.messsy.vote.Vote;
import in.hackslash.messsy.vote.VoteActivity;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bnv;
    private DrawerLayout drawer;

    String name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bnv = findViewById(R.id.btm_nav);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new HomeFragment()).commit();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(this);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment temp = null;
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        temp = new HomeFragment();
                        break;
                    case R.id.menu_qrCode:
                        temp = new QRFragment();
                        break;
                    case R.id.menu_complaint:
                        temp = new ComplaintFragment();
                        break;
                    case R.id.menu_profile:
                        temp = new ProfileFragment();

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, temp).commit();

                return true;
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_absence:
                startActivity(new Intent(HomeActivity.this, AbsenceNotification.class));
                break;

            case R.id.menu_notice:
                startActivity(new Intent(HomeActivity.this, NoticeActivity.class));
                break;

            case R.id.menu_payment:
                Toast.makeText(this, "payement done", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_change:
                startActivity(new Intent(HomeActivity.this, VoteActivity.class));
                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
        }
        return true;
    }
}
