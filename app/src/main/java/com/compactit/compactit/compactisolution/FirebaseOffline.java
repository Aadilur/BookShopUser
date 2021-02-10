package com.compactit.compactit.compactisolution;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseOffline extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
