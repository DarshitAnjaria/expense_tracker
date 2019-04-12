package com.example.asus.expensetracker;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddingActivity extends AppCompatActivity{

    DatabaseManager dbManager;

    private Toolbar toolbar;
    private FloatingActionButton floatingActionButtonList;
    private CoordinatorLayout coordinatorLayout;
    private MaterialSpinner materialSpinner;
    private RadioButton rbInc,rbExp,rbTrans;
    private EditText amount,remark,from,to;
    private TextView date;
    private Button submit;
    private Calendar calendar;
    int year,day,month;
    int transaction = 0;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);

        dbManager = new DatabaseManager(AddingActivity.this);

        toolbar = (Toolbar) findViewById(R.id.addActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Expense Tracker");
        getSupportActionBar().setSubtitle("Track Your Expense Here");
        getSupportActionBar().setIcon(R.drawable.ic_action_name_exp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        floatingActionButtonList = (FloatingActionButton) findViewById(R.id.fabList);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        materialSpinner = (MaterialSpinner) findViewById(R.id.materialSpinner);
        rbInc = (RadioButton) findViewById(R.id.radioIncome);
        rbExp = (RadioButton)findViewById(R.id.radioExpense);
        rbTrans = (RadioButton) findViewById(R.id.radioTransfer);
        amount = (EditText) findViewById(R.id.etEnterAmount);
        remark = (EditText) findViewById(R.id.etEnterRemark);
        from = (EditText) findViewById(R.id.etEnterFromAcc);
        to = (EditText) findViewById(R.id.etEnterTo);
        date = (TextView) findViewById(R.id.etSelectDate);
        submit = (Button) findViewById(R.id.btnAdd);

        rbInc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    transaction = 1;
                    from.setVisibility(View.GONE);
                    to.setVisibility(View.GONE);
                    materialSpinner.setItems(Categories.incomeCategory);
                }
            }
        });

        rbExp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked) {
                   transaction = 2;
                   from.setVisibility(View.GONE);
                   to.setVisibility(View.GONE);
                   materialSpinner.setItems(Categories.expenseCategory);
               }
            }
        });

        rbTrans.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    transaction = 3;
                    from.setVisibility(View.VISIBLE);
                    to.setVisibility(View.VISIBLE);
                    from.requestFocus();
                    materialSpinner.setItems(Categories.transferCategory);
                }
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddingActivity.this,
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

        floatingActionButtonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iList = new Intent(AddingActivity.this,MainActivity.class);
                startActivity(iList);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

    }


    private void addData() {

        String currentDate = DateFormat.getDateInstance().format(new Date());

        String type = materialSpinner.getText().toString();
        String amt = amount.getText().toString();
        String fromAcc = transaction == 3 ? from.getText().toString() : null;
        String toAcc = transaction == 3 ? to.getText().toString() : null;
        String dateOfData = date.getText().toString();
        String rmk = remark.getText().toString();

        if (type.equals("Select Category")){
            materialSpinner.setError("Please select category");
            materialSpinner.requestFocus();
            return;
        }
        if (amt.isEmpty()){
            amount.setError("Amount can't be Empty!");
            amount.requestFocus();
            return;
        }
        if (dateOfData.isEmpty()){
            date.setError("Please select date");
            date.requestFocus();
            return;
        }

        if (transaction == 3 && fromAcc.isEmpty()){
            from.setError("Can't be Empty");
            from.requestFocus();
            return;
        }

        if (transaction == 3 && toAcc.isEmpty()){
            to.setError("Can't be Empty");
            to.requestFocus();
            return;
        }

        Cursor cursor = dbManager.getIncomeTotal();
        Cursor cursor1 = dbManager.getExpenseTotal();

        double totalIncome = 0;
        double totalExpense = 0;

        if (cursor.moveToFirst()){
            totalIncome = cursor.getDouble(cursor.getColumnIndex("TotalIncome"));
        }
        if (cursor1.moveToFirst()){
            totalExpense = cursor1.getDouble(cursor1.getColumnIndex("TotalExpense"));
        }

        if (totalExpense > totalIncome){
            Toast.makeText(AddingActivity.this,"Your Expense is higher than Income", Toast.LENGTH_LONG).show();
            Toast.makeText(AddingActivity.this,"Could not Add Data",Toast.LENGTH_LONG).show();
        }

        else{
            if (dbManager.addData(String.valueOf(transaction), type, fromAcc,toAcc,Double.parseDouble(amt), rmk, dateOfData, currentDate)){
                showSuccessMessage();
                materialSpinner.setText("");
                amount.setText("");
                date.setText("Select Date");
                from.setText("");
                to.setText("");
                remark.setText("");
                rbInc.setChecked(false);
                rbExp.setChecked(false);
                rbTrans.setChecked(false);
                materialSpinner.setItems("");
            }
        }
    }

    private void showSuccessMessage() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout,"Data Added",Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.setAction("VIEW", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.show();
    }

}
