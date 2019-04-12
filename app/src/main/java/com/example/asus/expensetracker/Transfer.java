package com.example.asus.expensetracker;

public class Transfer {
    int id;
    private String type;
    private String fromAccount;
    private String toAccount;
    private String amount;
    private String dateOfTransfer;
    private String dateOfEntryTransfer;
    private String remarks;

    public Transfer() {
    }


    public Transfer(int id, String type, String fromAccount, String toAccount, Double amount, String dateOfTransfer,String dateOfEntryTransfer, String remarks) {
        this.id = id;
        this.type = type;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = String.valueOf(amount);
        this.dateOfTransfer = dateOfTransfer;
        this.dateOfEntryTransfer = dateOfEntryTransfer;
        this.remarks = remarks;
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

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getDateOfTransfer() {
        return dateOfTransfer;
    }

    public void setDateOfTransfer(String dateOfTransfer) {
        this.dateOfTransfer = dateOfTransfer;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDateOfEntryTransfer() {
        return dateOfEntryTransfer;
    }

    public void setDateOfEntryTransfer(String dateOfEntryTransfer) {
        this.dateOfEntryTransfer = dateOfEntryTransfer;
    }
}
