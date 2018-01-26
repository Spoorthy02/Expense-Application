package com.example.rspoo.inclass10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ShowExpenseActivity extends AppCompatActivity {
    TextView name,amount,date,category;

    Button BtnClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);

        if(getIntent().getExtras().containsKey(ExpenseActivity.SHOW_TAG)) {

            Expense expense = getIntent().getExtras().getParcelable(ExpenseActivity.SHOW_TAG);
            name = (TextView) findViewById(R.id.textViewname);
            amount = (TextView) findViewById(R.id.textViewAmount);
            date = (TextView) findViewById(R.id.textViewDate);
            category = (TextView) findViewById(R.id.textViewCategory);

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            String dateString = format.format(expense.date);

            name.setText(expense.expenseName);
            amount.setText("$ " + expense.amount + "");
            date.setText(dateString);
            category.setText(expense.category.toString());
        }

        BtnClose = (Button)findViewById(R.id.buttonClose);
        BtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSend = new Intent(ShowExpenseActivity.this,ExpenseActivity.class);
                toSend.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(toSend);
            }
        });
    }
}
