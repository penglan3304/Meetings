<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>
 <%-- <link rel="stylesheet" href="/meetings/layui/css/layui.css" media="all">
  <body>
     <ul class="layui-nav layui-nav-tree layui-nav-side">
  <li class="layui-nav-item layui-nav-itemed">
    <a href="javascript:;">默认展开</a>
    <dl class="layui-nav-child">
      <dd><a href="javascript:;">选项1</a></dd>
      <dd><a href="javascript:;">选项2</a></dd>
      <dd><a href="">跳转</a></dd>
    </dl>
  </li>
  <li class="layui-nav-item">
    <a href="javascript:;">解决方案</a>
    <dl class="layui-nav-child">
      <dd><a href="">移动模块</a></dd>
      <dd><a href="">后台模版</a></dd>
      <dd><a href="">电商平台</a></dd>
    </dl>
  </li>
  <li class="layui-nav-item"><a href="">产品</a></li>
  <li class="layui-nav-item"><a href="">大数据</a></li>
</ul>
 <div style="padding: 20px;width:1680px;margin-left:200px; background-color: #F2F2F2;">
  <div class="layui-row layui-col-space10">
    <div class="layui-col-md4">
      <div class="layui-card" style="height:250px">
        <div class="layui-card-header" style="font-size:20px">待办事项</div>
         <div class="layui-card-body">
             <button class="layui-btn layui-btn-lg"  style="margin-left:10px;width:240px;height:80px;background-color:#54b5ff" id="noattend">未报名<span class="layui-badge">${noattend }</span></button>
             <button class="layui-btn layui-btn-lg" style="width:240px;height:80px;background-color:#54b5ff" id="waitchecked">待被审核<span class="layui-badge">${checkedcount }</span></button>
             <button class="layui-btn layui-btn-lg" style="width:240px;height:80px;margin-top:10px;background-color:#54b5ff" id="nostart">未参加<span class="layui-badge">${absent }</span></button>
             <button class="layui-btn layui-btn-lg" style="width:240px;height:80px;margin-top:10px;margin-rigth:100px;background-color:#54b5ff" id="waitcheck">待审核<span class="layui-badge">${checkcount }</span></button>
        </div>
      </div>
    </div>
 
    <div class="layui-col-md4">
      <div class="layui-card" style="height:250px">
        <div class="layui-card-header" style="font-size:20px">已办事项</div>
         <div class="layui-card-body">
             <button class="layui-btn layui-btn-lg" style="margin-left:10px;width:240px;height:80px;background-color:#54b5ff" id="attended">已报名</button>
             <button class="layui-btn layui-btn-lg" style="width:240px;height:80px;background-color:#54b5ff" id="publish">已发布</button>
             <button class="layui-btn layui-btn-lg" style="width:240px;height:80px;margin-top:10px;background-color:#54b5ff" >已参加</button>
             <button class="layui-btn layui-btn-lg" style="width:240px;height:80px;margin-top:10px;margin-rigth:100px;background-color:#54b5ff" id="checked">已审核</button>
        </div>
      </div>
    </div>
    
    
    <div class="layui-col-md4">
      <div class="layui-card" style="height:250px">
        <div class="layui-card-header" style="font-size:20px">其他</div>
         <div class="layui-card-body">
             
             <button class="layui-btn layui-btn-lg" style="margin-left:10px;width:240px;height:80px;background-color:#54b5ff" id="checkednopassshow">审核不通过<span class="layui-badge">${nopasscount }</span></button>
             <button class="layui-btn layui-btn-lg" style="margin-left:10px;width:240px;height:80px;background-color:#54b5ff" id="information">实时会议信息</button>
              <button class="layui-btn layui-btn-lg" style="margin-left:10px;width:240px;height:80px;margin-top:10px;background-color:#54b5ff" id="listen">会议旁听</button>
             
            
        </div>
      </div>
    </div>
  </div>
    <iframe src="${ctx}/meeting/information.do" width="100%"  height="600px"  frameborder="0" id="contentIframes"></iframe> 
</div> 
 --%> 
 <link rel="stylesheet" href="/meetings/layui/css/layui.css" media="all">
 <link href="/meetings/css/menu.css" rel="stylesheet" type="text/css" />
<link href="http://www.lingshi.com/templates/default/css/reset.css" rel="stylesheet" type="text/css" />
<style>
.menu_sp .i-list { 
position:absolute; left:119px; padding:10px 15px; background:#fff; display:none; width:200px; height:435px;
border:none; overflow:hidden; z-index:0; }
</style>
<div class="menu" style="margin-left:280px;float:left;border:1px solid;background-color:#333">
		<div class="menu_list">
								<h3 class=""><a href="javascript:void(0);" class="new_sort1" style="font-size:20px" title="待办事项">待办事项</a></h3>
				<div class="i-list l1" style="border:1px solid">
						<ul class="fixed" >
								<li id="noattend"><a title="未报名"  href="javascript:void(0);" style="font-size:16px">未报名<span class="layui-badge">${noattend }</span></a></li>
								<li id="nostart"><a title="未参加"  href="javascript:void(0);" style="font-size:16px">未参加<span class="layui-badge">${absent }</span></a></li>
								<li id="waitchecked"><a title="待被审核"  href="javascript:void(0);" style="font-size:16px" >待被审核<span class="layui-badge">${checkedcount }</span></a></li>
								<li id="waitcheck"><a title="待审核"  href="javascript:void(0);" style="font-size:16px">待审核<span class="layui-badge">${checkcount }</span></a></li>
						</ul>
				</div>
			</div>
			<div class="menu_list">
								<h3 class=""><a href="javascript:void(0);" class="new_sort2" style="font-size:20px" title="已办事项">已办事项</a></h3>
				<div class="i-list l2" style="border:1px solid">
						<ul class="fixed">
								<li id="attended"><a title="已报名"  href="javascript:void(0);" style="font-size:16px">已报名</a></li>
								<li id="publish"><a title="已发布"  href="javascript:void(0);" style="font-size:16px">已发布</a></li>
								<li id="hasattend"><a title="已参加"  href="javascript:void(0);" style="font-size:16px">已参加</a></li>
								
						</ul>
				</div>
			</div>
			<div class="menu_list">
								<h3 class=""><a href="javascript:void(0);" class="new_sort3" style="font-size:20px"  title="其它">其它</a></h3>
				<div class="i-list l3" style="border:1px solid">
						<ul class="fixed">
								<li id="checkednopassshow"><a title="审核不通过"  href="javascript:void(0);" style="font-size:16px">审核不通过<span class="layui-badge">${nopasscount }</span></a></li>
								<li id="information"><a title="实时会议信息"  href="javascript:void(0);" style="font-size:16px">实时会议信息</a></li>
								<li id="listen"><a title="会议旁听"  href="javascript:void(0);" style="font-size:16px">会议旁听</a></li>
								<li id="checked"><a title="已审核"  href="javascript:void(0);" style="font-size:16px">会议操作</a></li>
						</ul>
				</div>
			</div>
	

	</div>  
	<div style="float:left;width:1100px" >
	  <iframe src="${ctx}/meeting/information.do" style="width:100%;height:655px;background-color:#54b5ff;"   frameborder="0" id="contentIframes"></iframe> 
	</div>
	
<div style="float:left;margin-left:280px;margin-top:20px;height:200px">
<div style="float:left">
   <i class="layui-icon layui-icon-flag" style="font-size: 120px;color: #1E9FFF;float:left"></i>
   <p style="margin-top:20px;margin-left:150px;font-size:20px"><strong>会议创建须知</strong></p>
   <p style="margin-top:5px">
     1.在会议管理中创建会议，创建时注意选好参会部门<br/>
     2.创建时默认会议可旁听，意味着所有人都可参会
   </p>
</div> 
<div style="float:left;margin-left:140px"> 
   <i class="layui-icon layui-icon-template" style="font-size: 120px; color: #1E9FFF;float:left"></i> 
</div>
<div style="float:left;width:240px">
<p style="margin-top:20px;margin-left:50px;font-size:20px"><strong>会议进行须知</strong></p>
   <p style="margin-top:5px">
      1.签到时需到已审核会议中点击开始签到进行人员签到<br/>
      2.签到完成后点击开始会议，会议正式开始
   </p>
</div>
<div style="float:left;margin-left:120px">
   <i class="layui-icon layui-icon-read" style="font-size: 120px; color: #1E9FFF;float:left"></i> 
</div>
<div style="float:left;width:240px;margin-left:10px">
 <p style="margin-top:20px;margin-left:50px;font-size:20px"><strong>会议结束须知</strong></p>
   <p style="margin-top:5px">
        1.会议结束后点击结束会议，会议结束<br/>
        2.会议管理-->查看人员信息中导出参会人员签到信息
   </p>
</div>
</div>
<script src="${ctx}/layui/layui.js"></script>    
<script src="${ctx}/js/jquery.min.js"></script>
<script>

$('.menu_list').mousemove(function(){
	$(this).find('.i-list').show();
	$(this).find('h3').addClass('hover');
});
$('.menu_list').mouseleave(function(){
	$(this).find('.i-list').hide();
	$(this).find('h3').removeClass('hover');
});
    $("#waitcheck").on('click',function(){
    	 $('#contentIframes').attr('src','${ctx}/meeting/waitshow.do');
   	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
   	  });
    
    
    $("#waitchecked").on('click',function(){
   	 $('#contentIframes').attr('src','${ctx}/meeting/waitcheckedshow.do');
  	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
  	  });
    
    $("#checked").on('click',function(){
      	 $('#contentIframes').attr('src','${ctx}/meeting/checkedshow.do');
     	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
     	  });
    
    $("#checkednopassshow").on('click',function(){
     	 $('#contentIframes').attr('src','${ctx}/meeting/checkednopassshow.do');
    	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
    	  });
    
    
    $("#noattend").on('click',function(){
    	 $('#contentIframes').attr('src','${ctx}/meeting/noattendshow.do');
   	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
   	  });
    
    $("#nostart").on('click',function(){
   	 $('#contentIframes').attr('src','${ctx}/meeting/leaveshow.do');
  	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
  	  });
    
    
    $("#publish").on('click',function(){
      	 $('#contentIframes').attr('src','${ctx}/meeting/publishedshow.do');
     	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
     	  });
    $("#signsss").on('click',function(){
    	document.location.href = "${ctx}/meeting/signs.do"
    });
    $("#information").on('click',function(){
    	 $('#contentIframes').attr('src','${ctx}/meeting/information.do');
    	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
    	//document.location.href = "${ctx}/meeting/information.do"
    });
    
    $("#attended").on('click',function(){
   	 $('#contentIframes').attr('src','${ctx}/meeting/attendedshow.do');
   	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
   	//document.location.href = "${ctx}/meeting/information.do"
   });
    $("#listen").on('click',function(){
      	 $('#contentIframes').attr('src','${ctx}/meeting/listenshow.do');
      	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
      	//document.location.href = "${ctx}/meeting/information.do"
      });
    
      $("#hasattend").on('click',function(){
    	   	 $('#contentIframes').attr('src','${ctx}/meeting/hasattendshow.do');
    	   	  $(this).addClass('layui-this').siblings().removeClass('layui-this');
    	   	//document.location.href = "${ctx}/meeting/information.do"
    	   });
    </script>
    </body>