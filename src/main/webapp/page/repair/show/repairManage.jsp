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
    <title>申故管理</title>
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

    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.getDom.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.getRowData.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.tooltip.js"></script>

    <script type="text/javascript">
        var pageContext = "${pageContext.request.contextPath}";
        var g_searchType = '';
        var g_searchValue = '';
        //用于search.js自动补全
        var _idName = '申故单号';
        var _value = 'id';
        //用于crud.js的esayui初始参数
        var root = "${pageContext.request.contextPath}/repair/";
        var pageName = 'repairThrough';
        var name = 'Repair';
        var title = '申故列表';
        var idField = 'id';
        var addParam = '?' + idField + '=';
        var selectRows;
        var tooltip = "此条申故单号的申故信息将被清除！";
        var flag = "";
        var columns = [[
            {
                field: 'repairDate',
                title: '维修日期',
                width: 50,
                align: 'center',
                tooltip: true,
            }, {
                field: 'repairPlace',
                title: '维修地点',
                width: 50,
                align: 'center',
                tooltip: true,
            }, {
                field: 'repairLevel',
                title: '维修程度',
                width: 50,
                align: 'center',
                // formatter: function (value, row, index) {
                //     if (row.goods == null) {
                //         return "";
                //     } else {
                //         return row.goods.goodsName;
                //     }
                // }
            }, {
                field: 'repairType',
                title: '故障类别',
                width: 50,
                align: 'center',
            }, {
                field: 'repairContext',
                title: '故障描述',
                width: 50,
                align: 'center',
            }, {
                field: 'recordDate',
                title: '记录日期',
                width: 50,
                align: 'center',
                // hidden: true//列表默认隐藏
            }
        ]];
        var h = 400;
        var w = 400;
        var _title = '申故管理';

        function getID(info) {
            if (info == 'update') {
                var selectRows = $("#dg").datagrid("getSelections");
                var row = selectRows[0].id;
                return row;
            } else {
                var selectRows = $("#dg").datagrid("getSelections");
                if (selectRows.length == 0) {
                    $.messager.alert("系统提示", "请选择要删除的数据！");
                    return;
                }
                var strIds = [];
                for (var i = 0; i < selectRows.length; i++) {
                    strIds.push(selectRows[i].id);
                }
                return strIds;
            }
        }

        $(document).ready(function () {

            $('#endDate').datetimebox({
                required: true,
                showSeconds: true,
                editable: false,
            });

            $('#dg').datagrid({
                // rowStyler:function(index,row){
                //     if(typeof row.goods == 'undefined'){
                //         return ;
                //     }else if(row.goods == null) {
                //         return 'background-color:#1685a9;color:#fff;';
                //     }
                // }
                fitColumns: true,
            });

        })
    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/crud2.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/down  load2.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north',title:'查询'"
     style="padding: 4px; background-color: #eee; height: 90px">
    <form id="searchForm">
        <table cellpadding="5">
            <tr>
                <td> &nbsp;
                    故障发生时间:&nbsp;<input type="text" id="s_startDate" name="startDate" size="20"/> --
                    <input type="text" id="s_endDate" name="endDate" size="20"/>
                </td>
                <td>
                    <input id="ss" style="width:300px">
                    <div id="searchType" style="width:120px">
                        <div data-options="name:'id',iconCls:'icon-ok'">申故单号</div>
                        <div data-options="name:'repairPlace'">故障发生地点</div>
                        <div data-options="name:'repairType'">故障类别</div>
                        <div data-options="name:'repiarContext'">故障描述</div>
                    </div>
                </td>
                <td>
                    <a class="easyui-linkbutton"
                       data-options="iconCls:'icon-reload'" id="btnReset">重置</a>
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
                <td>存货编码：</td>
                <td>
                    <input id="goodsId" name="goodsId" class="easyui-textbox easyui-validatebox"
                           data-options="required:true,missingMessage:'存货编码不能为空!'">
                </td>
            </tr>
            <tr>
                <td>物料名称：</td>
                <td>
                    <input id="goodsName" name="goodsName" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
            <tr>
                <td>图号：</td>
                <td>
                    <input id="goodsType" name="goodsType" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
            <tr>
                <td>单位：</td>
                <td>
                    <input id="goodsUnit" name="goodsUnit" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
            <tr>
                <td>尺寸：</td>
                <td>
                    <input id="goodsSize" name="goodsSize" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
            <tr>
                <td>功能：</td>
                <td>
                    <input id="goodsFunction" name="goodsFunction" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
            <tr>
                <td>资料：</td>
                <td>
                    <input id="goodsMessage" name="goodsMessage" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
            <tr>
                <td>其他：</td>
                <td>
                    <input id="goodsElse" name="goodsElse" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
            <tr>
                <td>价值：</td>
                <td>
                    <input id="goodsValue" name="goodsValue" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
            <tr>
                <td>链接：</td>
                <td>
                    <input id="goodsLink" name="goodsLink" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
        </table>
        <br/>
        <button id="btnSave" class="easyui-linkbutton" class="easyui-textbox easyui-validatebox" type="button">保存</button>
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