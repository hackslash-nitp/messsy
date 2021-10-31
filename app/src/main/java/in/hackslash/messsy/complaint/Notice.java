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

    ArrayList<NoticeBoxAdapterClass> arrayList = new ArrayList<>();
 Notice( NoticeActivity noticeActivity){

}
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_notice_adapter_layout, parent, false);
        viewholder v1 = new viewholder(view);

        return v1;
    }

     void updatefood(ArrayList<NoticeBoxAdapterClass> f){
//         arrayList.clear();
         arrayList.addAll(f);
         notifyDataSetChanged();
     }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.subject.setText(arrayList.get(position).getSubject());
        holder.description.setText(arrayList.get(position).getDescription());
        holder.hostelMessName.setText(arrayList.get(position).getHostel() + " " + arrayList.get(position).getDesignation());
        String hostel = arrayList.get(position).getHostel().toString();
        if("brahmputra".equalsIgnoreCase(hostel))
        {
            holder.imageView.setImageResource(R.drawable.brahmaputra_hostel_icon);
        }
        else if("kosi".equalsIgnoreCase(hostel))
        {
            holder.imageView.setImageResource(R.drawable.kosi_icon);
        }
        else if("ganga".equalsIgnoreCase(hostel))
        {
            holder.imageView.setImageResource(R.drawable.ganga_hostel_icon);
        }
        else if("sone".equalsIgnoreCase(hostel))
        {
            holder.imageView.setImageResource(R.drawable.sone_icon);
        }
        else
        {
            holder.imageView.setImageResource(R.drawable.admin_profile_fragment_img);
        }
        holder.date.setText(arrayList.get(position).getDate());
        holder.time.setText(arrayList.get(position).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();

    }

    // TODO Add notice fields here
}
    class viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView hostelMessName, description, date, time, subject;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageIcon);
            hostelMessName = itemView.findViewById(R.id.hostelMessName);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.timeDisplay);
            subject = itemView.findViewById(R.id.subject);
        }
    }

