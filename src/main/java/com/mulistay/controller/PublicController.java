package com.mulistay.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mulistay.dao.ProductDao;
import com.mulistay.meta.Product;
import com.mulistay.meta.User;
import com.mulistay.service.impl.CookieServiceImpl;

@Controller
public class PublicController {
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private CookieServiceImpl cookieServiceImpl;
	

	@RequestMapping(value="/public", method=RequestMethod.GET)
	public String showPublic(HttpServletRequest request, ModelMap map){

		User user = cookieServiceImpl.getUserCookie(request);
		map.addAttribute("user",user);
		
		
		return "public";
	}
	
	@RequestMapping(value="/publicSubmit", method=RequestMethod.POST)
	public String showPublicSubmit(
			String title, int price, String image, 
			String summary, String detail, HttpServletRequest request,ModelMap map){
		
		User user = cookieServiceImpl.getUserCookie(request);
		map.addAttribute("user",user);
		
		productDao.insertProduct(title, price, image, summary , detail);
		
		Product product = productDao.getProductByTitle(title);
		map.addAttribute("product",product);

		return "publicSubmit";
	}
	
	@ResponseBody
	@RequestMapping(value="/api/upload",method=RequestMethod.POST)
	public ModelMap showUpload(@RequestParam("file") MultipartFile file, 
			HttpServletRequest request, ModelMap map) throws IOException {
		
        String path = request.getServletContext().getRealPath("/image/");     
        
        String fileName = file.getOriginalFilename();  
        
        String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = String.valueOf(System.currentTimeMillis())+ "." + extensionName;

        File targetFile = new File(path, newFileName);  

        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  

        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        
        map.addAttribute("code",200);
        map.addAttribute("message","上传成功");
        map.addAttribute("result", "./image/"+newFileName);  
        
		return map;
	}
	
}
