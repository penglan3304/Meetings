<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>
<link rel="stylesheet" href="/meetings/layui/css/layui.css" media="all">
 <div style="width:100%;height:46px;background-color: #f2f2f2;">
<div style="margin-bottom: 16px;">
  <div class="layui-inline" >
    <form class="layui-form" style="margin-top:5px">
	  <div class="layui-inline" >
      	<label class="layui-form-label" style="padding:8px 15px;line-height:30px;">姓名</label>
      	<div class="layui-input-inline">
           <input type="text"  id="name" lay-verify="title" autocomplete="off" class="layui-input">
        </div>
      </div>
  	</form>
  	</div>
	  <button class='layui-btn layui-btn-ks'  id="doSearch">搜索</button>
	  <button class='layui-btn layui-btn-ks'  id="add">添加</button>
	</div>
</div>
<div class="" style="width:100%;margin-left:5px;float: left;">
	<table class="layui-hide" style="margin:0px 0" id="userlist" lay-filter="userlist"></table>
</div>
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/layui/layui.js"></script>
<script>
layui.use(['laydate','form'], function(){
	  var laydate = layui.laydate;
	  var form = layui.form;
	  
	  
	});
</script>
<script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>

        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>

        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>


      </script>
<script>
    layui.use(['tree','table','layer','form','upload'], function(){
        var $ = layui.$;
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
        var upload = layui.upload;

        
        
       table.render({
            elem: '#userlist'
            ,url:'/meetings/user/userlist.do'
            ,title: '会议列表'
            ,height: 550
            ,method:'POST'
            ,cols: [
                [
                	{checkbox: true}
                    ,{field:'username', title:'姓名',height:38}
                    ,{field:'email', title:'邮箱',height:38}
                    ,{field:'phone', title:'电话',height:38}
                    ,{field:'createtime', title:'创建时间',height:38}
                    ,{field:'modifytime', title:'修改时间',height:38}
                    ,{field:'departname', title:'所属部门',height:38}
                    ,{fixed: 'right', title:'操作', toolbar: '#barDemo',height:38}
                ]
            ]
            ,page: true
        }); 
        table.on('tool(userlist)', function(obj){ 
      	  var data = obj.data; 
      	  var layEvent = obj.event; 
      	  if(layEvent === 'detail'){ 
      		  $.ajax({
            		url: "${ctx}/user/detail.do?id="+data.id,
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
      else if(layEvent === 'del'){ //删除
      	    layer.confirm('是否删除该用户', function(index){
      	    	$.ajax({
              		url: "${ctx}/user/deluser.do?id="+data.id,
              		type: "POST",
              		dataType: "json",
             			success: function(data){            
                  		if(data==1){
                   			layer.msg("删除失败", {icon: 5});                        
                  		}else{    
                      		obj.del();
                     			layer.closeAll();
			                    location.reload();
                 			 }
              		},
              		error:function(){
              			layer.msg("删除失败", {icon: 5}); 
              		}

          		});
      	      
      	    });
      	  } 
           
       else if(layEvent === 'edit'){ //编辑
    	   $.ajax({
         		url: "${ctx}/user/findrole.do?id="+data.id,
         		type: "POST",
         		dataType: "json",
        			success: function(data1){ 
        				layer.open({
        		  			  type : 2,
        		  		      title : "用户信息编辑",
        		  		      area: ['600px', '500px'],
        		  		      content : '${ctx}/user/edit.do?id='+data.id,
        		  		      success: function(layero, index){
        		  		    	    var body = layer.getChildFrame('body', index);
        		  		    	    body.find('#username').val(data.username);
        		  		    	    body.find('#email').val(data.email);
        		  		    	    body.find('#phone').val(data.phone);
        		  		    	    body.find('#departdiv').find("[lay-value='" +data.departid + "']").click();
        		  		    	    body.find('#positiondiv').find("[lay-value='" +data1.roleid + "']").click();
        		  		    	  }
        		  		  });
        			}
    	   });
         }
      
      });
      	
        $('#doSearch').on('click',function(){
        	var username=$("#name").val();
        	table.reload('userlist', {
       		 where: {
       			username:username
                }
       	 	, page: {
                	curr: 1 //重新从第 1 页开始
            	}
            }); 
        });
       
    });
    
    
    
    
    $('#add').on('click',function(){
    	layer.open({
 			  type : 2,
 		      title : "添加用户",
 		      area: ['800px', '750px'],
 		      content : '${ctx}/user/add.do'
    	});
    });
 </script>