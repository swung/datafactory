package org.datafactory.values.impl;

import java.util.Locale;

import org.datafactory.values.ContentDataValues;

public class RBContentDataValues extends ResourceBundleValues implements
		ContentDataValues {

	public RBContentDataValues(Locale locale) {
		super("org.datafactory.values.res.ContentDataValues", locale);
	}

	@Override
	public String[] getWords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getBusinessTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getEmailHosts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getTlds() {
		// TODO Auto-generated method stub
		return null;
	}

}
