package com.example.workoutintervaltimer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class IntervalTimerActivity extends AppCompatActivity {
    private int interval;
    private int seconds;
    private boolean isRunning;
    private ImageButton pauseButton;
    private ImageButton resumeButton;
    private TextView timeTextView;
    private TextView cycles;
    private int numCycles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interval_timer_activity);
        pauseButton = (ImageButton) findViewById(R.id.interval_pause_button);
        resumeButton = (ImageButton) findViewById(R.id.interval_play_button);
        TextView textView = (TextView) findViewById(R.id.interval_timer_interval_text);
        String message = getIntent().getStringExtra("interval");
        textView.setText("Intervals of : " + message);
        timeTextView = (TextView) findViewById(R.id.interval_timer_time_text);
        cycles = (TextView) findViewById(R.id.interval_cycles_count_text_view);
        numCycles = 0;
        cycles.setText("Cycles : " + numCycles);

        interval = Integer.parseInt(message);
        seconds = 0;
        isRunning = true;
        runTimer();
    }

    public void runTimer(){
        //final TextView textView = (TextView) findViewById(R.id.interval_timer_time_text);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(isRunning) {
                    int hours = seconds / 3600;
                    int minutes = (seconds % 3600) / 60;
                    int secs = (seconds%60);
                    String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
                    seconds++;
                    timeTextView.setText(time);
                    if((seconds-1) > 0 && (seconds - 1 ) % interval == 0){
                        cycleReached();
                    }
                }
                handler.postDelayed(this, 1000);
            }
        });
    }


    public void cycleReached(){
        numCycles++;
        cycles.setText("Cycles : " + numCycles);

        startService(new Intent(this, SoundService.class));
    }

    public void myPause(View view){
        isRunning = false;
        pauseButton.setVisibility(View.GONE);
        resumeButton.setVisibility(View.VISIBLE);
        //show resume button

    }

    public void myResume(View view){
        isRunning = true;
        pauseButton.setVisibility(View.VISIBLE);
        resumeButton.setVisibility(View.GONE);
        //show pause button
    }

}
