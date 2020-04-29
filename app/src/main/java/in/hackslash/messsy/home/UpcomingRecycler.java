package in.hackslash.messsy.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.hackslash.messsy.R;

public class UpcomingRecycler extends RecyclerView.Adapter<UpcomingRecycler.ViewHolder> {



    // Use upcoming_meal.xml to define the layout of single item of the list and remove this comment when done
    @NonNull
    @Override
    public UpcomingRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_meal, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}