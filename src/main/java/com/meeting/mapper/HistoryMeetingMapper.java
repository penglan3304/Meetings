package com.meeting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.meeting.pojo.HistoryMeeting;
public interface HistoryMeetingMapper {
	@Insert("INSERT INTO historymeeting(name,starttime,endtime,createtime,meetingid,meetingroomid,state) "
			+ "VALUES (#{name},#{starttime},#{endtime},#{createtime},#{meetingid},#{meetingroomid},#{state})")
	@SelectKey(statement="SELECT currval('historymeeting_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class) 
	long insert(HistoryMeeting historymeeting);
	
	@Select("select * from historymeeting where meetingroomid = #{meetingroomid}")
	List<HistoryMeeting> selectById(long meetingroomid);
}
