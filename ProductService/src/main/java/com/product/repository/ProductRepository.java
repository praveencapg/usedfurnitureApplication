package com.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	public List<Product> findByBrand(String brandName);

	// Custom Repository method
	@Query(value = "select * from products where category_id=?1", nativeQuery = true)
	public List<Product> findProductsByCatogory(int category_id);

}