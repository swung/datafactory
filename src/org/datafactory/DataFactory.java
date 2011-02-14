package org.datafactory;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.datafactory.values.NameDataValues;
import org.datafactory.values.AddressDataValues;
import org.datafactory.values.ContentDataValues;
import org.datafactory.values.impl.RBAddressDataValues;
import org.datafactory.values.impl.RBContentDataValues;

public class DataFactory
{

	private static Random random = new Random(93285);

	private Locale locale;

	private NameDataValues nameDataValues;
	private AddressDataValues addressDataValues;
	private ContentDataValues contentDataValues;

	public DataFactory(Locale locale)
	{
		this.locale = locale;
	}

	public DataFactory(String locale)
	{
		this.locale = new Locale(locale);
	}

	public DataFactory()
	{
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
	public <T> T getItem(List<T> items)
	{
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
	public <T> T getItem(List<T> items, int probability)
	{
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
	public <T> T getItem(List<T> items, int probability, T defaultItem)
	{
		if (items == null) {
			throw new IllegalArgumentException("Item list cannot be null");
		}
		if (items.isEmpty()) {
			throw new IllegalArgumentException("Item list cannot be empty");
		}

		return chance(probability) ? items.get(random.nextInt(items.size())) : defaultItem;
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
	public <T> T getItem(T[] items)
	{
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
	public <T> T getItem(T[] items, int probability)
	{
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
	public <T> T getItem(T[] items, int probability, T defaultItem)
	{
		if (items == null) {
			throw new IllegalArgumentException("Item array cannot be null");
		}
		if (items.length == 0) {
			throw new IllegalArgumentException("Item array cannot be empty");
		}
		return chance(probability) ? items[random.nextInt(items.length)] : defaultItem;
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
	public boolean chance(int chance)
	{
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
	public void randomize(int seed)
	{
		random = new Random(seed);
	}

	// address data
	private void initAddressDataValues()
	{
		if (addressDataValues == null) {
			addressDataValues = new RBAddressDataValues(locale);
		}
	}

	public AddressDataValues getAddressDataValues()
	{
		initAddressDataValues();
		return addressDataValues;
	}

	public String getStreetName()
	{
		return getItem(getAddressDataValues().getStreetNames());
	}

	public String getStreetSuffix()
	{
		return getItem(getAddressDataValues().getAddressSuffixes());
	}

	public String getCity()
	{
		return getItem(getAddressDataValues().getCities());
	}

	// content data
	public String getRandomUnicode()
	{
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

	private void initContentDataValues()
	{
		if (contentDataValues == null) {
			contentDataValues = new RBContentDataValues(locale);
		}
	}

	public ContentDataValues getContentDataValues()
	{
		initContentDataValues();
		return contentDataValues;
	}

	public static void main(String[] args)
	{
		DataFactory df = new DataFactory(new Locale("zh", "CN"));
		for (int i = 0; i < 50; i++) {
			// System.out.println(df.getCity() + df.getStreetSuffix()
			// + df.getStreetName());
			System.out.print(df.getRandomUnicode());
		}
	}
}
