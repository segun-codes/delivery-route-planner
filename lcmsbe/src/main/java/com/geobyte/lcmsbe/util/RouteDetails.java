package com.geobyte.lcmsbe.util;

import java.util.List;

import com.geobyte.lcmsbe.entity.Location;
import com.geobyte.lcmsbe.entity.Route;

public class RouteDetails {
	private Route bestRroute;
	private String instruction;
	private List<Location> routeInsequence;
	
	public RouteDetails() {}

	public RouteDetails(Route bestRroute, String instruction, List<Location> routeInsequence) {
		this.bestRroute = bestRroute;
		this.instruction = instruction;
		this.routeInsequence = routeInsequence;
	}

	public Route getRoute() {
		return bestRroute;
	}

	public void setRoute(Route bestRroute) {
		this.bestRroute = bestRroute;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public List<Location> getRouteInsequence() {
		return routeInsequence;
	}

	public void setRouteInsequence(List<Location> routeInsequence) {
		this.routeInsequence = routeInsequence;
	}

	@Override
	public String toString() {
		return "RouteDetails [bestRroute=" + bestRroute + ", instruction=" + instruction + ", routeInsequence="
				+ routeInsequence + "]";
	}
}
