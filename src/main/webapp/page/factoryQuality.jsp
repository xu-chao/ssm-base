<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>质检人员编辑</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plupload/queue/css/jquery.plupload.queue.css"
          type="text/css">
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/date.format/date.format.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/datagrid-detailview.js"></script>

    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>

    <link href="${pageContext.request.contextPath}/static/css/icon-standard.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/jeasyui-extensions-datagrid.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-menu.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-datagrid-getColumnInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-datagrid-columnToggle.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.getDom.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.getRowData.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.tooltip.js"></script>

    <%--行回车编辑--%>
    <link href="${pageContext.request.contextPath}/static/css/jeasyui.extensions.validatebox.css"
          rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.validatebox.rules.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.validatebox.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.rowState.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.getColumnInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.editors.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.edit.enterFocus.js"></script>

    <script type="text/javascript">
        var ddv;
        var checkoutMount;
        var checkoutNotQuaMount;
        var purchaseMount;
        var str;
        var pageContext = "${pageContext.request.contextPath}";
        str = '质检返修';

        $(function () {
            $("#btnSubmit").hide();

            $("#btnSubmit").click(function () {
                $("#dlgSubmit").dialog("open");
            });

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

            $("#comment").val("已全部送检!");
        });

        <!-- 提交审批人 -->
        function submit(state) {
            debugger
            $("#state").textbox("setValue", state);
            $("#fm").form("submit", {
                url: '${pageContext.request.contextPath}/needTask/needGoodsTask',
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

        <!-- 编辑checkout -->
        function openCheckoutDialogAdd(NID, goodsId) {
            $("#dlgCheckout").dialog("open").dialog("setTitle", "编辑（" + goodsId + "）送检记录");
            $("#flag").val(1);
            $("#NID_t").val(NID);
            $("#goodsId_t").val(goodsId);
        }

        function openCheckoutDialogUpdate(NID, goodsId, checkoutId) {
            $("#dlgCheckout").dialog("open").dialog("setTitle", "编辑（" + goodsId + "）送检记录");
            $("#flag").val(2);
            $("#NID_t").val(NID);
            $("#checkoutId_t").val(checkoutId);
        }

        function checkCheckout() {
            var flag = $("#flag").val();
            $("#checkoutFlag").val(1);
            var temp1 = parseInt($("#checkoutMount").val());
            var temp2 = parseInt($("#checkoutNotQuaMount").val());
            var countNow = temp1 + temp2;
            if (countNow > purchaseMount) {
                $.messager.alert("系统提示", "质检数量(合格与不合格数量)超过送货数量！");
                return;
            } else if ((typeof checkoutMount == "undefined") || (typeof checkoutNotQuaMount == "undefined")) {
                if (flag == 1) {
                    addCheckout()
                } else if (flag == 2) {
                    updateCheckout()
                }
                return;
            } else if ((temp1 != checkoutMount) || (temp2 != checkoutNotQuaMount)) {
                $.messager.alert("系统提示", "不可修改上次质检数量(合格与不合格数量)！");
                return;
            } else {
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
                        $("#dgStorage").datagrid("reload");
                        // resetValue();
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
        }

        function updateCheckout() {
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
                        $("#dgStorage").datagrid("reload");
                        // resetValue();
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
        }

        <!-- 编辑checkout -->

        <!-- 编辑RCheckout -->
        function EditRCheckout(checkoutNotQuaId) {
            if (typeof checkoutNotQuaId == "undefined") {
                checkoutNotQuaId = "待填写";
            }
            var selectRows = $("#dgStorage").datagrid("getSelections");
            if (selectRows.length != 1) {
                $.messager.alert("系统提示", "请选择一条要查看的数据！");
                return;
            }
            var row = selectRows[0];
            if (row.checkout == null || row.checkout.notQualified == null) {
                $("#RCheckoutDate").datetimebox("setValue", "");
                $("#RCheckoutMount").textbox("setValue", "0");
                $("#RCheckoutNotQuaMount").textbox("setValue", "0");
                $("#RCheckoutResult").textbox("setValue", "");
                $("#RCheckoutDesc").textbox("setValue", "");
                $("#RCheckoutNum").textbox("setValue", "1");
                openRCheckoutDialogAdd(row.nId, row.goods.goodsId);
            } else {
                $("#RCheckoutDate").datetimebox("setValue", row.checkout.notQualified.rCheckout.RCheckoutDate);
                $("#RCheckoutMount").textbox("setValue", row.checkout.notQualified.rCheckout.RCheckoutMount);
                $("#RCheckoutNotQuaMount").textbox("setValue", row.checkout.notQualified.rCheckout.RCheckoutNotQuaMount);
                $("#RCheckoutResult").textbox("setValue", row.checkout.notQualified.rCheckout.RCheckoutResult);
                $("#RCheckoutDesc").textbox("setValue", row.checkout.notQualified.rCheckout.RCheckoutDesc);
                $("#RCheckoutNum").textbox("setValue", row.checkout.notQualified.rCheckout.RCheckoutNum);
                openRCheckoutDialogUpdate(row.nId, row.goods.goodsId, row.checkout.notQualified.rCheckout.RCheckoutId);
            }
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

        function checkRCheckout() {
            debugger
            var flagRCheckout = $("#flagRCheckout").val();
            if (flagRCheckout == 1) {
                addRCheckout()
            } else if (flagRCheckout == 2) {
                updateRCheckout()
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
        <!-- 编辑RCheckout -->

        <!-- 编辑返修历史 -->
        function openRCheckoutListDialog1(rCheckoutId) {
            $("#dlgRCheckoutList1").dialog("open").dialog("setTitle", "（" + rCheckoutId + "）返修历史记录表");
            $('#dgRCheckoutList').datagrid({
                url: '${pageContext.request.contextPath}/rCheckoutList/rCheckoutListPage',
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'RCheckoutListId', title: '返修记录Id', width: 20, align: 'center', hidden: true},
                    {
                        field: 'RCheckoutDate', title: '返修日期', width: 20, align: 'center', tooltip: true,
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

        <!-- 编辑返修历史 -->

        <!-- 编辑不合格原因 -->
        function EditQuality(checkoutNotQuaId) {
            if (typeof checkoutNotQuaId == "undefined") {
                checkoutNotQuaId = "待填写";
            }
            $("#dlgQuality").dialog("open").dialog("setTitle", "编辑（" + checkoutNotQuaId + "）不合格原因");
            $("#NotQuaId").val(checkoutNotQuaId);
        }

        function checkQuality(NotQuaId) {
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

        <!-- 编辑不合格原因 -->

        $(function () {

            var ss = [];

            $('#pg').propertygrid({
                width: 300,
                height: 'auto',
                showGroup: true,
                scrollbarSize: 10,
                fit: true,
                // fitColumns: true,
                // multiple: true,
                columns: [[
                    {field: 'name', title: '属性', width: 80, resizable: true},
                    {field: 'value', title: '值', width: 200, resizable: true}
                ]]
            });

            var NID;
            $.post("${pageContext.request.contextPath}/need/getNeedByTaskId", {taskId: '${param.taskId}'}, function (result) {
                need = result.need;
                var needDate = new Date(need.EApplicantData.time)
                ss.push({
                    "name": "姓名",
                    "value": "<label style='color: orange'>" + need.user.allName + "</label>",
                    "group": "提交人"
                });
                ss.push({"name": "申请日期", "value": needDate.format("Y-m-d H:i:s"), "group": "提交日期"});
                ss.push({
                    "name": "采购单号",
                    "value": "<label style='color: red'>" + need.NID + "</label>",
                    "group": "需求单"
                });
                ss.push({"name": "提单公司", "value": need.EBillCompany, "group": "需求单"});
                if (need.EType == '0' || need.EType == '机械生产') {
                    var strEType = "机械生产";
                } else if (need.EType == '4' || need.EType == '电气生产') {
                    var strEType = "电气生产";
                } else if (need.EType == '6' || need.EType == '机械现场') {
                    var strEType = "机械现场";
                } else if (need.EType == '7' || need.EType == '电气现场') {
                    var strEType = "电气现场";
                } else if (need.EType == '8' || need.EType == '试制/研发') {
                    var strEType = "试制/研发";
                }
                ss.push({"name": "类型", "value": strEType, "group": "需求单"});
                ss.push({"name": "区域", "value": need.EZone, "group": "需求单"});
                ss.push({"name": "工厂耗材", "value": need.EConsumer, "group": "需求单"});
                ss.push({"name": "工程名称", "value": need.subProject.city.cityName, "group": "需求单"});
                ss.push({"name": "公园名称", "value": need.subProject.park.parkName, "group": "需求单"});
                ss.push({"name": "项目名称", "value": need.subProject.subProjectName, "group": "需求单"});
                ss.push({"name": "系统名称", "value": need.ESysName, "group": "需求单"});
                ss.push({"name": "子项目名称", "value": need.ESubProjectNameElse, "group": "需求单"});
                ss.push({"name": "公司主体", "value": need.company.COMPANY_NAME, "group": "需求单"});
                $('#pg').propertygrid('loadData', ss);
                NID = need.NID;
                // need = result.need;
                // debugger
                // $("#allName").text(need.user.allName);
                // var needDate = new Date(need.EApplicantData.time);
                // $("#EApplicantData").text(needDate.format("Y-m-d H:i:s"));
                // $("#NID").text(need.NID);
                // NID = need.NID;
                // $("#EPType").text(need.EPType);
                // $("#EBillCompany").text(need.EBillCompany);
                // if (need.EType == '0') {
                //     $("#EType").text("机械生产");
                // } else if (need.EType == '4') {
                //     $("#EType").text("电气生产");
                // } else if (need.EType == '6') {
                //     $("#EType").text("机械现场");
                // } else if (need.EType == '7') {
                //     $("#EType").text("电气现场");
                // } else if (need.EType == '8') {
                //     $("#EType").text("试制/研发");
                // }
                // $("#EZone").text(need.EZone);
                // $("#EConsumer").text(need.EConsumer);
                // $("#city").text(need.subProject.city.cityName);
                // $("#park").text(need.subProject.park.parkName);
                // $("#subProjectName").text(need.subProject.subProjectName);
                // $("#ESysName").text(need.ESysName);
                // $("#ESubProjectNameElse").text(need.ESubProjectNameElse);
                // $("#ECompanyId").text(need.ECompanyId);
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

            var editBoxing = undefined;
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
                        field: 'goodsUnit', title: '单位', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return row.goods.goodsUnit;
                        }
                    },
                    {
                        field: 'mountStorage', title: '申购', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            if (row.storage == null) {
                                return "";
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
                        field: 'ETemp', title: '变更', width: 20, align: 'center', tooltip: true, hidden: true,
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
                                return "";
                            } else {
                                return row.storage.EPlan;
                            }
                        }
                    },
                    {
                        field: 'EIsRun', title: '发货标识', width: 20, align: 'center', hidden: true,
                        formatter: function (value, row, index) {
                            if (row.storage == null) {
                                return "";
                            } else {
                                return row.storage.EIsRun;
                            }
                        }
                    },
                    {field: 'chaeckoutId', title: '检验单号', width: 20, align: 'center', hidden: true},
                    {
                        field: 'checkoutDate', title: '送检日期', width: 20, align: 'center', tooltip: true,
                        editor: {
                            type: 'datetimebox',
                            options: {
                                required: true,
                                editable: false,
                                disabled: false,
                            }
                        },
                        formatter: function (value, row, index) {
                            if (row.checkout == null) {
                                return "";
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
                        editor: {
                            type: 'combobox',
                            options: {
                                prompt: '输入关键字后自动搜索',
                                required: true,
                                url: '${pageContext.request.contextPath}/supplier/searchAllSpuplier',
                                valueField: 'supplier_ID',
                                textField: 'supplier_NAME',
                                panelHeight: 'auto',
                                panelMaxHeight: 150,
                                editable: true,
                            }
                        },
                        formatter: function (value, row, index) {
                            if (row.checkout == null) {
                                return "";
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
                        editor: {
                            type: 'numberspinner',
                        },
                        formatter: function (value, row, index) {
                            if (row.checkout == null) {
                                return "";
                            } else {
                                return row.checkout.checkoutMount;
                            }
                        }
                    },
                    {
                        field: 'checkoutNotQuaMount', title: '不合格数量', width: 20, align: 'center',
                        editor: {
                            type: 'numberspinner',
                        },
                        formatter: function (value, row, index) {
                            if (row.checkout == null) {
                                return "";
                            } else {
                                return row.checkout.checkoutNotQuaMount;
                            }
                        }
                    },
                    {
                        field: 'checkoutStatus', title: '首次送检状态', width: 20, align: 'center',
                        editor: {
                            type: 'combobox',
                            options: {
                                required: true,
                                valueField: 'checkoutStatus',
                                textField: 'checkoutStatusShow',
                                data: [{
                                    "checkoutStatus": "1",
                                    "checkoutStatusShow": "合格"
                                }, {
                                    "checkoutStatus": "2",
                                    "checkoutStatusShow": "不合格"
                                }],
                                panelHeight: 'auto',
                                panelMaxHeight: 150,
                                editable: false,
                            },
                        },
                        formatter: function (value, row, index) {
                            if (row.checkout == null) {
                                return "";
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
                        editor: {
                            type: 'combobox',
                            options: {
                                required: true,
                                valueField: 'checkoutDataStatus',
                                textField: 'checkoutDataStatusShow',
                                data: [{
                                    "checkoutDataStatus": "1",
                                    "checkoutDataStatusShow": "合格"
                                }, {
                                    "checkoutDataStatus": "2",
                                    "checkoutDataStatusShow": "不合格"
                                }],
                                panelHeight: 'auto',
                                panelMaxHeight: 150,
                                editable: false,
                            },
                        },
                        formatter: function (value, row, index) {
                            if (row.checkout == null) {
                                return "";
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
                        editor: {
                            type: 'combobox',
                            options: {
                                required: true,
                                valueField: 'checkoutLabelStatus',
                                textField: 'checkoutLabelStatusShow',
                                data: [{
                                    "checkoutLabelStatus": "1",
                                    "checkoutLabelStatusShow": "合格"
                                }, {
                                    "checkoutLabelStatus": "2",
                                    "checkoutLabelStatusShow": "不合格"
                                }],
                                panelHeight: 'auto',
                                panelMaxHeight: 150,
                                editable: false,
                            },
                        },
                        formatter: function (value, row, index) {
                            if (row.checkout == null) {
                                return "";
                            } else if (row.checkout.checkoutLabelStatus == '1') {
                                return "合格";
                            } else if (row.checkout.checkoutLabelStatus == '2') {
                                return "不合格";
                            }
                        }
                    },
                    {
                        field: 'checkoutType', title: '送检类型', width: 20, align: 'center',
                        editor: {
                            type: "text"
                        },
                        formatter: function (value, row, index) {
                            if (row.checkout == null) {
                                return "";
                            } else {
                                return row.checkout.checkoutType;
                            }
                        }
                    },
                    {
                        field: 'checkoutMaterial', title: '送检材料', width: 20, align: 'center', tooltip: true,
                        editor: {
                            type: "text"
                        },
                        formatter: function (value, row, index) {
                            if (row.checkout == null) {
                                return "";
                            } else {
                                return row.checkout.checkoutMaterial;
                            }
                        }
                    },
                    {
                        field: 'action', title: '操作', width: 30, align: 'center',
                        formatter: function (value, row, index) {
                            if (row.checkout == null) {
                                $("#btnSubmit").hide();
                                return "<label style='color:gold'>" + "暂时全部合格" + "</label>";
                            } else {
                                if (row.checkout.checkoutNotQuaId == null ||
                                    (
                                        (row.checkout.checkoutNotQuaMount == 0) && (row.checkout.checkoutStatus == 1) &&
                                        (row.checkout.checkoutDataStatus == 1) && (row.checkout.checkoutLabelStatus == 1)
                                    )
                                ) {
                                    $("#btnSubmit").show();
                                    return "<label style='color:green'>" + "全部合格" + "</label>";
                                } else {
                                    $("#btnSubmit").show();
                                    return "<a href=\"javascript:EditQuality(" + row.checkout.checkoutNotQuaId + ")\" class=\"easyui-linkbutton\">编辑</a>";
                                    + "<br /><a href=\"javascript:EditRCheckout("+ row.checkout.checkoutNotQuaId +")\" class=\"easyui-linkbutton\">返修记录</a>";
                                }
                            }
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
                toolbar: [
                    {
                        id: 'btnSubmit',
                        text: '提交',
                        iconCls: 'icon-submit',
                        handler: function () {
                            $("#dlgSubmit").dialog("open");
                        }
                    },
                    '-', {
                        text: '编辑',
                        iconCls: 'icon-edit',
                        handler: function () {
                            var selectRows = $("#dgStorage").datagrid("getSelections");
                            if (selectRows.length != 1) {
                                $.messager.alert("系统提示", "请选择一条要编辑的数据！");
                                return;
                            }
                            var row = selectRows[0];
                            if (row.checkout != null) {
                                checkoutMount = row.checkout.checkoutMount;
                                checkoutNotQuaMount = row.checkout.checkoutNotQuaMount;
                                if (row.purchase != null) {
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
                                if (row.purchase != null) {
                                    purchaseMount = row.purchase.purchaseMount;
                                }
                                $("#checkoutDate").datetimebox("setValue", "");
                                $("#SUPPLIER_NAME").combobox("setValue", "");
                                $("#checkoutMount").textbox("setValue", "0");
                                $("#checkoutNotQuaMount").textbox("setValue", "0");
                                $("#checkoutType").textbox("setValue", "");
                                $("#checkoutMaterial").textbox("setValue", "");
                                $("#checkoutPerson").textbox("setValue", "");
                                $("#checkoutStatus").textbox("setValue", "");
                                $("#checkoutDataStatus").textbox("setValue", "");
                                $("#checkoutLabelStatus").textbox("setValue", "");
                                $("#checkoutNum").textbox("setValue", "1");
                                $("#checkoutNotQuaId").textbox("setValue", "");
                                openCheckoutDialogAdd(row.nId, row.goods.goodsId);
                            }
                        }
                    },
                    '-', {
                        text: '取消编辑',
                        iconCls: 'icon-redo',
                        handler: function () {
                            if (editBoxing == 0) {
                                $('#dgStorage').datagrid('deleteRow', editBoxing);
                                editBoxing = undefined;
                                $('#dgStorage').datagrid('reload');
                            } else {
                                editBoxing = undefined;
                                $('#dgStorage').datagrid('reload');
                            }
                        }
                    }
                ],
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
                onDblClickRow: function (index, row) {
                    $('#dgStorage').datagrid("beginEdit", index);
                },
                // 表示开启单元格编辑功能
                enableCellEdit: false,
                // 表示开启编辑后是否启用回车后自动聚焦下一个列编辑器的功能
                enterFocusNextEditor: true,
                // 表示当编辑器内容为空，是否停止自动聚焦功能
                stopEnterFocusWhenEmpty: false,
                // 表示开始编辑触发的事件
                // onBeforeEdit: function (rowIndex, rowData) {
                //     // 重要! 重新设置百分比填报范围
                //     var columnOption = $('#dgStorage').datagrid('getColumnOption', "purchaseMount");
                //     columnOption.editor.options.min = 0;
                //     columnOption.editor.options.max = rowData.storage.mountStorage;
                // },
                // 表示聚焦到最后一个单元格编辑器之后触发的事件
                onAfterFoucsLastEditor: function (index, field) {
                    var data = $('#dgStorage').datagrid('getData');
                    var row = data.rows[index];
                    debugger
                    if (row.checkout == null || row.purchaseId == "") {
                        $.post('${pageContext.request.contextPath}/checkout/addCheckout', {
                            "NID": row.nId,
                            "goodsId": row.goodsId,
                            "checkoutDate": row.checkoutDate,
                            "SUPPLIER_ID": row.SUPPLIER_NAME,
                            "checkoutMount": row.checkoutMount,
                            "checkoutNotQuaMount": row.checkoutNotQuaMount,
                            "checkoutType": row.checkoutType,
                            "checkoutMaterial": row.checkoutMaterial,
                            "checkoutStatus": row.checkoutStatus,
                            "checkoutDataStatus": row.checkoutDataStatus,
                            "checkoutLabelStatus": row.checkoutLabelStatus,
                        }, function (data) {
                            var data = eval('(' + data + ')');
                            if (data.success == true) {
                                $.messager.show({
                                    title: '新增消息',
                                    msg: '新增成功',
                                    timeout: 3000,
                                });
                                $('#dgStorage').datagrid('reload');
                            } else {
                                $.messager.alert('警告', '未完成新增');
                            }
                            ;
                        });
                    } else {
                        $.post('${pageContext.request.contextPath}/checkout/updateCheckoutThrough', {
                            "checkoutId": row.checkoutId,
                            "checkoutDate": row.checkoutDate,
                            "SUPPLIER_ID": row.SUPPLIER_NAME,
                            "checkoutMount": row.checkoutMount,
                            "checkoutNotQuaMount": row.checkoutNotQuaMount,
                            "checkoutType": row.checkoutType,
                            "checkoutMaterial": row.checkoutMaterial,
                            "checkoutStatus": row.checkoutStatus,
                            "checkoutDataStatus": row.checkoutDataStatus,
                            "checkoutLabelStatus": row.checkoutLabelStatus,
                        }, function (data) {
                            var data = eval('(' + data + ')');
                            if (data.success == true) {
                                $.messager.show({
                                    title: '更新消息',
                                    msg: '更新成功',
                                    timeout: 3000,
                                });
                                $('#dgStorage').datagrid('reload');
                            } else {
                                $.messager.alert('警告', '未完成新增');
                            }
                            ;
                        });
                    }
                }
            });

            $('#dg').datagrid({
                remoteSort: false,
                title: '批注',
                iconCls: 'icon-tip',
                fit: true,
                fitColumns: true,
                rownumbers: true,
                collapsed: false,
                nowrap: false,
                url: '${pageContext.request.contextPath}/task/listHistoryComment?taskId=${param.taskId}',
                columns: [[
                    {
                        field: 'time',
                        title: '任务名称',
                        width: 50,
                        align: 'center'
                    }, {
                        field: 'userId',
                        title: '任务别名',
                        width: 50,
                        align: 'center'
                    }, {
                        field: 'message',
                        title: '任务分组',
                        width: 50,
                        align: 'center'
                    }
                ]],
                // queryParams:{},
                collapsible: true,	//定义是否显示可折叠按钮。
                // pageSize: 10,
                sortName: 'time',
                sortOrder: 'desc',
                border: false,
                autoHighlightColumn: true,
                enableHeaderContextMenu: true,
                enableHeaderClickMenu: true,
            });
        });
    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/autoPinyin.js"></script>
</head>
<body class="easyui-layout" style="margin: 5px">

<!-- 右侧对话框 -->
<div data-options="region:'east',title:'计划单信息',split:true,collapsible:true,collapsed:true"
     style="width: 400px;padding: 1px; background-color: #eee">
    <table id="pg"></table>
</div>

<!-- 审批提交对话框 -->
<div id="dlgSubmit" class="easyui-window" title="流程提交" style="width: 340px;height: 180px;" data-options="closed:true"
     align="center">
    <form id="fm">
        <table>
            <tr>
                <td>批注：</td>
                <td colspan="4">
                        <textarea id="comment" name="comment" rows="5" cols="25" class="easyui-validatebox"
                                  required="true"></textarea>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="hidden" name="taskId" value="${param.taskId}"/>
                    <input type="hidden" name="state" id="state" class="easyui-textbox"/>
                </td>
                <td colspan="4">
                    <a href="javascript:submit(1)" class="easyui-linkbutton" iconCls="icon-ok">完成</a>
                    &nbsp;&nbsp;
                    <a href="javascript:submit(2)" class="easyui-linkbutton" iconCls="icon-no">驳回</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<!-- 编辑对话框 -->
<div id="dlgCheckout" class="easyui-window" title="编辑送检" style="width: 400px;height: 400px;"
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
        </table>
        <br/>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:checkCheckout()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$('#dlgCheckout').dialog('close')">关闭</a>
    </form>
</div>

<!-- 编辑不合格原因对话框 -->
<div id="dlgQuality" class="easyui-window" title="产品不合格" style="width: 300px;height: 320px;"
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

<!-- 编辑产品返修对话框 -->
<div id="dlgRCheckout" class="easyui-window" title="产品返修" style="width: 450px;height: 300px;"
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
        </table>
        <br/>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:checkRCheckout()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$('#dlgRCheckout').dialog('close')">关闭</a>
    </form>
</div>

<!-- 编辑产品返修历史对话框 -->
<div id="dlgRCheckoutList1" class="easyui-window" title="产品返修历史" style="width: 500px;height: 300px;"
     data-options="closed:true"
     align="center">
    <table id="dgRCheckoutList">
    </table>
</div>

<!-- 编辑产品返修历史对话框 -->
<div id="dlgRCheckoutList2" class="easyui-window" title="产品返修历史" style="width: 500px;height: 300px;"
     data-options="closed:true"
     align="center">
    <span>暂无返修记录</span>
</div>

<div data-options="region:'center',collapsible:true,split:true">
    <table id="dgStorage" title="处理">
    </table>
    <table id="dg"></table>
</div>
</body>
</html>
