package com.scsa.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.scsa.addon.NameManager;
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
	public String addProduct(Product product, MultipartFile productFile, HttpSession session, HttpServletRequest request) throws IllegalStateException, IOException {

		
		
		System.out.println(request.getServletContext().getRealPath("product") + "/" + productFile.getOriginalFilename());
		if (!productFile.isEmpty()) {
			String uploadFileName = productFile.getOriginalFilename();
			///사진을 올렸을 경우
			int number = 1;
			while (new File(request.getServletContext().getRealPath("images/product") + "/" + uploadFileName).exists()) {
				////이미 있으면 이름 바꿔야 함
				number++;
				uploadFileName = NameManager.changeFileName(productFile.getOriginalFilename(), 
						number);
			}
			productFile.transferTo(new File(request.getServletContext().getRealPath("images/product") + "/" +uploadFileName));
			product.setProductImage(uploadFileName);
		}
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
	public @ResponseBody List<Product> selectProductListForAndroid(String compId) {
		return productService.selectProductList(compId);
	}
	
	
	@RequestMapping("/delete_product.do")
	public  String deleteProduct(String productId, HttpSession session,HttpServletRequest request) {
		String result = null;
		Product product = productService.selectProduct(productId);
		if(productService.deleteProduct(productId) == true) {
			session.setAttribute("compId", product.getCompId());
			new File(request.getServletContext().getRealPath("images/product") + "/" + 
							product.getProductImage()).delete();
			result = "redirect:/corporate.do?compId="+product.getCompId();
		}else {
			result = null;
			////삭제 에러 페이지 
		}
		return result;
	} 
	
}
