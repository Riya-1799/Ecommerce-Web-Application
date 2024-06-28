package com.model.Dao;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.model.LocalUser;
import com.model.WebOrder;

public interface WebOrederDao extends ListCrudRepository<WebOrder, Integer>{
	
	List<WebOrder> findByUser(LocalUser user);
}
