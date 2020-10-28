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
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>出库管理</title>
    <link href="${pageContext.request.contextPath}/static/css/Site.css" rel="stylesheet"/>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
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

    <%--翻页导航--%>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.navigating.js"></script>

    <%--右键菜单--%>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.menu.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.rowContext.js"></script>

    <script type="text/javascript">
        var sessionPid = ${sessionWarehouse.warehouse.whId};
        var pageContext = "${pageContext.request.contextPath}";
        var g_searchType = '';
        var g_searchValue = '';
        //用于search.js自动补全
        var _idName = '出库单号';
        var _value = 'outOdd';
        //用于crud2.js的esayui初始参数
        var root = "${pageContext.request.contextPath}/wmsrecordout/";
        var pageName = 'wmsrecordoutThrough';
        var name = 'Wmsrecordout';
        var title = '出库列表';
        var idField = 'outOdd';
        var addParam = '?' + idField + '=';
        var selectRows;
        var tooltip = "此条出库单号的所有信息将被清除！";
        var updateRoot = '${pageContext.request.contextPath}/wmsrecordout/';
        var updateName = 'Wmsrecordout';
        var updateId = 'wmsrecordout';
        var updateParam = '?' + updateId + '=';
        var flag = "wmsrecordout";
        var columns = [[
            // {
            //     field: 'outOdd',
            //     title: '出库单号',
            //     width: 50,
            //     align: 'center',
            //     hidden: false//列表默认隐藏
            // },
            {
                field: 'goodEncoding',
                title: '物料编码',
                width: 100,
                align: 'center',
                sortable: false,
                hidden: false,
                formatter: function (value, row, index) {
                    if (row.goods) {
                        return row.goods.goodEncoding;
                    } else {
                        return "获取不到该物料编码！";
                    }
                }
            }, {
                field: 'goodName',
                title: '物料名称',
                width: 100,
                align: 'center',
                sortable: false,
                hidden: false,
                formatter: function (value, row, index) {
                    if (row.goods) {
                        return row.goods.goodName;
                    } else {
                        return "获取不到该物料名称！";
                    }
                }
            }, {
                field: 'goodModel',
                title: '物料型号',
                width: 100,
                align: 'center',
                sortable: false,
                hidden: false,
                formatter: function (value, row, index) {
                    if (row.goods) {
                        return row.goods.goodModel;
                    } else {
                        return "获取不到该物料型号！";
                    }
                }
            }, {
                field: 'outGoodRemark',
                title: '标签',
                width: 100,
                align: 'center',
                sortable: false,
                hidden: false,
                tooltip: true,
                formatter: function (value, row, index) {
                    if (row.goods) {
                        if (row.goods.perform && row.goods.overhaul) {
                            return row.goods.perform.typeName + "/" + row.goods.overhaul.typeName;
                        } else {
                            return "获取不到该物料标签！";
                        }
                    } else {
                        return "获取不到该物料标签！";
                    }
                }
            }, {
                field: 'outAmount',
                title: '数量',
                width: 50,
                align: 'center',
                sortable: false,
                hidden: false
            }, {
                field: 'inWarehouseName',
                title: '本仓库',
                width: 80,
                align: 'center',
                sortable: true,
                hidden: true,
                formatter: function (value, row, index) {
                    if (row.inWarehouse) {
                        return row.inWarehouse.whName;
                    } else {
                        return "获取不到该本仓库！";
                    }
                }
            }, {
                field: 'outWarehouseName',
                title: '用往',
                width: 80,
                align: 'center',
                sortable: true,
                hidden: false,
                formatter: function (value, row, index) {
                    if (row.outWarehouse) {
                        return row.outWarehouse.whName;
                    } else {
                        return "获取不到该调往仓库！";
                    }
                }
            }, {
                field: 'outType',
                title: '出库类别',
                width: 80,
                align: 'center',
                tooltip: false,
                formatter: function (value, row, index) {
                    if (row.outType) {
                        return row.outType.typeName;
                    } else {
                        return "获取不到该出库类别！";
                    }
                }
            }, {
                field: 'outDate',
                title: '出库日期',
                width: 100,
                align: 'center',
                sortable: true,
                hidden: false,
                tooltip: true,
            }, {
                field: 'outInfo',
                title: '备注',
                width: 50,
                align: 'center',
                sortable: false,
                hidden: true,
                tooltip: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return value;
                    } else {
                        return '';
                    }
                }
            }, {
                field: 'allName',
                title: '领用人',
                width: 50,
                align: 'center',
                sortable: false,
                hidden: false,
                formatter: function (value, row, index) {
                    if (row.user) {
                        return row.user.allName;
                    } else {
                        return "获取不到该领用人！";
                    }
                }
            }, {
                field: 'approveId',
                title: '操作',
                width: 80,
                align: 'center',
                sortable: false,
                hidden: false,
                formatter: function (value, row, index) {
                    if (row.outTypeId == '85') {
                        if (value == '1') {
                            return "<a href='javascript:updateApproveId(" + row.outId + "," + row.approveId + ")' class='easyui-linkbutton' data-options='toggle:true'><font color='#deb887'>初始提交</font></a>";
                        } else if (value == '2') {
                            return getPermission(row);
                        } else if (value == '3') {
                            return "<a class='easyui-linkbutton' data-options='toggle:true'><font color='lightgreen'>审核通过</font></a>";
                        } else if (value == '4') {
                            return "<a class='easyui-linkbutton' data-options='toggle:true'><font color='#a52a2a'>审核未通过</font></a>";
                        } else if (value == '5') {
                            return "<a class='easyui-linkbutton' data-options='toggle:true'><font color='#00bfff'>已完成</font></a>";
                        }
                    } else {
                        return "不需要审核";
                    }
                }
            },
        ]];
        var h = 340;
        var w = 400;
        var _title = '出库管理';

        function getID(info) {
            if (info == 'update') {
                var selectRows = $("#dg").datagrid("getSelections");
                var row = selectRows[0].outId;
                return row;
            } else {
                var selectRows = $("#dg").datagrid("getSelections");
                if (selectRows.length == 0) {
                    $.messager.alert("系统提示", "请选择要删除的数据！");
                    return;
                }
                var strIds = [];
                for (var i = 0; i < selectRows.length; i++) {
                    strIds.push(selectRows[i].outId);
                }
                return strIds;
            }
        }

        function addRecordOut() {
            var addOrUpdate = $('#addOrUpdate').val();
            if (addOrUpdate == 'add') {
                var newSearchGood = $('#goodEncoding').combobox('getValue');
                if (newSearchGood == "") {
                    $.messager.alert('提示', '请选择物料编码！', 'info');
                    return;
                }
                var formData = $('#editForm').serializeJSON();
                $.ajax({
                    url: "${pageContext.request.contextPath}/wmsrecordout/addWmsrecordout?inRespositoryId=" + sessionPid,
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
                                $("#dg").datagrid("clearSelections");//解决方法：在删除数据成功后清空所有的已选择项
                                $('#dg').datagrid('reload');
                            }
                        });
                    }
                });
            } else if (addOrUpdate == 'update') {
                var newSearchGood = $('#goodEncoding').combobox('getValue');
                if (newSearchGood == "") {
                    $.messager.alert('提示', '请选择物料编码！', 'info');
                    return;
                }
                var formData = $('#editForm').serializeJSON();
                $.ajax({
                    url: "${pageContext.request.contextPath}/wmsrecordout/updateWmsrecordout",
                    data: formData,
                    dataType: 'json',
                    type: 'post',
                    success: function (result) {
                        if (result.success) {
                            message = "修改成功";
                        } else {
                            message = "修改失败";
                        }
                        $.messager.alert("提示", message, 'info', function () {
                            if (result.success) {
                                $("#dg").datagrid("clearSelections");//解决方法：在删除数据成功后清空所有的已选择项
                                $('#dg').datagrid('reload');
                            }
                        });
                    }
                });
            }
        }

        function endRecordOut() {
            $("#editForm").form('clear');
            $('#editDlg').dialog('close');
        }

        function updateApproveId(outId, approveId) {
            $.ajax({
                async: false,
                cache: false,
                type: 'POST',
                url: '${pageContext.request.contextPath}/wmsrecordout/updateApproveId',
                data: {outId: outId, approveId: approveId},
                dataType: "json",
                error: function () {
                    $.messager.show({
                        title: '警示信息',
                        msg: "审核失败"
                    })
                },
                success: function (data) {
                    if (approveId == '1') {
                        $.messager.show({
                            title: '警示信息',
                            msg: "初次提交成功"
                        })
                    } else {
                        $.messager.show({
                            title: '警示信息',
                            msg: "审核成功"
                        })
                    }
                    $("#dg").datagrid('reload');
                }

            });
        }

        function updateStroageAndApprovelId(outId, goodId, inRespositoryId, outAmount, approveId) {
            $.ajax({
                async: false,
                cache: false,
                type: 'POST',
                url: '${pageContext.request.contextPath}/wmsrecordout/updateStroageAndApprovelId',
                data: {
                    outId: outId,
                    goodId: goodId,
                    inRespositoryId: inRespositoryId,
                    outAmount: outAmount,
                    approveId: approveId
                },
                dataType: "json",
                error: function () {
                    $.messager.show({
                        title: '警示信息',
                        msg: "审核失败"
                    })
                },
                success: function (data) {
                    if (data.success) {
                        if (approveId == '1') {
                            $.messager.show({
                                title: '警示信息',
                                msg: "初次提交成功"
                            })
                        } else {
                            $.messager.show({
                                title: '警示信息',
                                msg: "审核成功"
                            })
                        }
                    } else {
                        $.messager.show({
                            title: '警示信息',
                            msg: "审核失败"
                        })
                    }
                    $("#dg").datagrid('reload');
                }

            });
        }

        function getPermission(row) {
            var str = '';
            <shiro:hasRole name="Administrator">
            str += "<a href='javascript:updateStroageAndApprovelId(" + row.outId + "," + row.goodId + "," + row.inRespositoryId + "," + row.outAmount + "," + row.approveId + ")' class='easyui-linkbutton' data-options='toggle:true'><font color='#9acd32'>审核</font></a>";
            </shiro:hasRole>
            <shiro:hasRole name="warehouseRecipients">
            str += "<font color='#9acd32'>审核中</font>";
            </shiro:hasRole>
            return str;
        }

        $(document).ready(function () {

            $('#endDate').datetimebox({
                required: true,
                showSeconds: true,
                editable: false,
            });

            $('#outType').combobox(
                {
                    prompt: '输入关键字后自动搜索',
                    // mode: 'remote',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=outType',
                    valueField: 'typeID',
                    textField: 'typeName',
                    panelHeight: 'auto',
                    panelMaxHeight: 100,
                    editable: true,
                    onSelect: function (record) {
                        debugger
                        var whType = record.typeID;
                        $('#outRespository').combobox(
                            {
                                prompt: '输入关键字后自动搜索',
                                // mode: 'remote',
                                url: '${pageContext.request.contextPath}/warehouse/searchAllWarehouseByWhType?whType=' + whType,
                                valueField: 'whId',
                                textField: 'whName',
                                panelHeight: 'auto',
                                panelMaxHeight: 100,
                                editable: true,
                                onLoadSuccess: function () { //加载完成后,val[0]写死设置选中第一项
                                    var val = $(this).combobox("getData");
                                    for (var item in val[0]) {
                                        if (item == "whId") {
                                            $(this).combobox("select", val[0][item]);
                                        }
                                    }
                                },
                            });
                    }
                });

            <%--$('#inRespository').combobox(--%>
            <%--    {--%>
            <%--        prompt: '输入关键字后自动搜索',--%>
            <%--        // mode: 'remote',--%>
            <%--        url: '${pageContext.request.contextPath}/warehouse/searchAllWarehouse',--%>
            <%--        valueField: 'whId',--%>
            <%--        textField: 'whName',--%>
            <%--        panelHeight: 'auto',--%>
            <%--        panelMaxHeight: 100,--%>
            <%--        editable: true--%>
            <%--    });--%>

            <%--$('#outRespository').combobox(--%>
            <%--    {--%>
            <%--        prompt: '输入关键字后自动搜索',--%>
            <%--        // mode: 'remote',--%>
            <%--        url: '${pageContext.request.contextPath}/warehouse/searchAllWarehouse',--%>
            <%--        valueField: 'whId',--%>
            <%--        textField: 'whName',--%>
            <%--        panelHeight: 'auto',--%>
            <%--        panelMaxHeight: 100,--%>
            <%--        editable: true--%>
            <%--    });--%>

            $('#goodEncoding').combobox({
                disabled: false,
                url: '${pageContext.request.contextPath}/wmsGood/searchAllWmsGood',
                valueField: 'id',
                textField: 'goodEncoding',
                panelHeight: 'auto',
                panelMaxHeight: 100,
                editable: true,
            }).combobox("clear");

            $('#dg').datagrid({
                navigatingWithKey: true,
                navigateHandler: {
                    up: function (targetIndex) {
                        console.log("上之后被selected的数据索引" + targetIndex);
                    },
                    down: function (targetIndex) {
                        console.log("下之后被selected的数据索引" + targetIndex);
                    },
                    enter: function (selectedData) {
                        alert("功能有待开发！");
                    }
                },
                enableRowContextMenu: true,
                selectOnRowContextMenu: true,
                refreshMenu: false,
                pagingMenu: false,
                rowContextMenu: [
                    {
                        text: "修改", iconCls: "icon-ok", handler: function (e, item, menu, grid, rowIndex, row) {
                            alert("功能有待开发！");
                        }
                    }
                ],
                fitColumns: true,
                toolbar: [
                    {
                        text: '新增',
                        iconCls: 'icon-add',
                        handler: function () {
                            $('#editForm').form('clear');
                            method = "add";
                            $.ajax({
                                async: false,
                                cache: false,
                                type: 'POST',
                                url: '${pageContext.request.contextPath}/wmsrecordout/getNewoutOdd',
                                dataType: "json",
                                error: function () {
                                    $.messager.show({
                                        title: '警示信息',
                                        msg: "新增失败"
                                    })
                                },
                                success: function (data) {
                                    $("#outOdd").textbox('setValue', data);
                                    $("#addOrUpdate").val('add');
                                }

                            });
                            $("#editDlg").dialog("open").dialog("setTitle", "新增出库");
                        }
                    },
                    '-',
                    {
                        text: '再次新增',
                        iconCls: 'icon-add',
                        handler: function () {
                            debugger
                            var selectRows = $("#dg").datagrid("getSelections");
                            if (selectRows.length == 0) {
                                $.messager.alert("系统提示", "请选择要再次出库的数据！");
                                return;
                            } else if (selectRows.length > 1) {
                                $.messager.alert("系统提示", "请选择一条要再次出库的数据！");
                                return;
                            } else {
                                $("#outOdd").textbox('setValue', selectRows[0].outOdd);
                                $("#addOrUpdate").val('add');
                                $("#editDlg").dialog("open").dialog("setTitle", "再次新增出库");
                            }
                        }
                    },
                    '-',
                    {
                        text: '修改',
                        iconCls: 'icon-edit',
                        handler: function () {
                            $('#editForm').form('clear');
                            var selectRows = $("#dg").datagrid("getSelections");
                            if (selectRows.length != 1) {
                                $.messager.alert("系统提示", "请选择一条要编辑的数据！");
                                return;
                            } else {
                                var row = selectRows[0];
                                $('#editDlg').dialog('open');
                                $('#editForm').form('load', row);
                                $("#outId").val(selectRows[0].outId);
                                $("#addOrUpdate").val('update');
                                $("#editDlg").dialog("open").dialog("setTitle", "修改出库数据");
                            }
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
                            $.download(root + pageName + "Export" + listParam + '?flag=' + flag, formData);
                        }
                    },
                    '-',
                    {
                        text: '导入',
                        iconCls: 'icon-save',
                        handler: function () {
                            $('#importDlg').dialog('open');
                        }
                    }
                ],
            });

        })
    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/crud2.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/download2.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/CityParkProject.js"></script>
</head>
<body class="easyui-layout">
<!--搜索区  -->
<div data-options="region:'north',title:'查询'"
     style="padding: 4px; background-color: #eee; height: 130px">
    <form id="searchForm">
        <table cellpadding="5">
            <tr>
                <td> &nbsp;
                    出库时间:&nbsp;<input type="text" id="s_startDate" name="startDate" size="20"/> --
                    <input type="text" id="s_endDate" name="endDate" size="20"/>
                </td>
                <td>
                    <input id="ss" style="width:300px">
                    <div id="searchType" style="width:120px">
                        <div data-options="name:'outOdd',iconCls:'icon-ok'">出库单号</div>
                        <div data-options="name:'allName'">操作人</div>
                        <div data-options="name:'outRemark'">出库标签</div>
                    </div>
                </td>
                <td>
                    <a class="easyui-linkbutton"
                       data-options="iconCls:'icon-reload'" id="btnReset">重置</a>
                </td>
            </tr>
        </table>
    </form>
    <form id="searchForm2">
        <div>
            <label>入库单号:</label><input name="inOdd" class="easyui-textbox" id="searchInOdd"/>
            <label>供应商:</label><input name="supplierId" class="easyui-combobox" id="searchSupplier"/>
            <label>物料编码:</label><input name="goodId" class="easyui-combobox" id="searchGood"/>
            <a href="javascript:searchTable()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
               id="btnSearch">查询</a>
            <a href="javascript:resetSearch()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置</a>
        </div>
    </form>
</div>

<!-- 数据表格区 -->
<div data-options="region:'center',collapsible:true,split:true">
    <table id="dg"></table>
</div>
<!-- 添加、修改 -->
<div id="editDlg" class="easyui-window" data-options="closed:true" align="center">
    <form id="editForm">
        <table>
            <tr>
                <td>出库单号：</td>
                <td>
                    <input id="outOdd" name="outOdd" class="easyui-textbox easyui-validatebox" type="text"
                           readonly="readonly">
                    <input id="outId" name="outId" hidden="true">
                    <input id="addOrUpdate" hidden="true">
                    <%--                    <input id="inRespository" name="inRespositoryId" value="1" hidden="true">--%>
                </td>
            </tr>
            <tr>
                <td>物料编码：</td>
                <td>
                    <input id="goodEncoding" name="goodId" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
            <tr>
                <td>出库数量：</td>
                <td>
                    <input id="outAmount" name="outAmount" class="easyui-numberspinner easyui-validatebox" type="text">
                </td>
            </tr>
            <tr>
                <td>出库类型：</td>
                <td valign="middle">
                    <input id="outType" name="outTypeId" class="easyui-combobox easyui-validatebox" type="text"
                           editable="false">
                </td>
            </tr>
            <%--            <tr>--%>
            <%--                <td>本仓库：</td>--%>
            <%--                <td>--%>
            <%--                    <input id="inRespository" name="inRespositoryId" class="easyui-textbox easyui-validatebox" type="text">--%>
            <%--                </td>--%>
            <%--            </tr>--%>
            <tr>
                <td>用往：</td>
                <td>
                    <input id="outRespository" name="outRespositoryId" class="easyui-textbox easyui-validatebox"
                           type="text">
                </td>
            </tr>
            <tr>
                <td>备注：</td>
                <td>
                    <input id="outInfo" name="outInfo" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
        </table>
        <br/>
        <button id="btnAdd" class="easyui-linkbutton" type="button" onclick="addRecordOut()">提交物料</button>
        <button id="btnEnd" class="easyui-linkbutton" type="button" onclick="endRecordOut()">结束单号</button>
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