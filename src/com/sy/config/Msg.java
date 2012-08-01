package com.sy.config;

public class Msg
{
	public static final int SERVICE_ERROR=100;
	
	public static final int LOGIN_USRERROR = 1;
	public static final int LOGIN_SERVERERROR = 2;
	public static final int LOGIN_LOCKPANEL = 3;
	public static final int LOGIN_UNLOCKPANEL = 4;
	public static final int LOGIN_RUNLOGINTHREAD = 5;
	public static final int LOGIN_OFFLINELOADERROR = 6;
	public static final int LOGIN_LOADSPINNER = 7;
	public static final int LOGIN_CONTINUELOAD = 8;	//检查完更新继续加载
	public static final int LOGIN_FORCEUPDATE = 9;	//检查完更新继续加载
	public static final int LOGIN_NEEDUPDATE = 10;	//检查完更新继续加载
	public static final int LOGIN_STARTGPS = 11;	//检查完更新继续加载
	
	public static final int MODIFYPWD_SUCCESS = 21;
	public static final int MODIFYPWD_ERROR = 22;
	
	public static final int UPDATE_EVENT_SUCCESS=31;
	public static final int UPDATE_ERROR=32;
	public static final int UPDATE_GETMAP_SUCCESS=33;
	public static final int UPDATE_GETMAPFILE_SUCCESS=34;
	public static final int UPDATE_UNZIPGETMAPFILE_SUCCESS=35;
	public static final int UPDATE_UNZIPGETMAPFILE_ERROR=36;
	
	public static final int REFREASH=100;
	
	public static final int TASK_UPDATE=41;
	
  
}
