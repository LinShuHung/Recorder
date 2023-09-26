package com.suhun.recorder;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.transform.Source;

public class RecorderService extends Service {
    private String tag = RecorderService.class.getSimpleName();
    private MediaRecorder mediaRecorder;
    private File saveDir, rFile;
    private int second;
    private Timer timer;

    public RecorderService() {
        saveDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        rFile = new File(saveDir, "suhun.3gp");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        second = 0;
        timer = new Timer();
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(rFile);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            Log.d(tag, "-----Start Record.....-----");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    second++;
                    Intent intent = new Intent("record");
                    intent.putExtra("recordSecond", second);
                    sendBroadcast(intent);
                }
            }, 100, 1000);
        }catch (Exception e){
            Log.d(tag, "-----Error Exception in prepare " + e.toString());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaRecorder!=null){
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
            Log.d(tag, "-----Stop Record.....-----");
        }
        if(timer != null){
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }
}