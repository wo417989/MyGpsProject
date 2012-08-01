package com.sy.gps;

public class CoordParam {

	public static int coordSysID = 1;
    static double a = 6378137d;
    static double b = 6356752.3142d; 


    public static double  getA()
    {
        return a;
    }
    public static double  getB()
    {
        return b;
    }

    
}
