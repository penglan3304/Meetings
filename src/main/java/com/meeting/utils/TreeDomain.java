package com.meeting.utils;

import java.util.ArrayList;
import java.util.List;

public class TreeDomain {
	private Object data;
	private Integer id;
	private String name;
	private Integer parentId;
	private List<TreeDomain> children;
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public List<TreeDomain> getChildren() {
		return children;
	}
	public void setChildren(List<TreeDomain> children) {
		this.children = children;
	}
	public TreeDomain(Integer id,String name,Integer parentId) {
		this.id=id;
		this.parentId=parentId;
		this.name=name;
	}
	
	public void addChildNode(TreeDomain node) {
		if(children==null) {
			children=new ArrayList<>();
		}
		children.add(node);
	}
	public boolean isBrother(TreeDomain node) {
		return this.parentId.equals(((TreeDomain)node).getParentId());
	}
	public boolean isChildFrom(TreeDomain node) {
		return this.parentId.equals(node.getId());
	}
	public TreeDomain() {
		
	}
}
