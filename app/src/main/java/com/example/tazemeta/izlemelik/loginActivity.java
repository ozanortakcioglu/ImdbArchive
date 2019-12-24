package com.example.tazemeta.izlemelik;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    private EditText userMail,userPassword;
    private Button btnLogin;
    private ProgressBar loginProgess;
    private ImageView img;
    private TextView kayitOl;

    private FirebaseAuth mAuth;
    private Intent home;
    private Intent register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userMail=findViewById(R.id.login_mail);
        userPassword=findViewById(R.id.login_password);
        btnLogin=findViewById(R.id.loginBtn);
        loginProgess=findViewById(R.id.login_progress);
        kayitOl = findViewById(R.id.textView);
        img = findViewById(R.id.login_photo);
        mAuth=FirebaseAuth.getInstance();
        home=new Intent(this,HomeActivity.class);
        register=new Intent(this,registerActivity.class);
        loginProgess.setVisibility(View.INVISIBLE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProgess.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);
                final String mail=userMail.getText().toString();
                final String pass=userPassword.getText().toString();
                if(mail.isEmpty() || pass.isEmpty()){
                    Toast.makeText(loginActivity.this, "Lütfen boş alanları doldurun.", Toast.LENGTH_SHORT).show();
                    loginProgess.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                }
                else
                {
                    signIn(mail,pass);
                }
            }
        });
    }

    private void signIn(String mail, String pass) {

        mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    loginProgess.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                    Toast.makeText(loginActivity.this,    "Giriş başarılı", Toast.LENGTH_SHORT).show();
                    startActivity(home);
                    finish();

                }
                else
                {
                    Toast.makeText(loginActivity.this,   task.getException().getMessage()+ "", Toast.LENGTH_SHORT).show();
                    loginProgess.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                }
            }
        });

    }
    public void reg(View v)
    {
        Intent i = new Intent();
        i.setClass(this,registerActivity.class);
        startActivity(i);
        finish();
    }
}
