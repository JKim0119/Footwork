package com.example.footwork;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class WeightDialog extends DialogFragment implements NumberPicker.OnValueChangeListener {
//    private EditText editUsername, editPassword;
    AlertDialog.Builder builder;
    TextView textPercentage;
    int count;
    int[] intP, intPV;
    WeightDialogListener listener;
    AlertDialog dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.weight_dialog, null);

        textPercentage = view.findViewById(R.id.percentage);

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
            numberPickerA.setEnabled(false);
        }
        else {
            numberPickerA.setValue(intPV[0]);
            count += intPV[0];
        }
        if(intP[1] == 0) {
            numberPickerB.setEnabled(false);
        }
        else {
            numberPickerB.setValue(intPV[1]);
            count += intPV[1];
        }
        if(intP[2] == 0) {
            numberPickerC.setEnabled(false);
        }
        else {
            numberPickerC.setValue(intPV[2]);
            count += intPV[2];
        }
        if(intP[3] == 0) {
            numberPickerD.setEnabled(false);
        }
        else {
            numberPickerD.setValue(intPV[3]);
            count += intPV[3];
        }
        textPercentage.setText("Total Percentage: " + count);


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
                        int[] temp = new int[4];
                        temp[0] = numberPickerA.getValue();
                        temp[1] = numberPickerB.getValue();
                        temp[2] = numberPickerC.getValue();
                        temp[3] = numberPickerD.getValue();
                        listener.applyWeights(temp);
                    }
                });

        dialog = builder.create();
        Toast.makeText(getActivity(),"Total percentage must be 100",Toast.LENGTH_LONG).show();

        // Create the AlertDialog object and return it
        return dialog;
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
        positiveButtonChecker();
    }


    public void newInstance(int[] intPositions, int[] intPosVal) {
        intP = intPositions;
        intPV = intPosVal;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (WeightDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement WeightDialogListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        positiveButtonChecker();
    }

    public interface WeightDialogListener {
        void applyWeights(int[] newPosVal);
    }

    public void positiveButtonChecker() {
        if(count != 100) {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        }
        else {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
        }
    }
}
