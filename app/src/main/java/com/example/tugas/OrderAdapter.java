package com.example.tugas;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private ArrayList<Order> orderList;
    private static RVClickListener myListener;
    public OrderAdapter(ArrayList<Order> orderList,RVClickListener rvcl)
    {
        this.orderList=orderList;
        myListener=rvcl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View v=inflater.inflate(R.layout.order,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvQty.setText(orderList.get(i).getQty()+" "+ orderList.get(i).getType());
        viewHolder.tvToppings.setText(orderList.get(i).getToppings().toString());
        viewHolder.tvTotal.setText(orderList.get(i).getSubtotal()+"");

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvQty,tvToppings,tvTotal;
        public ViewHolder(@NonNull final View itemView){
            super(itemView);
            tvQty=itemView.findViewById(R.id.textView_qty_type);
            tvToppings=itemView.findViewById(R.id.textView_toppings);
            tvTotal=itemView.findViewById(R.id.textView_subtotal);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myListener.recyclerViewClick(v, ViewHolder.this.getLayoutPosition());
                }
            });
        }
    }
}
