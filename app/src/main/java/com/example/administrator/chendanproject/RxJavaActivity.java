package com.example.administrator.chendanproject;

import android.database.Observable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.chendanproject.Entity.weatherEntity;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/3/7.
 */

public class RxJavaActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_submit;
    private TextView search_content;
    private EditText editText;

    private static final String WEATHRE_API_URL="http://php.weather.sina.com.cn/xml.php?city=%s&password=DJOYnieT8234jlsK&day=0";
    private String ActivityName;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    weatherEntity entity = analysisXml((String) msg.obj);
                    search_content.setText(entity.toString());
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxjava_layout);
        ActivityName =  getLocalClassName();
        InitView();

    }
    private void InitView(){
        tv_submit = (TextView)findViewById(R.id.submit);
        search_content = (TextView)findViewById(R.id.search_content);
        editText = (EditText)findViewById(R.id.et);
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit:
                String str = editText.getText().toString().trim();
                if(!TextUtils.isEmpty(str)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String weatherXml = getWeather(editText.getText().toString().trim());
                            Message message = new Message();
                            message.what = 1;
                            message.obj = weatherXml;
                            handler.sendMessage(message);
                        }
                    }).start();
                }


                break;
        }

    }
    /**
     * 得到城市的天气情况
     * */
    private String getWeather(String city){
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        try {
            String urlString = String.format(WEATHRE_API_URL, URLEncoder.encode(city, "GBK"));
            URL url = new URL(urlString);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            //连接
            connection.connect();

            //处理返回结果
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));

            String line = "";
            while (!TextUtils.isEmpty(reader.readLine())){
                buffer.append(reader.readLine());
            }
        } catch (Exception e) {
            Log.e(ActivityName,""+e.toString());
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection.disconnect();
            }
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return buffer.toString();
    }


    private weatherEntity analysisXml(String XmlString){
        //采用Pull方式解析xml
        StringReader reader = new StringReader(XmlString);
        XmlPullParser xmlParser = Xml.newPullParser();
        weatherEntity weather = null;
        try {
            xmlParser.setInput(reader);
            int eventType = xmlParser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        weather = new weatherEntity();
                        break;
                    case XmlPullParser.START_TAG:
                        String nodeName = xmlParser.getName();
                        if("city".equals(nodeName)){
                            weather.city = xmlParser.nextText();
                        } else if("savedate_weather".equals(nodeName)){
                            weather.date = xmlParser.nextText();
                        } else if("temperature1".equals(nodeName)) {
                            weather.temperature = xmlParser.nextText();
                        } else if("temperature2".equals(nodeName)){
                            weather.temperature += "-" + xmlParser.nextText();
                        } else if("direction1".equals(nodeName)){
                            weather.direction = xmlParser.nextText();
                        } else if("power1".equals(nodeName)){
                            weather.power = xmlParser.nextText();
                        } else if("status1".equals(nodeName)){
                            weather.status = xmlParser.nextText();
                        }
                        break;
                }
                eventType = xmlParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weather;
    }

    /**
     * 使用RxJava框架,采用普通写法创建Observable(被观察者)
     * 搞不懂啊
     * */
    private void observableAsNormal(String city){


    }

}
