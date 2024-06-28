package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.LocalUser;
import com.model.WebOrder;
import com.model.Dao.WebOrederDao;

@Service
public class OrderService {
	
	@Autowired
	private WebOrederDao weborderdao;
	
	public List<WebOrder> getorders(LocalUser user){
		return weborderdao.findByUser(user);
		
	}

}
