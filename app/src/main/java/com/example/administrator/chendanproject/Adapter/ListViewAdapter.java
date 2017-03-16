package com.example.administrator.chendanproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.chendanproject.R;
import com.example.administrator.chendanproject.Utils.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/3/16.
 *   /**
 * ListView适配器
 * */


public class ListViewAdapter extends BaseAdapter {
    private List<String> stringList;
    private LayoutInflater layoutInflater;
    public ListViewAdapter(LayoutInflater layoutInflater, List<String> list){
        this.stringList = list;
        this.layoutInflater = layoutInflater;
    }


    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.listview_iterm_layout,null);
        }
        TextView childView =  BaseViewHolder.get(convertView,R.id.iterm_tv);
        childView.setText(stringList.get(position));
        return convertView;
    }
}
