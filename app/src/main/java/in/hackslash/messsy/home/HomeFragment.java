package in.hackslash.messsy.home;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import in.hackslash.messsy.R;
import in.hackslash.messsy.home.home.MealData;
import in.hackslash.messsy.home.home.MealListDataAdapter;


public class HomeFragment extends Fragment {

    RecyclerView mealListrecyclerView;
    MealListDataAdapter adapter;
    String descriptiontext;
    ImageView image;
    TextView mealDescription;
    TextView mealNext;
    String mealNextType;
    FirebaseFirestore firestore;
    CollectionReference mealsReference;
    ArrayList<MealData> mealList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View item = inflater.inflate(R.layout.fragment_home, container, false);
        mealListrecyclerView = item.findViewById(R.id.meals_list);
        mealListrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

//        firestore = FirebaseFirestore.getInstance();
        image = item.findViewById(R.id.imageView_meal);
        mealDescription = item.findViewById(R.id.textView_meal);
        mealNext = item.findViewById(R.id.meal_next_button);
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        int hours = Integer.parseInt(currentTime.substring(0, 2));
        final String mealType = (hours > 18 && hours < 24) ? "dinner" : (hours < 18 && hours > 12) ? "lunch" : "breakfast";

        if (mealType.equals("breakfast")) {
            mealNextType = "lunch";
        } else if (mealType.equals("lunch")) {
            mealNextType = "dinner";
        } else if (mealType.equals("dinner")) {
            mealNextType = "breakfast";
        }

//        //dummy data
//        String url = "https://images.unsplash.com/photo-1482049016688-2d3e1b311543?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=653&q=80";
//        descriptiontext = "Meal";
//        mealList.add(new MealData(descriptiontext,url));
//        adapter = new MealListDataAdapter(getContext(),mealList);
//        mealListrecyclerView.setAdapter(adapter);

        mealNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), mealNextType, Toast.LENGTH_SHORT).show();
                mealList.clear();
                fetchMealData(mealNextType);
            }
        });

        fetchMealData(mealType);
        return item;
    }

    private void fetchMealData(String meal) {

        firestore = FirebaseFirestore.getInstance();
        firestore.collection("MealListData")
                .document("Hyoa6CRWu7F2s5ljhbYb").collection(meal).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    {
                        MealData mealData = snapshot.toObject(MealData.class);
                        if (snapshot.exists()) {
                            String description = mealData.getDescription();
                            String imageUrl = mealData.getImageUrl();
                            mealList.add(new MealData(imageUrl, description));
                        }
                    }
                }
                adapter = new MealListDataAdapter(getContext(), mealList);
                mealListrecyclerView.setAdapter(adapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}