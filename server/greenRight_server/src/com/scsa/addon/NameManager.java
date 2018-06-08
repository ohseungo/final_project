package com.scsa.addon;

public class NameManager {
	
	public static String changeFileName(String fileName, int i) {
		String result = fileName;
		System.out.println(result);
		String[] re = result.split("\\.");
		result = re[0] + i + "." + re[1];
		
		return result;
	}
}
