package com.example.project_text.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.project_text.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    private EditText ForgetPasswordEmail_editText;
    private Button btn_resetEmail;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initView();

        auth = FirebaseAuth.getInstance();

        btn_resetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void initView() {
        // 初始化控件
        ForgetPasswordEmail_editText = findViewById(R.id.ForgetPasswordEmail_editText);
        btn_resetEmail = findViewById(R.id.btn_resetEmail);
        progressBar = findViewById(R.id.progressBar);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            //返回到HomePage
            case R.id.BackToHome:
                Intent intent = new Intent(ForgetPassword.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void resetPassword() {
        String email = ForgetPasswordEmail_editText.getText().toString().trim();

        if(email.isEmpty()){
            ForgetPasswordEmail_editText.setError("Email is required!");
            ForgetPasswordEmail_editText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ForgetPasswordEmail_editText.setError("Please provide valid email");
            ForgetPasswordEmail_editText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(ForgetPassword.this, "Try again!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}