package com.zd.rxtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zd.rxtest.rxjava.TestRxJavaActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testRxJava(View view) {
        Intent intent = new Intent(this, TestRxJavaActivity.class);
        startActivity(intent);
    }
}
