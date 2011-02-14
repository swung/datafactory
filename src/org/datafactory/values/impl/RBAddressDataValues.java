package org.datafactory.values.impl;

import java.util.Locale;

import org.datafactory.values.AddressDataValues;

public class RBAddressDataValues extends ResourceBundleValues implements
		AddressDataValues {
	private static String[] streetNames;
	private static String[] addressSuffixes;
	private static String[] cities;

	public RBAddressDataValues(Locale locale) {
		super("org.datafactory.values.res.AddressDataValues", locale);
	}

	@Override
	public String[] getStreetNames() {
		if (streetNames == null) {
			streetNames = getValues("streetNames");
		}
		return streetNames;
	}

	@Override
	public String[] getCities() {
		if (cities == null) {
			cities = getValues("cities");
		}
		return cities;
	}

	@Override
	public String[] getAddressSuffixes() {
		if (addressSuffixes == null) {
			addressSuffixes = getValues("addressSuffixes");
		}
		return addressSuffixes;
	}

	public static void main(String[] args) {
		RBAddressDataValues r = new RBAddressDataValues(new Locale("zh","CN"));
		System.out.println(r.getStreetNames()[180]);
	}

}
