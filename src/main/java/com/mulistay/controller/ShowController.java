package com.mulistay.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mulistay.dao.ProductDao;
import com.mulistay.dao.TrxDao;
import com.mulistay.meta.Product;
import com.mulistay.meta.Trx;
import com.mulistay.meta.User;
import com.mulistay.service.impl.CookieServiceImpl;

@Controller
public class ShowController {
	
	@Autowired
	private ProductDao productListDao;
	
	@Autowired
	private CookieServiceImpl cookieServiceImpl;
	
	@Autowired
	private TrxDao trxDao;
	
	@RequestMapping(value="/show", method=RequestMethod.GET)
	public String showShow(HttpServletRequest request, ModelMap map){

		User user = cookieServiceImpl.getUserCookie(request);
		map.addAttribute("user",user);
		
		if ( request.getParameter("id") != null ){
			
			int id = Integer.parseInt(request.getParameter("id").trim());
			
			Product product = productListDao.getProductById(id);	
			
			if ( trxDao.getTrxByIds(id) != null ) {
				
				Trx trx = trxDao.getTrxByIds(id);
				product.setIsBuy(true);
				product.setIsSell(true);
				product.setBuyPrice(trx.getPrice());
				product.setBuyNum(trx.getBuyNum());
				product.setSaleNum(trx.getBuyNum());
			}
			map.addAttribute("product",product);
			
		}

		
		return "show";

	}
}
