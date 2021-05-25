package com.example.hkrgroup2se.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.hkrgroup2se.R;
import com.example.hkrgroup2se.Skeleton.DBConnect;
import com.example.hkrgroup2se.Skeleton.Waste;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class WasteStatFragment extends Fragment {

    FloatingActionButton backButton;
    TextView moneyText, amountText, infoText;
    DBConnect dbConnect = DBConnect.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    ArrayList<Waste> wasteArrayList = new ArrayList<>();
    double totalWaste, totalMoney;
    String monthChosen, yearChosen;
    NumberPicker pickerMonth, pickerYear;
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private static final String[] MONTHS = new String[]{
            "-", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December", "All months"
    };

    private static final String[] YEAR = new String[]{
            "-", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028","2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040", "2041",
            "2042", "2043", "2044", "2045", "2046", "2047", "2048", "2049", "2050"
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waste_stat, container, false);
        backButton = view.findViewById(R.id.wasteStatBack);
        moneyText = view.findViewById(R.id.moneyText);
        amountText = view.findViewById(R.id.amountText);
        pickerMonth = view.findViewById(R.id.pickMonth);
        pickerYear = view.findViewById(R.id.pickYear);
        infoText = view.findViewById(R.id.infoWasted);


        pickerMonth.setMaxValue(MONTHS.length - 1);
        pickerMonth.setMinValue(0);
        pickerMonth.setDisplayedValues(MONTHS);

        pickerYear.setMaxValue(YEAR.length-1);
        pickerYear.setMinValue(0);
        pickerYear.setDisplayedValues(YEAR);


        pickerMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                monthChosen = MONTHS[pickerMonth.getValue()];
                updateGui(yearChosen, monthChosen);

            }
        });


        pickerYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                yearChosen = YEAR[pickerYear.getValue()];
                updateGui(yearChosen, monthChosen);

            }
        });

        infoText.setText("Amount wasted in");
        if (yearChosen == null && monthChosen == null){
            moneyText.setText("Please choose a Date");
            amountText.setText("Down below");
        }


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_wasteStatFragment_to_appFragment);
            }
        });


        String hashedEmail = dbConnect.getCurrentHash();
        databaseReference = database.getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = ds.child("Email").getValue().toString();
                    if (email.equals(hashedEmail)) {
                        databaseReference = database.getReference("User/" + ds.getKey() + "/Waste");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    Waste waste = ds.getValue(Waste.class);
                                    wasteArrayList.add(waste);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        return view;
    }

    public void updateGui(String year, String month) {
        if (month != null && year != null  && !month.equals("-") && !year.equals("-")) {
            totalMoney = 0;
            totalWaste = 0;
            if (!month.equals("All months")) {
                for (int i = 0; i < wasteArrayList.size(); i++) {
                    if (wasteArrayList.get(i).getTypeOfAmountForWaste().equals("Kilogram") && wasteArrayList.get(i).getWasteDate().contains(month) && wasteArrayList.get(i).getWasteDate().contains(year)) {
                        totalWaste = totalWaste + Double.parseDouble(wasteArrayList.get(i).getAmountOfWaste());
                        totalMoney = totalMoney + wasteArrayList.get(i).getPriceOfWaste();
                    } else if (wasteArrayList.get(i).getTypeOfAmountForWaste().equals("gram") && wasteArrayList.get(i).getWasteDate().contains(month) && wasteArrayList.get(i).getWasteDate().contains(year)) {
                        totalWaste = totalWaste + (Double.parseDouble(wasteArrayList.get(i).getAmountOfWaste()) * 0.001);
                        totalMoney = totalMoney + wasteArrayList.get(i).getPriceOfWaste();
                    }
                }
            } else {
                for (int i = 0; i < wasteArrayList.size(); i++) {
                    if (wasteArrayList.get(i).getTypeOfAmountForWaste().equals("Kilogram") && wasteArrayList.get(i).getWasteDate().contains(year)) {
                        totalWaste = totalWaste + Double.parseDouble(wasteArrayList.get(i).getAmountOfWaste());
                        totalMoney = totalMoney + wasteArrayList.get(i).getPriceOfWaste();
                    } else if (wasteArrayList.get(i).getTypeOfAmountForWaste().equals("gram") && wasteArrayList.get(i).getWasteDate().contains(year)) {
                        totalWaste = totalWaste + (Double.parseDouble(wasteArrayList.get(i).getAmountOfWaste()) * 0.001);
                        totalMoney = totalMoney + wasteArrayList.get(i).getPriceOfWaste();
                    }
                }
            }
            if (monthChosen.equals("All months")){
                infoText.setText("Amount wasted in " + monthChosen.toLowerCase() +" " + yearChosen);
            }else{
                infoText.setText("Amount wasted in " + monthChosen +" " + yearChosen);
            }
            moneyText.setText(decimalFormat.format(totalMoney) + "kr");
            amountText.setText(decimalFormat.format(totalWaste)+ "kg");
        }else {
            infoText.setText("Amount wasted in ");
            moneyText.setText("Please choose a Date");
            amountText.setText("Down below");
        }
    }
}