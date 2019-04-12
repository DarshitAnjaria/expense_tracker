package com.example.asus.expensetracker;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListExpenseFragment extends Fragment {

    DatabaseManager dbFetchExpense;
    private RecyclerView expenseListView;
    private ImageView emptyExpImg;
    private List<Transaction> transactionList;

    public ListExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_expense,container,false);

        transactionList = new ArrayList<>();
        expenseListView = view.findViewById(R.id.listViewExpense);
        emptyExpImg = view.findViewById(R.id.imgEmptyExp);

        dbFetchExpense = new DatabaseManager(getContext());

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        emptyData();
        loadData();
    }

    private void emptyData() {
        if (dbFetchExpense.getExpense().getCount() > 0){
            expenseListView.setVisibility(View.VISIBLE);
            emptyExpImg.setVisibility(View.GONE);
        }
        else{
            expenseListView.setVisibility(View.GONE);
            emptyExpImg.setVisibility(View.VISIBLE);
        }
    }

    private void loadData() {
        Cursor cursor = dbFetchExpense.getExpense();

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

            expenseListView.setHasFixedSize(true);
            expenseListView.setLayoutManager(new LinearLayoutManager(getContext()));
            expenseListView.setItemAnimator(new DefaultItemAnimator());
            expenseListView.setAdapter(new ExpenseAdapter(dbFetchExpense, getContext(), R.layout.all_item_view, transactionList));
        }
    }
}
