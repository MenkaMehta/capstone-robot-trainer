package com.example.avatarmind.robotmotion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterMemberActivity extends Activity implements View.OnClickListener {

    private ImageView mTitleBack;
    private EditText mNameEt;
    private EditText mUserPhoneEt;
    private EditText mECPhoneEt;
    private EditText mUserAgeEt;
    private Button mBtnSubmit;


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
        setContentView(R.layout.activity_register_member);

        mTitleBack = (ImageView) findViewById(R.id.common_title_back);
        mNameEt = (EditText) findViewById(R.id.edit_name);
        mUserPhoneEt = (EditText) findViewById(R.id.users_phone);

        mECPhoneEt = (EditText) findViewById(R.id.users_phone_ec);
        mUserAgeEt = (EditText) findViewById(R.id.edit_text_age);
        mBtnSubmit = (Button) findViewById(R.id.submit_button);
    }

    private void initListener() {
        mTitleBack.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.submit_button:
                intent.setClass(RegisterMemberActivity.this, ExtrasActivity.class);
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
