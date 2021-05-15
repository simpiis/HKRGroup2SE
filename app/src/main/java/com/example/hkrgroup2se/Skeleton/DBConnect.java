package com.example.hkrgroup2se.Skeleton;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.hkrgroup2se.Fragments.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DBConnect<T> {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private static DBConnect single_instance = null;
    private static Security security = new Security();

    public static DBConnect getInstance() {
        if (single_instance == null) {
            single_instance = new DBConnect();
        }
        return single_instance;
    }

    private DBConnect() {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getCurrentHash(){
        FirebaseUser getCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        return security.hashString(getCurrentUser.getEmail());
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public <T> void addInformation(T info) {
        if (info instanceof User) {
            databaseReference = database.getReference("User");
            String id = databaseReference.push().getKey();
            databaseReference = database.getReference("User/" + id + "/Email");
            String hash = security.hashString(((User) info).getEmail());
            databaseReference.setValue(hash);
        } else if (info instanceof Grocery) {
            FirebaseUser getCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
            String hashedEmail = security.hashString(getCurrentUser.getEmail());
            databaseReference = database.getReference("User");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String email = ds.child("Email").getValue().toString();
                        if (email.equals(hashedEmail)) {
                            databaseReference = database.getReference("User/" + ds.getKey() + "/Inventory");
                            databaseReference.push().setValue(info);
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        } else if (info instanceof Waste){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String hashedEmail = security.hashString(currentUser.getEmail());
        databaseReference = database.getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String email = ds.child("Email").getValue().toString();
                    if (email.equals(hashedEmail)){
                        databaseReference = database.getReference("User/" + ds.getKey() + "/Waste");
                        databaseReference.push().setValue(info);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    }

}
