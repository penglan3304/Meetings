package com.meeting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.meeting.pojo.Comment;


public interface CommentMapper {
	@Select("${sql} limit #{offset},#{rowCount}")
	List<Comment> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<Comment> selectBySql(@Param("sql") String sql);
	
	@Insert("INSERT INTO comment(meetingid,commenttime,content,userid,replayid) "
			+ "VALUES (#{meetingid},#{commenttime},#{content},#{userid},#{replayid})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	long insert(Comment comment);

	@Delete("DELETE FROM comment WHERE id =#{id}")
	void deleteById(long id);
//	
//	@Delete("DELETE FROM notify WHERE meetingid =#{meetingid}")
//	void deleteBymeetingId(long id);
//	
	@Select("select a.*,b.username as username from comment a,users b where a.id = #{id} and a.userid=b.id")
	Comment selectById(long id);
//	
	@Select("select a.*,b.username as username from comment a,users b where a.meetingid = #{meetingid} and a.userid=b.id")
	List<Comment> selectByMeetingId(long meetingid);
//	
//	@Select("select name from notify where id = #{id}")
//	String selectNameById(long id);
}
