package com.example.sse.minisink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

private EditText edtIntentData;

//NO DECLARATIONS ARE NECESSARY WITH EARLY BINDING, UNLESS YOU WANT TO MANIPULATE THE OBJECT.
//    private ImageButton imgBtnMap;
 //   private ImageButton imgBtnCamera;
//    private ImageButton imgBtnMic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//NOTE: NONE OF THIS IS NECESSARY WITH EARLY BINDING
//    imgBtnMap = (ImageButton) findViewById(R.id.imgBtnMap);
//    imgBtnCamera = (ImageButton) findViewById(R.id.imgBtnCamera);
//    imgBtnMic = (ImageButton) findViewById(R.id.imgBtnMic);
//        imgBtnMic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        edtIntentData = (EditText) findViewById(R.id.edtIntentData);
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);  //prevent keyboard from popping up, unless user explicitly clicks editText
                                                                                                        //Ref: http://stackoverflow.com/questions/2496901/android-on-screen-keyboard-auto-popping-up
    }

    //Review Early Binding
    public void onAnyButtonClick(View v) {

        Intent NextScreen;
        String intentData = edtIntentData.getText().toString();  //refers to the textbox at the top, [optional] fill in with a string if you want to pass to the next activity.
        switch (v.getId()){
            case R.id.imgBtnMap:
                NextScreen = new Intent("com.example.sse.minisink.MapsActivity");  //this is another way of identifying your destination class, Intent constructor is overloaded to also take string.
                if (intentData != "")
                  NextScreen.putExtra("GeneralIntentData", intentData );
                startActivity(NextScreen);
                break;

            case R.id.imgBtnCamera:
                NextScreen = new Intent("com.example.sse.minisink.CameraActivity");
                if (intentData != "")
                    NextScreen.putExtra("GeneralIntentData", intentData );
                startActivity(NextScreen);
            break;

            case R.id.imgBtnMic:
                NextScreen = new Intent("com.example.sse.minisink.MicActivity");
                if (intentData != "")
                    NextScreen.putExtra("GeneralIntentData", intentData );
                startActivity(NextScreen);
                break;

            default:

        }

    }


}
