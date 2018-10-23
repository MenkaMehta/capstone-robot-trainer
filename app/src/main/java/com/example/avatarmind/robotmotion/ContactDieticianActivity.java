package com.example.avatarmind.robotmotion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.robot.speech.SpeechManager;
import android.robot.speech.SpeechService;
import android.view.View;
import android.widget.ImageView;

public class ContactDieticianActivity extends Activity implements View.OnClickListener {

    private ImageView mTitleBack;
    private SpeechManager mSpeechManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }

        mSpeechManager = (SpeechManager) getSystemService(SpeechService.SERVICE_NAME);
        mSpeechManager.startSpeaking(" Please book consultations. We are happy to help.");
        initView();
        initListener();
    }

    private void initView() {

        setContentView(R.layout.activity_dietician_contacts);
        mTitleBack = (ImageView) findViewById(R.id.common_title_back);

    }

    private void initListener() {
        mTitleBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.common_title_back:
                finish();
                return;
            default:
                break;
        }
        startActivity(intent);
    }
}
