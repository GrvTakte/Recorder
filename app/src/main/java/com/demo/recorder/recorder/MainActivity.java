package com.demo.recorder.recorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /**
     * Variable declaration
     */

    private Button recordingButton;
    private Button stopButton;
    private Button playButton;
    private Button matchButton;


    private MediaRecorder mediaRecorder;
    String voiceStoragePath;

    static final String AB = "abcdefghijklmnopqrstuvwxyz";
    static Random rnd = new Random();

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hasSDCard();

        voiceStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File audioVoice = new File(voiceStoragePath + File.separator + "voices");

        if(!audioVoice.exists()){
            audioVoice.mkdir();
        }

        voiceStoragePath = voiceStoragePath + File.separator +"voices/" + generateVoiceFilename(6) + ".3gpp";
        Toast.makeText(getApplicationContext(),"Audio path: " + voiceStoragePath,Toast.LENGTH_LONG).show();

        recordingButton = (Button) findViewById(R.id.recording_button);
        stopButton = (Button) findViewById(R.id.stop_button);
        playButton = (Button) findViewById(R.id.play_button);

        stopButton.setEnabled(false);
        recordingButton.setEnabled(false);
        playButton.setEnabled(false);

        //initializedMediaRecord();


        /**
         * button event for recording audio.
         */
        recordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaRecorder == null){
                   initializedMediaRecord();
                }
                startAudioRecording();
            }
        });


        /**
         * button event for stop recording audio.
         */
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAudioRecording();
            }
        });


        /**
         * button event for playing last recorded audio.
         */
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playLastStoredAudioMusic();
                mediaPlayerPlaying();
            }
        });

    }

    private String generateVoiceFilename(int len){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    private void stopAudioRecording(){
        if(mediaRecorder != null){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        stopButton.setEnabled(false);
        playButton.setEnabled(true);
    }

    private void startAudioRecording(){
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        recordingButton.setEnabled(false);
        stopButton.setEnabled(true);
        Toast.makeText(getApplicationContext(),"Record button pressed",Toast.LENGTH_LONG).show();
    }


    private void initializedMediaRecord(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(voiceStoragePath);
    }


    private void mediaPlayerPlaying(){
        if(!mediaPlayer.isPlaying()){
            stopAudioPlay();
        }
    }

    private void playLastStoredAudioMusic(){
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(voiceStoragePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        recordingButton.setEnabled(true);
        playButton.setEnabled(false);
    }

    private void stopAudioPlay(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void hasSDCard(){
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if(isSDPresent)        {
            Toast.makeText(getApplicationContext(),"There is SDCard",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"There is no SDCard",Toast.LENGTH_LONG).show();
           // System.out.println("There is no SDCard");
        }
    }
}
