package com.example.footwork;

import android.os.Bundle;
import android.util.Log;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
    ToggleButton firstButton, secondButton, thirdButton;
    TextView textView;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.textView);
        createBottomSheetDialog();

        firstButton = findViewById(R.id.toggle_button_1);
        secondButton = findViewById(R.id.toggle_button_2);
        thirdButton = findViewById(R.id.toggle_button_3);

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

    public void showBottomSheet(View view) {
        bottomSheetDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.timerLinearLayout:
                textView.setText("Timer");
                bottomSheetDialog.dismiss();
                break;
            case R.id.weightLinearLayout:
                textView.setText("Weights");
                bottomSheetDialog.dismiss();
                break;
        }
    }

    public void changeFirstButton(View view) {
        boolean checked = ((ToggleButton)view).isChecked();
        Log.i("myTag","Counter: " + counter);
        Log.i("myOtherTag","Checked value: " + checked);
        if(checked) {
            counter++;
            textView.setText(String.valueOf(counter));
            firstButton.setText(String.valueOf(counter));
        }
        else {
            counter--;
            textView.setText(String.valueOf(counter));
            firstButton.setText("");
        }
    }

    public void changeSecondButton(View view) {
        boolean checked = ((ToggleButton)view).isChecked();
        Log.i("myTag","Counter: " + counter);
        Log.i("myOtherTag","Checked value: " + checked);
        if(checked) {
            counter++;
            textView.setText(String.valueOf(counter));
            secondButton.setText(String.valueOf(counter));
        }
        else {
            counter--;
            textView.setText(String.valueOf(counter));
            secondButton.setText("");
        }
    }

    public void changeThirdButton(View view) {
        boolean checked = ((ToggleButton)view).isChecked();
        Log.i("myTag","Counter: " + counter);
        Log.i("myOtherTag","Checked value: " + checked);
        if(checked) {
            counter++;
            textView.setText(String.valueOf(counter));
            thirdButton.setText(String.valueOf(counter));
        }
        else {
            counter--;
            textView.setText(String.valueOf(counter));
            thirdButton.setText("");
        }
    }
}
