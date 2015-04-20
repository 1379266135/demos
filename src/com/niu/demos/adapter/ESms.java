package com.niu.demos.adapter;

public class ESms {

	private long id;
	private String name;
	private String addr;
	private String body;
	private long date;

	// private int reportStatus = ReportStatus.STATUS_UNREPORT;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	// public int getReportStatus() {
	// return reportStatus;
	// }
	//
	// public void setReportStatus(int reportStatus) {
	// this.reportStatus = reportStatus;
	// }

}
