package com.gwtplatform.dispatch.server.seam;

import java.security.SecureRandom;

import com.gwtplatform.dispatch.server.AbstractRandomSecurityCookieFilter;

/**
 * @author Florian Sauter
 */
public class RandomSecurityCookieFilter extends AbstractRandomSecurityCookieFilter implements SecurityCookieFilter {

	protected RandomSecurityCookieFilter(String securityCookieName, SecureRandom random) {
		super(securityCookieName, random);
	}

}