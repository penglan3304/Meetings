package com.meeting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.meeting.pojo.Menu;



public interface MenuMapper {
	@Select("${sql} limit #{rowCount} offset #{offset}")
	List<Menu> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<Menu> selectBySql(@Param("sql") String sql);
	
	@Insert("INSERT INTO menu(pid,level,namne,url,icon) "
			+ "VALUES (#{pid},#{level},#{namne},#{url},#{icon})")
	@SelectKey(statement="SELECT currval('menu_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class) 
	long insert(Menu menu);

	@Update("UPDATE  menu SET pid=#{pid},level=#{level},name=#{name},url=#{url},"
			+ "icon=#{icon}"
			+ " WHERE id = #{id} ")
	void update(Menu menu);
	
	@Delete("DELETE FROM menu WHERE id =#{id}")
	void deleteById(long id);
	
	
	@Select("select * from menu where id = #{id}")
	List<Menu> selectById(long id);

}
