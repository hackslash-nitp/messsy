package in.hackslash.messsy.home;


import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.hackslash.messsy.R;

public class HomeActivity extends AppCompatActivity {

    RecyclerView upcomingRecycler;
    ArrayList<CardViewModel> model;
    UpcomingRecycler adapter;

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
    }
}
