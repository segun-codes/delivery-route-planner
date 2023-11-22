package com.geobyte.lcmsbe.service;


import java.util.List;

import com.geobyte.lcmsbe.entity.Route;



public interface RouteService {
	public Route save(Route route);
	public Route update(Route route);
	public void deleteById(long id);
	public Route findById(long id);
	public List<Route> findAll();
	public double getDistanceById(long routeId);
	public List<Route> findAllRoutesWithDeliveryCost();
	public Route getBestRoute(String origin, String destination);
	public Route getMostExpensiveRoute(String origin, String destination);
}
