package com.example.avatarmind.robotmotion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.robot.speech.SpeechManager;
import android.robot.speech.SpeechService;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SessionActivity extends Activity implements View.OnClickListener {

    private ImageView mTitleBack;

    private Button mBtnYoga;

    private Button mBtnHealthyDiet;

    private Button mBtnAerobics;

    private Button mBtnExtras;

    private Button mBtnRoboWorkout;

    private Button mBtnPlayWithMe;

    private SpeechManager mSpeechManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }

        mSpeechManager = (SpeechManager) getSystemService(SpeechService.SERVICE_NAME);
        mSpeechManager.startSpeaking("It is a good day! Lets workout.");
        initView();
        initListener();

    }

    private void initView() {
        setContentView(R.layout.activity_session);

        mTitleBack = (ImageView) findViewById(R.id.common_title_back);
        mBtnYoga = (Button) findViewById(R.id.main_yoga);
        mBtnHealthyDiet = (Button) findViewById(R.id.main_healthy_diet);
        mBtnAerobics = (Button) findViewById(R.id.main_aerobics);
        mBtnExtras = (Button) findViewById(R.id.main_extras);
        mBtnRoboWorkout = (Button) findViewById(R.id.main_robot_workout);
        mBtnPlayWithMe = (Button) findViewById(R.id.main_play_with_me);

    }

    private void initListener() {
        mTitleBack.setOnClickListener(this);
        mBtnYoga.setOnClickListener(this);
        mBtnHealthyDiet.setOnClickListener(this);
        mBtnAerobics.setOnClickListener(this);
        mBtnExtras.setOnClickListener(this);
        mBtnRoboWorkout.setOnClickListener(this);
        mBtnPlayWithMe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.main_yoga:
                intent.setClass(SessionActivity.this, YogaActivity.class);
                break;
            case R.id.main_healthy_diet:
                intent.setClass(SessionActivity.this, DietActivity.class);
                break;
            case R.id.main_aerobics:
                intent.setClass(SessionActivity.this, AerobicsActivity.class);
                break;
            case R.id.main_extras:
                intent.setClass(SessionActivity.this, SocialMediaActivity.class);
                break;
            case R.id.main_robot_workout:
                intent.setClass(SessionActivity.this, RoboticWorkoutActivity.class);
                break;
            case R.id.main_play_with_me:
                intent.setClass(SessionActivity.this, ExtrasActivity.class);
                break;
            case R.id.common_title_back:
                finish();
                return;
            default:
                break;
        }
        startActivity(intent);
    }
}
