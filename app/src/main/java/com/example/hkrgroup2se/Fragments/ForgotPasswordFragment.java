package com.example.hkrgroup2se.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hkrgroup2se.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordFragment extends Fragment {
    public static final String TAG = "ForgotPasswordFragment";
    private FirebaseAuth auth;
    Button sendEmailButton, goBackButton;
    EditText emailTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        sendEmailButton = view.findViewById(R.id.forgotEmailSendButton);
        goBackButton = view.findViewById(R.id.forgotEmailBackButton);
        emailTextView = view.findViewById(R.id.forgotEmailTextField);
        auth = FirebaseAuth.getInstance();

        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTextView.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    emailTextView.setError("Email is required");
                    return;
                }

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                    Toast.makeText(getActivity(),
                                            "Email sent to " + email,
                                            Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(view).navigate(R.id.action_forgotPasswordFragment_pop);
                                }
                            }
                        });
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_forgotPasswordFragment_pop);
            }
        });
        return view;
    }
}