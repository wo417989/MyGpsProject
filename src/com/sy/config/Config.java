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
	
	
	public static boolean offLineLoad = false;	//��ǰ״̬;
	public static boolean offLineOpen = true;	//�����ߵ�½����
	public static boolean gpsIsOpen = false;	//��,�ر�
	public static boolean thread_run = true;	//֪ͨ�����߳��˳�,�������Ϲر���
	public static float version = 0f;	//�汾��
	public static int windowType=0;		//0ΪĬ�ϴ���Ļ,1Ϊ320*478����480��
	public static String windowStytle = "0";	//������ʽ,����,��������
	public static String appName="";	//������
	public static String packageName="";	//Ӧ�ó������
	public static String statInfo ="";	//��ȡ�ֻ�id
	public static String phoneType="";	//�ֻ��ͺ�
	public static String appPath = "";	//�ļ�����·��
	public static String cityName="������";
	public static final String DATABASE_NAME = "cgt";
	public static int autologin_waiteseconds = 3;	//�Զ���¼�ȴ�ʱ�� 
	public static String username = "";
	public static String name = "";	//��ס�û�������
	public static String password = "";
	public static String dutyRgnCode="";
	public static String dutyRgnCodes="";
	public static final int webservice_timeout = 5;
	public static Point point = new Point();
	public static Point point4UploadPosition = new Point();
	public static long gps_timeFefresh = 0;	//gps���һ��ˢ��ʱ��
	public static String missionStat="";	//����ͳ�ƽ��
	public static int isInArea = 2;	//�Ƿ��������� 0��������,1������,2δ֪
	
	public static ArrayList<Point> listPoint = new ArrayList<Point>();	//��
	
	public static void loadConfig(Context context) {
	}
	
	
	
	public static void updateWithNewLocation(Location location) {
//		if(location!=null){
//			CoordTrans ct = new CoordTrans();
//			//��ȥ���
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
////				print.out("λ��û�и���");
//			}
//		}else{
////			print.out("û�ҵ�λ��");
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
				//������ֻpoint4UploadPosition,uploadȡ�����ֵ,����Ϊ0
				Point tmp = new Point();
				tmp.setX(p.getX());
				tmp.setY(p.getY());
				point4UploadPosition = tmp;
				System.gc();
			} else {
				// print.out("λ��û�и���");
			}
		} else {
			// print.out("û�ҵ�λ��");
		}
		
		
	}
	
	//�Ѷ�תΪ�ȷ�
	private static double gps2df(double data){
		double d,f,m;	//�ȷ���
		double tmp;
		d = Math.floor(data);
		
		tmp = data -d;	//ȡС��
		tmp *=60;
		f = Math.floor(tmp);	//ȡ��
		
		m = tmp-f;	//ȡС��
		
		
//		print.out("d="+d);
//		print.out("f="+f);
//		print.out("m="+m);
		
		data = d*100 +f +m;
		return data;
	}
}
