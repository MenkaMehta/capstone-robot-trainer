package com.example.avatarmind.robotmotion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.robot.motion.RobotPlayer;
import android.robot.speech.SpeechManager;
import android.robot.speech.SpeechService;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.xguzm.pathfinding.grid.GridCell;

import java.io.InputStream;

public class RoboticWorkoutActivity extends Activity implements View.OnClickListener {


    private ImageView mTitleBack;

    private Button mBtnPlay;

    private Button mBtnStop;

    RobotPlayer robotPlayer;

    SpeechManager mSpeechManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }

        initView();
        initListener();
        mSpeechManager = (SpeechManager) getSystemService(SpeechService.SERVICE_NAME);
        robotPlayer = new RobotPlayer();
    }

    private void initView() {
        setContentView(R.layout.activity_robotic_workout);

        mTitleBack = (ImageView) findViewById(R.id.common_title_back);
        mBtnPlay = (Button) findViewById(R.id.main_play);
        mBtnStop = (Button) findViewById(R.id.main_stop);

    }

    private void play() {
        byte[] bytes = getFromAssets("demo2.arm");
        robotPlayer.setDataSource(bytes, 0, bytes.length);
        robotPlayer.prepare();
        Runnable runnable = () -> {
            try {
                Thread.sleep(5000);
                robotPlayer.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread t = new Thread(runnable);
        t.start();
        mSpeechManager.startSpeaking("Hi,Please do this with me");
    }

    private void stop() {
        if (isCurrentlyPlaying()) {
            robotPlayer.stop();
        }
    }

    private void initListener() {
        mTitleBack.setOnClickListener(this);
        mBtnPlay.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //mSpeechManager.startSpeaking("Hi people, how are you today!");
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.main_play:
                //intent.setClass(RoboticWorkoutActivity.this, RegisterMemberActivity.class);
                play();
                break;
            case R.id.main_stop:
                //intent.setClass(RoboticWorkoutActivity.this, SessionActivity.class);
                stop();
                break;
            case R.id.common_title_back:
                finish();
                return;
            default:
                break;
        }
        //startActivity(intent);
    }

    public byte[] getFromAssets(String fileName) {
        try {
            InputStream in = getResources().getAssets().open(fileName);
            int fileSize = in.available();
            byte[] buffer = new byte[fileSize];
            in.read(buffer);
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private boolean isCurrentlyPlaying() {
        //Toast.makeText(this, "duration:" + robotPlayer.getDuration() + ", position: " + robotPlayer.getPosition(), Toast.LENGTH_LONG).show();

        return robotPlayer.getPosition() > 0;
    }
}
