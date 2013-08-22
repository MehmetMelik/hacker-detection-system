/**
 * 
 */
package com.sky.detector;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.sky.detector.model.LoginInfo;

/**
 * @author Mehmet Melik Kose
 * 
 * 
 */

public class LogProcessor implements HackerDetector {
	private static Logger logger = Logger.getLogger(LogProcessor.class);

	private HackerDetectorConfiguraion configuration;
	private Queue<LoginInfo> loginInfoQueue;
	private LoginInfo lastLogin;
	
	

	/**
	 * initialize <code>LogProcessor</code> instance
	 * @throws IOException
	 */
	public void init() throws IOException {
		logger.debug("initializing hacker detector application");
		loginInfoQueue = new ConcurrentLinkedQueue<LoginInfo>();
		configuration = new HackerDetectorConfiguraion();
		configuration.init();
		
		new Thread(){
			public void run(){
				while(true){
					try {
						if (lastLogin != null) {
							while (loginInfoQueue.peek() != null
									&& loginInfoQueue.element().getTime() < (lastLogin.getTime() - configuration.getInspectFailedLoginInterval())) {
								
								loginInfoQueue.remove();
							}
						}
						Thread.sleep(1000*10);
					} catch (Exception e) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e1) {
							logger.debug("Thread sleep.", e1);
						}
						logger.debug("Exception in queue clean up thread", e);
					}
					
				}
			}
		}.start();
		logger.debug("hacker detector application has been initialized successfully");
	}

	public synchronized String parseLine(String line) {
		// a log line looks like this:
		// 80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning
		String[] logLine;
		String delimiter = ",";
		logLine = line.split(delimiter);
		String ip = logLine[0];
		Long time = Long.parseLong(logLine[1]);
		String success = logLine[2];
		
		if (success.equalsIgnoreCase(configuration.getSigninSuccess())) {
			return null;
		} else {
			LoginInfo loginInfo = new LoginInfo(ip, Long.parseLong(logLine[1]), logLine[2], logLine[3]);

			// queue failed attempt
			loginInfoQueue.offer(loginInfo);
			lastLogin = loginInfo;
			int count = 0;
			// count failed attempts
			for (LoginInfo i : loginInfoQueue) {
				if (i.getIp().equals(ip) && i.getTime()> (time-configuration.getInspectFailedLoginInterval()) ) {
					count++;
				}
			}
			if (count >= configuration.getSuspiciousAttemptCount())
				return ip;
			else
				return null;
		}
	}
}
