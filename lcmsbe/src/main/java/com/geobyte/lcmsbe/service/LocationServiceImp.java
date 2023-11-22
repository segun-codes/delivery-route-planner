package com.geobyte.lcmsbe.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geobyte.lcmsbe.dao.LocationDAO;
import com.geobyte.lcmsbe.entity.Location;

import jakarta.transaction.Transactional;



@Service
public class LocationServiceImp implements LocationService {
	private LocationDAO locationDao;
		
	@Autowired
	public LocationServiceImp(LocationDAO locationDao) {
		this.locationDao = locationDao;
	}

	@Override
	@Transactional
	public Location save(Location location) {
		Location savedLocation = locationDao.save(location);
		return savedLocation;
	}

	@Override
	@Transactional
	public Location update(Location location) {
		locationDao.update(location);
		
		return null;
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		locationDao.deleteById(id);
	}

	@Override
	public Location findById(long id) {
		return locationDao.findById(id);
	}

	@Override
	public List<Location> findAll() {
		return locationDao.findAll();
	}
	
	public long getCountOfLocationById() {
		return locationDao.getCountOfLocationById();
	}

	@Override
	public BigDecimal getClearingCostById(long id) {
		Location location = findById(id);
		
		return location.getClearingCost();
	}

	@Override
	public Location getLocationByName(String locationName) {
		return locationDao.getLocationByName(locationName);
	}
}
