<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>历史任务管理</title>
	<link rel="stylesheet" type="text/css"
		  href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css"
		  href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/datagrid-detailview.js"></script>
	<script type="text/javascript">
		var pageContext = '${pageContext.request.contextPath}';
		var userId= '${sessionInfo.user.id }';
		var groupId= '${sessionInfo.group.id}';
	</script>
	<shiro:hasAnyRoles name="QuestionScene,QuestionAdviser,QuestionInitiator">
		<style type="text/css">
			table.gridtable {
				font-family: verdana, arial, sans-serif;
				font-size: 11px;
				color: #333333;
				border-width: 1px;
				border-color: #666666;
				border-collapse: collapse;
			}
			table.gridtable th {
				border-width: 1px;
				padding: 8px;
				border-style: solid;
				border-color: #666666;
				background-color: #dedede;
			}

			table.gridtable td {
				border-width: 1px;
				padding: 8px;
				border-style: solid;
				border-color: #666666;
				background-color: #ffffff;
			}
		</style>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/base/questionLiShi.js"></script>
	</shiro:hasAnyRoles>

	<shiro:hasAnyRoles name="factoryPlan,factoryPurchase,factoryQuality">
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/base/erpLiShi.js"></script>
	</shiro:hasAnyRoles>

	<shiro:hasAnyRoles name="Maintainer,OperationalManagers,Operators,ProjectSupervisor,Technician,ZoneAdmin">
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/base/repairLiShi.js"></script>
	</shiro:hasAnyRoles>
</head>
<body style="margin: 1px">
<table id="dg" title="历史任务管理" class="easyui-datagrid"
	   fitColumns="true" pagination="true" rownumbers="true"
	   fit="true" toolbar="#tb">
</table>
<div id="tb">
	<div>
		&nbsp;流程ID&nbsp;<input type="text" id="s_name" size="20" onkeydown="if(event.keyCode==13) searchTask()"/>
		<a href="javascript:searchTask()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
</div>

<div id="dlg2" class="easyui-dialog" style="width: 750px;height: 250px;padding: 10px 20px" closed="true">
	<table id="dg2" class="easyui-datagrid">
	</table>
</div>

<div id="dlg3" class="easyui-dialog" style="width: 750px;height: 350px;padding: 10px 20px" closed="true">
	<table id="dg3" class="easyui-datagrid">
	</table>
</div>
</body>
</html>