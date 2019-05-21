package com.meeting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.meeting.pojo.Notify;

public interface NotifyMapper {
	@Select("${sql} limit #{offset},#{rowCount}")
		List<Notify> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
		
		@Select("${sql}")
		List<Notify> selectBySql(@Param("sql") String sql);
		
		@Insert("INSERT INTO notify(meetingid) "
				+ "VALUES (#{meetingid})")
		@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
		long insert(Notify notify);

		@Delete("DELETE FROM notify WHERE id =#{id}")
		void deleteById(long id);
		
		@Delete("DELETE FROM notify WHERE meetingid =#{meetingid}")
		void deleteBymeetingId(long id);
		
		@Select("select * from notify where id = #{id}")
		List<Notify> selectById(long id);
		
		@Select("select * from notify where meetingid = #{meetingid}")
		List<Notify> selectByMeetingId(long meetingid);
		
		@Select("select name from notify where id = #{id}")
		String selectNameById(long id);
}
