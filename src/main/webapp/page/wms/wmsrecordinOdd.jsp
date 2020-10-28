<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>入库表单</title>
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
        var newInOdd = ${param.NewInOdd};
        $(document).ready(function () {
            $("#newInOdd1").text(newInOdd);
            $("#newInOdd").textbox('setValue', newInOdd);

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
                        width: 100,
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
                    },{
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
                onBeforeLoad: function (param) {
                    var firstLoad = $(this).attr("firstLoad");
                    if (firstLoad == "false" || typeof (firstLoad) == "undefined")
                    {
                        $(this).attr("firstLoad","true");
                        return false;
                    }
                    return true;
                }
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
                $("#addRecordIn").dialog("open").dialog("setTitle", "新增入库");
            },
            closeBox: function () {
                window.parent.refreshTabData("物料入库", window.top.wmsrecordin_reload);
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
                "startDate": '',
                "endDate": ''
            });
        }


        function searchTable() {
            var inOdd = $("#searchInOdd").textbox('getValue');
            var supplierId = $('#searchSupplier').combobox('getValue');
            var goodId = $('#searchGood').combobox('getValue');
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
                    "startDate": startDate,
                    "endDate": endDate
                });
            }
        }

        $(function () {
            debugger
            // 自动补全
            $('#searchGood').combobox(
                {
                    disabled: false,
                    url: '${pageContext.request.contextPath}/wmsGood/searchAllWmsGood',
                    valueField: 'goodEncoding',
                    textField: 'goodEncoding',
                });
            $('#newSearchGood').combobox({
                disabled: false,
                url: '${pageContext.request.contextPath}/wmsGood/searchAllWmsGood',
                valueField: 'goodEncoding',
                textField: 'goodEncoding',
                onSelect: function (record) {
                    debugger
                    $('#newSearchGoodName').combobox('setValue',record.goodEncoding);//一定要先value后text,否则text与value值会相同全为value值
                    $('#newSearchGoodName').combobox('setText', record.goodName);
                }
                // onLoadSuccess: function (rec) {//加载完成后,val[0]写死设置选中第一项
                //     $(this).combobox('setValue', rec[0].parkID);
                // },
            }).combobox("clear");
            $('#newSearchGoodName').combobox({
                disabled: false,
                url: '${pageContext.request.contextPath}/wmsGood/searchAllWmsGood',
                valueField: 'goodEncoding',
                textField: 'goodName',
                onSelect: function (record) {
                    debugger
                    $('#newSearchGood').combobox('setValue',record.goodEncoding);//一定要先value后text,否则text与value值会相同全为value值
                    $('#newSearchGood').combobox('setText', record.goodEncoding);
                }
            }).combobox("clear");
            $('#newSearchWhId').combobox({
                disabled: false,
                url: '${pageContext.request.contextPath}/warehouse/searchAllWarehouse',
                valueField: 'whId',
                textField: 'whName',
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
        })


        function addRecordInFuc() {
            var isValid = $('#addRecordInForm').form('validate');
            if (isValid == false) {
                $.messager.alert("系统提示", "请填写必要字段！");
                return;
            }
            var newSearchSupplier = $('#newSearchSupplier').combobox('getValue');
            if (newSearchSupplier == "") {
                $.messager.alert('提示', '请选择供应商！', 'info');
                return;
            }
            var newSearchGood = $('#newSearchGood').combobox('getValue');
            if (newSearchGood == "") {
                $.messager.alert('提示', '请选择物料！', 'info');
                return;
            }
            var newSearchWhId = $('#newSearchWhId').combobox('getValue');
            if (newSearchWhId == "") {
                $.messager.alert('提示', '请选择仓库！', 'info');
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
                            $("#addRecordIn").dialog("close");
                            resetValue();
                            // 刷新表格数据
                            $("#dg").datagrid("clearSelections");//解决方法：在删除数据成功后清空所有的已选择项
                            $("#dg").datagrid("reload",{inOdd:newInOdd});
                        }
                    });
                }
            });
        }

        function endRecordInFuc() {
            $("#addRecordInForm").form('clear');
            $('#addRecordIn').dialog('close');
        }
        function closeGaiZao() {
            $("#addRecordIn").dialog("close");
            resetValue();
        }

        function resetValue() {
            $("#inOrder").textbox('setValue','');
            $('#inAmount').numberbox('setValue', null);
            $('#inAmount').numberbox('setValue', null);
        }
    </script>
</head>
<body style="margin: 1px">
<table id="dg" title="入库单号表"></table>
<div id="tb">
    <label>入库单号：</label> <span id="newInOdd1"></span>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="obj.addBox()">新增</a>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-ok" onclick="obj.closeBox()">完成</a>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-excel" onclick="obj.openExcel" id="openExcel">导入</a>
</div>


<!-- 存货编码选择 -->

<div id="addRecordIn" class="easyui-dialog" closed="true" style="width: 580px;height: 450px;padding: 10px 20px "  buttons="#dlg-buttons">
    <form id="addRecordInForm">
        <table>
            <tr>
                <td class="TailLabel">入库单号：</td>
                <td align="center" valign="middle">
                    <input id="newInOdd" name="inOdd" type="text" readonly="readonly"  style="width: 180px"
                           class="easyui-textbox">
                </td>
            </tr>
            <tr>
                <td class="TailLabel">订单号：</td>
                <td align="center" valign="middle">
                    <input id="inOrder" name="inOrder" type="text"   style="width: 180px" required="true"
                           class="easyui-textbox">
                </td>
            </tr>
            <tr>
                <td class="TailLabel">供应商：</td>
                <td align="center" valign="middle">
                    <input id="newSearchSupplier" name="supplierId"  style="width: 180px" class="easyui-combobox">
                </td>
            </tr>
            <tr>
                <td class="TailLabel">物料编码：</td>
                <td align="center" valign="middle">
                    <input id="newSearchGood" name="goodId"  style="width: 180px" class="easyui-combobox">
                </td>
                <td class="TailLabel">物料名称：</td>
                <td align="center" valign="middle">
                    <input id="newSearchGoodName"  style="width: 180px" class="easyui-combobox">
                </td>

            </tr>
            <tr>
                <td class="TailLabel">入库仓库：</td>
                <td align="center" valign="middle">
                    <input id="newSearchWhId" name="whId"  style="width: 180px" class="easyui-combobox">
                </td>
            </tr>
            <tr>
                <td class="TailLabel">数量：</td>
                <td align="center" valign="middle">
                    <input id="inAmount" name="inAmount" required="true"  style="width: 180px" type="text" class="easyui-numberspinner easyui-validatebox">
                </td>
            </tr>
            <tr>
                <td class="TailLabel">价格：</td>
                <td align="center" valign="middle">
                    <input name="inPrice" type="text" required="true"  style="width: 180px" class="easyui-numberspinner easyui-validatebox" data-options="min:0,precision:2">
                </td>
            </tr>
            <tr>
                <td class="TailLabel">入库类别：</td>
                <td align="center" valign="middle">
                    <input type="text" name="inStatus" style="width: 180px"
                           class="easyui-combobox TailInput" editable="false"
                           data-options="valueField:'typeID',textField: 'typeName',
                            url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=inStatus',
                   onLoadSuccess: function(rec){
                    }"
                    ></td>
            </tr>
            <tr>
                <td class="TailLabel">备注：</td>
                <td align="center" valign="middle">
                    <input id="inRemark" name="inRemark" type="text" style="width: 180px"
                           class="easyui-textbox easyui-validatebox">
                </td>
            </tr>

        </table>
        <br/>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:addRecordInFuc()" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
    <a href="javascript:closeGaiZao()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
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