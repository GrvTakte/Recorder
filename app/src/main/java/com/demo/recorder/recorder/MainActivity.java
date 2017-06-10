package com.demo.recorder.recorder;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity implements SurfaceHolder.Callback{

    private static final String TAG = MainActivity.class.getSimpleName();

    public static SurfaceView mSurfaceView;
    public static SurfaceHolder mSurfaceHolder;
    public static Camera mCamera;
    public static boolean mPreviewRunning;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void playAudio(View v){
        Toast.makeText(getApplicationContext(),"Audio Recording started",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, RecorderService.class);
        startService(intent);
    }

    public void stopAudio(View v){
        Toast.makeText(getApplicationContext(),"Audio Recording stopped",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, RecorderService.class);
        stopService(intent);
    }

    public void playMusic(View v){
        Toast.makeText(getApplicationContext(),"Music Started",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        startService(intent);
    }

    public void stopMusic(View v){
        Toast.makeText(getApplicationContext(),"Music stopped",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        stopService(intent);
    }

    public void playVideo(View view){
        Toast.makeText(getApplicationContext(),"Video Recording started",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, CameraService.class);
        startService(intent);
    }

    public void stopVideo(View view){
        Toast.makeText(this, "Video Recording stopped", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, CameraService.class);
        stopService(intent);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}