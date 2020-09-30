package com.example.workoutintervaltimer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class IntervalTimerActivity extends AppCompatActivity {
    private boolean saved;
    private int interval;
    private int seconds;
    private boolean isRunning;
    private ImageButton pauseButton;
    private TextView timeTextView;
    private TextView cycles;
    private int numCycles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interval_timer_activity);
        pauseButton = (ImageButton) findViewById(R.id.interval_pause_button);
        TextView textView = (TextView) findViewById(R.id.interval_timer_interval_text);
        String message = getIntent().getStringExtra("interval");
        textView.setText(getString(R.string.intervals_of) + message);
        timeTextView = (TextView) findViewById(R.id.interval_timer_time_text);
        cycles = (TextView) findViewById(R.id.interval_cycles_count_text_view);
        interval = Integer.parseInt(message);
        if(savedInstanceState == null){
            Log.i("intervalTimer", "wtf");
        }

        if(savedInstanceState != null){
            numCycles = savedInstanceState.getInt("cycles");
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
        }
        else {
            Log.i("IntervalTimer", "SavedInstance was null");
            numCycles = 0;
            seconds = 0;
            isRunning = true;
        }
        setTime();
        cycles.setText(getString(R.string.interval_timer_cycles) + numCycles);
        runTimer();
    }

    public void runTimer(){
        //final TextView textView = (TextView) findViewById(R.id.interval_timer_time_text);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(isRunning) {
                    setTime();
                    seconds++;
                    if((seconds-1) > 0 && (seconds - 1 ) % interval == 0){
                        cycleReached();
                    }
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void setTime(){
        int hours = seconds/3600;
        int minutes = (seconds % 3600) / 60;
        int secs = (seconds%60);
        String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
        timeTextView.setText(time);
    }


    public void cycleReached(){
        numCycles++;
        cycles.setText("Cycles : " + numCycles);

        startService(new Intent(this, SoundService.class));
    }

    public void myPause(View view){
        if(isRunning){
            isRunning = false;
            pauseButton.setImageResource(R.drawable.play2);
        }
        else{
            isRunning = true;
            pauseButton.setImageResource(R.drawable.pause2);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt("cycles", numCycles);
        outState.putInt("seconds", seconds);
        outState.putBoolean("isRunning", isRunning);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning=false;
    }
}
