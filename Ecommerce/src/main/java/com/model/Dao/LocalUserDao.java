package com.model.Dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

import com.model.LocalUser;

public interface LocalUserDao extends ListCrudRepository<LocalUser, Long>{
	
	public Optional<LocalUser> findByUsernameIgnoreCase(String username);
	public Optional<LocalUser> findByEmailIgnoreCase(String email);

}
