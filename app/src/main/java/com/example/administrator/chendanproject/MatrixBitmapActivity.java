package com.example.administrator.chendanproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.chendanproject.Utils.CustomDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@ContentView(R.layout.matrix_bitmap_layout)
public class MatrixBitmapActivity extends AppCompatActivity {
    @ViewInject(R.id.select)
    private TextView selectButton;
    @ViewInject(R.id.matrix_type)
    private TextView bitmapTypeButton;
    @ViewInject(R.id.post)
    private TextView postButton;
    @ViewInject(R.id.imageView)
    private ImageView imageViewSrc;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
    @Event(value={R.id.select,R.id.matrix_type,R.id.post},type = View.OnClickListener.class)
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.select://选择照片
                List<String> list = new ArrayList<>();
                list.add("相册");
                list.add("拍照");
                CustomDialog.Builder builder = new CustomDialog.Builder(this).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }).setStrings(list);
                CustomDialog customDialog = builder.creatDialog();
                customDialog.show();

                break;
            case R.id.matrix_type://选择处理照片的类型
                break;
            case R.id.post://提交
                break;
        }
    }
}
