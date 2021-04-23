package com.example.hkrgroup2se.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.hkrgroup2se.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {

    Button login, signUp;
    FirebaseAuth firebaseAuth;
    EditText email, password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        login = view.findViewById(R.id.loginButton);
        signUp = view.findViewById(R.id.signupButton);
        email = view.findViewById(R.id.loginEmailTextField);
        password = view.findViewById(R.id.LoginPasswordTextField);
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EMAIL = email.getText().toString().trim();
                String PW = password.getText().toString().trim();

                if(TextUtils.isEmpty(EMAIL)){
                    email.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(PW)) {
                    password.setError("Password is required");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(EMAIL,PW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_appFragment);
                        } else {
                           // TODO: ERROR MESSAGE
                        }
                    }
                });
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_registerFragment);
            }
        });

        return view;
    }

}

