package in.hackslash.messsy.payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import in.hackslash.messsy.R;

public class PaymentMenuAdapter extends RecyclerView.Adapter<PaymentMenuAdapter.MenuHolder> {
    ArrayList<MenuModel> list;
    Context context;
    OnMenuTouch listener;

    public PaymentMenuAdapter(ArrayList<MenuModel> list, Context context,OnMenuTouch listener) {
        this.list = list;
        this.context = context;
        this.listener=listener;
    }

    @NonNull
    @NotNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_menu_element,parent,false);
        final MenuHolder holder=new MenuHolder(view);
        holder.plus.setOnClickListener(v->{
            listener.onPlusClicked(holder.getAdapterPosition());
        });
        holder.minus.setOnClickListener(v -> {
            listener.onMinusClicked(holder.getAdapterPosition());
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PaymentMenuAdapter.MenuHolder holder, int position) {
        MenuModel model=list.get(position);
        holder.title.setText(model.getName());
        holder.price.setText("Price: "+model.getPrice());
        holder.quantity.setText(model.getQuantity()+"");
        Glide.with(context).load(model.getImage())
                .placeholder(R.drawable.scan).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MenuHolder extends RecyclerView.ViewHolder {
        TextView title,quantity,price;
        Button plus,minus;
        ImageView image;
        public MenuHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.element_title);
            quantity=itemView.findViewById(R.id.element_quantity);
            plus=itemView.findViewById(R.id.element_plus);
            minus=itemView.findViewById(R.id.element_minus);
            image=itemView.findViewById(R.id.element_img);
            price=itemView.findViewById(R.id.element_price);
        }
    }
}
