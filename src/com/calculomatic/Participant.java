package com.calculomatic;

public class Participant {
	private long uid;
	private long eid;
	private Integer amount;
	
	public long getEid() {
		return eid;
	}
	
	public void setEid(long eid) {
		this.eid = eid;
	}
	
	public Integer getamount() {
		return amount;
	}
	
	public void setamount(Integer amount) {
		this.amount = amount;
	}
	
	public long getuid() {
		return uid;
	}
	
	public void setuid(long uid) {
		this.uid = uid;
	}
}
