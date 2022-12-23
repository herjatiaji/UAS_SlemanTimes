package com.awikwok.uas_slemantimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText etLoginUsername, etLoginPw;
    TextView registerHere;
    Button btnLgn;
    FirebaseAuth mAuth;
    String Key;
    public static SharedPrefManager SP;
    DatabaseReference mDatabaseReferences = FirebaseDatabase.getInstance().getReference();
    boolean passwordVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginUsername = findViewById(R.id.editTextUsername);
        etLoginPw = findViewById(R.id.editTextPw);
        registerHere =findViewById(R.id.textViewSignup);
        mAuth = FirebaseAuth.getInstance();
        SP = new SharedPrefManager(this);
        btnLgn = findViewById(R.id.buttonLgn);
        if (SP.getSPSudahLogin() == true){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnLgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etLoginUsername.getText().toString();
                String password = etLoginPw.getText().toString();

                mDatabaseReferences.child("User").orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            mDatabaseReferences.child("User").orderByChild("password").equalTo(password).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (dataSnapshot.exists()) {
                                        Toast.makeText(LoginActivity.this, "Berhasil login!", Toast.LENGTH_SHORT).show();
                                        Intent messageIntent = new Intent(LoginActivity.this, MainActivity.class);
                                        SP.saveSPString(SharedPrefManager.SP_USERNAME, username);
                                        SP.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                                        startActivity(messageIntent);
                                        finish();
                                    }
                                    else {
                                        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(LoginActivity.this);
                                        myAlertBuilder.setTitle("Gagal Login!");
                                        myAlertBuilder.setMessage("Username atau password anda salah. Silahkan coba lagi!");
                                        myAlertBuilder.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        myAlertBuilder.show();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else {
                            AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(LoginActivity.this);
                            myAlertBuilder.setTitle("Gagal Login!");
                            myAlertBuilder.setMessage("Username atau password anda salah. Silahkan coba lagi!");
                            myAlertBuilder.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            myAlertBuilder.show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

        });
        registerHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

            }
        });
        etLoginPw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=etLoginPw.getRight()-etLoginPw.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = etLoginPw.getSelectionEnd();
                        if(passwordVisible ){
                            etLoginPw.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            etLoginPw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;

                        }else{
                            etLoginPw.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);
                            etLoginPw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        etLoginPw.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });
    }




    public void logout(){
        SP.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
    }
}

