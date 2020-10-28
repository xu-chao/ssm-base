<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="keywords" content="jquery,ui,easy,easyui,web">
    <meta name="description" content="easyui help you build your web page easily!">
    <title>工厂erp</title>
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
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/datagrid-detailview.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
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

    <link href="${pageContext.request.contextPath}/static/css/jeasyui.extensions.validatebox.css"
          rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.validatebox.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.rowState.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.editors.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.edit.cellEdit.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/uploadExcel.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/allExcel.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.editors.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.edit.enterFocus.js"></script>

    <%--翻页导航--%>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.navigating.js"></script>

    <%--右键菜单--%>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.menu.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.rowContext.js"></script>

    <script>
        var pageContext = "${pageContext.request.contextPath}";
        var ddv;
        var flagSubmit = true;
        $(document).ready(function () {

            // $('#endDate_a').tooltip({
            //     position: "right"
            // });

            $('#endDate').datetimebox({
                required: true,
                showSeconds: true,
                editable: false,
            });

            $('#COMPANY_NAME').combobox(
                {
                    prompt: '输入关键字后自动搜索',
                    // mode: 'remote',
                    url: '${pageContext.request.contextPath}/company/searchAllCompany',
                    valueField: 'company_ID',
                    textField: 'company_NAME',
                    panelHeight: 'auto',
                    panelMaxHeight: 150,
                    editable: true
                });

            $('#dept_2').combotree(
                {
                    // prompt: '输入关键字后自动搜索',
                    // value: deptValue_session,
                    required: true,
                    missingMessage: '部门名称不能为空!',
                    url: '${pageContext.request.contextPath}/dept/selectChild?deptID=' + "800",
                    onSelect: function (record) {
                        var deptID = record.id;
                        $('#QuestionAdviser_2').combogrid({
                            method: 'POST',
                            idField: 'id',
                            textField: 'allName',
                            url: '${pageContext.request.contextPath}/user/userPage?deptID=' + deptID + '&groupID=factoryPurchase',
                            columns: [[
                                {field: 'cb', checkbox: true, align: 'center'},
                                {field: 'id', title: '用户名/工号', width: 100, align: 'center'},
                                {field: 'allName', title: '姓名', width: 100, align: 'center'},
                                {field: 'phone', title: '手机号码', width: 100, align: 'center'},
                                {field: 'email', title: '邮箱', width: 100, align: 'center'}
                            ]],
                        });
                    }
                });


            $('#dg').datagrid({
                view: detailview,
                url: '${pageContext.request.contextPath}/need/needPage',
                title: "计划需求单管理",
                fit: true,
                fitColumns: true,
                rownumbers: true,
                pagination: true,
                singleSelect: true,
                toolbar: "#tb",
                pageList: [5, 10, 20, 50],//可以设置每页记录条数的列表
                iconCls: 'icon-tip',
                striped: true,// 是否显示斑马线效果。
                collapsible: true,	//定义是否显示可折叠按钮。
                nowrap: true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
                // sortName: 'goodsId',//定义哪些列可以进行排序。
                // sortOrder: 'desc',//定义列的排序顺序，只能是'asc'或'desc'。
                remoteSort: false,//定义从服务器对数据进行排序。
                loading: true,//显示载入状态。
                loadMsg: '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
                pageNumber: 1,// 在设置分页属性的时候初始化页码。
                pageSize: 50,// 在设置分页属性的时候初始化页面大小。
                border: false,
                autoHighlightColumn: true,
                enableHeaderContextMenu: true,
                enableHeaderClickMenu: true,
                rowTooltip: false, //是否启用行数据的 tooltip 功能，若该属性为true，则设置在columns中的tooltip会自动失效
                rowStyler: function (index, row) {
                    if (index % 2 == 1) {
                        return 'background-color:#D3D3D3;color:#000;';
                    }
                },
                detailFormatter: function (index, row) {
                    return '<div style="width:100%;padding:5px 0""><table id="ddv-' + index + '" ></table></div>';
                },
                onExpandRow: function (index, row) {
                    debugger
                    ddv = $(this).datagrid('getRowDetail', index).find('#ddv-' + index);
                    var id = row.id;
                    ddv.datagrid(
                        {
                            url: '${pageContext.request.contextPath}/need/getNeedGoodsById?id=' + id,
                            fitColumns: true,
                            frozenColumns: [[
                                {field: 'id', checkbox: true}
                            ]],
                            columns: [[
                                {
                                    field: 'goodsId', title: '存货编码', width: 20, align: 'center', tooltip: true,
                                    formatter: function (value, row, index) {
                                        if (value.substr(3, 3) == 'NEW') {
                                            flagSubmit == false;
                                            return "<a id=\"updateGoodsId\" " +
                                                "href=\"javascript:updateGoodsId('" + row.nId + "','" + row.goods.goodsId + "','" + row.goods.goodsType + "','" + row.goods.goodsName + "','" + row.goods.goodsCode + "')\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-add'\">选择</a>";
                                        } else {
                                            return value;
                                        }
                                    }
                                },
                                {
                                    field: 'goodsName', title: '名称', width: 20, align: 'center', tooltip: true,
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
                                    field: 'goodsType', title: '类型', width: 20, align: 'center', hidden: true,
                                    formatter: function (value, row, index) {
                                        return row.goods.goodsType;
                                    }
                                },
                                {
                                    field: 'goodsUnit', title: '单位', width: 20, align: 'center',
                                    formatter: function (value, row, index) {
                                        return row.goods.goodsUnit;
                                    }
                                },
                                {
                                    field: 'mount', title: '需求', width: 20, align: 'center',
                                    editor: {
                                        type: "numberbox",
                                        options: {
                                            required: true
                                        }
                                    },
                                    formatter: function (value, row, index) {
                                        if (row.storage == null) {
                                            return "";
                                        } else {
                                            return row.storage.mount;
                                        }
                                    }
                                },
                                {
                                    field: 'mountIn', title: '库存', width: 20, align: 'center',
                                    editor: {
                                        type: "numberbox",
                                        options: {
                                            required: true
                                        }
                                    },
                                    formatter: function (value, row, index) {
                                        if (row.storage == null) {
                                            return "";
                                        } else {
                                            return row.storage.mountIn;
                                        }
                                    }
                                },
                                {
                                    field: 'mountBack', title: '备用', width: 20, align: 'center',
                                    editor: {
                                        type: "numberbox",
                                        options: {
                                            required: true
                                        }
                                    },
                                    formatter: function (value, row, index) {
                                        if (row.storage == null) {
                                            return "";
                                        } else {
                                            return row.storage.mountBack;
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
                                    field: 'ETemp', title: '变更', width: 20, align: 'center',
                                    formatter: function (value, row, index) {
                                        if (row.storage == null) {
                                            return "";
                                        } else {
                                            return row.storage.ETemp;
                                        }
                                    }
                                },
                                {
                                    field: 'EPlan', title: '计划备注', width: 20, align: 'center',
                                    formatter: function (value, row, index) {
                                        if (row.storage == null) {
                                            return "";
                                        } else {
                                            return row.storage.EPlan;
                                        }
                                    }
                                },
                                {
                                    field: 'EIsRun', title: '发货标识', width: 20, align: 'center',
                                    formatter: function (value, row, index) {
                                        if (row.storage == null) {
                                            return "";
                                        } else {
                                            return row.storage.EIsRun;
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
                            fit: false,
                            rownumbers: true,
                            pagination: true,
                            singleSelect: true,
                            pageList: [5, 10, 20, 50],//可以设置每页记录条数的列表
                            //iconCls: 'icon-tip',
                            striped: true,// 是否显示斑马线效果。
                            collapsible: true,	//定义是否显示可折叠按钮。
                            nowrap: true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
                            toolbar: "#editStorage",
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
                                $('#dg').datagrid('fixDetailRowHeight', index);
                            },
                            onLoadSuccess: function () {
                                $('#dg').datagrid('fixDetailRowHeight', index);
                            },
                            // onDblClickRow: function (index, row) {
                            //     ddv.datagrid("beginEdit", index);
                            // },
                            //表示开启单元格编辑功能
                            enableCellEdit: false,
                            //表示开启编辑后是否启用回车后自动聚焦下一个列编辑器的功能
                            //enterFocusNextEditor: true,
                            //表示当编辑器内容为空，是否停止自动聚焦功能
                            //stopEnterFocusWhenEmpty: false,
                            //表示聚焦到最后一个单元格编辑器之后触发的事件
                            // onAfterFoucsLastEditor: function (index, field) {
                            //     $("#btnInsert").click();
                            // }
                        });
                    $('#dg').datagrid('fixDetailRowHeight', index);

                    var buttonBindEvent = function () {
                        $("#btnSave").click(function () {
                            debugger
                            var rows = ddv.datagrid("getRows"),
                                len = rows.length;
                            if (len == 0) {
                                return;
                            }
                            for (var i = 0; i < len; i++) {
                                if (ddv.datagrid("isEditing", i)) {
                                    ddv.datagrid("endEdit", i);
                                }
                            }
                            // 获取选中的行
                            var rowsSave = ddv.datagrid('getChecked');
                            var NID = rowsSave[0].nId;
                            var goodsId = rowsSave[0].goodsId;
                            var storageId = rowsSave[0].storageId;
                            var mount = rowsSave[0].mount;
                            var mountBack = rowsSave[0].mountBack;
                            var mountIn = rowsSave[0].mountIn;
                            // var data = {
                            //     // mountRows:rowsSave[0].mount,
                            //     // mountBack:rowsSave[0].mountBack,
                            //     // mountIn:rowsSave[0].mountIn
                            // };
                            $.ajax({
                                type: "POST",
                                url: "${pageContext.request.contextPath}/storage/updateStorageRows?mount=" + mount +
                                    "&mountBack=" + mountBack + "&mountIn=" + mountIn + "&storageId=" + storageId +
                                    "&NID=" + NID + "&goodsId=" + goodsId,
                                dataType: "json",
                                contentType: "application/json",
                                // data:JSON.stringify(data),
                                // data:{mount:mount},
                                success: function (result) {
                                    if (result.success == 'success') {
                                        $.messager.alert("系统提示", "保存成功！");
                                        ddv.datagrid("reload");
                                    } else if (result.success == 'error') {
                                        $.messager.alert("输入参数不正确！");
                                        return;
                                    } else {
                                        $.messager.alert("系统提示", "保存失败！");
                                        return;
                                    }
                                }
                            });
                        });

                        $("#btnDlgStorage").click(function () {
                            debugger
                            var selectRows = ddv.datagrid("getSelections");
                            if (selectRows.length != 1) {
                                $.messager.alert("系统提示", "请选择一条要编辑的数据！");
                                return;
                            }
                            var row = selectRows[0];
                            if (row.storage != null) {
                                $("#mount").textbox("setValue", row.storage.mount);
                                $("#mountBack").textbox("setValue", row.storage.mountBack);
                                $("#mountIn").textbox("setValue", row.storage.mountIn);
                                $("#ETemp").textbox("setValue", row.storage.ETemp);
                                $("#EPlan").textbox("setValue", row.storage.EPlan);
                                $("#EIsRun").textbox("setValue", row.storage.EIsRun);
                                openStorageDialogUpdate(row.nId, row.goods.goodsId, row.storage.storageId);
                                // $("#editStorageForm").form("load", row);
                            } else {
                                $("#mount").textbox("setValue", "");
                                $("#mountBack").textbox("setValue", "");
                                $("#mountIn").textbox("setValue", "");
                                $("#ETemp").textbox("setValue", "");
                                $("#EPlan").textbox("setValue", "");
                                $("#EIsRun").textbox("setValue", "");
                                openStorageDialogAdd(row.nId, row.goods.goodsId);
                                // $("#editStorageForm").form("load", row);
                            }
                        });
                    };
                    buttonBindEvent();
                },
                columns: [[
                    {title: '计划需求信息', colspan: 14},
                    {title: '状态信息', colspan: 6}
                ], [
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'EID', title: '序号', width: 20, align: 'center', hidden: true},
                    {field: 'NID', title: '采购单号', width: 50, align: 'center'},
                    {field: 'EApplicantData', title: '日期', width: 50, align: 'center', tooltip: true,},
                    {field: 'EPType', title: '加工类型', width: 50, align: 'center'},
                    {field: 'EBillCompany', title: '提单公司', width: 50, align: 'center', hidden: true},
                    {
                        field: 'EType', title: '专业/类型', width: 50, align: 'center',
                        formatter: function (value, row, index) {
                            if (value == 0 || value == "机械生产") {
                                return "机械生产";
                            } else if (value == 4 || value == "电气生产") {
                                return "电气生产";
                            } else if (value == 6 || value == "机械现场") {
                                return "机械现场";
                            } else if (value == 7 || value == "电气现场") {
                                return "电气现场";
                            } else if (value == 8 || value == "试制研发") {
                                return "试制研发";
                            }
                        }
                    },
                    {field: 'EZone', title: '区域', width: 50, align: 'center', hidden: true,},
                    {field: 'EConsumer', title: '工厂耗材', width: 50, align: 'center', hidden: true,},
                    {
                        field: 'cityName', title: '工程名称', width: 50, align: 'center', hidden: true,
                        formatter: function (value, row, index) {
                            return row.subProject.city.cityName;
                        }
                    },
                    {
                        field: 'parkName', title: '公园名称', width: 50, align: 'center', tooltip: true,
                        formatter: function (value, row, index) {
                            return row.subProject.park.parkName;
                        }
                    },
                    {
                        field: 'subProjectName', title: '项目名称', width: 50, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            return row.subProject.subProjectName;
                        }
                    },
                    {field: 'ESubProjectNameElse', title: '子项目名称', width: 30, align: 'center', tooltip: true, hidden: true},
                    {field: 'ESysName', title: '系统名称', width: 30, align: 'center', hidden: true},
                    {
                        field: 'EApplicant', title: '申请人', width: 30, align: 'center',
                        formatter: function (value, row, index) {
                            return row.user.allName;
                        }
                    },
                    {
                        field: 'COMPANY_NAME', title: '公司主体', width: 50, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            if (row.company == null) {
                                return "";
                            } else {
                                return row.company.COMPANY_NAME;
                            }
                        }
                    },
                    {field: 'endDate', title: '结束日期', width: 50, align: 'center', hidden: true},
                    {field: 'processInstanceId', title: '流程实例Id', width: 100, align: 'center', hidden: true},
                    {field: 'state', title: '提交状态', width: 50, align: 'center'},
                    {
                        field: 'action', title: '操作', width: 50, align: 'center',
                        formatter:
                            function (value, row, index) {
                                if (row.state == '未提交') {
                                    return "<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" onclick=\"$('#dlg3').dialog('open').dialog('center');\">提交申请</a>" +
                                        "<br /><a href=\"javascript:saveErpDoc('" + row.id + "')\" class=\"easyui-linkbutton\">保存Excel</a>";
                                } else if (row.state == '不能提交') {
                                    return "<a href=\"javascript:saveErpDoc('" + row.id + "')\" class=\"easyui-linkbutton\">保存Excel</a>";
                                } else if (row.state == '审核通过' || row.state == '审核未通过') {
                                    return "<a href='javascript:openListCommentDialog(" + row.processInstanceId + ")'>查看历史批注</a>" +
                                        "<br /><a href=\"javascript:saveErpDoc('" + row.id + "')\" class=\"easyui-linkbutton\">保存Excel</a>";
                                } else {
                                    return "<a href=\"javascript:saveErpDoc('" + row.id + "')\" class=\"easyui-linkbutton\">保存Excel</a>";
                                }
                            }
                    }
                ]],
                onDblClickRow: function (value, row, index) {
                    openTable(row.NID,row.taskID);
                },
                navigatingWithKey: true,
                navigateHandler: {
                    up: function (targetIndex) {
                        console.log("上之后被selected的数据索引" + targetIndex);
                    },
                    down: function (targetIndex) {
                        console.log("下之后被selected的数据索引" + targetIndex);
                    },
                    enter: function (selectedData) {
                        openTable(selectedData[0].NID,selectedData[0].taskID);
                    }
                },
                enableRowContextMenu: true,
                selectOnRowContextMenu: true,
                refreshMenu: false,
                pagingMenu: false,
                rowContextMenu: [
                    {
                        text: "编辑", iconCls: "icon-ok", handler: function (e, item, menu, grid, rowIndex, row) {
                            openTable(row.NID,row.taskID);
                        }
                    }
                ]
            });

            $('#QuestionAdviser_2').combogrid({
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
        });

        function openTable(id,taskID){
            debugger
            window.parent.openTab("编辑","erp/allManage.jsp?id="+ id +"&taskId=" + taskID,'icon-check');
        }

        function saveErpDoc(id) {
            $.ajax({
                url: "${pageContext.request.contextPath}/word/getErpWord",
                type: 'post',
                data: {"id": id},//返回给客户端的json数据
                async: false,
                success: function (data, filename) {
                    var blob = new Blob([data], {type: 'application/vnd.ms-excel'});
                    var link = document.createElement("a");
                    var body = document.querySelector("body");
                    link.href = window.URL.createObjectURL(blob);
                    link.download = "03-（智能）试制物料需求表--外购";//文件名
                    link.style.display = "none";
                    body.appendChild(link);
                    link.click();
                    body.removeChild(link);
                    window.URL.revokeObjectURL(link.href);
                }
            });
        }

        function saveErp() {
            $("#fm").form("submit", {
                url: '${pageContext.request.contextPath}/need/addNeed',
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    debugger
                    if (result.success == 'success') {
                        $.messager.alert("系统系统", "保存成功！");
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");
                        resetValue();
                    } else if (result.success == 'error') {
                        $.messager.alert("系统系统", "本月单号已存在！");
                        return;
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
        }

        function closeErpDialog() {
            resetValue();
            $("#dlg").dialog("close");
        }

        function resetValue() {
            $("#ECodeID").textbox("setValue", "");
            $("#EBillCompany").textbox("setValue", "");
            $("#EApplicant").textbox("setValue", "");
            $("#ESubProjectNameElse").textbox("setValue", "");
            $("#ECompany").textbox("setValue", "");
            $('#endDate').combo('setText', '');
        }

        function closeStorageDialog() {
            resetStorageValue();
            $("#dlgStorage").dialog("close");
        }

        function resetStorageValue() {
            $("#mount").textbox("setValue", "");
            $("#mountBack").textbox("setValue", "");
            $("#mountIn").textbox("setValue", "");
            $("#ETemp").textbox("setValue", "");
            $("#EPlan").textbox("setValue", "");
            $('#EIsRun').textbox('setValue', "");
        }

        function openListCommentDialog(processInstanceId) {
            $("#dg2").datagrid("load", {
                processInstanceId: processInstanceId
            });
            $("#dlg2").dialog("open").dialog("setTitle", "查看历史批注");
        }

        //提交流程申请
        function startApply(id) {
            var selectRows = $("#dg").datagrid("getSelections");
            var taskID = selectRows[0].id;
            var userID = $('#QuestionAdviser_2').combobox('getValue');
            if (userID == "") {
                $.messager.alert("系统提示", "请填写审批人！");
                return;
            }
            var userTask = {
                taskID: taskID,
                userID: userID
            }
            $.ajax({
                url: "${pageContext.request.contextPath}/need/startApply",
                data: userTask,
                dataType: 'json',
                type: 'post',
                success: function (rtn) {
                    if (rtn.success) {
                        $.messager.alert("系统提示", "申请提交成功，目前审核中，请耐心等待！");
                        $("#dg").datagrid("reload");
                        closeQuestionDialog();
                    } else {
                        $.messager.alert("系统提示", "申请提交失败，请联系管理员！");
                    }
                }
            });
        }

        function opensqd() {
            $("#dlg").dialog("open").dialog("setTitle", "添加计划需求信息");
        }

        function openExcel() {
            $("#allExcel").dialog("open").dialog("setTitle", "Excel批量导入");
        }

        function openStorageDialogAdd(NID, goodsId) {
            $("#dlgStorage").dialog("open").dialog("setTitle", "编辑（" + goodsId + "）库存信息").dialog('center');;
            $("#flag").val(1);
            $("#NID_t").val(NID);
            $("#goodsId_t").val(goodsId);
        }

        function openStorageDialogUpdate(NID, goodsId, storageId) {
            $("#dlgStorage").dialog("open").dialog("setTitle", "编辑（" + goodsId + "）库存信息").dialog('center');;
            $("#flag").val(2);
            $("#NID_t").val(NID);
            $("#goodsId_t").val(goodsId);
            $("#storageId_t").val(storageId);
        }

        function checkStorage() {
            var flag = $("#flag").val();
            if (flag == 1) {
                addStorage()
            } else if (flag == 2) {
                updateStorage()
            }
        }

        function addStorage() {
            $("#editStorageForm").form("submit", {
                url: '${pageContext.request.contextPath}/storage/addStorage',
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统系统", "保存成功！");
                        $("#dlgStorage").dialog("close");
                        // $("#dlg").dialog("close");
                        // $("#dg").datagrid("reload");
                        ddv.datagrid("reload");
                        resetValue();
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
        }

        function updateStorage() {
            $("#editStorageForm").form("submit", {
                url: '${pageContext.request.contextPath}/storage/updateStorage',
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统系统", "保存成功！");
                        $("#dlgStorage").dialog("close");
                        // $("#dlg").dialog("close");
                        // $("#dg").datagrid("reload");
                        ddv.datagrid("reload");
                        resetValue();
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
        }

        function closeQuestionDialog() {
            $("#dlg").dialog("close");
            $("#dlg3").dialog("close");
            resetValue();
            setTimeout(function () {
                document.location.reload();//重载当前页面
            }, 2000);
        }
    </script>

    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/CityParkProject.js"></script>

    <script type="text/javascript">
        var g_goodsType;
        var g_goodList;
        var goodsId_tt;
        var nId_tt;

        function updateGoodsId(nId_tt, goodsId_tt, goodsType, goodsName, goodsCode) {
            debugger
            $("#addECodeID").dialog("open");
            $("#updateGoods").show();
            $("#saveGoods").hide();
            $("#goodsTable").datagrid({
                singleSelect: true
            });
            $("#goodsTableSelect").datagrid({
                singleSelect: true
            });
            nId_tt = nId_tt;
            goodsId_tt = goodsId_tt;
            $("#goodsId_tt").val(goodsId_tt);
            $("#nId_tt").val(nId_tt);
            $('#searchGoodsType').combobox("setValue", goodsType);
            // $("#goodsNameS").textbox("setValue",goodsName);
            // $("#goodsCodeS").textbox("setValue",goodsCode);
            $('#goodsTable').datagrid('load', {
                goodsType: $("#searchGoodsType").val(),
                // goodsName: $("#goodsNameS").val(),
                // goodsCode: $("#goodsCodeS").val(),
            });
        }

        function updateGoods() {
            debugger
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
                            ddv.datagrid('reload');
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
            $('#EP4').numberspinner({
                min: 0,
                max: 2000,
                editable: true
            });

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

            $("#btnExcel").click(function () {
                $("#addExcelECodeID").dialog("open").dialog("setTitle", "Excel批量导入");
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
            $('#btnReset').bind('click', function () {
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
</head>
<body>

<table id="dg">
</table>

<div id="tb">
    <a href="javascript:opensqd()" class="easyui-linkbutton" iconCls="icon-add"
       plain="true">新增</a>
    <a href="javascript:openExcel()" class="easyui-linkbutton" iconCls="icon-excel"
       plain="true">批量导入</a>
</div>

<div id="editStorage">
    <%--<a id="btnSave" class="easyui-linkbutton" iconCls="icon-save"--%>
    <%--   plain="true">保存</a>--%>
    <a id="btnDlgStorage" class="easyui-linkbutton" iconCls="icon-edit"
       plain="true">编辑</a>
</div>


<div id="dlgStorage" class="easyui-window" title="编辑库存数量" style="width: 400px;height: 240px;" data-options="closed:true"
     align="center">
    <form id="editStorageForm">
        <table>
            <tr>
                <td>需求
                </td>
                <td>
                    <input id="mount" name="mount" class="easyui-numberspinner" value="0"
                           data-options="required:false,increment:2" style="width:60px;"/>
                    备用
                    <input id="mountBack" name="mountBack" class="easyui-numberspinner" value="0"
                           data-options="required:false,increment:2" style="width:60px;"/>
                    库存
                    <input id="mountIn" name="mountIn" class="easyui-numberspinner" value="0"
                           data-options="required:false,increment:2" style="width:60px;"/>
                    <input id="NID_t" type="hidden" name="NID"/>
                    <input id="goodsId_t" type="hidden" name="goodsId"/>
                    <input id="goodsId_tt" type="hidden"/>
                    <input id="nId_tt" type="hidden"/>
                    <input id="storageId_t" type="hidden" name="storageId"/>
                    <input type="hidden" id="flag" name="flag"/>
                </td>
            </tr>
            <tr>
                <td>变更</td>
                <td>
                    <input type="text" id="ETemp" name="ETemp" size="30" maxlength="40"
                           class="easyui-textbox easyui-validatebox" data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>计划备注</td>
                <td>
                    <input type="text" id="EPlan" name="EPlan" size="30" maxlength="40"
                           class="easyui-textbox easyui-validatebox" data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>发货标识</td>
                <td>
                    <span>
                        <input class="EIsRun" type="radio" name="EIsRun" value="×" checked="true">×</input>
                        <input class="EIsRun" type="radio" name="EIsRun" value="√">√</input>
                    </span>
                </td>
            </tr>
        </table>
        <br/>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:checkStorage()">保存</a>
        <a href="javascript:closeStorageDialog()" class="easyui-linkbutton">关闭</a>
    </form>
</div>

<div id="dlg" class="easyui-dialog" style="width: 640px;height: 440px;padding: 10px 20px" closed="true"
     buttons="#dlg-buttons">
    <form id="fm" method="post" enctype="multipart/form-data">
        <table style="margin:5px;height:70px;padding:5px">
            <tr>
                <td style="width:70px;">加工类型</td>
                <td>
                    <input id="EPType" class="easyui-combotree" name="EPType" value="外协件"
                           data-options="url:'${pageContext.request.contextPath}/static/mock/mock_tree.json',
                           required:true,editable:false,panelHeight:'auto'"
                           style="width:180px;">
                </td>
            </tr>
            <tr>
                <td>生产类型</td>
                <td>
                    <span>
                        <input class="EP1" type="radio" name="EP1" value="A" checked="true">A:智能生产</input>
                        <input class="EP1" type="radio" name="EP1" value="B">B:智能零单</input>
                        <input class="EP1" type="radio" name="EP1" value="K">K:科技零单</input>
                        <input class="EP1" type="radio" name="EP1" value="empty">空白:科技生产</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>类别分类</td>
                <td>
                    <span>
                        <input class="EType" type="radio" name="EType" value="0" checked="true">0:机械生产</input>
                        <input class="EType" type="radio" name="EType" value="4">4:电气生产</input>
                        <input class="EType" type="radio" name="EType" value="6">6:机械现场</input>
                        <input class="EType" type="radio" name="EType" value="7">7:电气现场</input>
                        <input class="EType" type="radio" name="EType" value="8">8:试制研发</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>场地</td>
                <td>
                    <span>
                        <input class="EP3" type="radio" name="EP3" value="H" checked="true">H：芜湖工厂</input>
                        <input class="EP3" type="radio" name="EP3" value="empty">空白</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>项目</td>
                <td>
                    <input type="text" id="cityIDSelect" name="EPName" size="10" maxlength="40" class="easyui-combobox"
                           data-options="required:false,panelHeight:'auto'"/>
                    <input type="text" id="parkIDSelect" name="EParkName" size="10" maxlength="40"
                           class="easyui-combobox" data-options="required:false,panelHeight:'auto'"/>
                    <input type="text" id="projectIDSelect" name="ESubID" size="10" maxlength="40"
                           class="easyui-combobox" data-options="required:false,panelHeight:'auto'"/>
                    <select id="ESysName" class="easyui-combobox" style="width:80px;" name="ESysName" value="辅助"
                            size="10" data-options="editable:false,panelHeight:'auto'">
                        <option value="辅助">辅助</option>
                        <option value="轨道">轨道</option>
                        <option value="平台">平台</option>
                        <option value="特技">特技</option>
                        <option value="投影">投影</option>
                        <option value="辅助">小车</option>
                        <option value="银幕架">银幕架</option>
                        <option value="空白">空白</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>子项目</td>
                <td>
                    <input type="text" id="ESubProjectNameElse" name="ESubProjectNameElse" size="38"
                           class="easyui-textbox easyui-validatebox" data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>存货编码</td>
                <td>
                    <input type="text" id="ECodeID" name="ECodeID" class="easyui-textbox"
                           data-options="width:300,multiline:true,height:80,required:true"/>
                    <%--<input class="easyui-textbox" type="hidden" id="nId" name="nId"/>--%>
                    <a id="btnSelect" class="easyui-linkbutton" data-options="iconCls:'icon-add'">选择</a>
                    <a id="btnExcel" class="easyui-linkbutton" data-options="iconCls:'icon-excel'">批量</a>
                </td>
            </tr>
            <tr>
                <td class="datagrid-toolbar"></td>
                <td class="datagrid-toolbar"></td>
            </tr>
            <tr></tr>
            <tr>
                <td>提单公司</td>
                <td>
                    <input type="text" id="EBillCompany" name="EBillCompany" size="10" maxlength="20"
                           class="easyui-textbox easyui-validatebox" data-options="required:false"/>
                    区域
                    <input type="text" id="EZone" name="EZone" size="5" maxlength="10" value="工厂"
                           class="easyui-textbox easyui-validatebox" data-options="required:false"/>
                    工厂耗材
                    <select id="EConsumer" class="easyui-combobox" style="width:60px;" name="EConsumer"
                            panelMaxHeight="200px" data-options="editable:false,panelHeight:'auto'">
                        <option value="否">否</option>
                        <option value="是">是</option>
                    </select>
                </td>
            </tr>
            <%--            <tr>--%>
            <%--                <td>申请人</td>--%>
            <%--                <td>--%>
            <%--                    <input type="text" id="EApplicant" name="EApplicant" size="10" maxlength="20"--%>
            <%--                           class="easyui-textbox easyui-validatebox" data-options="required:false"/>--%>
            <%--                </td>--%>
            <%--            </tr>--%>
            <tr>
                <td>公司主体</td>
                <td>
                    <%--<input type="hidden" name="userId" value="${currentMemberShip.userId}"/>--%>
                    <input type="hidden" name="state" value="未提交"/>
                    <%--<input type="text" id="ECompany" name="ECompany" size="20"--%>
                    <%--       class="easyui-textbox easyui-validatebox" data-options="required:false"/>--%>
                    <input id="COMPANY_NAME" name="ECompanyId" type="text" class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
            <tr>
                <td>要求到货时间：</td>
                <td>
                    <%--<a id="endDate_a" href="#" title="不填写，默认当前时间" class="easyui-tooltip">--%>
                    <input type="text" id="endDate" style="width:200px;" name="endDate" class="easyui-validatebox"
                           required="true"/>
                    <%--</a>--%>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="dlg-buttons">
    <%--<a href="javascript:saveErpDoc()" class="easyui-linkbutton" iconCls="icon-save">生成文档</a>--%>
    <a href="javascript:saveErp()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeErpDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
<div id="dlg2" class="easyui-dialog" style="width: 750px;height: 250px;padding: 10px 20px" closed="true">
    <table id="dg2" title="批注列表" class="easyui-datagrid"
           fitColumns="true"
           url="${pageContext.request.contextPath}/task/listHistoryCommentWithProcessInstanceId.action"
           style="width: 700px;height: 200px;">
        <thead>
        <tr>
            <th field="time" width="120" align="center">批注时间</th>
            <th field="userId" width="100" align="center">批注人</th>
            <th field="message" width="200" align="center">批注信息</th>
        </tr>
        </thead>
    </table>
</div>

<!-- 存货编码选择 -->
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
<%--待选择项--%>
<div id="goodsTableSearch">
    名称:<input type="text" class="easyui-textbox" id="goodsNameS" style="width:100px"/> 
    图号:<input type="text" class="easyui-textbox" id="goodsCodeS" style="width:100px"/> 
    <a id="standardQueryBtn" href="javascript:void(0)" class="easyui-linkbutton"
       data-options="iconCls:'icon-search'">搜索</a>
    <a class="easyui-linkbutton"
       data-options="iconCls:'icon-remove'" id="btnReset">重置</a>
</div>
<div id="addExcelECodeID" class="easyui-dialog" closed="true" style="width: 400px;height: 140px;">
    <form id="excelManage" method="post" enctype="multipart/form-data">
        <div style="margin-bottom:20px" style="width: 300px;height: 60px;">
            <div>选择文件:</div>
            <input class="easyui-filebox" name="file" id="uploadExcel"
                   data-options="prompt:'选择文件',buttonText:'&nbsp;选&nbsp;择&nbsp;',required:true" style="width:300px">
        </div>
        <a href="#" class="easyui-linkbutton" style="width:122px" onclick="uploadExcel()">导入</a> 　　 　　　　　

    </form>
</div>
<!-- 存货编码选择 -->

<div id="allExcel" class="easyui-dialog" closed="true" style="width: 400px;height: 140px;">
    <form id="allExcelManage" method="post" enctype="multipart/form-data">
        <div style="margin-bottom:20px" style="width: 300px;height: 60px;">
            <div>选择文件:</div>
            <input class="easyui-filebox" name="file" id="uploadAllExcel"
                   data-options="prompt:'选择文件',buttonText:'&nbsp;选&nbsp;择&nbsp;',required:true" style="width:300px">
        </div>
        <a href="#" class="easyui-linkbutton" style="width:122px" onclick="uploadAllExcel()">导入</a> 　　 　　　　　

    </form>
</div>

<div id="dlg3" class="easyui-window" title="选择提交人" style="width: 360px;height: 180px;" data-options="closed:true"
     align="center">
    <form id="editForm">
        <table>
            <tr>
                <td>部门：</td>
                <td>
                    <input id="dept_2" class="easyui-combotree" style="width:200px;"
                           data-options="editable:false,panelHeight:'auto'">
                </td>
            </tr>
            <tr>
                <div class="datagrid-toolbar"></div>
            </tr>
            <tr>
                <td>提交审批人：</td>
                <td>
                    <select id="QuestionAdviser_2" class="projectSupervisor" type="text" name="factoryPurchase"
                            data-options="editable:false,panelHeight:'auto'"
                            style="width: 200px;">
                    </select>
                </td>
            </tr>
        </table>
        <br/>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:startApply()">提交</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg3').dialog('close')">关闭</a>
    </form>
</div>

</body>
</html>