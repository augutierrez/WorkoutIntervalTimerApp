package com.example.workoutintervaltimer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WorkoutTimerActivity extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_timer_layout);
        editText = (EditText) findViewById(R.id.workout_timer_edit_text);
        Button button = (Button) findViewById(R.id.go_workout_timer_activity_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String interval = editText.getText().toString();
                Intent intent = new Intent(WorkoutTimerActivity.this, IntervalTimerActivity.class);
                intent.putExtra("interval", interval);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        editText.getText();
    }
}
