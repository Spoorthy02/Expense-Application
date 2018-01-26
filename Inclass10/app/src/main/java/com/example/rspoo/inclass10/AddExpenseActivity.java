package com.example.rspoo.inclass10;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class AddExpenseActivity extends AppCompatActivity {

    Spinner categoryList;
    DatabaseReference mRoot  = FirebaseDatabase.getInstance().getReference();

    EditText name ;
    Category check ;
    EditText amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        setSpinner();
    }

    private void setSpinner() {
        categoryList = (Spinner) findViewById(R.id.spinnerCategory);



        final ArrayAdapter<Category> spinnerArrayAdapter = new ArrayAdapter<Category>(
                AddExpenseActivity.this, android.R.layout.simple_list_item_1, Category.values()) {


            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        categoryList.setAdapter(spinnerArrayAdapter);


        Button add = (Button)findViewById(R.id.buttonAddExpense);
        Button cancle = (Button)findViewById(R.id.buttonCancel);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSend = new Intent(AddExpenseActivity.this,ExpenseActivity.class);
                toSend.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(toSend);
            }
        }) ;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (EditText)findViewById(R.id.editTextExpenseName);
                check = (Category) categoryList.getSelectedItem();
                amount = (EditText)findViewById(R.id.editTextAmount);
                if (amount.getText().length() == 0
                        || name.getText().length() == 0  || check == Category.SelectCategory ) {

                    Toast required = Toast.makeText(AddExpenseActivity.this, "Enter all the details", Toast.LENGTH_SHORT);
                    required.show();
                }
                else {

                    Expense newExpense = new Expense();
                    newExpense.expenseName = name.getText().toString();
                    newExpense.category = (Category) categoryList.getSelectedItem();
                    newExpense.amount = Double.parseDouble(amount.getText().toString());
                    newExpense.date = new Date();


                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String key1 = mRoot.child("values").push().getKey();
                    newExpense.key = key1;
                    //the below line also can be used
                    // mRoot.child("expenses").child("/"+user.getUid()+"/").child("/"+key1+"/").setValue(newExpense);
                    mRoot.child("expenses").child(user.getUid()).child(key1).setValue(newExpense);


                    Intent toSend = new Intent(AddExpenseActivity.this,ExpenseActivity.class);
                    toSend.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(toSend);
                }
            }
        });

    }
}