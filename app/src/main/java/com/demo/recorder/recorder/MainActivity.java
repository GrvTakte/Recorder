package com.demo.recorder.recorder;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity extends Activity implements SurfaceHolder.Callback{

    public static SurfaceView mSurfaceView;
    public static SurfaceHolder mSurfaceHolder;
    public static final int RequestPermissionCode = 1;

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
        if(checkPermission()) {
            Toast.makeText(getApplicationContext(), "Audio Recording started", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, RecorderService.class);
            startService(intent);
        }else{
            requestPermission();
        }
    }

    public void stopAudio(View v){
        Toast.makeText(getApplicationContext(),"Audio Recording stopped",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, RecorderService.class);
        stopService(intent);
    }

    public void playMusic(View v){
        if (checkPermission()) {
            Toast.makeText(getApplicationContext(), "Music Started", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, MusicService.class);
            startService(intent);
        }else{
            requestPermission();
        }
    }

    public void stopMusic(View v){
        Toast.makeText(getApplicationContext(),"Music stopped",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        stopService(intent);
    }

    public void playVideo(View view){
        if (checkPermission()) {
            Toast.makeText(getApplicationContext(), "Video Recording started", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, CameraService.class);
            startService(intent);
        }else{
            requestPermission();
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(MainActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.RECORD_AUDIO);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO, CAMERA}, RequestPermissionCode);
    }
}