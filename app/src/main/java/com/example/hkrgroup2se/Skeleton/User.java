package com.example.hkrgroup2se.Skeleton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {

    String firstName;
    String lastName;
    String email;
    String userUID;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");

    public void pushToDatabase(){
        myRef.push().setValue(this);
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
