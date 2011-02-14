package org.datafactory.values.impl;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleValues {
	protected String resource;
	protected Locale locale;

	public ResourceBundleValues(String resource, Locale locale) {
		this.resource = resource;
		this.locale = locale;
	}

	public String[] getValues(String key) {
		ResourceBundle rb = ResourceBundle.getBundle(resource, locale);
		String content = rb.getString(key);
		return content.split(",");
	}

}
