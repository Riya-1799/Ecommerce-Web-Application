package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Product;
import com.model.Dao.ProductDao;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productdao;
	public List<Product> getProducts(){
		return productdao.findAll();
	}

}
