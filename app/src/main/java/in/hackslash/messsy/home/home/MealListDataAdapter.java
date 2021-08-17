package in.hackslash.messsy.home.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.hackslash.messsy.R;

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
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mealList != null && mealList.size() > 0) {
            MealData model = mealList.get(position);
            holder.mealname.setText(model.getName());
            Picasso.with(context).load(model.getImageUrl()).into(holder.image);
            holder.card.setCardBackgroundColor(context.getResources().getColor(model.getCardColor()));
            holder.mealname.setTextColor(context.getResources().getColor(model.textColor));
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
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card=itemView.findViewById(R.id.meal_card);
            image = itemView.findViewById(R.id.imageView_meal);
            mealname = itemView.findViewById(R.id.textView_meal);
            description = itemView.findViewById(R.id.textView_meal);
        }
    }
}

