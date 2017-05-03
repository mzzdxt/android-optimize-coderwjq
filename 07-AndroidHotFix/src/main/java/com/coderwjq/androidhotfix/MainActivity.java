package com.coderwjq.androidhotfix;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // apk的安装位置：/data/app/packagename~1/base.apk

    private Button mBtnTest;
    private Button mBtnFix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnTest = (Button) findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testBug();
            }
        });
    }

    private void testBug() {
        Log.d(TAG, "testBug() called");
        int a = 10;
        int b = 0;
        Toast.makeText(MainActivity.this, "result" + a / b, Toast.LENGTH_SHORT).show();
    }
}
