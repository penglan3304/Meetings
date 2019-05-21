package com.meeting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.meeting.pojo.MeetingRoom;

public interface MeetingRoomMapper {
	@Select("${sql} limit #{offset},#{rowCount}")
	List<MeetingRoom> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<MeetingRoom> selectBySql(@Param("sql") String sql);
	
	@Insert("INSERT INTO meetingroom(name,state,createname,createtime) "
			+ "VALUES (#{name},#{state},#{createname},#{createtime})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	long insert(MeetingRoom meetingroom);

	@Update("UPDATE  meetingroom SET name=#{name},state=#{state},createname=#{createname},createtime=#{createtime}"
			+ " WHERE id = #{id} ")
	void update(MeetingRoom meetingroom);
	
	
	@Update("UPDATE  meetingroom SET state='空闲'"
			+ " WHERE id = #{id}")
	void updatestate(int meetingroomid);
	
	@Delete("DELETE FROM meetingroom WHERE id =#{id}")
	void deleteById(long id);
	
	
	@Select("select * from meetingroom where id = #{id}")
	List<MeetingRoom> selectById(long id);
	
	@Select("select count(*) from meetingroom")
	int count();
}
