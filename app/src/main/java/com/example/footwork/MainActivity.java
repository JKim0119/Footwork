package com.example.footwork;

import android.os.Bundle;
import android.util.Log;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.arch.core.executor.TaskExecutor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout timerLinearLayout, weightLinearLayout;
    BottomSheetDialog bottomSheetDialog;
    ToggleButton aButton, bButton, cButton, dButton;
    FloatingActionButton floatButton;
    TextView textView;
    int counter = 0;
    int[] intPositions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intPositions = new int[4];

        textView = findViewById(R.id.textView);
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

    }

    private void createBottomSheetDialog() {
        if (bottomSheetDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, null);
            timerLinearLayout = view.findViewById(R.id.timerLinearLayout);
            weightLinearLayout = view.findViewById(R.id.weightLinearLayout);

            timerLinearLayout.setOnClickListener(this);
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
            case R.id.timerLinearLayout:
                textView.setText("Timer");
                bottomSheetDialog.dismiss();
                break;
            case R.id.weightLinearLayout:
                openDialog();
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
        }
    }

    private void openDialog() {
        WeightDialog weightDialog = new WeightDialog();
        if(aButton.isChecked()) {
            intPositions[0] = Integer.parseInt(aButton.getText().toString());
        }
        if(bButton.isChecked()) {
            intPositions[1] = Integer.parseInt(bButton.getText().toString());
        }
        if(cButton.isChecked()) {
            intPositions[2] = Integer.parseInt(cButton.getText().toString());
        }
        if(dButton.isChecked()) {
            intPositions[3] = Integer.parseInt(dButton.getText().toString());
        }
        weightDialog.newInstance(intPositions);
        weightDialog.show(getSupportFragmentManager(), "weight dialog");
    }

    private void buttonToggle(View view) {
        if(((ToggleButton)view).isChecked()) {
            floatButton.show();
            counter++;
            ((ToggleButton)view).setTextOn(String.valueOf(counter));
            ((ToggleButton)view).setText(String.valueOf(counter));
        }
        else {
            counter--;
            int goneValue = Integer.parseInt(((ToggleButton)view).getTextOn().toString());

            recheck(view, aButton, goneValue);
            recheck(view, bButton, goneValue);
            recheck(view, cButton, goneValue);
            recheck(view, dButton, goneValue);

        }

//        Log.i("myTag","Counter: " + counter);
    }

    private void recheck(View view, ToggleButton button, int val) {
            if(view.getId() != button.getId() && button.isChecked()) {
                int tempInt = Integer.parseInt(button.getTextOn().toString());
                if(tempInt > val) {
                    tempInt--;
                    button.setTextOn(String.valueOf(tempInt));
                    button.setText(String.valueOf(tempInt));
                }
            }
    }


}
