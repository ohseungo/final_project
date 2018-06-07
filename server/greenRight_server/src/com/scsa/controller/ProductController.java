package com.scsa.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scsa.model.service.ProductService;
import com.scsa.model.vo.Product;
import com.scsa.model.vo.User;

@Controller
public class ProductController {
	
	private ProductService productService;
	
	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	

	@RequestMapping("/add_product.do")
	public String addProduct(Product product, HttpSession session) {
		
		product.setCompId((String) session.getAttribute("compId"));
		productService.addProduct(product);
		System.out.println(product);
		return "redirect:/corporate.do?compId="+
				session.getAttribute("compId");
	}
	

	@RequestMapping("/select_product.do")
	public @ResponseBody Product selectProduct(String productId) {
		return productService.selectProduct(productId);
	}
	

	@RequestMapping("/corporate.do")
	public String selectProductList(Model model, String compId, HttpSession session) {
		model.addAttribute("productList", productService.selectProductList(compId));
		return "/corporate.jsp";
	}
	
	@RequestMapping("/list_product.do")
	public List<Product> selectProductListForAndroid(String compId) {
		return productService.selectProductList(compId);
	}
	
	
	@RequestMapping("/delete_product.do")
	public  String deleteProduct(String productId, HttpSession session) {
		String result = null;
		Product product = productService.selectProduct(productId);
		session.setAttribute("product", product);
		if(productService.deleteProduct(productId) == true) {
			session.setAttribute("compId", product.getCompId());
			result = "redirect:/corporate.do?compId="+product.getCompId();
		}else {
			result = null;
		}
		return result;
	} 
	
}
