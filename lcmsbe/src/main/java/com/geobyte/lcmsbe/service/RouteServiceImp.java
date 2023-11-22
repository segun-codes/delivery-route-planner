package com.geobyte.lcmsbe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geobyte.lcmsbe.dao.RouteDAO;
import com.geobyte.lcmsbe.entity.Route;

import jakarta.transaction.Transactional;



@Service
public class RouteServiceImp implements RouteService {
	private RouteDAO routeDao;
	
	
	@Autowired
	public RouteServiceImp(RouteDAO routeDao) {
		this.routeDao = routeDao;
	}

	@Override
	@Transactional
	public Route save(Route route) {
		return routeDao.save(route);
	}

	@Override
	@Transactional
	public Route update(Route route) {
		routeDao.update(route);
		
		return null;
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		routeDao.deleteById(id);
	}

	@Override
	public Route findById(long id) {
		return routeDao.findById(id);
	}

	@Override
	public List<Route> findAll() {
		return routeDao.findAll();
	}

	@Override
	public List<Route> findAllRoutesWithDeliveryCost() {
		return routeDao.findAllRoutesWithDeliveryCost();
	}

	@Override
	public double getDistanceById(long routeId) {
		Route route = findById(routeId);
		return route.getDistance();
	}

	@Override
	public Route getBestRoute(String origin, String destination) {		
		return routeDao.getBestRoute(origin, destination);
	}

	@Override
	public Route getMostExpensiveRoute(String origin, String destination) {
		return routeDao.getMostExpensiveRoute(origin, destination);
		
	}
}
