<%--
  Created by IntelliJ IDEA.
  User: xuchao
  Date: 2019/8/21
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
    <title>仓库管理</title>
    <link href="${pageContext.request.contextPath}/static/css/Site.css" rel="stylesheet"/>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
    <link href="http://www.easyui-extlib.com/Content/icons/icon-standard.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/jeasyui-extensions-datagrid.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-menu.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-datagrid-getColumnInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-datagrid-columnToggle.js"></script>
    <script type="text/javascript">
        //用于search.js自动补全
        var _url = '${pageContext.request.contextPath}/respository/searchRespository';
        var _idName = '仓库Id';
        var _value = 'REPO_ADDRESS';
        //用于crud.js的esayui初始参数
        var root = "${pageContext.request.contextPath}/respository/";
        var pageName = 'respository';
        var name = 'Respository';
        var title = '仓库列表';
        var idField = 'REPO_ID';
        var addParam = '?' + idField + '=';
        var selectRows;
        var columns = [[
            {
                field: 'REPO_ADDRESS',
                title: '仓库地址',
                width: 50,
                align: 'center'
            }, {
                field: 'REPO_STATUS',
                title: '状态',
                width: 50,
                align: 'center'
            }, {
                field: 'REPO_AREA',
                title: '区域',
                width: 50,
                align: 'center'
            },
            {
                field: 'REPO_DESC',
                title: '描述',
                width: 50,
                align: 'center'
            }]];
        var h = 180;
        var w = 280;
        var _title = '仓库管理';

        function getID(info) {
            if (info == 'update') {
                var selectRows = $("#dg").datagrid("getSelections");
                var row = selectRows[0].REPO_ID;
                return row;
            } else {
                var selectRows = $("#dg").datagrid("getSelections");
                if (selectRows.length == 0) {
                    $.messager.alert("系统提示", "请选择要删除的数据！");
                    return;
                }
                var strIds = [];
                for (var i = 0; i < selectRows.length; i++) {
                    strIds.push(selectRows[i].REPO_ID);
                }
                return strIds;
            }
        }
    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/crud.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/search2.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/download.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north',title:'查询'"
     style="padding: 4px; background-color: #eee; height: 90px">
    <form id="searchForm">
        <table cellpadding="5">
            <tr>
                <td>
                    仓库地址  <input name="REPO_ADDRESS" class="easyui-combobox"/>
                </td>
                <td>
                    区域  <input name="REPO_AREA" class="easyui-combobox"/>
                </td>
                <td>
                    描述  <input name="REPO_DESC" class="easyui-combobox"/>
                </td>
                <td>
                    <a class="easyui-linkbutton"
                       data-options="iconCls:'icon-remove'" id="btnReset">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<!-- 数据表格区 -->
<div data-options="region:'center',collapsible:true,split:true"
     style="width: 600px;padding: 4px; background-color: #eee">
    <!--搜索区  -->
    <table id="dg"></table>
</div>
<!-- 添加、修改 -->
<div id="editDlg" class="easyui-window" data-options="closed:true" align="center">
    <form id="editForm">
        <table>
            <tr>
                <td>仓库Id：</td>
                <td>
                    <input id="REPO_ID" name="REPO_ID" class="easyui-validatebox"
                           data-options="required:true,missingMessage:'仓库Id不能为空!'">
                </td>
            </tr>
            <tr>
                <td>仓库地址：</td>
                <td>
                    <input id="REPO_ADDRESS" name="REPO_ADDRESS" type="text">
                </td>
            </tr>
            <tr>
                <td>状态：</td>
                <td>
                    <input id="REPO_STATUS" name="REPO_STATUS" type="text">
                </td>
            </tr>
            <tr>
                <td>区域：</td>
                <td>
                    <input id="REPO_AREA" name="REPO_AREA" type="text">
                </td>
            </tr>
            <tr>
                <td>描述：</td>
                <td>
                    <input id="REPO_DESC" name="REPO_DESC" type="text">
                </td>
            </tr>
        </table>
        <br/>
        <button id="btnSave" class="easyui-linkbutton" type="button">保存</button>
    </form>
</div>
<!-- 导入窗口 -->
<div id="importDlg" style="padding: 2px;">
    <form id="importForm" enctype="multipart/form-data">
        导入文件:<input type="file" name="file">
    </form>
</div>
</body>
</html>