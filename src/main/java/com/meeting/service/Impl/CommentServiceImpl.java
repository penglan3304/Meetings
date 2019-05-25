package com.meeting.service.Impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meeting.mapper.CommentMapper;
import com.meeting.pojo.Comment;
import com.meeting.pojo.User;
import com.meeting.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentMapper commentmapper;
	public List<Comment> comment(int meetingid){
		List<Comment> comments=commentmapper.selectByMeetingId(meetingid);
		if(comments.size()!=0) {
			for(int i=0;i<comments.size();i++) {
				if(comments.get(i).getReplayid()!=0) {
					Comment comment=commentmapper.selectById(comments.get(i).getReplayid());
					comments.get(i).setReplayname(comment.getUsername());
				}
			}
		}
		return comments;
	}
	
	public Comment selectbyId(int id) {
		Comment comment=commentmapper.selectById(id);
		return comment;
	}
	
	public int add(int meetingid,String content,User user,int replayid) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		Comment comments=commentmapper.selectById(replayid);
		String commenttime=sdf.format(date);
		Comment comment=new Comment();
		comment.setMeetingid(meetingid);
		comment.setUserid(user.getId());
		comment.setCommenttime(commenttime);
		comment.setContent(content);
		comment.setReplayid(replayid);
		if(null!=comments) {
			comment.setReplayname(comments.getUsername());
		}
		
		commentmapper.insert(comment);
		return 0;
	}
	
	public int del(int id) {
		commentmapper.deleteById(id);
		return 0;
	}
}
