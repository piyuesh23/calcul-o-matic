package com.calculomatic;

public class Event {
	private long id;
	private String event;
	private String place;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
	public String getPlace() {
		return place;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return event;
	}

}
