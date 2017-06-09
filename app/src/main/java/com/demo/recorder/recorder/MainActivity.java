package com.demo.recorder.recorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


public class MainActivity extends Activity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void playAudio(View v){
        Intent intent = new Intent(MainActivity.this, RecorderService.class);
        startService(intent);
    }

    public void stopAudio(View v){
        Intent intent = new Intent(MainActivity.this, RecorderService.class);
        stopService(intent);
    }

    public void playMusic(View v){
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        startService(intent);
    }

    public void stopMusic(View v){
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        stopService(intent);
    }
}