package com.example.BroadcastTest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//创建IntentFilter实例，添加接受广播的值
//创建NetworikChangeReceiver实例，调用registerReceiver方法进行注册
//将NetworikChangeReceiver的适合和IntentFilter实例都传进去，实现对值为android.netconn.CONNECTIVITY_CHANGE的广播进行监听
//最终实现对网络变的监听
//构建Intent对象使用按钮发送com.example.broadcasttext.MY_BROADCAST这条广播
//Intent发送广播可以传递一些数据给广播接收器
public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReciver;

    private LocalReceiver localReceiver;

    private LocalBroadcastManager localBroadcastManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
                //发送本地广播
                localBroadcastManager.sendBroadcast(intent1);
//                Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
          //   sendBroadcast(intent);
                //发送有序广播
//                sendOrderedBroadcast(intent,null);
            }
        });

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttext.LOCAL_BROADCAST");
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
         localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(loaclReceiver,intentFilter);
//        networkChangeReciver = new NetworkChangeReceiver();
//        registerReceiver(networkChangeReciver,intentFilter);
    }
    //动态注册广播接收器必须取消注册才行
    //使用onDestroy（）方法中的unregisterReceiver（）方法实现
    @Override
    protected void onDestroy(){
        super.onDestroy();
  localBroadcastManager.unregisterReceiver(localReceiver);
//     unregisterReceiver(networkChangeReciver);
    }
    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent){
            Toast.makeText(context,"received local broadcast",Toast.LENGTH_SHORT).show();
        }
    }

    //定义内部类
    //重写onReceiver
    //每当网络状态发生变化时onReceive（）得到执行
    //打印Toast内容
    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context , Intent intent){
            //通过getSystem.Service（）方法得到ConnectivityManager一个系统服务类的的实例
            //getActiveNetworkInfo（）可以得到NetworkInfo的实例
            //调用isAvailable（）方法判断网络是否可用
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable()){
                Toast.makeText(context,"network is availeable",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context,"network is unavailable",Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(context,"network changes",Toast.LENGTH_SHORT).show();
        }
    }
}
