package com.example.hw_challenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import static android.R.attr.right;

public class hw_challenge extends AppCompatActivity {

    //following objects are the widgets showing in the app
    private TextView tvNum1;
    private TextView tvNum2;
    private TextView tvSign;
    private Button btnGenerate;
    private RadioGroup rg;
    private RadioButton rb;
    private EditText edtInput;
    private Button btnSubmit;
    private  TextView tvCurrent;
    private Button btTop5;
    private String usr;
    DatabaseReference myRef;

    //following three arrays for saving the 10 math problems values
    int[] a = new int[10];//10 random numbers
    int[] b = new int[10];//10 random numbers
    int[] res = new int[10];//the result of addition or subtraction for a and b
    int right = 0;//counting the right numbers the user get
    int count = 1;//recording the numbers until all the 10 problems are showing completely
    // when changing the status of labels in the loop after user press the submit button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRef = FirebaseDatabase.getInstance().getReference();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            usr = extras.getString("curr_username");

        }

        btTop5 = (Button) findViewById(R.id.btTop5);
        btTop5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TopFiveActivity.class);
                i.putExtra("curr_username", usr);
                startActivity(i);
            }
        });


        Toast toast = Toast.makeText(getApplicationContext(), "Welcome " + usr, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);//put the toast in the center
        toast.show();
        btnGenerate = (Button) findViewById(R.id.btnGenerate);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        tvNum1 = (TextView) findViewById(R.id.tvNum1);
        tvNum2 = (TextView) findViewById(R.id.tvNum2);
        tvSign = (TextView) findViewById(R.id.tvSign);
        tvCurrent = (TextView) findViewById(R.id.tvCurrent);

        //set a listener for the button generate 10 ...
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = rg.getCheckedRadioButtonId();//recording the status of radioGroup
                Log.i("dd", String.valueOf(selectedId));

                right = 0;
                count = 1;
                if (selectedId != -1) {//-1 means the user does not choose the radioButton
                    rb = (RadioButton) findViewById(selectedId);
                    String sign =(String) rb.getText();
                    Random rand = new Random();

                    if (sign.equals("Addition")) {//addition
                        for (int i = 0; i < 10; i++) {//this loop saving the randomly generated numbers
                            int aNumber = rand.nextInt(1000);//randomly generate numbers between 0-999
                            int bNumber = rand.nextInt(1000);//same
                            a[i] = aNumber;
                            b[i] = bNumber;
                        }

                        for (int i = 0; i < 10; i++) {//this loop for recording the result of addtion of the two arrays
                            res[i] = a[i] + b[i];
                        }

                        //when user click the button generate 10 math problems, it will showing the first problem
                        tvNum1.setText(String.valueOf(a[0]));
                        tvNum2.setText(String.valueOf(b[0]));
                        tvSign.setText("+");
                        tvCurrent.setText("1/10");//showing the current number of problems the user is calculating

                        //prompting the user start
                        Toast toast = Toast.makeText(hw_challenge.this, "start", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 170);
                        toast.show();
                    }
                    else if (sign.equals("Subtraction")){//subtraction
                        for (int i = 0; i < 10; i++) {//this loop saving the randomly generated numbers
                            int bNumber = rand.nextInt(1000);//randomly generate numbers between 0-999
                            int aNumber = rand.nextInt(1000 - bNumber) + bNumber;//randomly generate numbers between bNumber-999 so that the top number is always greater than or equal to the bottom one
                            a[i] = aNumber;
                            b[i] = bNumber;
                        }

                        for (int i = 0; i < 10; i++) {//this loop for recording the result of subtraction of the two arrays
                            res[i] = a[i] - b[i];
                        }


                        tvNum1.setText(String.valueOf(a[0]));
                        tvNum2.setText(String.valueOf(b[0]));
                        tvSign.setText("-");
                        tvCurrent.setText("0/10");

                        Toast toast = Toast.makeText(hw_challenge.this, "start", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 170);
                        toast.show();
                    }
                }
                else {//if the user does not choose the sign, there will be a toast in order to remind the user of choosing the sign
                    Toast toast = Toast.makeText(hw_challenge.this, "please choose the sign", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 170);
                    toast.show();
                }
            }
        });

        //set a listener for the button submit
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rg.getCheckedRadioButtonId();
                rb = (RadioButton) findViewById(selectedId);
                edtInput = (EditText) findViewById(R.id.edtInput);

                String ans2 = edtInput.getText().toString();//get the text in the edtInput
                if (ans2.equals("")){//if user does not input anything, there will be a toast prompting the user
                    Toast toast = Toast.makeText(hw_challenge.this, "please input your answer", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 170);
                    toast.show();
                }else{
                    int ans = Integer.parseInt(ans2);
                    if (count <= 10 && ans == res[count-1]){//comparing the input with the array res, and if the ans is right, the right variable will self-add
                        right++;
                    }


                    edtInput.setText("");//each time initialize the text of edtInput for user convenience

                    if (count < 10) {//counting the numbers showing next, if haven't up to 10 problems, continue
                        tvNum1.setText(String.valueOf(a[count]));
                        tvNum2.setText(String.valueOf(b[count]));
                        tvCurrent.setText(String.valueOf(count + 1) + "/10");//showing the current number user is calculating
                        count++;
                    }else{
                        Toast toast = Toast.makeText(hw_challenge.this, "end", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 170);
                        toast.show();

                        //showing the right numbers user get out of the 10 problems
                        Toast toast2 = Toast.makeText(hw_challenge.this, "Your score is " + right, Toast.LENGTH_LONG);
                        toast2.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 170);
                        toast2.show();

                        myRef.child("Users").child(usr).child("hiscore").setValue(right);
                    }
                }
            }
        });
    }
}

