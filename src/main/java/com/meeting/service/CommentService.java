package com.meeting.service;

import java.util.List;

import com.meeting.pojo.Comment;
import com.meeting.pojo.User;

public interface CommentService {
	public List<Comment> comment(int meetingid);
	public int add(int meetingid,String content,User user,int replayid);
	public int del(int id);
	public Comment selectbyId(int id);
}
