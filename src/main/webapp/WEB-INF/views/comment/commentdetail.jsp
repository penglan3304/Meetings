<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>
<link rel="stylesheet" href="/meetings/layui/css/layui.css" media="all">
<link rel="stylesheet" href="/meetings/css/detail.css">
<link rel="stylesheet" href="/meetings/css/detail1.css">
<link rel="stylesheet" href="/meetings/css/sandalstrap.css">

<div class="comment-box">
   <c:if test="${length!=0 }">
      <div class="comment-list-container">
		<a id="comments"></a>
		<div class="comment-list-box" style="max-height: none;" id="comment-list">
		    <c:forEach items="${comments }" var="infos" varStatus="s">
		       <ul class="comment-list">
		         <li class="comment-line-box d-flex" data-commentid="6421322" data-replyname="qq_21801399">        
		           <a target="_blank" href=""><img src="http://t.cn/RCzsdCq" class="layui-nav-img"></a>          
		             <div class="right-box ">            
		                <div class="info-box">
		                
		                 <c:choose>
		                       <c:when test="${infos.replayid!=0 }">
		                              <a target="_blank" href=""><span class="name ">${infos.username } 回复 ${infos.replayname }：</span></a>  
		                        </c:when>
		                         <c:otherwise>
		                              <a target="_blank" href=""><span class="name ">${infos.username }：</span></a>  
			          			 </c:otherwise>     
		                   </c:choose>     
		                    
		                       <span class="comment">${infos.content }</span>
		                       <span class="date" title="${infos.commenttime }">(${infos.commenttime }</span>
		                       <span class="floor-num">#${s.count }楼)</span>
		                       <c:choose>
		                             <c:when test="${name==infos.username }">
		                              <span class="opt-box">
		                                  <a  href="javascript:void(0);" onclick="del(${infos.id})" class="btn btn-link-blue btn-report">删除</a></span>
		                             </c:when>
		                             <c:otherwise>
		                              <span class="opt-box">
		                                   <a href="javascript:void(0);" onclick="add(${infos.id})" class="btn btn-link-blue btn-reply" data-type="reply">回复</a></span>
			          			    </c:otherwise>
		                           </c:choose>    
		                  </div>
		             </div>
		         </li>
		      </ul>
		      </c:forEach>   
		  </div>
	 </div>
	</c:if>
	
	<div class="comment-edit-box d-flex" style="800px">
		<a id="commentsedit"></a>
		<div class="user-img">
			<a href="javascript:void(0);" target="_blank">
				<img class="show_loginbox" src="<%=request.getContextPath()%>/img/comment.png">
			</a>
		</div>
		<div>
			<textarea  id="comment_content" placeholder="想说点什么"></textarea>
			<input type="submit" class="btn btn-sm btn-red btn-comment" id="submit" value="发表评论">
		</div>
	</div>
</div>






<%--   <div style="margin-top:30px;width:100%">
     <input type="text" id="meetingname" style="border:none;font-size:30px;width:150px;float:left;margin-left:300px" class="layui-input" readonly>
     <input type="text" id="createname" style="border:none;width:350px;float:left;font-size:25px"  class="layui-input" readonly>
  </div>
<div style="margin-top:30px">
<c:choose >
  <c:when test="${length==0 }">
           <div style="font-size:32px;text-align:center">暂时无人评价</div>
  </c:when>
  <c:otherwise>
      <c:forEach items="${comments }" var="infos">
         <div>
              <div>
                <span style="font-size:20px;color:blue"><strong>${infos.username }</strong></span>
                <span>${infos.commenttime }</span>
              </div>
              <div>${infos.content }</div>
         </div>
      </c:forEach>
  </c:otherwise>
</c:choose>
</div> --%>
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/layui/layui.js"></script>
<script>
    layui.use(['tree','table','layer','form','upload'], function(){
        var $ = layui.$;
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
        var upload = layui.upload;
       
    });
    
    var meetingid=${id};
    $("#submit").on('click',function(){
    	var content=$("#comment_content").val();
    	//alert(content);
    	if(content.length==0){
    		alert("请输入评论内容");
    		return false;
    	}
    	else{
    		$.ajax({
           		url: "${ctx}/meeting/addcomment.do?content="+content+"&meetingid="+meetingid+"&replayid=0",
           		type: "POST",
           		dataType: "json",
          		success: function(data){ 
          			if(data==0){
          				location.reload();
          			}
          			
          		},
          		error:function(){
          			alert("评论错误");
          		}
        	});
    	}
    	
    });
    
    function del(id){
   	 $.ajax({
      		url: "${ctx}/meeting/delcomment.do?id="+id,
      		type: "POST",
      		dataType: "json",
     		success: function(data){ 
     			if(data==0){
      				location.reload();
      			}
      			
      		},
      		error:function(){
      			alert("错误");
      		}
   		  });
   }
    function add(id){
    	 layer.prompt({
 	    	formType:2,
 	    	title:'请填写回复内容',
 	    	area:['300px','100px']
 	    },function(value,index,elem){
 	    	var value= encodeURIComponent(value);
 	    	$.ajax({
         		url: "${ctx}/meeting/addcomment.do?content="+value+"&meetingid="+meetingid+"&replayid="+id,
         		type: "POST",
         		dataType: "json",
        			success: function(data){            
             		if(data==1){
              			layer.msg("回复失败", {icon: 5});                        
             		}else{    
             			location.reload();
            			 }
         		},
         		error:function(){
         			layer.msg("回复失败", {icon: 5}); 
         		}

     		});
 	    });
    }
 </script>