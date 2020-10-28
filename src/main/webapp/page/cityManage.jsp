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
    <title>城市管理</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/autoPinyin.js"></script>
    <script type="text/javascript">
        //用于search.js自动补全
        var _url = '${pageContext.request.contextPath}/city/searchCity';
        var _idName = '城市ID';
        var _value = 'cityName';
        //用于crud.js的esayui初始参数
        var root = "${pageContext.request.contextPath}/city/";
        var pageName = 'city';
        var name = 'City';
        var title = '城市列表';
        var idField = 'cityID';
        var addParam = '?' + idField + '=';
        var selectRows;
        var columns = [[{
            field : 'cityName',
            title : '城市名称',
            width : 100,
            align: 'center'
        }, {
            field : 'longitude',
            title : '经度',
            width : 100,
            align: 'center'
        }, {
            field : 'latitude',
            title : '纬度',
            width : 100,
            align: 'center'
        }]];
        var h = 180;
        var w = 280;
        var _title = '城市管理';

        function getID(info) {
            if(info == 'update'){
                var selectRows = $("#dg").datagrid("getSelections");
                var row = selectRows[0].cityID;
                return row;
            }else {
                var selectRows = $("#dg").datagrid("getSelections");
                if(selectRows.length == 0){
                    $.messager.alert("系统提示", "请选择要删除的数据！");
                    return;
                }
                var strIds = [];
                for (var i = 0; i < selectRows.length; i++) {
                    strIds.push(selectRows[i].cityID);
                }
                return strIds;
            }
        }
    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/crud.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/search.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/download.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north',title:'查询'"
     style="padding: 4px; background-color: #eee; height: 90px">
    <form id="searchForm">
        <table cellpadding="5">
            <tr>
                <td>城市名称 ：</td>
                <td><input name="cityName" class="easyui-combobox" id="inputtable" /></td>
                <td><a class="easyui-linkbutton"
                       data-options="iconCls:'icon-search'" id="btnSearch">查询</a></td>
                <td><a class="easyui-linkbutton"
                       data-options="iconCls:'icon-remove'" id="btnReset">重置</a></td>
        </table>
    </form>
</div>
<!-- 数据表格区 -->
<div data-options="region:'center',collapsible:true,split:true"
     style="width: 600px;padding: 4px; background-color: #eee">
    <!--搜索区  -->
    <table id="dg"></table>
</div>
<div id="cityPanel" data-options="region:'east',title:'城市地图',split:true"
     style="width: 500px;">
    <div id="dl" class="easyui-datalist"></div>
</div>
<!-- 添加、修改 -->
<div id="editDlg" class="easyui-window" data-options="closed:true" align="center">
    <form id="editForm">
        <table>
            <tr>
                <td>城市名称：</td>
                <td>
                    <input id="cityName" name="cityName" class="easyui-validatebox" data-options="required:true,missingMessage:'城市名称不能为空!'">
                </td>
            </tr>
            <tr>
                <td>经度：</td>
                <td><input id="longitude" type="text" name="longitude"></td>
            </tr>
            <tr>
                <td>纬度：</td>
                <td><input id="latitude" type="text" name="latitude"></td>
            </tr>
        </table>
        <br />
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