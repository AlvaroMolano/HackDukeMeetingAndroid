package com.example.hackduke;


import java.util.HashMap;
import java.util.Map;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class CreateMeeting extends Activity {

    Firebase meetingRef = new Firebase("https://hackduke.firebaseio.com/current_value/");
    
    Long meetingNumber;
    Boolean done = false;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting_id);
        
        
        //Map<String, Object> meetingMap = new HashMap<String, Object>();
        //meetingRef.updateChildren(meetingMap);
        //Integer integer = (Integer) meetingMap.get("current_number");
        //final TextView meetingId = (TextView) findViewById(R.id.new_meeting_id);
        // meetingId.setText(""+meetingMap.size());
        //meetingRef.setValue(83488);
        meetingRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange (DataSnapshot snapshot) {
                if (!done) {
                    TextView meetingId = (TextView) findViewById(R.id.new_meeting_id);
                    meetingNumber = (Long)snapshot.getValue();
                    
                    // INCREMENTING MEETING
                    meetingNumber++;
                    
                    Log.e("CreateMeeting", "wasting data "+ meetingNumber);
                    meetingRef.setValue(meetingNumber);
                    meetingId.setText(meetingNumber.toString());
                    done = true;
                    }
            }

            @Override
            public void onCancelled (FirebaseError arg0) {
                // TODO Auto-generated method stub

            }
        });

        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick (View v) {
                launchMainPhoneScreen();

            }
        });

    }

    protected void launchMainPhoneScreen () {
        Intent i = new Intent(this, MainPhoneScreen.class);
        i.putExtra(MainPhoneScreen.MeetingNumber, meetingNumber);
        startActivity(i);

    }

}
