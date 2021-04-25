package com.example.hkrgroup2se.Skeleton;


import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class User {

    String firstName;
    String lastName;
    String email;
    String userUID;
    FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference("Users");

    public void pushToDatabase(String UID) {

        Log.e("in push", "in push");


//       Map<String,String> data = new HashMap<>();
//       data.put("name",firstName);
//       data.put("email",email);
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        db.collection("users").document(UID).set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("test", "in push");
            }
        });


//        myRef.push().setValue(this);
    }

    public User() {

    }

    public User(String firstName, String lastName, String email, String userUID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userUID = userUID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
}
