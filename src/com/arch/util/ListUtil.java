package com.arch.util;

import java.util.List;

public class ListUtil {

	public static <E> String parseList(List<E> lst){
		
		StringBuilder sb = new StringBuilder();
		
		if(null == lst){
			return null;
		}
		
		for(E e : lst){
			sb.append(e.toString()+"\n");
		}
		
		return sb.toString();
	}
	
}
