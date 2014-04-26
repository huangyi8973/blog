package com.hy.core.action;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.hy.core.annotations.At;
import com.hy.core.annotations.Controller;

public final class ActionMapper {

	private static ActionMapper _instance;
	private static Object _lock = new Object();
	/**
	 * map<url,类#方法>
	 */
	private Map<String,String> _mapper = new HashMap<String,String>();
	/**
	 * controller包扫描路径
	 */
	private String _scanPackagePath = "com.hy.blog.controller";
	
	
	public String getScanPackagePath() {
		return _scanPackagePath;
	}

	public void setScanPackagePath(String scanPackagePath) {
		this._scanPackagePath = scanPackagePath;
	}

	private ActionMapper(){}
	
	public static ActionMapper getInstance(){
		if(_instance == null){
			synchronized (_lock) {
				if(_instance == null){
					_instance = new ActionMapper();
				}
			}
		}
		return _instance;
	}
	
	public void init() throws IOException, ClassNotFoundException{
		System.out.println("url映射初始化开始");
		long start = System.currentTimeMillis();
		String packagePath = getScanPackagePath().replace('.', File.separatorChar);
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packagePath);
		while(urls.hasMoreElements()){
			URL url = urls.nextElement();
			String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
			addClassToMapper(filePath);
		}
		System.out.println(String.format("url映射初始化结束,耗时:%d",+System.currentTimeMillis() - start));
	}
	
	public String getActionInfoStr(String url){
		return this._mapper.get(url);
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
		Controller controller = (Controller) cls.getAnnotation(Controller.class);
		if(controller != null){
			//尝试获取类上面的URL
			At at = (At) cls.getAnnotation(At.class);
			String url = "";
			if(at != null){
				url+=at.value();
			}
			//获取方法里的url映射
			Method[] methods = cls.getMethods();
			for(Method method : methods){
				At methodAt = method.getAnnotation(At.class);
				if(methodAt != null){
					String finalUrl = url+methodAt.value();
					this._mapper.put(finalUrl, String.format("%s#%s", className,method.getName()));
				}
			}
		}
	}
}
