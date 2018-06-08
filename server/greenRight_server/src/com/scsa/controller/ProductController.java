package com.scsa.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scsa.model.service.ProductService;
import com.scsa.model.vo.Product;

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
		return "redirect:/corporate.do?compId="+product.getCompId();
	}
	
	@RequestMapping("/update_product.do")
	public String updateProduct(Product product, HttpSession session) {
		product.setCompId((String) session.getAttribute("compId"));
		productService.updateProduct(product.getProductId());
		return "redirect:/corporate.do?compId="+product.getCompId();
	}
	
	@RequestMapping("/select_product.do")
	public @ResponseBody Product selectProduct(String productId) {
		return productService.selectProduct(productId);
	}
	

	@RequestMapping("/corporate.do")
	public String selectProductList(Model model, String compId) {
		model.addAttribute("productList", productService.selectProductList(compId));
		return "/corporate.jsp";
	}
	
	@RequestMapping("/delete_product.do")
	public  String deleteProduct(String productId, HttpSession session) {
		String result = null;
		Product product = productService.selectProduct(productId);
		if(productService.deleteProduct(productId) == true) {
			session.setAttribute("compId", product.getCompId());
			result = "redirect:/corporate.do?compId="+product.getCompId();
		}else {
			result = null;
		}
		return result;
	} 
	
}
