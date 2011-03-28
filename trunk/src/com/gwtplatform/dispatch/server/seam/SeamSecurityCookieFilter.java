package com.gwtplatform.dispatch.server.seam;

import java.io.IOException;
import java.security.SecureRandom;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.annotations.web.Filter;
import org.jboss.seam.log.Log;
import org.jboss.seam.servlet.ContextualHttpServletRequest;
import org.jboss.seam.web.AbstractFilter;
import org.jboss.seam.web.ServletContexts;

import com.google.gwt.user.server.rpc.seam.GWTSeamBundleReader;

/**
 * 
 * @author Florian Sauter
 */
@Startup
@Scope(ScopeType.APPLICATION)
@Name("gwtpSeamSecurityCookieFilter")
@BypassInterceptors
@Filter(within = "org.jboss.seam.web.ajax4jsfFilter")
public class SeamSecurityCookieFilter extends AbstractFilter {

	@Logger
	private Log log;
	
	private static javax.servlet.Filter securityCookieFilter;

	public static final String DEFAULT_URL_PATTERN = "/seam/resource/*";
	
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {

		 new ContextualHttpServletRequest( (HttpServletRequest) request )
	      {
	         @Override
	         public void process() throws ServletException, IOException  {
	        	 DispatchConfiguration gwtpDispatchConfiguration = (DispatchConfiguration) Component.getInstance(DispatchConfiguration.COMPONENT_NAME);
	     		SecurityCookieFilterConfig securityConfiguration = gwtpDispatchConfiguration.getClass().getAnnotation(SecurityCookieFilterConfig.class);
	    		if(securityConfiguration != null) { 
	        		 javax.servlet.Filter customSecurityCookieFilter = getSecurityCookieFilter(securityConfiguration.cookieName(), securityConfiguration.filterClass());
	        		 customSecurityCookieFilter.doFilter(request, response, chain);
	        	 } else {
	        		 chain.doFilter(request, response);
	        	 }
	         }
	      }.run();
	}

	/**
	 * Creates (if null) or returns the static cookie filter based on the passed filter class.
	 */
	protected javax.servlet.Filter getSecurityCookieFilter(String securityCookieName, Class<? extends SecurityCookieFilter> securityCookieFilterClass) {
		if (securityCookieFilter == null) {
			 if(HttpSessionSecurityCookieFilter.class.equals(securityCookieFilterClass)) {
				 securityCookieFilter = new HttpSessionSecurityCookieFilter(securityCookieName, getSession());
    		 } else if(RandomSecurityCookieFilter.class.equals(securityCookieFilterClass)) {
    			 securityCookieFilter = new RandomSecurityCookieFilter(securityCookieName, new SecureRandom());
    		 }
			 log.info("GWTP - Protecting against XSRF attacks is on.");
			 log.info("SecurityCookieFilter: " + securityCookieFilter.getClass().getSimpleName());
			 log.info("SecurityCookieName: " + securityCookieName);
		}
		return securityCookieFilter;
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String customUrlPattern = GWTSeamBundleReader.getKeyValueAsString("gwtp.security.filter.pattern");
		if(customUrlPattern != null) {
			setUrlPattern(customUrlPattern);
			log.info("Found gwtseam.properties and custom security filter url pattern " + customUrlPattern);
		} else {
			setUrlPattern(DEFAULT_URL_PATTERN);
			log.info("No gwtseam.properties or gwtp.security.filter.pattern property found. Falling back to url pattern " + DEFAULT_URL_PATTERN);
		}
		super.init(filterConfig);
	}
	
	protected HttpSession getSession() {
		return ServletContexts.instance().getRequest().getSession();
	}

}
