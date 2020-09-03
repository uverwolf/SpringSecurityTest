package com.uverwolf.auth.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uverwolf.auth.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	User findByUsername(String email);
	List<User>findAll();
}
