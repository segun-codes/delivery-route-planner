package com.geobyte.lcmsbe.util;

public class RouteQuery {
	private String origin;
	private String destination;

	public RouteQuery() {}

	public RouteQuery(String origin, String destination) {
		this.origin = origin;
		this.destination = destination;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "RouteQuery [origin=" + origin + ", destination=" + destination + "]";
	}
}
