package com.mulistay.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mulistay.dao.ProductDao;
import com.mulistay.dao.TrxDao;
import com.mulistay.meta.JsonResult;
import com.mulistay.meta.Product;
import com.mulistay.meta.User;
import com.mulistay.service.impl.CookieServiceImpl;

@Controller 
public class IndexController {
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private TrxDao trxDao;
	
	@Autowired
	private CookieServiceImpl cookieServiceImpl;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String showIndex(HttpServletRequest request, ModelMap map){

		User user = cookieServiceImpl.getUserCookie(request);
		map.addAttribute("user",user);

		List<Product> productList = productDao.getProductList();
		if ( productList != null ){
			if ( user != null ){
				for(Product product:productList){
					if ( trxDao.getTrxByIds(product.getId()) != null ) {
						product.setIsBuy(true);
						product.setIsSell(true);
					}
				}
			}
		}
		map.addAttribute("productList",productList);

		return "index";

	}
	
	@RequestMapping(value="/api/delete", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult showDelete(int id,HttpServletRequest request){
		
		id = Integer.parseInt(request.getParameter("id"));

		productDao.deleteProductById(id);
				
		JsonResult jsonResult = new JsonResult();
		jsonResult.setCode(200);
		jsonResult.setMessage("É¾³ý³É¹¦");
		jsonResult.setResult(true);
		
		return jsonResult;
	}

}
