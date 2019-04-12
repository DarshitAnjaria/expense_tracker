package com.example.asus.expensetracker;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTransferFragment extends Fragment {

    DatabaseManager dbTransfer;


    private EditText amountTrans,remarkTrans,fromAccountTrans,toAccountTrans;
    private TextView  txtTotalTransfer,selectDateTrans;
    private Button submitTrans;

    private DatePickerDialog.OnDateSetListener dateSetListenerTrans;


    public AddTransferFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbTransfer = new DatabaseManager(getContext());

        View view = inflater.inflate(R.layout.fragment_add_transfer,null);

        amountTrans = (EditText) view.findViewById(R.id.etEnterAmountTrans);
        remarkTrans = (EditText) view.findViewById(R.id.etEnterRemarkTrans);
        fromAccountTrans = (EditText) view.findViewById(R.id.etFromAccount);
        toAccountTrans = (EditText) view.findViewById(R.id.etToAccount);
        selectDateTrans = (TextView) view.findViewById(R.id.etSelectDateTrans);
        submitTrans = (Button) view.findViewById(R.id.btnAddTransfer);
        txtTotalTransfer = (TextView) view.findViewById(R.id.tvTransferTotal);

        final MaterialSpinner materialSpinnerTransfer = view.findViewById(R.id.materialSpinnerTransfer);
        materialSpinnerTransfer.setItems(Categories.transferCategory);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();

    }
}
