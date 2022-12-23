package com.awikwok.uas_slemantimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText edEmail, edPassword, edUsername, edAge, edConfirmPw;
    Button btnRegister;
    TextView lgnHere;
    FirebaseAuth mAuth;
    String Key;
    DatabaseReference mDatabaseReferences = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName());
    boolean passwordVisible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edEmail = findViewById(R.id.editTextEmail);
        edPassword = findViewById(R.id.editTextPw);
        edAge = findViewById(R.id.editTextAge);
        edUsername = findViewById(R.id.editTextUsrnm);
        edConfirmPw = findViewById(R.id.editTextPw2);
        btnRegister= findViewById(R.id.buttonRegister);
        lgnHere = findViewById(R.id.textViewLgn);
        mAuth = FirebaseAuth.getInstance();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        lgnHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        edPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=edPassword.getRight()-edPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = edPassword.getSelectionEnd();
                        if(passwordVisible ){
                            edPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;

                        }else{
                            edPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);
                            edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        edPassword.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });

    }

    private void createUser(){
        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();
        String username = edUsername.getText().toString();
        String confirmpw = edConfirmPw.getText().toString();
        String age = edAge.getText().toString();


        if(TextUtils.isEmpty(email)){
            edEmail.setError("Email cannot be empty!");
            edEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            edPassword.setError("Password cannot be empty!");
            edPassword.requestFocus();
        }else if (TextUtils.isEmpty(username)){
            edUsername.setError("Password cannot be empty!");
            edUsername.requestFocus();
        }else if (TextUtils.isEmpty(confirmpw)){
            edConfirmPw.setError("Password cannot be empty!");
            edConfirmPw.requestFocus();
        }else if (TextUtils.isEmpty(age)){
            edAge.setError("Password cannot be empty!");
            edAge.requestFocus();
        } else if(!password.equals(confirmpw)){
            Toast.makeText(this, "Password Doesn't match!!", Toast.LENGTH_SHORT).show();
        }
        else{
            User registerData = new User();
            registerData.setAge(age);
            registerData.setEmail(email);
            registerData.setUsername(username);
            registerData.setPassword(password);

            mDatabaseReferences.push().setValue(registerData);
            Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show();
        }
    }

}