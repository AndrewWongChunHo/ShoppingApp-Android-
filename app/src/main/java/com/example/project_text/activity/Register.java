package com.example.project_text.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_text.R;
import com.example.project_text.fragments.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText mEtRegisteractivityEmail;
    private EditText mEtRegisteractivityPassword;
    private Button mBtRegisteractivityRegister;
    private ProgressBar progressBar;
    private TextView HaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        mAuth = FirebaseAuth.getInstance();
    }

    private void initView() {
        mBtRegisteractivityRegister = findViewById(R.id.btn_register);
        mEtRegisteractivityEmail = findViewById(R.id.RegisterEmail_editText);
        mEtRegisteractivityPassword = findViewById(R.id.RegisterPassword_editText);

        progressBar = findViewById(R.id.progressBar);

        HaveAccount = findViewById(R.id.HaveAccount);
        HaveAccount.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RegisterBackToHome:
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.HaveAccount:
                intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                break;
            case R.id.btn_register:    //註冊按鈕
                registerUser();
                break;
        }
    }

    public void registerUser() {
        String email = mEtRegisteractivityEmail.getText().toString().trim();
        String password = mEtRegisteractivityPassword.getText().toString().trim();

        //將用戶email和password加入Database
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                User user = new User(email, password);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(Register.this,"Registration Successful!", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.VISIBLE);
                                            Intent intent = new Intent(Register.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }else{
                                            Toast.makeText(Register.this,"Registration Failed! Try again!", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });

                            }else{
                                Toast.makeText(Register.this,"Registration Failed! Try again!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                });
    }
}
