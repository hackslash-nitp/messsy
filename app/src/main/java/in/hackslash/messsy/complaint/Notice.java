package in.hackslash.messsy.complaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import in.hackslash.messsy.R;

 public class Notice extends RecyclerView.Adapter<viewholder> {

    ArrayList<String> arrayList = new ArrayList<>();
 Notice( NoticeActivity noticeActivity){

}
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        viewholder v1 = new viewholder(view);

        return v1;
    }
     void updatefood(ArrayList<String> f){
//         arrayList.clear();
         arrayList.addAll(f);
         notifyDataSetChanged();
     }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        String currentItem = arrayList.get(position);

        holder.notice.setText(currentItem);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();

    }

    // TODO Add notice fields here
}
    class viewholder extends RecyclerView.ViewHolder{
        TextView notice;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            notice=itemView.findViewById(R.id.text_view);
        }
    }


