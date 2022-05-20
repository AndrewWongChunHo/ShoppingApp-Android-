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
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_text.R;
import com.example.project_text.fragments.HomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText mEtLoginactivityEmail;
    private EditText mEtLoginactivityPassword;
    private Button mBtLoginactivityLogin, google_signIn;
    private ProgressBar progressBar;
    private TextView btn_forgetPassword;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);

        google_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        mAuth = FirebaseAuth.getInstance();

    }

    private void initView() {
        // 初始化控件
        mBtLoginactivityLogin = findViewById(R.id.btn_login);
        mEtLoginactivityEmail = findViewById(R.id.Email_editText);
        mEtLoginactivityPassword = findViewById(R.id.Password_editText);

        progressBar = findViewById(R.id.progressBar);

        btn_forgetPassword = findViewById(R.id.btn_forgetPassword);
        btn_forgetPassword.setOnClickListener(this);

        google_signIn = findViewById(R.id.google_signIn);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            //返回到HomePage
            case R.id.LoginBackToHome:
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                userLogin();
                break;
            case R.id.btn_forgetPassword:
                intent = new Intent(Login.this, ForgetPassword.class);
                startActivity(intent);
                break;
        }
    }

    private void userLogin() {
        String email = mEtLoginactivityEmail.getText().toString().trim();
        String password = mEtLoginactivityPassword.getText().toString().trim();

        if (email.isEmpty()) {
            mEtLoginactivityEmail.setError("Email is required!");
            mEtLoginactivityEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEtLoginactivityEmail.setError("Please enter a valid email!");
            mEtLoginactivityEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            mEtLoginactivityPassword.setError("Password is required!");
            mEtLoginactivityPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    startActivity(new Intent(Login.this, HomeActivity.class));
                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(Login.this, "Your Email or Password is incorrect, Please check again!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void signIn() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                HomeActivity();
            } catch (ApiException e) {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void HomeActivity() {

        finish();
        Toast.makeText(Login.this, "Login successful", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }
}