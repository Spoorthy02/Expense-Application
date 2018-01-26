package com.example.rspoo.inclass10;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ExpenseActivity extends AppCompatActivity {
    ListView ExpenseList;
    ImageButton addNew;
    DatabaseReference mRoot  = FirebaseDatabase.getInstance().getReference();
    DatabaseReference  mConditionRef ;
    ExpenseAdapter adapter;
    static final String SHOW_TAG = "Show";
    static final int SHOW_TAG_R = 10;
    TextView textViewmesg, userhome ;

    FirebaseUser user;
    ProgressDialog progress;
    ArrayList<Expense> MyList = new ArrayList<Expense>();


    private void hideProgressDialog() {
        progress.hide();
    }

    private void showProgressDialog() {
        progress = new ProgressDialog(ExpenseActivity.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(true);
        progress.setMessage("Loading Expenses...");
        progress.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);


        MyList = new ArrayList<Expense>();
        addNew = (ImageButton)findViewById(R.id.imageButton);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addExpense =  new Intent(ExpenseActivity.this,AddExpenseActivity.class);
                startActivity(addExpense);
            }
        });




        textViewmesg = (TextView)findViewById(R.id.textViewmesg);
        ExpenseList = (ListView)findViewById(R.id.ExpenseList);
        adapter = new ExpenseAdapter(ExpenseActivity.this, R.layout.item_row_layout, MyList);
        ExpenseList.setDivider(null);
        ExpenseList.setDividerHeight(10);


        adapter.setNotifyOnChange(true);
        ExpenseList.setAdapter(adapter);


        ExpenseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showExpense = new Intent(ExpenseActivity.this,ShowExpenseActivity.class);
                Expense toShow = MyList.get(position);
                showExpense.putExtra(SHOW_TAG,toShow);
                startActivity(showExpense);
            }
        });




        ExpenseList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Expense toDelete = MyList.get(position);
                mConditionRef.child(toDelete.key).removeValue();
                MyList.remove(toDelete);
                adapter.notifyDataSetChanged();
                if(MyList.size()== 0)
                {
                    textViewmesg.setVisibility(View.VISIBLE);
                }
                Toast.makeText(ExpenseActivity.this, "Expense Deleted",
                        Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();



        mConditionRef = mRoot.child("expenses").child(user.getUid());

        //showProgressDialog();
        mConditionRef.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Expense toadd = dataSnapshot.getValue(Expense.class);
                if(!MyList.contains(toadd)) {
                    MyList.add(toadd);
                    if(MyList.size()!= 0)
                    {
                        textViewmesg.setVisibility(View.INVISIBLE);
                    }

                    adapter.notifyDataSetChanged();
                }


            }



            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                // allUsers.re(toadd);


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                /*Expense toremove = dataSnapshot.getValue(Expense.class);
                MyList.remove(toremove);
                adapter.notifyDataSetChanged();*/
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        Intent loggOut = new Intent(ExpenseActivity.this,MainActivity.class);
        loggOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loggOut);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void onStart() {
        super.onStart();

    }
}
