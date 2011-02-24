package com.gwtplatform.dispatch.server.seam;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Scope;

/**
 * The dispatch configuration of GWTP Seam.
 * <p>
 * IMPORTANT: Do not forget to annotate you implementation with
 * {@link org.jboss.seam.annotations.Startup}
 * </p>
 * 
 * @author Florian Sauter
 */
@Scope(ScopeType.APPLICATION)
public interface DispatchConfiguration {
	
	public static final String DEFAULT_ENDPOINT = "seam/resource/gwtp";
	
	String getEndPoint();

	void configureHandlers(HandlerRegistry handlerRegistry);
}