package in.hackslash.messsy.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.hackslash.messsy.R;

public class UpcomingRecycler extends RecyclerView.Adapter<UpcomingRecycler.ViewHolder> {

    ArrayList<CardViewModel> model;
    Context mContext;

    public UpcomingRecycler(ArrayList<CardViewModel> model, Context context) {
        this.model = model;
        mContext = context;
    }

    @NonNull
    @Override
    public UpcomingRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_upcoming_meal_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mImageView.setImageResource(model.get(position).getFoodImage());
        holder.mTextView.setText(model.get(position).getNameFood());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.upcomingcard_pic);
            mTextView = itemView.findViewById(R.id.upcomingcard_name);
        }
    }
}
