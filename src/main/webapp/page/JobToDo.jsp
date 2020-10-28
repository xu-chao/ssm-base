<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>待办任务管理</title>
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
    <script type="text/javascript">
        debugger
        var group_session = '${sessionInfo.groupId }';
        var pageContext = '${pageContext.request.contextPath}';
        var userId = '${sessionInfo.user.id }';
        var groupId = '${sessionInfo.group.id}';
    </script>
    <shiro:hasAnyRoles name="QuestionScene,QuestionAdviser,QuestionExaminer">
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/base/questionToDo.js"></script>
    </shiro:hasAnyRoles>
    <shiro:hasAnyRoles name="HYSexaminer">
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/base/huiyishi/huiyishiToDo.js"></script>
    </shiro:hasAnyRoles>
    <shiro:hasAnyRoles name="factoryPlan,factoryPurchase,factoryQuality">
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/base/erpToDo.js"></script>
    </shiro:hasAnyRoles>
    <shiro:hasAnyRoles name="Operators,ProjectSupervisor,Maintainer,OperationalManagers,Technician,ZoneAdmin">
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/base/repairToDo.js"></script>
    </shiro:hasAnyRoles>
    <shiro:hasAnyRoles name="PTAVManager,PTSupervisor,PTScene,PTEngineer">
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/base/planTask/planTaskToDo.js"></script>
    </shiro:hasAnyRoles>
</head>
<body style="margin: 1px">
<table id="dg" title="待办任务管理" fitColumns="true" pagination="true" rownumbers="true" fit="true" toolbar="#tb">
</table>
<div id="tb">
    <div>
        &nbsp;流程ID&nbsp;<input type="text" id="s_name" size="20" onkeydown="if(event.keyCode==13) searchTask()"/>
        <a href="javascript:searchTask()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>
<style type="text/css">
    /* 文本超长class */
    .textEllipsis{
        color:black;
        overflow: hidden;/*不允许滚动条*/
        white-space: nowrap;/*不允许文本换行*/
        text-overflow: ellipsis;/*文本超长显示省略号*/
    }

    /* 鼠标移上,显示全文class */
    .textEllipsis:hover {
        height: auto;
        word-break:break-all;
        white-space: pre-wrap;
        text-decoration: none;
    }

</style>
</body>
</html>