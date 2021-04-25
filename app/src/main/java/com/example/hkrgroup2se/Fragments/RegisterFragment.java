package com.example.hkrgroup2se.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hkrgroup2se.R;
import com.example.hkrgroup2se.Skeleton.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterFragment extends Fragment {

    Button registerButton;
    EditText firstName, lastName, email, password;
    TextView goBack;
    FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        goBack = view.findViewById(R.id.gobackTextField);
        firstName = view.findViewById(R.id.firstnameTextField);
        lastName = view.findViewById(R.id.LastnameTextField);
        email = view.findViewById(R.id.emailTextField);
        password = view.findViewById(R.id.LoginPasswordTextField);
        registerButton = view.findViewById(R.id.registerButton);
        firebaseAuth = FirebaseAuth.getInstance();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //VALIDATE FIELDS
                String FN = firstName.getText().toString().trim();
                String LN = lastName.getText().toString().trim();
                String MAIL = email.getText().toString().trim();
                String PW = password.getText().toString().trim();

                if (TextUtils.isEmpty(FN)) {
                    firstName.setError("First name is required");
                    return;
                }
                if (TextUtils.isEmpty(LN)) {
                    lastName.setError("Last name is required");
                    return;
                }
                if (TextUtils.isEmpty(MAIL)) {
                    email.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(PW)) {
                    password.setError("Password is required");
                    return;
                }

                // CREATING FIREBASE USER
                firebaseAuth.createUserWithEmailAndPassword(MAIL, PW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("on complete auth", "on complete auth");
                            // PUSHING CREATED USER TO DATABASE
                            String UID = firebaseAuth.getUid();
                            Log.e("test uid", UID);
                            User user = new User(FN, LN, MAIL, UID);
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("email", MAIL);
                            db.collection("users").document(UID).set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.e("complete", "complete");
                                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_homeFragment);
                                }
                            });

                        } else {
                            Toast.makeText(getContext(),"ERROR",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        // GO BACK TO HOME FRAGMENT
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_homeFragment);
            }
        });

        return view;
    }
}