<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>
<style type="text/css">
    a:hover { text-decoration: underline; }/* 鼠标移动到链接上 */
</style>
<link rel="stylesheet" href="/meetings/layui/css/layui.css" media="all">
<body class="layui-layout-body" style="font-size:20px">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo" style="font-size:18px;margin-left:280px"><font color=#bdbec0>欢迎登入会议签到系统</font></div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <div style="float:right;margin-right:200px;margin-top:8px"><span id="n1" class="layui-badge">${notifys }</span></div>
    <ul class="layui-nav layui-layout-right" style="margin-top:5px;margin-right:200px">
      <li class="layui-nav-item">
        <a href="javascript:;" style="font-size:18px">
          <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
          ${currentUser } 
        </a>
        <dl class="layui-nav-child">
          <dd><a href="javascript:void(0);" onclick="notify()" style="font-size:18px">消息中心<span class="layui-badge" style="margin-left:80px"id="n2" >${notifys }</span></a></dd>
          <dd><a href="javascript:void(0);" onclick="message()" style="font-size:18px">基本资料</a></dd>
          <dd><a href="javascript:void(0);" onclick="modify()" style="font-size:18px">修改密码</a></dd>
          <dd><a href="${ctx}/logout.do" style="font-size:18px">安全退出</a></dd>
        </dl>
      </li>
    </ul>
  </div>
  
  
  <div style="float:right;margin-right:300px">
  <ul >
      <li class="layui-nav-item"  style="float:left"><img src="<%=request.getContextPath()%>/img/logo.png" style="margin-right:560px"/> </li>
      <li class="layui-nav-item" id="first" style="float:left;margin-top:25px"><a href="javascript:void(0);" style="margin-left:40px;font-size:20px;color:#1e2b88"><strong>首页</strong></a></li>
      <!-- <li class="layui-nav-item" id="chatinfo" style="float:left;margin-top:25px"><a href="javascript:void(0);" style="margin-left:40px;font-size:20px;color:#1e2b88"><strong>实时聊天</strong></a></li> -->
      <li class="layui-nav-item" id="meetinginfo" style="float:left;margin-top:25px"><a href="javascript:void(0);" style="margin-left:40px;font-size:20px;color:#1e2b88"><strong>会议信息管理</strong></a></li>
      <li class="layui-nav-item" id="meetingManage" style="float:left;margin-top:25px"><a href="javascript:void(0);" style="margin-left:40px;font-size:20px;color:#1e2b88"><strong>会议管理</strong></a></li>
      <li class="layui-nav-item" id="userManage" style="float:left;margin-top:25px"><a href="javascript:void(0);" style="margin-left:40px;font-size:20px;color:#1e2b88"><strong>用户管理</strong></a></li>
      <li class="layui-nav-item" id="meetingRoom" style="float:left;margin-top:25px"><a href="javascript:void(0);" style="margin-left:40px;font-size:20px;color:#1e2b88"><strong>会议室管理</strong></a></li>
    <!--   <li class="layui-nav-item" id="systemManage" style="float:left">
        <a href="javascript:void(0);" style="margin-left:40px;font-size:25px;color:blue">系统管理</a>
        <dl class="layui-nav-child">
          <dd><a href="">邮件管理</a></dd>
          <dd><a href="">消息管理</a></dd>
          <dd><a href="">授权管理</a></dd>
        </dl>
      </li> -->
   </ul>
    </div>
<!--   <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      左侧导航区域（可配合layui已有的垂直导航）
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
        <li class="layui-nav-item layui-nav-itemed">
          <a class="" href="javascript:;">待办事项</a>
          <dl class="layui-nav-child">
            <dd><a href="javascript:void(0);">未报名</a></dd>
            <dd><a href="javascript:void(0);">未参加</a></dd>
            <dd><a href="javascript:void(0);">待被审核</a></dd>
            <dd><a href="javascript:void(0);">待审核</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item">
          <a href="javascript:;">已办事项</a>
          <dl class="layui-nav-child">
            <dd><a href="javascript:void(0);">已报名</a></dd>
            <dd><a href="javascript:void(0);">已参加</a></dd>
            <dd><a href="javascript:void(0);">已发布</a></dd>
            <dd><a href="javascript:void(0);">已审核</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item">
          <a href="javascript:;">其它</a>
          <dl class="layui-nav-child">
            <dd><a href="javascript:void(0);">审核不通过</a></dd>
            <dd><a href="javascript:void(0);">实时会议信息</a></dd>
            <dd><a href="javascript:void(0);">会议旁听</a></dd>
          </dl>
        </li>
      </ul>
    </div>
  </div> -->
 <div><iframe src="${ctx}/home/show.do" width="100%"  height="800px"  frameborder="0" id="contentIframe"></iframe></div>
</div>
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/layui/layui.js"></script>
 <script>
 $('#first').on('click',function(){
	 location.reload();
	  });
 
 
$('#meetingManage').on('click',function(){
	 $('#contentIframe').attr('src','${ctx}/meeting/show.do');
	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
	  });



$('#meetinginfo').on('click',function(){
	 $('#contentIframe').attr('src','${ctx}/welcome.do');
	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
	  });

$('#userManage').on('click',function(){
	 $('#contentIframe').attr('src','${ctx}/user/show.do');
	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
	  });

$('#meetingRoom').on('click',function(){
	 $('#contentIframe').attr('src','${ctx}/meetingroom/show.do');
	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
	  });
	  
/* $('#systemManage').on('click',function(){
	 $('#contentIframe').attr('src','https://blog.csdn.net/xiaoxiaoluckylucky/article/details/80083891');
	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
	  }); */
	  
var count=${notifys };
var status=${status};
if(count==0){
	$('#n1').css("display","none");
	$('#n2').css("display","none");
	
}
else{
	$('#n1').css("display","block");
	$('#n2').css("display","block");
}
if(status==2){
	$('#meetingManage').css("display","none");
	$('#userManage').css("display","none");
	$('#meetingRoom').css("display","none");
	
}
if(status==3){
	$('#userManage').css("display","none");
	$('#meetingRoom').css("display","none");
	
}
 </script>
<script>
//JavaScript代码区域
layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider'], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页
  ,layer = layui.layer //弹层
  ,table = layui.table //表格
  ,carousel = layui.carousel //轮播
  ,upload = layui.upload //上传
  ,element = layui.element //元素操作
  ,slider = layui.slider //滑块
	 
});
var userid=${userid};
$('#chatinfo').on('click',function(){
	 $.ajax({
		   url:"${ctx}/user/chatptp.do?topic="+userid+"&msg=",
		   type:"post",
		   async:true,
		   success:function(data){
			   layer.open({
					  type : 2,
				      title : "聊天室",
				      area: ['1000px', '600px'],
				      btn: ['关闭'],
				      content : '${ctx}/user/chat.do',
				      success: function(layero, index){
         		    	    var body = layer.getChildFrame('body', index);
         		    	    body.find('#chatinfo_').val(data);
         		    	  }
				  });
		   }
	 });
	
});
function notify(){
	layer.open({
			  type : 2,
		      title : "消息详情",
		      area: ['1000px', '600px'],
		      btn: ['关闭'],
		      content : '${ctx}/meeting/notifys.do',
		  });
}

function chat(){
	
}
function message(){
	var id=${userid};
	$.ajax({
		url: "${ctx}/user/detail.do?id="+id,
		type: "POST",
		dataType: "json",
		success: function(data){ 
				 layer.open({
     			  type : 2,
     		      title : "用户信息详情",
     		      area: ['600px', '400px'],
     		      btn: ['关闭'],
     		      content : '${ctx}/user/detailshow.do',
     		      yes: function(){   
     		    	  layer.closeAll();
     		      },
     		      success: function(layero, index){
     		    	    var body = layer.getChildFrame('body', index);
     		    	    body.find('#name').val(data.username);
     		    	    body.find('#email').val(data.email);
     		    	    body.find('#phone').val(data.phone);
     		    	    body.find('#depart').val(data.departname);
     		    	    body.find('#position').val(data.rolename);
     		    	   
     		    	  }
     		  });
			}
	  });
}
function modify(){
	var id=${userid};
	 layer.open({
		  type : 2,
	      title : "修改密码",
	      area: ['600px', '400px'],
	      content : '${ctx}/updatepassword.do?id='+id,
	  });
}

function backhome(){
	parent.location.reload();
	  }
</script>
</body>