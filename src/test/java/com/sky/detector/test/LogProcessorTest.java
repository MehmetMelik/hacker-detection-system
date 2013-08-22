package com.sky.detector.test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.sky.detector.LogProcessor;

public class LogProcessorTest {
	
	private LogProcessor logProcessor;
	
	@Before
	public void setup() throws IOException {
		logProcessor = new LogProcessor();
		logProcessor.init();
		
		// put some data
		logProcessor.parseLine("80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning");
		logProcessor.parseLine("80.238.10.120,133612997,SIGNIN_FAILURE,Some.One");
		logProcessor.parseLine("80.238.10.120,133613097,SIGNIN_FAILURE,Some.One");
		logProcessor.parseLine("80.238.10.120,133613110,SIGNIN_FAILURE,Some.One");
		logProcessor.parseLine("80.238.10.120,133613180,SIGNIN_FAILURE,Some.One");
	}
	
	@Test
	public void shouldReturnIPIfAnySuspiciousActivityIdentified(){
		String testIp = "80.238.10.120";
		String result = logProcessor.parseLine(testIp + ",133613239,SIGNIN_FAILURE,Some.One");
		assertTrue("Suspicious activity has to be found. expected value is " + testIp + " but result is " + result, testIp.equals(result) );
	}

	@Test
	public void shouldNotReturnIPIfFailedAttemptIsAfterInterval(){
		String testIp = "80.238.10.120";
		String result = logProcessor.parseLine(testIp + ",133613298,SIGNIN_FAILURE,Some.One");
		assertNull("Suspicious activity has to be not found. expected value is null but result is " + result, result );
	}
	
	@Test
	public void shouldReturnNullIfTheActivityAppearsToBeNormal(){
		String testIp = "80.238.10.120";
		String result = logProcessor.parseLine(testIp + ",133613250,SIGNIN_SUCCESS,Some.One");
		assertNull("login success. Null value has returned. ", result );
	}
	
	@Test
	public void shouldReturnAllIPsSuspiciousActivityIdentified() {
		String testIP1 = "80.238.10.130";
		String testIP2 = "80.238.10.140";
		String testIP3 = "80.238.10.150";
		String testIP4 = "80.238.10.160";
		logProcessor.parseLine(testIP1+",133622948,SIGNIN_FAILURE,Some.Two");
		logProcessor.parseLine(testIP2+",133622949,SIGNIN_FAILURE,Some.Three");
		logProcessor.parseLine(testIP3+",133622950,SIGNIN_FAILURE,Some.Four");
		logProcessor.parseLine(testIP4+",133622951,SIGNIN_FAILURE,Some.Five");
		
		logProcessor.parseLine(testIP1+",133622998,SIGNIN_FAILURE,Some.Two");
		logProcessor.parseLine(testIP2+",133622999,SIGNIN_FAILURE,Some.Three");
		logProcessor.parseLine(testIP3+",133623000,SIGNIN_FAILURE,Some.Four");
		logProcessor.parseLine(testIP4+",133623001,SIGNIN_FAILURE,Some.Five");
		
		logProcessor.parseLine(testIP1+",133623098,SIGNIN_FAILURE,Some.Two");
		logProcessor.parseLine(testIP2+",133623099,SIGNIN_FAILURE,Some.Three");
		logProcessor.parseLine(testIP3+",133623100,SIGNIN_FAILURE,Some.Four");
		logProcessor.parseLine(testIP4+",133623101,SIGNIN_FAILURE,Some.Five");
		
		logProcessor.parseLine(testIP4+",133623108,SIGNIN_FAILURE,Some.Five");
		logProcessor.parseLine(testIP3+",133623109,SIGNIN_FAILURE,Some.Four");
		logProcessor.parseLine(testIP2+",133623110,SIGNIN_FAILURE,Some.Three");
		logProcessor.parseLine(testIP1+",133623111,SIGNIN_FAILURE,Some.Two");

		String result1 = logProcessor.parseLine(testIP1+",133623179,SIGNIN_FAILURE,Some.Two");
		String result2 = logProcessor.parseLine(testIP2+",133623180,SIGNIN_FAILURE,Some.Three");
		String result3 = logProcessor.parseLine(testIP3+",133623181,SIGNIN_FAILURE,Some.Four");
		String result4 = logProcessor.parseLine(testIP4+",133623182,SIGNIN_FAILURE,Some.Five");
		
		assertTrue("Suspicious activity has to be found. expected value is " + testIP1 + " but result is " + result1, testIP1.equals(result1) );
		assertTrue("Suspicious activity has to be found. expected value is " + testIP2 + " but result is " + result1, testIP2.equals(result2) );
		assertTrue("Suspicious activity has to be found. expected value is " + testIP3 + " but result is " + result1, testIP3.equals(result3) );
		assertTrue("Suspicious activity has to be found. expected value is " + testIP4 + " but result is " + result1, testIP4.equals(result4) );

	}
	
}
