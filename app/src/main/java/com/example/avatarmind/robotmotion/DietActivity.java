package com.example.avatarmind.robotmotion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.robot.speech.SpeechManager;
import android.robot.speech.SpeechService;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class DietActivity extends Activity implements View.OnClickListener {

    private ImageView mTitleBack;
    private ImageButton mBtnVegeterian;
    private ImageButton mBtnNonVegetarian;
    private ImageButton mBtnFruit;
    private ImageButton mBtnDietician;
    private SpeechManager mSpeechManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }


        mSpeechManager = (SpeechManager) getSystemService(SpeechService.SERVICE_NAME);
        mSpeechManager.startSpeaking("Please spend some time reading this dietary advise from our experts.");
        initView();
        initListener();

    }

    private void initView() {
        setContentView(R.layout.activity_diet);

        mTitleBack = (ImageView) findViewById(R.id.common_title_back);
        mBtnVegeterian = (ImageButton) findViewById(R.id.imageButton1);
        mBtnNonVegetarian = (ImageButton) findViewById(R.id.imageButton2);
        mBtnFruit = (ImageButton) findViewById(R.id.imageButton3);
        mBtnDietician = (ImageButton) findViewById(R.id.imageButton4);
    }

    private void initListener() {
        mTitleBack.setOnClickListener(this);
        mBtnVegeterian.setOnClickListener(this);
        mBtnNonVegetarian.setOnClickListener(this);
        mBtnFruit.setOnClickListener(this);
        mBtnDietician.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.imageButton1:
                intent.setClass(DietActivity.this, VegDietActivity.class);
                break;
            case R.id.imageButton2:
                intent.setClass(DietActivity.this, NonVegDietActivity.class);
                break;
            case R.id.imageButton3:
                intent.setClass(DietActivity.this, FruitDietActivity.class);
                break;
            case R.id.imageButton4:
                intent.setClass(DietActivity.this, ContactDieticianActivity.class);
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
