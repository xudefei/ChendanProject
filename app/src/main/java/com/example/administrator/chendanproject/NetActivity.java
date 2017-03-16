package com.example.administrator.chendanproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/3/8.
 */
@ContentView(R.layout.net_layout)
public class NetActivity extends AppCompatActivity {
    @ViewInject(R.id.get)
    private TextView tv_get;
    @ViewInject(R.id.post)
    private TextView tv_post;
    @ViewInject(R.id.webView)
    private TextView tv_content;
    private String activityName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        activityName = getLocalClassName();
    }
    //被@Event注解标记的方法，必须是private的，返回值必须是void 参数必须是View
    @Event(value = {R.id.get,R.id.post},type = View.OnClickListener.class)
    private void getEvent1(View view){
        RequestParams requestParams = new RequestParams("http://blog.csdn.net/a_zhon/article/details/52817530");
        switch (view.getId()){
            case R.id.get:
                RequestData(HttpMethod.GET,requestParams);
                break;
            case R.id.post:
                RequestData(HttpMethod.POST,requestParams);
                break;
        }

    }

    private void RequestData(HttpMethod requestMethod, RequestParams requestParams){
        x.http().request(requestMethod, requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
             if(!TextUtils.isEmpty(result)){
                 tv_content.setText(result);
             }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(activityName,"onError");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e(activityName,"onCancelled");
            }

            @Override
            public void onFinished() {
                Log.e(activityName,"onFinished");
            }
        });


    }
}
