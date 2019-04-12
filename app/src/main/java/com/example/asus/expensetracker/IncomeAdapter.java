package com.example.asus.expensetracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Calendar;
import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> {

    DatabaseManager dbUpdateIncome;
    Context context;
    int layoutResource;
    List<Transaction> transactionList;
    private DatePickerDialog.OnDateSetListener dateSetListener;



    public IncomeAdapter(DatabaseManager dbUpdateIncome, Context context, int layoutResource, List<Transaction> transactionList) {
        this.dbUpdateIncome = dbUpdateIncome;
        this.context = context;
        this.layoutResource = layoutResource;
        this.transactionList = transactionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResource,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Transaction transaction = transactionList.get(position);

        holder.textType.setText(transaction.getType());
        holder.textAmount.setText(transaction.getAmount() + " Rs.");
        holder.textDate.setText(transaction.getDate());
        holder.textRemark.setText(transaction.getRemark());
        holder.textEntryDate.setText(transaction.getEntryDate());


        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIncome();
            }

            private void updateIncome() {

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.edit_income,null);
                alertBuilder.setView(view);

                final AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();

                final MaterialSpinner materialSpinnerType = view.findViewById(R.id.spinnerUpdateIncome);
                final EditText amount = view.findViewById(R.id.editViewAmountIncome);
                final TextView date = view.findViewById(R.id.editViewDateOfIncome);
                final EditText remark = view.findViewById(R.id.editViewRemarkIncome);
                Button saveNew = view.findViewById(R.id.btnSaveChangesInc);
                Button delete = view.findViewById(R.id.btnDeleteInc);

                materialSpinnerType.setItems(Categories.incomeCategory);

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

                materialSpinnerType.setText(transaction.getType());
                amount.setText(transaction.getAmount());
                date.setText(transaction.getDate());
                remark.setText(transaction.getRemark());
                saveNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String newType = materialSpinnerType.getText().toString();
                        String newAmount = amount.getText().toString();
                        String newDate = date.getText().toString();
                        String newRemark = remark.getText().toString();

                        if (dbUpdateIncome.update(transaction.getId(), newType, transaction.getFromAcc(), transaction.getToAcc(),Double.parseDouble(newAmount),newDate,newRemark)){
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Are You Sure You Want Delete?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (dbUpdateIncome.delete(transaction.getId())){
                                    Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                    loadDataAgain();
                                }
                                else{
                                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }

            private void loadDataAgain() {
                Cursor cursor = dbUpdateIncome.getIncome();
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

        });
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView textType,textAmount,textDate,textRemark,textEntryDate;
        public Button update;

        public ViewHolder(View itemView) {
            super(itemView);

            textType = itemView.findViewById(R.id.tvTypes);
            textAmount = itemView.findViewById(R.id.tvAmounts);
            textDate = itemView.findViewById(R.id.tvDates);
            textRemark = itemView.findViewById(R.id.tvRemarks);
            textEntryDate = itemView.findViewById(R.id.tvEntryDate);
            update = itemView.findViewById(R.id.updateButton);

        }
    }
}
