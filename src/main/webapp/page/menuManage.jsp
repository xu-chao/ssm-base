<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>菜单管理</title>
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
        })
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/menu.js"></script>
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
                <td align="right">编号:</td>
                <td align="left"><input type="text" class="easyui-numberbox"
                                        name="menuid" /></td>
            </tr>
            <tr>
                <td align="right">名称:</td>
                <td align="left"><input name="menuname" type="text" /></td>
            </tr>
            <tr>
                <td align="right">对应URL:</td>
                <td align="left"><input name="url" type="text" /></td>
            </tr>
            <tr>
                <td align="right">图标样式:</td>
                <td align="left">
                    <select id="icon" class="easyui-combobox"
                            data-options="panelHeight:'100',required:true" editable="false" name="icon">
                        <option value="icon-log">icon-log</option>
                        <option value="icon-sys">icon-sys</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- 修改表单 -->
<div id="updateDlg">
    <form id="updateForm" method="post">
        <table style="text-align: center;">
            <tr>
                <td align="right">编号:</td>
                <td align="left"><input type="text" class="easyui-numberbox"
                                        name="menuid" /></td>
            </tr>
            <tr>
                <td align="right">名称:</td>
                <td align="left"><input name="menuname" type="text" /></td>
            </tr>
            <tr>
                <td align="right">对应URL:</td>
                <td align="left"><input name="url" type="text" /></td>
            </tr>
            <tr>
                <td align="right">图标样式:</td>
                <td align="left">
                    <select id="icon1" class="easyui-combobox"
                            data-options="panelHeight:'100',required:true" editable="false" name="icon">
                        <option value="icon-log">icon-log</option>
                        <option value="icon-sys">icon-sys</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">上级菜单编号:</td>
                <td align="left">
                    <input id="pid" name="pid" type="text"readOnly />
                    <input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
                </td>
            </tr>
            <tr>
                <td align="right">是否是父菜单:</td>
                <td align="left">
                    <select id="is_parent" class="easyui-combobox"
                            data-options="panelHeight:'100',required:true" editable="false" name="is_parent">
                        <option value="0">否</option>
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
        新名称:<input type="text" name="menuname" />
    </form>
</div>
</body>
</html>