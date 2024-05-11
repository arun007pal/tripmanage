package com.example.tripmanage;

import static com.example.tripmanage.MainActivity.groupModel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripmanage.adapter.CustomSpinnerAdapter;
import com.example.tripmanage.adapter.MemberAdapter;
import com.example.tripmanage.model.Expense_Model;
import com.example.tripmanage.model.GroupModel;
import com.example.tripmanage.model.Member_Model;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    RecyclerView group_activity_rec;
    FloatingActionButton floating_button,floating_expense,floating_remove;

    public static Member_Model memberModel;
    String uid = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_group);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        group_activity_rec=findViewById(R.id.group_activity_rec);

        group_activity_rec.setLayoutManager(new LinearLayoutManager(this));
        MemberAdapter memberAdapter=new MemberAdapter(this,groupModel.getList());
        group_activity_rec.setAdapter(memberAdapter);


        floating_button=findViewById(R.id.floating_button);

        floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PopupHelper popupHelper = new PopupHelper.showSimplePopup(getApplicationContext(),"hh","kkmjmjunu");
//                 PopupHelper.showSimplePopup(GroupActivity.this,"hh","kkmjmjunu");
                // Inflate the layout for the dialog
                View dialogView = LayoutInflater.from(GroupActivity.this).inflate(R.layout.add_member, null);

                // Find the EditText for member name
                EditText editTextName = dialogView.findViewById(R.id.edit_text_name);

                // Create the MaterialAlertDialogBuilder
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(GroupActivity.this);
                builder.setView(dialogView)
                        .setTitle("Add Member")
                        .setPositiveButton("OK", (dialog, which) -> {
                            // OK button clicked, handle the input
                            String memberName = editTextName.getText().toString();
                            FirebaseUidFinder.searchForString(FirebaseDatabase.getInstance().getReference().child("users"), memberName, new FirebaseUidFinder.Callback() {
                                @Override
                                public void onComplete(String uidd) {
                                    if (uidd != null) {
                                        uid = uidd;
                                        System.out.println("UID associated with the string: " + uid);
                                        groupModel.getList().add(new Member_Model(memberName, uid, Collections.emptyList()));
                                        DatabaseReference newChildRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupModel.getKey()).child("members");//.push();
                                        groupModel.addMember(newChildRef.child(uid),new Member_Model(newChildRef.getKey(),memberName, "a", Collections.emptyList()));
                                    } else {
                                        System.out.println("String not found.");
                                        groupModel.getList().add(new Member_Model(memberName, uid, Collections.emptyList()));
                                        DatabaseReference newChildRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupModel.getKey()).child("members");//.push();
                                        groupModel.addMember(newChildRef.push(),new Member_Model(newChildRef.getKey(),memberName, "a", Collections.emptyList()));
                                    }
                                }
                            });
//                            if(!FirebaseUidFinder.searchForString(FirebaseDatabase.getInstance().getReference().child("users"),memberName).isEmpty())
//                            {
//                                uid = FirebaseUidFinder.getUidByEmail(memberName);
//                            }
//                            Log.d("Nmr",uid);
                            // TODO: Add member using the input data
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> {
                            // Cancel button clicked, dismiss the dialog
                            dialog.dismiss();
                        });

                // Create and show the dialog
                builder.create().show();
            }
        });
        floating_remove=findViewById(R.id.floating_remove);

        floating_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference().child("groups").child(groupModel.getKey()).removeValue();
                finish();

            }
        });

        floating_expense=findViewById(R.id.floating_expense);
        floating_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View dialogView = LayoutInflater.from(GroupActivity.this).inflate(R.layout.expense_add, null);

                // Find the EditText for member name
                EditText editTextName = dialogView.findViewById(R.id.text_view_message);
                EditText editTextam = dialogView.findViewById(R.id.auto_complete_text_amount);
                Spinner spinnerAmount = dialogView.findViewById(R.id.spinner_amount);

                CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getApplicationContext(),groupModel.getList());
                spinnerAmount.setAdapter(customSpinnerAdapter);
                // Create the MaterialAlertDialogBuilder
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(GroupActivity.this);
                builder.setView(dialogView)
                        .setTitle("Add Member")
                        .setPositiveButton("OK", (dialog, which) -> {
                            // OK button clicked, handle the input
                            String memberName = editTextName.getText().toString();
                            // TODO: Add member using the input data
                            // Retrieve the selected member from the spinner adapter
                            int selectedPosition = customSpinnerAdapter.Selectedpos;
                            Member_Model selectedMember = groupModel.getList().get(selectedPosition);// customSpinnerAdapter.getItem(selectedPosition);

// Ensure that the selected member and input fields are not null
                            if (selectedMember != null && editTextName != null && editTextam != null) {
                                // Get the list of expenses associated with the selected member
                                List<Expense_Model> expenses = selectedMember.getList();

                                // Create a new expense model with the provided details
                                Expense_Model newExpense = new Expense_Model(editTextName.getText().toString(), "ss",
                                        Double.valueOf(editTextam.getText().toString()));

                                // Add the new expense to the list of expenses
//                                expenses.add(newExpense);

                                // Optionally, set the updated list of expenses back to the selected member
//                                selectedMember.addExpense(newExpense);
                                selectedMember.addExpense(groupModel.getKey(),newExpense);
                                // Notify the adapter or UI about the data change if necessary
                                // For example, if you're using RecyclerView, you would notify the adapter:
                                // adapter.notifyDataSetChanged();
                            }

//                            groupModel.getList().get(customSpinnerAdapter.Selectedpos).getList().add(new Expense_Model(editTextName.getText().toString(),"ss", Double.valueOf(editTextam.getText().toString())));
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> {
                            // Cancel button clicked, dismiss the dialog
                            dialog.dismiss();
                        });

                // Create and show the dialog
                builder.create().show();


            }
        });

    }
}