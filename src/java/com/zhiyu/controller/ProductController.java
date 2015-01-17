package com.zhiyu.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhiyu.service.product.ProductService;

@Controller
public class ProductController {

	@Resource
	private ProductService productService;
	
	@RequestMapping(value = "/product/list")
	public String list(HttpServletRequest request) {
		System.out.println(productService.getProductList());
		return "product/productList";
	}
}
