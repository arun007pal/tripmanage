package com.example.tripmanage.model;

public class Expense_Model {

    String itemname,content;
    double amount;

    public Expense_Model(String itemname, String content, double amount) {
        this.itemname = itemname;
        this.content = content;
        this.amount = amount;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
