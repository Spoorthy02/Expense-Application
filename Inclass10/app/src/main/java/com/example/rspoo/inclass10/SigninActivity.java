package com.example.rspoo.inclass10;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mFulName;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG = "signActivity";
    ProgressDialog progress;
    Button signUp,cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mEmailField = (EditText) findViewById(R.id.editTextSignUpEmail);
        mPasswordField = (EditText) findViewById(R.id.editTextChSignUpPass);
        mFulName = (EditText) findViewById(R.id.editTextFName);
        signUp = (Button) findViewById(R.id.buttonSendSignUp);
        signUp.setOnClickListener(this);

        cancle = (Button) findViewById(R.id.buttonCancel);
        cancle.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {

                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }


            }
        };
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());


                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(SigninActivity.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                        }


                        else {

                        }
                        hideProgressDialog();

                    }
                });

    }

    private void hideProgressDialog() {
        progress.hide();
    }

    private void showProgressDialog() {
        progress = new ProgressDialog(SigninActivity.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setMessage("Please Wait...");
        progress.show();
    }


    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());


                        if (!task.isSuccessful()) {
                            Toast.makeText(SigninActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(mFulName.getText().toString())
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                                updateUI();
                                                Intent toSend = new Intent(SigninActivity.this, ExpenseActivity.class);

                                                startActivity(toSend);
                                            }
                                        }
                                    });





                        }
                        hideProgressDialog();

                    }
                });

    }


    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {

            mPasswordField.setError(null);

        }

        String fullName = mFulName.getText().toString();
        if (TextUtils.isEmpty(fullName)) {
            mFulName.setError("Required.");
            valid = false;
        } else {
            mFulName.setError(null);
        }

        return valid;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void updateUI() {
        mEmailField.setText("");
        mPasswordField.setText("");
        mFulName.setText("");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            case R.id.buttonSendSignUp:
                FirebaseAuth.getInstance().signOut();
                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());


                //
                break;
            case R.id.buttonCancel:
                Intent login = new Intent(SigninActivity.this,MainActivity.class);
                startActivity(login);
                break;
        }

    }
}
