package com.example.hkrgroup2se.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hkrgroup2se.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AppFragment extends Fragment {
    Button addGroceryFragment, inventoryButton, shoppingListButton, wasteStatButton, logOut,quizButton;
    EditText email;
    private SharedPreferences sharedPref;
    Context context;

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

        //Shared preferences
        context = getActivity();
        sharedPref = context.getSharedPreferences("settings", context.MODE_PRIVATE);

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

        quizButton = view.findViewById(R.id.quizButton);
        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_appFragment_to_quizFragment);
            }
        });

        logOut = view.findViewById(R.id.logOutButton);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("rememberMe", false);
                editor.commit();
                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(view).navigate(R.id.action_appFragment_pop);
            }
        });


        return view;
    }

    public void checkCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email.setText(user.getEmail());
        } else {
            email.setText("Cant resolve user");
        }
    }


}