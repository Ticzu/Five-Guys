package com.example.hw_challenge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Register extends Activity {
    EditText username, fname, lname, password;
    String Username, Firstname, Lastname, Password;
    Button btnSubmit, btnBack;
    DatabaseReference myRef;
    Context ctx = this;
    boolean exist = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerview);
        username = (EditText) findViewById(R.id.edtUser);
        password = (EditText) findViewById(R.id.edtpsw);
        fname = (EditText) findViewById(R.id.edtfname);
        lname = (EditText) findViewById(R.id.edtlname);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnBack = (Button)findViewById(R.id.btnBack);

        myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("Users").child("user4").child("password").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("sucess", "Value is: " + value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("failure", "Failed to read value.", error.toException());
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(), simplelogin.class);
                startActivity(i);
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = username.getText().toString();
                Firstname = fname.getText().toString();
                Lastname = lname.getText().toString();
                Password = password.getText().toString();
                ThreadTask threadTask = new ThreadTask();
                System.out.println(Username);
                if (Username.equals("") || Firstname.equals("") || Lastname.equals("") || Password.equals("")) {
                    Toast.makeText(ctx, "You must fill in all the blanks", Toast.LENGTH_SHORT).show();
                   // Intent i = new Intent(getApplicationContext(), Register.class);
                    //startActivity(i);
                } else {
                    threadTask.execute(Username, Password, Firstname, Lastname);
                    //Toast.makeText(ctx, "Congratulations!", Toast.LENGTH_SHORT).show();

//                        Intent i = new Intent(getApplicationContext(), simplelogin.class);
//                        startActivity(i);

                }
            }
        });
    }


    class ThreadTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            final String username = params[0];
            final String password = params[1];
            final String firstname = params[2];
            final String lastname = params[3];
            //fianl String data = "";
            int tempt;

            myRef.child("Users").child(username).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if (dataSnapshot.exists()) {
                        Context context = getApplicationContext();
                        Toast.makeText(context, "user name already exist", Toast.LENGTH_SHORT).show();
                        }else{
                        DatabaseReference User = myRef.child("Users").child(username);
                        User.child("password").setValue(password);
                        User.child("firstname").setValue(firstname);
                        User.child("lastname").setValue(lastname);
                        Intent i = new Intent(getApplicationContext(), simplelogin.class);
                        startActivity(i);
                    }
                    }
                @Override
                public void onCancelled(DatabaseError e){
                    Context context = getApplicationContext();
                    Toast.makeText(context, "connection fail", Toast.LENGTH_SHORT).show();
                }
                });

//            try{
//                URL url = new URL("http://155.41.92.23/MYCODE/register.php");
//                String urlParams = "username="+username+"&password="+password+"&firstname="+firstname+"&lastname="+lastname;
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true);
//                OutputStream os = httpURLConnection.getOutputStream();
//                os.write(urlParams.getBytes());
//                os.flush();
//                os.close();
//                InputStream is = httpURLConnection.getInputStream();
//                while((tempt = is.read()) != -1) {
//                    data += (char)tempt;
//                }
//                is.close();
//                httpURLConnection.disconnect();
//                return data;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                return e.getMessage();
//            } catch (IOException e) {
//                e.printStackTrace();
//                return e.getMessage();
//            }
            //   }
            return "haha";
        }

    }

}