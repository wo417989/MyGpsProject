package com.sy.config;

import java.util.ArrayList;

import com.sy.gps.CoordTrans;
import com.sy.gps.Point;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.telephony.TelephonyManager;


public class Config {

	
//	public static boolean debug = false;
	public static boolean debug = true;
	
	
	public static boolean offLineLoad = false;	//当前状态;
	public static boolean offLineOpen = true;	//打开离线登陆功能
	public static boolean gpsIsOpen = false;	//打开,关闭
	public static boolean thread_run = true;	//通知所有线程退出,程序马上关闭了
	public static float version = 0f;	//版本号
	public static int windowType=0;		//0为默认大屏幕,1为320*478或者480，
	public static String windowStytle = "0";	//窗口样式,经典,还是最新
	public static String appName="";	//程序名
	public static String packageName="";	//应用程序包名
	public static String statInfo ="";	//获取手机id
	public static String phoneType="";	//手机型号
	public static String appPath = "";	//文件缓存路径
	public static String cityName="扬州市";
	public static final String DATABASE_NAME = "cgt";
	public static int autologin_waiteseconds = 3;	//自动登录等待时间 
	public static String username = "";
	public static String name = "";	//记住用户的名字
	public static String password = "";
	public static String dutyRgnCode="";
	public static String dutyRgnCodes="";
	public static final int webservice_timeout = 5;
	public static Point point = new Point();
	public static Point point4UploadPosition = new Point();
	public static long gps_timeFefresh = 0;	//gps最后一次刷新时间
	public static String missionStat="";	//任务统计结果
	public static int isInArea = 2;	//是否在网格内 0不在网格,1在网格,2未知
	
	public static ArrayList<Point> listPoint = new ArrayList<Point>();	//点
	
	public static void loadConfig(Context context) {
	}
	
	
	
	public static void updateWithNewLocation(Location location) {
//		if(location!=null){
//			CoordTrans ct = new CoordTrans();
//			//减去误差
//			double x_d = 0.159973d;
//			double y_d = 0.185847d;
//			DecimalFormat df = new DecimalFormat("#####.0000"); 
//			double x = (location.getLatitude()-x_d)*100;
//			x = Double.parseDouble(df.format(x));
//			double y = (location.getLongitude()-y_d)*100;
//			y = Double.parseDouble(df.format(y));
//			Point p = ct.getProjectCoord(x, y);
//			p.setX(p.getX()-96.3166d);	//84
////			p.setY(p.getY()-8.5575d);	//28,,-8.5575d
//			if(p.getX()!=Config.point.getX() || p.getY()!=Config.point.getY()){
//				Config.point.setX(p.getX());
//				Config.point.setY(p.getY());
//			}else{
////				print.out("位置没有更新");
//			}
//		}else{
////			print.out("没找到位置");
//		}
		if (location != null) {
			CoordTrans ct = new CoordTrans();
			double x = location.getLatitude();
			double y = location.getLongitude();
			x = gps2df(x);
			y = gps2df(y);
			Point p = ct.getProjectCoord(x, y);
			p.setX(p.getX()-117);
			if (p.getX() != Config.point.getX()|| p.getY() != Config.point.getY()) {
				Config.point.setX(p.getX());
				Config.point.setY(p.getY());
				//单独这只point4UploadPosition,upload取完这个值,会置为0
				Point tmp = new Point();
				tmp.setX(p.getX());
				tmp.setY(p.getY());
				point4UploadPosition = tmp;
				System.gc();
			} else {
				// print.out("位置没有更新");
			}
		} else {
			// print.out("没找到位置");
		}
		
		
	}
	
	//把度转为度分
	private static double gps2df(double data){
		double d,f,m;	//度分秒
		double tmp;
		d = Math.floor(data);
		
		tmp = data -d;	//取小数
		tmp *=60;
		f = Math.floor(tmp);	//取分
		
		m = tmp-f;	//取小数
		
		
//		print.out("d="+d);
//		print.out("f="+f);
//		print.out("m="+m);
		
		data = d*100 +f +m;
		return data;
	}
}
