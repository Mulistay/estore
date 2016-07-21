package com.mulistay.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.mulistay.meta.User;

@Repository	
public interface UserDao {
	
	@Results({ 
		@Result(property = "id", column = "id"), 
		@Result(property = "username", column = "userName"), 
		@Result(property = "password", column = "password"), 
		@Result(property = "nickname", column = "nickName"), 
		@Result(property = "usertype", column = "usertype")}
	)
	@Select("Select * from user")
	public List<User> getUserList();

	@Results({ 
		@Result(property = "id", column = "id"), 
		@Result(property = "username", column = "userName"), 
		@Result(property = "password", column = "password"), 
		@Result(property = "nickname", column = "nickName"), 
		@Result(property = "usertype", column = "usertype")}
	)
	@Select("Select * from user where usertype = #{usertype}")
	public User getUserByType(@Param("usertype") int usertype);
	
	@Results({ 
		@Result(property = "id", column = "id"), 
		@Result(property = "username", column = "userName"), 
		@Result(property = "password", column = "password"), 
		@Result(property = "nickname", column = "nickName"), 
		@Result(property = "usertype", column = "usertype")}
	)
	@Select("Select * from user where username = #{username}")
	public User getUserByName(@Param("username") String username);

}
