package com.example.asus.expensetracker;


public class Income {
    private int id;
    private String transaction;
    private String type;
    private String amount;
    private String dateOfIncome;
    private String dateOfEntry;
    private String remarks;

    public Income() {
    }

    public Income(int id,String transaction,  String type, Double amount, String dateOfIncome,String dateOfEntry, String remarks) {
        this.id = id;
        this.type = type;
        this.amount = String.valueOf(amount);
        this.dateOfIncome = dateOfIncome;
        this.dateOfEntry = dateOfEntry;
        this.remarks = remarks;
        this.transaction = transaction;
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

    public String getDateOfIncome() {
        return dateOfIncome;
    }

    public void setDateOfIncome(String dateOfIncome) {
        this.dateOfIncome = dateOfIncome;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(String dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
}
