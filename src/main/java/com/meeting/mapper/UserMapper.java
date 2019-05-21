package com.meeting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.meeting.pojo.User;

public interface UserMapper {
	
	@Select("${sql} limit #{offset},#{rowCount}")
	List<User> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<User> selectBySql(@Param("sql") String sql);
	
	@Insert("INSERT INTO users(username,password,phone,modifytime,createtime,email,departid) "
			+ "VALUES (#{username},#{password},#{phone},#{modifytime},#{createtime},#{email},#{departid})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	long insert(User user);

	@Update("UPDATE  users SET username=#{username},password=#{password},phone=#{phone},modifytime=#{modifytime},"
			+ "createtime=#{createtime},email=#{email},departid=#{departid}"
			+ " WHERE id = #{id} ")
	void update(User user);
	
	@Delete("DELETE FROM users WHERE id =#{id}")
	void deleteById(long id);
	
	
	@Select("select * from users where id = #{id}")
	List<User> selectById(long id);
	
	@Select("select * from users where phone = #{phone}")
	List<User> selectByPhone(String phone);
	
	@Select("select * from users where email = #{email}")
	List<User> selectByEmail(String email);
	
	@Select("select * from users where departid = #{departid}")
	List<User> selectByDepartId(long departid);
	
	@Select("select a.*,b.name as departname,d.name as rolename from users a,depart b,userrole c,roles d where a.id=#{id}"
			+ " and a.departid=b.id and c.userid=a.id and c.roleid=d.id ")
	List<User> select(long id);
}
