package com.geobyte.lcmsbe.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geobyte.lcmsbe.entity.Route;

import jakarta.persistence.EntityManager;


@Repository
public class RouteDAOImp implements RouteDAO {
	private EntityManager entityManager;
	
	@Autowired
	public RouteDAOImp(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	// Note, merge will operate as persist() if routeId is 0
	@Override
	public Route save(Route route) {
		return entityManager.merge(route); 
	}

	@Override
	public Route update(Route route) {		
		return entityManager.merge(route);
	}

	@Override
	public void deleteById(long id) {
		Route route = entityManager.find(Route.class, id);
		entityManager.remove(route);
	}

	@Override
	public Route findById(long id) {
		return entityManager.find(Route.class, id);
	}

	@Override
	public List<Route> findAll() {
		return entityManager.createQuery("FROM Route", Route.class).getResultList();
	}
	
	@Override
	public List<Route> findAllRoutesWithDeliveryCost() {
		return entityManager.createQuery("SELECT r FROM Route r WHERE r.totalDeliveryCost IS NOT NULL", Route.class).getResultList();
	}

	/**
	 * Best route refers to the lowest distance cost and clearing cost
	 * 
	 */
	@Override
	public Route getBestRoute(String origin, String destination) {		
		String query1 = "SELECT rt FROM Route rt";
		List<Route> routes = entityManager.createQuery(query1, Route.class).getResultList();
		
		List<Route> requiredRoutes = new ArrayList<>();
		
		for(Route route: routes) {
			boolean startsOrEndwithOrigin = route.getRouteName().startsWith(origin) || route.getRouteName().endsWith(origin);
			boolean startsOrEndwithDest  = route.getRouteName().endsWith(destination) || route.getRouteName().startsWith(destination);
			
			if (origin != destination && startsOrEndwithOrigin & startsOrEndwithDest) {
				requiredRoutes.add(route);
			}
		}
		
		int i = 0;
		Route bestRoute = null;
		
		for(Route requiredRoute: requiredRoutes) {
			if (i == 0) {
				bestRoute = requiredRoute;
			} else {
				if (requiredRoute.getDistance() <= bestRoute.getDistance()) {				
					if ((requiredRoute.getTotalDeliveryCost().compareTo(bestRoute.getTotalDeliveryCost())) <= 1) {
						bestRoute = requiredRoute; 
					}
				}
			}
			
			++i;
		}
					
		return bestRoute;
	}

	@Override
	public Route getMostExpensiveRoute(String origin, String destination) {
		Route mostExpensiveRoute = null;
		String queryStmt = "SELECT r FROM Route r "
				+ "WHERE r.distance=(SELECT MAX(rt.distance) FROM Route rt)"
				+ "AND r.totalDeliveryCost=(SELECT MAX(rtt.totalDeliveryCost) FROM Route rtt)";
		
		try {

			mostExpensiveRoute = entityManager.createQuery(queryStmt, Route.class).getSingleResult();
		} catch(Exception e) {
			System.out.println("Log duplicate max error: " + e);
		}

		return mostExpensiveRoute;
	}
}
