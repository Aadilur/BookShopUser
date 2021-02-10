package com.compactit.compactit.compactisolution.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.compactit.compactit.compactisolution.R;
import com.compactit.compactit.compactisolution.adapter.EditBooks_Adapter;
import com.compactit.compactit.compactisolution.adapter.SliderAdapterExample;
import com.compactit.compactit.compactisolution.model.Books_Model;
import com.compactit.compactit.compactisolution.model.SliderItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseBookRef;
    MaterialToolbar materialToolbar;

    EditBooks_Adapter editBooks_adapter;

    RecyclerView recyclerView;
    List<Books_Model> booksModelList;


    SliderView sliderView;
    SliderAdapterExample adapter;
    List<String> sliderList;
    DatabaseReference databaseBookRef2;

    TextView cart;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    Button verifyB;
    TextView verifyT;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsesPermission();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();



        verifyB = findViewById(R.id.verifyButton);
        verifyT = findViewById(R.id.verifyText);

        verifyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                firebaseUser.reload();

                if (firebaseUser.isEmailVerified()) {
                    verifyB.setVisibility(View.GONE);
                    verifyT.setVisibility(View.GONE);
                } else {
                    firebaseUser.reload();
                    verifyB.setVisibility(View.VISIBLE);
                    if (!firebaseUser.isEmailVerified()){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        System.out.println(user.getEmail());
                        System.out.println(user.getUid());

                        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    verifyT.setVisibility(View.VISIBLE);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Something Went Wrong...!" + e, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }

        });




        TextView textView = findViewById(R.id.verifyText2);
        if (firebaseUser!=null && !firebaseUser.isAnonymous()){
            textView.setVisibility(View.GONE);
        }else {
            textView.setVisibility(View.VISIBLE);
            textView.setText("Please sign in to order books.");
        }

        materialToolbar = findViewById(R.id.materialToolbar);
        materialToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(materialToolbar);
        getSupportActionBar().setTitle("User");

        drawerLayout = findViewById(R.id.drawer_layout);
//        toggle = new ActionBarDrawerToggle(this,drawerLayout,materialToolbar,R.string.app_name,R.string.app_name);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.setDrawerIndicatorEnabled(true);
//        toggle.syncState();
        navigationView = findViewById(R.id.nav_view2);



        if (firebaseUser == null) {
            firebaseAuth.signInAnonymously();
        }


        try {


            if (firebaseUser.getEmail() != null && !firebaseUser.getEmail().equals("")) {
                navigationView.getMenu().setGroupVisible(R.id.drawer_menu_anonimas, false);
                navigationView.getMenu().setGroupVisible(R.id.drawer_menu_loggedIn, true);

                if (firebaseUser.isEmailVerified()) {
                    verifyB.setVisibility(View.GONE);
                    verifyT.setVisibility(View.GONE);
                } else {
                    firebaseUser.reload();
                    verifyB.setVisibility(View.VISIBLE);
                }
            } else {
                navigationView.getMenu().setGroupVisible(R.id.drawer_menu_anonimas, true);
                navigationView.getMenu().setGroupVisible(R.id.drawer_menu_loggedIn, false);
            }


        } catch (Exception ignored) {
        }





        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_menu_login:
                        try {

                            if (firebaseUser.isAnonymous()) {
                                firebaseUser.delete();
                            }
                        } catch (Exception ignored) {
                        }
                        Toast.makeText(MainActivity.this, "hola", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, SignIn.class);
                        startActivity(intent);
                        finish();
                        return true;

                    case R.id.drawer_menu_logout:
                        Toast.makeText(MainActivity.this, "hola", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        Intent intent2 = new Intent(MainActivity.this, SignIn.class);
                        startActivity(intent2);
                        finish();
                        return true;

                    case R.id.drawer_menu_author_d1:
                    case R.id.drawer_menu_author_d2:
                        Intent intent3 = new Intent(MainActivity.this, Author.class);
                        startActivity(intent3);
                        finish();
                        return true;
                    case R.id.drawer_menu_user:

                        Intent intent4 = new Intent(MainActivity.this,UserDetails.class);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent4);
                        finish();
                        return true;

                }
                return true;
            }
        });


        sliderView = findViewById(R.id.imageSlider);


        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();


        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });


        databaseBookRef2 = FirebaseDatabase.getInstance().getReference().child("Slider");
        sliderList = new ArrayList<>();

        databaseBookRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    sliderList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        sliderList.add(dataSnapshot.getValue().toString());
                    }

                    if (sliderList.size() == 0) {
                        sliderView.setVisibility(View.GONE);
                    } else sliderView.setVisibility(View.VISIBLE);

                    adapter = new SliderAdapterExample(MainActivity.this, sliderList);
                    sliderView.setSliderAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        databaseBookRef = FirebaseDatabase.getInstance().getReference().child("Books");
        recyclerView = findViewById(R.id.AllBook_RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        booksModelList = new ArrayList<>();
        databaseBookRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                booksModelList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Books_Model books_model = dataSnapshot.getValue(Books_Model.class);

                        booksModelList.add(books_model);
                    }
                }

                editBooks_adapter = new EditBooks_Adapter(MainActivity.this, booksModelList);
                recyclerView.setAdapter(editBooks_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


//


    public void siup(View view) {
        startActivity(new Intent(this, SignUp.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
//
//        View baseCount = menu.findItem(R.id.main_menu2).getActionView();
//        cart = baseCount.findViewById(R.id.cart_badge);
//        cart.setText("8");

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.main_menu2:
//                return true;
            case R.id.main_menu3:
                drawerLayout.openDrawer(GravityCompat.END);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    private void UsesPermission() {
        Dexter.withContext(this).withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.CALL_PHONE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                }
                if (report.isAnyPermissionPermanentlyDenied()) {
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

}