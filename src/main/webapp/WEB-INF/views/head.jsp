<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>
<link rel="stylesheet" href="${ctx}/layui/css/layui.css" media="all">
<body class="layui-layout-body" style="font-size:20px">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo" style="font-size:28px;"><a href="${ctx}/welcome.do" ><font color=#54b5ff>会议签到系统</font></a></div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-left">
      <li class="layui-nav-item" id="meetingManage"><a href="javascript:void(0);" style="margin-left:40px;font-size:18px">会议管理</a></li>
      <li class="layui-nav-item" id="userManage"><a href="javascript:void(0);" style="margin-left:40px;font-size:18px">用户管理</a></li>
      <li class="layui-nav-item" id="meetingRoom"><a href="javascript:void(0);" style="margin-left:40px;font-size:18px">会议室管理</a></li>
      <li class="layui-nav-item" id="systemManage">
        <a href="javascript:void(0);" style="margin-left:40px;font-size:18px">系统管理</a>
        <dl class="layui-nav-child">
          <dd><a href="">邮件管理</a></dd>
          <dd><a href="">消息管理</a></dd>
          <dd><a href="">授权管理</a></dd>
        </dl>
      </li>
    </ul>
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;" style="font-size:18px">
          <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
          ${currentUser }
        </a>
        <dl class="layui-nav-child">
          <dd><a href="" style="font-size:18px">基本资料</a></dd>
          <dd><a href="" style="font-size:18px">修改密码</a></dd>
          <dd><a href="${ctx}/logout.do" style="font-size:18px">安全退出</a></dd>
        </dl>
      </li>
    </ul>
  </div>
</div>
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/layui/layui.js"></script>
<!-- <script>
//JavaScript代码区域
layui.use('element', function(){
  var element = layui.element;
	 
});
$('#userManage').on('click',function(){
	alert("进入");
	  $(window.parent.document).find('contentIframe').attr('src','https://blog.csdn.net/xiaoxiaoluckylucky/article/details/80083891');
	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
	  });
	  
	  function meetingManage(){
		  alert("进入");
		  $(window.parent.document).find('contentIframe').attr('src','https://blog.csdn.net/xiaoxiaoluckylucky/article/details/80083891');
		  $(this).addClass('layui-this').siblings().removeClass('layui-this');
		  }
</script>
</body>
</html> -->






<%-- <html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
<link rel="stylesheet" href="//cdn.bootcss.com/font-awesome/4.2.0/css/font-awesome.min.css"/>


<link rel="stylesheet" href="${ctx}/layui/css/layui.css">
 
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/layui/layui.js"></script>


<title>Insert title here</title>
</head>
<body class="layui-layout-body">
 <div class="layui-layout layui-layout-admin">  
   	  <div class="layui-header">
         <div class="layui-logo" style="font-size:28px">会议签到系统</div>
           <ul class="layui-nav layui-layout-left">
		       <c:forEach items="${mainmenus}" var="menu" varStatus="status">
		           <li class="layui-nav-item" role="menu" style="margin-left:40px">
		              <span style="float:left;padding-top: 20px;margin-left:5px;font-size:18px"><i class="${menu.icon }"  ></i></span>
		              <a href="${ctx}'+'${menu.url}"   style="float:right;font-size:18px;text-decoration: none">${menu.name}</a>
		               <dl class="layui-nav-child">
		              <c:forEach items="${ menu.childMenus}" var="childmenu" varStatus="childstatus">
			        	  <c:choose>
			          			<c:when test="${fn:length(childmenu.childMenus)>0}">
			          				
		                               <dd><a href="" style="text-decoration: none">${childmenu.name}</a></dd>
		                           
		                               <c:forEach items="${childmenu.childMenus}" var="submenu" varStatus="substatus">  
						                     <dd><a href="#" style="text-decoration: none">${submenu.name}</a><dd>
						               </c:forEach>
			          			</c:when>
			          			
			          			<c:otherwise>
			          			   
		                               <dd><a href="" style="text-decoration: none">${childmenu.name}</a></dd>
		                           
			          			</c:otherwise>
			          			
			          		</c:choose>
			        	</c:forEach>
			        	 </dl> 
			         </li>
			     </c:forEach>
			       
			  </ul>
			    <ul  class="layui-nav layui-layout-right" style="font-size:18px">
			      <li class="layui-nav-item">
			        <a href="#" style="text-decoration: none">当前用户:${currentUser }</a>
			        <dl class="layui-nav-child">
			        <dd><a href="javascript:void(0);" style="text-decoration: none" onclick="openChangePwdModal()" >修改密码</a></dd>
			        <dd><a href="${ctx}/logout.do" style="text-decoration: none">安全退出</a></dd>
			        </dl>
                 </li>
              </ul>
          </div>
       </div>
    </body>
</html> --%>