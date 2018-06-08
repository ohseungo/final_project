package com.scsa.model.service;

import java.util.List;

import com.scsa.model.vo.Product;

public interface ProductService {

	boolean addProduct(Product product);
	
	Product selectProduct(String productId);
	
	List<Product> selectProductList(String compId);
	
	boolean deleteProduct(String productId);

	boolean updateProduct(String productId);
	
}
