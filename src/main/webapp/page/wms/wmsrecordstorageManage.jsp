<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>库存明细</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/autoPinyin.js"></script>

    <link href="${pageContext.request.contextPath}/static/css/icon-standard.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/jeasyui-extensions-datagrid.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-menu.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-datagrid-getColumnInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-datagrid-columnToggle.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.getDom.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.getRowData.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.tooltip.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
    <script type="text/javascript">
        window.top["wmsrecordin_reload"] = function () {
            $("#dg").datagrid("reload");
            // document.location.reload();//重载当前页面
        }
        $(document).ready(function () {


            $('#s_startDate').datebox({
                required: false,
                showSeconds: true,
                editable: false,
                onSelect: function (date) {
                    $('#s_endDate').datebox('enable');	//启用结束日期控件
                }
            });

            $('#s_endDate').datebox({
                required: false,
                showSeconds: true,
                editable: false,
                disabled: true,
                validType: 'compareDate[\'#s_startDate\']'
            });

            $.extend($.fn.validatebox.defaults.rules, {
                compareDate: {
                    validator: function (value, param) {
                        var d1 = $(param[0]).datetimebox('getValue');  //获取开始时间
                        return value >= d1;  //有效范围为大于开始时间
                    },
                    message: '结束时间不能早于开始时间!'
                }
            });

            $('#dg').datagrid({
                remoteSort: false,
                //singleSelect:true,
                nowrap: false,
                url: '${pageContext.request.contextPath}/wmsrecordstorage/wmsrecordstoragePage',
                columns: [[
                    {
                        field: 'recordWarehouseId',
                        title: '仓库ID',
                        width: 100,
                        align: 'center',
                        sortable: true,
                        hidden: true,
                    },
                    {
                        field: 'whName', title: '仓库名称', width: 150, align: 'center', sortable: false, hidden: false,
                        formatter: function (value, row, index) {
                            if (row.recordWarehouse) {
                                return "<div  class='textEllipsis'>" + row.recordWarehouse.whName + "</div>";
                            } else {
                                return "获取不到该仓库名称！";
                            }
                        }
                    }, {
                        field: 'whAddress', title: '仓库地址', width: 100, align: 'center', sortable: false, hidden: false,
                        formatter: function (value, row, index) {
                            if (row.recordWarehouse) {
                                return "<div  class='textEllipsis'>" + row.recordWarehouse.whAddress + "</div>";
                            } else {
                                return "获取不到该仓库名称！";
                            }
                        }
                    },
                    {
                        field: 'recordGoodId',
                        title: '物料ID',
                        width: 100,
                        align: 'center',
                        sortable: true,
                        hidden: true,
                    },
                    {
                        field: 'goodEncoding',
                        title: '物料编码',
                        width: 150,
                        align: 'center',
                        sortable: false,
                        hidden: false,
                        formatter: function (value, row, index) {
                            if (row.recordGood) {
                                return "<div  class='textEllipsis'>" + row.recordGood.goodEncoding + "</div>";
                            } else {
                                return "获取不到该物料编码！";
                            }
                        }
                    },
                    {
                        field: 'goodName', title: '物料名称', width: 150, align: 'center', sortable: false, hidden: false,
                        formatter: function (value, row, index) {
                            if (row.recordGood) {
                                return "<div  class='textEllipsis'>" + row.recordGood.goodName + "</div>";
                            } else {
                                return "获取不到该物料名称！";
                            }
                        }
                    }, {
                        field: 'goodModel', title: '物料型号', width: 150, align: 'center', sortable: false, hidden: true,
                        formatter: function (value, row, index) {
                            if (row.recordGood) {
                                return "<div  class='textEllipsis'>" + row.recordGood.goodModel + "</div>";
                            } else {
                                return "获取不到该物料型号！";
                            }
                        }
                    },
                    {
                        field: 'performStatus',
                        title: '性能类别',
                        width: 100,
                        align: 'center', sortable: true, tooltip: true,
                        formatter: function (value, row, index) {
                            if (row) {
                                return row.recordGood.perform.typeName;
                            } else {
                                return "获取不到！";
                            }
                        }
                    }, {
                        field: 'overhaulStatus',
                        title: '检修类别',
                        width: 100,
                        align: 'center', sortable: true, tooltip: true,
                        formatter: function (value, row, index) {
                            if (row) {
                                return row.recordGood.overhaul.typeName;
                            } else {
                                return "获取不到！";
                            }
                        }
                    },
                    {field: 'recordNumber', title: '数量', width: 50, align: 'center', sortable: true, hidden: false},

                ]],
                // queryParams:{},
                fit: true,
                fitColumns:
                    false,
                // sortName: 'materielsId',
                // sortOrder: 'desc',
                pagination:
                    true,// 如果为true，则在DataGrid控件底部显示分页工具栏。
                striped:
                    true,// 是否显示斑马线效果。
                collapsible:
                    true,	//定义是否显示可折叠按钮。
                rownumbers:
                    true,//如果为true，则显示一个行号列。
                nowrap:
                    true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
                remoteSort:
                    false,//定义从服务器对数据进行排序。
                toolbar:
                    "#tb",
                loading:
                    true,//显示载入状态。
                loadMsg:
                    '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
                pageNumber:
                    1,// 在设置分页属性的时候初始化页码。
                pageSize:
                    20,// 在设置分页属性的时候初始化页面大小。
                pageList:
                    [10, 20, 50, 100, 200],//在设置分页属性的时候 初始化页面大小选择列表。
                border:
                    false,
                autoHighlightColumn:
                    true,
                enableHeaderContextMenu:
                    true,
                enableHeaderClickMenu:
                    true,
                rowTooltip:
                    false, //是否启用行数据的 tooltip 功能，若该属性为true，则设置在columns中的tooltip会自动失效
                // view: detailview,
                // detailFormatter: function(rowIndex, rowData){
                //     return '<table><tr>' +
                //         '<td rowspan=2 style="border:0"><img src="images/' + rowData.itemid + '.png" style="height:50px;"></td>' +
                //         '<td style="border:0">' +
                //         '<p>Attribute: ' + rowData.attr1 + '</p>' +
                //         '<p>Status: ' + rowData.status + '</p>' +
                //         '</td>' +
                //         '</tr></table>';
                // }
            });
        })


        obj = {
            // 查询
            // find: function () {
            //     $("#table").datagrid('load', {
            //         user: $("#user").val(),
            //         date: $.trim($("#dd").val())
            //     })
            // },
            // 添加
            addBox: function () {
            },
            //excel导入
            openExcel: function () {

            }
            ,
            // 编辑
            edit: function (id) {

            }
            ,
            // 更新
            update: function () {
            }
            ,
            // 查阅
            look: function () {
            }
            ,
            // 求总
            sum: function () {
            }
            ,
            // 删除多个
            del: function () {

            }

        }

        /**
         * 重置
         */
        function resetSearch() {
            $('#searchForm').form('clear');
            $('#dg').datagrid('load', {
                "recordGoodId": null,
                "recordWarehouseId": null,
            });
        }


        function searchTable() {
            var goodId = $('#searchGood').combobox('getValue');
            var whId = $('#SearchWhId').combobox('getValue');

            $('#dg').datagrid('load', {
                "recordGoodId": goodId,
                "recordWarehouseId": whId,
            });

        }

        $(function () {
            $('#SearchWhId').combobox({
                disabled: false,
                url: '${pageContext.request.contextPath}/warehouse/searchAllWarehouse',
                valueField: 'whId',
                textField: 'whName',
                // onLoadSuccess: function (rec) {//加载完成后,val[0]写死设置选中第一项
                //     $(this).combobox('setValue', rec[0].parkID);
                // },
            }).combobox("clear");
            // 自动补全
            $('#searchGood').combobox(
                {
                    disabled: false,
                    url: '${pageContext.request.contextPath}/wmsGood/searchAllWmsGood',
                    valueField: 'id',
                    textField: 'goodEncoding',
                });


        })


        function endRecordInFuc() {
            $("#addRecordInForm").form('clear');
            $('#addRecordIn').dialog('close');
        }
    </script>
</head>
<body style="margin: 1px">
<table id="dg" title="物料总汇记录表"></table>
<div id="tb">
    <form id="searchForm">
        <div>
            <label>操作时间:</label>&nbsp;
            <label>物料编码:</label><input name="recordGoodId" class="easyui-combobox" id="searchGood"/>
            <label>库存仓库:</label><input name="recordWarehouseId" class="easyui-combobox" id="SearchWhId"/>
            <a href="javascript:searchTable()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
               id="btnSearch">查询</a>
            <a href="javascript:resetSearch()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置</a>
        </div>
    </form>
</div>


<style type="text/css">
    /* 文本超长class */
    .textEllipsis {
        /*color:red;*/
        overflow: hidden; /*不允许滚动条*/
        white-space: nowrap; /*不允许文本换行*/
        text-overflow: ellipsis; /*文本超长显示省略号*/
    }

    /* 鼠标移上,显示全文class */
    .textEllipsis:hover {
        height: auto;
        word-break: break-all;
        white-space: pre-wrap;
        text-decoration: none;
    }

</style>
</body>
</html>