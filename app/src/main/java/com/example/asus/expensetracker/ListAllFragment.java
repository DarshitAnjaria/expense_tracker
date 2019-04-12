package com.example.asus.expensetracker;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListAllFragment extends Fragment {

    DatabaseManager dbFetchAll;

    private RecyclerView listViewAllItem;
    private List<Transaction> transactionList;


    public ListAllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dbFetchAll = new DatabaseManager(getContext());

        View view = inflater.inflate(R.layout.fragment_list_all,null);
        listViewAllItem = view.findViewById(R.id.listViewAll);
        transactionList = new ArrayList<>();

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        Cursor cursor = dbFetchAll.getAllData();

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

            listViewAllItem.setHasFixedSize(true);
            listViewAllItem.setLayoutManager(new LinearLayoutManager(getContext()));
            listViewAllItem.setAdapter(new AllItemAdapter(dbFetchAll,getContext(),R.layout.all_item,transactionList));
        }
    }
}
