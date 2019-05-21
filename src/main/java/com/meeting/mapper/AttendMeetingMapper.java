package com.meeting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.meeting.pojo.AttendMetting;


public interface AttendMeetingMapper {
	@Select("${sql} limit #{offset},#{rowCount}")
	List<AttendMetting> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<AttendMetting> selectBySql(@Param("sql") String sql);
	
	@Insert("INSERT INTO attendmeeting(meetingid,attendperson,signtime,state,absencereason,personid) "
			+ "VALUES (#{meetingid},#{attendperson},#{signtime},#{state},#{absencereason},#{personid})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	long insert(AttendMetting attendmeeting);

	@Update("UPDATE  attendmeeting SET meetingid=#{meetingid},attendperson=#{attendperson},signtime=#{signtime},state=#{state}"
			+ ",absencereason=#{absencereason},personid=#{personid}"
			+ " WHERE id = #{id} ")
	void update(AttendMetting attendmeeting);
	
	
	@Delete("DELETE FROM attendmeeting WHERE id =#{id}")
	void deleteById(long id);
	
	@Delete("DELETE FROM attendmeeting WHERE meetingid =#{meetingid}")
	void deleteBymeetingId(long meetingid);
	
	@Select("select * from attendmeeting where personid = #{personid}")
	List<AttendMetting> selectBypersonId(long personid);
	
	@Select("select * from attendmeeting where id = #{id}")
	List<AttendMetting> selectById(long id);
	
	@Select("select * from attendmeeting where meetingid = #{0} and personid=#{1}")
	AttendMetting select(long meetingid,long personid);
	
	
	@Select("select * from attendmeeting where personid = #{0} and state=#{1}")
	List<AttendMetting> selectByState(long id,String state);
	
	
	@Select("select * from attendmeeting where personid = #{0} and state=#{1} "
			 + " and unix_timestamp(starttime) between #{2} and #{3} and unix_timestamp(endtime) between #{2} and #{3}")
	List<AttendMetting> selectByStateT(long id,String state,String starttime,String endtime);
	
	@Select("select * from attendmeeting where meetingid = #{meetingid}")
	List<AttendMetting> selectBymeetingid(long meetingid);

	@Select("select * from attendmeeting where meetingid = #{0} and attendperson ~ #{1}")
	List<AttendMetting> selectBymeetingidN(long meetingid,String person);
	
	@Select("select * from attendmeeting where id = #{id} and (state='未报名' or state='请假')")
	List<AttendMetting> selectByStates(long id);
	
	
	@Update("UPDATE  attendmeeting SET absencereason=#{1},state='请假'"
			+ " WHERE meetingid = #{0} and personid=#{2} ")
	void updateStates(long id,String reason,long userid);
	
	@Update("UPDATE  attendmeeting SET state='已报名'"
			+ " WHERE id = #{id} ")
	void updateState(long id);
}
