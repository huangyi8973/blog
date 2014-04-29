package com.hy.core.aspect;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hy.core.action.ActionMapper;
import com.hy.core.annotations.aspect.AfterAdvice;
import com.hy.core.annotations.aspect.Aspect;
import com.hy.core.annotations.aspect.BeforeAdvice;
import com.hy.core.annotations.web.At;
import com.hy.core.annotations.web.Controller;
import com.hy.core.config.JHelloConfig;

public class AdviceMapper {

	private final static Logger logger = LoggerFactory.getLogger(AdviceMapper.class);
	private static AdviceMapper _instance;
	private static Object _lock = new Object();
	/**
	 * map<joinPointPartten,类名#方法名>
	 */
	private Map<String,String> _beforeMapper = new HashMap<String,String>();
	private Map<String,String> _afterMapper = new HashMap<String,String>();
	
	public Map<String,String> getBeforeAdviceParttenAndInfoMap(){
		return this._beforeMapper;
	}
	public Map<String,String> getAfterAdviceParttenAndInfoMap(){
		return this._afterMapper;
	}
	
	/**
	 * controller包扫描路径
	 */
	private String _scanPackagePath = JHelloConfig.getAspectScanPackage();
	
	
	public String getScanPackagePath() {
		return _scanPackagePath;
	}

	private AdviceMapper(){}
	
	public static AdviceMapper getInstance(){
		if(_instance == null){
			synchronized (_lock) {
				if(_instance == null){
					_instance = new AdviceMapper();
				}
			}
		}
		return _instance;
	}
	
	public void init() throws IOException, ClassNotFoundException{
		logger.debug("aspect mapper init");
		long start = System.currentTimeMillis();
		String packagePath = getScanPackagePath().replace('.', File.separatorChar);
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packagePath);
		while(urls.hasMoreElements()){
			URL url = urls.nextElement();
			String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
			addClassToMapper(filePath);
		}
		logger.debug(String.format("aspect mapper end, spend:%dms",+System.currentTimeMillis() - start));
	}
	
	private void addClassToMapper(String filePath) throws ClassNotFoundException{
		File dir = new File(filePath);
		if(!dir.exists() && !dir.isDirectory()){
			return;
		}
		//扫描java.class文件和文件夹
		File[] files = dir.listFiles(new FileFilter() {
			
			public boolean accept(File pathname) {
				return pathname.isDirectory() || (pathname.isFile() && pathname.getName().endsWith(".class"));
			}
		});
		
		if(files != null && files.length > 0){
			for(File file : files){
				if(file.isDirectory()){
					//递归查找所有目录
					addClassToMapper(file.getAbsolutePath());
				}else{
					Mapping(file);
				}
			}
		}
	}

	/**
	 * 映射
	 * @param file
	 * @throws ClassNotFoundException
	 */
	private void Mapping(File file) throws ClassNotFoundException {
		String rootPath = getScanPackagePath().replace('.', File.separatorChar);
		//获取类
		String className = file.getAbsolutePath().substring(file.getAbsolutePath().indexOf(rootPath)).replace(File.separatorChar, '.');
		//去掉.class
		className = className.substring(0,className.length() - 6);
		Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass(className);
		//获取注解
		Aspect aspect = (Aspect) cls.getAnnotation(Aspect.class);
		if(aspect != null){
			// 获取通知
			Method[] methods = cls.getDeclaredMethods();
			for(Method method : methods){
				AfterAdvice after =  method.getAnnotation(AfterAdvice.class);
				if(after != null){
					logger.debug(String.format("AfterAdvice found %s#%s, pattern : %s", className,method.getName(),after.value()));
					this._afterMapper.put(after.value(), String.format("%s#%s", className,method.getName()));
				}
				
				BeforeAdvice before =  method.getAnnotation(BeforeAdvice.class);
				if(before != null){
					logger.debug(String.format("BeforeAdvice found %s#%s, pattern：%s", className,method.getName(),before.value()));
					this._beforeMapper.put(before.value(), String.format("%s#%s", className,method.getName()));
				}
			}
		}
	}
}