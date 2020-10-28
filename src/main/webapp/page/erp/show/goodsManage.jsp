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
    <title>存货管理</title>
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

    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.getDom.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.getRowData.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.tooltip.js"></script>

    <script type="text/javascript">
        var pageContext = "${pageContext.request.contextPath}";
        var g_searchType = '';
        var g_searchValue = '';
        //用于search.js自动补全
        var _idName = '采购单号';
        var _value = 'nId';
        //用于crud.js的esayui初始参数
        var root = "${pageContext.request.contextPath}/needGoods/";
        var pageName = 'needGoodsThrough';
        var name = 'NeedGoods';
        var title = '存货列表';
        var idField = 'nId';
        var addParam = '?' + idField + '=';
        var selectRows;
        var tooltip = "此条采购单号的存货信息将被清除！";
        //一般为空
        var flag = "goods";
        var columns = [[
            {
                field: 'EApplicantData',
                title: '申请日期',
                width: 50,
                align: 'center',
                tooltip: true,
                formatter: function (value, row, index) {
                    return row.need.EApplicantData;
                }
            }, {
                field: 'goodsId',
                title: '存货编码',
                width: 50,
                align: 'center',
                tooltip: true,
                formatter: function (value, row, index) {
                    if (value.substr(3, 3) == 'NEW') {
                        return "<label style='color: red'>" + value + "</label>";
                    } else {
                        return value;
                    }
                }
            }, {
                field: 'goodsName',
                title: '物料名称',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.goods == null) {
                        return "";
                    } else {
                        return row.goods.goodsName;
                    }
                }
            }, {
                field: 'goodsType',
                title: '类别',
                width: 50,
                align: 'center',
                hidden: true, //列表默认隐藏
                formatter: function (value, row, index) {
                    if (row.goods == null) {
                        return "";
                    } else {
                        return row.goods.goodsType;
                    }
                }
            }, {
                field: 'goodsCode',
                title: '图号',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.goods == null) {
                        return "";
                    } else {
                        return row.goods.goodsCode;
                    }
                }
            }, {
                field: 'goodsUnit',
                title: '单位',
                width: 20,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.goods == null) {
                        return "";
                    } else {
                        return row.goods.goodsUnit;
                    }
                }
                // hidden: true//列表默认隐藏
            }
        ]];
        var h = 240;
        var w = 400;
        var _title = '存货管理';

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

        $(document).ready(function () {

            $('#endDate').datetimebox({
                required: true,
                showSeconds: true,
                editable: false,
            });

            $('#dg').datagrid({
                rowStyler:function(index,row){
                    if(typeof row.goods == 'undefined'){
                        return ;
                    }else if(row.goods == null) {
                        return 'background-color:#1685a9;color:#fff;';
                    }
                },
                fitColumns: true,
                toolbar: [
                    {
                        text: '选择',
                        iconCls: 'icon-edit',
                        handler: function () {
                            updateGoodsId();
                        }
                    },
                    '-',
                    {
                        text: '删除',
                        iconCls: 'icon-cut',
                        handler: function () {
                            var temp = '';
                            var strIds = getID(temp);
                            del(strIds);
                        }
                    },
                    '-',
                    {
                        text: '导出',
                        iconCls: 'icon-excel',
                        handler: function () {
                            var formData = $('#searchForm').serializeJSON();
                            // 下载文件
                            $.download(root + pageName + "Export" + listParam , formData);
                        }
                    }
                ],
            });

        })
    </script>
    <!-- 存货编码选择 -->
    <script type="text/javascript">
        var g_goodsType;
        var g_goodList;
        var goodsId_tt;
        var nId_tt;
        var goodsType_tt;

        function updateGoodsId() {
            debugger
            var selectRows = $("#dg").datagrid("getSelections");
            if (selectRows.length != 1) {
                $.messager.alert("系统提示", "请选择一条要编辑的数据！");
                return;
            } else {
                var row = selectRows[0];
                nId_tt = row.nId;
                goodsId_tt = row.goodsId;
                goodsType_tt = row.goods.goodsType;
            }
            $("#addECodeID").dialog("open").dialog("center");
            $("#updateGoods").show();
            $("#saveGoods").hide();
            $("#goodsTable").datagrid({
                singleSelect: true
            });
            $("#goodsTableSelect").datagrid({
                singleSelect: true
            });
            $("#goodsId_tt").val(goodsId_tt);
            $("#nId_tt").val(nId_tt);
            $('#searchGoodsType').combobox("setValue", goodsType_tt);
            $('#goodsTable').datagrid('load', {
                goodsType: $("#searchGoodsType").val(),
            });
        }

        function updateGoods() {
            goodsId_tt = $("#goodsId_tt").val();
            nId_tt = $("#nId_tt").val();
            var selectedRows = $('#goodsTableSelect').datagrid("getRows");//已选
            var goodsId;
            if (!selectedRows.length > 0) {
                $("#addECodeID").dialog("close");
                return;
            }
            if (selectedRows.length > 1) {
                $.messager.alert("系统提示", "选择项超过一个！");
                return;
            }
            if ($.isArray(selectedRows)) {
                $.array.map(selectedRows, function (item) {
                    goodsId = item.goodsId;
                });
                $.ajax({
                    url: "${pageContext.request.contextPath}/needGoods/updateGoods",
                    type: "post",
                    data: {nId_tt: nId_tt, goodsId_tt: goodsId_tt, goodsId: goodsId},
                    dataType: "json",
                    success: function (result) {
                        if (result.success) {
                            $.messager.alert('系统提示', '修改成功');
                            $("#addECodeID").dialog("close");
                            $("#dg").datagrid('reload');
                        } else {
                            $.messager.alert("系统提示", "操作失败！");
                            return;
                        }
                    },
                    error: function () {
                        $.messager.alert("系统提示", "系统异常！");
                        return;
                    }
                });
            } else {
                $.messager.alert("系统提示", "操作失败！");
                return;
            }
        }

        $(function () {
            //去边框 dialog
            $("#addECodeID").dialog({
                modal: true,
                draggable: true,
                resizable: true,
                title: "添加存货编号",
                shadow: false
            });
            $("#addECodeID").parent().css("padding", "2");

            $("#btnSelect").click(function () {
                $("#addECodeID").dialog("open");
                $("#updateGoods").hide();
                $("#saveGoods").show();
                $("#goodsTable").datagrid({
                    singleSelect: false
                });
                $("#goodsTableSelect").datagrid({
                    singleSelect: false
                });
            })

            $('#searchGoodsType').combobox(
                {
                    prompt: '输入关键字后自动搜索',
                    // mode : 'remote',
                    disabled: false,
                    method: "post",
                    url: "${pageContext.request.contextPath}/goods/selectGoodsType",
                    valueField: 'id',
                    textField: 'text',
                    // panelHeight : 'auto',
                    panelMaxHeight: 150,
                    editable: true,
                    onSelect: function (record) {
                        $("#goodsNameS").textbox('clear');
                        $("#goodsCodeS").textbox('clear');
                        g_goodsType = record.text;
                        $('#goodsTable').datagrid('options').url = "${pageContext.request.contextPath}/goods/goodsPage",
                            $('#goodsTable').datagrid('load', {
                                goodsType: g_goodsType,
                            });
                    }
                });

            $('#goodsTable').datagrid({
                url: "",
                idField: 'goodsId',
                columns: [[
                    {field: 'goodsId', title: '存货编码', width: 90, hidden: true},
                    {field: 'goodsName', title: '名称', width: 60},
                    {field: 'goodsCode', title: '图号', width: 60, tooltip: true},
                    {field: 'goodsUnit', title: '单位', width: 60, hidden: true}
                ]],
                rowStyler: function (index, row) {
                    if (typeof (row.goodsId) != "undefined") {
                        if (row.goodsId.substr(3, 3) == 'NEW') {
                            return 'background-color:#f05b72;color:#fff;';
                        }
                    }
                },
                toolbar: "#goodsTableSearch",
                singleSelect: false,
                fit: true,
                fitColumns: true,
                rownumbers: true,
                iconCls: 'icon-tip',
                pagination: true,// 如果为true，则在DataGrid控件底部显示分页工具栏。
                striped: true,// 是否显示斑马线效果。
                loading: true,//显示载入状态。
                loadMsg: '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
                pageNumber: 1,// 在设置分页属性的时候初始化页码。
                pageSize: 50,// 在设置分页属性的时候初始化页面大小。
                pageList: [20, 30, 50, 100, 150],//在设置分页属性的时候 初始化页面大小选择列表。
                //  待选datagrid的取消选中行操作
                onUnselect: function (rowIndex, rowData) {
                    var idValue = rowData.goodsId;
                    var index = $("#goodsTableSelect").datagrid("getRowIndex", idValue);
                    if (index > -1) {
                        $("#goodsTableSelect").datagrid("deleteRow", index);
                    }
                },
                onSelect: function (rowIndex, rowData) {
                    var rows = new Array()
                    rows[0] = rowData;
                    var idField = 'goodsId';
                    var selectedRows = $('#goodsTableSelect').datagrid("getRows");//已选
                    var selectedRowsIDs = $.array.map(selectedRows, function (row) {
                            return idField ? row[idField] : undefined;
                        }),
                        unselectedRows = rows.length > 0 ? $.array.filter(rows, function (row) {//比较出未选的
                            return idField ? !$.array.contains(selectedRowsIDs, row[idField]) : true;
                        }) : [];
                    $.each(unselectedRows, function (i, val) {
                        $("#goodsTableSelect").datagrid("appendRow", val);
                    });
                },
            });
            // 待选择项  搜索
            $("#standardQueryBtn").click(function () {
                $('#goodsTable').datagrid('load', {
                    goodsType: g_goodsType,
                    goodsName: $("#goodsNameS").val(),
                    goodsCode: $("#goodsCodeS").val(),
                });
            });
            // 待选择项  点击重置按钮
            $('#btnReset_goods').bind('click', function () {
                $("#goodsNameS").textbox('clear');
                $("#goodsCodeS").textbox('clear');
            });
            $('#goodsTableSelect').datagrid({
                url: "",
                idField: 'goodsId',
                columns: [[
                    {field: 'goodsId', title: '存货编码', width: 90, hidden: true},
                    {field: 'goodsName', title: '名称', width: 60, tooltip: true},
                    {field: 'goodsCode', title: '图号', width: 60, tooltip: true},
                    {field: 'goodsUnit', title: '单位', width: 60,}
                ]],
                rowStyler: function (index, row) {
                    if (typeof (row.goodsId) != "undefined") {
                        if (row.goodsId.substr(3, 3) == 'NEW') {
                            return 'background-color:#f05b72;color:#fff;';
                        }
                    }
                },
                fit: true,
                singleSelect: false,
                fitColumns: true,
                rownumbers: true,
                iconCls: 'icon-tip',
                striped: true,// 是否显示斑马线效果。
                loading: true,//显示载入状态。
                loadMsg: '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
                onSelect: function (rowIndex, rowData) {
                    $("#goodsTableSelect").datagrid("deleteRow", rowIndex);
                },
            });


            $("#btnClean").click(function () {
                $("#ECodeID").textbox("setValue", "");
            });

        });

        function selectAllGood() {
            var rows = $('#goodsTable').datagrid("getRows"),//待选
                selectedRows = $('#goodsTableSelect').datagrid("getRows"),//已选
                idField = 'goodsId';
            var selectedRowsIDs = $.array.map(selectedRows, function (row) {//选出已选的 ID序列
                    return idField ? row[idField] : undefined;
                }),
                unselectedRows = rows.length > 0 ? $.array.filter(rows, function (row) {//比较出未选的
                    return idField ? !$.array.contains(selectedRowsIDs, row[idField]) : true;
                }) : [];

            $.each(unselectedRows, function (i, val) {
                $("#goodsTableSelect").datagrid("appendRow", val);
            });
        }

        function cancelAllGood() {
            $("#goodsTableSelect").datagrid("loadData", []);
            $('#goodsTable').datagrid("clearSelections");
        }

        function saveGoods() {
            var selectedRows = $('#goodsTableSelect').datagrid("getRows");//已选
            if (!selectedRows.length > 0) {
                $("#addECodeID").dialog("close");
                return;
            }
            if ($.isArray(selectedRows)) {
                $("#ECodeID").textbox("setValue", $.array.map(selectedRows, function (item) {
                    return item.goodsId
                }).join(","));
            } else {
                $("#ECodeID").textbox("setValue", selectedRows.goodsId);
            }
            $("#addECodeID").dialog("close");
        }

        function cancelGoods() {
            $("#goodsTable").datagrid("loadData", []);
            $("#goodsTableSelect").datagrid("loadData", []);
            $("#addECodeID").dialog("close");
        }
    </script>
    <!-- 存货编码选择 -->
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/crud2.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/download2.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north',title:'查询'"
     style="padding: 4px; background-color: #eee; height: 90px">
    <form id="searchForm">
        <table cellpadding="5">
            <tr>
                <td> &nbsp;
                    操作时间:&nbsp;<input type="text" id="s_startDate" name="startDate" size="20"/> --
                    <input type="text" id="s_endDate" name="endDate" size="20"/>
                    <input type="text" id="flag" name="flag" hidden="true" value="goods"/>
                </td>
                <td>
                    <input id="ss" style="width:300px">
                    <div id="searchType" style="width:120px">
                        <div data-options="name:'nId',iconCls:'icon-ok'">采购单号</div>
                        <div data-options="name:'goodsId'">存货编码</div>
                        <div data-options="name:'goodsName'">物料名称</div>
                        <div data-options="name:'goodsCode'">图号</div>
                        <div data-options="name:'goodsUnit'">单位</div>
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
    <input id="goodsId_tt" type="hidden"/>
    <input id="nId_tt" type="hidden"/>
</div>

<%--存货编码待选择项--%>
<div id="addECodeID" class="easyui-dialog" closed="true">
    <div class="easyui-layout" style="width:900px;height:450px;">
        <div data-options="region:'south',split:true" style="height:50px;" class="dialog-button">
            <a id="updateGoods" href="javascript:updateGoods();" class="l-btn l-btn-small"><span
                    class="l-btn-left l-btn-icon-left"><span
                    class="l-btn-text">保存</span><span class="l-btn-icon icon-save">&nbsp;</span></span></a>
            <a id="saveGoods" href="javascript:saveGoods();" class="l-btn l-btn-small"><span
                    class="l-btn-left l-btn-icon-left"><span
                    class="l-btn-text">确定</span><span class="l-btn-icon icon-ok">&nbsp;</span></span></a>
            <a id="cancelGoods" href="javascript:cancelGoods();" class="l-btn l-btn-small"><span
                    class="l-btn-left l-btn-icon-left"><span
                    class="l-btn-text">取消</span><span class="l-btn-icon icon-cancel">&nbsp;</span></span></a>
        </div>
        <div data-options="region:'west',split:true,border: false" title="类型" style="width:178px;">
            <input id="searchGoodsType" class="easyui-combobox">
        </div>
        <div data-options="region:'center',split:true,bodyCls: 'grid-selector-outer-center'">
            <div class="easyui-layout" data-options="fit:true">
                <div data-options="region: 'west', split: false, border: false" title="待选择项" style="width:420px;">
                    <table id="goodsTable"></table>
                </div>

                <div data-options="region: 'center', border: true, bodyCls: 'grid-selector-buttons'"
                     class="panel-body panel-body-noheader grid-selector-buttons layout-body"
                     style="width: 41px; height: 408px;background:#efefef; color:#c3c3c3">
                    <div style="margin-top:100px;">
                        <a href="javascript:selectAllGood()" class="l-btn l-btn-small l-btn-plain tooltip-f"
                           style="display: block; text-align: center;">
                    <span class="l-btn-left l-btn-icon-left">
                     <span class="l-btn-text l-btn-empty">&nbsp;</span>
                        <span class="l-btn-icon pagination-last">&nbsp;</span>
                    </span>
                        </a>
                        &nbsp
                        <a href="javascript:cancelAllGood()" class="l-btn l-btn-small l-btn-plain tooltip-f"
                           style="display: block; text-align: center;">
                    <span class="l-btn-left l-btn-icon-left">
                        <span class="l-btn-text l-btn-empty">&nbsp;</span>
                    <span class="l-btn-icon pagination-first">&nbsp;</span>
                    </span>
                        </a>
                    </div>
                </div>

                <div data-options="region: 'east', split: false, border: false" title="以选项" style="width:255px;">
                    <table id="goodsTableSelect"></table>
                </div>
            </div>

        </div>
    </div>
</div>
<div id="goodsTableSearch">
    名称:<input type="text" class="easyui-textbox" id="goodsNameS" style="width:100px"/> 
    图号:<input type="text" class="easyui-textbox" id="goodsCodeS" style="width:100px"/> 
    <a id="standardQueryBtn" href="javascript:void(0)" class="easyui-linkbutton"
       data-options="iconCls:'icon-search'">搜索</a>
    <a class="easyui-linkbutton"
       data-options="iconCls:'icon-remove'" id="btnReset_goods">重置</a>
</div>
<%--存货编码待选择项--%>
</body>
</html>