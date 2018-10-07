package com.example.avatarmind.robotmotion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.robot.speech.SpeechManager;
import android.robot.speech.SpeechService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConfirmRegistrationActivity extends Activity implements View.OnClickListener {

    private DatabaseReference mDatabaseReference;
    private ValueEventListener mMembersListener;
    private FirebaseDatabase mFirebaseInstance;

    private ImageView mTitleBack;
    private EditText mConfUserPhoneEt;
    private Button mBtnSubmit;
    private Button mBtnContinue;
    private SpeechManager mSpeechManager;
    private List<Attendee> attendees = new ArrayList<Attendee>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        mFirebaseInstance = FirebaseDatabase.getInstance();

        mSpeechManager = (SpeechManager) getSystemService(SpeechService.SERVICE_NAME);
        mSpeechManager.startSpeaking("Provide a phone number for confirmation.");

        initView();

        // Get a reference to the Firebase Database
        mDatabaseReference = mFirebaseInstance.getReference("members");
        mMembersListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    // Extract an Attendee object from the DataSnapshot
                    Attendee attendee = child.getValue(Attendee.class);
                    attendees.add(attendee);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mDatabaseReference.addValueEventListener(mMembersListener);
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
                //validateMember();
                intent.setClass(ConfirmRegistrationActivity.this, MainActivity.class);
                break;
            case R.id.continue_button:
                validateMember();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseReference.removeEventListener(mMembersListener);
    }

    public void validateMember(){
        String memberPhone = mConfUserPhoneEt.getText().toString();
        boolean memberFound = false;
        for(Attendee attendee: attendees){
            //Log.i("attendee", attendee.getPhone());
            if(attendee.getPhone().equals(memberPhone)){
                memberFound = true;
                registerAttendee(attendee);
            }
        }
        if(!memberFound){
            Toast.makeText(this, "Sorry we couldn't find you in the system, please register as a member first.",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void registerAttendee(Attendee attendee){
        mDatabaseReference = mFirebaseInstance.getReference("attendees");
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String strDate = formatter.format(date);
        //Log.i("date", strDate);
        mDatabaseReference.child(strDate.toString()).push().setValue(attendee.getName());
        Toast.makeText(this, "Welcome back, you are in the session.",
                Toast.LENGTH_LONG).show();
    }
}
