package com.suhun.recorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String tag = MainActivity.class.getSimpleName();
    private TextView recordTime, path;
    private Button startBtn;
    private MyReciver myReciver;
    private boolean isPlay;

    private class MyReciver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int recordSecond = intent.getIntExtra("recordSecond", -1);
            if(recordSecond > 0){
                recordTime.setText("" + recordSecond+"秒");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if(checkUserAgreePermissionAboutRecorder()){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 123);
        }else{
            initRecorder();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        myReciver = new MyReciver();
        IntentFilter intentFilter = new IntentFilter("record");
        registerReceiver(myReciver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myReciver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 123){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
            && grantResults[2] == PackageManager.PERMISSION_GRANTED){
                initRecorder();
            }else{
                finish();
            }
        }
    }

    private boolean checkUserAgreePermissionAboutRecorder(){
        boolean result = false;
        /*如果有一項使用者權限不同意，回傳true，發送請求請使用者同意*/
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED){
            result = true;
        }
        return result;
    }

    private void initRecorder(){

    }
    private void initView(){
        recordTime = findViewById(R.id.lid_recordTime);
        path = findViewById(R.id.lid_path);
        startBtn = findViewById(R.id.lid_playBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playFun();
            }
        });
    }

    public void startRecorderFun(View view){
        Intent intent = new Intent(this, RecorderService.class);
        startService(intent);
    }

    public void stopRecorderFun(View view){
        Intent intent = new Intent(this, RecorderService.class);
        stopService(intent);
    }

    private void playFun(){
        isPlay = !isPlay;
        if(isPlay){
            startBtn.setText("Pause");
        }else{
            startBtn.setText("Start");
        }
    }

    public void stopPlayFun(View view){

    }
}