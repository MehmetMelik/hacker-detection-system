/**
 * 
 */
package com.sky.detector;

/**
 * @author Melik
 * 
 */
public interface HackerDetector {

	/**
	 * The first detection method will be to identify a single IP address that
	 * has attempted a failed login
	 * {@link HackerDetectorConfiguraion#getSuspiciousAttemptCount()} or more
	 * times within
	 * {@link HackerDetectorConfiguraion#getInspectFailedLoginInterval()} second
	 * period
	 * 
	 * @param line
	 *            be in "ip,date,action,username" format. Such as
	 *            "80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning"
	 * @return the IP address if any suspicious activity is identified or null
	 *         if the activity appears to be normal
	 */
	public String parseLine(String line);
}
