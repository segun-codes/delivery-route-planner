package com.geobyte.lcmsbe.dao;

import java.util.List;

import com.geobyte.lcmsbe.entity.Route;


public interface RouteDAO {
	public Route save(Route route);
	public Route update(Route route);
	public void deleteById(long id);
	public Route findById(long id);
	public List<Route> findAll();
	public List<Route> findAllRoutesWithDeliveryCost();
	public Route getBestRoute(String origin, String destination);
	public Route getMostExpensiveRoute(String origin, String destination);
}
