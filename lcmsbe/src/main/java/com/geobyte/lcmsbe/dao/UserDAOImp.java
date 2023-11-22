package com.geobyte.lcmsbe.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geobyte.lcmsbe.entity.User;

import jakarta.persistence.EntityManager;


@Repository
public class UserDAOImp implements UserDAO {
	private EntityManager entityManager;
	
	@Autowired
	public UserDAOImp(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	// Note, merge will operate as persist() if locationId is 0
	@Override
	public User save(User user) {
		return entityManager.merge(user); 
	}

	@Override
	public User update(User user) {		
		return entityManager.merge(user);
	}

	@Override
	public void deleteById(long id) {
		User user = entityManager.find(User.class, id);
		entityManager.remove(user);
	}

	@Override
	public User findById(long id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public List<User> findAll() {
		return entityManager.createQuery("FROM User", User.class).getResultList();
	}

	@Override
	public String getHashedPasswordByEmail(String email) {
		User user  = entityManager.createQuery("SELECT u FROM User u WHERE u.email =:em", User.class)
				.setParameter("em", email)
				.getSingleResult();

		return user.getPassword();
	}
}
