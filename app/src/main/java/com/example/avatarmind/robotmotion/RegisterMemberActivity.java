package com.example.avatarmind.robotmotion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        mECName = (EditText) findViewById(R.id.users_name_ec);
        mECPhoneEt = (EditText) findViewById(R.id.ec_users_phone);
        mUserAgeEt = (EditText) findViewById(R.id.edit_text_age);
        mBtnSubmit = (Button) findViewById(R.id.submit_button);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        //get reference to attendee node
        mFirebaseDatabase = mFirebaseInstance.getReference("attendees");

        //store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                //update toolbar title
                //getSupportActionBar().setTitle(appTitle);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Failed to read Value
                Log.e(TAG, "Failed to read app title value.", databaseError.toException());
            }
        });


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
                //intent.setClass(RegisterMemberActivity.this, ExtrasActivity.class);
                //save/update the attendee
                String name = mNameEt.getText().toString();
                String age = mUserAgeEt.getText().toString();
                String phone = mUserPhoneEt.getText().toString();
                String ecname = mECName.getText().toString();
                String ecphone  = mECPhoneEt.getText().toString();

                //check for already existed user id
                if(TextUtils.isEmpty(userId))
                {
                   // createUser(....);
                }
                break;
            case R.id.common_title_back:
                finish();
                return;
            default:
                break;
        }
        startActivity(intent);
    }

    //creating new user node under 'attendees'
    public void createUser()
    {
        //Todo
        //in real apps this userId should be fetched
        //by implementing firebase auth
        if(TextUtils.isEmpty(userId)){
            userId = mFirebaseDatabase.push().getKey();
        }

        //Attendee attendee = new Attendee(name, age, phone, ecname, ecphone);
    }
}
