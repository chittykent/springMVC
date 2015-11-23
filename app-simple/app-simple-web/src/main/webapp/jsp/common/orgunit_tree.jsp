<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%String ctx = request.getContextPath(); %>
<script src="<%=ctx %>/js/common/orgunit_tree.js"></script>
<form method="post" id="addRoleForm">
	<input type="hidden" id="mids" name="mids" />
	<input type="hidden" id="roleId" name="id"/>
	<table width="100%" style="float: left;">
		<tr>
			<td style="vertical-align: top; align="left">
					<ul id="moduleTree"></ul>
			</td>
			
			<!-- <td style="vertical-align: top; width: 310px;">
				<table id="roleGrid" align="left"></table>
			</td> -->
		</tr>
	</table>
</form>
