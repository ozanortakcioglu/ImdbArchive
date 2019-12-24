package com.example.tazemeta.izlemelik;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class registerActivity extends AppCompatActivity {
    private EditText userMail,userPassword,userPassword_again;
    private Button btnRegister;
    private ProgressBar loginProgess;
    private TextView signIntext;
    private FirebaseAuth mAuth;
    private Intent login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userMail=findViewById(R.id.login_mail);
        userPassword=findViewById(R.id.login_password);
        userPassword_again=findViewById(R.id.login_password_repeat);
        btnRegister=findViewById(R.id.loginBtn);
        signIntext = findViewById(R.id.textView2);
        loginProgess=findViewById(R.id.login_progress);
        loginProgess.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        login= new Intent(this,loginActivity.class);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=userMail.getText().toString();
                String pass=userPassword.getText().toString();
                String pass2=userPassword_again.getText().toString();
                btnRegister.setVisibility(View.INVISIBLE);
                loginProgess.setVisibility(View.VISIBLE);
                if(!pass.equals(pass2) || mail.isEmpty() || pass.isEmpty() || pass2.isEmpty())
                {
                    Toast.makeText(registerActivity.this, "Girdiğiniz bilgileri kontrol edin.", Toast.LENGTH_SHORT).show();
                    btnRegister.setVisibility(View.VISIBLE);
                    loginProgess.setVisibility(View.INVISIBLE);
                }
                else
                {
                    mAuth.createUserWithEmailAndPassword(mail,pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(registerActivity.this, "Hesap başarıyla oluşturuldu.", Toast.LENGTH_SHORT).show();
                                        startActivity(login);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(registerActivity.this, task.getException().getMessage() +"", Toast.LENGTH_SHORT).show();
                                        btnRegister.setVisibility(View.VISIBLE);
                                        loginProgess.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                }

            }
        });
        signIntext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(login);
                finish();
            }
        });
    }
}
