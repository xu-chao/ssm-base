<%--
  Created by IntelliJ IDEA.
  User: xuchao
  Date: 2019/8/14
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>权限分配管理</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        //用于groupAllocation.js的url
        var _url_pg = "${pageContext.request.contextPath}/allocation/findPermissionByGid";
        var _url_ga = "${pageContext.request.contextPath}/allocation/updateAllocation";

        $(document).ready(function () {
            $('#dg').datagrid({
                remoteSort: false,
                singleSelect: true,
                nowrap: false,
                url: '${pageContext.request.contextPath}/allocation/allocationPage.action',
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'id', title: '角色名称', width: 100, align: 'center'},
                    {field: 'name', title: '角色类型', width: 100, align: 'center'},
                    {field: 'permissions', title: '拥有的权限', width: 100, align: 'center'}
                ]],
                onClickRow: function (rowIndex, rowData) {
                    $('#dl').datalist({
                        url: _url_pg + '?id=' + rowData.id,
                        checkbox: true,
                        lines: true,
                        animate: false,
                        singleSelect: false,
                        selectOnCheck: true,
                        checkOnSelect: true,//选择checkbox则选择行
                        onLoadSuccess: function (row) {//当datalist成功加载时执行
                            var rowData = row.rows;
                            $.each(rowData, function (index, val) {//遍历JSON
                                if (val.checked) {
                                    $("#dl").datagrid("selectRow", index);//如果数据行为已选中则选中改行
                                }
                            });
                        }
                    });
                }
            });
        })
    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/groupAllocationSet.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',title:'角色列表'"
     style="width: 600px;padding: 4px; background-color: #eee">
    <table id="dg" class="easyui-datagrid" fit="true" fitColumns="true" pagination="true" rownumbers="true"
           toolbar="#tb">
    </table>
</div>
<div id="allocationPanel" data-options="region:'east',title:'权限列表',split:true"
     style="width: 500px;">
    <div id="dl" class="easyui-datalist"></div>
</div>
</body>
</html>