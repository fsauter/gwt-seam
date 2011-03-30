package com.gwtplatform.dispatch.server.seam;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gwtplatform.dispatch.server.seam.SecurityCookieFilter;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityCookieFilterConfig {

	public static final String DEFAULT_URL_PATTERN = "/seam/resource/*";
	
	Class<? extends SecurityCookieFilter> filterClass();
	
	String urlPattern() default DEFAULT_URL_PATTERN;
	
	String cookieName() default "";
	
}
