<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>仓管人员再次审批</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plupload/queue/css/jquery.plupload.queue.css"
          type="text/css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<%--    <style type="text/css">--%>
<%--        .table th {--%>
<%--            font-weight: bold;--%>
<%--        }--%>

<%--        .table th,--%>
<%--        .table td {--%>
<%--            padding: 8px;--%>
<%--            line-height: 20px;--%>
<%--        }--%>

<%--        .table td {--%>
<%--            text-align: center;--%>
<%--        }--%>

<%--        .table-border {--%>
<%--            border-top: 1px solid #ddd;--%>
<%--        }--%>

<%--        .table-border th,--%>
<%--        .table-border td {--%>
<%--            border-bottom: 1px solid #ddd;--%>
<%--        }--%>

<%--        .table-bordered {--%>
<%--            border: 1px solid #ddd;--%>
<%--            border-collapse: separate;--%>
<%--            *border-collapse: collapse;--%>
<%--            border-left: 0;--%>
<%--        }--%>

<%--        .table-bordered th,--%>
<%--        .table-bordered td {--%>
<%--            border-left: 1px solid #ddd;--%>
<%--        }--%>

<%--        .table-border.table-bordered {--%>
<%--            border-bottom: 0;--%>
<%--        }--%>

<%--        .table-striped tbody > tr:nth-child(odd) > td,--%>
<%--        .table-striped tbody > tr:nth-child(odd) > th {--%>
<%--            background-color: #f9f9f9;--%>
<%--        }--%>
<%--    </style>--%>
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

    <link href="http://www.easyui-extlib.com/Content/icons/icon-standard.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/static/css/jeasyui-extensions-datagrid.css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-menu.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-datagrid-getColumnInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-datagrid-columnToggle.js"></script>

    <script src="http://www.easyui-extlib.com/Scripts/jquery-easyui-extensions/datagrid/jeasyui.extensions.datagrid.getDom.js"></script>
    <script src="http://www.easyui-extlib.com/Scripts/jquery-easyui-extensions/datagrid/jeasyui.extensions.datagrid.getRowData.js"></script>
    <script src="http://www.easyui-extlib.com/Scripts/jquery-easyui-extensions/datagrid/jeasyui.extensions.datagrid.tooltip.js"></script>

    <script type="text/javascript">
        var ddv;
        var mountStorage;
        var purchaseLeave;
        var str;
        var assign;
        var pageContext = "${pageContext.request.contextPath}";
        var group_session = '${sessionInfo.groupId }';
        assign = "factoryQuality";
        str = '需求采购';
        $(function () {

            $("#btnSubmit").click(function () {
                $("#dlgSubmit").dialog("open");
            });

            $('#purchaseDate').datetimebox({
                required: true,
                showSeconds: true,
                editable: false,
            });

            $('#puchaseNum').numberspinner({
                min: 1,
                max: 5,
                editable: false
            });

            $('#puchaseLeave').numberspinner({
                min: 1,
                editable: true
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

            <%--$('#dept').combotree(--%>
            <%--    {--%>
            <%--        // prompt: '输入关键字后自动搜索',--%>
            <%--        // value: deptValue_session,--%>
            <%--        missingMessage: '部门名称不能为空!',--%>
            <%--        // mode: 'remote',--%>
            <%--        url: '${pageContext.request.contextPath}/dept/selectChild?deptID=' + "800",--%>
            <%--        onSelect: function (record) {--%>
            <%--            var deptID = record.id;--%>
            <%--            $('#QuestionExaminer').combogrid({--%>
            <%--                method: 'POST',--%>
            <%--                idField: 'id',--%>
            <%--                textField: 'allName',--%>
            <%--                // formatter: function(row){--%>
            <%--                //     row.id = row.firstName;--%>
            <%--                //     return row.id;--%>
            <%--                // },--%>
            <%--                url: '${pageContext.request.contextPath}/user/userPage?deptID=' + deptID + '&groupID=' + assign,--%>
            <%--                columns: [[--%>
            <%--                    {field: 'cb', checkbox: true, align: 'center'},--%>
            <%--                    {field: 'id', title: '用户名/工号', width: 100, align: 'center'},--%>
            <%--                    {field: 'allName', title: '姓名', width: 100, align: 'center'},--%>
            <%--                    {field: 'phone', title: '手机号码', width: 100, align: 'center'},--%>
            <%--                    {field: 'email', title: '邮箱', width: 100, align: 'center'}--%>
            <%--                ]]--%>
            <%--            });--%>
            <%--        }--%>
            <%--    });--%>
        });

        function submit(state) {
            $("#state").textbox("setValue",state);
            $("#fm").form("submit", {
                url: '${pageContext.request.contextPath}/needTask/needFactoryPurchase',
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

        function openPurchaseDialogAdd(NID, goodsId) {
            $("#dlgPurchase").dialog("open").dialog("setTitle", "编辑（" + goodsId + "）收货日报表");
            $("#flag").val(1);
            $("#NID_t").val(NID);
            $("#goodsId_t").val(goodsId);
        }

        function openPurchaseDialogUpdate(NID, goodsId, purchaseId) {
            $("#dlgPurchase").dialog("open").dialog("setTitle", "编辑（" + goodsId + "）收货日报表");
            $("#flag").val(2);
            $("#NID_t").val(NID);
            $("#purchaseId_t").val(purchaseId);
        }

        function checkPurchase() {
            debugger
            var flag = $("#flag").val();
            $("#mountStorage").val(mountStorage);
            var countNow = $("#purchaseMount").val();
            if(countNow > purchaseLeave){
                $.messager.alert("系统提示", "送货数量超过申购数量！");
                return;
            }else {
                if (flag == 1) {
                    addPurchase()
                } else if (flag == 2) {
                    updatePurchase()
                }
            }
        }

        function addPurchase() {
            $("#editPurchaseForm").form("submit", {
                url: '${pageContext.request.contextPath}/purchase/addPurchase',
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统系统", "保存成功！");
                        $("#dlgPurchase").dialog("close");
                        ddv.datagrid("reload");
                        // $("#dgStorage").datagrid("reload");
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
                url: '${pageContext.request.contextPath}/purchase/updatePurchase',
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统系统", "保存成功！");
                        $("#dlgPurchase").dialog("close");
                        ddv.datagrid("reload");
                        // $("#dgStorage").datagrid("reload");
                        // resetValue();
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
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
                    { field: 'name', title: '属性', width: 80, resizable: true },
                    { field: 'value', title: '值', width: 200, resizable: true }
                ]]
            });

            var NID;
            $.post("${pageContext.request.contextPath}/need/getNeedByTaskId", {taskId: '${param.taskId}'}, function (result) {
                debugger
                need = result.need;
                var needDate = new Date(need.EApplicantData.time)
                ss.push({"name":"姓名","value":"<label style='color: orange'>" + need.user.allName + "</label>","group":"提交人"});
                ss.push({"name":"申请日期","value":needDate.format("Y-m-d H:i:s"),"group":"提交日期"});
                ss.push({"name":"采购单号","value":"<label style='color: red'>" + need.NID + "</label>","group":"需求单"});
                ss.push({"name":"提单公司","value":need.EBillCompany,"group":"需求单"});
                if (need.EType == '0') {
                    var strEType = "机械生产";
                } else if (need.EType == '4') {
                    var strEType = "电气生产";
                } else if (need.EType == '6') {
                    var strEType = "机械现场";
                } else if (need.EType == '7') {
                    var strEType = "电气现场";
                } else if (need.EType == '8') {
                    var strEType = "试制/研发";
                }
                ss.push({"name":"类型","value":strEType,"group":"需求单"});
                ss.push({"name":"区域","value":need.EZone,"group":"需求单"});
                ss.push({"name":"工厂耗材","value":need.EConsumer,"group":"需求单"});
                ss.push({"name":"工程名称","value":need.subProject.city.cityName,"group":"需求单"});
                ss.push({"name":"公园名称","value":need.subProject.park.parkName,"group":"需求单"});
                ss.push({"name":"项目名称","value":need.subProject.subProjectName,"group":"需求单"});
                ss.push({"name":"系统名称","value":need.ESysName,"group":"需求单"});
                ss.push({"name":"子项目名称","value":need.ESubProjectNameElse,"group":"需求单"});
                ss.push({"name":"公司主体","value":need.company.COMPANY_NAME,"group":"需求单"});
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

            $('#dgStorage').datagrid({
                url: '${pageContext.request.contextPath}/needGoods/needGoodsPage',
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'goodsId', title: '存货编码', width: 20, align: 'center', hidden:true},
                    {
                        field: 'goodsName', title: '名称', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return row.goods.goodsName;
                        }
                    },
                    {
                        field: 'goodsCode', title: '图号', width: 20, align: 'center',tooltip: true,
                        formatter: function (value, row, index) {
                            return row.goods.goodsCode;
                        }
                    },
                    {
                        field: 'goodsType', title: '类型', width: 20, align: 'center', hidden: true,tooltip: true,
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
                    //         if(row.storage==null){
                    //             return "无数据";
                    //         }else {
                    //             return row.storage.mount;
                    //         }
                    //     }
                    // },
                    // {
                    //     field: 'mountBack', title: '备用', width: 20, align: 'center',
                    //     formatter: function (value, row, index) {
                    //         if(row.storage==null){
                    //             return "无数据";
                    //         }else {
                    //             return row.storage.mountBack;
                    //         }
                    //     }
                    // },
                    // {
                    //     field: 'mountIn', title: '库存', width: 20, align: 'center',
                    //     formatter: function (value, row, index) {
                    //         if(row.storage==null){
                    //             return "无数据";
                    //         }else {
                    //             return row.storage.mountIn;
                    //         }
                    //     }
                    // },
                    {
                        field: 'mountStorage', title: '申购', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            if(row.storage==null){
                                return "无数据";
                            }else {
                                return "<p style='color:orange'>" + row.storage.mountStorage + "</p>";
                            }
                        }
                    },
                    {
                        field: 'checkoutDate', title: '质检日期', width: 20, align: 'center',tooltip: true,
                        formatter: function (value, row, index) {
                            if(row.checkout==null){
                                return "无内容";
                            }else {
                                return row.checkout.checkoutDate;
                            }
                        }
                    },
                    {
                        field: 'checkoutMount', title: '合格数量', width: 20, align: 'center',tooltip: true,
                        formatter: function (value, row, index) {
                            if(row.checkout==null){
                                return "无内容";
                            }else {
                                return "<p style='color:lightgreen'>" + row.checkout.checkoutMount + "</p>";
                            }
                        }
                    },
                    {
                        field: 'checkoutNotQuaMount', title: '不合格数量', width: 20, align: 'center',tooltip: true,
                        formatter: function (value, row, index) {
                            if(row.checkout==null){
                                return "无内容";
                            }else {
                                return "<p style='color:firebrick'>" + row.checkout.checkoutNotQuaMount + "</p>";
                            }
                        }
                    },
                    // {
                    //     field: 'purchaseLeave', title: '剩余数量', width: 20, align: 'center',tooltip: true,
                    //     formatter: function (value, row, index) {
                    //         if(row.purchase==null){
                    //             return "无内容";
                    //         }else {
                    //             return row.purchase.purchaseLeave;
                    //         }
                    //     }
                    // },
                    // {
                    //     field: 'ETemp', title: '变更', width: 20, align: 'center',tooltip: true,
                    //     formatter: function (value, row, index) {
                    //         if(row.storage==null){
                    //             return "无说明";
                    //         }else {
                    //             return row.storage.ETemp;
                    //         }
                    //     }
                    // },
                    // {
                    //     field: 'EPlan', title: '计划备注', width: 20, align: 'center',tooltip: true,
                    //     formatter: function (value, row, index) {
                    //         if(row.storage==null){
                    //             return "无说明";
                    //         }else {
                    //             return row.storage.EPlan;
                    //         }
                    //     }
                    // },
                    // {
                    //     field: 'EIsRun', title: '发货标识', width: 20, align: 'center',
                    //     formatter: function (value, row, index) {
                    //         if(row.storage==null){
                    //             return "无说明";
                    //         }else {
                    //             return row.storage.EIsRun;
                    //         }
                    //     }
                    // },
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
                singleSelect : true,// 如果为true，则只允许选择一行。
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
                                {field: 'purchaseId', title: '流水号', width: 20, align: 'center', hidden: true},
                                {
                                    field: 'purchaseDate', title: '收货日期', width: 20, align: 'center', tooltip: true,
                                    formatter: function (value, row, index) {
                                        if (row.purchase == null) {
                                            return "无内容";
                                        } else {
                                            return row.purchase.purchaseDate;
                                        }
                                    }
                                },
                                {
                                    field: 'SUPPLIER_NAME', title: '供应商', width: 20, align: 'center', tooltip: true,
                                    formatter: function (value, row, index) {
                                        if (row.purchase == null) {
                                            return "无内容";
                                        } else {
                                            return row.purchase.supplier.SUPPLIER_NAME;
                                        }
                                    }
                                },
                                {
                                    field: 'purchasedId', title: '送货单号', width: 20, align: 'center', hidden: true,
                                    formatter: function (value, row, index) {
                                        if (row.purchase == null) {
                                            return "无内容";
                                        } else {
                                            return row.purchase.purchasedId;
                                        }
                                    }
                                },
                                {
                                    field: 'purchaseMount', title: '收货数量', width: 20, align: 'center',
                                    formatter: function (value, row, index) {
                                        if (row.purchase == null) {
                                            return "无内容";
                                        } else {
                                            return "<p style='color:black'>" + row.purchase.purchaseMount + "</p>";
                                        }
                                    }
                                },
                                {
                                    field: 'purchaseLeave', title: '剩余数量', width: 20, align: 'center',
                                    formatter: function (value, row, index) {
                                        if(row.purchase==null){
                                            return "无内容";
                                        }else {
                                            return "<p style='color:#1f637b'>" + row.purchase.purchaseLeave + "</p>";
                                        }
                                    }
                                },
                                {
                                    field: 'purchasePerson', title: '采购人员', width: 20, align: 'center',
                                    formatter: function (value, row, index) {
                                        if (row.purchase == null) {
                                            return "无内容";
                                        } else {
                                            return row.purchase.purchasePerson;
                                        }
                                    }
                                },
                                {
                                    field: 'purchaseGoodsPerson', title: '收货人员', width: 20, align: 'center',
                                    formatter: function (value, row, index) {
                                        if (row.purchase == null) {
                                            return "无内容";
                                        } else {
                                            return row.purchase.purchaseGoodsPerson;
                                        }
                                    }
                                },
                                {
                                    field: 'REPO_ADDRESS', title: '仓库', width: 20, align: 'center', tooltip: true,
                                    formatter: function (value, row, index) {
                                        if (row.purchase == null) {
                                            return "无内容";
                                        } else {
                                            return row.purchase.respository.REPO_ADDRESS;
                                        }
                                    }
                                }
                                // {
                                //     field: 'action', title: '操作', width: 20, align: 'center',
                                //     formatter: function (value, row, index) {
                                //         return "<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" onclick=\"$('#dlgStorage').dialog('open')\">编辑</a>";
                                //         // + "<br /><a id=\"btnSave\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-save',plain:true\">保存</a>";
                                //     }
                                // }
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
                            toolbar: "#editPurchase",
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

                        $("#btnDlgPurchase").click(function () {
                            var selectRows = ddv.datagrid("getSelections");
                            if (selectRows.length != 1) {
                                $.messager.alert("系统提示", "请选择一条要编辑的数据！");
                                return;
                            }
                            var row = selectRows[0];
                            if(row.purchase!=null){
                                if(row.storage != null){
                                    mountStorage = row.storage.mountStorage;
                                }
                                purchaseLeave = row.purchase.purchaseLeave;
                                $("#purchaseDate").datetimebox("setValue", row.purchase.purchaseDate);
                                $("#SUPPLIER_NAME").combobox("setValue", row.purchase.supplier.SUPPLIER_ID);
                                $("#purchasedId").textbox("setValue", row.purchase.purchasedId);
                                $("#purchaseMount").textbox("setValue", row.purchase.purchaseLeave);
                                $("#purchasePerson").textbox("setValue", row.purchase.purchasePerson);
                                $("#purchaseGoodsPerson").textbox("setValue", row.purchase.purchaseGoodsPerson);
                                $("#REPO_ADDRESS").combobox("setValue", row.purchase.respository.REPO_ID);
                                $("#purchaseNum").textbox("setValue", row.purchase.purchaseNum);
                                openPurchaseDialogUpdate(row.nId, row.goods.goodsId, row.purchase.purchaseId);
                                // $("#editPurchaseForm").form("load", row);
                            }else {
                                if(row.storage != null){
                                    mountStorage = row.storage.mountStorage;
                                }
                                $("#purchaseDate").datetimebox("setValue", "");
                                $("#SUPPLIER_NAME").combobox("setValue", "");
                                $("#purchasedId").textbox("setValue", "");
                                $("#purchaseMount").textbox("setValue", "0");
                                $("#purchasePerson").textbox("setValue", "");
                                $("#purchaseGoodsPerson").textbox("setValue", "");
                                $("#REPO_ADDRESS").combobox("setValue", "");
                                $("#purchaseNum").textbox("setValue", "1");
                                openPurchaseDialogAdd(row.nId, row.goods.goodsId);
                                // $("#editPurchaseForm").form("load", row);
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
                    };
                    buttonBindEvent();
                },
            });
        });
    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/autoPinyin.js"></script>
</head>
<body class="easyui-layout" style="margin: 5px">
<%--<div data-options="region:'center',title:'仓管人员再次编辑'"--%>
<%--     style="width: 200px;padding: 4px; background-color: #eee">--%>
<%--    <div id="p">--%>
<%--        <form id="fm" method="post">--%>
<%--            <table class="table table-border table-bordered table-striped" style="width: 100%">--%>
<%--                <tr>--%>
<%--                    <td>申请人：</td>--%>
<%--                    <td>--%>
<%--                        <span id="allName"></span>--%>
<%--                    </td>--%>
<%--                    <td>申请时间：</td>--%>
<%--                    <td>--%>
<%--                        <span id="EApplicantData"></span>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td>采购单号：</td>--%>
<%--                    <td>--%>
<%--                        <span id="NID"></span>--%>
<%--                    </td>--%>
<%--                    <td>加工类型：</td>--%>
<%--                    <td>--%>
<%--                        <span id="EPType"></span>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td>提单公司：</td>--%>
<%--                    <td>--%>
<%--                        <span id="EBillCompany"></span>--%>
<%--                    </td>--%>
<%--                    <td>类型/专业：</td>--%>
<%--                    <td>--%>
<%--                        <span id="EType"></span>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td>区域：</td>--%>
<%--                    <td>--%>
<%--                        <span id="EZone"></span>--%>
<%--                    </td>--%>
<%--                    <td>工厂耗材：</td>--%>
<%--                    <td>--%>
<%--                        <span id="EConsumer"></span>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td>工程名称：</td>--%>
<%--                    <td>--%>
<%--                        <span id="city"></span>--%>
<%--                    </td>--%>
<%--                    <td>公园名称：</td>--%>
<%--                    <td>--%>
<%--                        <span id="park"></span>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td>项目名称：</td>--%>
<%--                    <td>--%>
<%--                        <span id="subProjectName"></span>--%>
<%--                    </td>--%>
<%--                    <td>系统名称：</td>--%>
<%--                    <td>--%>
<%--                        <span id="ESysName"></span>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td>子项目名称：</td>--%>
<%--                    <td>--%>
<%--                        <span id="ESubProjectNameElse"></span>--%>
<%--                    </td>--%>
<%--                    <td>公司主体：</td>--%>
<%--                    <td>--%>
<%--                        <span id="ECompanyId"></span>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td>批注：</td>--%>
<%--                    <td colspan="4">--%>
<%--                        <textarea id="comment" name="comment" rows="2" cols="49" class="easyui-validatebox"--%>
<%--                                  required="true"></textarea>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--&lt;%&ndash;                <tr>&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <td valign="top">指定部门：</td>&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <td colspan="4">&ndash;%&gt;--%>
<%--&lt;%&ndash;                        <input id="dept" class="easyui-combotree" style="width:200px;">&ndash;%&gt;--%>
<%--&lt;%&ndash;                    </td>&ndash;%&gt;--%>
<%--&lt;%&ndash;                </tr>&ndash;%&gt;--%>
<%--&lt;%&ndash;                <tr>&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <td valign="top">指定审批人员：</td>&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <td colspan="4">&ndash;%&gt;--%>
<%--&lt;%&ndash;                        <select id="QuestionExaminer" type="text" name="userId"&ndash;%&gt;--%>
<%--&lt;%&ndash;                                style="width: 200px;">&ndash;%&gt;--%>
<%--&lt;%&ndash;                        </select>&ndash;%&gt;--%>
<%--&lt;%&ndash;                    </td>&ndash;%&gt;--%>
<%--&lt;%&ndash;                </tr>&ndash;%&gt;--%>
<%--                <tr>--%>
<%--                    <td>--%>
<%--                        <input type="hidden" name="taskId" value="${param.taskId}"/>--%>
<%--                    </td>--%>
<%--                    <td colspan="4">--%>
<%--                        <a href="javascript:submit(1)" class="easyui-linkbutton" iconCls="icon-ok">批准</a>--%>
<%--                        &nbsp;&nbsp;--%>
<%--                        <a href="javascript:submit(2)" class="easyui-linkbutton" iconCls="icon-no">驳回</a>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--            </table>--%>
<%--        </form>--%>
<%--    </div>--%>
<%--</div>--%>

<div data-options="region:'east',title:'采购单信息',split:true"
     style="width: 400px;padding: 1px; background-color: #eee">
    <table id="pg"></table>
</div>

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

<div id="uploaderOne" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">
    <table id="findFiles"
           fitColumns="true" fit="true" toolbar="#tb">
    </table>
</div>

<div id="dlgPurchase" class="easyui-window" title="再次编辑收货日报表" style="width: 400px;height: 380px;" data-options="closed:true"
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
            <tr>
                <td>送货单号
                </td>
                <td>
                    <input id="purchasedId" name="purchasedId" type="text" class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
            <tr>
                <td>收货数量
                </td>
                <td>
                    <input id="purchaseMount" name="purchaseMount"  class="easyui-numberspinner" value="0"
                           data-options="required:false,increment:2" style="width:80px;"/>
                </td>
            </tr>
            <tr>
                <td>采购人员
                </td>
                <td>
                    <input id="purchasePerson" name="purchasePerson" type="text" class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
            <tr>
                <td>收货人员
                </td>
                <td>
                    <input id="purchaseGoodsPerson" name="purchaseGoodsPerson" type="text" class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
            <tr>
                <td>仓库
                </td>
                <td>
                    <input id="REPO_ADDRESS" name="REPO_ID" type="text" class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
            <tr>
                <td>当前送货次数：
                </td>
                <td>
                    <input id="purchaseNum" name="purchaseNum" class="easyui-numberspinner" value="1" readonly="readonly"
                           data-options="required:false,increment:1" style="width:60px;"/>
                </td>
            </tr>
        </table>
        <br/>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:checkPurchase()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$('#dlgPurchase').dialog('close')">关闭</a>
    </form>
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

<div data-options="region:'center',title:'再次处理/批注列表',split:true">
    <div style="padding-top: 5px">
        <div id="editStorage">
            <a id="btnSubmit" class="easyui-linkbutton" iconCls="icon-save"
               plain="true">提交</a>
        </div>
        <table id="dgStorage">
        </table>
        <div id="editPurchase">
            <a id="btnDlgPurchase" class="easyui-linkbutton" iconCls="icon-edit"
               plain="true">再次送货/最新送货</a>
            <a id="btnDlgPurchaseRecordList" class="easyui-linkbutton" iconCls="icon-save"
               plain="true">历史送货记录</a>
        </div>
        <table id="dg" class="easyui-datagrid"
               fitColumns="true"
               url="${pageContext.request.contextPath}/task/listHistoryComment?taskId=${param.taskId}"
               style="width:100%;height: 200px;">
            <thead>
            <tr>
                <th field="time" width="40" align="center">批注时间</th>
                <th field="userId" width="20" align="center">批注人</th>
                <th field="message" width="40" align="center">批注信息</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<div id="uploader" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">&nbsp;</div>
</body>
</html>
