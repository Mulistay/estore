package com.mulistay.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mulistay.dao.ProductDao;
import com.mulistay.dao.TrxDao;
import com.mulistay.meta.Buy;
import com.mulistay.meta.Product;
import com.mulistay.meta.Trx;
import com.mulistay.meta.User;
import com.mulistay.service.impl.CookieServiceImpl;

@Controller
public class AccountController {
	
	@Autowired
	private CookieServiceImpl cookieServiceImpl;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private TrxDao trxDao;

	@RequestMapping(value="/account", method=RequestMethod.GET)
	public String showAccount(HttpServletRequest request, ModelMap map){
		
		User user = cookieServiceImpl.getUserCookie(request);
		map.addAttribute("user",user);
		
		List<Buy> buyList = new ArrayList<Buy>();
		
		List<Trx> trxList = trxDao.getTrxList();
		
		for (Trx trx : trxList) {
			Buy buy = new Buy();
			if (trx.getPersonId() == user.getId()){
				buy.setId(trx.getContentId());
				Product product = productDao.getProductById(trx.getContentId());
				buy.setImage(product.getImage());
				buy.setTitle(product.getTitle());
				buy.setBuyPrice(trx.getPrice());
				buy.setBuyNum(trx.getBuyNum());
				buy.setBuyTime(trx.getTime());
				buyList.add(buy);
			}
		}
		
		map.addAttribute("buyList",buyList);
		
		return "account";
	}

}

