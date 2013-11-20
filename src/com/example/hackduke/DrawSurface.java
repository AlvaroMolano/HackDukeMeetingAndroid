package com.example.hackduke;

import java.util.HashMap;
import java.util.Map;
import com.firebase.client.Firebase;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class DrawSurface extends SurfaceView implements SurfaceHolder.Callback, Runnable

{
    Context context;
    Bitmap bitmap;
    String endpoint;
    Firebase refMovement;
    
    Map<String, Object> test = new HashMap<String, Object>();

    public DrawSurface (Context ctx, String bitmap, String endpoint)

    {

        super(ctx);
        this.endpoint = endpoint;
        context = ctx;
        setBitmap(bitmap);
        
        refMovement = new Firebase(endpoint);

        // the bitmap we wish to draw
        Log.e("Craeted view", "created");

        SurfaceHolder holder = getHolder();

        holder.addCallback(this);

    }
    
    public void kill(){
        //t.interrupt();
        //t.stop();
        //t.alive = false;
        //t = null;
        t.setActive(false);
        
    }

    public DrawSurface (Context ctx, AttributeSet attrSet)

    {

        super(ctx, attrSet);

        context = ctx;

        // the bitmap we wish to draw

        SurfaceHolder holder = getHolder();

        holder.addCallback(this);

    }

    @Override
    public void surfaceDestroyed (SurfaceHolder holder)

    {

    }

    @Override
    public void surfaceChanged (SurfaceHolder holder, int format, int width, int height)

    {

    }

    SurfaceHolder holder;
    boolean ready = false;
    DrawThread t;

    @Override
    public synchronized void surfaceCreated (SurfaceHolder holder) {
        Log.e("REady!", "" + (bitmap == null));
        this.holder = holder;

        t = new DrawThread(bitmap, holder, this);
        t.start();

    }

    public void setBitmap (String bitmap) {
        this.bitmap = BitmapConverter.StringToBitMap(bitmap);
        Log.e("Bitmap", "" + (this.bitmap.getWidth()));

    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        // TODO Auto-generated method stub
        // return super.onTouchEvent(event);
        int action = event.getAction();
        if(t==null){
            return false;
        }
        if (action == MotionEvent.ACTION_MOVE) {
            t.x = (int) event.getX();
            t.y = (int) event.getY();
            t.enabled = true;

        }
        else if (action == MotionEvent.ACTION_DOWN) {
            t.x = (int) event.getX();
            t.y = (int) event.getY();
            t.enabled = true;
        }
        else if (action == MotionEvent.ACTION_UP) {
            t.enabled = false;
        }

        return true;
    }

    @Override
    public void run () {

    }

    class DrawThread extends Thread {

        Bitmap bitmap;
        SurfaceHolder holder;
        DrawSurface d;
        boolean enabled = false;
        int x;
        int y;
        
        public synchronized void setActive(boolean value){
            alive = false;
        }

        public DrawThread (Bitmap b, SurfaceHolder h, DrawSurface d) {
            Log.e("Bitmap?", "" + (b != null));
            bitmap = b;
            holder = h;
            this.d = d;

        }
        boolean alive = true;
        @Override
        public void run () {

            Log.e("I called ", "run");
            Log.e("I called ", "" + bitmap.getWidth());
            while (alive) {

                Canvas canvas = this.holder.lockCanvas();
                Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                Rect des = new Rect(0, 0, d.getWidth(), d.getHeight());
                //Log.e("src", "" + src.toShortString());
                canvas.drawBitmap(bitmap, src, des, null);
                if (enabled) {
                    canvas.drawCircle(x, y, 40, new Paint(Color.YELLOW));
                    PointF point = new PointF((float)(x)/bitmap.getWidth(),(float)(y)/bitmap.getHeight());
                    refMovement.setValue(point);
                }
                this.holder.unlockCanvasAndPost(canvas);

            }
        }

    }

}
