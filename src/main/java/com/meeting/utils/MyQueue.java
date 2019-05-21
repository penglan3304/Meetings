package com.meeting.utils;

import java.util.LinkedList;

public class MyQueue {
	private LinkedList<String> list = new LinkedList<String> ();
	private int size = 0;
	private static MyQueue instance;
	private MyQueue(){}
	public static synchronized MyQueue getInstance() {
		if (instance == null) {
			instance = new MyQueue();
	}
	return instance;
	}
	//添加
	public synchronized void put(String e) {
		size++;
		list.addLast(e);
	}
	//使用removeFirst()方法，返回队列中第一个数据，然后将它从队列中删除
	public synchronized String get() {
		if (list.size()==0) {
			return "";
		}else {
			size--;
			return list.removeFirst();
		}
	}
	//是否为空
	public synchronized boolean empty() {
		boolean flag = false;
		if(size==0){
			flag = true;
		}
		return flag;
	}
	//获取长虔
	public synchronized int size(){
		return size;
	}
	//清空
	public synchronized void clear() {
		list.clear();
		size = 0;
	}
}
