package com.meeting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import com.meeting.pojo.Roles;

public interface RoleMapper {
	@Select("${sql} limit #{offset},#{rowCount}")
	List<Roles> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<Roles> selectBySql(@Param("sql") String sql);
	
	@Insert("INSERT INTO roles(id,name) "
			+ "VALUES (#{id},#{name})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	long insert(Roles roles);

	@Update("UPDATE  roles SET id=#{id},name=#{name}"
			+ " WHERE id = #{id} ")
	void update(Roles roles);
	
	@Delete("DELETE FROM roles WHERE id =#{id}")
	void deleteById(long id);
	
	
	@Select("select * from roles where id = #{id}")
	List<Roles> selectById(long id);

}
