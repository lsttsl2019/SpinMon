package com.isttis2019.spinmon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void clickStart(View view) {
        //GameActivity로 전환
        Intent intent= new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void clickExit(View view) {
        finish();
    }
}
