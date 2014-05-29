package com.wtl.wawork.config;

import java.util.Set;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Application initializer. Based on <a
 * href="https://github.com/spring-guides/tut-rest/">spring-guides</a>
 * tutorials.
 * 
 * @author Weston Turney-Loos
 * 
 */
public class Initializer implements WebApplicationInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(Initializer.class);

    private static final String SERVLET_NAME = "WaWorks Servlet";

    private void configureSpringMvc(ServletContext servCtx, WebApplicationContext rootCtx) {
        AnnotationConfigWebApplicationContext mvcCtx = new AnnotationConfigWebApplicationContext();
        mvcCtx.register(MvcConfig.class);
        mvcCtx.setParent(rootCtx);

        ServletRegistration.Dynamic servlet = servCtx.addServlet(SERVLET_NAME, new DispatcherServlet(mvcCtx));
        servlet.setLoadOnStartup(1);
        Set<String> conflicts = servlet.addMapping("/");

        if (!conflicts.isEmpty()) {
            for (String conflict : conflicts) {
                if (LOG.isDebugEnabled()) {
                    LOG.error("Unable to map due to conflict " + conflict);
                }
            }
            throw new IllegalStateException(String.format("%s cannot be mapped to '/'", SERVLET_NAME));
        }
    }

    private void configureSpringSecurity(ServletContext servCtx, WebApplicationContext rootCtx) {
        FilterRegistration.Dynamic springSecurity = servCtx.addFilter("springSecurityFilterChain",
                new DelegatingFilterProxy("springSecurityFilterChain", rootCtx));

        springSecurity.addMappingForUrlPatterns(null, true, "/*");
    }

    private WebApplicationContext createRootContext(ServletContext servCtx) {
        AnnotationConfigWebApplicationContext rootCtx = new AnnotationConfigWebApplicationContext();
        rootCtx.register(JpaConfig.class, CoreConfig.class, SecurityConfig.class);
        rootCtx.refresh();

        servCtx.addListener(new ContextLoaderListener(rootCtx));
        servCtx.setInitParameter("defaultHtmlEscape", "true");

        return rootCtx;
    }

    @Override
    public void onStartup(ServletContext servCtx) throws ServletException {
        WebApplicationContext rootCtx = createRootContext(servCtx);
        configureSpringMvc(servCtx, rootCtx);
        configureSpringSecurity(servCtx, rootCtx);
    }

}
