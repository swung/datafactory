package org.datafactory;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.datafactory.values.NameDataValues;
import org.datafactory.values.AddressDataValues;
import org.datafactory.values.ContentDataValues;
import org.datafactory.values.impl.RBAddressDataValues;
import org.datafactory.values.impl.RBContentDataValues;

public class DataFactory {

	private static Random random = new Random(93285);

	private Locale locale;

	private NameDataValues nameDataValues;
	private AddressDataValues addressDataValues;
	private ContentDataValues contentDataValues;

	public DataFactory(Locale locale) {
		this.locale = locale;
	}

	public DataFactory(String locale) {
		this.locale = new Locale(locale);
	}

	public DataFactory() {
		this.locale = Locale.getDefault();
	}

	/**
	 * Returns a random item from a list of items.
	 * 
	 * @param <T>
	 *            Item type in the list and to return
	 * @param items
	 *            List of items to choose from
	 * @return Item from the list
	 */
	public <T> T getItem(List<T> items) {
		return getItem(items, 100, null);
	}

	/**
	 * Returns a random item from a list of items or the null depending on the
	 * probability parameter. The probability determines the chance (in %) of
	 * returning an item off the list versus null.
	 * 
	 * @param <T>
	 *            Item type in the list and to return
	 * @param items
	 *            List of items to choose from
	 * @param probability
	 *            chance (in %, 100 being guaranteed) of returning an item from
	 *            the list
	 * @return Item from the list or null if the probability test fails.
	 */
	public <T> T getItem(List<T> items, int probability) {
		return getItem(items, probability, null);
	}

	/**
	 * Returns a random item from a list of items or the defaultItem depending
	 * on the probability parameter. The probability determines the chance (in
	 * %) of returning an item off the list versus the default value.
	 * 
	 * @param <T>
	 *            Item type in the list and to return
	 * @param items
	 *            List of items to choose from
	 * @param probability
	 *            chance (in %, 100 being guaranteed) of returning an item from
	 *            the list
	 * @param defaultItem
	 *            value to return if the probability test fails
	 * @return Item from the list or the default value
	 */
	public <T> T getItem(List<T> items, int probability, T defaultItem) {
		if (items == null) {
			throw new IllegalArgumentException("Item list cannot be null");
		}
		if (items.isEmpty()) {
			throw new IllegalArgumentException("Item list cannot be empty");
		}

		return chance(probability) ? items.get(random.nextInt(items.size()))
				: defaultItem;
	}

	/**
	 * Returns a random item from an array of items
	 * 
	 * @param <T>
	 *            Array item type and the type to return
	 * @param items
	 *            Array of items to choose from
	 * @return Item from the array
	 */
	public <T> T getItem(T[] items) {
		return getItem(items, 100, null);
	}

	/**
	 * Returns a random item from an array of items or null depending on the
	 * probability parameter. The probability determines the chance (in %) of
	 * returning an item from the array versus null.
	 * 
	 * @param <T>
	 *            Array item type and the type to return
	 * @param items
	 *            Array of items to choose from
	 * @param probability
	 *            chance (in %, 100 being guaranteed) of returning an item from
	 *            the array
	 * @return Item from the array or the default value
	 */
	public <T> T getItem(T[] items, int probability) {
		return getItem(items, probability, null);
	}

	/**
	 * Returns a random item from an array of items or the defaultItem depending
	 * on the probability parameter. The probability determines the chance (in
	 * %) of returning an item from the array versus the default value.
	 * 
	 * @param <T>
	 *            Array item type and the type to return
	 * @param items
	 *            Array of items to choose from
	 * @param probability
	 *            chance (in %, 100 being guaranteed) of returning an item from
	 *            the array
	 * @param defaultItem
	 *            value to return if the probability test fails
	 * @return Item from the array or the default value
	 */
	public <T> T getItem(T[] items, int probability, T defaultItem) {
		if (items == null) {
			throw new IllegalArgumentException("Item array cannot be null");
		}
		if (items.length == 0) {
			throw new IllegalArgumentException("Item array cannot be empty");
		}
		return chance(probability) ? items[random.nextInt(items.length)]
				: defaultItem;
	}

	/**
	 * Gives you a true/false based on a probability with a random number
	 * generator. Can be used to optionally add elements.
	 * 
	 * <pre>
	 * if (DataFactory.chance(70)) {
	 * 	// 70% chance of this code being executed
	 * }
	 * </pre>
	 * 
	 * @param chance
	 *            % chance of returning true
	 * @return
	 */
	public boolean chance(int chance) {
		return random.nextInt(100) < chance;
	}

	/**
	 * Call randomize with a seed value to reset the random number generator. By
	 * using the same seed over different tests, you will should get the same
	 * results out for the same data generation calls.
	 * 
	 * @param seed
	 *            Seed value to use to generate random numbers
	 */
	public void randomize(int seed) {
		random = new Random(seed);
	}

	// address data

	public AddressDataValues getAddressDataValues() {
		initAddressDataValues();
		return addressDataValues;
	}

	public String getStreetName() {
		return getItem(getAddressDataValues().getStreetNames());
	}

	public String getStreetSuffix() {
		return getItem(getAddressDataValues().getAddressSuffixes());
	}

	public String getCity() {
		return getItem(getAddressDataValues().getCities());
	}

	// content data
	/**
	 * @return a random character
	 */
	public char getRandomChar() {
		return (char) (random.nextInt(26) + 'a');
	}

	/**
	 * Return a string containing <code>length</code> random characters
	 * 
	 * @param length
	 *            number of characters to use in the string
	 * @return A string containing <code>length</code> random characters
	 */
	public String getRandomChars(int length) {
		return getRandomChars(length, length);
	}

	/**
	 * Return a string containing between <code>length</code> random characters
	 * 
	 * @param length
	 *            number of characters to use in the string
	 * @return A string containing <code>length</code> random characters
	 */
	public String getRandomChars(int minLength, int maxLength) {
		validateMinMaxParams(minLength, maxLength);
		StringBuilder sb = new StringBuilder(maxLength);

		int length = minLength;
		if (maxLength != minLength) {
			length = length + random.nextInt(maxLength - minLength);
		}
		while (length > 0) {
			sb.append(getRandomChar());
			length--;
		}
		return sb.toString();
	}

	public String getRandomUnicode() {
		byte[] str = new byte[2];
		int chr = random.nextInt(0x51A5) + 0x4E00;
		str[1] = (byte) ((chr & 0xFF00) >> 8);
		str[0] = (byte) (chr & 0xFF);
		try {
			return new String(str, "UTF-16LE");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public String getRandomUnicodes(int length) {
		return getRandomUnicodes(length, length);
	}

	public String getRandomUnicodes(int minLength, int maxLength) {
		validateMinMaxParams(minLength, maxLength);
		StringBuilder sb = new StringBuilder(maxLength);

		int length = minLength;
		if (maxLength != minLength) {
			length = length + random.nextInt(maxLength - minLength);
		}
		while (length > 0) {
			sb.append(getRandomUnicode());
			length--;
		}
		return sb.toString();
	}

	public ContentDataValues getContentDataValues() {
		initContentDataValues();
		return contentDataValues;
	}

	// date data
	/**
	 * Builds a date from the year, month, day values passed in
	 * 
	 */
	public Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(year, month - 1, day, 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * Returns a random date which is in the range <code>baseData</code> +
	 * <code>minDaysFromData</code> to <code>baseData</code> +
	 * <code>maxDaysFromData</code>. This method does not alter the time
	 * component and the time is set to the time value of the base date.
	 * 
	 */
	public Date getDate(Date baseDate, int minDaysFromDate, int maxDaysFromDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(baseDate);
		int diff = minDaysFromDate
				+ (random.nextInt(maxDaysFromDate - minDaysFromDate));
		cal.add(Calendar.DATE, diff);
		return cal.getTime();
	}

	/**
	 * Returns a random date between two dates. This method will alter the time
	 * component of the dates
	 * 
	 */
	public Date getDateBetween(Date minDate, Date maxDate) {
		// this can break if seconds is an int
		long seconds = (maxDate.getTime() - minDate.getTime()) / 1000;
		seconds = (long) (random.nextDouble() * seconds);
		Date result = new Date();
		result.setTime(minDate.getTime() + (seconds * 1000));
		return result;
	}

	public Date getBirthDate() {
		Date base = new Date(0);
		return getDate(base, -365 * 15, 365 * 15);
	}

	// private methods
	private void initAddressDataValues() {
		if (addressDataValues == null) {
			addressDataValues = new RBAddressDataValues(locale);
		}
	}

	private void initContentDataValues() {
		if (contentDataValues == null) {
			contentDataValues = new RBContentDataValues(locale);
		}
	}

	private void validateMinMaxParams(int minLength, int maxLength) {
		if (minLength < 0) {
			throw new IllegalArgumentException(
					"Minimum length must be a non-negative number");
		}

		if (maxLength < 0) {
			throw new IllegalArgumentException(
					"Maximum length must be a non-negative number");
		}

		if (maxLength < minLength) {
			throw new IllegalArgumentException(
					String.format(
							"Minimum length must be less than maximum length (min=%d, max=%d)",
							minLength, maxLength));
		}
	}

	public static void main(String[] args) {
		DataFactory df = new DataFactory(new Locale("zh", "CN"));
		for (int i = 0; i < 50; i++) {
			// System.out.println(df.getCity() + df.getStreetSuffix()
			// + df.getStreetName());
			System.out.println(df.getRandomChars(2, 50));
			System.out.println(df.getRandomUnicodes(2, 50));
			System.out.println(df.getBirthDate());
		}
	}
}
