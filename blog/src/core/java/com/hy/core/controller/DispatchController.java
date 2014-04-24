package com.hy.core.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.core.annotations.At;
import com.hy.core.annotations.Controller;
import com.hy.core.view.View;
import com.hy.core.viewrender.JspViewRender;
import com.hy.core.viewrender.ViewRender;

public class DispatchController extends HttpServlet {
	
	private boolean isInited = false;
	/**
	 * map<url,类#方法>
	 */
	private Map<String,String> mapper = new HashMap<String, String>();
	private String scanPackagePath = "com.hy.blog.controller";
	private String[] ignoreUrls;
	
	
	public String[] getIgnoreUrls() {
		return ignoreUrls;
	}

	public void setIgnoreUrls(String ignoreUrls) {
		this.ignoreUrls = ignoreUrls.split(",");
	}

	public String getScanPackagePath() {
		return scanPackagePath;
	}

	public void setScanPackagePath(String scanPackagePath) {
		this.scanPackagePath = scanPackagePath;
	}

	@Override
	public void init() throws ServletException {
		try {
			initMapper();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		this.setIgnoreUrls(config.getInitParameter("ignoreUrls"));
		super.init(config);
	}
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String url = req.getRequestURI().substring(req.getContextPath().length());
		//跳过忽略的url
		if(isIgnoreUrl(url)){
			return;
		}
		//执行controller
		try {
			execute(url,req,resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private boolean isIgnoreUrl(String url) {
		if(this.getIgnoreUrls() != null){
			for(String ignoreUrl : this.getIgnoreUrls()){
				if(url.matches(ignoreUrl)){
					System.out.println("忽略url:"+url+" 模式:"+ignoreUrl);
					return true;
				}
			}
		}
		return false;
	}

	private void execute(String url,HttpServletRequest req, HttpServletResponse resp) 
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, ServletException, IOException {
		String clsAndMethod = this.mapper.get(url);
		System.out.println(String.format("获得url映射:%s",clsAndMethod));
		if(clsAndMethod != null){
			String[] rs = clsAndMethod.split("#");
			Class cls = Thread.currentThread().getContextClassLoader().loadClass(rs[0]);
			Object controller = cls.newInstance();
			Method method = controller.getClass().getMethod(rs[1], HttpServletRequest.class,HttpServletResponse.class);
			Object result = method.invoke(controller, req,resp);
			if(result != null){
				if(result instanceof View){
					//返回视图
					JspViewRender jsp = new JspViewRender(req, resp);
					jsp.render((View) result);
				}
			}
		}else{
			resp.sendError(404);
		}
	}

	/**
	 * 初始化controller的扫描
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void initMapper() throws IOException, ClassNotFoundException {
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
	
	private void addClassToMapper(String filePath) throws ClassNotFoundException{
		File dir = new File(filePath);
		
		if(!dir.exists() && !dir.isDirectory()){
			return;
		}
		
		//只扫描java.class文件和文件夹
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
					String rootPath = getScanPackagePath().replace('.', file.separatorChar);
					//获取类
					String className = file.getAbsolutePath().substring(file.getAbsolutePath().indexOf(rootPath)).replace(File.separatorChar, '.');
					className = className.substring(0,className.length() - 6);
					Class cls = Thread.currentThread().getContextClassLoader().loadClass(className);
					//获取注解
					Controller controller = (Controller) cls.getAnnotation(Controller.class);
					if(controller != null){
						//尝试获取类上面的URL
						At at = (At) cls.getAnnotation(At.class);
						StringBuilder url = new StringBuilder();
						if(at != null){
							url.append(at.value());
						}
						//获取方法里的url映射
						Method[] methods = cls.getMethods();
						for(Method method : methods){
							At methodAt = method.getAnnotation(At.class);
							if(methodAt != null){
								String finalUrl = url.append(methodAt.value()).toString();
								this.mapper.put(finalUrl, String.format("%s#%s", className,method.getName()));
							}
						}
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		DispatchController controller = new DispatchController();
		controller.initMapper();
	}
}
