package com.example.avatarmind.robotmotion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ConfirmRegistrationActivity extends Activity implements View.OnClickListener {

    private ImageView mTitleBack;
    private EditText mConfUserPhoneEt;
    private Button mBtnSubmit;
    private Button mBtnContinue;



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
        setContentView(R.layout.activity_confirm_registration);

        mTitleBack = (ImageView) findViewById(R.id.common_title_back);
        mConfUserPhoneEt = (EditText) findViewById(R.id.confirm_users_phoneEt);
        mBtnSubmit = (Button) findViewById(R.id.confirmed_submit_button);
        mBtnContinue = (Button) findViewById(R.id.continue_button);
    }

    private void initListener() {
        mTitleBack.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);
        mBtnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.submit_button:
                intent.setClass(ConfirmRegistrationActivity.this, ExtrasActivity.class);
                break;
            case R.id.continue_button:
                intent.setClass(ConfirmRegistrationActivity.this, MainActivity.class);
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
