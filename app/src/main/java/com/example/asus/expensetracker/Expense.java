package com.example.asus.expensetracker;

public class Expense {
    int id;
    private String type;
    private String amount;
    private String dateOfExpense;
    private String dateOfEntry;
    private String remark;

    public Expense() {
    }

    public Expense(int id, String type, Double amount, String dateOfExpense,String dateOfEntry, String remark) {
        this.id = id;
        this.type = type;
        this.amount = String.valueOf(amount);
        this.dateOfExpense = dateOfExpense;
        this.dateOfEntry = dateOfEntry;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDateOfExpense() {
        return dateOfExpense;
    }

    public void setDateOfExpense(String dateOfExpense) {
        this.dateOfExpense = dateOfExpense;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(String dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }
}
