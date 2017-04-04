package com.example.hw_challenge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class simplelogin
        extends AppCompatActivity {

    private Button btnNext;
    private Button btnRegister;
    private TextView tv1;
    private TextView tv2;
    private EditText edtusr;
    private EditText edtpass;
    private int counter = 10;
    private String usr = "cshou";
    private String psw = "1234";
    public String usrname;
    public boolean islogin = false;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simplelogin);
        myRef = FirebaseDatabase.getInstance().getReference();

        btnNext = (Button)findViewById(R.id.btnClicktoSubmit);
        btnRegister = (Button) findViewById(R.id.btnClicktoRegister);
        edtusr = (EditText)findViewById(R.id.edtUser);
        edtpass = (EditText)findViewById(R.id.edtpsw);
        tv1 = (TextView)findViewById(R.id.tvUser);
        tv2 = (TextView)findViewById(R.id.tvpsw);
        if(islogin){
            login();
        }
        Toast toast = Toast.makeText(getApplicationContext(),"Please login first",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                boolean b = true;
                boolean a = true;
                usr = edtusr.getText().toString();
                final String pass = edtpass.getText().toString();

                if(usr.equals("") && pass.equals("")) {
                    b = false;
                    Toast.makeText(getBaseContext(),"Please Enter Your LogIn Account", Toast.LENGTH_SHORT).show();
                }
                if(usr.equals("") ||pass.equals("")) {
                    a = false;
                    Toast.makeText(getBaseContext(),"Please Enter Your Username / Password", Toast.LENGTH_SHORT).show();
                }




                    myRef.child("Users").child(usr).child("password").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            if (dataSnapshot.exists()) {
                                String value = dataSnapshot.getValue(String.class);
                                Log.i("sda", value);
                                if (pass.equals(value)) {
                                    islogin = true;
                                    usrname = usr;
                                    login();
                                }else{
                                    Context context = getApplicationContext();
                                    Toast.makeText(context, "wrong username/password", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Context context = getApplicationContext();
                                Toast.makeText(context, "wrong username/password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Context context = getApplicationContext();
                            Toast.makeText(context, "connection fail", Toast.LENGTH_SHORT).show();
                            Log.w("failure", "Failed to read value.", error.toException());
                        }
                    });

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                Toast.makeText(getApplicationContext(),
                        "Redirecting...",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Register.class );
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Bundle bundle = new Bundle();
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

    private void login(){
        Intent i = new Intent(getApplicationContext(), hw_challenge.class);
        i.putExtra("curr_username", usr);
        startActivity(i);

    }

    public boolean isLogin() {
        return islogin;
    }

    public String usrName() {
        return this.usr;
    }
}
