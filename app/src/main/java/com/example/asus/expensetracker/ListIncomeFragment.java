package com.example.asus.expensetracker;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListIncomeFragment extends Fragment {


    DatabaseManager dbFetchIncome;

    private RecyclerView incomeListView;
    private ImageView imgEmpty;
    private List<Transaction> transactionList;


    public ListIncomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_income, container, false);
        transactionList = new ArrayList<>();
        incomeListView = v.findViewById(R.id.recyclerViewIncome);
        imgEmpty = v.findViewById(R.id.imgEmptyInc);


        dbFetchIncome = new DatabaseManager(getContext());
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();

        loadData();
        emptyData();
    }

    private void emptyData() {
        if (dbFetchIncome.getIncome().getCount() > 0){
            incomeListView.setVisibility(View.VISIBLE);
            imgEmpty.setVisibility(View.GONE);
        }
        else{
            incomeListView.setVisibility(View.GONE);
            imgEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void loadData() {
        Cursor cursor = dbFetchIncome.getIncome();

        if (cursor.moveToFirst()){

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

            incomeListView.setHasFixedSize(true);
            incomeListView.setLayoutManager(new LinearLayoutManager(getContext()));
            incomeListView.setItemAnimator(new DefaultItemAnimator());
            incomeListView.setAdapter(new IncomeAdapter(dbFetchIncome, getContext(), R.layout.all_item_view, transactionList));

        }
    }


}
