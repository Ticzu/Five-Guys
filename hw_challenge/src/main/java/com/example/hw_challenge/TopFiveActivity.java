package com.example.hw_challenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TopFiveActivity extends AppCompatActivity {

    DatabaseReference myRef;
    ArrayList<String> userNames;
    ArrayList<Integer> scores;
    String[] uNames;
    int[] hiscores;
    int count = 4;
    private TextView player1;
    private TextView player2;
    private TextView player3;
    private TextView player4;
    private TextView player5;
    private TextView cplayer;
    private TextView tvScore1;
    private TextView tvScore2;
    private TextView tvScore3;
    private TextView tvScore4;
    private TextView tvScore5;
    private TextView[] players;
    private TextView[] tvScores;
    private Button btBack;
    private String usr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_five);
//        count = 5;
        uNames = new String[5];
        hiscores = new int[5];

        //usrname
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            usr = extras.getString("curr_username");

        }

        player1 = (TextView) findViewById(R.id.player1);
        player2 = (TextView) findViewById(R.id.player2);
        player3 = (TextView) findViewById(R.id.player3);
        player4 = (TextView) findViewById(R.id.player4);
        player5 = (TextView) findViewById(R.id.player5);

        tvScore1 = (TextView) findViewById(R.id.tvScore1);
        tvScore2 = (TextView) findViewById(R.id.tvScore2);
        tvScore3 = (TextView) findViewById(R.id.tvScore3);
        tvScore4 = (TextView) findViewById(R.id.tvScore4);
        tvScore5 = (TextView) findViewById(R.id.tvScore5);
        btBack = (Button) findViewById(R.id.btBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), hw_challenge.class);
//                i.putExtra("curr_username", usr);
//                startActivity(i);
                finish();
            }
        });

        players = new TextView[]{player1, player2, player3, player4, player5};
        tvScores = new TextView[]{tvScore1, tvScore2, tvScore3, tvScore4, tvScore5};
//        userNames = new ArrayList<>();
//        scores = new ArrayList<>();

        myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("Users").orderByChild("hiscore").limitToLast(5).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {


                if (dataSnapshot.child("hiscore").exists()) {
                    uNames[count] = dataSnapshot.getKey();
                    players[count].setText(dataSnapshot.getKey());
                    tvScores[count].setText(dataSnapshot.child("hiscore").getValue().toString());

                    System.out.println(Integer.valueOf(dataSnapshot.child("hiscore").getValue().toString()));
                    hiscores[count] = Integer.valueOf(dataSnapshot.child("hiscore").getValue().toString());
                    System.out.println(hiscores[count]);

                    count--;

                } else {

                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }


            // ...
        });


        System.out.println(uNames.length);
        System.out.println(hiscores[4]);
        System.out.println(count);

        //System.out.println(userNames.size());
        for (int i = 0; i < hiscores.length; i++) {
            System.out.println(hiscores[i]);
        }

        for (int i = 0; i < uNames.length; i++) {
            System.out.println(uNames[i]);
        }
//        for (String each: userNames) System.out.println(each);
//        for (Integer each: scores) System.out.println(each);

    }
}
