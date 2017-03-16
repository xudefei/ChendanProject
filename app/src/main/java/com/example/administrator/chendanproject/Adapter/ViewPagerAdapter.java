package com.example.administrator.chendanproject.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9.
 * 实现无限轮番的广告图片
 */

public class ViewPagerAdapter extends PagerAdapter {
    private List<ImageView> list;
    public ViewPagerAdapter( List<ImageView> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        //取超大的数，实现无线循环效果
        return Integer.MAX_VALUE;
    }
     //判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
   //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position%list.size()));
        return list.get(position%list.size());
    }
   //这个方法，是从ViewGroup中移出当前View
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView(list.get(position%list.size()));
    }
}
