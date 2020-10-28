<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>部门管理</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        var pageContext;
        $(document).ready(function() {
            pageContext = $("#PageContext").val();
            $('#selectPid').combotree({
                url:pageContext+'/dept/deptList',
                required: true
            });
        })
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dept.js"></script>
</head>
<body class="easyui-layout">
<!-- 适应父容器大小 -->
<div data-options="region:'west',title:'使用中',split:true"
     style="width: 210%;">
    <ul id="tt"></ul>
</div>
<div data-options="region:'center'">
    <table id="grid"></table>
</div>
<!-- 添加表单 -->
<div id="insertDlg">
    <form id="insertForm" method="post">
        <table style="width: 300px; text-align: center;">
            <tr>
                <td align="right">部门ID:</td>
                <td align="left"><input type="text" class="easyui-numberbox"
                                        name="deptID" /></td>
            </tr>
            <tr>
                <td align="right">部门名称:</td>
                <td align="left"><input name="deptName" type="text" /></td>
            </tr>
            <tr>
                <td align="right">部门地址:</td>
                <td align="left"><input name="deptAddress" type="text" /></td>
            </tr>
            <tr>
                <td align="right">部门负责人:</td>
                <td align="left"><input name="deptMaster" type="text" /></td>
            </tr>
            <tr>
                <td align="right">部门电话:</td>
                <td align="left"><input name="deptPhone" type="text" /></td>
            </tr>
            <tr>
                <td align="right">部门传真:</td>
                <td align="left"><input name="deptFax" type="text" /></td>
            </tr> <tr>
                <td align="right">部门Email:</td>
                <td align="left"><input name="deptEmail" type="text" /></td>
            </tr> <tr>
                <td align="right">部门备注:</td>
                <td align="left"><input name="deptRemarks" type="text" /></td>
            </tr>
            <tr>
                <td align="right">上级部门:</td>
                <td align="left">
                    <input id="selectPid" name="pid" type="text" />
                    <input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
                </td>
            </tr>
            <tr>
                <td align="right">是否是父菜单:</td>
                <td align="left">
                    <select id="is_parent" class="easyui-combobox"
                            data-options="panelHeight:'100',required:true" editable="false" name="is_parent" >
                        <option value="0" selected="selected">否</option>
                        <option value="1">是</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- 修改表单 -->
<div id="updateDlg">
    <form id="updateForm" method="post">
        <table style="width: 300px; text-align: center;">
            <tr>
                <td align="right">部门ID:</td>
                <td align="left"><input type="text" class="easyui-numberbox"
                                        name="deptID" /></td>
            </tr>
            <tr>
                <td align="right">部门名称:</td>
                <td align="left"><input name="deptName" type="text" /></td>
            </tr>
            <tr>
                <td align="right">部门地址:</td>
                <td align="left"><input name="deptAddress" type="text" /></td>
            </tr>
            <tr>
                <td align="right">部门负责人:</td>
                <td align="left"><input name="deptMaster" type="text" /></td>
            </tr>
            <tr>
                <td align="right">部门电话:</td>
                <td align="left"><input name="deptPhone" type="text" /></td>
            </tr>
            <tr>
                <td align="right">部门传真:</td>
                <td align="left"><input name="deptFax" type="text" /></td>
            </tr> <tr>
            <td align="right">部门Email:</td>
            <td align="left"><input name="deptEmail" type="text" /></td>
        </tr> <tr>
            <td align="right">部门备注:</td>
            <td align="left"><input name="deptRemarks" type="text" /></td>
        </tr>
            <tr>
                <td align="right">上级部门:</td>
                <td align="left">
                    <input id="selectPid" name="pid" type="text" />
                </td>
            </tr>
            <tr>
                <td align="right">是否是父菜单:</td>
                <td align="left">
                    <select id="is_parent" class="easyui-combobox"
                            data-options="panelHeight:'100',required:true" editable="false" name="is_parent" >
                        <option value="0" selected="selected">否</option>
                        <option value="1">是</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- 树形菜单的右键设置 -->
<div id="mm" class="easyui-menu" style="width: 120px;">
    <div data-options="iconCls:'icon-add'">添加</div>
    <div data-options="iconCls:'icon-save'">修改</div>
    <div data-options="iconCls:'icon-undo'">重命名</div>
</div>
<!-- 树形菜单右键重命名设置 -->
<div id="renameDlg">
    <form id="renameForm" method="post">
        新名称:<input type="text" name="deptName" />
    </form>
</div>
</body>
</html>