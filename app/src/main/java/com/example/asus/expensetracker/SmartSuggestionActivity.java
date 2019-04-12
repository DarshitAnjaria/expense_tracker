package com.example.asus.expensetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SmartSuggestionActivity extends AppCompatActivity {

    private Toolbar smartTool;
    private DatabaseReference dbIncome,dbExpense;

    private TextView totalIncome,totalExpense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_suggestion);

        smartTool = (Toolbar)findViewById(R.id.smart_suggestion_app_bar);
        setSupportActionBar(smartTool);
        getSupportActionBar().setTitle("Smart Suggestions");
        getSupportActionBar().setIcon(R.drawable.ic_action_main_smart);


        totalIncome = (TextView) findViewById(R.id.tvTotalIncomeSmart);
        totalExpense = (TextView) findViewById(R.id.tvTotalExpenseSmart);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        dbIncome = FirebaseDatabase.getInstance().getReference().child("Income").child(uid);
        dbExpense = FirebaseDatabase.getInstance().getReference().child("Expense").child(uid);

        dbIncome.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sumIncome = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String amount = snapshot.child("amount").getValue(String.class);
                    int value = Integer.valueOf(amount.replace("Rs. ",""));

                    sumIncome += value;
                }
                totalIncome.setText(String.valueOf(sumIncome) + " Rs.");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbExpense.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int expenseAmount = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String type = snapshot.child("type").getValue(String.class);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
