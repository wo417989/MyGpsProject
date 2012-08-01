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
        
      //检查Gps
		if(!checkGPS()){
//			autoLogin = false;
			showGPSConfig();
		}
		
		//启动gps
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
			
			if(!Config.thread_run){	//程序关闭
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
//					showError("用户名或密码不正确!");
//				}else if(result.getErrorCode().equals("2")){
//					showError("用户名或密码不正确!");
//				}else if(result.getErrorCode().equals("-1")){
//					showError("用户名不存在!");
//				}else{
//					showError("错误代码:"+result.getErrorCode()+"\n错误描述:"+result.getErrorDesc());
//				}
//				break;
//			case Msg.LOGIN_SERVERERROR:
//				if(Config.offLineOpen){	//如果离线登陆功能打开了
//					showError2("服务器无法连接!登陆失败,是否离线登陆?");
//				}else{
//					showError("服务器无法连接!");
//				}
//				break;
//			case Msg.LOGIN_LOCKPANEL:
//				setEnable(false);
//				break;
//			case Msg.LOGIN_UNLOCKPANEL:
//				setEnable(true);
//				break;
//			case Msg.LOGIN_RUNLOGINTHREAD:
//				LoginThread = new MyThread();	//登陆
//				LoginThread.showProcess();
//				break;
//			case Msg.LOGIN_LOADSPINNER:
//				loadSpinner();
//				break;
//			case Msg.LOGIN_OFFLINELOADERROR:	//离线登陆失败
//				showError("本地密码验证失败,离线登陆失败!");
//				//关闭离线登陆
//				Config.offLineLoad = false;
//				break;
//			case Msg.LOGIN_CONTINUELOAD:
//				autoLogin(); // 判断是否自动登录,如是,执行
//				break;
//			case Msg.LOGIN_FORCEUPDATE:
//				autoLogin=false;	//关闭自动升级
//				forceUpdate(); 
//				break;
//			case Msg.LOGIN_NEEDUPDATE:
//				autoLogin=false;//关闭自动升级
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
		//创建监听
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
			//当位置变化时触发
			@Override
			public void onLocationChanged(Location location) {
				//使用新的location更新TextView显示
//				print.out("经度="+location.getLongitude());
//				print.out("纬度="+location.getLatitude());
				Config.updateWithNewLocation(location);
			}
		};
		
        LocationManager loctionManager;
        String contextService=Context.LOCATION_SERVICE;
        loctionManager=(LocationManager) getSystemService(contextService);
        String provider2 = loctionManager.GPS_PROVIDER;
        //监听位置变化，3秒一次，距离10米以上
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
		Builder b = new AlertDialog.Builder(this).setTitle("GPS定位!")
				.setMessage("请开启GPS定位功能!");
		b.setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
		}).setNeutralButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		}).create();
		b.show();
	}
}