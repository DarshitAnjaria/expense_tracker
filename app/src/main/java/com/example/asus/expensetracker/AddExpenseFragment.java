package com.example.asus.expensetracker;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddExpenseFragment extends Fragment {

   DatabaseManager dbExpense;

    private EditText amountExp,remarkExp;
    private TextView txtTotalExpense,selectDateExp;
    private Button submitExp;

    private DatePickerDialog.OnDateSetListener dateSetListenerExp;

    public AddExpenseFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbExpense = new DatabaseManager(getContext());

        View view = inflater.inflate(R.layout.fragment_add_expense,null);

        amountExp = (EditText) view.findViewById(R.id.etEnterAmountExp);
        remarkExp = (EditText) view.findViewById(R.id.etEnterRemarkExp);
        submitExp = (Button) view.findViewById(R.id.btnAddExpense);
        selectDateExp = (TextView) view.findViewById(R.id.etSelectDateExp);
        txtTotalExpense = (TextView) view.findViewById(R.id.tvExpenseTotal);

        final MaterialSpinner materialSpinnerExpense = view.findViewById(R.id.materialSpinnerExpense);
        materialSpinnerExpense.setItems(Categories.expenseCategory);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
