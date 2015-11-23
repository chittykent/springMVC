<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/jsp/common/header.jsp"></jsp:include>
<%String ctx = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户注册</title>
    <link rel="stylesheet" href="<%=ctx %>/jsp/login/css/public.css"/>
    <link rel="stylesheet" href="<%=ctx %>/jsp/login/css/register.css"/>
    <script src="<%=ctx %>/js/login/register.js"></script>
</head>
<body>
<div class="title wrap b6">新用户注册</div>
<div class="main wrap">
<form id="registerForm" name="registerForm" >
    <div class="font1">欢迎您注册！</div>
    <div class="font4">帐户名：&nbsp;<input id="userName" name="userName" type="text" required="true" class="field2 easyui-validatebox" value="" size="20"></div>
    <div class="font4">密    &nbsp;&nbsp;码：<input id="passWd" name="passWd" type="password" required="true" class="field2 easyui-validatebox" value="" size="20"></div>
    <div class="font4">昵      &nbsp;&nbsp;称：<input id="nickName" name="nickName" type="text" size="20" required="true" class="field2 easyui-validatebox"></div>
    <div class="font4">手机号码:<input id="phoneNum" name="phoneNum" type="text" size="20" class="field2"></div>
    <div class="font4">邮    &nbsp;&nbsp;箱：<input id="email" name="email" type="text" size="20" class="field2 easyui-validatebox" required="true" validType="email"></div>

    <div class="font2"><a href="<%=ctx%>/index.jsp">返回登录界面?</a></div>
    <div class="font3">
    <img alt="提交" src="images/zc_11.gif" onclick="doPostData()">
    </div>
</form>
</div>
<div class="foot">
2014-2015 版权所有
</div>
</body>
</html>