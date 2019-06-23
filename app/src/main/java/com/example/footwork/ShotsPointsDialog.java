package com.example.footwork;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ShotsPointsDialog extends DialogFragment implements SeekBar.OnSeekBarChangeListener {
    AlertDialog.Builder builder;
    TextView shotsText, pointsText, timeShotsText, timePointsText;
    ShotsPointsDialogListener listener;
    int[] shotspoints;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.shotspoints_dialog, null);


        final SeekBar shotsSeekBar = view.findViewById(R.id.shotsSeekBar);
        shotsSeekBar.setOnSeekBarChangeListener(this);
        final SeekBar pointsSeekBar = view.findViewById(R.id.pointsSeekBar);
        pointsSeekBar.setOnSeekBarChangeListener(this);
        final SeekBar timeShotsSeekBar = view.findViewById(R.id.timeShotsSeekBar);
        timeShotsSeekBar.setOnSeekBarChangeListener(this);
        final SeekBar timePointsSeekBar = view.findViewById(R.id.timePointsSeekBar);
        timePointsSeekBar.setOnSeekBarChangeListener(this);

        shotsText = view.findViewById(R.id.shotsText);
        pointsText = view.findViewById(R.id.pointsText);
        timeShotsText = view.findViewById(R.id.timeShotsText);
        timePointsText = view.findViewById(R.id.timePointsText);

        shotsSeekBar.setProgress(shotspoints[0]);
        pointsSeekBar.setProgress(shotspoints[1]);
        timeShotsSeekBar.setProgress(shotspoints[2]);
        timePointsSeekBar.setProgress(shotspoints[3]);

        shotsText.setText(String.valueOf(shotsSeekBar.getProgress()));
        pointsText.setText(String.valueOf(pointsSeekBar.getProgress()));
        timeShotsText.setText(String.valueOf((float) (timeShotsSeekBar.getProgress() / 10.0)));
        timePointsText.setText(String.valueOf(timePointsSeekBar.getProgress()));

        builder.setView(view)
                .setTitle("Shots/Points")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int[] temp = new int[4];
                        temp[0] = shotsSeekBar.getProgress();
                        temp[1] = pointsSeekBar.getProgress();
                        temp[2] = timeShotsSeekBar.getProgress();
                        temp[3] = timePointsSeekBar.getProgress();
                        listener.applyShotsPoints(temp);

                    }
                });

        return builder.create();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.shotsSeekBar:
                shotsText.setText(String.valueOf(i));
                break;
            case R.id.pointsSeekBar:
                pointsText.setText(String.valueOf(i));
                break;
            case R.id.timeShotsSeekBar:
                timeShotsText.setText(String.valueOf((float) (i / 10.0)));
                break;
            case R.id.timePointsSeekBar:
                timePointsText.setText(String.valueOf(i));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ShotsPointsDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ShotsPointsDialogListener");
        }
    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public interface ShotsPointsDialogListener {
        void applyShotsPoints(int[] newShotsPoints);
    }

    public void newShotsPoints(int[] newShotsPoints) {
        shotspoints = newShotsPoints;
    }
}
