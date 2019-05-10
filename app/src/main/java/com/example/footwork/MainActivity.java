package com.example.footwork;

import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        WeightDialog.WeightDialogListener, ShotsPointsDialog.ShotsPointsDialogListener {

    LinearLayout spLinearLayout, weightLinearLayout;
    BottomSheetDialog bottomSheetDialog;
    ToggleButton aButton, bButton, cButton, dButton;
    ImageButton playPauseButton;
    FloatingActionButton floatButton;
    TextView shotsText, pointsText, timeShotsText, timePointsText, timerText, timerText2;
    int positionCounter = 4;
    int pointsCounter;
    int[] intPositions, intPosVal, intShotsPoints;
    CountDownTimer mCountDownTimer, mCountDownTimer2;
    boolean mTimerRunning;
    long timeLeft, timeLeft2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timerText = findViewById(R.id.timer);
        timerText2 = findViewById(R.id.timer2);
        shotsText = findViewById(R.id.shotsTextMain);
        pointsText = findViewById(R.id.pointsTextMain);
        timeShotsText = findViewById(R.id.timeShotsTextMain);
        timePointsText = findViewById(R.id.timePointsTextMain);

        playPauseButton = findViewById(R.id.playpause);
        playPauseButton.setOnClickListener(this);

        createBottomSheetDialog();

        floatButton = findViewById(R.id.fab);
        floatButton.setOnClickListener(this);

        aButton = findViewById(R.id.toggle_button_A);
        bButton = findViewById(R.id.toggle_button_B);
        cButton = findViewById(R.id.toggle_button_C);
        dButton = findViewById(R.id.toggle_button_D);

        aButton.setOnClickListener(this);
        bButton.setOnClickListener(this);
        cButton.setOnClickListener(this);
        dButton.setOnClickListener(this);

        intPositions = new int[4];
        intShotsPoints = new int[4];
        intPosVal = new int[]{25, 25, 25, 25};
        intPositions[0] = Integer.parseInt(0 + aButton.getText().toString());
        intPositions[1] = Integer.parseInt(0 + bButton.getText().toString());
        intPositions[2] = Integer.parseInt(0 + cButton.getText().toString());
        intPositions[3] = Integer.parseInt(0 + dButton.getText().toString());

    }

    private void createBottomSheetDialog() {
        if (bottomSheetDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, null);
            spLinearLayout = view.findViewById(R.id.spLinearLayout);
            weightLinearLayout = view.findViewById(R.id.weightLinearLayout);

            spLinearLayout.setOnClickListener(this);
            weightLinearLayout.setOnClickListener(this);

            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(view);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                bottomSheetDialog.show();
                break;
            case R.id.spLinearLayout:
                openDialogShotsPoints();
                bottomSheetDialog.dismiss();
                break;
            case R.id.weightLinearLayout:
                openDialogWeight();
                bottomSheetDialog.dismiss();
                break;
            case R.id.toggle_button_A:
                buttonToggle(view);
                break;
            case R.id.toggle_button_B:
                buttonToggle(view);
                break;
            case R.id.toggle_button_C:
                buttonToggle(view);
                break;
            case R.id.toggle_button_D:
                buttonToggle(view);
                break;
            case R.id.playpause:
                if (mTimerRunning) {
                    pauseTimer();
                }
                else {
                    startTimer();
                }
                break;
        }
    }

    private void pauseTimer() {
        mTimerRunning = false;
        mCountDownTimer.cancel();
        if(pointsCounter < intShotsPoints[1]) {
            mCountDownTimer2.cancel();
        }
        playPauseButton.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        floatButton.show();
        aButton.setEnabled(true);
        bButton.setEnabled(true);
        cButton.setEnabled(true);
        dButton.setEnabled(true);
    }

    private void startTimer() {
        pointsCounter = intShotsPoints[1];
        mCountDownTimer = new CountDownTimer(timeLeft, (long)(intShotsPoints[2]*100)) {
            @Override
            public void onTick(long timeUntilFinish) {
                timeLeft = timeUntilFinish;
                timerText.setText(String.valueOf(timeLeft));
            }

            @Override
            public void onFinish() {
                pointsCounter--;
                startTimer2();
            }
        }.start();
        mTimerRunning = true;
        playPauseButton.setImageResource(R.drawable.ic_pause_white_24dp);
        floatButton.hide();
        aButton.setEnabled(false);
        bButton.setEnabled(false);
        cButton.setEnabled(false);
        dButton.setEnabled(false);
    }

    private void startTimer2() {
        if(pointsCounter == 0) {
            mTimerRunning = false;
            playPauseButton.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        }
        else {
            timeLeft2 = (long)(intShotsPoints[3]*1000);
            mCountDownTimer2 = new CountDownTimer(timeLeft2, 1000) {
                @Override
                public void onTick(long timeUntilFinish) {
                    timeLeft2 = timeUntilFinish;
                    timerText2.setText(String.valueOf(timeLeft2));
                }

                @Override
                public void onFinish() {
                    startTimer();
                }
            }.start();
        }

    }

    private void buttonToggle(View view) {
        if(((ToggleButton)view).isChecked()) {
            positionCounter++;
        }
        else {
            positionCounter--;
        }
        intPositions[0] = Integer.parseInt(0 + aButton.getText().toString());
        intPositions[1] = Integer.parseInt(0 + bButton.getText().toString());
        intPositions[2] = Integer.parseInt(0 + cButton.getText().toString());
        intPositions[3] = Integer.parseInt(0 + dButton.getText().toString());

        int temp = 0;
        for(int x = 0; x < intPositions.length; x++) {
            if(positionCounter == 2 && intPositions[x] != 0) {
                intPosVal[x] = 50;
            }
            else if(positionCounter == 3 && intPositions[x] != 0) {
                if(temp == 0) {
                    intPosVal[x] = 34;
                    temp = 1;
                }
                else {
                    intPosVal[x] = 33;
                }
            }
            else if(positionCounter == 4 && intPositions[x] != 0) {
                intPosVal[x] = 25;
            }
        }

        if(positionCounter >= 2) {
            floatButton.show();
        }
        else {
            floatButton.hide();
        }
    }

    private void openDialogShotsPoints() {
        ShotsPointsDialog shotsPointsDialog = new ShotsPointsDialog();
        shotsPointsDialog.newShotsPoints(intShotsPoints);
        shotsPointsDialog.show(getSupportFragmentManager(), "shots points dialog");
    }

    private void openDialogWeight() {
        WeightDialog weightDialog = new WeightDialog();
        weightDialog.newInstance(intPositions, intPosVal);
        weightDialog.show(getSupportFragmentManager(), "weight dialog");
    }

    @Override
    public void applyWeights(int[] newPosVal) {
        intPosVal = newPosVal;
    }

    @Override
    public void applyShotsPoints(int[] newShotsPoints) {
        intShotsPoints = newShotsPoints;

        String shots = intShotsPoints[0] + " shots";
        String points = intShotsPoints[1] + " points";
        String timeShots = ((float) (intShotsPoints[2] / 10.0)) + " seconds";
        String timePoints = intShotsPoints[3] + " seconds";

        shotsText.setText(shots);
        pointsText.setText(points);
        timeShotsText.setText(timeShots);
        timePointsText.setText(timePoints);

        timeLeft = (long)(intShotsPoints[0]*intShotsPoints[2]*100);
        timerText.setText(String.valueOf(intShotsPoints[0]*intShotsPoints[2]*100));
        timerText2.setText(String.valueOf(intShotsPoints[3]*1000));
        playPauseButton.setVisibility(View.VISIBLE);

    }
}
