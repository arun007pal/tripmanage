package com.example.tripmanage.adapter;

import static com.example.tripmanage.MainActivity.groupModel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripmanage.GroupActivity;
import com.example.tripmanage.ItemActivity;
import com.example.tripmanage.MainActivity;
import com.example.tripmanage.R;
import com.example.tripmanage.model.Expense_Model;
import com.example.tripmanage.model.Member_Model;

import java.util.List;
import java.util.zip.Inflater;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.viewHolder> {
    Context context;
    List<Member_Model>list;
    double totalamount=0,totalmember = 0;

    public MemberAdapter(Context context, List<Member_Model> list) {
        this.context = context;
        this.list = list;
        for (Member_Model memberModel : groupModel.getList()) {
            for (Expense_Model expense : memberModel.getList()) {
                totalamount += expense.getAmount();
            }
            totalmember++;
        }
    }

    @NonNull
    @Override
    public MemberAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.member_list,parent,false);

        return  new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.viewHolder holder, int position) {
        holder.user_member_list.setText(list.get(position).getUsername());
        double sum = 0.0;
        List<Expense_Model> expenses = list.get(position).getList();
        for (Expense_Model expense : expenses) {
            sum += expense.getAmount();
        }
        GroupActivity.memberModel = list.get(position);
        holder.spent_amount_list.setText("amt:"+sum);
//        holder.user_member_list.setText(list.get(position).getName());
   //     holder.item_name_list.setText(list.get(position).);

       holder.recv_amount_list.setText("rcv:"+(sum-(totalamount/totalmember)));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ItemActivity.class);
                context.startActivity(intent.putExtra("pos",holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView member_list_img;
        TextView user_member_list,spent_amount_list,recv_amount_list,item_name_list;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            member_list_img=itemView.findViewById(R.id.member_list_img);
            user_member_list=itemView.findViewById(R.id.user_member_list);
            spent_amount_list=itemView.findViewById(R.id.spent_amount_list);
            recv_amount_list=itemView.findViewById(R.id.recv_amount_list);
            item_name_list=itemView.findViewById(R.id.item_name_list);
        }
    }
}
