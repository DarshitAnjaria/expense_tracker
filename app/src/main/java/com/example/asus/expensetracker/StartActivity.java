package com.example.asus.expensetracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StartActivity extends AppCompatActivity {

    private Spinner spinnerCountryData;
    private EditText editPhoneNumber,editUserName;
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        spinnerCountryData = (Spinner) findViewById(R.id.spn_country);
        editPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        editUserName = (EditText) findViewById(R.id.etUserName);
        btnContinue = (Button) findViewById(R.id.btnContinue);

        spinnerCountryData.setAdapter(new ArrayAdapter<String>(StartActivity.this,android.R.layout.simple_spinner_dropdown_item,CountryData.countryNames));

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = editPhoneNumber.getText().toString().trim();
                String name = editUserName.getText().toString().trim();

                String code = CountryData.countryAreaCodes[spinnerCountryData.getSelectedItemPosition()];

                try{
                    if (number.isEmpty() || number.length()<10){
                        editPhoneNumber.setError("Please Enter Valid Mobile Number");
                        editPhoneNumber.requestFocus();
                        return;
                    }

                    else if (name.isEmpty()){
                        editUserName.setError("Please Enter Your Name");
                        editUserName.requestFocus();
                        return;
                    }

                    final String mobileNumber = "+" + code + number;
                    Intent intVerify = new Intent(StartActivity.this, VerifyPhoneActivity.class);
//                    intVerify.putExtra("uName", name);
                    intVerify.putExtra("mobileNumber", mobileNumber);
                    startActivity(intVerify);

                }
                catch (Exception e){
                    Toast.makeText(StartActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser!=null){
            Intent iM = new Intent(StartActivity.this,MainActivity.class);
            startActivity(iM);
        }
    }
}
