package com.example.hackduke;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceView;
import android.widget.LinearLayout;

public class DrawActivity extends Activity {
    
    DrawSurface drawSurface;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_view);
        
        String bitmap = this.getIntent().getStringExtra(MainPhoneScreen.BITMAP);
        String endpoint = this.getIntent().getStringExtra(MainPhoneScreen.END);
        drawSurface = new DrawSurface(this.getApplicationContext(), bitmap, endpoint);
        LinearLayout l = (LinearLayout) findViewById(R.id.cursor_view);
        l.addView(drawSurface);
        
        drawSurface.setBitmap(bitmap);
        
    
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
               drawSurface.kill();
            return true;
        }
        this.finish();
        return super.onKeyDown(keyCode, event);
    }
    
    

}
