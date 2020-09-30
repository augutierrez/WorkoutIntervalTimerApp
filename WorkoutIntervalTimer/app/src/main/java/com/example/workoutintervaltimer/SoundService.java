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

import androidx.annotation.Nullable;

public class SoundService extends Service {
    private Ringtone ringtone;
    Vibrator vibrator;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        try {
            /**
            Uri path = Uri.parse("android.resource://" + getPackageName()+ "/bell.mp3");
            //RingtoneManager.setActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_RINGTONE, path);
            ringtone = RingtoneManager.getRingtone(getApplicationContext(), path);

            //ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
            ringtone.play();
             **/
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.quack);
            mp.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        return START_NOT_STICKY;
    }
}
