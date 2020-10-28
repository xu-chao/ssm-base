<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>入库明细</title>
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
                    url: '${pageContext.request.contextPath}/wmsrecordin/wmsrecordinPage',
                    frozenColumns: [[
                        {
                            field: 'ck',
                            align: 'center',
                            checkbox: true
                        },
                        {
                            field: 'inOdd',
                            title: '入库单号',
                            width: 125,
                            align: 'center',
                            sortable: true,
                        },
                    ]],
                    columns: [[
                        {
                            field: 'inOrder',
                            title: '订单号',
                            width: 100,
                            align: 'center',
                            sortable: true,
                        },
                        {
                            field: 'supplierId', title: '供应商ID', width: 100, align: 'center', sortable: false, hidden: true,
                            formatter: function (value, row, index) {
                                if (row.supplier) {
                                    return "<div  class='textEllipsis'>" + row.supplier.supplierId + "</div>";
                                } else {
                                    return "获取不到该供应商ID！";
                                }
                            }
                        },
                        {
                            field: 'supplierName',
                            title: '供应商名称',
                            width: 120,
                            align: 'center',
                            sortable: false,
                            hidden: false,
                            formatter: function (value, row, index) {
                                if (row.supplier) {
                                    return "<div  class='textEllipsis'>" + row.supplier.supplierName + "</div>";
                                } else {
                                    return "获取不到该供应商名称！";
                                }
                            }
                        },
                        {
                            field: 'goodEncoding',
                            title: '物料编码',
                            width: 100,
                            align: 'center',
                            sortable: false,
                            hidden: false,
                            formatter: function (value, row, index) {
                                if (row.good) {
                                    return "<div  class='textEllipsis'>" + row.good.goodEncoding + "</div>";
                                } else {
                                    return "获取不到该物料编码！";
                                }
                            }
                        },
                        {
                            field: 'goodName', title: '物料名称', width: 100, align: 'center', sortable: false, hidden: false,
                            formatter: function (value, row, index) {
                                if (row.good) {
                                    return "<div  class='textEllipsis'>" + row.good.goodName + "</div>";
                                } else {
                                    return "获取不到该物料名称！";
                                }
                            }
                        }, {
                            field: 'goodModel', title: '物料型号', width: 100, align: 'center', sortable: false, hidden: false,
                            formatter: function (value, row, index) {
                                if (row.good) {
                                    return "<div  class='textEllipsis'>" + row.good.goodModel + "</div>";
                                } else {
                                    return "获取不到该物料型号！";
                                }
                            }
                        },
                        {
                            field: 'warehouse',
                            title: '入库仓库',
                            width: 100,
                            align: 'center',
                            sortable: false,
                            hidden: false,
                            formatter: function (value, row, index) {
                                if (row.warehouse) {
                                    return "<div  class='textEllipsis'>" + row.warehouse.whName + "</div>";
                                } else {
                                    return "获取不到该入库仓库！";
                                }
                            }
                        },
                        {
                            field: 'allName', title: '操作人', width: 100, align: 'center', sortable: false, hidden: false,
                            formatter: function (value, row, index) {
                                if (row.user) {
                                    return "<div  class='textEllipsis'>" + row.user.allName + "</div>";
                                } else {
                                    return "获取不到该操作人！";
                                }
                            }
                        },
                        {field: 'inAmount', title: '数量', width: 50, align: 'center', sortable: true, hidden: false},
                        {field: 'inPrice', title: '单价', width: 50, align: 'center', sortable: true, hidden: false},
                        {field: 'inAllPri', title: '总价', width: 80, align: 'center', sortable: true, hidden: false},
                        {
                            field: 'inSta',
                            title: '入库类别',
                            width: 50,
                            align: 'center',
                            tooltip: true,
                            formatter: function (value, row, index) {
                                if (row.inSta) {
                                    return "<div  class='textEllipsis'>" + row.inSta.typeName + "</div>";
                                } else {
                                    return "获取不到该入库类别！";
                                }
                            }
                },
                {field: 'inDate', title: '提交日期', width: 100, align: 'center', sortable: true, hidden: false},
                {
                    field: 'inRemark', title: '备注', width: 150, align: 'center', sortable: false, hidden: false,
                    formatter: function (value, row, index) {
                        if (value) {
                            return "<div  class='textEllipsis'>" + value + "</div>";
                        } else {
                            return '';
                        }
                    }
                },

        ]],
            // queryParams:{},
            fit: true,
                fitColumns
        :
            false,
                // sortName: 'materielsId',
                // sortOrder: 'desc',
                pagination
        :
            true,// 如果为true，则在DataGrid控件底部显示分页工具栏。
                striped
        :
            true,// 是否显示斑马线效果。
                collapsible
        :
            true,	//定义是否显示可折叠按钮。
                rownumbers
        :
            true,//如果为true，则显示一个行号列。
                nowrap
        :
            true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
                remoteSort
        :
            false,//定义从服务器对数据进行排序。
                toolbar
        :
            "#tb",
                loading
        :
            true,//显示载入状态。
                loadMsg
        :
            '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
                pageNumber
        :
            1,// 在设置分页属性的时候初始化页码。
                pageSize
        :
            20,// 在设置分页属性的时候初始化页面大小。
                pageList
        :
            [10, 20, 50, 100, 200],//在设置分页属性的时候 初始化页面大小选择列表。
                border
        :
            false,
                autoHighlightColumn
        :
            true,
                enableHeaderContextMenu
        :
            true,
                enableHeaderClickMenu
        :
            true,
                rowTooltip
        :
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
                $.ajax({
                    async: false,
                    cache: false,
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/wmsrecordin/getNewInOdd',
                    dataType: "json",
                    error: function () {
                        $.messager.show({
                            title: '警示信息',
                            msg: "新增失败"
                        })
                    },
                    success: function (data) {
                        /**重点：前台接收到返回值，直接处理就行*/
                        // $("#newInOdd").textbox('setValue',data);
                 window.parent.openTab("物料入库", "wms/wmsrecordinOdd.jsp?NewInOdd="+data, 'icon-shebei');

                    }

                });
                // $("#addRecordIn").dialog("open").dialog("setTitle", "新增入库");
            },
            //excel导入
            openExcel: function () {
                $('#importDlg').dialog('open');
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
                var rows = $("#dg").datagrid("getSelections");
                if (rows.length > 0) {
                    $.messager.confirm('确定删除', '你确定要删除你选择的记录吗？', function (flg) {
                        if (flg) {
                            var ids = [];
                            for (i = 0; i < rows.length; i++) {
                                ids.push(rows[i].materielsId);
                            }
                            var num = ids.length;
                            $.ajax({
                                type: 'post',
                                url: '${pageContext.request.contextPath}/materiels/materielsDelete',
                                data: {
                                    ids: ids.join(',')
                                },
                                beforesend: function () {
                                    $("#dg").datagrid('loading');
                                },

                                success: function (result) {
                                    var result = eval('(' + result + ')');
                                    if (result.success) {
                                        // $("#dg").datagrid('loaded');
                                        $("#dg").datagrid('load');
                                        // $("#dg").datagrid('unselectAll');
                                        $.messager.show({
                                            title: '提示',
                                            msg: num + '个信息被删除'
                                        })
                                    } else {
                                        $.messager.show({
                                            title: '警示信息',
                                            msg: "信息删除失败"
                                        })
                                    }
                                }
                            })
                        }
                    })
                } else {
                    $.messager.alert('提示', '请选择要删除的记录', 'info');
                }
            }
            <%--,--%>
            <%--// 删除一个--%>
            <%--delOne: function (id) {--%>
            <%--    $.messager.confirm('提示信息', '是否删除所选择记录', function (flg) {--%>
            <%--        if (flg) {--%>
            <%--            $.ajax({--%>
            <%--                type: 'post',--%>
            <%--                url: '${pageContext.request.contextPath}/swylsb/swylsbDelete',--%>
            <%--                data: {--%>
            <%--                    ids: id--%>
            <%--                },--%>
            <%--                beforesend: function () {--%>
            <%--                    $("#dg").datagrid('loading');--%>
            <%--                },--%>
            <%--                success: function (data) {--%>
            <%--                    if (data) {--%>
            <%--                        $("#dg").datagrid("loaded");--%>
            <%--                        $("#dg").datagrid("load");--%>
            <%--                        $("#dg").datagrid("unselectRow");--%>
            <%--                        $.messager.alert("系统提示", "删除成功！");--%>
            <%--                    } else {--%>
            <%--                        $.messager.alert("系统提示", "删除失败！");--%>
            <%--                    }--%>
            <%--                }--%>
            <%--            })--%>
            <%--        }--%>
            <%--    })--%>
            <%--}--%>
        }

        /**
         * 重置
         */
        function resetSearch() {
            $('#searchForm').form('clear');
            $('#dg').datagrid('load', {
                "inOdd": '',
                "supplierId": null,
                "goodId": null,
                "whId": null,
                "startDate": '',
                "endDate": ''
            });
        }


        function searchTable() {
            var inOdd = $("#searchInOdd").textbox('getValue');
            var supplierId = $('#searchSupplier').combobox('getValue');
            var goodId = $('#searchGood').combobox('getValue');
            var whId = $('#SearchWhId').combobox('getValue');
            var startDate = $('#s_startDate').datebox('getValue');
            var endDate = $('#s_endDate').datebox('getValue');
            if ((startDate == '' && endDate != '') || (startDate != '' && endDate == '')) {
                $.messager.alert('温馨提示', '请准确输入操作开始/结束时间');
            } else if (startDate > endDate) {
                $.messager.alert('警告', '结束时间不能早于开始时间!');
            } else {
                $('#dg').datagrid('load', {
                    "inOdd": inOdd,
                    "supplierId": supplierId,
                    "goodId": goodId,
                    "whId": whId,
                    "startDate": startDate,
                    "endDate": endDate
                });
            }
        }

        $(function () {
            $('#importDlg').dialog(
                {
                    title : '导入数据',
                    width : 330,
                    height : 130,
                    modal : true,
                    closed : true,
                    buttons : [
                        {
                            text:'模版',
                            handler : function() {
                                window.open(importExcelUrl);
                            }

                        },
                        {
                            text : '导入',
                            handler : function() {
                                $.ajax({
                                    url: '${pageContext.request.contextPath}/wmsIncoming/wmsIncomingImport',
                                    data : new FormData($('#importForm')[0]),
                                    type : 'post',
                                    processData : false,
                                    contentType : false,
                                    dataType : 'json',
                                    success : function(data) {
                                        var total = 0;
                                        var available = 0;
                                        var index = 0; //add
                                        var msg1 = "物料信息导入成功";
                                        var msg2 = "物料信息导入失败";
                                        var info;
                                        if (data.result == "success") {
                                            total = data.total;
                                            available = data.available;
                                            info = msg1;
                                            info = info + ",总条数：" + total + ",有效条数:" + available;
                                            $.messager.alert('温馨提示',info);
                                            $('#importDialog').dialog('close');
                                            $('#result_grid').datagrid('load',{});
                                            // 成功的话，我们要关闭窗口
                                            // $('#editDlg').dialog('close');
                                            // 刷新表格数据
                                            $("#dg").datagrid("clearSelections");//解决方法：在删除数据成功后清空所有的已选择项
                                            $('#dg').datagrid('reload');
                                        } else {
                                            info = msg2;
                                            total = data.total; //add
                                            index = data.false;
                                            info = info + ",总条数：" + total + ",第:" + index+"条数据错误";
                                            $.messager.alert('温馨提示',info);
                                        }
                                    }
                                });
                            }
                        }
                    ]
                });


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
            $('#newSearchGood').combobox({
                disabled: false,
                url: '${pageContext.request.contextPath}/wmsGood/searchAllWmsGood',
                valueField: 'id',
                textField: 'goodEncoding',
                // onLoadSuccess: function (rec) {//加载完成后,val[0]写死设置选中第一项
                //     $(this).combobox('setValue', rec[0].parkID);
                // },
            }).combobox("clear");
            // 自动补全
            $('#searchSupplier').combobox(
                {
                    disabled: false,
                    url: '${pageContext.request.contextPath}/wmssupplier/searchAllWmssupplier',
                    valueField: 'supplierId',
                    textField: 'supplierName',
                });

            $('#newSearchSupplier').combobox({
                disabled: false,
                url: '${pageContext.request.contextPath}/wmssupplier/searchAllWmssupplier',
                valueField: 'supplierId',
                textField: 'supplierName',
                // onLoadSuccess: function (rec) {//加载完成后,val[0]写死设置选中第一项
                //     $(this).combobox('setValue', rec[0].parkID);
                // },
            }).combobox("clear");

        });


        function addRecordInFuc() {
            var newSearchSupplier = $('#newSearchSupplier').combobox('getValue');
            if (newSearchSupplier == "") {
                $.messager.alert('提示', '请选择供应商！', 'info');
                return;
            }
            var newSearchGood = $('#newSearchGood').combobox('getValue');

            if (newSearchGood == "") {
                $.messager.alert('提示', '请选择物料编码！', 'info');
                return;
            }
            var formData = $('#addRecordInForm').serializeJSON();

            $.ajax({
                url: "${pageContext.request.contextPath}/wmsrecordin/addWmsrecordin",
                data: formData,
                dataType: 'json',
                type: 'post',
                success: function (result) {
                    if (result.success) {
                        message = "新增成功";
                    } else {
                        message = "新增失败";
                    }
                    $.messager.alert("提示", message, 'info', function () {
                        if (result.success) {
                            // 成功的话，我们要关闭窗口
                            // $('#editDlg').dialog('close');
                            // 刷新表格数据
                            $("#dg").datagrid("clearSelections");//解决方法：在删除数据成功后清空所有的已选择项
                            $('#dg').datagrid('reload');
                        }
                    });
                }
            });
        }

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
            <input type="text" id="s_startDate" class="easyui-datebox" style="width:120px" editable="false"/> --
            <input type="text" id="s_endDate" class="easyui-datebox" style="width:120px" editable="false"/>
            <label>入库单号:</label><input name="inOdd" class="easyui-textbox" id="searchInOdd"/>
            <label>供应商:</label><input name="supplierId" class="easyui-combobox" id="searchSupplier"/>
            <label>物料编码:</label><input name="goodId" class="easyui-combobox" id="searchGood"/>
            <label>入库仓库:</label><input name="whId" class="easyui-combobox" id="SearchWhId"/>
            <a href="javascript:searchTable()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
               id="btnSearch">查询</a>
            <a href="javascript:resetSearch()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置</a>
            <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="obj.addBox()">新增</a>
            <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-cut" onclick="obj.del()">删除</a>
            <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-excel" onclick="obj.openExcel()">导入</a>
        </div>
    </form>
</div>


<!-- 存货编码选择 -->

<div id="addRecordIn" class="easyui-dialog" closed="true" style="width: 300px;height: 420px;">
    <form id="addRecordInForm">
        <table>
            <tr>
                <td class="TailLabelc">入库单号：</td>
                <td align="center" valign="middle">
                    <input id="newInOdd" name="inOdd" type="text" readonly="readonly"
                           class="easyui-textbox">
                </td>
            </tr>
            <tr>
                <td class="TailLabelc">供应商：</td>
                <td align="center" valign="middle">
                <input id="newSearchSupplier" name="supplierId" class="easyui-combobox">
                </td>
            </tr>
            <tr>
                <td class="TailLabelc">物料编码：</td>
                <td align="center" valign="middle">
                    <input id="newSearchGood" name="goodId" class="easyui-combobox">
                </td>
            </tr>
            <tr>
                <td class="TailLabelc">数量：</td>
                <td align="center" valign="middle">
                    <input id="inAmount" name="inAmount" type="text" class="easyui-numberbox easyui-validatebox">
                </td>
            </tr>
            <tr>
                <td class="TailLabelc">价格：</td>
                <td align="center" valign="middle">
                    <input name="inPrice" type="text" class="easyui-numberbox" data-options="min:0,precision:2">
                </td>
            </tr>
            <tr>
                <td class="TailLabelc">入库类别：</td>
                <td align="center" valign="middle">
                    <input type="text" name="inStatus"
                     class="easyui-combobox TailInput" editable="false"
                           data-options="valueField:'typeID',textField: 'typeName',
                            url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=inStatus',
                   onLoadSuccess: function(rec){
                    }"
                ></td>
            </tr>
            <tr>
                <td class="TailLabelc">备注：</td>
                <td align="center" valign="middle">
                    <input id="inRemark" name="inRemark" type="text"
                           class="easyui-textbox">
                </td>
            </tr>

        </table>
        <br/>
        <tr>
            <td>
                <a href="#" class="easyui-linkbutton" style="width:122px" onclick="addRecordInFuc()">提交物料</a> 　　 　　　　　
                <a href="#" class="easyui-linkbutton" style="width:122px" onclick="endRecordInFuc()">结束单号</a> 　　 　　　　　
            </td>

        </tr>

    </form>
</div>
<!-- 导入窗口 -->
<div id="importDlg" style="padding: 2px;">
    <form id="importForm" enctype="multipart/form-data">
        导入文件:<input type="file" name="file">
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