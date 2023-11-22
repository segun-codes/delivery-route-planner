package com.geobyte.lcmsbe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geobyte.lcmsbe.dao.UserDAO;
import com.geobyte.lcmsbe.entity.User;

import jakarta.transaction.Transactional;



@Service
public class UserServiceImp implements UserService {
	private UserDAO userDao;
	
	
	@Autowired
	public UserServiceImp(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Override
	@Transactional
	public User save(User user) {
		return userDao.save(user);
	}

	@Override
	@Transactional
	public User update(User user) {
		userDao.update(user);
		
		return null;
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		userDao.deleteById(id);
	}

	@Override
	public User findById(long id) {
		return userDao.findById(id);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public String getHashedPasswordByEmail(String email) {
		return userDao.getHashedPasswordByEmail(email);
	}
}
