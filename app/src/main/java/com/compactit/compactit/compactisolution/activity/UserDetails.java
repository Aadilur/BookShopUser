package com.compactit.compactit.compactisolution.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.compactit.compactit.compactisolution.R;
import com.compactit.compactit.compactisolution.adapter.UserOrders_adapter;
import com.compactit.compactit.compactisolution.model.Order_Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDetails extends AppCompatActivity {

    TextView name,email,phone,add,uid;
    DatabaseReference reference;
    FirebaseUser user;
    RecyclerView recyclerView;

    UserOrders_adapter userOrders_adapter;
    List<Order_Model> orderModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        phone = findViewById(R.id.user_phoneNumber);
        add = findViewById(R.id.user_address);
        uid = findViewById(R.id.user_id);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    name.setText("Name: "+Objects.requireNonNull(snapshot.child("name").getValue()).toString()+"");
                    email.setText("Email: "+Objects.requireNonNull(snapshot.child("email").getValue()).toString()+"");
                    phone.setText("Phone: "+Objects.requireNonNull(snapshot.child("phone").getValue()).toString()+"");
                    add.setText("Address: "+Objects.requireNonNull(snapshot.child("address").getValue()).toString()+"");
                    uid.setText("UID: "+Objects.requireNonNull(snapshot.child("id").getValue()).toString()+"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        recyclerView = findViewById(R.id.userOrder);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        orderModelList = new ArrayList<>();

        DatabaseReference order = FirebaseDatabase.getInstance().getReference().child("pendingOrder");
        order.orderByChild("time").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderModelList.clear();
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Order_Model order_model = dataSnapshot.getValue(Order_Model.class);

                        try {
                            assert order_model != null;
                            if ( order_model.getUid().equals(user.getUid())) {
                                orderModelList.add(order_model);
                            }
                        }catch (Exception ignored){}

                    }
                }

                userOrders_adapter = new UserOrders_adapter(UserDetails.this,orderModelList);
                recyclerView.setAdapter(userOrders_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}