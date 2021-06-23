package in.hackslash.messsy.home.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.hackslash.messsy.R;

//public class MealListDataAdapter extends RecyclerView.Adapter<MealListDataAdapter.ViewHolder> {
//
//    private static final String TAG = "RecyclerAdapter";
//    private ArrayList<MealData> mealList  = new ArrayList<>();
//    private Context context;
//    private mealItemListener listener;
//
//    public MealListDataAdapter(Context context, ArrayList<MealData> mealList, mealItemListener listener) {
//        this.context = context;
//        this.mealList = mealList;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        final View elementView = LayoutInflater.from(context).inflate(R.layout.meals_list_item, parent, false);
//        final ViewHolder holder=new ViewHolder(elementView);
//        elementView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                elementView.setBackgroundColor(Color.parseColor("#008577"));
////                String name=mealList.get(holder.getPosition()).getDescription();
////                String url=mealList.get(holder.getPosition()).getImageUrl();
//                listener.onItemClicked(holder.getPosition());
//            }
//        });
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        if (mealList != null && mealList.size() > 0) {
//            MealData model = mealList.get(position);
//            holder.mealname.setText(model.getName());
//            Picasso.with(context).load(model.getImageUrl()).into(holder.image);
//        } else {
//            return;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return mealList.size();
//    }
//
//
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        TextView mealname;
//        ImageView image;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            itemView.setOnClickListener(this);
//            image = itemView.findViewById(R.id.imageView_meal);
//            mealname = itemView.findViewById(R.id.textView_meal);
//        }
//
//        @Override
//        public void onClick(View view) {
//            istItemClickListener.onItemClicked(getAdapterPosition());
//        }
//    }
//    public interface mealItemListener{
//        void onItemClicked(int position);
//    }
//}

public class MealListDataAdapter extends RecyclerView.Adapter<MealListDataAdapter.ViewHolder> {


    ArrayList<MealData> mealList;
    Context context;

    public MealListDataAdapter(Context context, ArrayList<MealData> mealList) {
        this.context = context;
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meals_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (mealList != null && mealList.size() > 0) {
            MealData model = mealList.get(position);
            holder.mealname.setText(model.getName());
            Picasso.with(context).load(model.getImageUrl()).into(holder.image);
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealname, description;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView_meal);
            mealname = itemView.findViewById(R.id.textView_meal);
            description = itemView.findViewById(R.id.textView_meal);
        }
    }
}
