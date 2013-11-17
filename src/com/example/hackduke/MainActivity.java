package com.example.hackduke;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener {
    
    // Create a reference to a Firebase location
    
    Firebase ref2 = new Firebase("https://hackduke.firebaseio.com/7435282457/images");
    Map<String, Object> test = new HashMap<String, Object>();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button join = (Button)findViewById(R.id.join_button);
        Button create = (Button)findViewById(R.id.create_button);
        join.setOnClickListener(this);
        create.setOnClickListener(this);
        
        
        // Write data to Firebase
        Map<String, Object> test = new HashMap<String, Object>();
        //ref.setValue(test);
       // test.put("hello", "hi");
        //test.put("bye", "bye");
        //ref.updateChildren(test);
        
        Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.grumpy_cat);
       //ref2.setValue(image);
        //ref.push("{name: name, text: text}")b

        // Read data and react to changes
        /*
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snap) {
                System.out.println(snap.getName() + " -> " + snap.getValue());
            }

          

            @Override
            public void onCancelled (FirebaseError arg0) {
                // TODO Auto-generated method stub
                
            }
        }); */
        
   
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick (View v) {
        switch(v.getId()){
            case R.id.create_button:
                createMeeting();
                break;
            case R.id.join_button:
                moveToMeeting();
                break;
            		
        }
        
    }
    
    private void createMeeting () {
        Intent createMeetingIntent = new Intent(this, CreateMeeting.class);
        startActivity(createMeetingIntent);
    }

    public void moveToMeeting()
    {
        Intent moveToMeetingIntent = new Intent(this, JoinMeeting.class);
        startActivity(moveToMeetingIntent);
        
    }
    
    public class Test<Integer, String> { 
        public final Integer x; 
        public final String content; 
        public Test(Integer x, String string) { 
          this.x = x; 
          this.content = string; 
        } 
      }
    
    


   


}
