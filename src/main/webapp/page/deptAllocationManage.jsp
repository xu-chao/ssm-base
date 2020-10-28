<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>部门分配</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        var sessionPid = ${sessionDept.dept.pid};
        var pageContext;
        $(document).ready(function() {
            pageContext = $("#PageContext").val();
        })

    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/deptAllocation.js"></script>
</head>
<body class="easyui-layout">
<!-- 适应父容器大小 -->
<div data-options="region:'west',title:'使用中',split:true"
     style="width: 210%;">
    <ul id="tt"></ul>
</div>
<div data-options="region:'center'">
    <table id="grid"></table>
<%--    <div region="south" style="padding: 5px">--%>
<%--        <table id="pg" style="width:300px"></table>--%>
<%--    </div>--%>
</div>

<div id="insertDlg" class="easyui-dialog" title="隶属于该部门人员" closed="true" style="width:400px;height:200px;padding:10px"
     data-options="iconCls: 'icon-save',toolbar: '#dlg-toolbar',buttons: '#dlg-buttons'">
    <td>隶属于该部门人员：</td>
    <td>
    <input id="ss" class="easyui-combogrid" style="width:250px" data-options="
			panelWidth: 500,
<%--			multiple: true,--%>
			method: 'POST',
		    remoteSort: false,
            nowrap: false,
            fitColumns: true,
            pagination: true,
            rownumbers:true,
            fit: true,
			idField: 'id',
			textField:'id',
			queryParams:{deptPid:sessionPid},
			url: '${pageContext.request.contextPath}/user/userPage',
			columns: [[
				{field: 'cb', checkbox: true, align: 'center'},
                {field: 'id', title: '用户名/工号', width: 100, align: 'center'},
                {field: 'firstName', title: '姓', width: 100, align: 'center'},
                {field: 'lastName', title: '名', width: 100, align: 'center'},
                {field: 'email', title: '邮箱', width: 100, align: 'center'}
			]],
			fitColumns: true
		"/>
    </td>
</div>
<div id="dlg-toolbar" style="padding:2px 0">
    <table cellpadding="0" cellspacing="0" style="width:100%">
        <tr>
            <td style="padding-left:2px">
                <%--                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>--%>
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-help',plain:true">帮助</a>
            </td>
            <td style="text-align:right;padding-right:2px">
                <input class="easyui-searchbox" data-options="prompt:'  请输入...'" style="width:150px"></input>
            </td>
        </tr>
    </table>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:addDeptAllocation()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
    <input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
</div>

<!-- 树形菜单的右键设置 -->
<div id="mm" class="easyui-menu" style="width: 120px;">
    <div data-options="iconCls:'icon-add'">添加</div>
<%--    <div data-options="iconCls:'icon-save'">修改</div>--%>
<%--    <div data-options="iconCls:'icon-undo'">重命名</div>--%>
</div>
<!-- 树形菜单右键重命名设置 -->
<div id="renameDlg">
    <form id="renameForm" method="post">
        新名称:<input type="text" name="menuname" />
    </form>
</div>
</body>
</html>