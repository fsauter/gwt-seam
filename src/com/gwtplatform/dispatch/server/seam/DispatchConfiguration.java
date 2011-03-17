package com.gwtplatform.dispatch.server.seam;

/**
 * The dispatch configuration of GWTP Seam.
 * <p>
 * IMPORTANT: Do not forget to annotate you implementation with
 * {@link org.jboss.seam.annotations.Startup}
 * </p>
 * 
 * @author Florian Sauter
 */
public interface DispatchConfiguration {
	void configureHandlers(HandlerRegistry handlerRegistry);
}