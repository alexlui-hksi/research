package org.hksi.research.core.util;

import java.security.SecureRandom;

import javax.validation.constraints.Min;

import lombok.extern.log4j.Log4j2;

/**
 * 
 * 
 * @author Alex Lui
 *
 */
@Log4j2
public class NumberUtils {
	private static SecureRandom secureRandom;

	/**
	 * Generate a random integer in set [begin, begin+(range-1)*step).
	 * e.g. begin=4, setSize=5, stepSize=3 => an integer will be randomly picked from set [4, 7, 10, 13, 16].
	 * 
	 * @param begin	the minimum integer
	 * @param setSize	number of integers in the set
	 * @param stepSize	the size of a step
	 * @return
	 */
	public static int getRandomInteger(final int begin, final int setSize, final int stepSize) {
		log.traceEntry("getRandomInteger; begin={}, setSize={}, stepSize={}", begin, setSize, stepSize);
		int result;
		if(setSize<1) {
			throw new IllegalArgumentException("setSize cannot be less than 1");
		} else if(setSize==1) {
			result = begin;
		} else {
			if(stepSize<1) {
				throw new IllegalArgumentException("stepSize cannot be less than 1");
			}
			result = begin + ((int)(Math.random()*setSize))*stepSize;
		}
		return log.traceExit("getRandomInteger; {}", result);
	}
	/**
	 * Alias of getRandomInteger(begin, range, 1);
	 * 
	 * @param begin
	 * @param range
	 * @return
	 */
	public static int getRandomInteger(final int begin, final int range) {
		return getRandomInteger(begin, range, 1);
	}
	/**
	 * Alias of getRandomInteger(0, cap, 1);
	 * 
	 * @param cap
	 * @return
	 */
	public static int getRandomInteger(final int cap) {
		return getRandomInteger(0, cap, 1);
	}

	public static int getSecureRandomInteger(final int begin, @Min(2) final int setSize, @Min(1) final int stepSize) {
		log.traceEntry("getSecureRandomInteger; begin={}, setSize={}, stepSize={}", begin, setSize, stepSize);
		final int result = begin + getSecureRandom().nextInt(setSize)*stepSize;
		return log.traceExit("getSecureRandomInteger; {}", result);
	}
	public static int getSecureRandomInteger(final int begin, final int range) {
		return getSecureRandomInteger(begin, range, 1);
	}
	public static int getSecureRandomInteger(final int cap) {
		return getSecureRandomInteger(0, cap, 1);
	}

	private static SecureRandom getSecureRandom () {
		if(secureRandom==null) {
			secureRandom = new SecureRandom(); 
		}
		return secureRandom;
	}
}
