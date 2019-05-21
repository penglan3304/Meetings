<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>
<link rel="stylesheet" href="/meetings/layui/css/layui.css" media="all">
<div style="margin-bottom: 16px;">
  <div class="layui-inline" >
    <form class="layui-form" style="margin-top:5px">
	  <div class="layui-inline" >
          <label class="layui-form-label">选择条件</label>
               <div class="layui-input-block">
			      <select id="state" lay-verify="required" lay-filter="state">
			        <option value=""></option>
			        <option value="0">空闲</option>
			        <option value="1">已预定</option>
			        <option value="2">使用中</option>
			      </select>
			   </div>
	 </div>
	 
	 
	  <div class="layui-inline" >
      	<label class="layui-form-label" style="padding:8px 15px;line-height:30px;">会议室名称</label>
      	<div class="layui-input-inline">
           <input type="text"  id="name" lay-verify="title" autocomplete="off" class="layui-input">
        </div>
      </div>
  	</form>
  	</div>
	  <button class='layui-btn layui-btn-ks'  id="doSearch">搜索</button>
	  <button class='layui-btn layui-btn-ks'  id="add">添加</button>
</div>
<div class="" style="width:100%;margin-left:5px;float: left;">
	<table class="layui-hide" style="margin:0px 0" id="meetingroomlist" lay-filter="meetingroomlist"></table>
</div>

<script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
 
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>


      </script>
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/layui/layui.js"></script>
<script>
layui.use(['tree','table','layer','form'], function(){
        var $ = layui.$;
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
        table.render({
            elem: '#meetingroomlist'
            ,url:'/meetings/meetingroom/meetingroomlist.do'
            ,title: '会议列表'
            ,height: 550
            ,method:'POST'
            ,cols: [
                [
                	 {field:'name', title:'会议室名称',height:38}
                    ,{field:'state', title:'会议室状态',height:38}
                    ,{field:'createname', title:'创建人',height:38}
                    ,{field:'createtime', title:'创建时间',height:38}
                    ,{fixed: 'right', title:'操作', toolbar: '#barDemo',height:38}
                ]
            ]
            ,page: true
        });
        form.on('select(state)', function (data) {
            state = data.value;
            table.reload('meetingroomlist', {
          		 where: {
          			state:state
                   }
          	 	, page: {
                   	curr: 1 //重新从第 1 页开始
               	}
               }); 
        });
        
        table.on('tool(meetingroomlist)', function(obj){ 
      	  var data = obj.data; 
      	  var layEvent = obj.event; 
      	  if(layEvent === 'detail'){ 
      		layer.open({
   			  type : 2,
   		      title : "会议室详情",
   		      area: ['600px', '400px'],
   		      btn: ['关闭'],
   		      content : '${ctx}/meetingroom/detail.do?id='+data.id+'&name='+data.name,	
   		   success: function(layero, index){
		    	    var body = layer.getChildFrame('body', index);
		    	    body.find('#meetingroomname').val(data.name);
		    	    body.find('#state').val(data.state);
		    	    body.find('#createname').val(data.createname);
		    	    body.find('#time').val(data.createtime);
		    	  }
      		  });
      		 
      	  } 
      else if(layEvent === 'del'){ //删除
      	    layer.confirm('是否删除该条会议室', function(index){
      	    	$.ajax({
              		url: "${ctx}/meetingroom/del.do?ids="+data.id,
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
      	    
      	  }
        });
        
        
        $('#doSearch').on('click',function(){
        	var meetingroomname=$("#name").val();
        	table.reload('meetingroomlist', {
       		 where: {
       			name:meetingroomname
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
		      title : "添加会议室",
		      area: ['800px', '750px'],
		      content : '${ctx}/meetingroom/add.do'
	});
});
        
</script>