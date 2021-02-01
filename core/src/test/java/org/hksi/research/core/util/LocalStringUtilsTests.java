package org.hksi.research.core.util;

import static org.junit.Assert.*;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.databene.contiperf.junit.ParallelRunner;
import org.databene.contiperf.timer.RandomTimer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RunWith(ParallelRunner.class)
public class LocalStringUtilsTests {
	private final static String CHARSET = LocalStringUtils.ALPHANUMERIC+"!@#$%^&*()";
	private final static String CHINESE_CHARS = "甲乙丙丁戊己庚辛壬癸子丑寅卯辰巳午未申酉戌亥";
	@Rule
    public ContiPerfRule rule = new ContiPerfRule();

	static {
		log.debug("CHARSET={}", CHARSET);
	}

	@Test
	@PerfTest(invocations = 300, threads = 100, timer = RandomTimer.class, timerParams = { 5, 100 })
	@Required(throughput = 10)
	public void testGenerateRandomString() {
		log.traceEntry("testGenerateRandomString;");
		final int minLength = NumberUtils.getRandomInteger(10);
		final String strRandom = LocalStringUtils.generateRandomString(minLength, 100-minLength, CHARSET);
		assertTrue(strRandom.length()>=minLength);
		assertTrue(strRandom.length()<100);
		for(int i=0; i<strRandom.length(); i++) {
			assertNotEquals(-1, CHARSET.indexOf(strRandom.charAt(i)));
		}
		log.traceExit("testGenerateRandomString;");
	}

	@Test
	@PerfTest(invocations = 300, threads = 100, timer = RandomTimer.class, timerParams = { 5, 100 })
	@Required(throughput = 10)
	public void testIsChineseChar() {
		log.traceEntry("testIsChineseChar;");
		assertTrue(LocalStringUtils.isChineseChar(CHINESE_CHARS.charAt(NumberUtils.getRandomInteger(CHINESE_CHARS.length()))));
		assertFalse(LocalStringUtils.isChineseChar(CHARSET.charAt(NumberUtils.getRandomInteger(CHARSET.length()))));
		log.traceExit("testIsChineseChar;");
	}

	@Test
	@PerfTest(invocations = 300, threads = 100, timer = RandomTimer.class, timerParams = { 5, 100 })
	@Required(throughput = 10)
	public void testHasChineseChar() {
		log.traceEntry("testHasChineseChar;");
		final StringBuffer sbTest = new StringBuffer(LocalStringUtils.generateRandomString(10, 10, CHARSET));
		log.debug("testHasChineseChar; string without chinese charater: {}", sbTest);
		assertFalse(LocalStringUtils.hasChineseCharater(sbTest.toString()));
		final String strWithChineseCharacter = sbTest.insert(NumberUtils.getRandomInteger(sbTest.length()+1), CHINESE_CHARS.charAt(NumberUtils.getRandomInteger(CHINESE_CHARS.length()))).toString();
		log.debug("testHasChineseChar; strWithChineseCharacter={}", strWithChineseCharacter);
		assertTrue(LocalStringUtils.hasChineseCharater(strWithChineseCharacter));
		log.traceExit("testHasChineseChar;");
	}
}
