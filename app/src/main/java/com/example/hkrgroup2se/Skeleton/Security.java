package com.example.hkrgroup2se.Skeleton;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.LocalDate;

public class Security {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate checkDate(String day, String month, String year){
        String correctDate = year + "-";
        if(month.length() < 2){
            correctDate += "0"+month;
        }else{
            correctDate += month;
        }
        correctDate += "-";
        if(day.length() < 2){
            correctDate += "0"+day;
        }else{
            correctDate += day;
        }
        return LocalDate.parse(correctDate);
    }

    public boolean checkString(String input) {
        if (input == null) {
            return false;
        }

        int len = input.length();

        for (int i = 0; i < len; i++) {
            if ((Character.isLetter(input.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkEmail(String input) {
        if (input.contains("@") & input.contains(".")) {
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuffer hexString = new StringBuffer();
            for (Byte e : encodedHash) {
                String hex = Integer.toHexString(0xff & e);
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return ("Error");
        }
    }
}
