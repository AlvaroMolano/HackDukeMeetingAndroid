package com.example.hackduke;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainPhoneScreen extends Activity {

    public static final String MeetingNumber = "MeetingNumber";
    public static final String ISFIRSTTIME_STRING = "FirstTime";

    Firebase meetingRef;

    HashMap<String, Object> meeting_map = new HashMap<String, Object>();
    // Map<String,Bitmap> image_array = new HashMap<String,Bitmap>();
    ArrayList<String> question_array = new ArrayList<String>();
    String base = "https://hackduke.firebaseio.com/meetings/";
    Long meetingNumber;

    final int max_images = 4;
    String[] test = new String[4];
    String[] imageUrls = new String[max_images];

    String imageOneUrl;
    String imageTwoUrl;
    String imageThreeUrl;
    String imageFourUrl;
    Firebase imageOneRef;
    Firebase imageTwoRef;
    Firebase imageThreeRef;
    Firebase imageFourRef;

    int image_count = 0;

    // Bitmap[] images = new Bitmap[4];

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_phone_screen);
        Intent i = this.getIntent();

        meetingNumber = i.getLongExtra(MeetingNumber, 0);

        base += meetingNumber + "/";
        meetingRef = new Firebase(base);
        imageOneUrl = base.concat("0/");
        imageTwoUrl = base.concat("1/");
        imageThreeUrl = base.concat("2/");
        imageFourUrl = base.concat("3/");
        imageOneRef = new Firebase(imageOneUrl);
        imageTwoRef = new Firebase(imageTwoUrl);
        imageThreeRef = new Firebase(imageThreeUrl);
        imageFourRef = new Firebase(imageFourUrl);

        Boolean firstTime = i.getBooleanExtra(ISFIRSTTIME_STRING, true);
        if (firstTime) {
            meeting_map.put("image_count", new Integer(0));
            // meeting_map.put("image_array",image_array);
            meeting_map.put("question_array", question_array);
            meetingRef.setValue(meeting_map);
        }
            TextView meeting_id_text_view = (TextView) findViewById(R.id.meeting_id_text_view);

            meeting_id_text_view.setText("Meeting ID: " + meetingNumber.toString());

        

        Button add_question_button = (Button) findViewById(R.id.add_question_button);
        Button add_file_button = (Button) findViewById(R.id.add_file_button);

        for (int image_iterator = 0; image_iterator < max_images; image_iterator++) {
            imageUrls[image_iterator] = base + "" + image_iterator + "/";
        }

        add_question_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick (View v) {

                // launchMainPhoneScreen();
                add_question_dialog();
            }
        });
        add_file_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick (View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });
        Log.e("Url", imageOneUrl);
        imageOneRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange (DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    // Bitmap b = (Bitmap)snapshot.getValue();
                    ImageView v = (ImageView) findViewById(R.id.image_one);
                    v.setImageBitmap(BitmapConverter.StringToBitMap((String) snapshot.getValue()));
                    // Log.e("MPS", snapshot.getValue()+"");
                }
            }

            @Override
            public void onCancelled (FirebaseError arg0) {
                // TODO Auto-generated method stub

            }
        });
        imageTwoRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange (DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    ImageView v = (ImageView) findViewById(R.id.image_two);
                    v.setImageBitmap(BitmapConverter.StringToBitMap((String) snapshot.getValue()));
                }
            }

            @Override
            public void onCancelled (FirebaseError arg0) {
                // TODO Auto-generated method stub

            }
        });
        imageThreeRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange (DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    ImageView v = (ImageView) findViewById(R.id.image_three);
                    v.setImageBitmap(BitmapConverter.StringToBitMap((String) snapshot.getValue()));
                }
            }

            @Override
            public void onCancelled (FirebaseError arg0) {
                // TODO Auto-generated method stub

            }
        });
        imageFourRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange (DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    ImageView v = (ImageView) findViewById(R.id.image_four);
                    v.setImageBitmap(BitmapConverter.StringToBitMap((String) snapshot.getValue()));
                }
                else {

                }
                // Log.e("MainPhoneScreen", map.size()+"s");
            }

            @Override
            public void onCancelled (FirebaseError arg0) {
                // TODO Auto-generated method stub

            }
        });

        meetingRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange (DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    image_count =
                            ((Long)((HashMap<String, Object>) snapshot.getValue())
                                    .get("image_count")).intValue();
                }
            }

            @Override
            public void onCancelled (FirebaseError arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private static final int SELECT_PHOTO = 100;

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                        ImageView v;
                        switch (image_count) {
                            case 0:
                                v = (ImageView) findViewById(R.id.image_one);
                                break;
                            case 1:
                                v = (ImageView) findViewById(R.id.image_two);
                                break;
                            case 2:
                                v = (ImageView) findViewById(R.id.image_three);
                                break;
                            default:
                                v = (ImageView) findViewById(R.id.image_four);
                                break;
                        }
                        v.setImageBitmap(BitmapConverter.StringToBitMap((BitmapConverter
                                .BitMapToString(yourSelectedImage))));
                        // Log.e("What is the to strip",
                        // BitmapConverter.convert(yourSelectedImage)+"");
                        String imageurl = imageUrls[Math.min(image_count, 3)];
                        Firebase meetingRefPic = new Firebase(imageurl);
                        meetingRefPic.setValue(BitmapConverter.BitMapToString(yourSelectedImage));
                        // meetingRef.updateChildren(meeting_map);

                        if (image_count < 4) {
                            image_count++;
                        }
                        meeting_map.put("image_count", image_count);
                        meetingRef.updateChildren(meeting_map);

                    }
                    catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
        }
    }

    public void add_question_dialog () {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        /*
         * builder.setMessage(R.string.dialog_fire_missiles)
         * .setPositiveButton(R.string.fire, new
         * DialogInterface.OnClickListener() {
         * public void onClick(DialogInterface dialog, int id) {
         * // FIRE ZE MISSILES!
         * }
         * })
         * .setNegativeButton(R.string.cancel, new
         * DialogInterface.OnClickListener() {
         * public void onClick(DialogInterface dialog, int id) {
         * // User cancelled the dialog
         * }
         * });
         */
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    public void add_file_dialog () {

    }

}
