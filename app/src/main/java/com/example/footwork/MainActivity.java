package com.example.footwork;

import android.os.Bundle;
import android.util.Log;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, WeightDialog.WeightDialogListener {

    LinearLayout spLinearLayout, weightLinearLayout;
    BottomSheetDialog bottomSheetDialog;
    ToggleButton aButton, bButton, cButton, dButton;
    FloatingActionButton floatButton;
    TextView textView;
    int counter = 4;
    int[] intPositions, intPosVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        textView = findViewById(R.id.textView);
        createBottomSheetDialog();

        floatButton = findViewById(R.id.fab);
        floatButton.show();
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
        }
    }

    private void openDialogShotsPoints() {
        ShotsPointsDialog shotsPointsDialog = new ShotsPointsDialog();
        shotsPointsDialog.show(getSupportFragmentManager(), "shots points dialog");
    }

    private void openDialogWeight() {
        WeightDialog weightDialog = new WeightDialog();
        weightDialog.newInstance(intPositions, intPosVal);
        weightDialog.show(getSupportFragmentManager(), "weight dialog");
    }

    private void buttonToggle(View view) {
        if(((ToggleButton)view).isChecked()) {
            counter++;
//            ((ToggleButton)view).setTextOn(String.valueOf(counter));
//            ((ToggleButton)view).setText(String.valueOf(counter));
        }
        else {
            counter--;
//            int goneValue = Integer.parseInt(((ToggleButton)view).getTextOn().toString());

//            recheck(view, aButton, goneValue);
//            recheck(view, bButton, goneValue);
//            recheck(view, cButton, goneValue);
//            recheck(view, dButton, goneValue);
        }
        intPositions[0] = Integer.parseInt(0 + aButton.getText().toString());
        intPositions[1] = Integer.parseInt(0 + bButton.getText().toString());
        intPositions[2] = Integer.parseInt(0 + cButton.getText().toString());
        intPositions[3] = Integer.parseInt(0 + dButton.getText().toString());
        for(int x = 0; x < intPositions.length; x++) {
            if(counter == 2 && intPositions[x] != 0) {
                intPosVal[x] = 50;
            }
            else if(counter == 3 && intPositions[x] != 0) {
                intPosVal[x] = 33;
            }
            else if(counter == 4 && intPositions[x] != 0) {
                intPosVal[x] = 25;
            }
        }

        if(counter >= 2) {
            floatButton.show();
        }
        else {
            floatButton.hide();
        }
//        Log.i("myTag","Counter: " + counter);
    }

    @Override
    public void applyWeights(int[] newPosVal) {
        intPosVal = newPosVal;
    }

//    private void recheck(View view, ToggleButton button, int val) {
//            if(view.getId() != button.getId() && button.isChecked()) {
//                int tempInt = Integer.parseInt(button.getTextOn().toString());
//                if(tempInt > val) {
//                    tempInt--;
//                    button.setTextOn(String.valueOf(tempInt));
//                    button.setText(String.valueOf(tempInt));
//                }
//            }
//    }


}
