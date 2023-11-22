package com.geobyte.lcmsbe.util;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.geobyte.lcmsbe.entity.Route;


@Component
public class RouteAnalyticsReport {
	private RouteDetails bestRouteDetails;           // in terms of shortest distance and lowest cost
	private Route mostExpensiveRoute;  // in terms of longest distance and highest cost
	private BigDecimal totalDeliveryCostForBestRoute;
	private BigDecimal highestDeliveryCost;

	public RouteAnalyticsReport() {}

	public RouteAnalyticsReport(RouteDetails bestRouteDetails, Route mostExpensiveRoute,
			BigDecimal totalDeliveryCostForBestRoute, BigDecimal highestDeliveryCost) {
		this.bestRouteDetails = bestRouteDetails;
		this.mostExpensiveRoute = mostExpensiveRoute;
		this.totalDeliveryCostForBestRoute = totalDeliveryCostForBestRoute;
		this.highestDeliveryCost = highestDeliveryCost;
	}

	public RouteDetails getBestRoute() {
		return bestRouteDetails;
	}

	public void setBestRoute(RouteDetails bestRouteDetails) {
		this.bestRouteDetails = bestRouteDetails;
	}

	public Route getMostExpensiveRoute() {
		return mostExpensiveRoute;
	}

	public void setMostExpensiveRoute(Route mostExpensiveRoute) {
		this.mostExpensiveRoute = mostExpensiveRoute;
	}

	public BigDecimal getTotalDeliveryCostForBestRoute() {
		return totalDeliveryCostForBestRoute;
	}

	public void setTotalDeliveryCostForBestRoute(BigDecimal totalDeliveryCostForBestRoute) {
		this.totalDeliveryCostForBestRoute = totalDeliveryCostForBestRoute;
	}

	public BigDecimal getHighestDeliveryCostForMostExpensiveRoute() {
		return highestDeliveryCost;
	}

	public void setHighestDeliveryCostForMostExpensiveRoute(BigDecimal highestDeliveryCost) {
		this.highestDeliveryCost = highestDeliveryCost;
	}

	@Override
	public String toString() {
		return "DeliveryRouteAnalysis [RouteDetails=" + bestRouteDetails + ", mostExpensiveRoute=" + mostExpensiveRoute
				+ ", totalDeliveryCostForBestRoute=" + totalDeliveryCostForBestRoute
				+ ", highestDeliveryCost=" + highestDeliveryCost + "]";
	}
}