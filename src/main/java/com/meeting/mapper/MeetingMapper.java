package com.meeting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.meeting.pojo.Meeting;

public interface MeetingMapper {
	@Select("${sql} limit #{offset},#{rowCount}")
	List<Meeting> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<Meeting> selectBySql(@Param("sql") String sql);
	
	@Insert("INSERT INTO meeting(name,starttime,endtime,createtime,modifytime,createname,meetingroomid,state,isanother) "
			+ "VALUES (#{name},#{starttime},#{endtime},#{createtime},#{modifytime},#{createname},#{meetingroomid},#{state},#{isanother})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	long insert(Meeting meeting);

	@Update("UPDATE  meeting SET name=#{name},starttime=#{starttime},endtime=#{endtime},createtime=#{createtime},"
			+ "modifytime=#{modifytime},createname=#{createname},meetingroomid=#{meetingroomid},state=#{state},isanother=#{isanother}"
			+ " WHERE id = #{id} ")
	void update(Meeting meeting);
	
	
	
	@Update("UPDATE  meeting SET name=#{name},starttime=#{starttime},endtime=#{endtime},reason=#{reason},"
			+ "modifytime=#{modifytime},meetingroomid=#{meetingroomid},state=#{state},isanother=#{isanother}"
			+ " WHERE id = #{id} ")
	void updateS(Meeting meeting);
	
	
	@Delete("DELETE FROM meeting WHERE id =#{id}")
	void deleteById(long id);
	
	
	@Select("select * from meeting where id = #{id}")
	Meeting selectById(long id);
	
/*	@Select("select * from meeting where id = #{0} "
			+ " and (cast(starttime as TIMESTAMP) between '#{1}' and '#{2}') and (cast(endtime as TIMESTAMP) between '#{1}' and '#{2}')")
	Meeting selectByIdT(long id,String starttime,String endtime);*/
	
	
	@Select("select * from meeting where meetingroomid = #{meetingroomid}")
	List<Meeting> selectBymeetingroomid(long meetingroomid);
	
	@Select("select count(*) from meeting")
	int count();
	
	
	@Update("UPDATE  meeting SET state='审核通过'"
			+ " WHERE id = #{id} ")
	void updateState(long id);
	
	@Update("UPDATE  meeting SET reason=#{1},state='审核不通过'"
			+ " WHERE id = #{0} ")
	void updateStates(long id,String reason);
	
}
