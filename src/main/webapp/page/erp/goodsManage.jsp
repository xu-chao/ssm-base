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
    <link href="${pageContext.request.contextPath}/static/css/icon-standard.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/jeasyui-extensions-datagrid.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-menu.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-datagrid-getColumnInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-datagrid-columnToggle.js"></script>
    <script type="text/javascript">
        //用于search.js自动补全
        var _url = '${pageContext.request.contextPath}/goods/searchGoodsNameLike';
        var _idName = '物料ID';
        var _value = 'goodsName';
        //用于crud.js的esayui初始参数
        var root = "${pageContext.request.contextPath}/goods/";
        var pageName = 'goods';
        var name = 'Goods';
        var title = '物料列表';
        var idField = 'goodsId';
        var addParam = '?' + idField + '=';
        var selectRows;
        var columns = [[
            {
                field: 'goodsName',
                title: '物料名称',
                width: 50,
                align: 'center'
            }, {
                field: 'goodsCode',
                title: '规格型号/图号',
                width: 50,
                align: 'center'
            }, {
                field: 'goodsUnit',
                title: '单位',
                width: 50,
                align: 'center'
            },
            {
                field: 'goodsType',
                title: '类型',
                width: 50,
                align: 'center'
            },
            {
                field: 'goodsSize',
                title: '尺寸',
                width: 50,
                align: 'center',
                hidden: true
            }, {
                field: 'goodsFunction',
                title: '功能',
                width: 50,
                align: 'center',
                hidden: true
            }, {
                field: 'goodsMessage',
                title: '资料',
                width: 50,
                align: 'center',
                hidden: true
            }, {
                field: 'goodsElse',
                title: '税目',
                width: 50,
                align: 'center'
            }, {
                field: 'goodsValue',
                title: '价值',
                width: 50,
                align: 'center',
                hidden: true
            }, {
                field: 'goodsLink',
                title: '链接',
                width: 50,
                align: 'center',
                hidden: true
            }]];
        var h = 180;
        var w = 280;
        var _title = '存货编码管理';

        function getID(info) {
            if (info == 'update') {
                var selectRows = $("#dg").datagrid("getSelections");
                var row = selectRows[0].goodsId;
                return row;
            } else {
                var selectRows = $("#dg").datagrid("getSelections");
                if (selectRows.length == 0) {
                    $.messager.alert("系统提示", "请选择要删除的数据！");
                    return;
                }
                var strIds = [];
                for (var i = 0; i < selectRows.length; i++) {
                    strIds.push(selectRows[i].goodsId);
                }
                return strIds;
            }
        }
        $(function() {
        // 自动补全
        $('#searchCode').combobox(
            {
                prompt : '输入关键字后自动搜索',
                mode : 'remote',
                class: "easyui-searchbox",
                url : '${pageContext.request.contextPath}/goods/searchGoodsCodeLike',// _url,_value已经在各自的js文件中定义
                valueField : 'goodsCode',
                textField :  'goodsCode',
                panelHeight : 'auto',
                panelMaxHeight : 150,
                editable : true,
                hasDownArrow : false,
                onBeforeLoad : function(param) {
                    if (param == null || param.q == null
                        || param.q.replace(/ /g, '') == '') {
                        var value = $(this).combobox('getValue');
                        if (value) {// 修改的时候才会出现q为空而value不为空
                            param.goodsCode = value;
                            return true;
                        }
                        return false;
                    }
                }
            });
        // 自动补全
        $('#searchType').combobox(
            {
                prompt : '输入关键字后自动搜索',
                mode : 'remote',
                class: "easyui-searchbox",
                url : '${pageContext.request.contextPath}/goods/searchGoodsTypeLike',// _url,_value已经在各自的js文件中定义
                valueField : 'goodsType',
                textField :  'goodsType',
                panelHeight : 'auto',
                panelMaxHeight : 150,
                editable : true,
                hasDownArrow : false,
                onBeforeLoad : function(param) {
                    if (param == null || param.q == null
                        || param.q.replace(/ /g, '') == '') {
                        var value = $(this).combobox('getValue');
                        if (value) {// 修改的时候才会出现q为空而value不为空
                            param.goodsType = value;
                            return true;
                        }
                        return false;
                    }
                }
            });
        })
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
                <td>物料名称 ：</td>
                <td><input name="goodsName" class="easyui-combobox" id="inputtable"/></td>
                <td>型号/图号:</td>
                <td><input name="goodsCode" class="easyui-combobox" id="searchCode"/></td>
                <td>类型:</td>
                <td><input name="goodsType" class="easyui-combobox" id="searchType"/></td>
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
<!-- 添加、修改 -->
<div id="editDlg" class="easyui-window" data-options="closed:true" align="center">
    <form id="editForm">
        <table>
            <tr>
                <td>存货编码：</td>
                <td>
                    <input id="goodsId" name="goodsId" class="easyui-validatebox"
                           data-options="required:true,missingMessage:'存货编码不能为空!'">
                </td>
            </tr>
            <tr>
                <td>物料名称：</td>
                <td>
                    <input id="goodsName" name="goodsName" type="text">
                </td>
            </tr>
            <tr>
                <td>图号：</td>
                <td>
                    <input id="goodsType" name="goodsType" type="text">
                </td>
            </tr>
            <tr>
                <td>单位：</td>
                <td>
                    <input id="goodsUnit" name="goodsUnit" type="text">
                </td>
            </tr>
            <tr>
                <td>尺寸：</td>
                <td>
                    <input id="goodsSize" name="goodsSize" type="text">
                </td>
            </tr>
            <tr>
                <td>功能：</td>
                <td>
                    <input id="goodsFunction" name="goodsFunction" type="text">
                </td>
            </tr>
            <tr>
                <td>资料：</td>
                <td>
                    <input id="goodsMessage" name="goodsMessage" type="text">
                </td>
            </tr>
            <tr>
                <td>其他：</td>
                <td>
                    <input id="goodsElse" name="goodsElse" type="text">
                </td>
            </tr>
            <tr>
                <td>价值：</td>
                <td>
                    <input id="goodsValue" name="goodsValue" type="text">
                </td>
            </tr>
            <tr>
                <td>链接：</td>
                <td>
                    <input id="goodsLink" name="goodsLink" type="text">
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