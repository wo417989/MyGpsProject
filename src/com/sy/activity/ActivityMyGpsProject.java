package com.sy.activity;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;

import com.sy.config.Config;
import com.sy.config.Msg;

public class ActivityMyGpsProject extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
      //���Gps
		if(!checkGPS()){
//			autoLogin = false;
			showGPSConfig();
		}
		
		//����gps
		gpsThread.start();
    }
    
    Thread gpsThread = new Thread(){
		public void run(){
			while(!checkGPS() && Config.thread_run){
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(!Config.thread_run){	//����ر�
				return;
			}
			
			if(checkGPS()){
				Config.gpsIsOpen = true;
				mHandler.sendMessageDelayed(mHandler.obtainMessage(Msg.LOGIN_STARTGPS,0), 0);
			}
		}
	};
	
	
    
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Config.thread_run = false;
	}

	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
//			case Msg.LOGIN_USRERROR:
//				if(result.getErrorCode().equals("1")){
//					showError("�û��������벻��ȷ!");
//				}else if(result.getErrorCode().equals("2")){
//					showError("�û��������벻��ȷ!");
//				}else if(result.getErrorCode().equals("-1")){
//					showError("�û���������!");
//				}else{
//					showError("�������:"+result.getErrorCode()+"\n��������:"+result.getErrorDesc());
//				}
//				break;
//			case Msg.LOGIN_SERVERERROR:
//				if(Config.offLineOpen){	//������ߵ�½���ܴ���
//					showError2("�������޷�����!��½ʧ��,�Ƿ����ߵ�½?");
//				}else{
//					showError("�������޷�����!");
//				}
//				break;
//			case Msg.LOGIN_LOCKPANEL:
//				setEnable(false);
//				break;
//			case Msg.LOGIN_UNLOCKPANEL:
//				setEnable(true);
//				break;
//			case Msg.LOGIN_RUNLOGINTHREAD:
//				LoginThread = new MyThread();	//��½
//				LoginThread.showProcess();
//				break;
//			case Msg.LOGIN_LOADSPINNER:
//				loadSpinner();
//				break;
//			case Msg.LOGIN_OFFLINELOADERROR:	//���ߵ�½ʧ��
//				showError("����������֤ʧ��,���ߵ�½ʧ��!");
//				//�ر����ߵ�½
//				Config.offLineLoad = false;
//				break;
//			case Msg.LOGIN_CONTINUELOAD:
//				autoLogin(); // �ж��Ƿ��Զ���¼,����,ִ��
//				break;
//			case Msg.LOGIN_FORCEUPDATE:
//				autoLogin=false;	//�ر��Զ�����
//				forceUpdate(); 
//				break;
//			case Msg.LOGIN_NEEDUPDATE:
//				autoLogin=false;//�ر��Զ�����
//				normalUpdate(); 
//				break;
			case Msg.LOGIN_STARTGPS:
				startGPS();
				break;
			}
		}
	};
	
	@SuppressWarnings("static-access")
	public void startGPS(){
//		print.out("login startGps is call");
		//��������
		LocationListener locationListener = new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				if(provider.equals("gps")){
					Config.gps_timeFefresh = (new Date()).getTime();
				}
			}
			@Override
			public void onProviderEnabled(String provider) {
				Config.gpsIsOpen = true;
			}
			@Override
			public void onProviderDisabled(String provider) {
				Config.gpsIsOpen = false;
			}
			//��λ�ñ仯ʱ����
			@Override
			public void onLocationChanged(Location location) {
				//ʹ���µ�location����TextView��ʾ
//				print.out("����="+location.getLongitude());
//				print.out("γ��="+location.getLatitude());
				Config.updateWithNewLocation(location);
			}
		};
		
        LocationManager loctionManager;
        String contextService=Context.LOCATION_SERVICE;
        loctionManager=(LocationManager) getSystemService(contextService);
        String provider2 = loctionManager.GPS_PROVIDER;
        //����λ�ñ仯��3��һ�Σ�����10������
        loctionManager.requestLocationUpdates(provider2, 3000, 10, locationListener);
	}
    
    private boolean checkGPS(){
		String state = Settings.System.getString(getContentResolver(), Settings.System.LOCATION_PROVIDERS_ALLOWED);
		if(state.indexOf("gps")!=-1){
			return true;
		}
		return false;
	}
	
	private void showGPSConfig() {
		Builder b = new AlertDialog.Builder(this).setTitle("GPS��λ!")
				.setMessage("�뿪��GPS��λ����!");
		b.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
//				Intent mIntent = new Intent("/");
//				ComponentName comp = new ComponentName("com.android.settings",
//						"com.android.settings.SecuritySettings");
//				mIntent.setComponent(comp);
//				mIntent.setAction("android.intent.action.VIEW");
//				startActivity(mIntent);
//				Intent myIntent = new Intent( Settings.ACTION_SECURITY_SETTINGS );
				Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(myIntent);
			}
		}).setNeutralButton("ȡ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		}).create();
		b.show();
	}
}