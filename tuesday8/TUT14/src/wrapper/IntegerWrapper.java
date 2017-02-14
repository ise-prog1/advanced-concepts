package wrapper;

public class IntegerWrapper {

	
	/**
	 * Returns the parsed result or null if not a number
	 * @param s - the String to be parsed as a number
	 * @return the number or null
	 */
	public static Integer parseIntOrNull(String s) {
		Integer result = null;
		try {
			result = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return null;
		}
		return result;
	}
}
