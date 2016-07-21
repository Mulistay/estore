package com.mulistay.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.mulistay.meta.Product;

@Repository	
public interface ProductDao {
	
	
	@Results({ 
		@Result(property = "id", column = "id"), 
		@Result(property = "title", column = "title"), 
		@Result(property = "price", column = "price"), 
		@Result(property = "image", column = "image"),
		@Result(property = "summary", column = "summary"),
		@Result(property = "detail", column = "detail")}
	)
	@Insert("Insert into product (title, price, image, summary, detail) "
			+ "values (#{title}, #{price}, #{image}, #{summary}, #{detail})")
	public void insertProduct(
			@Param("title") String title,
			@Param("price") int price, 
			@Param("image") String image,
			@Param("summary") String summary,
			@Param("detail") String detail);
	
	@Results({ 
		@Result(property = "id", column = "id"), 
		@Result(property = "title", column = "title"), 
		@Result(property = "price", column = "price"), 
		@Result(property = "image", column = "image"),
		@Result(property = "summary", column = "summary"),
		@Result(property = "detail", column = "detail")}
	)
	@Update("Update product set title=#{title}, price=#{price}, image=#{image}, "
			+ "summary=#{summary}, detail=#{detail} where id=#{id}")
	public void updateProduct(
			@Param("title") String title,
			@Param("price") int price, 
			@Param("image") String image,
			@Param("summary") String summary,
			@Param("detail") String detail,
			@Param("id") int id);
	

	@Results({ 
		@Result(property = "id", column = "id"), 
		@Result(property = "title", column = "title"), 
		@Result(property = "price", column = "price"), 
		@Result(property = "image", column = "image"),
		@Result(property = "summary", column = "summary"),
		@Result(property = "detail", column = "detail")}
	)
	@Select("Select * from product")
	public List<Product> getProductList();
	
	@Results({ 
		@Result(property = "id", column = "id"), 
		@Result(property = "title", column = "title"), 
		@Result(property = "price", column = "price"), 
		@Result(property = "image", column = "image"),
		@Result(property = "summary", column = "summary"),
		@Result(property = "detail", column = "detail")}
	)
	@Select("Select * from product where title = #{title}")
	public Product getProductByTitle(@Param("title") String title);
	
	@Results({ 
		@Result(property = "id", column = "id"), 
		@Result(property = "title", column = "title"), 
		@Result(property = "price", column = "price"), 
		@Result(property = "image", column = "image"),
		@Result(property = "summary", column = "summary"),
		@Result(property = "detail", column = "detail")}
	)
	@Select("Select * from product where id = #{id}")
	public Product getProductById(@Param("id") int id);
	
	@Results({ 
		@Result(property = "id", column = "id"), 
		@Result(property = "title", column = "title"), 
		@Result(property = "price", column = "price"), 
		@Result(property = "image", column = "image"),
		@Result(property = "summary", column = "summary"),
		@Result(property = "detail", column = "detail")}
	)
	@Delete("Delete from product where id = #{id}")
	public void deleteProductById(@Param("id") int id);
	

}
