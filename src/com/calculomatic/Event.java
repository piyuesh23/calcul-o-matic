package com.calculomatic;

public class Event {
	private long id;
	private String event;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return event;
	}

	public void setComment(String event) {
		this.event = event;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return event;
	}

}
