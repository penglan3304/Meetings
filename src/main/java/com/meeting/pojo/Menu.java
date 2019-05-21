package com.meeting.pojo;

import java.util.List;

public class Menu implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1496327711322279760L;
	// 菜单id
    private long id;
    // 菜单名称
    private String name;
    // 父菜单id
    private long pid;
    // 菜单url
    private String url;
    // 菜单图标
    private String icon;
    // 菜单顺序
    private long level;
    // 子菜单
    private List<Menu> childMenus;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public long getLevel() {
		return level;
	}
	public void setLevel(long level) {
		this.level = level;
	}
	public List<Menu> getChildMenus() {
		return childMenus;
	}
	public void setChildMenus(List<Menu> childMenus) {
		this.childMenus = childMenus;
	}
}
