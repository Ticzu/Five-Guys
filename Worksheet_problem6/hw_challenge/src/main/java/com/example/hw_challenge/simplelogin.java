package com.example.hw_challenge;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class simplelogin
        extends AppCompatActivity {

    private Button btnNext;
    private TextView tv1;
    private TextView tv2;
    private EditText edtusr;
    private EditText edtpass;
    private int counter = 10;
    private String usr = "cshou";
    private String psw = "1234";
    public String usrWel;
    public boolean islogin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simplelogin);

        btnNext = (Button)findViewById(R.id.btnClicktoSubmit);
        edtusr = (EditText)findViewById(R.id.edtUser);
        edtpass = (EditText)findViewById(R.id.edtpsw);
        tv1 = (TextView)findViewById(R.id.tvUser);
        tv2 = (TextView)findViewById(R.id.tvpsw);
        Toast toast = Toast.makeText(getApplicationContext(),"Please login first",Toast
                                                                                         .LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                boolean b = true;
                boolean a = true;
                if(edtusr.getText().toString().trim().isEmpty() && edtpass.getText().toString()
                                                                           .trim().isEmpty()) {
                    b = false;
                    Toast.makeText(getBaseContext(),"Please Enter Your LogIn Account", Toast
                                                                                          .LENGTH_SHORT).show();
                }

                if(edtusr.getText().toString().trim().isEmpty() ^ edtpass.getText().toString()
                                                                           .trim().isEmpty()) {
                    a = false;
                    Toast.makeText(getBaseContext(),"Please Enter Your Username / Password", Toast
                                                                                               .LENGTH_SHORT).show();
                }
                if(edtusr.getText().toString().trim().equals(usr) &&
                           edtpass.getText().toString().trim().equals(psw)) {
                    usrWel = edtusr.getText().toString();
                    islogin = true;
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), hw_challenge.class );
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    Bundle bundle = new Bundle();
                    //Add your data to bundle
                    bundle.putString("username", edtusr.getText().toString());
                    bundle.putString("password", edtpass.getText().toString());
                    //Add the bundle to the intent
                    i.putExtras(bundle);
                    startActivity(i);
                    edtpass.setText("");
                    edtusr.setText("");
                }else {
                    counter--;
                    Toast.makeText(getApplicationContext(), "Wrong Credentials, times left: " + counter,Toast.LENGTH_SHORT).show();
                    if (counter == 0) {
                        edtusr.setEnabled(false);
                        edtpass.setEnabled(false);
                    }
                }
            }
        });
    }

    public boolean isLogin() {
        return islogin;
    }

    public String usrName() {
        return this.usrWel;
    }
}
