package com.demo.recorder.recorder;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by gaurav on 10/06/17.
 */

public class CameraService extends Service {

    private static final String TAG="VideoRecorder";
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Camera mServiceCamera;
    private boolean mRecordingStatus;
    private MediaRecorder mMediaRecorder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mRecordingStatus = false;
        mServiceCamera = MainActivity.mCamera;
        mSurfaceView = MainActivity.mSurfaceView;
        mSurfaceHolder = MainActivity.mSurfaceHolder;
        super.onCreate();
        /*   if (mRecordingStatus == false)
            startRecording(); */
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (mRecordingStatus == false)
            startRecording();

        return START_STICKY;
    }

    public boolean startRecording(){
        try {

            mServiceCamera = Camera.open();
            Camera.Parameters params = mServiceCamera.getParameters();
            mServiceCamera.setParameters(params);
            Camera.Parameters p = mServiceCamera.getParameters();

            final List<Camera.Size> listSize = p.getSupportedPreviewSizes();
            Camera.Size mPreviewSize = listSize.get(2);
            Log.v(TAG, "use: width = " + mPreviewSize.width
                    + " height = " + mPreviewSize.height);
            p.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
            p.setPreviewFormat(PixelFormat.YCbCr_420_SP);
            mServiceCamera.setParameters(p);

            try {
                mServiceCamera.setPreviewDisplay(mSurfaceHolder);
                mServiceCamera.startPreview();
            }
            catch (IOException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }

            mServiceCamera.unlock();

            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setCamera(mServiceCamera);
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
            mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +new Date().getDate() + "Video.mp4");
            mMediaRecorder.setVideoFrameRate(30);
            mMediaRecorder.setVideoSize(mPreviewSize.width, mPreviewSize.height);
            mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

            mMediaRecorder.prepare();
            mMediaRecorder.start();

            mRecordingStatus = true;

            return true;
        } catch (IllegalStateException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void stopRecording(){
        Toast.makeText(getBaseContext(), "Recording Stopped", Toast.LENGTH_SHORT).show();
        try {
            mServiceCamera.reconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaRecorder.stop();
        mMediaRecorder.reset();

        mServiceCamera.stopPreview();
        mMediaRecorder.release();

        mServiceCamera.release();
        mServiceCamera = null;
    }

    @Override
    public void onDestroy() {
        stopRecording();
        mRecordingStatus = false;

        super.onDestroy();
    }

}
