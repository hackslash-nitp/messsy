package in.hackslash.messsy.home;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.hackslash.messsy.R;

public class HomeActivity extends AppCompatActivity {


    RecyclerView upcoming_meals_recycler_view;
    private UpcomingRecycler mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        upcoming_meals_recycler_view = (RecyclerView)findViewById(R.id.meals_recycler);

        mAdapter = new UpcomingRecycler();

        upcoming_meals_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        upcoming_meals_recycler_view.setItemAnimator(new DefaultItemAnimator());

        upcoming_meals_recycler_view.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));

        upcoming_meals_recycler_view.setAdapter(mAdapter);
    }
}
