package com.olik.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.olik.entity.Product;
public interface ProductRepo extends CrudRepository<Product, Long> {

//	List<Product> findByCategoryAndStatus(String category, String status);

	List<Product> findByCategory(String category);

}
