package com.example.avatarmind.robotmotion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.robot.speech.SpeechManager;
import android.robot.speech.SpeechService;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterMemberActivity extends Activity implements View.OnClickListener {

    private static final String TAG = RegisterMemberActivity.class.getSimpleName();

    private ImageView mTitleBack;
    private EditText mNameEt;
    private EditText mUserPhoneEt;
    private EditText mECPhoneEt;
    private EditText mECName;
    private EditText mUserAgeEt;
    private Button mBtnSubmit;

    private String userId;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private SpeechManager mSpeechManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }

        mSpeechManager = (SpeechManager) getSystemService(SpeechService.SERVICE_NAME);
        mSpeechManager.startSpeaking("Welcome and Please Register as a new member.");

        initView();
        initListener();

    }

    private void initView() {
        setContentView(R.layout.activity_register_member);

        mTitleBack = (ImageView) findViewById(R.id.common_title_back);
        mNameEt = (EditText) findViewById(R.id.edit_name);
        mUserPhoneEt = (EditText) findViewById(R.id.users_phone);
        mECName = (EditText) findViewById(R.id.users_name_ec);
        mECPhoneEt = (EditText) findViewById(R.id.ec_users_phone);
        mUserAgeEt = (EditText) findViewById(R.id.edit_text_age);
        mBtnSubmit = (Button) findViewById(R.id.submit_button);

        mFirebaseInstance = FirebaseDatabase.getInstance();
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
                createMember();
                intent.setClass(RegisterMemberActivity.this, MainActivity.class);
                break;
            case R.id.common_title_back:
                finish();
                return;
            default:
                break;
        }
        startActivity(intent);
    }

    //creating new attendee node under 'attendees'
    public void createMember()
    {
        mFirebaseDatabase = mFirebaseInstance.getReference("members");
        String name = mNameEt.getText().toString();
        String phone = mUserPhoneEt.getText().toString();
        String age = mUserAgeEt.getText().toString();
        String ecName = mECName.getText().toString();
        String ecPhone = mECPhoneEt.getText().toString();

        Attendee attendee = new Attendee(name, age, phone, ecName, ecPhone);
        mFirebaseDatabase.push().setValue(attendee);
        registerAttendee(attendee);
    }

    public void registerAttendee(Attendee attendee){
        mFirebaseDatabase = mFirebaseInstance.getReference("attendees");
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String strDate = formatter.format(date);
        Log.i("date", strDate);
        mFirebaseDatabase.child(strDate.toString()).push().setValue(attendee.getName());
        Toast.makeText(this, "You have been registered for today's session.",
                Toast.LENGTH_SHORT).show();
    }


}
