package ch.hearc.p3.recsys.utils;

public class Tools
{
	public static boolean compare(double a, double b, double epsilon)
	{
		return Math.abs(a-b) < epsilon;
	}
	
	public static boolean compare(double a, double b)
	{
		return compare(a, b, 1e-6);
	}
}
