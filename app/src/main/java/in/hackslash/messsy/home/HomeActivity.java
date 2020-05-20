package in.hackslash.messsy.home;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import in.hackslash.messsy.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView upcomingRecycler;
    ArrayList<CardViewModel> model;
    UpcomingRecycler adapter;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        upcomingRecycler = (RecyclerView)findViewById(R.id.upcoming_meals);

        int[] image = {R.drawable.boiled_eggs, R.drawable.bread, R.drawable.milk};
        String[] name = {"Boled eggs", "Bread", "Milk"};

        model = new ArrayList<>();
        for(int i = 0; i<image.length; i++){
            CardViewModel model1 = new CardViewModel(image[i], name[i]);
            model.add(model1);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                HomeActivity.this, LinearLayoutManager.HORIZONTAL, false
        );
        upcomingRecycler.setLayoutManager(layoutManager);
        upcomingRecycler.setItemAnimator(new DefaultItemAnimator());

        adapter = new UpcomingRecycler(model, HomeActivity.this);

        upcomingRecycler.setAdapter(adapter);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
