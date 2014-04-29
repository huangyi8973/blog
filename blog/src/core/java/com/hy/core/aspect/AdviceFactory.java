package com.hy.core.aspect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hy.core.action.Action;


public class AdviceFactory {

	private static AdviceFactory _instance;
	private static Object _lock = new Object();
	private AdviceFactory(){}
	
	public static AdviceFactory getInstance(){
		if(_instance == null){
			synchronized (_lock) {
				if(_instance == null){
					_instance = new AdviceFactory();
				}
			}
		}
		return _instance;
	}

	public Pointcut[] getBeforeAdvicesByAction(Action action) {
		Map<String,String> map = AdviceMapper.getInstance().getBeforeAdviceParttenAndInfoMap();
		List<Pointcut> list = new ArrayList<Pointcut>();
		for(String pattern : map.keySet()){
			if(action.getClsAndMethod().matches(pattern)){
				String[] targetInfo = action.getClsAndMethod().split("#");
				String[] adviceInfo = map.get(pattern).split("#");
				Pointcut pointcut = new Pointcut(targetInfo[0],targetInfo[1],adviceInfo[0],adviceInfo[1]);
				list.add(pointcut);
			}
		}
		return list.toArray(new Pointcut[0]);
	}
	
}
