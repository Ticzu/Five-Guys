package com.example.hw_challenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TopFiveActivity extends AppCompatActivity {
    private TextView player1;
    private TextView player2;
    private TextView player3;
    private TextView player4;
    private TextView player5;
    private TextView score1;
    private TextView score2;
    private TextView score3;
    private TextView score4;
    private TextView score5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_five);
        
    }
}
