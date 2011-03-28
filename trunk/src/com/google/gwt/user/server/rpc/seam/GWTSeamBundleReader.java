package com.google.gwt.user.server.rpc.seam;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 
 * @author Florian Sauter
 *
 */
public class GWTSeamBundleReader {

	public static final String PROPERTY_BUNDLE = "gwtseam";
	
	/**
	 * Looks for a custom gwt-seam property.
	 * 
	 * @param key the property key.
	 * @return the property value or null if the property file or property itself does not exist.
	 */
	public static String getKeyValueAsString(String key) {
		String propertyValue = null;
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(PROPERTY_BUNDLE);
			Object propertyValueObject = bundle.getObject(key);
			if(propertyValueObject != null) {
				propertyValue = String.valueOf(propertyValueObject);
			}
		} catch(MissingResourceException e) {
			// Nothing to do, return null.
		}
		return propertyValue;
	}
	
}
