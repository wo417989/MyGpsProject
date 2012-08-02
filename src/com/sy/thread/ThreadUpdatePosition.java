package com.sy.thread;

import java.text.DecimalFormat;
import java.util.Date;

import com.sy.config.Config;
import com.sy.gps.Point;


public class ThreadUpdatePosition extends Thread {

	private Point point2 = new Point();
	public int waiteSeconds = 30 * 1000  /100; //30���¼һ������,��10���ϴ�,������Ҫ����30��
	public boolean test  = true;
	
	public void run(){
		
		//��ʼ�ϴ�����
		while(Config.thread_run){
			try {
				//�ж�λ���Ƿ�仯��
//				print.out("�������");
//				if(Config.point.getX()!=0 && Config.point.getY()!=0){
//					//�ж�Ҫ��Ҫ��¼��
//					if(Config.point.getX()!=point2.getX() || Config.point.getY()!=point2.getY()){
//						//�жϵ��Ƿ�Ϊ��,���򲻼�¼
//						Point temp = new Point(Config.point.getX(),Config.point.getY(),new Date());
//						Config.listPoint.add(temp);
//					}
//				}else{
//					print.out("����δ�õ�����");
//				}
				
				String ps = "0";
//				if(Config.isInArea==1){
//					ps ="1";
//				}
				
				
				if(Config.gpsIsOpen){//���gpsû��,�Ͳ��ϴ�������
					Point point4UploadPosition = Config.point4UploadPosition;
					//�ڲ�������,����һ��У��
					if(point4UploadPosition.getX()==0d || point4UploadPosition.getY()==0d){
						ps="0";
					}else{
						//��������
//						int i_ps = Area.checkWG(point4UploadPosition,"");
//						if(i_ps==1){
//							ps="1";
//						}
					}
					Point temp = new Point(point4UploadPosition.getX(),point4UploadPosition.getY(),new Date(),ps);
					//���ֵ�Ŀ����,Ϊ�˱�֤ûgps��ʱ��,Ӧ�ô�0��ȥ
					point4UploadPosition.setX(0);
					point4UploadPosition.setY(0);
					Config.listPoint.add(temp);
				}
				if(Config.listPoint.size()>=10){
					//�����10����,��ô�ϴ�����
					if(check()){
						//����в�Ϊ0������,�ϴ�!
						upload();
					}else{
						//���ȫΪ0,��ôɾ��,����
						Config.listPoint.clear();
					}
				}
				
				//���Կ�ʼ
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
				//���Խ���
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for(int i=0;i<100;i++){
				try {
					sleep(waiteSeconds);	//�ȴ�30��,300*100,ɨ��һ��
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(!Config.thread_run){
					//ϵͳ�˳�,�Ͻ��ϴ�,Ȼ���˳�
					if(check()){
						//����в�Ϊ0������,�ϴ�!
						upload();
					}else{
						//���ȫΪ0,��ôɾ��,����
						Config.listPoint.clear();
					}
					break;
				}
			}
		}
	}
	
	private boolean check(){
		boolean check_ok = false;	//����ǲ��Ƕ���0.�����,�����,�����ϴ�
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
			//���û������,��ִ�в���
			return;
		}
		StringBuffer x =new StringBuffer();
		StringBuffer y = new StringBuffer();;
		StringBuffer d = new StringBuffer();;
		StringBuffer ps =new StringBuffer();;
		DecimalFormat df = new DecimalFormat("#.00");	//ȡ��������,2λС��
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
			//�ϴ�λ�õ���Ҫ����
//			cr = Server.updatePosition(x.toString(),y.toString(),d.toString(),ps.toString());
//			print.out("getErrorCode="+cr.getErrorCode());
//			print.out("getErrorDesc="+cr.getErrorDesc());
//			print.out("getResultStr="+cr.getResultStr());
//			if(cr.getErrorCode().equals("0")){
//				Config.listPoint.clear();	//�ϴ��ɹ���,���
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

