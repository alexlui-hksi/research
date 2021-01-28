package org.hksi.research.core.util;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.extern.log4j.Log4j2;

/**
 * Common utilities for string operations 
 * 
 * @author Alex Lui
 *
 */
@Log4j2
public class LocalStringUtils {
	public final static String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public final static String SMALL_LETTERS = "abcdefghijklmnopqrstuvwxyz";
	public final static String DIGITS = "0123456789";
	public final static String ALPHANUMERIC = CAPITAL_LETTERS+SMALL_LETTERS+DIGITS;

	/**
	 * 
	 * @param minLength
	 * @param range
	 * @param charset
	 * @return
	 */
	public static String generateRandomString(@Min(1)final int minLength, @Min(0)final int range, @NotEmpty final String charset) {
		log.traceEntry("generateRandomString; minLength={}, range={}, charset={}", minLength, range, charset);
		final StringBuffer sb = new StringBuffer();
		final int length = range==0?minLength:NumberUtils.getRandomInteger(minLength, range);
		log.debug("generateRandomString; string lenght={}", length);
		for(int i=length; i>0; i--) {
			sb.append(charset.charAt(NumberUtils.getRandomInteger(charset.length())));
		}
		return log.traceExit("generateRandomString; {}", sb.toString());
	}
}
