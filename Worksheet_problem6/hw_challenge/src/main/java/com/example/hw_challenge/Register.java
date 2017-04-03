package com.example.hw_challenge;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by shou on 4/2/2017.
 */

public class Register extends Activity {
    EditText username, fname, lname, password;
    String Username, Firstname, Lastname, Password;
    Button btnSubmit;
    Context ctx = this;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerview);
        username = (EditText) findViewById(R.id.edtUser);
        password = (EditText) findViewById(R.id.edtpsw);
        fname = (EditText) findViewById(R.id.edtfname);
        lname = (EditText) findViewById(R.id.edtlname);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v) {
                register(v);
                Toast.makeText(ctx, "Congratulations!", Toast.LENGTH_SHORT ).show();
            }
        });
    }




    public void register(View view) {
        Username = username.getText().toString();
        Firstname = fname.getText().toString();
        Lastname = lname.getText().toString();
        Password = password.getText().toString();
        ThreadTask threadTask = new ThreadTask();
        threadTask.execute(Username,Password, Firstname, Lastname);
    }


    class ThreadTask extends AsyncTask <String, String, String> {

        @Override protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            String firstname = params[2];
            String lastname = params[3];
            String data = "";
            int tempt;
            try{
                URL url = new URL("http://155.41.120.181/MYCODE/register.php");
                String urlParams = "username="+username+"&password="+password+"&firstname="+firstname+"&lastname="+lastname;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                while((tempt = is.read()) != -1) {
                    data += (char)tempt;
                }
                is.close();
                httpURLConnection.disconnect();
                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override protected void onPostExecute(String s) {
            if (s.equals("")) {
                s = "Data Successfully Saved";
            }
            Toast.makeText(ctx, s, Toast.LENGTH_SHORT ).show();
        }
    }

}
