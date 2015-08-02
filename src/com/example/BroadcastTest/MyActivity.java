package com.example.BroadcastTest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReciver;
//����IntentFilterʵ������ӽ��ܹ㲥��ֵ
//����NetworikChangeReceiverʵ��������registerReceiver��������ע��
//��NetworikChangeReceiver���ʺϺ�IntentFilterʵ��������ȥ��ʵ�ֶ�ֵΪandroid.netconn.CONNECTIVITY_CHANGE�Ĺ㲥���м���
    //����ʵ�ֶ������ļ���
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReciver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReciver,intentFilter);
    }
    //��̬ע��㲥����������ȡ��ע�����
    //ʹ��onDestroy���������е�unregisterReceiver��������ʵ��
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(networkChangeReciver);
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
