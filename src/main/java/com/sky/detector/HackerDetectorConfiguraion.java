package com.sky.detector;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * @author Melik
 * 
 *         <code>HackerDetectorConfiguraion</code> manages the configurable
 *         system values
 * 
 */
public class HackerDetectorConfiguraion {
	private static Logger logger = Logger
			.getLogger(HackerDetectorConfiguraion.class);

	private long inspectFailedLoginInterval; // 300L; // 5 * 60 seconds
	private long suspiciousAttemptCount;
	private String signinSuccess;
	private String signinFailure;

	/**
	 * load the fields' values
	 * 
	 * @throws IOException
	 */
	public void init() throws IOException {
		logger.debug("loading configuration file ...");

		Properties properties = new Properties();
		properties.load(this.getClass().getClassLoader()
				.getResourceAsStream(("application.properties")));

		inspectFailedLoginInterval = Long.parseLong(properties
				.getProperty("inspectFailedLoginInterval"));
		suspiciousAttemptCount = Long.parseLong(properties
				.getProperty("suspiciousAttemptCount"));
		signinSuccess = properties.getProperty("signinSuccess");
		signinFailure = properties.getProperty("signinFailure");
	}

	public String getSigninSuccess() {
		return signinSuccess;
	}

	public void setSigninSuccess(String signinSuccess) {
		this.signinSuccess = signinSuccess;
	}

	public String getSigninFailure() {
		return signinFailure;
	}

	public void setSigninFailure(String signinFailure) {
		this.signinFailure = signinFailure;
	}

	public long getSuspiciousAttemptCount() {
		return suspiciousAttemptCount;
	}

	public void setSuspiciousAttemptCount(long suspiciousAttemptCount) {
		this.suspiciousAttemptCount = suspiciousAttemptCount;
	}

	public long getInspectFailedLoginInterval() {
		return inspectFailedLoginInterval;
	}

	public void setInspectFailedLoginInterval(long inspectFailedLoginInterval) {
		this.inspectFailedLoginInterval = inspectFailedLoginInterval;
	}

}
