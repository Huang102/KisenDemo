package com.kisen.sindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
    }

    private void setupView() {
        findViewById(R.id.sin).setOnClickListener(this);
        findViewById(R.id.circle).setOnClickListener(this);
        findViewById(R.id.curve).setOnClickListener(this);
        findViewById(R.id.fibbonacci).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sin:
                startActivity(new Intent(this,SinActivity.class));
                break;
            case R.id.circle:
                startActivity(new Intent(this,CircleActivity.class));
                break;
            case R.id.curve:
                startActivity(new Intent(this,CurveActivity.class));
                break;
            case R.id.fibbonacci:
                startActivity(new Intent(this,FibbonacciActivity.class));
                break;
        }
    }
}
