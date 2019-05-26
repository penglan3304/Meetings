<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>
<link rel="stylesheet" href="${ctx}/layui/css/layui.css" media="all">
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
	  <button class='layui-btn layui-btn-ks'  id="export">导出Excel</button>
	</div>
</div>
<div class="" style="width:100%;margin-left:5px;float: left;">
	<table class="layui-hide" style="margin:0px 0" id="perdetaillist" lay-filter="perdetaillist"></table>
</div>
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/layui/layui.js"></script>
<script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>

      </script>
<script>
    layui.use(['tree','table','layer','form','upload'], function(){
        var $ = layui.$;
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
        var id_=${id};
        
       table.render({
            elem: '#perdetaillist'
            ,url:'${ctx}/meeting/perdetaillist.do?id='+id_
            ,title: '参会信息列表'
            ,height: 550
            ,method:'POST'
            ,cols: [
                [
                	{field:'attendperson', title:'姓名',height:38}
                    ,{field:'signtime', title:'签到时间',height:38}
                    ,{field:'state', title:'状态 ',height:38}
                    ,{field:'absencereason', title:'缺席原因',height:38}
                    ,{fixed: 'right', title:'操作', toolbar: '#barDemo',height:38}
                ]
            ]
            ,page: true
        }); 
       
       table.on('tool(perdetaillist)', function(obj){ 
       	  var data = obj.data; 
       	  var layEvent = obj.event; 
       	  if(layEvent === 'detail'){ 
       		  $.ajax({
             		url: "${ctx}/user/detail.do?id="+data.personid,
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
       });
       
        $('#doSearch').on('click',function(){
        	var username=$("#name").val();
        	table.reload('perdetaillist', {
       		 where: {
       			username:username
                }
       	 	, page: {
                	curr: 1 //重新从第 1 页开始
            	}
            }); 
        });
        
        $('#export').on('click',function(){
        	var url="${ctx}/meeting/export.do?id="+id_;
        	window.location.href = url;
        	
        	
        	  });
    });
 </script>