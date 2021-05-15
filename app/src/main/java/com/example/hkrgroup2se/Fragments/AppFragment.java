package com.example.hkrgroup2se.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hkrgroup2se.R;
import com.example.hkrgroup2se.Skeleton.Grocery;
import com.example.hkrgroup2se.Skeleton.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AppFragment extends Fragment {
    Button addGroceryFragment, inventoryButton, shoppingListButton, wasteStatButton, logOut;
    EditText email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app, container, false);
        email = view.findViewById(R.id.LoggedInUser);
        checkCurrentUser();
        inventoryButton = view.findViewById(R.id.inventoryButton);
        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_appFragment_to_inventoryFragment);
            }
        });

        addGroceryFragment = view.findViewById(R.id.addGroceryFragment);
        addGroceryFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_appFragment_to_addGroceriesFragment);
            }
        });

        shoppingListButton = view.findViewById(R.id.shoppingListButton);
        shoppingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_appFragment_to_shoppingListFragment);
            }
        });

        wasteStatButton = view.findViewById(R.id.wasteStatButton);
        wasteStatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_appFragment_to_wasteStatFragment);
            }
        });

        logOut = view.findViewById(R.id.logOutButton);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(view).navigate(R.id.action_appFragment_to_homeFragment);
            }
        });



        return view;
    }

    public void checkCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email.setText(user.getEmail());
        } else {

        }
    }

    public String getCurrentUser() {
        FirebaseUser getCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        return getCurrentUser.getEmail();
    }
}