<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>REEOR</title>
<script language="JavaScript">
alert("登录超时请重新登录 ")
top.location.herf="${ctx }/";
</script>
</head>
<body>

</body>
</html>