package com.geobyte.lcmsbe.service;

import java.util.List;

import com.geobyte.lcmsbe.entity.User;


public interface UserService {
	public User save(User user);
	public User update(User user);
	public void deleteById(long id);
	public User findById(long id);
	public List<User> findAll();
	public String getHashedPasswordByEmail(String email);
}
