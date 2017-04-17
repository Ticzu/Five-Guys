package com.example.sse.minisink;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MicActivity extends AppCompatActivity {

    private EditText edtTalkToMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mic);

        Intent i = getIntent();
        if (i.hasExtra("GeneralIntentData")){
            String value = i.getStringExtra("GeneralIntentData");
         //   txtMessage.setText(value);
        }

        edtTalkToMe = (EditText) findViewById(R.id.edtTalkToMe);
    }

    public void onAnyButtonClick(View v) {
        if (v.getId() == R.id.imgBtnTalkToMe)
        {
            Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
          //  i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
          //  i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr_FR");
          //  i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-cmn");   //chinese doesn't work, ref: http://stackoverflow.com/questions/27194988/speech-to-text-api-other-language-android
          //  i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-us");
//            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es");         //Spanish
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-EG");      //Arabic
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Talk to Me!");  //this is what the user sees before they talk.

            try
            {
                startActivityForResult(i, 999);  //the 999 serves as our request code (breadcrumb), so we know what to phish for if we start other activities for results.
            }
            catch (ActivityNotFoundException e)
            {
                Toast.makeText(MicActivity.this, "Microphone not available.  Does you device have a Mic?", Toast.LENGTH_LONG);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 999 && resultCode == RESULT_OK)  //Ensuring we are following the right bread crumb trail, and that the result is OK.  q&d - better to use a constant, not 999.
        {
            ArrayList<String> txtspeech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);  //EXTRA_RESULTS CONTAINS THE INTENT'S RETURNED TEXT
            edtTalkToMe.setText(txtspeech.get(0));

//            String speech = data.getStringExtra(RecognizerIntent.EXTRA_RESULTS);
//            edtTalkToMe.setText(speech);
        }


    }
}

