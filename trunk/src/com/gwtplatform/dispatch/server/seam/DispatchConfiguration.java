package com.gwtplatform.dispatch.server.seam;

/**
 * The dispatch configuration of GWTP Seam.
 * 
 * @author Florian Sauter
 */
public interface DispatchConfiguration {
	
	public static final String COMPONENT_NAME = "gwtpDispatchConfiguration";
	
	void configureHandlers(HandlerRegistry handlerRegistry);
}