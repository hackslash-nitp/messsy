package in.hackslash.messsy.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import in.hackslash.messsy.R;
import in.hackslash.messsy.home.home.MealData;
import in.hackslash.messsy.home.home.MealListDataAdapter;

public class HomeFragment extends Fragment{
    private static final String TAG = "HomeFrag";

    public static String userName;
    private RecyclerView mealListrecyclerView;
    private MealListDataAdapter adapter;
    private ImageView image, foodimg;
    private TextView name, mealDescription ,mealNext, foodnametv, fooddesctv;
    private FirebaseFirestore firestore;
    private CollectionReference mealsReference;
    private final ArrayList<MealData> mealList = new ArrayList<>();
    private String descriptiontext, mealNextType, mealSelect,time = "Good ";
    String mealTimeArray[] = {"Select meal type","breakfast", "lunch", "dinner"};
    Spinner mealSpinner;

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

        name=item.findViewById(R.id.name_tv);
        image = item.findViewById(R.id.imageView_meal);
        mealNext = item.findViewById(R.id.meal_next_button);
        mealSpinner = item.findViewById(R.id.spinner);

        foodnametv = item.findViewById(R.id.food_name_tv);
        fooddesctv = item.findViewById(R.id.food_description);
        foodimg = item.findViewById(R.id.food_img);

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        int hours = Integer.parseInt(currentTime.substring(0, 2));
        final String mealType = (hours > 18 && hours < 24 || hours < 4) ? "dinner" : (hours < 18 && hours > 12) ? "lunch" : "breakfast";
        final String day = currentDay();

        switch (mealType) {
            case "breakfast":
                mealNextType = "lunch";
                time += "morning";
                break;
            case "lunch":
                mealNextType = "dinner";
                time += "afternoon";

                break;
            case "dinner":
                mealNextType = "lunch";
                time += "evening";
                break;
        }

        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users").document(uid).get().
                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        time +=" "+documentSnapshot.getString("name");
                        name.setText(time);
                        userName=documentSnapshot.getString("name");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });


        mealNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), mealNextType, Toast.LENGTH_SHORT).show();
                fetchMealData(mealNextType,day);
            }
        });
        adapter = new MealListDataAdapter(getContext(), mealList);
        mealListrecyclerView.setAdapter(adapter);
        fetchMealData(mealType, day);
        setupMealSpinner(mealSpinner);
        return item;
    }

    private String currentDay(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        return day;
    }
    private void fetchMealData(String meal, String day) {

        mealList.clear();
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("MealListData")
                .document("Sunday").collection(meal).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    {
                        MealData mealData = snapshot.toObject(MealData.class);
                        if (snapshot.exists()) {
                            String mealname = mealData.getName();
                            String description = mealData.getDescription();
                            String imageUrl = mealData.getImageUrl();
                            mealList.add(new MealData(imageUrl, description, mealname));
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

//    @Override
//    public void onItemClicked(int position) {
//        Log.d(TAG, "Clicked"+position);
//        foodnametv.setText(mealList.get(position).getName());
//        fooddesctv.setText(mealList.get(position).getDescription());
//        Picasso.with(getContext()).load(mealList.get(position).getImageUrl()).into(foodimg);
//    }
    private void setupMealSpinner(Spinner mealSpinner) {
        ArrayAdapter<String> mealSpinnerAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mealTimeArray);
        mealSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealSpinner.setAdapter(mealSpinnerAdapter);
        mealSpinner.setOnItemSelectedListener(new MealSpinner());
    }

    private class MealSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            mealSelect= mealTimeArray[i];
            String day = currentDay();
            fetchMealData(mealSelect,day);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
