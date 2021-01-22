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
public class NumberUtilsTests {
	private final static long MAX_INTEGER_RANGE = (long)Integer.MAX_VALUE - Integer.MIN_VALUE;
	@Rule
    public ContiPerfRule rule = new ContiPerfRule();

	static {
		log.debug("MAX_INTEGER_RANGE={}", MAX_INTEGER_RANGE);
	}

	@Test
	@PerfTest(invocations = 300, threads = 100, timer = RandomTimer.class, timerParams = { 5, 100 })
	@Required(throughput = 10)
	public void testConcurrentGetRandomInteger() {
		log.traceEntry("testConcurrentGetRandomInteger;");
		final int begin = (int)(Math.random()*10);	//Integer.MIN_VALUE + (int)(Math.random()*MAX_INTEGER_RANGE);
		final int stepSize = 1+(int)(Math.random()*10);	//(int)(Math.random()*10)+1;
		final int setSize = (int)(Math.random()*100) + 10;	//(int)(Math.random()*(((long)Integer.MAX_VALUE - begin)/setSize-2))+2;
		final int random = NumberUtils.getRandomInteger(begin, setSize, stepSize);
//		log.info("testGetRandomInteger; begin={}, step={}, numOfSteps={}, random={}", begin, step, numOfSteps, random);
		assertTrue(random>=begin);
		assertTrue(random<=begin+(setSize-1)*stepSize);
		assertEquals(0, (random-begin)%stepSize);
		log.traceExit("testConcurrentGetRandomInteger;");
	}

	@Test
	public void testGetRandomInteger() {
		log.traceEntry("testGetRandomInteger;");
		final int begin = (int)(Math.random()*10);	//Integer.MIN_VALUE + (int)(Math.random()*MAX_INTEGER_RANGE);
		final int stepSize = 1+(int)(Math.random()*10);	//(int)(Math.random()*10)+1;
		final int setSize = (int)(Math.random()*100) + 10;	//(int)(Math.random()*(((long)Integer.MAX_VALUE - begin)/setSize-2))+2;
		try {
			NumberUtils.getRandomInteger(begin, 1, stepSize);
			fail("setSize should not be equal to or less than 1");
		} catch(Exception e) {
			log.debug("getRandomInteger");
		}
		try {
			NumberUtils.getRandomInteger(begin, setSize, 0);
			fail("stepSize should not be equal to or less than 0");
		} catch(Exception e) {
			log.debug("getRandomInteger");
		}
		log.traceExit("testGetRandomInteger;");		
	}
}
