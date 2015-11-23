<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/jsp/common/header.jsp"></jsp:include>
<% String ctx = request.getContextPath(); 
   String code = request.getParameter("code");
   String userName = request.getParameter("userName");
   if(StringUtils.isEmpty(code)){
	   code = "ok";
   }
   if(StringUtils.isEmpty(userName)){
	   userName="";
   }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="金山养老—登录窗口" name="keywords" http-equiv="keywords" />
<meta content="金山养老—登录窗口" name="description" http-equiv="description" />
<title>颐和苑养老管理服务平台</title>
<link rel="stylesheet" href="<%=ctx %>/jsp/login/css/login.css"/>

	<script>
	var ctx = "<%=ctx%>";
	<%
	  if(code.equals("5001")){
		  out.print("$.messager.alert('提示信息','抱歉,用户名或密码错误,请重新输入!!','error');");
	  }
	%>
	
	$(function(){
		//居中
		 document.getElementById("userName").focus();
		 $("#userName").keydown(function(event){
		 	if(event.keyCode==13){
				login();
			}
		 })
		 $("#passWd").keydown(function(event){
		 	if(event.keyCode==13){
				login()
			}
		 })
		 
	});

	//登录
	function login() {
		if(!$('#loginForm').form('validate')){
			return ;
		}
		var errorMsg = "";
		var loginName = document.getElementById("userName");
		var password = document.getElementById("passWd");
		if(!loginName.value){
			errorMsg += "用户名不能为空!";
		}
		if(!password.value){
			errorMsg += "密码不能为空!";
		}

		if(errorMsg != ""){
			$.messager.alert('提示信息','抱歉,'+errorMsg,'error');
			return false;
		}else{
			$.post(ctx + "/page/index/login", {userName:$("#userName").val(),passWd:$("#passWd").val()},
					function(data){
						if(data.success){
							window.location.href= ctx+"/jsp/index.jsp";
						}else{
							$.messager.alert('提示信息','抱歉,账户名或密码错误!','error');
						}
			});
		}
		
	}
	
</script>
</head>
<body style="background:#55b4c6">
<div id="loginContent">
 <img src="<%=ctx %>/jsp/login/images/loginTitle.jpg" />
 <div class="loginFrame">
 <form id="loginForm" name="loginForm" method="post" action="<%=ctx%>/page/user/login">
 <div  class="login">
  <ul>
   <li><span>用户名：</span><input id="userName" name="userName" class="userName" type="text" /></li>
   <li><span>密码：</span><input id="passWd" name="passWd" class="pwd" type="password" /></li>
   <li><input class="loginBtn" value="登录" name="loginBtn" type="button" onclick="login()"/><input name="cancelBtn" class="cancelBtn" type="reset" value="重置" /></li>
   <!-- <li><a href="#">忘记密码？</a><a href="#">新用户注册</a></li> -->
  </ul>
  </div>
  </form>
 </div>
</div>
</body>
</html>
