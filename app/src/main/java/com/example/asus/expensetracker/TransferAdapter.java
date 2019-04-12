package com.example.asus.expensetracker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class TransferAdapter extends RecyclerView.Adapter<TransferAdapter.ViewHolder> {

    DatabaseManager dbUpdateTransfer;

    Context context;
    int layoutResource;
    List<Transaction> transactionList;
    private DatePickerDialog.OnDateSetListener dateSetListener;


    public TransferAdapter(DatabaseManager dbUpdateTransfer, Context context, int layoutResource, List<Transaction> transactionList) {
        this.dbUpdateTransfer = dbUpdateTransfer;
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
    public void onBindViewHolder(TransferAdapter.ViewHolder holder, int position) {

        final Transaction transaction = transactionList.get(position);
        holder.typeTrans.setText(transaction.getType());
        holder.amountTrans.setText(transaction.getAmount() + " Rs.");
        holder.from.setText(transaction.getFromAcc() + " To");
        holder.to.setText(transaction.getToAcc());
        holder.dateTrans.setText(transaction.getDate());
        holder.entryTrans.setText(transaction.getEntryDate());
        holder.remarkTrans.setText(transaction.getRemark());

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTransfer();
            }

            private void updateTransfer() {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.edit_transfer,null);
                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                final MaterialSpinner materialSpinner = view.findViewById(R.id.spinnerUpdateTransfer);
                final EditText amount = view.findViewById(R.id.editViewAmountTransfer);
                final TextView date = view.findViewById(R.id.editViewDateOfTransfer);
                final EditText from = view.findViewById(R.id.editViewFromAcc);
                final EditText to = view.findViewById(R.id.editViewToAcc);
                final EditText remark = view.findViewById(R.id.editViewRemarkTransfer);
                Button saveNew = view.findViewById(R.id.btnSaveChangesTrans);
                Button delete = view.findViewById(R.id.btnDeleteTrans);

                materialSpinner.setItems(Categories.transferCategory);

                materialSpinner.setText(transaction.getType());
                amount.setText(transaction.getAmount());
                date.setText(transaction.getDate());
                from.setText(transaction.getFromAcc());
                to.setText(transaction.getToAcc());
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
                        String newFrom = from.getText().toString();
                        String newTo = to.getText().toString();
                        String newRemark = remark.getText().toString();

                        if (dbUpdateTransfer.update(transaction.getId(),newType,newFrom,newTo,Double.parseDouble(newAmount),newDate,newRemark)){
                            Toast.makeText(context,"Data Updated!",Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            loadDataAgain();
                        }
                        else{
                            Toast.makeText(context,"Error in Updating",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setTitle("Confirmation");
                        builder1.setTitle("Are You Sure You Want Delete?");
                        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (dbUpdateTransfer.delete(transaction.getId())){
                                    Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                                    loadDataAgain();
                                    alertDialog.dismiss();
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
        Cursor cursor = dbUpdateTransfer.getTransfer();
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView typeTrans, amountTrans, from,to,dateTrans,entryTrans,remarkTrans;
        Button update;

        public ViewHolder(View itemView) {
            super(itemView);

            typeTrans = itemView.findViewById(R.id.tvTypes);
            amountTrans = itemView.findViewById(R.id.tvAmounts);
            from = itemView.findViewById(R.id.tvFrom);
            to = itemView.findViewById(R.id.tvTo);
            dateTrans = itemView.findViewById(R.id.tvDates);
            entryTrans = itemView.findViewById(R.id.tvEntryDate);
            remarkTrans = itemView.findViewById(R.id.tvRemarks);
            update = itemView.findViewById(R.id.updateButton);
        }
    }
}
