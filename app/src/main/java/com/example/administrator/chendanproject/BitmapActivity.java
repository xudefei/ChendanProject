package com.example.administrator.chendanproject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chendanproject.Adapter.ViewPagerAdapter;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
@ContentView(R.layout.bitmap_layout)
public class BitmapActivity extends AppCompatActivity {
    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;
    @ViewInject(R.id.line)
    private LinearLayout layout;
    private ViewPagerAdapter adapter;
    private List<ImageView> imageViews = new ArrayList<>();
    private int crypositionindex = 0;//当前指示器的下表
    private String[] url = {
            "http://h5.86.cc/walls/20150417/1440x900_c76178554edac30.jpg",
            "http://pic.58pic.com/58pic/13/87/72/73t58PICjpT_1024.jpg",
            "http://pic27.nipic.com/20130319/11935511_225831392000_2.jpg",
            "http://pic.58pic.com/58pic/14/27/54/10q58PIC2bx_1024.jpg",
            "http://pic.58pic.com/58pic/13/71/30/15r58PIChmX_1024.jpg"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        InitData();
        getBitmap();
        // 开启新线程，2秒一次更新Banner
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    SystemClock.sleep(2000);
                    /**
                     * 提交事件到UI线程中
                     * */
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //在UI线程中执行
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }).start();

    }
    private void InitData(){
        adapter = new ViewPagerAdapter(imageViews);
        viewPager.setAdapter(adapter);
        ViewPagerLister viewPagerLister = new ViewPagerLister();
        viewPager.setOnPageChangeListener(viewPagerLister);

    }

    private void getBitmap(){
        ImageOptions options = new ImageOptions.Builder()
                               .setConfig(Bitmap.Config.RGB_565)//设置图片质量,这个是默认的
                               .setFadeIn(true)
                                .setSquare(true)//设置图片为方形
                                .setUseMemCache(true)
                                .setSize(200,200).build();
        for (int i = 0; i < url.length; i++){
            ImageView imageView = new ImageView(BitmapActivity.this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            x.image().bind(imageView,url[i],options);
            imageViews.add(imageView);

            View view = new View(BitmapActivity.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10,10);
            layoutParams.leftMargin = 10;
            view.setLayoutParams(layoutParams);
            view.setBackgroundResource(R.drawable.point);
            view.setEnabled(false);
            layout.addView(view);
        }
        adapter.notifyDataSetChanged();
    }


    /**
     * ViewPager接口监听类
     * */
    class ViewPagerLister implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        //page被选中
        @Override
        public void onPageSelected(int position) {
            int newPosition = position%imageViews.size();
            layout.getChildAt(newPosition).setEnabled(true);
            layout.getChildAt(crypositionindex).setEnabled(false);
            crypositionindex = newPosition;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}
