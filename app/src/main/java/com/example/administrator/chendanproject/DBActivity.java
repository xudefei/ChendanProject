package com.example.administrator.chendanproject;

import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.chendanproject.Entity.User;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
@ContentView(R.layout.db_layout)
public class DBActivity extends AppCompatActivity {
   @ViewInject(R.id.add)
    private TextView tv_add;

    @ViewInject(R.id.delect)
    private TextView tv_delect;

    @ViewInject(R.id.modify)
    private TextView tv_modify;

    @ViewInject(R.id.query)
    private TextView tv_query;

   @ViewInject(R.id.content)
    private TextView content;

    private   DbManager dbManager;

    private String activityName ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        activityName = getLocalClassName();
        Log.e(activityName,"+++onCreate");
        getDB();
    }
    @Event(value = {R.id.add,R.id.delect,R.id.modify,R.id.query},type = View.OnClickListener.class)
    private void  getEvent(View view){
      switch (view.getId()){
          case R.id.add:
              List<User> userList = new ArrayList<>();
              for (int i = 0;i < 20;i++){
                  User user = new User(i,"女"+i,"陈丹"+i,"123456");
                  userList.add(user);
              }
              try {
                  dbManager.save(userList);
                  List<User> list = dbManager.findAll(User.class);
                  StringBuffer stringBuffer = new StringBuffer();
                  for (User user:list){
                      stringBuffer.append(user.toString()+"\n");
                  }
                  content.setText(stringBuffer.toString());
              } catch (DbException e) {
                  e.printStackTrace();
              }
              break;
          case R.id.delect:
              /**
               * 自增的ID你删除了之后Id也会比以前的大
               *
               * */
              WhereBuilder whereBuilder =  WhereBuilder.b();//添加条件
              whereBuilder.and("id",">",5);
              try {
                  dbManager.delete(User.class,whereBuilder);
                  List<User> list = dbManager.findAll(User.class);
                  StringBuffer stringBuffer = new StringBuffer();
                  for (User user:list){
                      stringBuffer.append(user.toString()+"\n");
                  }
                  content.setText(stringBuffer.toString());

              } catch (DbException e) {
                  e.printStackTrace();
              }

              break;
          case R.id.modify:
              try {
                  User userFirst = dbManager.findFirst(User.class);
                  WhereBuilder whereBuilder1 = WhereBuilder.b();//添加条件
                  whereBuilder1.and("id","=",userFirst.getId());
                  KeyValue name = new KeyValue("name","张三");
                  KeyValue phone = new KeyValue("phone","15208173940");
                  dbManager.update(User.class,whereBuilder1,name,phone);
                  User user = dbManager.findFirst(User.class);
                  content.setText(user.toString());

              } catch (DbException e) {
                  e.printStackTrace();
              }
              break;
          case R.id.query:
              break;
      }
    }


    /**
     * 得到数据库
     * */
    private void getDB() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("chendan.db")//数据库的名称
                .setTableCreateListener(new DbManager.TableCreateListener() {//数据库中创建表的监听
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table) {
                        Log.e(activityName,"数据库中创建该表"+table.toString());
                    }
                }).setAllowTransaction(true)//设置是否允许事务，默认是true
                .setDbDir(new File(Environment.getExternalStorageDirectory() + "/zsy"))
                .setDbOpenListener(new DbManager.DbOpenListener() {//设置数据库打开的监听
                    @Override
                    public void onDbOpened(DbManager db) {
                        Log.e(activityName,"数据库打开");
                        db.getDatabase().enableWriteAheadLogging();//开启多线程操作
                    }
                }).setDbVersion(1);
        dbManager = x.getDb(daoConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            dbManager.dropTable(User.class);//从数据库中删除表User；
            dbManager.close();//关闭数据库
        } catch (DbException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}