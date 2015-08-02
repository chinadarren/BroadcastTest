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

//����IntentFilterʵ������ӽ��ܹ㲥��ֵ
//����NetworikChangeReceiverʵ��������registerReceiver��������ע��
//��NetworikChangeReceiver���ʺϺ�IntentFilterʵ��������ȥ��ʵ�ֶ�ֵΪandroid.netconn.CONNECTIVITY_CHANGE�Ĺ㲥���м���
//����ʵ�ֶ������ļ���
//����Intent����ʹ�ð�ť����com.example.broadcasttext.MY_BROADCAST�����㲥
//Intent���͹㲥���Դ���һЩ���ݸ��㲥������
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
                //���ͱ��ع㲥
                localBroadcastManager.sendBroadcast(intent1);
//                Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
          //   sendBroadcast(intent);
                //��������㲥
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
    //��̬ע��㲥����������ȡ��ע�����
    //ʹ��onDestroy���������е�unregisterReceiver��������ʵ��
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

    //�����ڲ���
    //��дonReceiver
    //ÿ������״̬�����仯ʱonReceive�����õ�ִ��
    //��ӡToast����
    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context , Intent intent){
            //ͨ��getSystem.Service���������õ�ConnectivityManagerһ��ϵͳ������ĵ�ʵ��
            //getActiveNetworkInfo�������Եõ�NetworkInfo��ʵ��
            //����isAvailable���������ж������Ƿ����
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
