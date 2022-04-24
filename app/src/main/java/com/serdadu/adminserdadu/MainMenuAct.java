package com.serdadu.adminserdadu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainMenuAct extends AppCompatActivity {

    TextView tvorder,tvpulau,tvuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        tvorder=findViewById(R.id.tvorder);
        tvpulau=findViewById(R.id.tvpulau);
        tvuser=findViewById(R.id.tvuser);

        tvorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenuAct.this,OrderAct.class);
                startActivity(i);
            }
        });

        tvpulau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenuAct.this,PulauAct.class);
                startActivity(i);
            }
        });

        tvuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenuAct.this, UserAct.class);
                startActivity(i);
            }
        });
    }
}