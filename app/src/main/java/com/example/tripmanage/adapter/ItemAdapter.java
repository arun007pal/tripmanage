package com.example.tripmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripmanage.R;
import com.example.tripmanage.model.Expense_Model;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.viewHolder> {
    Context context;
    List<Expense_Model>list;

    public ItemAdapter(Context context, List<Expense_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);

       return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.viewHolder holder, int position) {

        holder.item_list.setText(list.get(position).getItemname());
        holder.item_balance.setText(""+list.get(position).getAmount());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView item_list,item_balance;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            item_list=itemView.findViewById(R.id.item_list);
            item_balance=itemView.findViewById(R.id.item_balance);
        }
    }
}
