package com.model.Dao;

import org.springframework.data.repository.ListCrudRepository;

import com.model.Product;

public interface ProductDao extends ListCrudRepository<Product, Integer>{

}
