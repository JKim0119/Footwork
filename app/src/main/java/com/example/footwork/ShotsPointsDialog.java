package com.example.footwork;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ShotsPointsDialog extends DialogFragment implements SeekBar.OnSeekBarChangeListener {
    AlertDialog.Builder builder;
    TextView shotsText, pointsText, timeShotsText, timePointsText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.setsgoals_dialog, null);

        shotsText = view.findViewById(R.id.shotsText);
        pointsText = view.findViewById(R.id.pointsText);
        timeShotsText = view.findViewById(R.id.timeShotsText);
        timePointsText = view.findViewById(R.id.timePointsText);

        SeekBar seekBarShots = view.findViewById(R.id.seekBarShots);
        SeekBar seekBarPoints = view.findViewById(R.id.seekBarPoints);
        SeekBar seekBarTimeShots = view.findViewById(R.id.seekBarTimeShots);
        SeekBar seekBarTimePoints = view.findViewById(R.id.seekBarTimePoints);

        shotsText.setText(String.valueOf(seekBarShots.getProgress()));
        pointsText.setText(String.valueOf(seekBarPoints.getProgress()));
        timeShotsText.setText(String.valueOf((float) (seekBarTimeShots.getProgress() / 10.0)));
        timePointsText.setText(String.valueOf(seekBarTimePoints.getProgress()));

        seekBarShots.setOnSeekBarChangeListener(this);
        seekBarPoints.setOnSeekBarChangeListener(this);
        seekBarTimeShots.setOnSeekBarChangeListener(this);
        seekBarTimePoints.setOnSeekBarChangeListener(this);


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

        return builder.create();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.seekBarShots:
                shotsText.setText(String.valueOf(i));
                break;
            case R.id.seekBarPoints:
                pointsText.setText(String.valueOf(i));
                break;
            case R.id.seekBarTimeShots:
                float value = (float) (i / 10.0);
                timeShotsText.setText(String.valueOf(value));
                break;
            case R.id.seekBarTimePoints:
                timePointsText.setText(String.valueOf(i));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
