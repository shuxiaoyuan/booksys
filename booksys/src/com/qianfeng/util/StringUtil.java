package com.qianfeng.util;

public class StringUtil {
	public static boolean isNullOrEmpty(String info){
		if(info == null || info.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
}
