package com.gwtplatform.dispatch.server.seam;

/**
 * The dispatch configuration of GWTP Seam.
 * 
 * @author Florian Sauter
 */
public interface DispatchConfiguration {
	void configureHandlers(HandlerRegistry handlerRegistry);
}