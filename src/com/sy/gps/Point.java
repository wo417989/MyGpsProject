package com.sy.gps;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;


public class Point implements Serializable, Parcelable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double x=0;
	private double y=0;
	private String partCode="";
	private String date="";	//上传地址时使用
	private String address="";	//地址描述
	private String ps = "0";	//是否在网格内
	

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public Point(){
		
	}
	
	public Point(double x,double y){
		this.x = x;
		this.y = y;
	}
	
	public Point(double x,double y,Date date,String ps){
		this.x = x;
		this.y = y;
		this.ps = ps;
		setDate(date);
	}
	
	public void setPoint(double x,double y,String partCode,String address){
		this.x = x;
		this.y = y;
		this.partCode = partCode;
		this.address  = address;
//		print.out("setpoint address="+this.address);
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public void setDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = sdf.format(date);
		this.date = s;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	
	
//	public double getX() {
//		DecimalFormat df = new DecimalFormat( "###########.00"); 
//		return Double.parseDouble(df.format(x));
//	}
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}

//	public double getY() {
//		DecimalFormat df = new DecimalFormat( "###########.00"); 
//		return Double.parseDouble(df.format(y));
//	}
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		// TODO Auto-generated method stub
		 parcel.writeDouble(x);
		 parcel.writeDouble(y);
		 parcel.writeString(partCode);
		 parcel.writeString(address);
	}
	
	 public static final Parcelable.Creator<Point> CREATOR = new Creator<Point>() {   
	        public Point createFromParcel(Parcel source) {   
	        	Point point = new Point();   
	            point.x = source.readDouble();   
	            point.y = source.readDouble();  
	            point.partCode = source.readString(); 
	            point.address = source.readString(); 
	            return point;   
	        }   
	        public Point[] newArray(int size) {   
	            return new Point[size];   
	        }   
	 }; 


}
