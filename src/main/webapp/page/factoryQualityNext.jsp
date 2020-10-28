<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>质检人员再次编辑</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plupload/queue/css/jquery.plupload.queue.css"
          type="text/css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <style type="text/css">
        .table th {
            font-weight: bold;
        }

        .table th,
        .table td {
            padding: 8px;
            line-height: 20px;
        }

        .table td {
            text-align: center;
        }

        .table-border {
            border-top: 1px solid #ddd;
        }

        .table-border th,
        .table-border td {
            border-bottom: 1px solid #ddd;
        }

        .table-bordered {
            border: 1px solid #ddd;
            border-collapse: separate;
            *border-collapse: collapse;
            border-left: 0;
        }

        .table-bordered th,
        .table-bordered td {
            border-left: 1px solid #ddd;
        }

        .table-border.table-bordered {
            border-bottom: 0;
        }

        .table-striped tbody > tr:nth-child(odd) > td,
        .table-striped tbody > tr:nth-child(odd) > th {
            background-color: #f9f9f9;
        }
    </style>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/date.format/date.format.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plupload/plupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plupload/plupload.html4.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plupload/plupload.html5.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plupload/plupload.flash.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plupload/zh_CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/plupload/queue/jquery.plupload.queue.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/myplupload.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/datagrid-detailview.js"></script>

    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>

    <link href="http://www.easyui-extlib.com/Content/icons/icon-standard.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/jeasyui-extensions-datagrid.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-menu.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-datagrid-getColumnInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-datagrid-columnToggle.js"></script>

    <script src="http://www.easyui-extlib.com/Scripts/jquery-easyui-extensions/datagrid/jeasyui.extensions.datagrid.getDom.js"></script>
    <script src="http://www.easyui-extlib.com/Scripts/jquery-easyui-extensions/datagrid/jeasyui.extensions.datagrid.getRowData.js"></script>
    <script src="http://www.easyui-extlib.com/Scripts/jquery-easyui-extensions/datagrid/jeasyui.extensions.datagrid.tooltip.js"></script>

    <script type="text/javascript">
        var ddv;
        var checkoutMount;
        var checkoutNotQuaMount;
        var purchaseMount;
        var RCheckoutMount;
        var RCheckoutNotQuaMount;
        var checkoutNotQuaMount;
        var str;
        var pageContext = "${pageContext.request.contextPath}";
        str = '质检返修';

        function submit(state) {
            $("#fm").form("submit", {
                url: '${pageContext.request.contextPath}/needTask/needFactoryPurchase?state=' + state,
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统提示", "提交成功！");
                        setTimeout(function () {
                            window.parent.refreshTabData(str, window.top.reload_factory);
                        }, 1500);
                        // closeTodo();
                    } else {
                        $.messager.alert("系统提示", "提交失败，请联系管理员！");
                        return;
                    }
                }
            });
        }

        function openCheckoutDialogAdd(NID, goodsId) {
            $("#dlgCheckout").dialog("open").dialog("setTitle", "编辑（" + goodsId + "）送检日报表");
            $("#flag").val(1);
            $("#NID_t").val(NID);
            $("#goodsId_t").val(goodsId);
        }

        function openCheckoutDialogUpdate(NID, goodsId, checkoutId) {
            debugger
            $("#dlgCheckout").dialog("open").dialog("setTitle", "编辑（" + goodsId + "）送检日报表");
            $("#flag").val(2);
            $("#NID_t").val(NID);
            $("#checkoutId_t").val(checkoutId);
        }

        function checkCheckoutSubmit() {
            debugger
            // var flag = $("#flag").val();
            // $("#checkoutFlag").val(2);
            // var countNow = $("#checkoutMount").val() + $("#checkoutNotQuaMount").val();
            // if(countNow > purchaseMount){
            //     $.messager.alert("系统提示", "质检数量(合格与不合格数量)超过送货数量！");
            //     return;
            // }else{
            //     if (flag == 1) {
            //         addCheckout()
            //     } else if (flag == 2) {
            //         updateCheckout()
            //     }
            // }
            var flag = $("#flag").val();
            $("#checkoutFlag").val(2);
            var temp1 = parseInt($("#checkoutMount").val());
            var temp2 = parseInt($("#checkoutNotQuaMount").val());
            var countNow = temp1 + temp2;
            if(countNow > purchaseMount){
                $.messager.alert("系统提示", "质检数量(合格与不合格数量)超过送货数量！");
                return;
            }else if((typeof checkoutMount == "undefined") || (typeof checkoutNotQuaMount == "undefined")){
                if (flag == 1) {
                    addCheckout()
                } else if (flag == 2) {
                    updateCheckout()
                }
                return;
            }else if((temp1 != checkoutMount) || (temp2 != checkoutNotQuaMount)){
                // $.messager.alert("系统提示", "不可修改上次质检数量(合格与不合格数量)！");
                // return;
                if (flag == 1) {
                    addCheckout()
                } else if (flag == 2) {
                    updateCheckout()
                }
            }
            // else {
            //     if (flag == 1) {
            //         addCheckout()
            //     } else if (flag == 2) {
            //         updateCheckout()
            //     }
            // }
        }

        function checkCheckout() {
            debugger
            var flag = $("#flag").val();
            $("#checkoutFlag").val(1);
            var temp1 = $("#checkoutMount").val();
            var temp2 = $("#checkoutNotQuaMount").val();
            if((temp1 != checkoutMount) || (temp2 != checkoutNotQuaMount)){
                $.messager.alert("系统提示", "不可修改上次质检数量(合格与不合格数量)！");
                return;
            }else{
                if (flag == 1) {
                    addCheckout()
                } else if (flag == 2) {
                    updateCheckout()
                }
            }
        }

        function addCheckout() {
            $("#editCheckoutForm").form("submit", {
                url: '${pageContext.request.contextPath}/checkout/addCheckout',
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统系统", "保存成功！");
                        $("#dlgCheckout").dialog("close");
                        ddv.datagrid("reload");
                        // resetValue();
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
        }

        function updateCheckout() {
            debugger
            $("#editCheckoutForm").form("submit", {
                url: '${pageContext.request.contextPath}/checkout/updateCheckout',
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统系统", "保存成功！");
                        $("#dlgCheckout").dialog("close");
                        ddv.datagrid("reload");
                        // resetValue();
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
        }

        function openRCheckoutDialogAdd(NID, goodsId) {
            $("#dlgRCheckout").dialog("open").dialog("setTitle", "编辑（" + goodsId + "）返修日报表");
            $("#flagRCheckout").val(1);
            $("#NID_t").val(NID);
            $("#goodsId_t").val(goodsId);
        }

        function openRCheckoutDialogUpdate(NID, goodsId, rCheckoutId) {
            debugger
            $("#dlgRCheckout").dialog("open").dialog("setTitle", "编辑（" + goodsId + "）返修日报表");
            $("#flagRCheckout").val(2);
            $("#NID_t").val(NID);
            $("#rCheckoutId_t").val(rCheckoutId);
        }

        function checkRCheckoutSubmit() {
            debugger
            var flagRCheckout = $("#flagRCheckout").val();
            $("#RCheckoutFlag").val(2);
            var temp1 = $("#RCheckoutMount").val();
            var temp2 = $("#RCheckoutNotQuaMount").val();
            // if(temp1 > RCheckoutNotQuaMount){
            //     $.messager.alert("系统提示", "返修中合格数量超过上次返修合格数量！");
            //     return;
            // }else if(temp2 > RCheckoutNotQuaMount){
            //     $.messager.alert("系统提示", "返修中不合格数量超过上次返修不合格数量！");
            //     return;
            if(temp1 + temp2 > checkoutNotQuaMount){
                $.messager.alert("系统提示", "返修中数量超过质检不合格数量！");
                return;
            }else{
                if (flagRCheckout == 1) {
                    addCheckout()
                } else if (flagRCheckout == 2) {
                    updateCheckout()
                }
            }
        }

        function checkRCheckout() {
            debugger
            var flagRCheckout = $("#flagRCheckout").val();
            $("#RCheckoutFlag").val(1);
            var temp1 = $("#RCheckoutMount").val();
            var temp2 = $("#RCheckoutNotQuaMount").val();
            if((temp1 != RCheckoutMount) || (temp2 != RCheckoutNotQuaMount)){
                $.messager.alert("系统提示", "不可修改上次返修数量(合格与不合格数量)！");
                return;
            }else{
                if (flagRCheckout == 1) {
                    addRCheckout()
                } else if (flagRCheckout == 2) {
                    updateRCheckout()
                }
            }
        }

        function addRCheckout() {
            $("#editRCheckoutForm").form("submit", {
                url: '${pageContext.request.contextPath}/rCheckout/addRCheckout',
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统系统", "保存成功！");
                        $("#dlgRCheckout").dialog("close");
                        ddv.datagrid("reload");
                        // resetValue();
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
        }

        function updateRCheckout() {
            debugger
            $("#editRCheckoutForm").form("submit", {
                url: '${pageContext.request.contextPath}/rCheckout/updateRCheckout',
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统系统", "保存成功！");
                        $("#dlgRCheckout").dialog("close");
                        ddv.datagrid("reload");
                        // resetValue();
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
        }

        function openRCheckoutListDialog1(rCheckoutId) {
            $("#dlgRCheckoutList1").dialog("open").dialog("setTitle", "（" + rCheckoutId + "）返修历史记录表");
            $('#dgRCheckoutList').datagrid({
                url: '${pageContext.request.contextPath}/rCheckoutList/rCheckoutListPage',
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'RCheckoutListId', title: '返修记录Id', width: 20, align: 'center', hidden: true},
                    {
                        field: 'RCheckoutDate', title: '返修日期', width: 20, align: 'center',  tooltip: true,
                        formatter: function (value, row, index) {
                            return row.rCheckout.RCheckoutDate;
                        }
                    },
                    {
                        field: 'RCheckoutMount', title: '合格数量', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return row.rCheckout.RCheckoutMount;
                        }
                    },
                    {
                        field: 'RCheckoutNotQuaMount', title: '不合格数量', width: 20, align: 'center', hidden: true,
                        formatter: function (value, row, index) {
                            return row.rCheckout.RCheckoutNotQuaMount;
                        }
                    },
                    {
                        field: 'RCheckoutResult', title: '返修结果', width: 20, align: 'center', tooltip: true,
                        formatter: function (value, row, index) {
                            return row.rCheckout.RCheckoutResult;
                        }
                    },
                    {
                        field: 'RCheckoutDesc', title: '返修描述', width: 20, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            return row.rCheckout.RCheckoutDesc;
                        }
                    },
                    {
                        field: 'RCheckoutNum', title: '当前返修次数', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return "第" + row.rCheckout.RCheckoutNum + "次";
                        }
                    }
                ]],
                fit: true,
                fitColumns: true,
                iconCls: 'icon-tip',
                pagination: true,// 如果为true，则在DataGrid控件底部显示分页工具栏。
                singleSelect: true,// 如果为true，则只允许选择一行。
                striped: true,// 是否显示斑马线效果。
                collapsible: true,	//定义是否显示可折叠按钮。
                rownumbers: true,//如果为true，则显示一个行号列。
                nowrap: true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
                // sortName: 'goodsId',//定义哪些列可以进行排序。
                // sortOrder: 'desc',//定义列的排序顺序，只能是'asc'或'desc'。
                remoteSort: false,//定义从服务器对数据进行排序。
                loading: true,//显示载入状态。
                loadMsg: '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
                pageNumber: 1,// 在设置分页属性的时候初始化页码。
                pageSize: 50,// 在设置分页属性的时候初始化页面大小。
                pageList: [10, 20, 30, 40, 50],//在设置分页属性的时候 初始化页面大小选择列表。
                queryParams: {RCheckoutId: rCheckoutId},
                rowTooltip: false, //是否启用行数据的 tooltip 功能，若该属性为true，则设置在columns中的tooltip会自动失效
                border: false,
                autoHighlightColumn: true,
                enableHeaderContextMenu: true,
                enableHeaderClickMenu: true,
            })
        }

        function openRCheckoutListDialog2() {
            $("#dlgRCheckoutList2").dialog("open").dialog("setTitle", "暂无返修日报表");
        }

        function openPurchaseListDialog1(purchaseId) {
            debugger
            $("#dlgPurchaseList1").dialog("open").dialog("setTitle", "（" + purchaseId + "）返修送货记录表");
            $('#dgPurchaseList').datagrid({
                url: '${pageContext.request.contextPath}/purchaseList/purchaseListPage',
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'purchaseListId', title: '送货记录Id', width: 20, align: 'center', hidden: true},
                    {
                        field: 'purchaseDate', title: '送货日期', width: 20, align: 'center',  tooltip: true,
                        formatter: function (value, row, index) {
                            return row.purchase.purchaseDate;
                        }
                    },
                    {
                        field: 'code', title: '供应商', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return row.purchase.supplier.SUPPLIER_NAME;
                        }
                    },
                    {
                        field: 'purchasedId', title: '送货单号', width: 20, align: 'center', hidden: true,
                        formatter: function (value, row, index) {
                            return row.purchase.purchasedId;
                        }
                    },
                    {
                        field: 'count', title: '送货数量', width: 20, align: 'center', tooltip: true, calcable: true,
                        formatter: function (value, row, index) {
                            return row.purchase.purchaseMount;
                        }
                    },
                    {
                        field: 'purchasePerson', title: '采购人员', width: 20, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            return row.purchase.purchasePerson;
                        }
                    },
                    {
                        field: 'purchaseGoodsPerson', title: '收货人员', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return row.purchase.purchaseGoodsPerson;
                        }
                    },
                    {
                        field: 'REPO_NAME', title: '仓库', width: 20, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            return row.purchase.respository.REPO_NAME;
                        }
                    },
                    {
                        field: 'purchaseLeave', title: '剩余数量', width: 20, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            return row.purchase.purchaseLeave;
                        }
                    },
                    {
                        field: 'total', title: '收货次数', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return "第" + row.purchase.purchaseNum + "次";
                        }
                    },
                ]],
                fit: true,
                fitColumns: true,
                iconCls: 'icon-tip',
                pagination: true,// 如果为true，则在DataGrid控件底部显示分页工具栏。
                singleSelect: true,// 如果为true，则只允许选择一行。
                striped: true,// 是否显示斑马线效果。
                collapsible: true,	//定义是否显示可折叠按钮。
                rownumbers: true,//如果为true，则显示一个行号列。
                nowrap: true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
                // sortName: 'goodsId',//定义哪些列可以进行排序。
                // sortOrder: 'desc',//定义列的排序顺序，只能是'asc'或'desc'。
                remoteSort: false,//定义从服务器对数据进行排序。
                loading: true,//显示载入状态。
                loadMsg: '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
                pageNumber: 1,// 在设置分页属性的时候初始化页码。
                pageSize: 50,// 在设置分页属性的时候初始化页面大小。
                pageList: [10, 20, 30, 40, 50],//在设置分页属性的时候 初始化页面大小选择列表。
                queryParams: {purchaseId: purchaseId},
                rowTooltip: false, //是否启用行数据的 tooltip 功能，若该属性为true，则设置在columns中的tooltip会自动失效
                border: false,
                autoHighlightColumn: true,
                enableHeaderContextMenu: true,
                enableHeaderClickMenu: true,
                // showFooter: true,
            })
        }

        function openPurchaseListDialog2() {
            $("#dlgPurchaseList2").dialog("open").dialog("setTitle", "暂无返修日报表");
        }

        function openCheckoutListDialog1(checkoutId) {
            debugger
            $("#dlgCheckoutList1").dialog("open").dialog("setTitle", "（" + checkoutId + "）质检记录表");
            $('#dgCheckoutList').datagrid({
                url: '${pageContext.request.contextPath}/checkoutList/checkoutListPage',
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'checkoutListId', title: '质检记录Id', width: 20, align: 'center', hidden: true},
                    {
                        field: 'checkoutDate', title: '送检日期', width: 20, align: 'center',  tooltip: true,
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutDate;
                        }
                    },
                    {
                        field: 'SUPPLIER_NAME', title: '供应商', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return row.checkout.supplier.SUPPLIER_NAME;
                        }
                    },
                    {
                        field: 'checkoutMount', title: '合格数量', width: 20, align: 'center', hidden: true,
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutMount;
                        }
                    },
                    {
                        field: 'checkoutNotQuaMount', title: '不合格数量', width: 20, align: 'center', tooltip: true, calcable: true,
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutNotQuaMount;
                        }
                    },
                    {
                        field: 'checkoutType', title: '送检类型', width: 20, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutType;
                        }
                    },
                    {
                        field: 'checkoutMaterial', title: '送检资料', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutMaterial;
                        }
                    },
                    {
                        field: 'checkoutPerson', title: '送检人员', width: 20, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutPerson;
                        }
                    },
                    {
                        field: 'checkoutStatus', title: '首次检验状态', width: 20, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutStatus;
                        }
                    },
                    {
                        field: 'checkoutDataStatus', title: '单据状态', width: 20, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutDataStatus;
                        }
                    },
                    {
                        field: 'checkoutLabelStatus', title: '标签状态', width: 20, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutLabelStatus;
                        }
                    },
                    {
                        field: 'checkoutNum', title: '质检次数', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return "第" + row.checkout.checkoutNum + "次";
                        }
                    },
                ]],
                fit: true,
                fitColumns: true,
                iconCls: 'icon-tip',
                pagination: true,// 如果为true，则在DataGrid控件底部显示分页工具栏。
                singleSelect: true,// 如果为true，则只允许选择一行。
                striped: true,// 是否显示斑马线效果。
                collapsible: true,	//定义是否显示可折叠按钮。
                rownumbers: true,//如果为true，则显示一个行号列。
                nowrap: true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
                // sortName: 'goodsId',//定义哪些列可以进行排序。
                // sortOrder: 'desc',//定义列的排序顺序，只能是'asc'或'desc'。
                remoteSort: false,//定义从服务器对数据进行排序。
                loading: true,//显示载入状态。
                loadMsg: '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
                pageNumber: 1,// 在设置分页属性的时候初始化页码。
                pageSize: 50,// 在设置分页属性的时候初始化页面大小。
                pageList: [10, 20, 30, 40, 50],//在设置分页属性的时候 初始化页面大小选择列表。
                queryParams: {checkoutId: checkoutId},
                rowTooltip: false, //是否启用行数据的 tooltip 功能，若该属性为true，则设置在columns中的tooltip会自动失效
                border: false,
                autoHighlightColumn: true,
                enableHeaderContextMenu: true,
                enableHeaderClickMenu: true,
                // showFooter: true,
            })
        }

        function openCheckoutListDialog2() {
            $("#dlgCheckoutList2").dialog("open").dialog("setTitle", "暂无质检记录");
        }

        function EditQuality(checkoutNotQuaId) {
            debugger
            $("#dlgQuality").dialog("open").dialog("setTitle", "编辑（" + checkoutNotQuaId + "）不合格原因");
            $("#NotQuaId").val(checkoutNotQuaId);
        }

        function checkQuality(NotQuaId) {
            debugger
            $("#editQualityForm").form("submit", {
                url: '${pageContext.request.contextPath}/notQualified/updateNotQualified',
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统系统", "保存成功！");
                        $("#dlgRCheckout").dialog("close");
                        ddv.datagrid("reload");
                        $("#dlgQuality").dialog("close");
                        // resetValue();
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
        }

        $(function () {

            $('#checkoutDate').datetimebox({
                required: true,
                showSeconds: true,
                editable: false,
            });

            $('#RCheckoutDate').datetimebox({
                required: true,
                showSeconds: true,
                editable: false,
            });

            $('#RCheckoutNum').numberspinner({
                min: 1,
                max: 5,
                editable: false
            });

            $('#SUPPLIER_NAME').combobox(
                {
                    prompt: '输入关键字后自动搜索',
                    // mode: 'remote',
                    url: '${pageContext.request.contextPath}/supplier/searchAllSpuplier',
                    valueField: 'supplier_ID',
                    textField: 'supplier_NAME',
                    panelHeight: 'auto',
                    panelMaxHeight: 150,
                    editable: true
                });

            var NID;
            $.post("${pageContext.request.contextPath}/need/getNeedByTaskId", {taskId: '${param.taskId}'}, function (result) {
                need = result.need;
                debugger
                $("#allName").text(need.user.allName);
                var needDate = new Date(need.EApplicantData.time);
                $("#EApplicantData").text(needDate.format("Y-m-d H:i:s"));
                $("#NID").text(need.NID);
                NID = need.NID;
                $("#EPType").text(need.EPType);
                $("#EBillCompany").text(need.EBillCompany);
                if (need.EType == '0') {
                    $("#EType").text("机械生产");
                } else if (need.EType == '4') {
                    $("#EType").text("电气生产");
                } else if (need.EType == '6') {
                    $("#EType").text("机械现场");
                } else if (need.EType == '7') {
                    $("#EType").text("电气现场");
                } else if (need.EType == '8') {
                    $("#EType").text("试制/研发");
                }
                $("#EZone").text(need.EZone);
                $("#EConsumer").text(need.EConsumer);
                $("#city").text(need.subProject.city.cityName);
                $("#park").text(need.subProject.park.parkName);
                $("#subProjectName").text(need.subProject.subProjectName);
                $("#ESysName").text(need.ESysName);
                $("#ESubProjectNameElse").text(need.ESubProjectNameElse);
                $("#ECompanyId").text(need.ECompanyId);
            }, "json");

            $('#QuestionExaminer').combogrid({
                title: '审批人',
                panelWidth: 500,
                remoteSort: false,
                nowrap: false,
                fitColumns: true,
                pagination: true,
                rownumbers: true,
                fitColumns: true,
                striped: true,
            });

            $('#dgStorage').datagrid({
                url: '${pageContext.request.contextPath}/needGoods/needGoodsPage',
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'goodsId', title: '存货编码', width: 20, align: 'center', hidden: true},
                    {
                        field: 'goodsName', title: '名称', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return row.goods.goodsName;
                        }
                    },
                    {
                        field: 'goodsCode', title: '图号', width: 20, align: 'center', tooltip: true,
                        formatter: function (value, row, index) {
                            return row.goods.goodsCode;
                        }
                    },
                    {
                        field: 'goodsType', title: '类型', width: 20, align: 'center', hidden: true, tooltip: true,
                        formatter: function (value, row, index) {
                            return row.goods.goodsType;
                        }
                    },
                    {
                        field: 'goodsUnit', title: '单位', width: 20, align: 'center', hidden: true,
                        formatter: function (value, row, index) {
                            return row.goods.goodsUnit;
                        }
                    },
                    // {
                    //     field: 'mount', title: '需求', width: 20, align: 'center',
                    //     formatter: function (value, row, index) {
                    //         if (row.storage == null) {
                    //             return "无数据";
                    //         } else {
                    //             return row.storage.mount;
                    //         }
                    //     }
                    // },
                    // {
                    //     field: 'mountBack', title: '备用', width: 20, align: 'center',
                    //     formatter: function (value, row, index) {
                    //         if (row.storage == null) {
                    //             return "无数据";
                    //         } else {
                    //             return row.storage.mountBack;
                    //         }
                    //     }
                    // },
                    // {
                    //     field: 'mountIn', title: '库存', width: 20, align: 'center',
                    //     formatter: function (value, row, index) {
                    //         if (row.storage == null) {
                    //             return "无数据";
                    //         } else {
                    //             return row.storage.mountIn;
                    //         }
                    //     }
                    // },
                    {
                        field: 'mountStorage', title: '申购', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            if (row.storage == null) {
                                return "无数据";
                            } else {
                                return row.storage.mountStorage;
                            }
                        }
                    },
                    {
                        field: 'purchaseMount', title: '送货总量', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            if (row.purchase == null) {
                                return "无数据";
                            } else {
                                return row.purchase.purchaseMount;
                            }
                        }
                    },
                    {
                        field: 'ETemp', title: '变更', width: 20, align: 'center', tooltip: true,
                        formatter: function (value, row, index) {
                            if (row.storage == null) {
                                return "无说明";
                            } else {
                                return row.storage.ETemp;
                            }
                        }
                    },
                    {
                        field: 'EPlan', title: '计划备注', width: 20, align: 'center', tooltip: true,
                        formatter: function (value, row, index) {
                            if (row.storage == null) {
                                return "无说明";
                            } else {
                                return row.storage.EPlan;
                            }
                        }
                    },
                    {
                        field: 'EIsRun', title: '发货标识', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            if (row.storage == null) {
                                return "无说明";
                            } else {
                                return row.storage.EIsRun;
                            }
                        }
                    },
                    // {
                    //     field: 'action', title: '操作', width: 20, align: 'center',
                    //     formatter: function (value, row, index) {
                    //         return "<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" onclick=\"$('#dlgStorage').dialog('open')\">编辑</a>";
                    //     }
                    // }
                ]],
                fit: true,
                fitColumns: true,
                iconCls: 'icon-tip',
                pagination: true,// 如果为true，则在DataGrid控件底部显示分页工具栏。
                singleSelect: true,// 如果为true，则只允许选择一行。
                striped: true,// 是否显示斑马线效果。
                collapsible: true,	//定义是否显示可折叠按钮。
                rownumbers: true,//如果为true，则显示一个行号列。
                nowrap: true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
                // sortName: 'goodsId',//定义哪些列可以进行排序。
                // sortOrder: 'desc',//定义列的排序顺序，只能是'asc'或'desc'。
                remoteSort: false,//定义从服务器对数据进行排序。
                loading: true,//显示载入状态。
                loadMsg: '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
                pageNumber: 1,// 在设置分页属性的时候初始化页码。
                pageSize: 50,// 在设置分页属性的时候初始化页面大小。
                pageList: [10, 20, 30, 40, 50],//在设置分页属性的时候 初始化页面大小选择列表。
                queryParams: {taskId: '${param.taskId}'},
                rowTooltip: false, //是否启用行数据的 tooltip 功能，若该属性为true，则设置在columns中的tooltip会自动失效
                border: false,
                autoHighlightColumn: true,
                enableHeaderContextMenu: true,
                enableHeaderClickMenu: true,
                view: detailview,
                detailFormatter: function (index, row) {
                    return '<div style="width:100%;padding:5px 0""><table id="ddv-' + index + '" ></table></div>';
                },
                onExpandRow: function (index, row) {
                    debugger
                    ddv = $(this).datagrid('getRowDetail', index).find('#ddv-' + index);
                    var nId = row.nId;
                    var goodsId = row.goodsId;
                    ddv.datagrid(
                        {
                            url: '${pageContext.request.contextPath}/need/getNeedGoodsById?nId=' + nId + '&goodsId=' + goodsId,
                            fitColumns: true,
                            frozenColumns: [[
                                {field: 'id', checkbox: true}
                            ]],
                            columns: [[
                                {field: 'chaeckoutId', title: '检验单号', width: 20, align: 'center', hidden: true},
                                {
                                    field: 'checkoutDate', title: '送检日期', width: 20, align: 'center', tooltip: true,
                                    formatter: function (value, row, index) {
                                        if (row.checkout == null) {
                                            return "无内容";
                                        } else {
                                            return row.checkout.checkoutDate;
                                        }
                                    }
                                },
                                {
                                    field: 'SUPPLIER_NAME',
                                    title: '供应商',
                                    width: 20,
                                    align: 'center',
                                    tooltip: true,
                                    hidden: true,
                                    formatter: function (value, row, index) {
                                        if (row.checkout == null) {
                                            return "无内容";
                                        } else {
                                            return row.checkout.supplier.SUPPLIER_NAME;
                                        }
                                    }
                                },
                                {
                                    field: 'checkoutMount',
                                    title: '合格数量',
                                    width: 20,
                                    align: 'center',
                                    tooltip: true,
                                    // hidden: true,
                                    formatter: function (value, row, index) {
                                        if (row.checkout == null) {
                                            return "无内容";
                                        } else {
                                            return row.checkout.checkoutMount;
                                        }
                                    }
                                },
                                {
                                    field: 'checkoutNotQuaMount', title: '不合格数量', width: 20, align: 'center',
                                    formatter: function (value, row, index) {
                                        if (row.checkout == null) {
                                            return "无内容";
                                        } else {
                                            return row.checkout.checkoutNotQuaMount;
                                        }
                                    }
                                },
                                {
                                    field: 'checkoutType', title: '送检类型', width: 20, align: 'center', hidden: true,
                                    formatter: function (value, row, index) {
                                        if (row.checkout == null) {
                                            return "无内容";
                                        } else {
                                            return row.checkout.checkoutType;
                                        }
                                    }
                                },
                                {
                                    field: 'checkoutMaterial', title: '送检材料', width: 20, align: 'center', tooltip: true,
                                    formatter: function (value, row, index) {
                                        if (row.checkout == null) {
                                            return "无内容";
                                        } else {
                                            return row.checkout.checkoutMaterial;
                                        }
                                    }
                                },
                                {
                                    field: 'checkoutPerson', title: '送检人员', width: 20, align: 'center',
                                    formatter: function (value, row, index) {
                                        if (row.checkout == null) {
                                            return "无内容";
                                        } else {
                                            return row.checkout.checkoutPerson;
                                        }
                                    }
                                },
                                {
                                    field: 'checkoutStatus', title: '首次送检状态', width: 20, align: 'center',
                                    formatter: function (value, row, index) {
                                        if (row.checkout == null) {
                                            return "无内容";
                                        } else if (row.checkout.checkoutStatus == '1') {
                                            return "合格";
                                        } else if (row.checkout.checkoutStatus == '2') {
                                            return "不合格";
                                        }
                                    }
                                },
                                {
                                    field: 'checkoutDataStatus',
                                    title: '单据状态',
                                    width: 20,
                                    align: 'center',
                                    tooltip: true,
                                    hidden: true,
                                    formatter: function (value, row, index) {
                                        if (row.checkout == null) {
                                            return "无内容";
                                        } else if (row.checkout.checkoutDataStatus == '1') {
                                            return "合格";
                                        } else if (row.checkout.checkoutDataStatus == '2') {
                                            return "不合格";
                                        }
                                    }
                                },
                                {
                                    field: 'checkoutLabelStatus',
                                    title: '标签状态',
                                    width: 20,
                                    align: 'center',
                                    tooltip: true,
                                    hidden: true,
                                    formatter: function (value, row, index) {
                                        if (row.checkout == null) {
                                            return "无内容";
                                        } else if (row.checkout.checkoutLabelStatus == '1') {
                                            return "合格";
                                        } else if (row.checkout.checkoutLabelStatus == '2') {
                                            return "不合格";
                                        }
                                    }
                                },
                                // {
                                //     field: 'checkoutNotQuaId', title: '不合格Id', width: 20, align: 'center', tooltip: true,
                                //     formatter: function (value, row, index) {
                                //         if (row.checkout == null) {
                                //             return "无内容";
                                //         } else {
                                //             return row.checkout.checkoutNotQuaId;
                                //         }
                                //     }
                                // },
                                {
                                    field: 'action', title: '操作', width: 20, align: 'center',
                                    formatter: function (value, row, index) {
                                        if (row.checkout == null) {
                                            return "<p style='color:gold'>" + "暂时全部合格" + "</p>";
                                        } else {
                                            if(row.checkout.checkoutNotQuaId == null ||
                                                (
                                                   (row.checkout.checkoutNotQuaMount == 0) && (row.checkout.checkoutStatus == 1) &&
                                                   (row.checkout.checkoutDataStatus == 1) && (row.checkout.checkoutLabelStatus == 1)
                                                )
                                            ){
                                                return "<p style='color:green'>" + "全部合格" + "</p>";
                                            }else {
                                                return "<a href=\"javascript:EditQuality("+ row.checkout.checkoutNotQuaId +")\" class=\"easyui-linkbutton\">编辑</a>";
                                                // + "<br /><a href=\"javascript:EditQuality("+ row.checkout.checkoutNotQuaId +")\" class=\"easyui-linkbutton\">查看</a>";
                                            }
                                        }
                                    }
                                }
                            ]],
                            fit: false,
                            rownumbers: true,
                            pagination: true,
                            singleSelect: true,
                            pageList: [5, 10, 20, 50],//可以设置每页记录条数的列表
                            //iconCls: 'icon-tip',
                            striped: true,// 是否显示斑马线效果。
                            collapsible: true,	//定义是否显示可折叠按钮。
                            nowrap: true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
                            toolbar: "#editCheckout",
                            // sortName: 'goodsId',//定义哪些列可以进行排序。
                            // sortOrder: 'desc',//定义列的排序顺序，只能是'asc'或'desc'。
                            remoteSort: false,//定义从服务器对数据进行排序。
                            loading: true,//显示载入状态。
                            loadMsg: '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
                            pageNumber: 1,// 在设置分页属性的时候初始化页码。
                            pageSize: 10,// 在设置分页属性的时候初始化页面大小。
                            border: false,
                            autoHighlightColumn: true,
                            enableHeaderContextMenu: true,
                            enableHeaderClickMenu: true,
                            rowTooltip: false, //是否启用行数据的 tooltip 功能，若该属性为true，则设置在columns中的tooltip会自动失效
                            onResize: function () {
                                $('#dgStorage').datagrid('fixDetailRowHeight', index);
                            },
                            onLoadSuccess: function () {
                                $('#dgStorage').datagrid('fixDetailRowHeight', index);
                            },
                        });
                    $('#dgStorage').datagrid('fixDetailRowHeight', index);

                    var buttonBindEvent = function () {
                        $("#btnSave").click(function () {
                            var rows = ddv.datagrid("getRows"), len = rows.length;
                            for (var i = 0; i < len; i++) {
                                if (ddv.datagrid("isEditing", i)) {
                                    ddv.datagrid("endEdit", i);
                                }
                            }
                        });

                        $("#btnDlgCheckout").click(function () {
                            debugger
                            var selectRows = ddv.datagrid("getSelections");
                            if (selectRows.length != 1) {
                                $.messager.alert("系统提示", "请选择一条要编辑的数据！");
                                return;
                            }
                            var row = selectRows[0];
                            if (row.checkout != null) {
                                checkoutMount = row.checkout.checkoutMount;
                                checkoutNotQuaMount = row.checkout.checkoutNotQuaMount;
                                if(row.purchase != null){
                                    purchaseMount = row.purchase.purchaseMount;
                                }
                                $("#checkoutDate").datetimebox("setValue", row.checkout.checkoutDate);
                                $("#SUPPLIER_NAME").combobox("setValue", row.checkout.supplier.SUPPLIER_ID);
                                $("#checkoutMount").textbox("setValue", row.checkout.checkoutMount);
                                $("#checkoutNotQuaMount").textbox("setValue", row.checkout.checkoutNotQuaMount);
                                $("#checkoutType").textbox("setValue", row.checkout.checkoutType);
                                $("#checkoutMaterial").textbox("setValue", row.checkout.checkoutMaterial);
                                $("#checkoutPerson").textbox("setValue", row.checkout.checkoutPerson);
                                $("#checkoutStatus").textbox("setValue", row.checkout.checkoutStatus);
                                $("#checkoutDataStatus").textbox("setValue", row.checkout.checkoutDataStatus);
                                $("#checkoutLabelStatus").textbox("setValue", row.checkout.checkoutLabelStatus);
                                $("#checkoutNum").textbox("setValue", row.checkout.checkoutNum);
                                $("#checkoutNotQuaId").textbox("setValue", row.checkout.checkoutNotQuaId);
                                openCheckoutDialogUpdate(row.nId, row.goods.goodsId, row.checkout.checkoutId);
                            } else {
                                if(row.purchase != null){
                                    purchaseMount = row.purchase.purchaseMount;
                                }
                                $("#checkoutDate").datetimebox("setValue", "");
                                $("#SUPPLIER_NAME").combobox("setValue", "");
                                $("#checkoutMount").textbox("setValue", "0");
                                $("#checkoutNotQuaMount").textbox("setValue","0");
                                $("#checkoutType").textbox("setValue", "");
                                $("#checkoutMaterial").textbox("setValue", "");
                                $("#checkoutPerson").textbox("setValue", "");
                                $("#checkoutStatus").textbox("setValue", "");
                                $("#checkoutDataStatus").textbox("setValue", "");
                                $("#checkoutLabelStatus").textbox("setValue", "");
                                $("#checkoutNum").textbox("setValue", row.checkout.checkoutNum);
                                $("#checkoutNotQuaId").textbox("setValue", "");
                                openCheckoutDialogAdd(row.nId, row.goods.goodsId);
                            }
                        });

                        $("#btnDlgCheckoutRecord").click(function () {
                            debugger
                            var selectRows = ddv.datagrid("getSelections");
                            if (selectRows.length != 1) {
                                $.messager.alert("系统提示", "请选择一条要查看的数据！");
                                return;
                            }
                            var row = selectRows[0];
                            if (row.checkout.notQualified != null) {
                                if(row.checkout != null){
                                    checkoutNotQuaMount = row.checkout.checkoutNotQuaMount;
                                }
                                RCheckoutMount = row.checkout.notQualified.rCheckout.RCheckoutMount;
                                RCheckoutNotQuaMount = row.checkout.notQualified.rCheckout.RCheckoutNotQuaMount;
                                $("#RCheckoutDate").datetimebox("setValue", row.checkout.notQualified.rCheckout.RCheckoutDate);
                                $("#RCheckoutMount").textbox("setValue", row.checkout.notQualified.rCheckout.RCheckoutMount);
                                $("#RCheckoutNotQuaMount").textbox("setValue", row.checkout.notQualified.rCheckout.RCheckoutNotQuaMount);
                                $("#RCheckoutResult").textbox("setValue", row.checkout.notQualified.rCheckout.RCheckoutResult);
                                $("#RCheckoutDesc").textbox("setValue", row.checkout.notQualified.rCheckout.RCheckoutDesc);
                                $("#RCheckoutNum").textbox("setValue", row.checkout.notQualified.rCheckout.RCheckoutNum);
                                openRCheckoutDialogUpdate(row.nId, row.goods.goodsId, row.checkout.notQualified.rCheckout.RCheckoutId);
                            } else {
                                if(row.checkout != null){
                                    checkoutNotQuaMount = row.checkout.checkoutNotQuaMount;
                                }
                                $("#RCheckoutDate").datetimebox("setValue", "");
                                $("#RCheckoutMount").textbox("setValue", "0");
                                $("#RCheckoutNotQuaMount").textbox("setValue", "0");
                                $("#RCheckoutResult").textbox("setValue", "");
                                $("#RCheckoutDesc").textbox("setValue", "");
                                $("#RCheckoutNum").textbox("setValue", "1");
                                openRCheckoutDialogAdd(row.nId, row.goods.goodsId);
                            }
                        });

                        $("#btnDlgCheckoutRecordList").click(function () {
                            debugger
                            var selectRows = ddv.datagrid("getSelections");
                            if (selectRows.length != 1) {
                                $.messager.alert("系统提示", "请选择一条要查看的数据！");
                                return;
                            }
                            var row = selectRows[0];
                            if (row.checkout.notQualified != null) {
                                openRCheckoutListDialog1(row.checkout.notQualified.rCheckout.RCheckoutId);
                            } else {
                                openRCheckoutListDialog2();
                            }
                        });

                        $("#btnDlgPurchaseRecordList").click(function () {
                            debugger
                            var selectRows = ddv.datagrid("getSelections");
                            if (selectRows.length != 1) {
                                $.messager.alert("系统提示", "请选择一条要查看的数据！");
                                return;
                            }
                            var row = selectRows[0];
                            if (row.purchase != null) {
                                openPurchaseListDialog1(row.purchase.purchaseId);
                            } else {
                                openPurchaseListDialog2();
                            }
                        });

                        $("#btnDlgCheckoutList").click(function () {
                            debugger
                            var selectRows = ddv.datagrid("getSelections");
                            if (selectRows.length != 1) {
                                $.messager.alert("系统提示", "请选择一条要查看的数据！");
                                return;
                            }
                            var row = selectRows[0];
                            if (row.purchase != null) {
                                openCheckoutListDialog1(row.checkout.checkoutId);
                            } else {
                                openCheckoutListDialog2();
                            }
                        });
                    };
                    buttonBindEvent();
                },
            });
        });
    </script>
</head>
<body class="easyui-layout" style="margin: 5px">
<div data-options="region:'center',title:'质检人员审批'"
     style="width: 200px;padding: 4px; background-color: #eee">
    <div id="p">
        <form id="fm" method="post">
            <table class="table table-border table-bordered table-striped" style="width: 100%">
                <tr>
                    <td>申请人：</td>
                    <td>
                        <span id="allName"></span>
                    </td>
                    <td>申请时间：</td>
                    <td>
                        <span id="EApplicantData"></span>
                    </td>
                </tr>
                <tr>
                    <td>采购单号：</td>
                    <td>
                        <span id="NID"></span>
                    </td>
                    <td>加工类型：</td>
                    <td>
                        <span id="EPType"></span>
                    </td>
                </tr>
                <tr>
                    <td>提单公司：</td>
                    <td>
                        <span id="EBillCompany"></span>
                    </td>
                    <td>类型/专业：</td>
                    <td>
                        <span id="EType"></span>
                    </td>
                </tr>
                <tr>
                    <td>区域：</td>
                    <td>
                        <span id="EZone"></span>
                    </td>
                    <td>工厂耗材：</td>
                    <td>
                        <span id="EConsumer"></span>
                    </td>
                </tr>
                <tr>
                    <td>工程名称：</td>
                    <td>
                        <span id="city"></span>
                    </td>
                    <td>公园名称：</td>
                    <td>
                        <span id="park"></span>
                    </td>
                </tr>
                <tr>
                    <td>项目名称：</td>
                    <td>
                        <span id="subProjectName"></span>
                    </td>
                    <td>系统名称：</td>
                    <td>
                        <span id="ESysName"></span>
                    </td>
                </tr>
                <tr>
                    <td>子项目名称：</td>
                    <td>
                        <span id="ESubProjectNameElse"></span>
                    </td>
                    <td>公司主体：</td>
                    <td>
                        <span id="ECompanyId"></span>
                    </td>
                </tr>
                <tr>
                    <td>批注：</td>
                    <td colspan="4">
                        <textarea id="comment" name="comment" rows="2" cols="49" class="easyui-validatebox"
                                  required="true"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="hidden" name="taskId" value="${param.taskId}"/>操作：
                    </td>
                    <td colspan="4">
                        <a href="javascript:submit(1)" class="easyui-linkbutton" iconCls="icon-ok">结单</a>
                        &nbsp;&nbsp;
                        <a href="javascript:submit(2)" class="easyui-linkbutton" iconCls="icon-no">驳回</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<div id="uploaderOne" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">
    <table id="findFiles"
           fitColumns="true" fit="true" toolbar="#tb">
    </table>
</div>

<div id="dlgCheckout" class="easyui-window" title="质检-送检日报表" style="width: 400px;height: 450px;"
     data-options="closed:true"
     align="center">
    <form id="editCheckoutForm">
        <table>
            <tr>
                <td>送检日期
                </td>
                <td>
                    <input id="checkoutDate" name="checkoutDate" type="text" class="easyui-validatebox"
                           style="width:200px;"/>
                    <input id="NID_t" type="hidden" name="NID"/>
                    <input id="goodsId_t" type="hidden" name="goodsId"/>
                    <input id="storageId_t" type="hidden" name="storageId"/>
                    <input id="checkoutId_t" type="hidden" name="checkoutId"/>
                    <input type="hidden" id="flag" name="flag"/>
                    <input type="hidden" id="checkoutFlag" name="checkoutFlag"/>
                </td>
            </tr>
            <tr>
                <td>供应商
                </td>
                <td>
                    <input id="SUPPLIER_NAME" name="SUPPLIER_ID" class="easyui-combobox"
                           style="width:200px;"/>
                </td>
            </tr>
            <tr>
                <td>合格数量
                </td>
                <td>
                    <input id="checkoutMount" name="checkoutMount" class="easyui-numberspinner" value="0"
                           data-options="required:false,increment:2" style="width:60px;"/>
                </td>
            </tr>
            <tr>
                <td>不合格数量
                </td>
                <td>
                    <input id="checkoutNotQuaMount" name="checkoutNotQuaMount" class="easyui-numberspinner" value="0"
                           data-options="required:false,increment:2" style="width:60px;"/>
                </td>
            </tr>
            <tr>
                <td>送检类型
                </td>
                <td>
                    <input id="checkoutType" name="checkoutType" type="text" class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
            <tr>
                <td>送检材料
                </td>
                <td>
                    <input id="checkoutMaterial" name="checkoutMaterial" class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
            <tr>
                <td>送检人员
                </td>
                <td>
                    <input id="checkoutPerson" name="checkoutPerson" type="text"
                           class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
            <tr>
                <td>首次送检状态
                </td>
                <td>
                    <span>
                        <input class="checkoutStatus" type="radio" name="checkoutStatus" value="1"
                               checked="true">合格</input>
                        <input class="checkoutStatus" type="radio" name="checkoutStatus" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>单据状态
                </td>
                <td>
                    <span>
                        <input class="checkoutDataStatus" type="radio" name="checkoutDataStatus" value="1"
                               checked="true">合格</input>
                        <input class="checkoutDataStatus" type="radio" name="checkoutDataStatus" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>标签状态
                </td>
                <td>
                    <span>
                        <input class="checkoutLabelStatus" type="radio" name="checkoutLabelStatus" value="1"
                               checked="true">合格</input>
                        <input class="checkoutLabelStatus" type="radio" name="checkoutLabelStatus" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>当前质检次数：
                </td>
                <td>
                    <input id="checkoutNum" name="checkoutNum" class="easyui-numberspinner" value="1" readonly="readonly"
                           data-options="required:false,increment:1" style="width:60px;"/>
                </td>
            </tr>
        </table>
        <br/>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:checkCheckoutSubmit()">送检</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:checkCheckout()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$('#dlgCheckout').dialog('close')">关闭</a>
    </form>
</div>

<div id="dlgQuality" class="easyui-window" title="产品不合格日报表" style="width: 300px;height: 400px;"
     data-options="closed:true"
     align="center">
    <form id="editQualityForm">
        <table>
            <tr>
                <td>外观：
                </td>
                <td>
                    <span>
                        <input class="NotQuaLook" type="radio" name="NotQuaLook" value="1" checked="true">合格</input>
                        <input class="NotQuaLook" type="radio" name="NotQuaLook" value="2">不合格</input>
                        <input id="checkoutId_tt" type="hidden" name="checkoutId"/>
                        <input id="NotQuaId" type="hidden" name="NotQuaId"/>
                    </span>
                </td>
            </tr>
            <tr>
                <td>尺寸：
                </td>
                <td>
                    <span>
                        <input class="NotQuaSize" type="radio" name="NotQuaSize" value="1" checked="true">合格</input>
                        <input class="NotQuaSize" type="radio" name="NotQuaSize" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>硬度：
                </td>
                <td>
                    <span>
                        <input class="NotQuaYd" type="radio" name="NotQuaYd" value="1" checked="true">合格</input>
                        <input class="NotQuaYd" type="radio" name="NotQuaYd" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>型号：
                </td>
                <td>
                    <span>
                        <input class="NotQuaType" type="radio" name="NotQuaType" value="1" checked="true">合格</input>
                        <input class="NotQuaType" type="radio" name="NotQuaType" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>性能：
                </td>
                <td>
                    <span>
                        <input class="NotQuaXn" type="radio" name="NotQuaXn" value="1" checked="true">合格</input>
                        <input class="NotQuaXn" type="radio" name="NotQuaXn" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>探伤：
                </td>
                <td>
                    <span>
                        <input class="NotQuaTs" type="radio" name="NotQuaTs" value="1" checked="true">合格</input>
                        <input class="NotQuaTs" type="radio" name="NotQuaTs" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>资料：
                </td>
                <td>
                    <span>
                        <input class="NotQuaZl" type="radio" name="NotQuaZl" value="1" checked="true">合格</input>
                        <input class="NotQuaZl" type="radio" name="NotQuaZl" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>其他：
                </td>
                <td>
                    <span>
                        <input class="NotQuaQt" type="radio" name="NotQuaQt" value="1" checked="true">合格</input>
                        <input class="NotQuaQt" type="radio" name="NotQuaQt" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>描述：
                </td>
                <td>
                    <textarea type="text" id="NotQuaDesc" name="NotQuaDesc" rows="2" cols="15"
                              class="easyui-validatebox"></textarea>
                </td>
            </tr>
        </table>
        <br/>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:checkQuality()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$('#dlgQuality').dialog('close')">关闭</a>
    </form>
</div>

<div id="dlgRCheckout" class="easyui-window" title="产品返修日报表" style="width: 450px;height: 300px;"
     data-options="closed:true"
     align="center">
    <form id="editRCheckoutForm">
        <table>
            <tr>
                <td>返修日期：
                </td>
                <td>
                    <span>
                        <input id="RCheckoutDate" name="RCheckoutDate" type="text" class="easyui-validatebox"
                               style="width:200px;"/>
                        <input id="rCheckoutId_t" type="hidden" name="rCheckoutId"/>
                        <input type="hidden" id="flagRCheckout" name="flag"/>
                        <input type="hidden" id="RCheckoutFlag" name="RCheckoutFlag"/>
                    </span>
                </td>
            </tr>
            <tr>
                <td>返修中合格数量：
                </td>
                <td>
                    <input id="RCheckoutMount" name="RCheckoutMount" class="easyui-numberspinner" value="0"
                           data-options="required:false,increment:2" style="width:60px;"/>
                </td>
            </tr>
            <tr>
                <td>返修中不合格数量：
                </td>
                <td>
                    <input id="RCheckoutNotQuaMount" name="RCheckoutNotQuaMount" class="easyui-numberspinner" value="0"
                           data-options="required:false,increment:2" style="width:60px;"/>
                </td>
            </tr>
            <tr>
                <td>返修结果：
                </td>
                <td>
                    <input id="RCheckoutResult" name="RCheckoutResult" type="text"
                           class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
            <tr>
                <td>返修描述：
                </td>
                <td>
                    <input id="RCheckoutDesc" name="RCheckoutDesc" type="text" class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
            <tr>
                <td>当前返修次数：
                </td>
                <td>
                    <input id="RCheckoutNum" name="RCheckoutNum" class="easyui-numberspinner" value="1" readonly="readonly"
                           data-options="required:false,increment:1" style="width:60px;"/>
                </td>
            </tr>
        </table>
        <br/>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:checkRCheckoutSubmit()">返修</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:checkRCheckout()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$('#dlgRCheckout').dialog('close')">关闭</a>
    </form>
</div>

<div id="dlgRCheckoutList1" class="easyui-window" title="产品返修历史记录表" style="width: 500px;height: 300px;"
     data-options="closed:true"
     align="center">
    <table id="dgRCheckoutList">
    </table>
</div>

<div id="dlgRCheckoutList2" class="easyui-window" title="产品返修历史记录表" style="width: 500px;height: 300px;"
     data-options="closed:true"
     align="center">
    <span>暂无返修记录</span>
</div>

<div id="dlgPurchaseList1" class="easyui-window" title="送货历史记录表" style="width: 500px;height: 300px;"
     data-options="closed:true"
     align="center">
    <table id="dgPurchaseList">
    </table>
</div>

<div id="dlgPurchaseList2" class="easyui-window" title="送货历史记录表" style="width: 500px;height: 300px;"
     data-options="closed:true"
     align="center">
    <span>暂无送货记录</span>
</div>

<div id="dlgCheckoutList1" class="easyui-window" title="送货历史记录表" style="width: 500px;height: 300px;"
     data-options="closed:true"
     align="center">
    <table id="dgCheckoutList">
    </table>
</div>

<div id="dlgCheckoutList2" class="easyui-window" title="送货历史记录表" style="width: 500px;height: 300px;"
     data-options="closed:true"
     align="center">
    <span>暂无质检记录</span>
</div>

<div data-options="region:'east',title:'批注列表',split:true"
     style="width: 600px;">
    <div style="padding-top: 5px">
        <div id="editCheckout">
            <a id="btnDlgCheckout" class="easyui-linkbutton" iconCls="icon-edit"
               plain="true">编辑/最新质检</a>
            <a id="btnDlgCheckoutList" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">质检历史</a>
            <a id="btnDlgCheckoutRecord" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">最新返修</a>
            <a id="btnDlgCheckoutRecordList" class="easyui-linkbutton" iconCls="icon-save"
               plain="true">返修历史</a>
            <a id="btnDlgPurchaseRecordList" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">送货历史</a>
        </div>
        <table id="dgStorage">
        </table>
        <table id="dg" class="easyui-datagrid"
               fitColumns="true"
               url="${pageContext.request.contextPath}/task/listHistoryComment?taskId=${param.taskId}"
               style="width:100%;height: 200px;">
            <thead>
            <tr>
                <th field="time" width="40" align="center">批注时间</th>
                <th field="userId" width="40" align="center">批注人</th>
                <th field="message" width="40" align="center">批注信息</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<div id="uploader" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">&nbsp;</div>
</body>
</html>
