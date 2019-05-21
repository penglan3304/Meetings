package com.meeting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.meeting.pojo.MeetingParam;



public interface MeetingParamMapper {
	@Select("${sql} limit #{offset},#{rowCount}")
	List<MeetingParam> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<MeetingParam> selectBySql(@Param("sql") String sql);
	
	@Insert("INSERT INTO meetingparam(meetingid,departs,userid) "
			+ "VALUES (#{meetingid},#{departs},#{userid})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id") 
	long insert(MeetingParam meeting);

	@Update("UPDATE  meetingparam SET meetingid=#{meetingid},departs=#{departs},userid=#{userid}"
			+ " WHERE id = #{id} ")
	void update(MeetingParam meeting);
	
	@Delete("DELETE FROM meetingparam WHERE meetingid =#{meetingid}")
	void deleteById(long id);
	
	
	@Select("select * from meetingparam where id = #{id}")
	List<MeetingParam> selectById(long id);
	
	@Select("select * from meetingparam where userid = #{userid}")
	List<MeetingParam> selectByUserId(long id);
	
	
	@Select("select * from meetingparam where meetingid = #{meetingid}")
	MeetingParam selectByMeetingId(long id);
	
	@Select("select count(*) from meeting")
	int count();
}
