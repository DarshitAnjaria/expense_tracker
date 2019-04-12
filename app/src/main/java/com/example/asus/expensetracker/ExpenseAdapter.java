package com.example.asus.expensetracker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Calendar;
import java.util.List;
import java.util.zip.Inflater;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {


    DatabaseManager dbUpdateExpense;
    Context context;
    int layoutResource;
    List<Transaction> transactionList;
    private DatePickerDialog.OnDateSetListener dateSetListener;


    public ExpenseAdapter(DatabaseManager dbUpdateExpense, Context context, int layoutResource, List<Transaction> transactionList) {
        this.dbUpdateExpense = dbUpdateExpense;
        this.context = context;
        this.layoutResource = layoutResource;
        this.transactionList = transactionList;
    }

    @Override
    public ExpenseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExpenseAdapter.ViewHolder holder, int position) {
        final Transaction transaction = transactionList.get(position);

        holder.typeExp.setText(transaction.getType());
        holder.amountExp.setText(transaction.getAmount() + " Rs.");
        holder.dateExp.setText(transaction.getDate());
        holder.remarkExp.setText(transaction.getRemark());
        holder.entryDateExp.setText(transaction.getEntryDate());

        holder.updateExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateExpense();
            }

            private void updateExpense() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.edit_expense,null);
                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                final MaterialSpinner materialSpinner = view.findViewById(R.id.spinnerTypeExpense);
                final EditText amount = view.findViewById(R.id.editViewAmountExpense);
                final TextView date = view.findViewById(R.id.editViewDateOfExpense);
                final EditText remark = view.findViewById(R.id.editViewRemarkExpense);
                Button saveNew = view.findViewById(R.id.btnSaveChangesExp);
                Button delete = view.findViewById(R.id.btnDeleteExp);

                materialSpinner.setItems(Categories.expenseCategory);

                materialSpinner.setText(transaction.getType());
                amount.setText(transaction.getAmount());
                date.setText(transaction.getDate());
                remark.setText(transaction.getRemark());

                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(context,
                                android.R.style.Theme_Material_Light_Dialog,
                                dateSetListener,
                                year,month,day);

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                        dialog.show();
                    }
                });

                dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String dateShow = dayOfMonth + "/" + month + "/" + year;
                        date.setText(dateShow);
                    }
                };

                saveNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newType = materialSpinner.getText().toString();
                        String newAmount = amount.getText().toString();
                        String newDate = date.getText().toString();
                        String newRemark = remark.getText().toString();

                        if (dbUpdateExpense.update(transaction.getId(),newType,transaction.getFromAcc(), transaction.getToAcc(),Double.parseDouble(newAmount), newDate, newRemark)){
                            Toast.makeText(context, "Data Updated!",Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            loadDataAgain();

                        }
                        else{
                            Toast.makeText(context, "Error in Updating",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setTitle("Confirmation");
                        builder1.setMessage("Are You Sure You Want Delete?");

                        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (dbUpdateExpense.delete(transaction.getId())){
                                    Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                    loadDataAgain();
                                }
                                else{
                                    Toast.makeText(context,"Error in Deleting",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        AlertDialog alertDialog1 = builder1.create();
                        alertDialog1.show();
                    }
                });
            }

        });

    }

    private void loadDataAgain() {
        Cursor cursor = dbUpdateExpense.getExpense();
        if (cursor.moveToFirst()){
            transactionList.clear();
            do {
                transactionList.add(new Transaction(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getDouble(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)
                ));
            }while (cursor.moveToNext());

            notifyDataSetChanged();
        }
    }


    @Override
    public int getItemCount() {
        return transactionList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView typeExp, amountExp, dateExp, remarkExp,entryDateExp;
        Button updateExp;

        public ViewHolder(View itemView) {
            super(itemView);

            typeExp = (TextView) itemView.findViewById(R.id.tvTypes);
            amountExp = (TextView) itemView.findViewById(R.id.tvAmounts);
            dateExp = (TextView) itemView.findViewById(R.id.tvDates);
            remarkExp = (TextView) itemView.findViewById(R.id.tvRemarks);
            entryDateExp = (TextView) itemView.findViewById(R.id.tvEntryDate);
            updateExp = (Button) itemView.findViewById(R.id.updateButton);
        }
    }
}
