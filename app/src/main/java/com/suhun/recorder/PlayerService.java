package com.suhun.recorder;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

public class PlayerService extends Service {
    private String tag = PlayerService.class.getSimpleName();
    private File saveDir, rFile;
    private MediaPlayer mediaPlayer;
    public PlayerService() {
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
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(rFile.getAbsolutePath());
            Intent intent = new Intent("record");
            intent.putExtra("path", rFile.getAbsolutePath());
            sendBroadcast(intent);
        }catch (Exception e){
            Log.d(tag, "----Exception in PlayService setDataSource-----");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isPlay = intent.getBooleanExtra("playStatus", false);
        if(isPlay){
            mediaPlayer.start();
            Log.d(tag, "-----play-----");
        }else{
            mediaPlayer.pause();
            Log.d(tag, "-----pause-----");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}