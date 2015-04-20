package com.niu.demos.preferences;

/**
 * 行为统计需要传递的参数， 这里封装为javaBean
 * 
 * @author Administrator
 * 
 */
public class ActionBean {

	private int count;
	private String acname;
	private String date;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getAcname() {
		return acname;
	}

	public void setAcname(String acname) {
		this.acname = acname;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
