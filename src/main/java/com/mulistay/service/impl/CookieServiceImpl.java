package com.mulistay.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mulistay.dao.UserDao;
import com.mulistay.meta.User;
import com.mulistay.service.CookieService;
import com.mulistay.utils.CookieUtils;

@Service
public class CookieServiceImpl implements CookieService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public User getUserCookie( HttpServletRequest request ) {
		String username = CookieUtils.getCookieValue(request,"userName");
		User user = userDao.getUserByName(username);
		return user;
	}

}
