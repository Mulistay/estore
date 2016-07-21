package com.mulistay.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mulistay.dao.ProductDao;
import com.mulistay.dao.UserDao;
import com.mulistay.meta.Product;
import com.mulistay.meta.User;
import com.mulistay.service.impl.CookieServiceImpl;
import com.mulistay.utils.CookieUtils;

@Controller
public class EditController {
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CookieServiceImpl cookieServiceImpl;
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String showEdit(@RequestParam("id") int id, HttpServletRequest request, ModelMap map){
		
		String username = CookieUtils.getCookieValue(request,"userName");
		User user = userDao.getUserByName(username);
		map.addAttribute("user",user);
		
		Product product = productDao.getProductById(id);
		map.addAttribute("product", product);
		
		return "edit";
	}
	
	@RequestMapping(value="/editSubmit", method=RequestMethod.POST)
	public String showAccountResult(@RequestParam("id") int id,
			String title, int price, String image, String summary, String detail,
			HttpServletRequest request, ModelMap map){
		
		User user = cookieServiceImpl.getUserCookie(request);
		map.addAttribute("user",user);
		
		productDao.updateProduct(title, price, image, summary, detail, id);
		
		Product product = productDao.getProductById(id);
		map.addAttribute("product",product);
		
		return "editSubmit";
	}
	

}
