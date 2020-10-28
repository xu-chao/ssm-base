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
    <title>子项目管理</title>
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
        var _url = '${pageContext.request.contextPath}/subProject/searchAllSubProject';
        var _idName = '子项目ID';
        var _value = 'subProjectName';
        //用于crud.js的esayui初始参数
        var root = "${pageContext.request.contextPath}/subProject/";
        var pageName = 'subProject';
        var name = 'SubProject';
        var title = '项目列表';
        var idField = 'subProjectID';
        var addParam = '?' + idField + '=';
        var selectRows;
        var columns = [[{
            field : 'subProjectName',
            title : '项目名称',
            width : 100,
            align: 'center'
        }, {
            field : 'parkName',
            title : '公园名称',
            width : 100,
            align: 'center',
            formatter: function (value, row, index) {
                if (row.park) {
                    return row.park.parkName;
                } else {
                    return "获取不到该公园！";
                }
            }
        },{
            field : 'cityName',
            title : '城市名称',
            width : 100,
            align: 'center',
            formatter: function (value, row, index) {
                if (row.city) {
                    return row.city.cityName;
                } else {
                    return "获取不到该城市！";
                }
            }
        }]];

        var h = 180;
        var w = 280;
        var _title = '子项目管理';

        function getID(info) {
            if(info == 'update'){
                var selectRows = $("#dg").datagrid("getSelections");
                var row = selectRows[0].projectID;
                return row;
            }else {
                var selectRows = $("#dg").datagrid("getSelections");
                if(selectRows.length == 0){
                    $.messager.alert("系统提示", "请选择要删除的数据！");
                    return;
                }
                var strIds = [];
                for (var i = 0; i < selectRows.length; i++) {
                    strIds.push(selectRows[i].projectID);
                }
                return strIds;
            }
        }

        $(function () {
            var com_cityName =   $('#cityName').combobox(
                {
                    prompt: '输入关键字后自动搜索',
                    // mode: 'remote',
                    url: '${pageContext.request.contextPath}/city/searchCity',
                    valueField: 'cityID',
                    textField: 'cityName',
                    panelHeight: 'auto',
                    panelMaxHeight: 150,
                    editable: true,
                    // hasDownArrow: false,
                    onBeforeLoad: function (param) {
                    },
                    onSelect:function(record) {
                        debugger
                        var cit= record.cityID;
                        $('#parkName').combobox({
                            disabled:false,
                            url: '${pageContext.request.contextPath}/subProject/findParkByCityID?cityID=' + cit,
                            valueField:'parkID',
                            textField:'parkName',
                            onLoadSuccess: function () { //加载完成后,val[0]写死设置选中第一项
                                var val = $(this).combobox("getData");
                                for (var item in val[0]) {
                                    if (item == "parkID") {
                                        $(this).combobox("select", val[0][item]);

                                    }
                                }
                            }
                        }).combobox("clear");
                    }
                });
        });
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
                <td>项目名称 ：</td>
                <td><input name="subProjectName" class="easyui-combobox" id="inputtable" /></td>
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
<%--<div data-options="region:'center',collapsible:true,split:true"--%>
<%--     style="width: 600px;padding: 4px; background-color: #eee">--%>
<%--</div>--%>
<%--<div data-options="region:'east',collapsible:true,split:true"--%>
<%--     style="width: 600px;padding: 4px; background-color: #eee">--%>
<%--</div>--%>
<!-- 添加、修改 -->
<div id="editDlg" class="easyui-window" data-options="closed:true" align="center">
    <form id="editForm">
        <table>
            <tr>
                <td>所属城市：</td>
                <td>
                    <input id="cityName" name="City.cityID" class="easyui-validatebox" data-options="required:true,missingMessage:'城市名称不能为空!'">
                </td>
            </tr>
            <tr>
                <td>公园名称：</td>
                <td>
                    <input id="parkName" name="Park.parkID"  class="easyui-combobox" >
                </td>
            </tr>
            <tr>
                <td>子项目名称：</td>
                <td><input id="projectName" type="text" name="projectName"></td>
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