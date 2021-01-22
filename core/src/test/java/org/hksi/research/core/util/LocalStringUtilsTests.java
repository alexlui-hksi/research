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

}
