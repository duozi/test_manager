package com.xn.manage.filter;

import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public class FreemarkerFilter implements Filter {
	 private Locale locale;

	    private ApplicationContext ctx;

	    @Override
	    public void init(FilterConfig filterConfig) throws ServletException {
	        String localeStr = filterConfig.getInitParameter("locale");
	        if(StringUtils.hasText(localeStr)){
	            locale = new Locale(localeStr);
	        }else {
	            locale = Locale.getDefault();
	        }
	    }

	    @Override
	    public void doFilter(ServletRequest request, ServletResponse response,
                             FilterChain chain) throws IOException, ServletException {
	        HttpServletRequest req = (HttpServletRequest)request;
	        HttpServletResponse res = (HttpServletResponse)response;
	        if(ctx == null){
	            ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getSession().getServletContext());
	            if(null == ctx){
	                throw new ExceptionInInitializerError("spring context is not loaded!");
	            }
	        }
	        try {
	            String name = req.getRequestURI();
	            name = name.substring(1, name.lastIndexOf(".html"));
	            FreeMarkerViewResolver viewResolver = ctx.getBean(FreeMarkerViewResolver.class);
	            View view = viewResolver.resolveViewName(name, locale);
//	            @SuppressWarnings("unchecked")
//	            Map<String, Object> model = (Map<String, Object>) request.getAttribute(ViewRendererServlet.MODEL_ATTRIBUTE);
	            view.render(null, req, res);
	        } catch (Exception e) {
	            throw new ServletException(e);
	        }
	    }

	    @Override
	    public void destroy() {
	        // TODO Auto-generated method stub
	        
	    }
}
