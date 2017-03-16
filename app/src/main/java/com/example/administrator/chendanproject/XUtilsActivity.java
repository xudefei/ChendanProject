package com.example.administrator.chendanproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.chendanproject.Entity.User;

import org.xutils.DbManager;
import org.xutils.db.table.DbBase;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
@ContentView(R.layout.xutils_layout)
public class XUtilsActivity extends AppCompatActivity {
    @ViewInject(R.id.viewNot)
    private TextView  NotModule;//注解模块
    @ViewInject(R.id.DBView)
    private TextView DBModule;//数据库模块
    @ViewInject(R.id.NetView)
    private TextView NetModule;//网络模块
    @ViewInject(R.id.BitmapView)
    private TextView BitmapModule;//图片模块
    @ViewInject(R.id.content)
    private TextView content;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

    }
    @Event(value = {R.id.viewNot,R.id.DBView,R.id.NetView,R.id.BitmapView},type = View.OnClickListener.class)
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.viewNot://view注释莫快
                break;
            case R.id.DBView://数据库模块
                Intent intent = new Intent(XUtilsActivity.this,DBActivity.class);
                startActivity(intent);
                break;
            case R.id.NetView://网络模块
                Intent intent1 = new Intent(XUtilsActivity.this,NetActivity.class);
                startActivity(intent1);
                break;
            case R.id.BitmapView://图片处理模块
                Intent intent2 = new Intent(XUtilsActivity.this,BitmapActivity.class);
                startActivity(intent2);
                break;
        }

    }




}
