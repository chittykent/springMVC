<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
</head>
<body>
	<table width="100%">
		<tr align="center">
			<td><img src="<%=request.getContextPath()%>/image/error.png"
				alt="" /></td>
		</tr>
		<tr align="center">
			<td style="font-size: 15px;">出错..${errMsg}</td>
		</tr>
	</table>

</body>
</html>