package com.example.hkrgroup2se.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hkrgroup2se.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {

    Button login, signUp;
    FirebaseAuth firebaseAuth;
    EditText email, password;
    TextView forgotPassword;
    CheckBox checkBoxRememberMe;
    Context context;
    View view;
    private String userKey;

    public String getUserKey() {
        return userKey;
    }

    //Shared preferences for remembered email
    private SharedPreferences sharedPref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        login = view.findViewById(R.id.loginButton);
        signUp = view.findViewById(R.id.signupButton);
        email = view.findViewById(R.id.loginEmailTextField);
        password = view.findViewById(R.id.LoginPasswordTextField);
        forgotPassword = view.findViewById(R.id.forgotPasswordTextView);
        checkBoxRememberMe = view.findViewById(R.id.rememberMeCheckBox);
        firebaseAuth = FirebaseAuth.getInstance();

        context = getActivity();
        sharedPref = context.getSharedPreferences("settings", context.MODE_PRIVATE);


        checkBoxRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    setAutoLogin();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EMAIL = email.getText().toString().trim();
                String PW = password.getText().toString().trim();

                if (TextUtils.isEmpty(EMAIL)) {
                    email.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(PW)) {
                    password.setError("Password is required");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(EMAIL, PW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setAutoLogin();
                            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_appFragment);
                        } else {
                            // TODO: ERROR MESSAGE
                        }
                    }
                });
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_forgotPasswordFragment);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        autoLogin();
    }

    public void autoLogin() {
        Boolean rememberMe = sharedPref.getBoolean("rememberMe", false);
        if (rememberMe && firebaseAuth.getCurrentUser() != null) {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_appFragment);
        }
    }

    public void setAutoLogin() {
        SharedPreferences.Editor editor = sharedPref.edit();
        if (checkBoxRememberMe.isChecked()) {
            editor.putBoolean("rememberMe", true);
            editor.commit();
        } else {
            editor.putBoolean("rememberMe", false);
            editor.commit();
        }
    }
}

