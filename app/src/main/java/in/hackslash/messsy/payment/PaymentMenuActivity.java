package in.hackslash.messsy.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.hackslash.messsy.R;

public class PaymentMenuActivity extends AppCompatActivity implements OnMenuTouch{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    ArrayList<MenuModel> menuList;
    PaymentMenuAdapter adapter;
    TextView amt;
    Button payBtn;
    private int amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_menu);
        setup();
        payBtn.setOnClickListener(v->{
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(PaymentMenuActivity.this,FinalPaymentActivity.class);
            intent.putExtra("balance",amount);
            startActivity(intent);
            finish();
        });
    }

    private void setup() {
        recyclerView=findViewById(R.id.mess_pay_chart);
        amt=findViewById(R.id.total_amt);
        payBtn=findViewById(R.id.pay_meal);
        manager=new LinearLayoutManager(this);
        menuList=fetchData();
        adapter=new PaymentMenuAdapter(menuList,this, PaymentMenuActivity.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<MenuModel> fetchData() {
        ArrayList<MenuModel> list=new ArrayList<>();
        list.add(new MenuModel("Dahi","null",0,10));
        list.add(new MenuModel("Rasgulla","null",0,12));
        list.add(new MenuModel("Mushroom","null",0,35));
        list.add(new MenuModel("Omelette","null",0,24));
        list.add(new MenuModel("Boiled egg","null",0,10));
        list.add(new MenuModel("Maggi","null",0,20));
        list.add(new MenuModel("Milk","null",0,20));
        list.add(new MenuModel("Mushroom","null",0,35));
        list.add(new MenuModel("Omelette","null",0,24));
        list.add(new MenuModel("Boiled egg","null",0,10));
        list.add(new MenuModel("Maggi","null",0,20));
        return list;
    }

    @Override
    public void onPlusClicked(int position) {
        MenuModel model=menuList.get(position);
        if(model.getQuantity()<10)
        {
            amount+=model.getPrice();
            amt.setVisibility(View.VISIBLE);
            amt.setText("Total: "+amount);
            menuList.get(position).setQuantity(model.getQuantity()+1);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onMinusClicked(int position) {
        MenuModel model=menuList.get(position);
        if(model.getQuantity()>0)
        {
            amount-=model.getPrice();
            amt.setText("Total: "+amount);
            if(amount==0)
            {
                amt.setVisibility(View.GONE);
            }
            menuList.get(position).setQuantity(model.getQuantity()-1);
            adapter.notifyDataSetChanged();
        }
        else
        {
            if(amount==0) {
                amt.setVisibility(View.GONE);
            }
        }
    }
}