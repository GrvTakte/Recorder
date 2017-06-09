package com.demo.recorder.recorder;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

/**
 * Created by gaurav on 09/06/17.
 */

public class MusicService extends Service {

    MediaPlayer objPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        objPlayer = MediaPlayer.create(this,R.raw.sound);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        objPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onStop(){
        objPlayer.stop();
        objPlayer.release();
    }

    public void onPause(){
        objPlayer.pause();
    }

    public void onDestroy(){
        objPlayer.stop();
        objPlayer.release();
    }
}
