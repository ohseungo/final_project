package com.scsa.model.service;

import java.util.List;

import com.scsa.model.dao.ProductDAO;
import com.scsa.model.vo.Product;
import com.scsa.model.vo.User;

public class ProductServiceImpl implements ProductService {
	
	ProductDAO productDao;

	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}
	
	@Override
	public boolean addProduct(Product product) {
		return productDao.insertProduct(product);
	}
	
	public Product selectProduct(String productId) {
		return productDao.selectProduct(productId);
	}
	
	public boolean deleteProduct(String productId) {
		return productDao.deleteProduct(productId);
	}

	@Override
	public List<Product> selectProductList(String compId) {
		return productDao.selectProductList(compId);
	}

	@Override
	public boolean updateProduct(String productId) {
		return productDao.updateProduct(productId);
	}
	
}
