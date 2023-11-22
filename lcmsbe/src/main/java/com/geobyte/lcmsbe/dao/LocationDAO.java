package com.geobyte.lcmsbe.dao;

import java.util.List;

import com.geobyte.lcmsbe.entity.Location;


public interface LocationDAO {
	public Location save(Location location);
	public Location update(Location location);
	public void deleteById(long id);
	public Location findById(long id);
	public List<Location> findAll();
	public long getCountOfLocationById();
	public Location getLocationByName(String locationName);
}
