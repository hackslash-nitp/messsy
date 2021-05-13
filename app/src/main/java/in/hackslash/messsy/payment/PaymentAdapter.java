package in.hackslash.messsy.payment;

import android.app.Activity;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import in.hackslash.messsy.R;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    final ArrayList<Map<String,Object>> paymentList;
    public PaymentAdapter(ArrayList<Map<String,Object>> paymentList) {
        this.paymentList = paymentList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView date,session,status;
        public ViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.date);
            session = view.findViewById(R.id.session);
            status = view.findViewById(R.id.status);
        }
        public TextView getDateView(){
            return date;
        }
        public TextView getSessionView(){
            return session;
        }
        public TextView getStatusView(){
            return status;
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment_details,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position){
        Timestamp timestamp = (Timestamp) paymentList.get(position).get("DATE");
        viewHolder.getDateView().setText(timestamp.toDate().toString());
        viewHolder.getSessionView().setText(paymentList.get(position).get("SESSION").toString());
        viewHolder.getStatusView().setText(paymentList.get(position).get("STATUS").toString());
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }
}
