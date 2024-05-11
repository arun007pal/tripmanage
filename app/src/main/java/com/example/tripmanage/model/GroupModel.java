package com.example.tripmanage.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class GroupModel {

    private String key;
    private String name;

   private List<Member_Model>list=new ArrayList<>();

    public GroupModel(String key, String name, List<Member_Model> list) {
        this.key = key;
        this.name = name;
        this.list = list;
    }

    public GroupModel(String name, List<Member_Model> list) {
        this.name = name;
        this.list = list;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member_Model> getList() {
        return list;
    }

    public void setList(List<Member_Model> list) {
        this.list = list;
    }

    public void addMember(Member_Model memberModel){
        DatabaseReference newChildRef = FirebaseDatabase.getInstance().getReference().child("groups").child(key).child("members").push();
        newChildRef.setValue(memberModel);
    }

    public void addMember(DatabaseReference newChildRef, Member_Model memberModel){
        newChildRef.setValue(memberModel);
    }
}
