package com.example.hw_challenge;

import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


/*
 In addition to the above, implement your own Second Grade Flash Card App.  The App will provide students with ten random math questions, either addition or subtraction.  For subtraction always ensure the top number is greater than or equal to the bottom.  Students will enter their response in a textbox.  Use a Single Activity.  Your program will utilize Java as the code-behind layer of your Activity and will generate 10 random problems, students will enter the answer into the textbox, then depress submit.  After 10 submissions the student’s score should popup in a Toast.
We will improve upon the design later, don’t worry about telling students which problems they got right or wrong, or giving them immediate or delayed feedback.  Provide a descriptive Storyboard for your App.  Much of the design and user interaction is up to you, but feel free to use the template below as a guide.
 */

public class hw_challenge
        extends AppCompatActivity {

    private Button btnSubmit;
    private Button btnGenerate;
    private RadioButton rbtnAdd;
    private RadioButton rbtnSub;
    private TextView tvinPut1;
    private TextView tvinPut2;
    private TextView Sig;
    private EditText edtResult;
    private RadioGroup radioGrp;
    private  simplelogin login;
    private String usr;
    int checkid;
    int countSubmit = 0;
    int result;
    int rightCount = 0;

    ArrayList<Integer> rightAsn = new ArrayList<Integer>();
    ArrayList<Integer> wrongAsn = new ArrayList<Integer>();
    private static String LogFilterFlag = "Removing";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw_challenge);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            usr = extras.getString("curr_username");

        }
        Log.i("username", usr);

        tvinPut1 = (TextView)findViewById(R.id.inPut1);
        tvinPut2 = (TextView)findViewById(R.id.inPut2);

        Sig = (TextView)findViewById(R.id.sig);
        login = new simplelogin();
        edtResult = (EditText)findViewById(R.id.result);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnGenerate = (Button)findViewById(R.id.btnClickMe);
        rbtnAdd = (RadioButton)findViewById(R.id.plusButton);
        rbtnSub = (RadioButton)findViewById(R.id.subButton);
        radioGrp = (RadioGroup)findViewById(R.id.radioGrp);
        Bundle bundle = getIntent().getExtras();
        String text= bundle.getString("username");
        Toast toast_wel = Toast.makeText(getApplicationContext(),"Welcome " + text + " " +
                                                                         "Congratulations." , Toast.LENGTH_SHORT);
        toast_wel.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast_wel.show();

        String S = "Please first select problem types first";
        Toast.makeText(getBaseContext(),S,Toast.LENGTH_LONG).show();
        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.plusButton:
                        Sig.setText("+");
                        Sig.setScaleX(1);
                        checkid = R.id.plusButton;
                        break;
                    case R.id.subButton:
                        Sig.setText("-");
                        Sig.setScaleX(2);
                        checkid = R.id.subButton;
                        break;
                }
            }
        });

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                edtResult.setText("");
                switch (checkid) {
                    case R.id.plusButton:
                        Random r = new Random();
                        int i1 = r.nextInt(50) + 1;
                        int i2 = r.nextInt(50) + 1;
                        tvinPut1.setText(String.valueOf(i1));
                        tvinPut2.setText(String.valueOf(i2));
                        result = i1 + i2;

                    case R.id.subButton:
                        Random rs = new Random();
                        int is1 = rs.nextInt(50) + 1;
                        int is2 = rs.nextInt(50) + 1;
                        if (is1 < is2) {
                            int tempt = is1;
                            is1 = is2;
                            is2 = tempt;
                        }

                        tvinPut1.setText(String.valueOf(is1));
                        tvinPut2.setText(String.valueOf(is2));
                        result = is1 - is2;

                    default:
                        if (checkid != R.id.plusButton && checkid != R.id.subButton) {
                            Toast.makeText(getBaseContext(),"Please select problem type first",Toast.LENGTH_LONG).show();
                        }


                }
            }
        });

    }

    public void onClickSubmit(View v) {
        if (countSubmit == 0) {
            wrongAsn.clear();
        }


        String res = " ";

        if (!isEmpty(edtResult)){
            if (edtResult.getText().toString().equals(String.valueOf(result))) {
                countSubmit += 1;
                res = "true";
                rightCount += 1;
            }
            else {
                edtResult.setText(String.valueOf(result));
                countSubmit += 1;
                res = "false";
                wrongAsn.add(countSubmit);
            }
            Toast.makeText(getBaseContext(),"You submitted problem " + countSubmit + ". The Answer " +
                                                    "is " + res,Toast.LENGTH_SHORT).show();

        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(),"Please enter your answer",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }


        // this is to reset the process
        if (countSubmit == 10) {
            StringBuilder builder = new StringBuilder();
            for (Integer value : wrongAsn) {
                builder.append(value);
            }
            String wrongtext = builder.toString();

            Toast toast2 = Toast.makeText(getApplicationContext(),"Your Score is " + rightCount + ". The wrong problems are: " + wrongAsn + ",",Toast
                                                                                                      .LENGTH_SHORT);
            toast2.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);

            LinearLayout toastLayout = (LinearLayout) toast2.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(30);
            toast2.show();
            countSubmit = 0;


        }




    }

    public void onDestroy() {
        super.onDestroy();
        Log.e(LogFilterFlag, "onDestroy Called.");
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
        {
            return false;
        }
        return true;
    }


}
