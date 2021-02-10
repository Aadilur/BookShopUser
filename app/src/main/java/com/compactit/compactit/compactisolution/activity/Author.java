package com.compactit.compactit.compactisolution.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.compactit.compactit.compactisolution.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Author extends AppCompatActivity {

    ImageView  img2;
    TextView textView1, textView2;


    DatabaseReference databaseBookRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);



        img2 = findViewById(R.id.authorImg2_authorDetails);
        textView1 = findViewById(R.id.textView1_authorDetails);
        textView2 = findViewById(R.id.textView2_authorDetails);
        databaseBookRef = FirebaseDatabase.getInstance().getReference().child("AuthorDetails");

        databaseBookRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String img = snapshot.child("img").getValue()+"";
                    if (!img.equals("")&& img!=null) {
                        Picasso.get().load(img).into(img2);
                    }else img2.setImageResource(R.drawable.profile_placeholder);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        textView1.setText(Html.fromHtml(Objects.requireNonNull(snapshot.child("txt1").getValue()).toString()+"", Html.FROM_HTML_MODE_COMPACT));
                        textView2.setText(Html.fromHtml(Objects.requireNonNull(snapshot.child("txt2").getValue()).toString()+"", Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        textView1.setText(Html.fromHtml(Objects.requireNonNull(snapshot.child("txt1").getValue()).toString()+""));
                        textView2.setText(Html.fromHtml(Objects.requireNonNull(snapshot.child("txt2").getValue()).toString()+""));
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Author.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}