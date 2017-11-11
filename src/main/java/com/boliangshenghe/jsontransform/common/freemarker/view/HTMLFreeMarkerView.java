package com.boliangshenghe.jsontransform.common.freemarker.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 对freemarker生成的页面进行缓存
 */
public class HTMLFreeMarkerView extends FreeMarkerView {

	@SuppressWarnings("unchecked")
	protected void doRender(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		System.out.println("HTMLFreeMarkerView开始  ");
		// Expose model to JSP tags (as request attributes).
		exposeModelAsRequestAttributes(model, request);
		// Expose all standard FreeMarker hash models.
		SimpleHash fmModel = buildTemplateModel(model, request, response);

		if (logger.isDebugEnabled()) {
			logger.debug("Rendering FreeMarker template [" + getUrl()
					+ "] in FreeMarkerView '" + getBeanName() + "'");
		}
		// Grab the locale-specific version of the template.
		Locale locale = RequestContextUtils.getLocale(request);

		
		
		/*
		 * 如果attribute中有key为“CREATE_HTML”表示要生成静态页
		 */
		String isCreateHtml = (String) request.getAttribute("CREATE_HTML");
		
		if (null!=isCreateHtml && isCreateHtml.equals("TRUE")) {
				createHTML(getTemplate(locale), fmModel, request, response);
		} else {
			processTemplate(getTemplate(locale), fmModel, response);
		}
		
	}
	
	
	/**
	 * 生成静态页
	 * 
	 * @param template
	 * @param model
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws TemplateException
	 * @throws ServletException
	 */
	public void createHTML(Template template, SimpleHash model,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, TemplateException, ServletException {
		// 获取配置文件中的静态页存放路径
		String basePath = request.getSession().getServletContext()
				.getRealPath("/");
		
		basePath= basePath+File.separator+"html"+File.separator;
		basePath = basePath.replace("\\\\","\\");
		
		String orgPath = request.getServerName();
		/*if(orgPath.indexOf("21teacher")!=-1){//学校主页--暂时不对学校开放静态化功能
			orgPath = orgPath.substring(0, orgPath.indexOf("21teacher")-1);
		}else{*///机构主页
			orgPath = orgPath.substring(orgPath.indexOf("www")+4, orgPath.indexOf("com")-1);
		//}
		
		String targetname = "";
		if(request.getRequestURI().equals("/")||request.getRequestURI().equals("")){//首页请求
			targetname = "index";
		}else{
			targetname = request.getRequestURI().substring(1,request.getRequestURI().length());
		}
		targetname = targetname.replace("/", "");
		
		String fullHtmlName = basePath+orgPath+File.separator + targetname+".html";
		
		File htmlFile = new File(fullHtmlName);
		if (!htmlFile.getParentFile().exists()) {
			htmlFile.getParentFile().mkdirs();
		}
		if(htmlFile.exists()){
			htmlFile.delete();
		}
		if (!htmlFile.exists()) {
			htmlFile.createNewFile();
		}
		/*
		if (!htmlFile.exists() || "TRUE".equals(request.getParameter("updatefile"))) {
			htmlFile.createNewFile();
		}*/
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
			template.process(model, out);
		
		}catch(Exception e){
			System.out.println("fileout 异常信息"+ e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
		// 处理模版
		
//		System.out.println("redirect= " + request.getContextPath()+request.getRequestURI());
		response.sendRedirect(request.getRequestURI());
	}

}