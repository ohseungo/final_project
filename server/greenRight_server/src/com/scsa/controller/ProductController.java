package com.scsa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scsa.model.service.ProductService;
import com.scsa.model.service.UserService;
import com.scsa.model.vo.Product;
import com.scsa.model.vo.User;

@Controller
public class ProductController {
	
	private ProductService productService;
	
	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	//@RequestMapping("#")
	public @ResponseBody boolean addProduct(Product product) {
		return productService.addProduct(product);
	}
	
	//@RequestMapping("#")
	public @ResponseBody Product selectProduct(String productId) {
		return productService.selectProduct(productId);
	}
	
	//@RequestMapping("#")
	public @ResponseBody List<Product> selectProductList() {
		return productService.selectProductList();
	}
	
	//@RequestMapping("#")
	public @ResponseBody boolean deleteProduct(String productId) {
		return productService.deleteProduct(productId);
	}
	
}
