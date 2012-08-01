package com.sy.gps;


public  class CoordTrans {

	private static int k0 = 1;

    private double L = 0;  //经度°′″形式
    private double B = 0;  //纬度°′″形式
    private double a = 0;  //长轴
    private double b = 0;  //短轴
    private double L0 = 0; //中央经度
//    private int coordSysID = 1;//坐标系：0:北京54采用;1:西安80; 2:WGS 84
    //double[] coord = new double[2];
    double coordx;
    double coordy;

    public CoordTrans()
    {
    }
    public double GetX()
    {
        return coordx;
    }
    public double GetY()
    {
        return coordy;
    }

    /**
     * 把度分秒转化为度
     * @param dfm double
     * @return double
     */
    private double transDFMtoDegree1(double dfm)
    {
        double d = Math.floor(dfm);
        double f = 1.0 * (Math.floor(dfm * 100) - d * 100) * 1.0 / 60.0;
        double m = (dfm * 10000 - Math.floor(dfm * 100) * 100.0) / 3600.0;
        double value = d + f + m;
        return value;
    }
    /**
     * 把度分转化为度
     * @param dfm double
     * @return double
     */
    private double transDFMtoDegree(double dfm)
    {
        //double d = Math.floor(dfm);
        //double f = 1.0 * (Math.floor(dfm * 100) - d * 100) * 1.0 / 60.0;
        //double m = (dfm * 10000 - Math.floor(dfm * 100) * 100.0) / 3600.0;
        double d = Math.floor(dfm / 100);
        //double f = (dfm - d * 100) * 60 / 100 / 100;
        double f = (dfm - d * 100) / 60;

        double value = d + f;
        return value;
    }


    private double getRad(double d)
    {
        return d / 180 * Math.PI;
    }

    /**
     * 获得扁率 (a-b)/a
     * @return double
     */
    private double getFlattening()
    {
        return (a - b) / a;
    }

    /**
     * 获得第一偏心率 对（1-(b/a)*(b/a)）求根
     * @return double
     */
    private double getEccentricity()
    {
        return Math.sqrt(1 - Math.pow(b / a, 2));
    }

    /**
     * 获得第二偏心率 对((a/b)*(a/b)-1)求根
     * @param coordSysID int
     * @return double
     */
    private double getSEccentricity()
    {
        double temp = a * 1.0 / b;
        //System.out.println("getSEccentricity  a/b = " + temp);
        //System.out.println("e' = " + Math.sqrt(temp * temp - 1));
        return Math.sqrt(temp * temp - 1);
    }

    /**
     * 获得卯酉圈曲率半径 a/对(1-(e*e) * (sinB*sinB))求根
     * @param coordSysID int
     * @param B double
     * @return double
     */
    private double getN()
    {
        double e = getEccentricity();
        return a / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(B), 2));
    }

    /**
     * tgB * tgB
     * @param coordSysID int
     * @param B double
     * @return double
     */
    private double getT()
    {
        return Math.pow(Math.tan(B), 2);
    }

    private double getC()
    {
        double se = getSEccentricity();
        //System.out.println("\tse*se = " + se * se);
        return Math.pow(se, 2) * Math.pow(Math.cos(B), 2);
    }

    private double getA()
    {
        return (L - L0) * Math.cos(B);
    }

    private double getM()
    {
        double e = getEccentricity();
        double temp1 = (1 - Math.pow(e, 2) / 4
                        - 3 * Math.pow(e, 4) / 64
                        - 5 * Math.pow(e, 6) / 256);

        //System.out.println("\tpart1的系数："  + temp1);
        double part1 = (1 - Math.pow(e, 2) / 4
                        - 3 * Math.pow(e, 4) / 64
                        - 5 * Math.pow(e, 6) / 256) * B;
        double part2 = (3 * Math.pow(e, 2) / 8
                        + 3 * Math.pow(e, 4) / 32
                        + 45 * Math.pow(e, 6) / 1024) * Math.sin(2 * B);
        double part3 = (15 * Math.pow(e, 4) / 256
                        + 45 * Math.pow(e, 6) / 1024) * Math.sin(4 * B);
        double part4 = (35 * Math.pow(e, 6) / 3072) * Math.sin(6 * B);
        //System.out.println("\t 计算M part1 = " + part1 + ";part2 = " + part2 + ";part3 = " + part3 + "; part4 = " + part4);
        return a * (part1 - part2 + part3 - part4);
    }

    public Point getProjectCoord(double bValue , double lValue)
    {
        if (bValue == 0 || bValue == 0.0)
        {
            coordx = 0.0;
            coordy = 0.0;
        }
        else
        {
//            coordSysID = CoordParam.coordSysID;
            L0 = 120.0 / 180 * Math.PI;
            a = CoordParam.getA();
            b = CoordParam.getB();
            double l1 = transDFMtoDegree(lValue);
            double b1 = transDFMtoDegree(bValue);
            //System.out.println("\t原始经纬度值是： l = " + l + "; b = " + b);
            //System.out.println("\t度分秒转化为度：l = " + l1 + "; b = " + b1);
            double l2 = getRad(l1);
            double b2 = getRad(b1);
            L = l2;
            B = b2;

            double M = getM();
            //System.out.println("\tM = " + M);
            double A = getA();
            //System.out.println("\tA = " + A);
            double N = getN();
            //System.out.println("\tN = " + N);
            double T = getT();
            //System.out.println("\tT = " + T);

            double C = getC();
            //System.out.println("\tC = " + C);
            //500000米 + 40 * 1000000
            double FE = 500000;
            //System.out.println("\tFE = " + FE);
            double part1 = M;
            double temp1 = Math.pow(A, 2) / 2
                + (5 - T + 9 * C + 4 * Math.pow(C, 2)) * Math.pow(A, 4) / 24;
            //System.out.println("\ttemp1 = " +temp1);
            double part2 = N * Math.tan(B) * (Math.pow(A, 2) / 2
                                              +
                                              (5 - T + 9 * C + 4 * Math.pow(C, 2)) *
                                              Math.pow(A, 4) / 24);
            double part3 = (61 - 58 * T + Math.pow(T, 2) + 270 * C - 330 * T * C) *
                Math.pow(A, 6) / 720;
            //System.out.println("\tpart1 = " + part1);
            //System.out.println("\tpart2 = " + part2);
            //System.out.println("\tpart3 = " + part3);
            double x = part1 + part2 + part3;
            part1 = FE;
            part2 = A;
            part3 = (1 - T + C) * Math.pow(A, 3) / 6;
            double part4 = (5 - 18 * T - Math.pow(T, 2) + 14 * C - 58 * T * C) *
                Math.pow(A, 5) / 120;
            double y = part1 + k0 * N * (part2 + part3 + part4);
            //double[] coord = new double[2];
            coordx = y;//--------有问题
            coordy = x;
            //System.out.println("\tx = " + x);
            //System.out.println("\ty = " + y);
            //Console.WriteLine(x.ToString());
            //Console.WriteLine(y.ToString());
            //return coord;

            //if (Globals.LOGDEBUG)
            //{
            //    try
            //    {
            //        StreamWriter sw = File.AppendText(Globals.AppPath + "\\gps.get2.txt");
            //        sw.WriteLine(DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            //        sw.WriteLine(" GPSCoord:X:" + bValue + "; Y:" + lValue);
            //        sw.WriteLine(" Trans2Degree:X:" + b1 + "; Y:" + l1);
            //        sw.WriteLine(" MapCoord:X:" + coordx + "; Y:" + coordy);
            //        sw.Close();
            //    }
            //    catch (Exception)
            //    {
            //        //
            //    }
            //}
        }
        return new Point(coordx,coordy);
    }
}
