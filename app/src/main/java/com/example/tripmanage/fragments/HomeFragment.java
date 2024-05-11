package com.example.tripmanage.fragments;

import static com.example.tripmanage.MainActivity.groupModel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tripmanage.GroupActivity;
import com.example.tripmanage.adapter.GroupAdapter;
import com.example.tripmanage.R;
import com.example.tripmanage.model.Expense_Model;
import com.example.tripmanage.model.GroupModel;
import com.example.tripmanage.model.Member_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment {

    FloatingActionButton floating_grpbutton;
    GroupAdapter groupAdapter;
 RecyclerView home_rec;
 List<GroupModel>list;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        home_rec=view.findViewById(R.id.home_rec);
        home_rec.setLayoutManager(new LinearLayoutManager(getContext()));



        floating_grpbutton=view.findViewById(R.id.floating_grpbutton);
        floating_grpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PopupHelper popupHelper = new PopupHelper.showSimplePopup(getApplicationContext(),"hh","kkmjmjunu");
//                 PopupHelper.showSimplePopup(GroupActivity.this,"hh","kkmjmjunu");
                // Inflate the layout for the dialog
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.add_member, null);

                // Find the EditText for member name
                EditText editTextName = dialogView.findViewById(R.id.edit_text_name);

                // Create the MaterialAlertDialogBuilder
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
                builder.setView(dialogView)
                        .setTitle("Add group")
                        .setPositiveButton("OK", (dialog, which) -> {
                            // OK button clicked, handle the input
                            String memberName = editTextName.getText().toString();
                            // TODO: Add member using the input data
//                            groupModel.getList().add(new Member_Model(memberName, "a", Collections.emptyList()));
                            DatabaseReference newChildRef = FirebaseDatabase.getInstance().getReference().child("groups").push();
                            GroupModel groupModel1 = new GroupModel(newChildRef.getKey(),memberName,new ArrayList<>());
                            newChildRef.setValue(groupModel1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    fetchdata();
                                }
                            });
//                            groupModel.addMember(newChildRef,new Member_Model(newChildRef.getKey(),memberName, "a", Collections.emptyList()));
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> {
                            // Cancel button clicked, dismiss the dialog
                            dialog.dismiss();
                        });

                // Create and show the dialog
                builder.create().show();
            }
        });

//        fetchdata();

    }

    private void fetchdata() {
        list = new ArrayList<>();

        groupAdapter=new GroupAdapter(getContext(),list);
        FirebaseDatabase.getInstance().getReference().child("groups").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for ( DataSnapshot snapshot1 : snapshot.getChildren()){
                    List<Member_Model> members = new ArrayList<>();
                    for ( DataSnapshot membersnap : snapshot1.child("members").getChildren()){
                        List<Expense_Model> expenseModels = new ArrayList<>();
                        for ( DataSnapshot expensesnap : membersnap.child("expenses").getChildren()){
                            expenseModels.add(new Expense_Model(expensesnap.child("itemname").getValue(String.class),expensesnap.child("content").getValue(String.class),(Objects.requireNonNull(expensesnap.child("amount").getValue(Long.class)))));
                        }
                        members.add(new Member_Model(membersnap.getKey(), membersnap.child("username").getValue(String.class), membersnap.getKey(), expenseModels));
                    }
                    list.add(new GroupModel(snapshot1.getKey(),snapshot1.child("name").getValue(String.class), members));
                    Log.d("ddd",snapshot1.getKey());
                }
                groupAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        home_rec.setAdapter(groupAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        fetchdata();
    }
}



