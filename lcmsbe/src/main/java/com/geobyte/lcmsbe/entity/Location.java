package com.geobyte.lcmsbe.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="locations")
public class Location {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="location_id")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="clearing_cost")
	private BigDecimal clearingCost;

	
	public Location() {}

	public Location(long id, String name, BigDecimal clearingCost) {
		this.id = id;
		this.name = name;
		this.clearingCost = clearingCost;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getClearingCost() {
		return clearingCost;
	}

	public void setClearingCost(BigDecimal clearingCost) {
		this.clearingCost = clearingCost;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", name=" + name + ", clearingCost=" + clearingCost + "]";
	}
}
