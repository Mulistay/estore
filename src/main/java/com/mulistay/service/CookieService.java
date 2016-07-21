package com.mulistay.service;

import javax.servlet.http.HttpServletRequest;

import com.mulistay.meta.User;

public interface CookieService {
	
	public User getUserCookie(HttpServletRequest request);
}
