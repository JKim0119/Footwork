package com.example.footwork;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        WeightDialog.WeightDialogListener, ShotsPointsDialog.ShotsPointsDialogListener {

    LinearLayout spLinearLayout, weightLinearLayout;
    BottomSheetDialog bottomSheetDialog;
    ToggleButton aButton, bButton, cButton, dButton;
    ImageButton playPauseButton;
    FloatingActionButton floatButton;
    Button resetButton;
    TextView shotsText, pointsText, timeShotsText, timePointsText, valueText, pointNumberText;
    TextView aText, bText, cText, dText;
    int positionCounter = 4;
    int pointsCounter, whichTimer, go, pointNumb;
    int[] intPositions, intPosVal, intShotsPoints;
    CountDownTimer mCountDownTimer, mCountDownTimer2, readyCountDownTimer;
    boolean mTimerRunning, beforeStart;
    long timeLeft, timeLeft2;
    private TextToSpeech mTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        whichTimer = 1;
        pointNumb = 1;

        pointNumberText = findViewById(R.id.pointNumber);
        valueText = findViewById(R.id.value);
        shotsText = findViewById(R.id.shotsTextMain);
        pointsText = findViewById(R.id.pointsTextMain);
        timeShotsText = findViewById(R.id.timeShotsTextMain);
        timePointsText = findViewById(R.id.timePointsTextMain);

        aText = findViewById(R.id.aText);
        bText = findViewById(R.id.bText);
        cText = findViewById(R.id.cText);
        dText = findViewById(R.id.dText);

        playPauseButton = findViewById(R.id.playpause);
        playPauseButton.setOnClickListener(this);

        resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(this);

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

        aText.setText(intPosVal[0]+"%");
        bText.setText(intPosVal[1]+"%");
        cText.setText(intPosVal[2]+"%");
        dText.setText(intPosVal[3]+"%");
        intPositions[0] = Integer.parseInt(0 + aButton.getText().toString());
        intPositions[1] = Integer.parseInt(0 + bButton.getText().toString());
        intPositions[2] = Integer.parseInt(0 + cButton.getText().toString());
        intPositions[3] = Integer.parseInt(0 + dButton.getText().toString());

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.UK);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart) {
            showStartDialog();
        }
    }

    private void showStartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Instructions")
                .setMessage("1. Start at center of baseline. \n\n" +
                        "2. Select the positions that you want on. \n(No number means off) \n\n" +
                        "3. Click the plus button in the bottom right corner to set the amount of shots/points. \n" +
                        "Optional: can set weights of each position \n\n" +
                        "4. Play button will appear and start practicing your footwork!")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
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
        if (id == R.id.action_instructions) {
            showStartDialog();
        }
        else if (id == R.id.action_about) {
            new AlertDialog.Builder(this)
                    .setTitle("About me")
                    .setMessage("My name is Jacob and I created this app mainly to help my endurance" +
                            " and footwork for tennis. I also made it for others to use in case" +
                            " they were looking to do the same!" +
                            "\n\nIf you would like to report a bug/crash or" +
                            " would like to get in contact with me, you can reach me at \njacobkim0119@gmail.com" +
                            "\n\nHope you enjoy the app!")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        else if (id == R.id.action_demo) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/7Z0YdNLd1CU"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.google.android.youtube");
            startActivity(intent);
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
                    readyTimer();
                }
                break;
            case R.id.resetBtn:
                resetTimer();
                break;
        }
    }

    private void resetTimer() {
        if(beforeStart) {
            playPauseButton.setVisibility(View.VISIBLE);
        }
        else {
            playPauseButton.setVisibility(View.INVISIBLE);
        }
        timeLeft = (long)(intShotsPoints[0]*intShotsPoints[2]*100);
        pointsCounter = intShotsPoints[1];
        playPauseButton.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        resetButton.setVisibility(View.INVISIBLE);
        pointNumberText.setVisibility(View.INVISIBLE);
        valueText.setVisibility(View.INVISIBLE);
        whichTimer = 1;
        pointNumb = 1;
    }

    private void pauseTimer() {
        mTimerRunning = false;
        mCountDownTimer.cancel();
        if(pointsCounter < intShotsPoints[1]) {
            mCountDownTimer2.cancel();
        }

        playPauseButton.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        resetButton.setVisibility(View.VISIBLE);
        floatButton.show();
        aButton.setEnabled(true);
        bButton.setEnabled(true);
        cButton.setEnabled(true);
        dButton.setEnabled(true);
    }

    private void readyTimer() {
        go = 0;
        valueText.setVisibility(View.VISIBLE);
        if(whichTimer == 1) {
            readyCountDownTimer = new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long timeUntilFinish) {
                    switch (go) {
                        case 0:
                            go++;
                            valueText.setText("Ready");
                            speak("Ready");
                            break;
                        case 1:
                            go++;
                            valueText.setText("Set");
                            speak("Set");
                            break;
                        case 2:
                            go++;
                            valueText.setText("Go");
                            speak("Go");
                            break;
                    }
                }

                @Override
                public void onFinish() {
                    playPauseButton.setVisibility(View.VISIBLE);
                    pointNumberText.setVisibility(View.VISIBLE);
                    startTimer();
                }
            }.start();
            mTimerRunning = true;
            playPauseButton.setVisibility(View.INVISIBLE);
            floatButton.hide();
            aButton.setEnabled(false);
            bButton.setEnabled(false);
            cButton.setEnabled(false);
            dButton.setEnabled(false);
        }
        else {
            startTimer2();
        }
    }

    private void startTimer() {
        pointNumberText.setText("Point #"+pointNumb);
        mCountDownTimer = new CountDownTimer(timeLeft, (long)(intShotsPoints[2]*100)) {
            @Override
            public void onTick(long timeUntilFinish) {
                timeLeft = timeUntilFinish;
                randomSelector();
            }

            @Override
            public void onFinish() {
                whichTimer = 2;
                pointsCounter--;
                timeLeft2 = (long)(intShotsPoints[3]*1000);
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
            playPauseButton.setVisibility(View.INVISIBLE);
            resetButton.setVisibility(View.VISIBLE);
            floatButton.show();
            whichTimer = 1;
            aButton.setEnabled(true);
            bButton.setEnabled(true);
            cButton.setEnabled(true);
            dButton.setEnabled(true);
        }
        else {
            mCountDownTimer2 = new CountDownTimer(timeLeft2, 1000) {
                @Override
                public void onTick(long timeUntilFinish) {
                    timeLeft2 = timeUntilFinish;
                    int temp = (int)Math.ceil(timeLeft2/1000.0);
                    valueText.setText(String.valueOf((int)Math.ceil(timeLeft2/1000.0)));
                    switch (temp) {
                        case 3:
                            speak("Ready");
                            break;
                        case 2:
                            speak("Set");
                            break;
                        case 1:
                            speak("Go");
                            break;
                    }

                }

                @Override
                public void onFinish() {
                    pointNumb++;
                    whichTimer = 1;
                    timeLeft = (long)(intShotsPoints[0]*intShotsPoints[2]*100);
                    startTimer();
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

    }

    private void randomSelector() {
        Random rand = new Random();

        int n = rand.nextInt(100);

        if(n < intPosVal[0] && intPositions[0] == 1) {
            valueText.setText("1");
            speak("1");
        }
        else if(n < intPosVal[0] + intPosVal[1] && intPositions[1] == 2) {
            valueText.setText("2");
            speak("2");
        }
        else if(n < intPosVal[0] + intPosVal[1] + intPosVal[2] && intPositions[2] == 3) {
            valueText.setText("3");
            speak("3");
        }
        else {
            valueText.setText("4");
            speak("4");
        }
    }

    private void speak(String s) {
        String text = s;
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private void buttonToggle(View view) {
        resetTimer();
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
            if(positionCounter == 1 && intPositions[x] != 0) {
                intPosVal[x] = 100;
            }
            else if(positionCounter == 2 && intPositions[x] != 0) {
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
            else {
                intPosVal[x] = 0;
            }
        }

        applyWeights(intPosVal);


        if(positionCounter >= 2) {
            floatButton.show();
        }
        else {
            floatButton.hide();
            playPauseButton.setVisibility(View.INVISIBLE);
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

        if(intPositions[0] == 0) {
            aText.setVisibility(View.INVISIBLE);
        }
        else {
            aText.setVisibility(View.VISIBLE);
            aText.setText(intPosVal[0]+"%");
        }
        if(intPositions[1] == 0) {
            bText.setVisibility(View.INVISIBLE);
        }
        else {
            bText.setVisibility(View.VISIBLE);
            bText.setText(intPosVal[1]+"%");
        }
        if(intPositions[2] == 0) {
            cText.setVisibility(View.INVISIBLE);
        }
        else {
            cText.setVisibility(View.VISIBLE);
            cText.setText(intPosVal[2]+"%");
        }
        if(intPositions[3] == 0) {
            dText.setVisibility(View.INVISIBLE);
        }
        else {
            dText.setVisibility(View.VISIBLE);
            dText.setText(intPosVal[3]+"%");
        }
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

        beforeStart = true;
        resetTimer();
    }
}
