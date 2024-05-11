package com.example.tripmanage.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripmanage.GroupActivity;
import com.example.tripmanage.MainActivity;
import com.example.tripmanage.R;
import com.example.tripmanage.model.GroupModel;

import java.io.Serializable;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.Viewholder> {

    Context context;
    List<GroupModel>list;

    public GroupAdapter(Context context, List<GroupModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GroupAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.group_list_item,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.Viewholder holder, int position) {
        holder.group_text_list.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.groupModel = list.get(position);
               Intent intent=new Intent(context, GroupActivity.class);
               context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView group_list_img;
        TextView group_text_list;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            group_list_img=itemView.findViewById(R.id.group_list_img);
            group_text_list=itemView.findViewById(R.id.group_text_list);
        }
    }
}
