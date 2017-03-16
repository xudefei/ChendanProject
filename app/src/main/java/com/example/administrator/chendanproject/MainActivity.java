package com.example.administrator.chendanproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.chendanproject.PayUtils.PayDemoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_rxJava;
    private TextView tv_Xutils;
    private TextView payDemo;
    private TextView matrixBimap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitView();
    }
    private void InitView(){
        tv_rxJava = (TextView)findViewById(R.id.rxjava);
        tv_Xutils = (TextView)findViewById(R.id.Xutils);
        payDemo = (TextView)findViewById(R.id.PayDemo);
        matrixBimap = (TextView)findViewById(R.id.matrix);

        tv_rxJava.setOnClickListener(this);
        tv_Xutils.setOnClickListener(this);
        payDemo.setOnClickListener(this);
        matrixBimap.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rxjava:
                Intent intent = new Intent(MainActivity.this,RxJavaActivity.class);
                startActivity(intent);
                break;
            case R.id.Xutils:
                Intent intent1 = new Intent(MainActivity.this,XUtilsActivity.class);
                startActivity(intent1);
                break;
            case R.id.PayDemo:
                Intent intent2 = new Intent(MainActivity.this, PayDemoActivity.class);
                startActivity(intent2);
                break;
            case R.id.matrix:
                Intent intent3 = new Intent(MainActivity.this,MatrixBitmapActivity.class);
                startActivity(intent3);
                break;

        }
    }
}
