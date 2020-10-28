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
    <title>需求管理</title>
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

    <%--翻页导航--%>
    <%--    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.navigating.js"></script>--%>

    <%--右键菜单--%>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.menu.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.rowContext.js"></script>

    <%--行回车编辑--%>
    <link href="${pageContext.request.contextPath}/static/css/jeasyui.extensions.validatebox.css"
          rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.validatebox.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.rowState.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.getColumnInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.editors.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.edit.enterFocus.js"></script>

    <%--时间格式--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/date.format/date.format.js"></script>

    <script type="text/javascript">
        function openStorageDialogAdd(NID, goodsId) {
            $("#dlgStorage").dialog("open").dialog("setTitle", "编辑（" + goodsId + "）库存信息");
            $("#flag").val(1);
            $("#NID_t").val(NID);
            $("#goodsId_t").val(goodsId);
        }

        function openStorageDialogUpdate(NID, goodsId, storageId) {
            debugger
            $("#dlgStorage").dialog("open").dialog("setTitle", "编辑（" + goodsId + "）库存信息");
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
            debugger
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
                        $("#dg").datagrid("reload");
                        resetValue();
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
        }

        function updateStorage() {
            debugger
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
                        $("#dg").datagrid("reload");
                        resetValue();
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
        }

        $(document).ready(function () {

            var editBoxing = undefined;
            $('#dg').datagrid({
                url: '${pageContext.request.contextPath}/need/getNeedGoodsById?nId=${param.id}',
                idField: "nId",
                fitColumns: true,
                frozenColumns: [[
                    {field: 'id', checkbox: true}
                ]],
                columns: [[
                    {
                        field: 'goodsId', title: '存货编码', width: 20, align: 'center', tooltip: true,
                        formatter: function (value, row, index) {
                            if (value.substr(3, 3) == 'NEW') {
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
                            if (row.goods == null) {
                                return "";
                            } else {
                                return row.goods.goodsName;
                            }
                        }
                    },
                    {
                        field: 'goodsCode', title: '图号', width: 20, align: 'center', tooltip: true,
                        formatter: function (value, row, index) {
                            if (row.goods == null) {
                                return "";
                            } else {
                                return row.goods.goodsCode;
                            }
                        }
                    },
                    {
                        field: 'goodsType', title: '类型', width: 20, align: 'center', hidden: true,
                        formatter: function (value, row, index) {
                            if (row.goods == null) {
                                return "";
                            } else {
                                return row.goods.goodsType;
                            }
                        }
                    },
                    {
                        field: 'goodsUnit', title: '单位', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            if (row.goods == null) {
                                return "";
                            } else {
                                return row.goods.goodsUnit;
                            }
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
                        editor: {
                            type: "text"
                        },
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
                        editor: {
                            type: "text"
                        },
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
                        editor: {
                            type: "text"
                        },
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
                fit: true,
                rownumbers: true,
                pagination: true,
                singleSelect: true,
                pageList: [5, 10, 20, 50],//可以设置每页记录条数的列表
                iconCls: 'icon-tip',
                striped: true,// 是否显示斑马线效果。
                collapsible: true,	//定义是否显示可折叠按钮。
                nowrap: true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
                //toolbar: "#editStorage",
                toolbar: [{
                    text: '编辑',
                    iconCls: 'icon-edit',
                    handler: function () {
                        var selectRows = $("#dg").datagrid("getSelections");
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
                        } else {
                            $("#mount").textbox("setValue", "");
                            $("#mountBack").textbox("setValue", "");
                            $("#mountIn").textbox("setValue", "");
                            $("#ETemp").textbox("setValue", "");
                            $("#EPlan").textbox("setValue", "");
                            $("#EIsRun").textbox("setValue", "");
                            openStorageDialogAdd(row.nId, row.goods.goodsId);
                        }
                    }
                }, '-', {
                    text: '取消编辑',
                    iconCls: 'icon-redo',
                    handler: function () {
                        if (editBoxing == 0) {
                            $('#dg').datagrid('deleteRow', editBoxing);
                            editBoxing = undefined;
                            $('#dg').datagrid('reload');
                        } else {
                            editBoxing = undefined;
                            $('#dg').datagrid('reload');
                        }
                    }
                },
                    // '-', {
                    //     text: '导出',
                    //     iconCls: 'icon-excel',
                    //     handler: function () {
                    //         if (editBoxing == 0) {
                    //             $('#dg').datagrid('deleteRow', editBoxing);
                    //             editBoxing = undefined;
                    //             $('#dg').datagrid('reload');
                    //         } else {
                    //             editBoxing = undefined;
                    //             $('#dg').datagrid('reload');
                    //         }
                    //     }
                    // }
                ],
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
                // onResize: function () {
                //     $('#dg').datagrid('fixDetailRowHeight', index);
                // },
                // onLoadSuccess: function () {
                //     $('#dg').datagrid('fixDetailRowHeight', index);
                // },
                loadFilter: function (data) {
                    var data_ = {
                        "rows": data.rows,
                        "total": data.rows.length,
                    };
                    return data_;
                },
                onDblClickRow: function (index, rows) {
                    // rows = rows.storage;
                    editBoxing = index;
                    $('#dg').datagrid("beginEdit", index);
                    // $('#dg').datagrid("loadData", rows);
                },
                // 表示开启单元格编辑功能
                enableCellEdit: true,
                // 表示开启编辑后是否启用回车后自动聚焦下一个列编辑器的功能
                enterFocusNextEditor: true,
                // 表示当编辑器内容为空，是否停止自动聚焦功能
                stopEnterFocusWhenEmpty: false,
                // 表示聚焦到最后一个单元格编辑器之后触发的事件
                onAfterFoucsLastEditor: function (index, field) {
                    var data = $('#dg').datagrid('getData');
                    var row = data.rows[index];
                    debugger
                    if (row.storageId == null || row.storageId == "") {
                        $.post('${pageContext.request.contextPath}/storage/addStorage', {
                            "NID": row.nId,
                            "goodsId": row.goodsId,
                            "mount": row.mount,
                            "mountIn": row.mountIn,
                            "mountBack": row.mountBack,
                            "ETemp": row.ETemp,
                            "EPlan": row.EPlan,
                            "EIsRun": row.EIsRun,
                        }, function (data) {
                            var data = eval('(' + data + ')');
                            if (data.success == true) {
                                $.messager.show({
                                    title: '新增消息',
                                    msg: '新增成功',
                                    timeout: 1500,
                                });
                                $('#dg').datagrid('reload');
                            } else {
                                $.messager.alert('警告', '未完成新增');
                            }
                            ;
                        });
                    } else {
                        $.post('${pageContext.request.contextPath}/storage/updateStorage', {
                            "storageId": row.storageId,
                            "mount": row.mount,
                            "mountIn": row.mountIn,
                            "mountBack": row.mountBack,
                            "ETemp": row.ETemp,
                            "EPlan": row.EPlan,
                            "EIsRun": row.EIsRun,
                        }, function (data) {
                            var data = eval('(' + data + ')');
                            if (data.success == true) {
                                $.messager.show({
                                    title: '更新消息',
                                    msg: '更新成功',
                                    timeout: 1500,
                                });
                                $('#dg').datagrid('reload');
                            } else {
                                $.messager.alert('警告', '未完成新增');
                            }
                            ;
                        });
                    }
                },
                <%--onAfterEdit: function (index, field) {--%>
                <%--    var data = $('#dg').datagrid('getData');--%>
                <%--    var row = data.rows[index];--%>
                <%--    debugger--%>
                <%--    if (row.storageId == null || row.storageId == "") {--%>
                <%--        $.post('${pageContext.request.contextPath}/storage/addStorage', {--%>
                <%--            "NID": row.nId,--%>
                <%--            "goodsId": row.goodsId,--%>
                <%--            "mount": row.mount,--%>
                <%--            "mountIn": row.mountIn,--%>
                <%--            "mountBack": row.mountBack,--%>
                <%--            "ETemp": row.ETemp,--%>
                <%--            "EPlan": row.EPlan,--%>
                <%--            "EIsRun": row.EIsRun,--%>
                <%--        }, function (data) {--%>
                <%--            var data = eval('(' + data + ')');--%>
                <%--            if (data.success == true) {--%>
                <%--                $.messager.show({--%>
                <%--                    title: '新增消息',--%>
                <%--                    msg: '新增成功',--%>
                <%--                    timeout: 3000,--%>
                <%--                });--%>
                <%--                $('#dg').datagrid('reload');--%>
                <%--            } else {--%>
                <%--                $.messager.alert('警告', '未完成新增');--%>
                <%--            };--%>
                <%--        });--%>
                <%--    }else {--%>
                <%--        $.post('${pageContext.request.contextPath}/storage/updateStorage', {--%>
                <%--            "storageId": row.storageId,--%>
                <%--            "mount": row.mount,--%>
                <%--            "mountIn": row.mountIn,--%>
                <%--            "mountBack": row.mountBack,--%>
                <%--            "ETemp": row.ETemp,--%>
                <%--            "EPlan": row.EPlan,--%>
                <%--            "EIsRun": row.EIsRun,--%>
                <%--        }, function (data) {--%>
                <%--            var data = eval('(' + data + ')');--%>
                <%--            if (data.success == true) {--%>
                <%--                $.messager.show({--%>
                <%--                    title: '更新消息',--%>
                <%--                    msg: '更新成功',--%>
                <%--                    timeout: 3000,--%>
                <%--                });--%>
                <%--                $('#dg').datagrid('reload');--%>
                <%--            } else {--%>
                <%--                $.messager.alert('警告', '未完成新增');--%>
                <%--            };--%>
                <%--        });--%>
                <%--    }--%>
                <%--},--%>
                <%--onDblClickCell: function (index, field, value) {--%>
                <%--    debugger--%>
                <%--    if (editBoxing == undefined) {--%>
                <%--        editBoxing = index;--%>
                <%--        $('#dg').datagrid('beginEdit', index);--%>
                <%--        var ed = $(this).datagrid('getEditor', {--%>
                <%--            index: index,--%>
                <%--            field: field--%>
                <%--        });--%>
                <%--        $(ed.target).focus();--%>
                <%--    } else {--%>
                <%--        $('#dg').datagrid('endEdit', editBoxing);--%>
                <%--        editBoxing = index;--%>
                <%--        $('#dg').datagrid('beginEdit', index);--%>
                <%--        var ed = $(this).datagrid('getEditor', {--%>
                <%--            index: index,--%>
                <%--            field: field--%>
                <%--        });--%>
                <%--        $(ed.target).focus();--%>
                <%--    }--%>
                <%--},--%>
            });

            <%--var editBoxing = undefined;--%>
            <%--$('#dg').datagrid({--%>
            <%--    toolbar: [{--%>
            <%--        text: '新增',--%>
            <%--        iconCls: 'icon-edit',--%>
            <%--        handler: function () {--%>
            <%--            if (editBoxing == undefined) {--%>
            <%--                editBoxing = 0;--%>
            <%--                $('#dg').datagrid("insertRow", {--%>
            <%--                    index: editBoxing, // 索引从0开始--%>
            <%--                    row: {--%>
            <%--                        "file_id": uuidOut(18, 20),//新建文件ID--%>
            <%--                        "projectID": g_projectID,//项目名--%>
            <%--                    }--%>
            <%--                });--%>
            <%--                $('#dg').datagrid('beginEdit', editBoxing);--%>
            <%--            } else {--%>
            <%--                $.messager.alert('警告', '尚有未编辑完成单元，请继续编辑', 'info');--%>
            <%--            }--%>

            <%--        }--%>
            <%--    }, '-', {--%>
            <%--        text: '保存',--%>
            <%--        iconCls: 'icon-save',--%>
            <%--        handler: function () {--%>
            <%--            $('#dg').datagrid('endEdit', editBoxing);--%>
            <%--            editBoxing = undefined;--%>
            <%--        }--%>
            <%--    }, '-', {--%>
            <%--        text: '删除',--%>
            <%--        iconCls: 'icon-remove',--%>
            <%--        handler: function () {--%>
            <%--            var array = $('#dg').datagrid('getSelections');--%>
            <%--            if (array.length >= 1) {--%>
            <%--                var str = '';--%>
            <%--                var fileID = '';//文件id--%>
            <%--                for (var i = 0; i < array.length; i++) {--%>
            <%--                    str += array[i].gzID + ",";--%>
            <%--                    fileID += array[i].file_id + ",";--%>
            <%--                }--%>
            <%--                $.post('${pageContext.request.contextPath}/gaizao/gaizaoDelete', {--%>
            <%--                    str: str,--%>
            <%--                    fileID: fileID,--%>
            <%--                }, function (data) {--%>
            <%--                    var data = eval('(' + data + ')');--%>
            <%--                    if (data.success == true) {--%>
            <%--                        $('#dg').datagrid('reload');--%>
            <%--                        $('#dg').datagrid('reload');--%>
            <%--                        $.messager.show({--%>
            <%--                            title: '更新消息',--%>
            <%--                            msg: '删除成功',--%>
            <%--                            timeout: 3000--%>
            <%--                        });--%>
            <%--                    }--%>
            <%--                    $('#dg').datagrid('reload');--%>
            <%--                });--%>
            <%--            } else {--%>
            <%--                $.messager.alert('警告', '请选择一条记录');--%>
            <%--            }--%>
            <%--        }--%>
            <%--    }, '-', {--%>
            <%--        text: '取消编辑',--%>
            <%--        iconCls: 'icon-redo',--%>
            <%--        handler: function () {--%>
            <%--            if (editBoxing == 0) {--%>
            <%--                $('#dg').datagrid('deleteRow', editBoxing);--%>
            <%--                editBoxing = undefined;--%>
            <%--                $('#dg').datagrid('reload');--%>
            <%--            } else {--%>
            <%--                editBoxing = undefined;--%>
            <%--                $('#dg').datagrid('reload');--%>

            <%--            }--%>

            <%--        }--%>
            <%--    }, '-',],--%>
            <%--    onAfterEdit: function (index, row, changes) {--%>
            <%--        if (row.storageId == null || row.storageId == "") {--%>
            <%--            $.post('${pageContext.request.contextPath}/storage/addStorage', {--%>
            <%--                "NID": row.nId,--%>
            <%--                "goodsId": row.goodsId,--%>
            <%--                "mount": row.mount,--%>
            <%--                "mountIn": row.mountIn,--%>
            <%--                "mountBack": row.mountBack,--%>
            <%--                "ETemp": row.ETemp,--%>
            <%--                "EPlan": row.EPlan,--%>
            <%--                "EIsRun": row.EIsRun,--%>
            <%--            }, function (data) {--%>
            <%--                var data = eval('(' + data + ')');--%>
            <%--                if (data.success == true) {--%>
            <%--                    $.messager.show({--%>
            <%--                        title: '新增消息',--%>
            <%--                        msg: '新增成功',--%>
            <%--                        timeout: 3000,--%>
            <%--                    });--%>
            <%--                    $('#dg').datagrid('reload');--%>
            <%--                } else {--%>
            <%--                    $.messager.alert('警告', '未完成新增');--%>
            <%--                };--%>
            <%--            });--%>
            <%--        }else {--%>
            <%--            $.post('${pageContext.request.contextPath}/storage/updateStorage', {--%>
            <%--                "storageId": row.storageId,--%>
            <%--                "mount": row.mount,--%>
            <%--                "mountIn": row.mountIn,--%>
            <%--                "mountBack": row.mountBack,--%>
            <%--                "ETemp": row.ETemp,--%>
            <%--                "EPlan": row.EPlan,--%>
            <%--                "EIsRun": row.EIsRun,--%>
            <%--            }, function (data) {--%>
            <%--                var data = eval('(' + data + ')');--%>
            <%--                if (data.success == true) {--%>
            <%--                    $.messager.show({--%>
            <%--                        title: '更新消息',--%>
            <%--                        msg: '更新成功',--%>
            <%--                        timeout: 3000,--%>
            <%--                    });--%>
            <%--                    $('#dg').datagrid('reload');--%>
            <%--                } else {--%>
            <%--                    $.messager.alert('警告', '未完成新增');--%>
            <%--                };--%>
            <%--            });--%>
            <%--        }--%>
            <%--    },--%>
            <%--    onDblClickCell: function (index, field, value) {--%>
            <%--        if (editBoxing == undefined) {--%>
            <%--            editBoxing = index;--%>
            <%--            $('#dg').datagrid('beginEdit', index);--%>
            <%--            var ed = $(this).datagrid('getEditor', {--%>
            <%--                index: index,--%>
            <%--                field: field--%>
            <%--            });--%>
            <%--            $(ed.target).focus();--%>
            <%--        } else {--%>
            <%--            $('#dg').datagrid('endEdit', editBoxing);--%>
            <%--            editBoxing = index;--%>
            <%--            $('#dg').datagrid('beginEdit', index);--%>
            <%--            var ed = $(this).datagrid('getEditor', {--%>
            <%--                index: index,--%>
            <%--                field: field--%>
            <%--            });--%>
            <%--            $(ed.target).focus();--%>
            <%--        }--%>
            <%--    }--%>
            <%--});--%>

            // var buttonBindEvent = function () {
            //     $("#btnDlgStorage").click(function () {
            //         var selectRows = $("#dg").datagrid("getSelections");
            //         if (selectRows.length != 1) {
            //             $.messager.alert("系统提示", "请选择一条要编辑的数据！");
            //             return;
            //         }
            //         var row = selectRows[0];
            //         if (row.storage != null) {
            //             $("#mount").textbox("setValue", row.storage.mount);
            //             $("#mountBack").textbox("setValue", row.storage.mountBack);
            //             $("#mountIn").textbox("setValue", row.storage.mountIn);
            //             $("#ETemp").textbox("setValue", row.storage.ETemp);
            //             $("#EPlan").textbox("setValue", row.storage.EPlan);
            //             $("#EIsRun").textbox("setValue", row.storage.EIsRun);
            //             openStorageDialogUpdate(row.nId, row.goods.goodsId, row.storage.storageId);
            //         } else {
            //             $("#mount").textbox("setValue", "");
            //             $("#mountBack").textbox("setValue", "");
            //             $("#mountIn").textbox("setValue", "");
            //             $("#ETemp").textbox("setValue", "");
            //             $("#EPlan").textbox("setValue", "");
            //             $("#EIsRun").textbox("setValue", "");
            //             openStorageDialogAdd(row.nId, row.goods.goodsId);
            //         }
            //     });
            // };
            // buttonBindEvent();

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
            $.post("${pageContext.request.contextPath}/need/getNeedByTaskId", {id: '${param.id}'}, function (result) {
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

        })
    </script>
    <!-- 存货编码选择 -->
    <script type="text/javascript">
        var g_goodsType;
        var g_goodList;
        var goodsId_tt;
        var nId_tt;

        function updateGoodsId(nId_tt, goodsId_tt, goodsType, goodsName, goodsCode) {
            $("#addECodeID").dialog("open").dialog("center");
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
                            $("#dgStorage").datagrid('reload');
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
<body class="easyui-layout">

<!-- 数据表格区 -->
<div data-options="region:'center',collapsible:true,split:true,title:'编辑'"
     style="width: 600px;padding: 4px; background-color: #eee">
    <!--搜索区  -->
    <table id="dg"></table>
</div>

<!-- 计划需求单信息 -->
<div data-options="region:'east',title:'计划需求单信息',split:true"
     style="width: 400px;padding: 1px; background-color: #eee">
    <table id="pg"></table>
</div>

<%--<div id="editStorage">--%>
<%--    <a id="btnDlgStorage" class="easyui-linkbutton" iconCls="icon-edit"--%>
<%--       plain="true">编辑</a>--%>
<%--</div>--%>

<!-- 添加、修改 -->
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

<!-- 导入窗口 -->
<div id="importDlg" style="padding: 2px;">
    <form id="importForm" enctype="multipart/form-data">
        导入文件:<input type="file" name="file">
    </form>
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
       data-options="iconCls:'icon-remove'" id="btnReset">重置</a>
</div>
<%--存货编码待选择项--%>
</body>
</html>