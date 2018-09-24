package com.example.avatarmind.robotmotion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SessionActivity extends Activity implements View.OnClickListener {

    private ImageView mTitleBack;

    private Button mBtnYoga;

    private Button mBtnHealthyDiet;

    private Button mBtnAerobics;

    private Button mBtnExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }

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

    }

    private void initListener() {
        mTitleBack.setOnClickListener(this);
        mBtnYoga.setOnClickListener(this);
        mBtnHealthyDiet.setOnClickListener(this);
        mBtnAerobics.setOnClickListener(this);
        mBtnExtras.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.main_yoga:
                intent.setClass(SessionActivity.this, ExtrasActivity.class);
                break;
            case R.id.main_healthy_diet:
                intent.setClass(SessionActivity.this, DietActivity.class);
                break;
            case R.id.main_aerobics:
                intent.setClass(SessionActivity.this, WheelActivity.class);
                break;
            case R.id.main_extras:
                intent.setClass(SessionActivity.this, EmojiActivity.class);
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
