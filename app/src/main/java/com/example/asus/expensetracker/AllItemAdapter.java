package com.example.asus.expensetracker;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AllItemAdapter extends RecyclerView.Adapter<AllItemAdapter.ViewHolder> {

    DatabaseManager dbManager;
    Context context;
    int layoutResource;
    List<Transaction> transactionList;

    public AllItemAdapter(DatabaseManager dbManager, Context context, int layoutResource, List<Transaction> transactionList) {
        this.dbManager = dbManager;
        this.context = context;
        this.layoutResource = layoutResource;
        this.transactionList = transactionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutResource,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllItemAdapter.ViewHolder holder, int position) {

        Transaction transaction = transactionList.get(position);

        if (transaction.getTransaction().equals("1")){
            holder.transaction.setText("Income");
            holder.transaction.setTextColor(Color.GREEN);
        }
        else if (transaction.getTransaction().equals("2")){
            holder.transaction.setText("Expense");
            holder.transaction.setTextColor(Color.RED);
        }
        else if (transaction.getTransaction().equals("3")){
            holder.transaction.setText("Transfer");
            holder.transaction.setTextColor(Color.BLUE);
        }
//        holder.transaction.setText(transaction.getTransaction());
        holder.amount.setText(transaction.getAmount() + " Rs.");
        holder.type.setText(transaction.getType());
        holder.from.setText(transaction.getFromAcc() + " To");
        holder.to.setText(transaction.getToAcc());
        holder.date.setText(transaction.getDate());
        holder.remark.setText(transaction.getRemark());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView transaction,type,amount,from,to,date,remark;

        public ViewHolder(View itemView) {
            super(itemView);

            transaction = itemView.findViewById(R.id.tvTransaction);
            type = itemView.findViewById(R.id.tvTypeAll);
            amount = itemView.findViewById(R.id.tvAmountsAll);
            from = itemView.findViewById(R.id.tvFromAccount);
            to = itemView.findViewById(R.id.tvToAccount);
            date = itemView.findViewById(R.id.tvDatesAll);
            remark = itemView.findViewById(R.id.tvRemarksAll);
        }
    }
}
