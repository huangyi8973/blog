package com.hy.core.utils;

import java.util.Collection;

public class Utils {

	public static <T> boolean isEmplyOrNull(T[] ary){
		if(ary == null || ary.length == 0){
			return true;
		}
		return false;
	}
	
	public static <T> boolean isEmplyOrNull(Collection<T> collection){
		if(collection == null || collection.size()  <= 0){
			return true;
		}
		return false;
	}
}
