package com.demo.recorder.recorder;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;

import java.io.File;
import java.util.Random;

/**
 * Created by gaurav on 07/06/17.
 */

public class RecorderService extends Service {
    private static final int RECORDER_SAMPLERATE = 8000;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private MediaRecorder recorder;
    private NotificationManager notificationManager;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";

    public RecorderService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        try {
            recorder = new MediaRecorder();
            int audioSource;

                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);


            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            recorder.setAudioSamplingRate(RECORDER_SAMPLERATE);
           // Log.e("path recording", intent.getStringExtra("recording_name"));

            final String filePath =Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + CreateRandomAudioFileName(5) + "AudioRecording.3gp";
            final File file = new File(filePath);
            file.getParentFile().mkdirs();
            recorder.setOutputFile(filePath);

            recorder.prepare();
            recorder.start();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return START_STICKY;
    }
    public String CreateRandomAudioFileName(int string){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            recorder.stop();
            recorder.release();
            recorder = null;
            notificationManager.cancel(0123456);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


}


