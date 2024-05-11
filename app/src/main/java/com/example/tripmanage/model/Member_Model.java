package com.example.tripmanage.model;

import com.example.tripmanage.model.Expense_Model;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Member_Model {

    String key;
    String username,uid;
    List<Expense_Model>list=new ArrayList<>();

    public Member_Model(String username, String uid, List<Expense_Model> list) {
        this.username = username;
        this.uid = uid;
//        this.list = list;
        this.list = new ArrayList<>();
        this.list.addAll(list);
    }

    public Member_Model(String key, String username, String uid, List<Expense_Model> list) {
        this.key = key;
        this.username = username;
        this.uid = uid;
        this.list = new ArrayList<>();
        this.list.addAll(list);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<Expense_Model> getList() {
        return list;
    }

    public void setList(List<Expense_Model> list) {
        this.list = list;
    }

    // Method to add a new expense
    public void addExpense(Expense_Model expense) {
        list.add(expense);
    }

    public void addExpense(String key,Expense_Model expense) {
        list.add(expense);
        DatabaseReference newChildRef = FirebaseDatabase.getInstance().getReference().child("groups").child(key).child("members").child(this.key).child("expenses").push();
        newChildRef.setValue(expense);
    }
    // Method to remove an expense by index
    public void removeExpense(int index) {
        if (index >= 0 && index < list.size()) {
            list.remove(index);
        }
    }

    // Method to update an expense by index
    public void updateExpense(int index, Expense_Model newExpense) {
        if (index >= 0 && index < list.size()) {
            list.set(index, newExpense);
        }
    }

    // Method to get the total number of expenses
    public int getTotalExpenses() {
        return list.size();
    }

    // Method to clear all expenses
    public void clearExpenses() {
        list.clear();
    }
}
