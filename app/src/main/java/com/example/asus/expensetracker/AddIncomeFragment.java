package com.example.asus.expensetracker;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddIncomeFragment extends Fragment {

    DatabaseManager dbIncome;

    private EditText txtAmount,txtRemark;
    private TextView txtTotalIncome,txtDate;
    private Button submit;
    private CoordinatorLayout coordinatorLayoutInc;
    private RadioButton rbInc,rbExp,rbTrans;
    private RadioGroup radioGroup;
    private int transaction = 0;


    private DatePickerDialog.OnDateSetListener dateSetListener;

    public AddIncomeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbIncome = new DatabaseManager(getContext());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_income,null);
        txtAmount = (EditText) view.findViewById(R.id.etEnterAmount);
        txtRemark = (EditText) view.findViewById(R.id.etEnterRemark);
        txtDate = (TextView) view.findViewById(R.id.etSelectDate);
        submit = (Button) view.findViewById(R.id.btnAddIncome);
        rbInc = (RadioButton) view.findViewById(R.id.radioIncome);
        rbExp = (RadioButton) view.findViewById(R.id.radioExpense);
        rbTrans = (RadioButton) view.findViewById(R.id.radioTransfer);
        txtTotalIncome = (TextView) view.findViewById(R.id.tvIncomeTotal);
        coordinatorLayoutInc = (CoordinatorLayout) view.findViewById(R.id.coordinatorInc);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

        final MaterialSpinner materialSpinnerIncome = view.findViewById(R.id.materialSpinnerIncome);
        materialSpinnerIncome.setItems(Categories.incomeCategory);

        if (rbInc.isChecked()){
            rbInc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    transaction = 1;
                }
            });
        }
        else if (rbExp.isChecked()){
            rbExp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    transaction = 2;
                }
            });
        }

        else if (rbTrans.isChecked()){
            rbTrans.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    transaction = 3;
                }
            });
        }

        else {
                rbInc.setError("Please check one of these!");
        }

        return view;
    }
    private void successMessage() {
        Snackbar snackbar = Snackbar.make(coordinatorLayoutInc,"Income Added", Snackbar.LENGTH_LONG);
        View newV = snackbar.getView();
        TextView textView = (TextView) newV.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
