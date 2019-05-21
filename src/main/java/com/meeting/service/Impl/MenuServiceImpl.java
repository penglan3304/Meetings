package com.meeting.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.meeting.mapper.MenuMapper;
import com.meeting.pojo.Menu;
import com.meeting.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private MenuMapper menuMapper;
	@Override
	public List<Menu> listMenu() {
		String sql="select * from menu";
		List<Menu> menus=menuMapper.selectBySql(sql);
		List<Menu> list =getMenuList(menus);
		return list;
	}
	private List<Menu> getMenuList(List<Menu> menus)
	{
		List<Menu> list=new ArrayList<Menu>();
		//遍历获取所有的一级菜单
		for(int i=0;i<menus.size();i++)
		{
			Menu menu=menus.get(i);
			if(menu.getPid()==0)
			{
				list.add(menu);
			}
		}
		//查找二级菜单
        /**
                        * 利用递归找出所有子菜单
         */
        for (Menu menu: list) {
            menu.setChildMenus(getChild(menu.getId(),menus));
        }
 
        return list;
	}
	 private List<Menu> getChild(long id, List<Menu> rootMenu) 
	 {
	        // 子菜单
	        List<Menu> childList = new ArrayList<Menu>();
	 
	        for (Menu menu : rootMenu) {
	            // 遍历所有节点，将父菜单id与传过来的id比较
	            if (menu.getPid()!=0) {
	                if (menu.getPid()==id) {
	                    childList.add(menu);
	                }
	            }
	        }
	 
	        // 把子菜单的子菜单再循环一遍
	        for (Menu menu : childList) {// 没有url子菜单还有子菜单
	            if (StringUtils.isEmpty(menu.getUrl())) {
	                // 递归
	                menu.setChildMenus(getChild(menu.getId(), rootMenu));
	            }
	        }
	 
	        // 递归退出条件
	        if (childList.size() == 0) {
	            return null;
	        }
	 
	        return childList;
	    }

}
