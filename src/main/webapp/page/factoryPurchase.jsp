<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>负责人审批</title>
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

    <%--右键菜单--%>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.menu.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.rowContext.js"></script>

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
        var mountStorage;
        var purchaseMount;
        var str;
        var assign;
        var pageContext = "${pageContext.request.contextPath}";
        var group_session = '${sessionInfo.groupId }';
        assign = "factoryQuality";
        str = '需求采购';
        $(function () {

            $("#btnSubmit").hide();

            $("#btnSubmit").click(function () {
                $("#dlgSubmit").dialog("open");
            });

            $('#purchaseDate').datetimebox({
                required: true,
                showSeconds: true,
                editable: false,
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

            $('#REPO_ADDRESS').combobox(
                {
                    prompt: '输入关键字后自动搜索',
                    // mode: 'remote',
                    url: '${pageContext.request.contextPath}/respository/searchAllRespository',
                    valueField: 'repo_ID',
                    textField: 'repo_ADDRESS',
                    panelHeight: 'auto',
                    panelMaxHeight: 150,
                    editable: true
                });

            $('#dept').combotree(
                {
                    // prompt: '输入关键字后自动搜索',
                    // value: deptValue_session,
                    missingMessage: '部门名称不能为空!',
                    // mode: 'remote',
                    url: '${pageContext.request.contextPath}/dept/selectChild?deptID=' + "800",
                    onSelect: function (record) {
                        var deptID = record.id;
                        $('#QuestionExaminer').combogrid({
                            method: 'POST',
                            idField: 'id',
                            textField: 'allName',
                            // formatter: function(row){
                            //     row.id = row.firstName;
                            //     return row.id;
                            // },
                            url: '${pageContext.request.contextPath}/user/userPage?deptID=' + deptID + '&groupID=' + assign,
                            columns: [[
                                {field: 'cb', checkbox: true, align: 'center'},
                                {field: 'id', title: '用户名/工号', width: 100, align: 'center'},
                                {field: 'allName', title: '姓名', width: 100, align: 'center'},
                                {field: 'phone', title: '手机号码', width: 100, align: 'center'},
                                {field: 'email', title: '邮箱', width: 100, align: 'center'}
                            ]]
                        });
                    }
                });

            $("#comment").val("已全部送货!");
        });

        <!-- 提交审批人 -->
        function submit(state) {
            debugger
            $("#state").textbox("setValue", state);
            var checkValue = $("#QuestionExaminer").combobox("getValue");//取值  //获取Select选择的Value
            if (state == 1 && checkValue == "") {
                $.messager.alert('', '指定审批人不能为空!', 'error');
                return;
            }
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
                    } else {
                        $.messager.alert("系统提示", "提交失败，请联系管理员！");
                        return;
                    }
                }
            });
        }
        <!-- 提交审批人 -->

        <!-- 编辑purchase -->
        function editPurchase() {
            var selectRows = $("#dgStorage").datagrid("getSelections");
            if (selectRows.length != 1) {
                $.messager.alert("系统提示", "请选择一条要编辑的数据！");
                return;
            }
            var row = selectRows[0];
            if (row.purchase != null) {
                if (row.storage != null) {
                    mountStorage = row.storage.mountStorage;
                }
                purchaseMount = row.purchase.purchaseMount;
                $("#purchaseDate").datetimebox("setValue", row.purchase.purchaseDate);
                $("#SUPPLIER_NAME").combobox("setValue", row.purchase.supplier.SUPPLIER_ID);
                $("#purchasedId").textbox("setValue", row.purchase.purchasedId);
                $("#purchaseMount").textbox("setValue", row.purchase.purchaseMount);
                $("#purchasePerson").textbox("setValue", row.purchase.purchasePerson);
                $("#purchaseGoodsPerson").textbox("setValue", row.purchase.purchaseGoodsPerson);
                $("#REPO_ADDRESS").combobox("setValue", row.purchase.respository.REPO_ID);
                $("#purchaseNum").textbox("setValue", row.purchase.purchaseNum);
                openPurchaseDialogUpdate(row.nId, row.goods.goodsId, row.purchase.purchaseId);
            } else {
                if (row.storage != null) {
                    mountStorage = row.storage.mountStorage;
                }
                $("#purchaseDate").datetimebox("setValue", "");
                $("#SUPPLIER_NAME").combobox("setValue", "");
                $("#purchasedId").textbox("setValue", "");
                $("#purchaseMount").textbox("setValue", "");
                $("#purchasePerson").textbox("setValue", "");
                $("#purchaseGoodsPerson").textbox("setValue", "");
                $("#REPO_ADDRESS").combobox("setValue", "");
                $("#purchaseNum").textbox("setValue", "1");
                openPurchaseDialogAdd(row.nId, row.goods.goodsId);
            }
        }

        function openPurchaseDialogAdd(NID, goodsId) {
            $("#dlgPurchase").dialog("open").dialog("setTitle", "编辑（" + goodsId + "）送货信息");
            $("#flag").val(1);
            $("#NID_t").val(NID);
            $("#goodsId_t").val(goodsId);
        }

        function openPurchaseDialogUpdate(NID, goodsId, purchaseId) {
            $("#dlgPurchase").dialog("open").dialog("setTitle", "编辑（" + goodsId + "）送货信息");
            $("#flag").val(2);
            $("#NID_t").val(NID);
            $("#purchaseId_t").val(purchaseId);
        }

        function checkPurchase() {
            var flag = $("#flag").val();
            $("#purchaseFlag").val(1);
            $("#mountStorage").val(mountStorage);
            if (flag == 1) {
                addPurchase()
            } else if (flag == 2) {
                updatePurchase()
            }
            // var countNow = parseInt($("#purchaseMount").val());
            // if (countNow > mountStorage) {
            //     $.messager.alert("系统提示", "送货数量超过申购数量！");
            //     return;
            // } else if (typeof purchaseMount == "undefined") {
            //     if (flag == 1) {
            //         addPurchase()
            //     } else if (flag == 2) {
            //         updatePurchase()
            //     }
            //     return;
            // } else if ((countNow != purchaseMount)) {
            //     $.messager.alert("系统提示", "不可修改上次送货数量！");
            //     return;
            // } else {
            //     if (flag == 1) {
            //         addPurchase()
            //     } else if (flag == 2) {
            //         updatePurchase()
            //     }
            //     return;
            // }
        }

        function addPurchase() {
            $("#editPurchaseForm").form("submit", {
                url: '${pageContext.request.contextPath}/purchase/addPurchaseThrough',
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统系统", "保存成功！");
                        $("#dlgPurchase").dialog("close");
                        $("#dgStorage").datagrid("reload");
                        // resetValue();
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
        }

        function updatePurchase() {
            $("#editPurchaseForm").form("submit", {
                url: '${pageContext.request.contextPath}/purchase/updatePurchaseThrough',
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统系统", "保存成功！");
                        $("#dlgPurchase").dialog("close");
                        $("#dgStorage").datagrid("reload");
                        // resetValue();
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
        }
        <!-- 编辑purchase -->

        <!-- 保存Doc -->
        function saveErpDoc() {
            var selectRows = $("#dgStorage").datagrid("getSelections");
            if (selectRows.length != 1) {
                $.messager.alert("系统提示", "请选择一条要导出的数据！");
                return;
            }
            var row = selectRows[0];
            if (row.purchase == null) {
                $.messager.alert("系统提示", "请选择一条要导出的数据！");
                return;
            } else {
                var purchaseId = row.purchase.purchaseId;
            }
            $.ajax({
                url: "${pageContext.request.contextPath}/word/getPurchaseWord",
                type: 'post',
                data: {"purchaseId": purchaseId},//返回给客户端的json数据
                async: false,
                success: function (data, filename) {
                    var blob = new Blob([data], {type: 'application/msword'});
                    var link = document.createElement("a");
                    var body = document.querySelector("body");
                    link.href = window.URL.createObjectURL(blob);
                    link.download = "进料验收单";//文件名
                    link.style.display = "none";
                    body.appendChild(link);
                    link.click();
                    body.removeChild(link);
                    window.URL.revokeObjectURL(link.href);
                }
            });
        }
        <!-- 保存Doc -->

        $(function () {

            var ss = [];

            $('#pg').propertygrid({
                // width: 100,
                // height: 'auto',
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
                debugger
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
                    {
                        field: 'mount', title: '需求', width: 20, align: 'center', hidden: true,
                        formatter: function (value, row, index) {
                            if (row.storage == null) {
                                return "";
                            } else {
                                return row.storage.mount;
                            }
                        }
                    },
                    {
                        field: 'mountBack', title: '备用', width: 20, align: 'center', hidden: true,
                        formatter: function (value, row, index) {
                            if (row.storage == null) {
                                return "";
                            } else {
                                return row.storage.mountBack;
                            }
                        }
                    },
                    {
                        field: 'mountIn', title: '库存', width: 20, align: 'center', hidden: true,
                        formatter: function (value, row, index) {
                            if (row.storage == null) {
                                return "";
                            } else {
                                return row.storage.mountIn;
                            }
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
                        field: 'ETemp', title: '变更', width: 20, align: 'center', tooltip: true,
                        formatter: function (value, row, index) {
                            if (row.storage == null) {
                                return "";
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
                    {field: 'purchaseId', title: '流水号', width: 20, align: 'center', hidden: true},
                    {
                        field: 'purchaseDate', title: '收货日期', width: 20, align: 'center', tooltip: true,
                        editor: {
                            type: 'datetimebox',
                            options: {
                                required: true,
                                editable: false,
                                disabled: false,
                            }
                        },
                        formatter: function (value, row, index) {
                            if (row.purchase == null) {
                                return "";
                            } else {
                                return row.purchase.purchaseDate;
                            }
                        }
                    },
                    {
                        field: 'SUPPLIER_NAME', title: '供应商', width: 20, align: 'center', tooltip: true,
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
                            if (row.purchase == null) {
                                return "";
                            } else {
                                return row.purchase.supplier.SUPPLIER_NAME;
                            }
                        }
                    },
                    {
                        field: 'purchasedId', title: '送货单号', width: 20, align: 'center', hidden: true,
                        editor: {
                            type: "text"
                        },
                        formatter: function (value, row, index) {
                            if (row.purchase == null) {
                                return "";
                            } else {
                                return row.purchase.purchasedId;
                            }
                        }
                    },
                    {
                        field: 'purchaseMount', title: '收货数量', width: 20, align: 'center',
                        editor: {
                            type: 'numberspinner',
                            options: {
                                increment: 10,
                                min: 0,
                                max: 10
                            }
                        },
                        formatter: function (value, row, index) {
                            if (row.purchase == null) {
                                $("#btnSubmit").hide();
                                return "<label style='color: red'>0</label>";
                            } else if (row.purchase.purchaseLeave == 0) {
                                $("#btnSubmit").show();
                                var count = row.storage.mountStorage - row.purchase.purchaseLeave;
                                return "<span style='color:green'>" + count + "</span>";
                            } else {
                                $("#btnSubmit").hide();
                                var count = row.storage.mountStorage - row.purchase.purchaseLeave;
                                return "<span style='color:red'>" + count + "</span>";
                            }
                        }
                    },
                    {
                        field: 'REPO_ADDRESS', title: '仓库', width: 20, align: 'center', tooltip: true,
                        editor: {
                            type: 'combobox',
                            options: {
                                prompt: '输入关键字后自动搜索',
                                required: true,
                                url: '${pageContext.request.contextPath}/respository/searchAllRespository',
                                valueField: 'repo_ID',
                                textField: 'repo_ADDRESS',
                                panelHeight: 'auto',
                                panelMaxHeight: 150,
                                editable: true,
                            }
                        },
                        formatter: function (value, row, index) {
                            if (row.purchase == null) {
                                return "";
                            } else {
                                return row.purchase.respository.REPO_ADDRESS;
                            }
                        }
                    },
                    {
                        field: 'purchasePerson', title: '采购人员', width: 20, align: 'center',
                        editor: {
                            type: "text"
                        },
                        formatter: function (value, row, index) {
                            if (row.purchase == null) {
                                return "";
                            } else {
                                return row.purchase.purchasePerson;
                            }
                        }
                    },
                    // {
                    //     field: 'action', title: '操作', width: 20, align: 'center',
                    //     formatter: function (value, row, index) {
                    //         return "<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" onclick=\"$('#dlgStorage').dialog('open')\">编辑</a>";
                    //         // + "<br /><a id=\"btnSave\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-save',plain:true\">保存</a>";
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
                // toolbar: "#editStorage",
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
                            editPurchase();
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
                    },
                    '-', {
                        text: '导出进料验收单',
                        iconCls: 'icon-excel',
                        handler: function () {
                            saveErpDoc();
                        }
                    },
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
                onBeforeEdit: function (rowIndex, rowData) {
                    // 重要! 重新设置百分比填报范围
                    var columnOption = $('#dgStorage').datagrid('getColumnOption', "purchaseMount");
                    columnOption.editor.options.min = 0;
                    columnOption.editor.options.max = rowData.storage.mountStorage;
                },
                // 表示聚焦到最后一个单元格编辑器之后触发的事件
                onAfterFoucsLastEditor: function (index, field) {
                    var data = $('#dgStorage').datagrid('getData');
                    var row = data.rows[index];
                    debugger
                    if (row.purchase == null || row.purchaseId == "") {
                        $.post('${pageContext.request.contextPath}/purchase/addPurchaseThrough', {
                            "NID": row.nId,
                            "goodsId": row.goodsId,
                            "mountStorage": row.storage.mountStorage,
                            "purchaseDate": row.purchaseDate,
                            "SUPPLIER_ID": row.SUPPLIER_NAME,
                            "purchaseMount": row.purchaseMount,
                            "purchasePerson": row.purchasePerson,
                            "REPO_ID": row.REPO_ADDRESS,
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
                        $.post('${pageContext.request.contextPath}/purchase/updatePurchaseThrough', {
                            "mountStorage": row.storage.mountStorage,
                            "purchaseId": row.purchaseId,
                            "purchaseDate": row.purchaseDate,
                            "SUPPLIER_ID": row.SUPPLIER_NAME,
                            "purchaseMount": row.purchaseMount,
                            "purchasePerson": row.purchasePerson,
                            "REPO_ID": row.REPO_ADDRESS,
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
<div data-options="region:'east',title:'计划单信息',split:true,collapsible:true,collapsed:true"
     style="width: 400px;padding: 1px; background-color: #eee">
    <table id="pg"></table>
</div>

<div id="dlgPurchase" class="easyui-window" title="编辑送货" style="width: 400px;height: 280px;"
     data-options="closed:true"
     align="center">
    <form id="editPurchaseForm">
        <table>
            <tr>
                <td>收货日期
                </td>
                <td>
                    <input id="purchaseDate" name="purchaseDate" type="text" class="easyui-validatebox"
                           style="width:200px;"/>
                    <input id="NID_t" type="hidden" name="NID"/>
                    <input id="goodsId_t" type="hidden" name="goodsId"/>
                    <input id="storageId_t" type="hidden" name="storageId"/>
                    <input id="purchaseId_t" type="hidden" name="purchaseId"/>
                    <input type="hidden" id="flag" name="flag"/>
                    <input type="hidden" id="purchaseFlag" name="purchaseFlag"/>
                    <input type="hidden" id="mountStorage" name="mountStorage"/>
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
            <%--            <tr>--%>
            <%--                <td>送货单号--%>
            <%--                </td>--%>
            <%--                <td>--%>
            <%--                    <input id="purchasedId" name="purchasedId" type="text" class="easyui-textbox easyui-validatebox"--%>
            <%--                           style="width:200px;"/>--%>
            <%--                </td>--%>
            <%--            </tr>--%>
            <tr>
                <td>收货数量
                </td>
                <td>
                    <input id="purchaseMount" name="purchaseMount" class="easyui-numberspinner" value="0"
                           data-options="required:false,increment:2" style="width:80px;"/>
                </td>
            </tr>
            <tr>
                <td>采购人员
                </td>
                <td>
                    <input id="purchasePerson" name="purchasePerson" type="text"
                           class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
            <%--            <tr>--%>
            <%--                <td>收货人员--%>
            <%--                </td>--%>
            <%--                <td>--%>
            <%--                    <input id="purchaseGoodsPerson" name="purchaseGoodsPerson" type="text"--%>
            <%--                           class="easyui-textbox easyui-validatebox"--%>
            <%--                           style="width:200px;"/>--%>
            <%--                </td>--%>
            <%--            </tr>--%>
            <tr>
                <td>仓库
                </td>
                <td>
                    <input id="REPO_ADDRESS" name="REPO_ID" type="text" class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
            <%--            <tr>--%>
            <%--                <td>当前送货次数：--%>
            <%--                </td>--%>
            <%--                <td>--%>
            <%--                    <input id="purchaseNum" name="purchaseNum" class="easyui-numberspinner" value="1"--%>
            <%--                           readonly="readonly"--%>
            <%--                           data-options="required:false,increment:1" style="width:60px;"/>--%>
            <%--                </td>--%>
            <%--            </tr>--%>
        </table>
        <br/>
        <%--<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:checkPurchaseSubmit()">送货</a>--%>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:checkPurchase()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$('#dlgPurchase').dialog('close')">关闭</a>
    </form>
</div>

<div id="dlgSubmit" class="easyui-window" title="提交下一步" style="width: 450px;height: 240px;" data-options="closed:true"
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
                <td class="datagrid-toolbar"></td>
                <td class="datagrid-toolbar"></td>
            </tr>
            <tr>
                <td>指定部门：</td>
                <td colspan="4">
                    <input id="dept" class="easyui-combotree" style="width:200px;"
                           data-options="editable:false,panelHeight:'auto'">
                </td>
            </tr>
            <tr>
                <td>指定审批人员：</td>
                <td colspan="4">
                    <select id="QuestionExaminer" type="text" name="userId"
                            data-options="editable:false,panelHeight:'auto'"
                            style="width: 200px;">
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="hidden" name="taskId" value="${param.taskId}"/>
                    <input type="hidden" name="state" id="state" class="easyui-textbox"/>
                </td>
                <td colspan="4">
                    <a href="javascript:submit(1)" class="easyui-linkbutton" iconCls="icon-ok">批准</a>
                    &nbsp;&nbsp;
                    <a href="javascript:submit(2)" class="easyui-linkbutton" iconCls="icon-no">驳回</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<div data-options="region:'center',collapsible:true,split:true">
    <table id="dgStorage" title="处理"></table>
    <table id="dg"></table>
</div>
</body>
</html>
