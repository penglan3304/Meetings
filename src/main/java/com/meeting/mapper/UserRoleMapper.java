package com.meeting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.meeting.pojo.UserRole;

public interface UserRoleMapper {
	@Select("${sql} limit #{offset},#{rowCount}")
	List<UserRole> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<UserRole> selectBySql(@Param("sql") String sql);
	
	@Insert("INSERT INTO userrole(userid,roleid) "
			+ "VALUES (#{userid},#{roleid})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	long insert(UserRole useroles);

	@Update("UPDATE  userrole SET id=#{id},userid=#{userid},roleid=#{roleid}"
			+ " WHERE id = #{id} ")
	void update(UserRole useroles);
	
	@Delete("DELETE FROM userrole WHERE id =#{id}")
	void deleteById(long id);
	
	@Delete("DELETE FROM userrole WHERE userid =#{userid}")
	void deleteByUserId(long userid);
	
	
	@Select("select * from userrole where id = #{id}")
	List<UserRole> selectById(long id);
	
	@Select("select * from userrole where userid = #{userid}")
	List<UserRole> selectByuserId(long id);
}
