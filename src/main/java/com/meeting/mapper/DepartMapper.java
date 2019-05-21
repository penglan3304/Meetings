package com.meeting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.meeting.pojo.Depart;



public interface DepartMapper {
	@Select("${sql} limit #{offset},#{rowCount}")
	List<Depart> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<Depart> selectBySql(@Param("sql") String sql);
	
	@Insert("INSERT INTO depart(pid,name) "
			+ "VALUES (#{pid},#{name})")
	@SelectKey(statement="SELECT currval('depart_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class) 
	long insert(Depart depart);

	@Update("UPDATE  depart SET pid=#{pid},name=#{name}"
			+ " WHERE id = #{id} ")
	void update(Depart depart);
	
	@Delete("DELETE FROM depart WHERE id =#{id}")
	void deleteById(long id);
	
	
	@Select("select * from depart where id = #{id}")
	List<Depart> selectById(long id);
	
	@Select("select name from depart where id = #{id}")
	String selectNameById(long id);
	
}
