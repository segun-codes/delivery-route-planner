package com.geobyte.lcmsbe.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geobyte.lcmsbe.entity.Location;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;


@Repository
public class LocationDAOImp implements LocationDAO {
	private EntityManager entityManager;
	
	@Autowired
	public LocationDAOImp(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	// Note, merge will operate as persist() if locationId is 0
	@Override
	public Location save(Location location) {
		return entityManager.merge(location); 
	}

	@Override
	public Location update(Location location) {		
		return entityManager.merge(location);
	}

	@Override
	public void deleteById(long id) {
		Location location = entityManager.find(Location.class, id);
		entityManager.remove(location);
	}

	@Override
	public Location findById(long id) {
		return entityManager.find(Location.class, id);
	}

	@Override
	public List<Location> findAll() {
		return entityManager.createQuery("FROM Location", Location.class).getResultList();
	}

	@Override
	public long getCountOfLocationById() {
		 Query query = entityManager.createQuery("SELECT COUNT(*) FROM Location ");
		 Long locationCount = (Long)query.getSingleResult();
		
		return locationCount;
	}

	@Override
	public Location getLocationByName(String locationName) {
		Location location = entityManager.createQuery("SELECT l FROM Location l WHERE l.name =:ln", Location.class)
				.setParameter("ln", locationName)
				.getSingleResult();
		
		System.out.println("DOA-LOATION" + location);
		
		return location;
	}
}
