package com.example.asus.expensetracker;

public class Transaction {
    int id;
    private String transaction;
    private String type;
    private String fromAcc;
    private String toAcc;
    private String amount;
    private String date;
    private String entryDate;
    private String remark;

    public Transaction() {
    }

    public Transaction(int id, String transaction, String type, String fromAcc, String toAcc, Double amount, String date, String entryDate, String remark) {
        this.id = id;
        this.transaction = transaction;
        this.type = type;
        this.fromAcc = fromAcc;
        this.toAcc = toAcc;
        this.amount = String.valueOf(amount);
        this.date = date;
        this.entryDate = entryDate;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFromAcc() {
        return fromAcc;
    }

    public void setFromAcc(String fromAcc) {
        this.fromAcc = fromAcc;
    }

    public String getToAcc() {
        return toAcc;
    }

    public void setToAcc(String toAcc) {
        this.toAcc = toAcc;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
