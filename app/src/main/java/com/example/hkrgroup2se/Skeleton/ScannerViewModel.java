package com.example.hkrgroup2se.Skeleton;

import androidx.lifecycle.ViewModel;

public class ScannerViewModel extends ViewModel {
    private String scannedBarcode = "";

    public String getScannedBarcode() {
        return scannedBarcode;
    }

    public void setScannedBarcode(String barcode) {
        scannedBarcode = barcode.trim();
    }

    public void resetScannedBarcode() {
        scannedBarcode = "";
    }
}
