package com.compactit.compactit.compactisolution.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.compactit.compactit.compactisolution.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    EditText Fname, Phone, Address, Email, Pass;
    Button SignUp;
    TextView toSignIn;
    String fname,phone,address, email, pass;

    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();


        initData();
        OnClickData();
    }

    private void OnClickData() {

        toSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = Fname.getText().toString();
                phone = Phone.getText().toString();
                address = Address.getText().toString();
                email = Email.getText().toString();
                pass = Pass.getText().toString();

                if (fname.equals("") || phone.equals("") || address.equals("") || email.equals("") || pass.equals("")){
                    Toast.makeText(SignUp.this, "All fields are required...", Toast.LENGTH_SHORT).show();
                }else {
                    if (pass.length()<6){
                        Pass.setError("Password Length should be 6 or more...");
                    }else {
                        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser firebaseUser = auth.getCurrentUser();
                                    assert firebaseUser != null;
                                    String userId = firebaseUser.getUid();
                                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                                    HashMap<String, String> data = new HashMap<>();
                                    data.put("name",fname);
                                    data.put("phone",phone);
                                    data.put("address",address);
                                    data.put("email",email);
                                    data.put("pic","");
                                    data.put("id",userId);

                                    databaseReference.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){
                                                Intent intent = new Intent(SignUp.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            }else {
                                                Toast.makeText(SignUp.this, "You can't sign up with this Email or Password.......", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignUp.this, "Error... " + e, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUp.this, "Error... " + e, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });

    }

    private void initData() {
        Fname = findViewById(R.id.SignUp_nameEditTextField);
        Phone = findViewById(R.id.SignUp_phoneEditTextField);
        Address = findViewById(R.id.SignUp_addressEditTextField);
        Email = findViewById(R.id.SignUp_emailEditTextField);
        Pass = findViewById(R.id.SignUp_passEditTextField);

        SignUp = findViewById(R.id.SignUp_signUpBtn);
        toSignIn = findViewById(R.id.SignUp_toLoginTxt);
    }
}