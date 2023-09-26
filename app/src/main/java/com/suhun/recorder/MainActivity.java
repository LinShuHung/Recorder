package com.suhun.recorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String tag = MainActivity.class.getSimpleName();
    private TextView recordTime, path;
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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

    }

    public void stopRecorderFun(View view){

    }

    private void playFun(){

    }

    public void stopPlayFun(View view){

    }
}