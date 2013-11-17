package com.example.hackduke;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class JoinMeeting extends Activity implements OnClickListener {
    
    
    EditText meetingBox;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_meeting_id);
        meetingBox = (EditText)findViewById(R.id.meeting_id_box);
        Button startMeetingButton = (Button)findViewById(R.id.go_button);
        startMeetingButton.setOnClickListener(this);
    }
    
    

    protected void launchMainPhoneScreen () {
        Intent i = new Intent(this, MainPhoneScreen.class);
        i.putExtra(MainPhoneScreen.MeetingNumber, Long.parseLong(meetingBox.getText().toString()));
        i.putExtra(MainPhoneScreen.ISFIRSTTIME_STRING, false);
        startActivity(i);

    }



  



    @Override
    public void onClick (View v) {
        launchMainPhoneScreen();
        
    }

}
