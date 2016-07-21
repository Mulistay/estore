package com.mulistay.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.mulistay.meta.Trx;

@Repository	
public interface TrxDao {
	
	@Results({ 
		@Result(property = "id", column = "id"), 
		@Result(property = "contentId", column = "contentId"), 
		@Result(property = "personId", column = "personId"), 
		@Result(property = "price", column = "price"),
		@Result(property = "buyNum", column = "buyNum"),
		@Result(property = "time", column = "time")}
	)
	@Select("Select * from trx where contentId=#{contentId}")
	public Trx getTrxByIds(@Param("contentId") int contentId);
	
	@Results({ 
		@Result(property = "id", column = "id"), 
		@Result(property = "contentId", column = "contentId"), 
		@Result(property = "personId", column = "personId"), 
		@Result(property = "price", column = "price"),
		@Result(property = "buyNum", column = "buyNum"),
		@Result(property = "time", column = "time")}
	)
	@Insert("Insert into trx (contentId, personId, price, time, buyNum) "
			+ "values (#{contentId}, #{personId}, #{price}, #{time}, #{buyNum})")
	public void insertTrx(@Param("contentId") int contentId, @Param("personId") int personId, 
			@Param("price") int price, @Param("time") long time, @Param("buyNum") int buyNum);
	
	@Results({ 
		@Result(property = "id", column = "id"), 
		@Result(property = "contentId", column = "contentId"), 
		@Result(property = "personId", column = "personId"), 
		@Result(property = "price", column = "price"),
		@Result(property = "buyNum", column = "buyNum"),
		@Result(property = "time", column = "time")}
	)
	@Select("Select * from trx")
	public List<Trx> getTrxList();
	
	
}
