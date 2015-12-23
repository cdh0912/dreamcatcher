package com.dreamcatcher.util;

public class NumberCheck {

	public static int nullToZero(String tmp) {
		int num = 0;
		if(tmp != null && !tmp.isEmpty()) {
			if(isNumber(tmp)) {
				num = Integer.parseInt(tmp);
			}
		}
		return num;
	}
	
	public static int nullToOne(String tmp) {
		int num = 1;
		if(tmp != null && !tmp.isEmpty()) {
			if(isNumber(tmp)) {
				num = Integer.parseInt(tmp);
			}
		}
		return num;
	}

	private static boolean isNumber(String tmp) {
		boolean flag = true;
		int len = tmp.length();
		for(int i=0;i<len;i++) {
			int n = tmp.charAt(i) - 48;
			if(n < 0 || n > 9) {
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	public static double nullToDoubleZero(String tmp) {
		double num = 0;
		if(tmp != null && !tmp.isEmpty()) {
			num = Double.parseDouble(tmp);
		}
		return num;
	}
	
	public static double nullToDoubleOne(String tmp) {
		double num = 1;
		if(tmp != null && !tmp.isEmpty()) {
			num =  Double.parseDouble(tmp);
		}
		return num;
	}
	
}
