package com.example.workoutintervaltimer;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class WorkoutTimerActivity extends AppCompatActivity {
    EditText editText;
    Spinner spinner;

    String lastInterval;
    Boolean savedStateAvailable = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_timer_layout);
        editText = (EditText) findViewById(R.id.workout_timer_edit_text);
        spinner = (Spinner) findViewById(R.id.spinner);

        List<String> selections = new ArrayList<>();
        selections.add("seconds");
        selections.add("minutes");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_list, selections);
        dataAdapter.setDropDownViewResource(R.layout.spinner_list);
        spinner.setAdapter(dataAdapter);

        if(savedStateAvailable){
            editText.setText(lastInterval);
        }
        else{
            editText.setText("30");
        }
        Button button = (Button) findViewById(R.id.go_workout_timer_activity_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int interval = Integer.parseInt(editText.getText().toString());
                if(spinner.getSelectedItem().equals("minutes")){
                    interval = interval * 60;
                }
                Intent intent = new Intent(WorkoutTimerActivity.this, IntervalTimerActivity.class);
                intent.putExtra("interval", String.valueOf(interval));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("myBoolean", true);
        savedInstanceState.putString("myInterval", editText.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        lastInterval = savedInstanceState.getString("myInterval");
        savedStateAvailable = savedInstanceState.getBoolean("myBoolean");
    }

    @Override
    protected void onResume() {
        super.onResume();
        editText.getText();
    }
}
