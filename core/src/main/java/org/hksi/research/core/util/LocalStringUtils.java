package org.hksi.research.core.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.extern.log4j.Log4j2;

/**
 * 
 * 
 * @author Alex Lui
 *
 */
@Log4j2
public class LocalStringUtils {
	private static final Pattern CHINESE_CHAR_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");

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
		for(int i=length; i>0; i--) {
			sb.append(charset.charAt(NumberUtils.getRandomInteger(charset.length())));
		}
		return log.traceExit("generateRandomString; {}", sb.toString());
	}

	/**
	 * To check whether a string has any Chinese character(s)
	 * 
	 * @param isoString
	 *            the string to be checked
	 * @return true if isoString has at least one Chinese character; otherwise,
	 *         false
	 */
	public static boolean hasChineseCharater(String isoString) {
		log.traceEntry("hasChineseCharater; isoString={}", isoString);
		final Matcher m = CHINESE_CHAR_PATTERN.matcher(isoString);
		final boolean result = m.find();

		return log.traceExit("hasChineseCharater; result={}", result);
	}

	/**
	 * Check if a character is a Chinese character.
	 * 
	 * @param c
	 *            the character to be checked
	 * @return true if character c is a Chinese character; otherwise, false.
	 */
	public static boolean isChineseChar(char c) {
		log.traceEntry("isChineseChar; char={}", c);
		final Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		final boolean result = (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);

		return log.traceExit("isChineseChar; result={}", result);
	}

	/**
	 * Converts each byte in the input array into a 2-digit hexadecimal number
	 * and concatenates them into a string
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHexString(final byte[] bytes) {
		if(log.isTraceEnabled())	log.traceEntry("bytesToHexString; bytes={}", Arrays.toString(bytes));
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(byteToHexString(bytes[i]));
		}

		return log.traceExit("bytesToHexString; str={}", sb.toString());

	}

	public static String byteToHexString(final byte b) {
		return Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3);
	}

	/**
	 * Converts hexadecimal string to a byte array, with each byte corresponds
	 * to a 2-digit hexadecimal in the string
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] hexStringToBytes(final String hex) {
		final int size = hex.length() / 2;
		final byte[] bytes = new byte[size];

		for (int i = 0; i < size; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, (i + 1) * 2), 16);
		}
		return bytes;

	}

}
