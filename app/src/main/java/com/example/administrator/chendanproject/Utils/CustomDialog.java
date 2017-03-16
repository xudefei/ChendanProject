package com.example.administrator.chendanproject.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.chendanproject.Adapter.ListViewAdapter;
import com.example.administrator.chendanproject.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.、
 * 自定义对话框
 */
@ContentView(R.layout.dialog_base_layout)
public class CustomDialog extends Dialog {
    private View contentView;
    @ViewInject(R.id.frame)
    private FrameLayout frameLayout;


    private CustomDialog(Context context,Builder builder) {
        super(context);
        this.contentView = builder.contentView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void show() {
        super.show();
    }


    /**
      * 参数设置类
      * */
    public static  class Builder{
        private Context context;
        private LayoutInflater layoutInflater;
        private List<String> strings;
        private AdapterView.OnItemClickListener onItemClickListener;

        private View contentView;

        public Builder( Context context){
            this.context = context;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        public Builder setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public Builder setStrings(List<String> strings) {
            this.strings = strings;
            return this;
        }

        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }
         public CustomDialog creatDialog(){
             CustomDialog customDialog = new CustomDialog(context,Builder.this);
           if(contentView == null && strings != null){//加载listView布局
               contentView = layoutInflater.inflate(R.layout.dialog_listview_layout,null);
               ListView listView = (ListView) contentView.findViewById(R.id.listView);
               ListViewAdapter adapter = new ListViewAdapter(layoutInflater,strings);
               listView.setAdapter(adapter);
               listView.setOnItemClickListener(onItemClickListener);
               customDialog.contentView = contentView;
           }else {//加载自定义布局
               customDialog.frameLayout.addView(contentView);
           }
            return customDialog;
         }
    }



}
