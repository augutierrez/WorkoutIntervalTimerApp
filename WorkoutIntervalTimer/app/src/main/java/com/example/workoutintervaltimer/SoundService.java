package com.example.workoutintervaltimer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.Nullable;

public class SoundService extends Service {
    String TAG = "SoundService";
    Vibrator vibrator;
    MediaPlayer mp;
    static final String CYCLE = "cycle";
    static final String PAUSE = "pause";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int sound = 0;
        try {
         //two different sounds, need to get action
            String action = intent.getAction();
            switch (action){
                case PAUSE: sound = R.raw.quack1;
                    break;
                case CYCLE: sound = R.raw.quack;
                    break;
                default: sound = R.raw.quack;
                    Log.i(TAG,"ERROR, default switch case reached");
                    break;
            }

            boolean isSound = intent.getBooleanExtra("isSound", true);
            if(isSound) {
                mp = MediaPlayer.create(getApplicationContext(), sound);
                mp.start();
                mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mediaPlayer) {
                        mp.release();
                        mp = null;
                    }
                });
            }
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        stopSelf();
        return START_NOT_STICKY;
    }
}
