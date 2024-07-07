package com.model.Dao;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.model.LocalUser;
import com.model.VerificationToken;

public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, Long>{
	
	Optional<VerificationToken> findByToken(String token);
	void deleteByUser(LocalUser user);
}
