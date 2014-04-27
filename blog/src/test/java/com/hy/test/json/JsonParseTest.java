package com.hy.test.json;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonParseTest {

	public static void main(String[] args) {
		Map<Integer,String> map1 = new HashMap<Integer, String>();
		map1.put(1, "a1");
		map1.put(2, "a2");
		List<Map<Integer,String>> list = new ArrayList<Map<Integer,String>>();
		list.add(map1);
		Object[] ary = new Object[]{20,30,"我是来测中文的？",false,null,new String[]{"c","d"},list};
		System.out.println(arrayToJson(ary));
	}
	
	public static String valueToJson(Object obj){
		if(obj != null){
			if(obj instanceof String){
				return "\""+(String) obj +"\"";
			}else if(isNumbertValue(obj)){
				return String.valueOf(obj);
			}else if(obj instanceof Boolean){
				return String.valueOf(obj);
			}else if(obj.getClass().isArray()){
				return arrayToJson(obj);
			}else if(obj instanceof Map){
				return mapToJson(obj);
			}else if(obj instanceof Collection){
				return CollectionToJson(obj);
			}else{
				
			}
		}
		return "null";
	}
	
	private static String mapToJson(Object obj) {
		if(obj != null && obj instanceof Map){
			Map map = (Map) obj;
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			for(Object key : map.keySet()){
				sb.append(valueToJson(key));
				sb.append(":");
				sb.append(valueToJson(map.get(key)));
				sb.append(",");
			}
			if(sb.charAt(sb.length() - 1) == ','){
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append("}");
			return sb.toString();
		}
		return "null";
	}

	private static String arrayToJson(Object obj) {
		if(obj != null && obj.getClass().isArray()){
			int length =  Array.getLength(obj);
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for(int i = 0; i < length ;i++){
				sb.append(valueToJson(Array.get(obj, i)));
				sb.append(",");
			}
			if(sb.charAt(sb.length() - 1) == ','){
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append("]");
			return sb.toString();
		}
		return "null";
	}
	
	private static String CollectionToJson(Object obj) {
		if(obj != null && obj instanceof Collection){
			Collection<Object> collection = (Collection<Object>) obj;
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			Iterator<Object> iterator = collection.iterator();
			while(iterator.hasNext()){
				sb.append(valueToJson(iterator.next()));
				sb.append(",");
			}
			if(sb.charAt(sb.length() - 1) == ','){
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append("]");
			return sb.toString();
		}
		return "null";
	}

	private static boolean isNumbertValue(Object obj) {
		if(obj instanceof Integer){
			return true;
		}else if(obj instanceof Double){
			return true;
		}else if(obj instanceof Float){
			return true;
		}else if(obj instanceof Long){
			return true;
		}
		return false;
	}

	public static String objectToJson(Object obj){
		
		return null;
		
	}
}
