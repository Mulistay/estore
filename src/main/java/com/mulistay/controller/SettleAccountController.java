package com.mulistay.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mulistay.dao.ProductDao;
import com.mulistay.dao.TrxDao;
import com.mulistay.meta.Buy;
import com.mulistay.meta.JsonResult;
import com.mulistay.meta.Product;
import com.mulistay.meta.User;
import com.mulistay.service.impl.CookieServiceImpl;

@Controller
public class SettleAccountController {
	
	@Autowired
	private CookieServiceImpl cookieServiceImpl;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private TrxDao trxDao;
	
	@RequestMapping(value="/settleAccount", method=RequestMethod.GET)
	public String showSettleAccount(HttpServletRequest request, ModelMap map) {
		
		User user = cookieServiceImpl.getUserCookie(request);
		map.addAttribute("user",user);
		
		return "settleAccount";
	}
	
	@ResponseBody
	@RequestMapping(value="/api/buy", method=RequestMethod.POST)
	public JsonResult showAccountResult(@RequestBody String buyListJson,HttpServletRequest request, 
			HttpServletResponse response, ModelMap map){
		
		JsonResult jsonResult = new JsonResult();
		
		User user = cookieServiceImpl.getUserCookie(request);
		map.addAttribute("user",user);
		
		JSONArray jsonArray = new JSONArray(buyListJson);  
		
		List<Buy> buyList = new ArrayList<Buy>();
		
		if ( response.getStatus() >= 200 && response.getStatus() < 300 || response.getStatus() == 304 ){
			
			for(int i=0;i<jsonArray.length();i++){  
				JSONObject jsonobject2=jsonArray.getJSONObject(i);  
				int id=jsonobject2.getInt("id");  
				int number=jsonobject2.getInt("number");  
				System.out.println(id+" "+number);

				Product product = productDao.getProductById(id);

				if (number != 0) {
					Buy buy = new Buy();
					buy.setId(id);
					buy.setImage(product.getImage());
					buy.setTitle(product.getTitle());
					buy.setBuyPrice(product.getPrice());
					buy.setBuyNum(number);
					buy.setBuyTime(System.currentTimeMillis());
					buyList.add(buy);

					trxDao.insertTrx(id, user.getId(), product.getPrice(), System.currentTimeMillis(), number);
					
					jsonResult.setCode(200);
					jsonResult.setMessage("success");
					jsonResult.setResult(true);
				} else{
					jsonResult.setCode(205);
					jsonResult.setMessage("数量不能为空");
					jsonResult.setResult(false);
					break;
				}
			}
			map.addAttribute("buyList", buyList);
			return jsonResult;
		}else{
			jsonResult.setCode(500);
			jsonResult.setMessage("error");;
			jsonResult.setResult(false);
			return jsonResult;
		}
	}
}
