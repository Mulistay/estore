package com.mulistay.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mulistay.dao.UserDao;
import com.mulistay.meta.JsonResult;
import com.mulistay.meta.User;
import com.mulistay.utils.CookieUtils;

@Controller
public class LoginController {
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLogin(){
		return "login";
	}
	
	@RequestMapping(value="/api/login", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult showLoginResult(HttpServletRequest request, HttpServletResponse response, ModelMap map){
	
		String value1 = request.getParameter("userName");
		String value2 = request.getParameter("password");
		
		JsonResult jsonResult = new JsonResult();
		
		if (userDao.getUserByName(value1) != null){
			
			User user = userDao.getUserByName(value1);
			
			if (value2.equals(user.getPassword())){
				
				CookieUtils.setCookie(request, response, "userName", value1, -1, true);
				
				jsonResult.setCode(200);
				jsonResult.setMessage("登录成功");;
				jsonResult.setResult(true);
				
				map.addAttribute("user",user);
				
				return jsonResult;
			}else{
				jsonResult.setCode(205);
				jsonResult.setMessage("密码错误");;
				jsonResult.setResult(false);  //
				return jsonResult;
			}
		}else{
			jsonResult.setCode(205);
			jsonResult.setMessage("用户不存在");;
			jsonResult.setResult(false);
			return jsonResult;
		}

	}

	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String showLogout(HttpServletRequest request, HttpServletResponse response){
		CookieUtils.deleteCookie(request, response, "userName");
		return "redirect:login";
	}
	
}
