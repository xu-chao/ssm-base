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
    <title>公司主体管理</title>
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
        var _url = '${pageContext.request.contextPath}/company/searchCompany';
        var _idName = '公司主体Id';
        var _value = 'COMPANY_NAME';
        //用于crud.js的esayui初始参数
        var root = "${pageContext.request.contextPath}/company/";
        var importExcelUrl = "${pageContext.request.contextPath}/static/excel/companyImport.xls";
        var pageName = 'company';
        var name = 'Company';
        var title = '公司主体列表';
        var idField = 'COMPANY_ID';
        var addParam = '?' + idField + '=';
        var selectRows;
        var columns = [[
            {
                field: 'COMPANY_NAME',
                title: '公司主体名称',
                width: 50,
                align: 'center'
            }, {
                field: 'COMPANY_PERSON',
                title: '公司主体负责人',
                width: 50,
                align: 'center'
            }, {
                field: 'COMPANY_TEL',
                title: '公司主体联系方式',
                width: 50,
                align: 'center'
            },
            {
                field: 'COMPANY_EMAIL',
                title: '公司主体Email',
                width: 50,
                align: 'center'
            },
            {
                field: 'COMPANY_ADDRESS',
                title: '公司主体地址',
                width: 50,
                align: 'center'
            }]];
        var h = 180;
        var w = 280;
        var _title = '公司主体管理';

        function getID(info) {
            if (info == 'update') {
                var selectRows = $("#dg").datagrid("getSelections");
                var row = selectRows[0].COMPANY_ID;
                return row;
            } else {
                var selectRows = $("#dg").datagrid("getSelections");
                if (selectRows.length == 0) {
                    $.messager.alert("系统提示", "请选择要删除的数据！");
                    return;
                }
                var strIds = [];
                for (var i = 0; i < selectRows.length; i++) {
                    strIds.push(selectRows[i].COMPANY_ID);
                }
                return strIds;
            }
        }

        <%--$(function () {--%>
        <%--    var com_goodsName =   $('#goodsName').combobox(--%>
        <%--        {--%>
        <%--            prompt: '输入关键字后自动搜索',--%>
        <%--            // mode: 'remote',--%>
        <%--            url: '${pageContext.request.contextPath}/goods/searchGoods',--%>
        <%--            valueField: 'goodsId',--%>
        <%--            textField: 'goodsName',--%>
        <%--            panelHeight: 'auto',--%>
        <%--            panelMaxHeight: 150,--%>
        <%--            editable: true,--%>
        <%--            // hasDownArrow: false,--%>
        <%--            onBeforeLoad: function (param) {--%>
        <%--            },--%>
        <%--            onSelect:function(record) {--%>
        <%--                debugger--%>
        <%--                var cit= record.cityID;--%>
        <%--                $('#parkName').combobox({--%>
        <%--                    disabled:false,--%>
        <%--                    url: '${pageContext.request.contextPath}/subProject/findParkByCityID?cityID=' + cit,--%>
        <%--                    valueField:'parkID',--%>
        <%--                    textField:'parkName',--%>
        <%--                    onLoadSuccess: function () { //加载完成后,val[0]写死设置选中第一项--%>
        <%--                        var val = $(this).combobox("getData");--%>
        <%--                        for (var item in val[0]) {--%>
        <%--                            if (item == "parkID") {--%>
        <%--                                $(this).combobox("select", val[0][item]);--%>

        <%--                            }--%>
        <%--                        }--%>
        <%--                    }--%>
        <%--                }).combobox("clear");--%>
        <%--            }--%>
        <%--        });--%>
        <%--});--%>
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
                    公司主体名称  <input name="COMPANY_NAME" class="easyui-combobox"/>
                </td>
                <td>
                    公司主体负责人  <input name="COMPANY_PERSON" class="easyui-combobox"/>
                </td>
                <td>
                    公司主体地址  <input name="COMPANY_ADDRESS" class="easyui-combobox"/>
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
                <td>公司主体Id：</td>
                <td>
                    <input id="COMPANY_ID" name="COMPANY_ID" class="easyui-validatebox"
                           data-options="required:true,missingMessage:'供应商Id不能为空!'">
                </td>
            </tr>
            <tr>
                <td>公司主体名称：</td>
                <td>
                    <input id="COMPANY_NAME" name="COMPANY_NAME" type="text">
                </td>
            </tr>
            <tr>
                <td>公司主体负责人：</td>
                <td>
                    <input id="COMPANY_PERSON" name="COMPANY_PERSON" type="text">
                </td>
            </tr>
            <tr>
                <td>公司主体联系方式：</td>
                <td>
                    <input id="COMPANY_TEL" name="COMPANY_TEL" type="text">
                </td>
            </tr>
            <tr>
                <td>公司主体Email：</td>
                <td>
                    <input id="COMPANY_EMAIL" name="COMPANY_EMAIL" type="text">
                </td>
            </tr>
            <tr>
                <td>公司主体地址：</td>
                <td>
                    <input id="COMPANY_ADDRESS" name="COMPANY_ADDRESS" type="text">
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