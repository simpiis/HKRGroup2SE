package com.example.hkrgroup2se.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;

import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.example.hkrgroup2se.R;
import com.example.hkrgroup2se.Skeleton.ScannerViewModel;
import com.google.zxing.Result;


public class ScannerFragment extends Fragment {
    private static final String TAG = "ScannerFragment";
    private CodeScanner mCodeScanner;
    ImageButton imageButtonBack;

    //ViewModel for scanner
    ScannerViewModel scannerViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Activity activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        CodeScannerView scannerView = view.findViewById(R.id.scanner_view);
        imageButtonBack = view.findViewById(R.id.imageButtonBack);
        scannerViewModel = new ViewModelProvider(getActivity()).get(ScannerViewModel.class);

        //Scanner settings
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setCamera(CodeScanner.CAMERA_BACK);
        mCodeScanner.setFormats(CodeScanner.ONE_DIMENSIONAL_FORMATS);
        mCodeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        mCodeScanner.setScanMode(ScanMode.CONTINUOUS);
        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setFlashEnabled(false);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Scanned barcode: " + result.getText(), Toast.LENGTH_SHORT).show();
                        scannerViewModel.setScannedBarcode(result.getText());
                        try {
                            Navigation.findNavController(view).navigate(R.id.action_scannerFragment_pop);
                        } catch (Exception e) {
                            Log.i(TAG, "NAV ERROR");
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_scannerFragment_pop);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}