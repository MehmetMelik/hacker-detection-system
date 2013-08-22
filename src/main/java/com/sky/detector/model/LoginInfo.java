/**
 * 
 */
package com.sky.detector.model;

/**
 * @author Melik
 *
 */
public class LoginInfo {
	private String ip;
	private Long time;
	private String  action;
	private String username;
	
	/**
	 * 
	 * @param ip
	 * @param time
	 * @param action
	 * @param username
	 */
	public LoginInfo(String ip, Long time, String action, String username) {
		super();
		this.ip = ip;
		this.time = time;
		this.action = action;
		this.username = username;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
