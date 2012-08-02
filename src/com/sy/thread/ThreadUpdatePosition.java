package com.sy.thread;

import java.text.DecimalFormat;
import java.util.Date;

import com.sy.config.Config;
import com.sy.gps.Point;


public class ThreadUpdatePosition extends Thread {

	private Point point2 = new Point();
	public int waiteSeconds = 30 * 1000  /100; //30秒记录一个坐标,满10个上传,不满就要超过30秒
	public boolean test  = true;
	
	public void run(){
		
		//开始上传坐标
		while(Config.thread_run){
			try {
				//判断位置是否变化了
//				print.out("检测坐标");
//				if(Config.point.getX()!=0 && Config.point.getY()!=0){
//					//判断要不要记录点
//					if(Config.point.getX()!=point2.getX() || Config.point.getY()!=point2.getY()){
//						//判断点是否为空,空则不记录
//						Point temp = new Point(Config.point.getX(),Config.point.getY(),new Date());
//						Config.listPoint.add(temp);
//					}
//				}else{
//					print.out("坐标未得到更新");
//				}
				
				String ps = "0";
//				if(Config.isInArea==1){
//					ps ="1";
//				}
				
				
				if(Config.gpsIsOpen){//如果gps没打开,就不上传空数据
					Point point4UploadPosition = Config.point4UploadPosition;
					//在不再网格,进行一次校验
					if(point4UploadPosition.getX()==0d || point4UploadPosition.getY()==0d){
						ps="0";
					}else{
						//后续更改
//						int i_ps = Area.checkWG(point4UploadPosition,"");
//						if(i_ps==1){
//							ps="1";
//						}
					}
					Point temp = new Point(point4UploadPosition.getX(),point4UploadPosition.getY(),new Date(),ps);
					//区分的目的是,为了保证没gps的时候,应该传0上去
					point4UploadPosition.setX(0);
					point4UploadPosition.setY(0);
					Config.listPoint.add(temp);
				}
				if(Config.listPoint.size()>=10){
					//如果有10个了,那么上传数据
					if(check()){
						//如果有不为0的数据,上传!
						upload();
					}else{
						//如果全为0,那么删除,重来
						Config.listPoint.clear();
					}
				}
				
				//测试开始
//				if(test){
//					Point p = new Point(123.12,345.12,new Date());
//					Config.listPoint.add(p);
//					p = new Point(563.12,145.12,new Date());
//					Config.listPoint.add(p);
//					p = new Point(173.12,245.12,new Date());
//					Config.listPoint.add(p);
//					p = new Point(183.12,545.12,new Date());
//					Config.listPoint.add(p);
//					p = new Point(193.12,645.12,new Date());
//					Config.listPoint.add(p);
//					upload();
//					test= false;
//				}
				//测试结束
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for(int i=0;i<100;i++){
				try {
					sleep(waiteSeconds);	//等待30秒,300*100,扫描一遍
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(!Config.thread_run){
					//系统退出,赶紧上传,然后退出
					if(check()){
						//如果有不为0的数据,上传!
						upload();
					}else{
						//如果全为0,那么删除,重来
						Config.listPoint.clear();
					}
					break;
				}
			}
		}
	}
	
	private boolean check(){
		boolean check_ok = false;	//检查是不是都是0.如果是,则清楚,而不上传
		for(int i=0;i<Config.listPoint.size();i++){
			Point p = Config.listPoint.get(i);
			if(p.getX()!=0 || p.getY()!=0){
				check_ok=true;
				break;
			}
		}
		return check_ok;
	}
	
	private void upload(){
//		print.out("upload is call ");
		if(Config.listPoint.size()==0){
			//如果没有数据,不执行操作
			return;
		}
		StringBuffer x =new StringBuffer();
		StringBuffer y = new StringBuffer();;
		StringBuffer d = new StringBuffer();;
		StringBuffer ps =new StringBuffer();;
		DecimalFormat df = new DecimalFormat("#.00");	//取所有整数,2位小数
		for(int i = 0;i<Config.listPoint.size();i++){
			Point p = Config.listPoint.get(i);
			if(i!=0){
				x.append(",");
				y.append(",");
				d.append(",");
				ps.append(",");
			}
			x.append(df.format(p.getX()));
			y.append(df.format(p.getY()));
			d.append(p.getDate());
			ps.append(p.getPs());
		}
//		CommonResult cr;
		try {
			//上传位置的主要部分
//			cr = Server.updatePosition(x.toString(),y.toString(),d.toString(),ps.toString());
//			print.out("getErrorCode="+cr.getErrorCode());
//			print.out("getErrorDesc="+cr.getErrorDesc());
//			print.out("getResultStr="+cr.getResultStr());
//			if(cr.getErrorCode().equals("0")){
//				Config.listPoint.clear();	//上传成功后,清除
//			}else{
//				print.out("ThreadUpdatePosition getErrorCode="+cr.getErrorCode());
//				print.out("ThreadUpdatePosition getErrorDesc="+cr.getErrorDesc());
//				print.out("ThreadUpdatePosition getResultStr="+cr.getResultStr());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

