package com.geobyte.lcmsbe.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.math.BigDecimal;

//import jakarta.validation.constraints.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * Todo: Remember to apply validation constraints on the field @Email @NotNull etc
 */
@Entity
@Table(name="routes")
public class Route {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="route_id")
	private long routeId;
	
	@Column(name="route_name")
	private String routeName;
	
	@Column(name="distance")
	private double distance;
	
	@Column(name="total_delivery_cost")
	private BigDecimal totalDeliveryCost;
	
	public Route() {}

	public Route(String routeName, double distance, BigDecimal totalDeliveryCost) {
		this.routeName = routeName;
		this.distance = distance;
		this.totalDeliveryCost = totalDeliveryCost;
	}

	public long getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public double getDistance() {
		return distance;
	}

	public void setLastName(double distance) {
		this.distance = distance;
	}

	public BigDecimal getTotalDeliveryCost() {
		return totalDeliveryCost;
	}

	public void setTotalDeliveryCost(BigDecimal totalDeliveryCost) {
		this.totalDeliveryCost = totalDeliveryCost;
	}
	
	

	public void setRouteId(long routeId) {
		this.routeId = routeId;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Route [routeId=" + routeId + ", routeName=" + routeName + ", distance=" + distance
				+ ", totalDeliveryCost=" + totalDeliveryCost + "]";
	}
	
	
}
