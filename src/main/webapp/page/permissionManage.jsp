<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>权限管理</title>
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
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/date.format/date.format.js"></script>
    <script type="text/javascript">

        var id = '';
        var name = '';
        var url = '';

        function formatAction(val, row) {
            return "<a href='javascript:openOnlineDetail(\"" + row.perssionName + "\")'>详情</a>";
        }

        function openOnlineDetail(perssionName) {
            $("#dlg2").dialog("open").dialog("setTitle", "详细情况");
        }

        function openPermissionAddDiglog() {
            $("#dlg").dialog("open").dialog("setTitle", "添加权限信息");
            $("#flag").val(1);
        }

        function checkData() {
            if ($("#name").val() == '') {
                $.messager.alert("系统系统", "请输入权限名称！");
                $("#name").focus();
                return;
            }
            if ($("#url").val() == '') {
                $.messager.alert("系统系统", "请输入权限路径！");
                $("#url").focus();
                return;
            }
            var flag = $("#flag").val();
            if (flag == 1) {
                $.post("${pageContext.request.contextPath}/permission/existPermissionID.action", {permissionName: $("#name").val()}, function (result) {
                    if (result.success) {
                        $.messager.alert("系统系统", "该权限名称已存在，请更换下！");
                        $("#name").focus();
                    } else {
                        savePermission();
                    }
                }, "json");
            } else {
                updatePermission();
            }
        }

        function savePermission() {
            $("#fm").form("submit", {
                url: '${pageContext.request.contextPath}/permission/permissionSave.action',
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统系统", "保存成功！");
                        resetValue();
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
        }

        function updatePermission() {
            var r = $("#fm").form("validate");//做表单字段验证，当所有字段都有效的时候返回true。
            id = $('#dg').datagrid("getSelected").id;
            name = $("#name").val();
            url = $("#url").val();
            var data = {
                id: id,
                name: name,
                url: url
            }
            if (r) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/permission/updatePermission.action",
                    type: "post",
                    data: data,
                    dataType: "json",
                    success: function (result) {
                        if (result.success) {
                            $.messager.alert("系统提示", "保存成功！");
                            resetValue();
                            $("#dlg").dialog("close");
                            $("#dg").datagrid("reload");
                        } else {
                            $.messager.alert("系统提示", "保存失败！");
                            return;
                        }
                    },
                    error: function () {
                        $.messager.alert("系统提示", "系统异常！");
                        return;
                    }
                });
            } else {
                $.messager.alert("系统提示", "数据验证失败！");
                return;
            }
        }

        function resetValue() {
            $("#name").val("");
            $("#url").val("");
            $("#description").val("");
        }

        function closePermissionDialog() {
            $("#dlg").dialog("close");
            resetValue();
        }

        function openPermissionModifyDiglog() {
            var selectRows = $("#dg").datagrid("getSelections");
            if (selectRows.length != 1) {
                $.messager.alert("系统提示", "请选择一条要编辑的数据！");
                return;
            }
            var row = selectRows[0];
            $("#dlg").dialog("open").dialog("setTitle", "编辑用户信息");
            $("#fm").form("load", row);
            $("#flag").val(2);
        }

        function deletePermission() {
            var selectRows = $("#dg").datagrid("getSelections");
            if (selectRows.length == 0) {
                $.messager.alert("系统提示", "请选择要删除的数据！");
                return;
            }
            var strIds = [];
            for (var i = 0; i < selectRows.length; i++) {
                strIds.push(selectRows[i].id);
            }
            var ids = strIds.join(",");
            $.messager.confirm("系统提示", "您确定要删除这<font color=red>" + selectRows.length + "</font>条数据吗?", function (r) {
                if (r) {
                    $.post("${pageContext.request.contextPath}/permission/deletePermission.action", {ids: ids}, function (result) {
                        if (result.success) {
                            $.messager.alert("系统提示", "数据已经成功删除！");
                            $("#dg").datagrid("reload");
                        } else {
                            $.messager.alert("系统提示", "数据删除失败，请联系管理员！");
                        }
                    }, "json");
                }
            });
        }

        function searchPermission() {
            $("#dg").datagrid('load', {
                "name": $("#s_name").val()
            });
        }

        function resetSearchPermission() {
            $("#s_name").val("");
            searchPermission();
        }

        $(document).ready(function () {
            $('#dg').datagrid({
                remoteSort: false,
                //singleSelect:true,
                nowrap: false,
                url: '${pageContext.request.contextPath}/permission/permissionPage.action',
                pageSize: 20,
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'id', title: '权限ID', width: 100, align: 'center'},
                    {field: 'name', title: '权限名称', width: 100, align: 'center'},
                    {field: 'url', title: '权限路径', width: 100, align: 'center'},
                    {field: 'operation', title: '操作', width: 100, align: 'center', formatter: formatAction}
                ]]
            });
        })
    </script>
</head>
<body style="margin: 1px">
<table id="dg" title="权限管理" class="easyui-datagrid" fit="true" fitColumns="true" pagination="true" rownumbers="true"
       toolbar="#tb"></table>
<div id="tb">
    <div>
        <a href="javascript:openPermissionAddDiglog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
        <a href="javascript:openPermissionModifyDiglog()" class="easyui-linkbutton" iconCls="icon-edit"
           plain="true">修改</a>
        <a href="javascript:deletePermission()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
    </div>
    <div>
        &nbsp;权限名称&nbsp;<input type="text" id="s_name" size="20" onkeydown="if(event.keyCode==13) searchPermission()"/>
        <a href="javascript:searchPermission()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
        <a href="javascript:resetSearchPermission()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置</a>
    </div>
</div>
<div id="dlg" class="easyui-dialog" style="width: 620px;height:180px;padding: 10px 20px" closed="true"
     buttons="#dlg-buttons">
    <form id="fm" method="post">
        <table cellpadding="8px">
            <tr>
                <td>权限名称：</td>
                <td>
                    <input type="text" id="name" name="name" class="easyui-validatebox" required="true"/>
                </td>
                <td></td>
                <td>权限路径：</td>
                <td>
                    <input type="text" id="url" name="url" class="easyui-validatebox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>详细描述：</td>
                <td>
                    <input type="text" id="description" name="description" class="easyui-validatebox"/>
                    <input type="hidden" id="flag" name="flag"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:checkData()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closePermissionDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
<div id="dlg2" class="easyui-dialog" style="width: 400px;height: 250px;padding: 10px 20px" closed="true">
    <p align="center">暂无详细描述</p>
<%--    <form method="post">--%>
<%--        <table id="systemTable" class="table table-border table-bordered table-striped" style="width: 100%"--%>
<%--               cellpadding="8">--%>
<%--            <tr>--%>
<%--                <td width="200">SessionID</td>--%>
<%--                <td width="350">--%>
<%--                    <span>9a8efa38-4a19-4c6e-9e66-a4e422609728</span>--%>
<%--                </td>--%>
<%--            </tr>--%>
<%--        </table>--%>
<%--    </form>--%>
</div>
</body>
</html>