package com.scsa.model.dao;

import java.util.List;

import com.scsa.model.vo.Product;

public interface ProductDAO {

	boolean insertProduct(Product product);

	Product selectProduct(String productId);

	List<Product> selectProductList(String compId);

	boolean deleteProduct(String productId);

	boolean updateProduct(String productId);
}
