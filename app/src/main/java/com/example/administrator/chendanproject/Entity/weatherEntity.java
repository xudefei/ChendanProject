package com.example.administrator.chendanproject.Entity;

/**
 * Created by Administrator on 2017/3/7.
 */

public class weatherEntity {
    /**
     * 城市
     */
   public String city;
    /**
     * 日期
     */
    public String date;
    /**
     * 温度
     */
    public  String temperature;
    /**
     * 风向
     */
    public String direction;
    /**
     * 风力
     */
    public String power;
    /**
     * 天气状况
     */
    public String status;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("城市:" + city + "\r\n");
        builder.append("日期:" + date + "\r\n");
        builder.append("天气状况:" + status + "\r\n");
        builder.append("温度:" + temperature + "\r\n");
        builder.append("风向:" + direction + "\r\n");
        builder.append("风力:" + power + "\r\n");
        return builder.toString();
    }
}
