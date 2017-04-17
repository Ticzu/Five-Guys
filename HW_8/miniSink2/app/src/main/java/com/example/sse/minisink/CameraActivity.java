package com.example.sse.minisink;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CameraActivity extends AppCompatActivity {

    private ImageView imgPhotoTaken;
    private ImageButton imgBtnSayCheeese;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Intent i = getIntent();
        if (i.hasExtra("GeneralIntentData")){
            String value = i.getStringExtra("GeneralIntentData");
         //   txtMessage.setText(value);
        }

        imgPhotoTaken = (ImageView) findViewById(R.id.imgPhotoTaken);


        imgBtnSayCheeese = (ImageButton) findViewById(R.id.imgBtnSayCheeese);
        imgBtnSayCheeese.setOnClickListener(new View.OnClickListener() {  //Q: Who wins early or late binding?  Why?
            @Override
            public void onClick(View v) {
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {  //before proceeding with taking a photo, we should ensure we have a camera. :)
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  //intending to use camera.
                    startActivityForResult(i, 999);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == 999 && resultCode == RESULT_OK)
    {
        //The image is stored in a Bundle named data.  Retrieve and render it as follows.
        Bundle PhotoBundle = data.getExtras();
        Bitmap PhotoBitmap = (Bitmap) PhotoBundle.get("data");
        imgPhotoTaken.setImageBitmap(PhotoBitmap);
    }

    }
}
