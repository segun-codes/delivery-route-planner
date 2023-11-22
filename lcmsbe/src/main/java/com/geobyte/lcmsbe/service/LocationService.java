package com.geobyte.lcmsbe.service;

import java.math.BigDecimal;
import java.util.List;

import com.geobyte.lcmsbe.entity.Location;



public interface LocationService {
	public Location save(Location location);
	public Location update(Location location);
	public void deleteById(long id);
	public Location findById(long id);
	public List<Location> findAll();
	public long getCountOfLocationById();
	public BigDecimal getClearingCostById(long id);
	public Location getLocationByName(String locationName);
}
