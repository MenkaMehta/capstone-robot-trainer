package com.example.avatarmind.robotmotion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.robot.speech.SpeechManager;
import android.robot.speech.SpeechService;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements View.OnClickListener {

    private ImageView mTitleBack;

    private Button mBtnNewMember;

    private Button mBtnExistingMember;

    private Button mBtnStartSession;

    private SpeechManager mSpeechManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }

        initView();
        initListener();

      // mSpeechManager = (SpeechManager) getSystemService(SpeechService.SERVICE_NAME);

    }

    private void initView() {
        setContentView(R.layout.activity_main);

        mTitleBack = (ImageView) findViewById(R.id.common_title_back);
        mBtnNewMember = (Button) findViewById(R.id.main_new_member);
        mBtnExistingMember = (Button) findViewById(R.id.main_existing_member);
        mBtnStartSession = (Button) findViewById(R.id.main_start_session);
    }

    private void initListener() {
        mTitleBack.setOnClickListener(this);
        mBtnNewMember.setOnClickListener(this);
        mBtnExistingMember.setOnClickListener(this);
        mBtnStartSession.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //mSpeechManager.startSpeaking("Hi people, how are you today!");
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.main_new_member:
                intent.setClass(MainActivity.this, RegisterMemberActivity.class);
                break;
            case R.id.main_existing_member:
                intent.setClass(MainActivity.this, ConfirmRegistrationActivity.class);
                break;
            case R.id.main_start_session:
                intent.setClass(MainActivity.this, SessionActivity.class);
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
