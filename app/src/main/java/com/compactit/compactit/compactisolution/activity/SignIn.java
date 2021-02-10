package com.compactit.compactit.compactisolution.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.compactit.compactit.compactisolution.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    EditText Email, Pass;
    Button SignIn;

    TextView toSignUp, forgetPass;
    String email, pass;


    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        auth = FirebaseAuth.getInstance();


        initData();
        OnClickData();
    }

    private void OnClickData() {

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                email = Email.getText().toString();
                pass = Pass.getText().toString();
                if (email.equals("") || pass.equals("")) {
                    Toast.makeText(SignIn.this, "All fields are required...", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.length() < 6) {
                        Pass.setError("Password Length should be 6 or more...");
                    } else {
                        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignIn.this, "Error... : " + e, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });


        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(SignIn.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);


                AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this);
                builder.setTitle("Resetting Your Password...");
                builder.setMessage("Inter You Valid Email Used in This Account...");
                builder.setView(input);

                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = input.getText().toString();
                        if (!email.equals("")) {
                            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(SignIn.this, "Success...! Please Check Your Email Inbox...!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignIn.this, "Something Went Wrong...! "+e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                builder.setNegativeButton("No", null);
                builder.show();

            }
        });

    }

    private void initData() {
        Email = findViewById(R.id.SignIn_emailEditTextField);
        Pass = findViewById(R.id.SignIn_passEditTextField);
        SignIn = findViewById(R.id.SignIn_signInBtn);

        toSignUp = findViewById(R.id.SignIn_CreateAccountTxt);
        forgetPass = findViewById(R.id.SignIn_ResetPassword);
    }
}