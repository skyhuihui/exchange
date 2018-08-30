package com.zag.core.util;

/*
 * 计算距离
 */
@SuppressWarnings("unused")
public class DistanceCalc {
	private static double PI = Math.PI;
	private static double TWOPI = Math.PI * 2;
	private static double DE2RA = 0.01745329252;
	private static double RA2DE = 57.2957795129;
	private static double ERAD = 6378.135;
	private static double ERADM = 6378135.0;
	private static double AVG_ERAD = 6371.0;
	private static double FLATTENING = 1.0 / 298.257223563;

	private static double EPS = 0.000000000005;
	private static double KM2MI = 0.621371;
	private static double GEOSTATIONARY_ALT = 35786.0; // km

	public static double distance(double lat1, double lon1, double lat2, double lon2) {
		lat1 = DE2RA * lat1;
		lon1 = -DE2RA * lon1;
		lat2 = DE2RA * lat2;
		lon2 = -DE2RA * lon2;

		double F = (lat1 + lat2) / 2.0;
		double G = (lat1 - lat2) / 2.0;
		double L = (lon1 - lon2) / 2.0;

		double sing = Math.sin(G);
		double cosl = Math.cos(L);
		double cosf = Math.cos(F);
		double sinl = Math.sin(L);
		double sinf = Math.sin(F);
		double cosg = Math.cos(G);

		double S = sing * sing * cosl * cosl + cosf * cosf * sinl * sinl;
		double C = cosg * cosg * cosl * cosl + sinf * sinf * sinl * sinl;
		double W = Math.atan2(Math.sqrt(S), Math.sqrt(C));
		double R = Math.sqrt((S * C)) / W;
		double H1 = (3 * R - 1.0) / (2.0 * C);
		double H2 = (3 * R + 1.0) / (2.0 * S);
		double D = 2 * W * ERAD;
		return (D * (1 + FLATTENING * H1 * sinf * sinf * cosg * cosg - FLATTENING * H2 * cosf * cosf * sing * sing)) * 1000;
	}

	public static void main(String[] args) {
		System.out.println(distance(30.56903946, 104.06201405, 32.234233, 23.23123));
	}
}
