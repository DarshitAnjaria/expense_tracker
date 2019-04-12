package com.example.asus.expensetracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class TaxCalculator extends AppCompatActivity {

    Toolbar toolbar;
    EditText basic,hra,ta,childEdu,medical,other,totalExm,totalInvest;
    TextView annualTax,monthlyTax;
    Button calculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_calculator);

        toolbar = (Toolbar) findViewById(R.id.taxAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Income Tax Calculator");

        basic = (EditText) findViewById(R.id.etBasicSalary);
        hra = (EditText) findViewById(R.id.etHRA);
        ta = (EditText) findViewById(R.id.etTA);
        childEdu = (EditText) findViewById(R.id.etChildEdu);
        medical = (EditText) findViewById(R.id.etMedical);
        other = (EditText) findViewById(R.id.etOther);
        totalExm = (EditText) findViewById(R.id.etTotalExm);
        totalInvest = (EditText) findViewById(R.id.etTotalInvest);

        annualTax = (TextView) findViewById(R.id.tvAnnualTax);
        monthlyTax = (TextView) findViewById(R.id.tvMonthlyTax);

        calculate = (Button) findViewById(R.id.btnCalculate);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTax();
            }
        });

    }

    private void calculateTax() {
        double basicSal = Double.parseDouble(basic.getText().toString());
        double hraSal = Double.parseDouble(hra.getText().toString());
        double taSal = Double.parseDouble(ta.getText().toString());
        double eduChild = Double.parseDouble(childEdu.getText().toString());
        double medicalAllow = Double.parseDouble(medical.getText().toString());
        double otherAllow = Double.parseDouble(other.getText().toString());
        double exmp = Double.parseDouble(totalExm.getText().toString());
        double invest = Double.parseDouble(totalInvest.getText().toString());

            double grossSalary = basicSal + hraSal + taSal + eduChild + medicalAllow + otherAllow;

            double taxableAnnualGrossIncome = (grossSalary - exmp) * 12;

            double grossTotalIncome = grossSalary;

            double netTaxableAmount = (taxableAnnualGrossIncome - invest);

            if (netTaxableAmount > 0 && netTaxableAmount <=250000){
                annualTax.setText("There is no Tax Apply");
                monthlyTax.setText("There is no Tax Apply");

                Toast.makeText(TaxCalculator.this,"Your amount is below 2,50,000",Toast.LENGTH_LONG).show();

                empty();
            }
            else if(netTaxableAmount > 250000 && netTaxableAmount <= 500000){

                double annualTaxCount = netTaxableAmount - 250000 * 0.05 * 0.02 * 0.01;
                annualTax.setText(String.valueOf(annualTaxCount) + " Rs.");

                double monthlyTaxCount = (netTaxableAmount - 250000 * 0.05 * 0.02 * 0.01) / 12;
                monthlyTax.setText(String.valueOf(monthlyTaxCount) + " Rs.");
                empty();

            }

            else if(netTaxableAmount > 500000 && netTaxableAmount <= 1000000){

                double annualTaxCount = 12500 + netTaxableAmount * 0.2 * 0.02 * 0.01;
                annualTax.setText(String.valueOf(annualTaxCount) + " Rs.");

                double monthlyTaxCount = (12500 + netTaxableAmount * 0.2 * 0.02 * 0.01) / 12;
                monthlyTax.setText(String.valueOf(monthlyTaxCount) + " Rs.");
                empty();

            }

            else if (netTaxableAmount > 1000000){

                double annualTaxCount = 112500 + netTaxableAmount * 0.3 * 0.02 * 0.01;
                annualTax.setText(String.valueOf(annualTaxCount) + " Rs.");

                double monthlyTaxCount = (112500 + netTaxableAmount * 0.3 * 0.02 * 0.01) / 12;
                monthlyTax.setText(String.valueOf(monthlyTaxCount) + " Rs.");
                empty();

            }

    }

    private void empty(){
        basic.setText("");
        hra.setText("");
        ta.setText("");
        childEdu.setText("");
        totalExm.setText("");
        totalInvest.setText("");
        medical.setText("");
        other.setText("");

        basic.requestFocus();
    }
}
