package com.example.tripmanage.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tripmanage.R;
import com.example.tripmanage.model.Member_Model;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<Member_Model> {

    private Context mContext;
    private List<Member_Model> mMembers;
    public int Selectedpos=0;

    public CustomSpinnerAdapter(Context context, List<Member_Model> members) {
        super(context, R.layout.spinner_item_layout, members); // Use custom layout
        mContext = context;
        mMembers = members;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(mContext).inflate(R.layout.spinner_item_layout, parent, false);
        }

        Member_Model member = mMembers.get(position);
        Selectedpos=position;
        TextView textView = listItemView.findViewById(R.id.spinner_item_text); // Use custom TextView ID
        textView.setText(member.getUsername());

        return listItemView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        // Get the current item from the list
        Member_Model member = mMembers.get(position);

        // Set the text for the item in the spinner
        TextView textViewItem = convertView.findViewById(android.R.id.text1);
        textViewItem.setTextColor(Color.WHITE);
        textViewItem.setText(member.getUsername());

        return convertView;
    }
}

