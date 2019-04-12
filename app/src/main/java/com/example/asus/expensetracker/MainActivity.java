package com.example.asus.expensetracker;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton floatingActionButton;

    DatabaseManager dbGetTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbGetTotal = new DatabaseManager(MainActivity.this);

        toolbar = (Toolbar) findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Expense Tracker");
        getSupportActionBar().setSubtitle("Track Your Expense Here!");
        getSupportActionBar().setIcon(R.drawable.ic_action_name_exp);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(MainActivity.this);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabAdd);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iAdd = new Intent(MainActivity.this,AddingActivity.class);
                startActivity(iAdd);
            }
        });

        loadFragment(new ListIncomeFragment());
    }

    private boolean loadFragment(android.support.v4.app.Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        android.support.v4.app.Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.list_incomes:
                fragment = new ListIncomeFragment();
                break;

            case R.id.list_expense:
                fragment = new ListExpenseFragment();
                break;

            case R.id.list_transfers:
                fragment = new ListTransferFragment();
                break;

            case R.id.list_all:
                fragment = new ListAllFragment();
                break;
        }
        return loadFragment(fragment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        return true;
    }

    private void showSavings(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Your Savings");

        LayoutInflater inflater = this.getLayoutInflater();
        View v =inflater.inflate(R.layout.savings,null);
        builder.setView(v);

        TextView savings = v.findViewById(R.id.tvSavings);

        Cursor cursor = dbGetTotal.getIncomeTotal();

        double totalInc = 0;
        double totalExp = 0;

        if (cursor.moveToFirst()){
            totalInc = cursor.getDouble(cursor.getColumnIndex("TotalIncome"));
//            savings.setText(String.valueOf(totalInc));
        }

        Cursor cursor1 = dbGetTotal.getExpenseTotal();

        if (cursor1.moveToFirst()){
            totalExp = cursor1.getDouble(cursor1.getColumnIndex("TotalExpense"));
        }

        double totalSaving = totalInc - totalExp;

        savings.setText(String.valueOf(totalSaving) + " Rs.");

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.optSavings:
                showSavings();
                break;

            case R.id.optShowTotal:
                showTotals();
                break;

            case R.id.optShowTaxCalcee:
                Intent intent = new Intent(MainActivity.this,TaxCalculator.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void showTotals() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.showtotal,null);
        builder.setTitle("Total");
        builder.setView(v);

        TextView totalInc = v.findViewById(R.id.incomeTotal);
        TextView totalExp = v.findViewById(R.id.expenseTotal);
        TextView totalTrans = v.findViewById(R.id.transferTotal);

        Cursor cursor = dbGetTotal.getIncomeTotal();

        if (cursor.moveToFirst()){
            double getTotal = cursor.getDouble(cursor.getColumnIndex("TotalIncome"));
            totalInc.setText(String.valueOf(getTotal) + " Rs.");
        }

        Cursor cursor1 = dbGetTotal.getExpenseTotal();

        if (cursor1.moveToFirst()){
            double getTotalExp = cursor1.getDouble(cursor1.getColumnIndex("TotalExpense"));
            totalExp.setText(String.valueOf(getTotalExp) + " Rs.");
        }

        Cursor cursor2 = dbGetTotal.getTransferTotal();

        if (cursor2.moveToFirst()){
            double getTotal = cursor2.getDouble(cursor2.getColumnIndex("TotalTransfer"));
            totalTrans.setText(String.valueOf(getTotal) + " Rs.");
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
