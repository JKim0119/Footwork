package com.example.footwork;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class WeightDialog extends DialogFragment implements NumberPicker.OnValueChangeListener {
//    private EditText editUsername, editPassword;
    AlertDialog.Builder builder;
    TextView textPercentage;
    int count;
    int[] intP;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.weight_dialog, null);

        textPercentage = view.findViewById(R.id.percentage);
        TextView npA = view.findViewById(R.id.npA);
        TextView npB = view.findViewById(R.id.npB);
        TextView npC = view.findViewById(R.id.npC);
        TextView npD = view.findViewById(R.id.npD);

        final NumberPicker numberPickerA = view.findViewById(R.id.numberPickerA);
        numberPickerA.setMinValue(0);
        numberPickerA.setMaxValue(100);
        numberPickerA.setWrapSelectorWheel(false);
        numberPickerA.setOnValueChangedListener(this);

        final NumberPicker numberPickerB = view.findViewById(R.id.numberPickerB);
        numberPickerB.setMinValue(0);
        numberPickerB.setMaxValue(100);
        numberPickerB.setWrapSelectorWheel(false);
        numberPickerB.setOnValueChangedListener(this);

        final NumberPicker numberPickerC = view.findViewById(R.id.numberPickerC);
        numberPickerC.setMinValue(0);
        numberPickerC.setMaxValue(100);
        numberPickerC.setWrapSelectorWheel(false);
        numberPickerC.setOnValueChangedListener(this);

        final NumberPicker numberPickerD = view.findViewById(R.id.numberPickerD);
        numberPickerD.setMinValue(0);
        numberPickerD.setMaxValue(100);
        numberPickerD.setWrapSelectorWheel(false);
        numberPickerD.setOnValueChangedListener(this);

        if(intP[0] == 0) {
            numberPickerA.setVisibility(View.INVISIBLE);
            npA.setVisibility(View.INVISIBLE);
        }
        else {
            npA.setText(String.valueOf(intP[0]));
        }
        if(intP[1] == 0) {
            numberPickerB.setVisibility(View.INVISIBLE);
            npB.setVisibility(View.INVISIBLE);
        }
        else {
            npB.setText(String.valueOf(intP[1]));
        }
        if(intP[2] == 0) {
            numberPickerC.setVisibility(View.INVISIBLE);
            npC.setVisibility(View.INVISIBLE);
        }
        else {
            npC.setText(String.valueOf(intP[2]));
        }
        if(intP[3] == 0) {
            numberPickerD.setVisibility(View.INVISIBLE);
            npD.setVisibility(View.INVISIBLE);
        }
        else {
            npD.setText(String.valueOf(intP[3]));
        }


        builder.setView(view)
                .setTitle("Weights of each position")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

//        editUsername = view.findViewById(R.id.dialogEmail);
//        editPassword = view.findViewById(R.id.dialogPassword);

        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal){
        //Process the changes here
        if(oldVal > newVal) {
            count--;
        }
        else {
            count++;
        }
        textPercentage.setText("Total Percentage: " + count);
    }


    public void newInstance(int[] intPositions) {
        intP = intPositions;
    }
}
