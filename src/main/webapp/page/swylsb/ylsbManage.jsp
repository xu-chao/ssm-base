<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>表格列表</title>
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

    <script type="text/javascript">
        $(document).ready(function () {
            $('#parkIDSelect').combobox({
                disabled: false,
                url: '${pageContext.request.contextPath}/park/searchAllPark',
                valueField: 'parkID',
                textField: 'parkAb',
                onLoadSuccess: function (rec) {//加载完成后,val[0]写死设置选中第一项
                    $(this).combobox('setValue', rec[0].parkID);
                },
                onSelect: function (record) {
                    var parkID = record.parkID;
                    $('#projectIDSelect').combobox({
                        disabled: false,
                        url: '${pageContext.request.contextPath}/project/findProjectByParkID?parkID=' + parkID,
                        valueField: 'projectID',
                        textField: 'projectName',
                        onLoadSuccess: function (rec) {//加载完成后,val[0]写死设置选中第一项
                            if (rec.length > 0) {
                                $(this).combobox('setValue', rec[0].projectID);
                            }
                        }
                    }).combobox("clear");
                }
            }).combobox("clear");

            $('#parkAb').combobox({
                disabled: false,
                url: '${pageContext.request.contextPath}/park/searchAllPark',
                valueField: 'parkID',
                textField: 'parkAb',
                // onLoadSuccess: function (rec) {//加载完成后,val[0]写死设置选中第一项
                //     $(this).combobox('setValue', rec[0].parkID);
                // },
                onSelect: function (record) {
                    var parkID = record.parkID;
                    $('#projectName').combobox({
                        disabled: false,
                        url: '${pageContext.request.contextPath}/project/findProjectByParkID?parkID=' + parkID,
                        valueField: 'projectID',
                        textField: 'projectName',
                        onLoadSuccess: function (rec) {//加载完成后,val[0]写死设置选中第一项
                            if (rec.length > 0) {
                                $(this).combobox('setValue', rec[0].projectID);
                            }
                        }
                    }).combobox("clear");
                }
            }).combobox("clear");

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
                url: '${pageContext.request.contextPath}/swylsb/swylsbPage',
                frozenColumns: [[
                    {
                        field: 'ck',
                        align: 'center',
                        checkbox: true
                    },
                    {
                        field: 'parkAb',
                        title: '公园简称',
                        width: 80,
                        align: 'center',
                        formatter: function (value, row, index) {
                            if (row.project.park) {
                                return row.project.park.parkAb;
                            } else {
                                return "获取不到该乐园简称！";
                            }
                        }
                    },
                    {
                        field: 'operation',
                        title: '操作',
                        width: 160,
                        align: 'center',
                        formatter: function (value, row, index) {
                            e = '<a  id="edit" data-id="98" class=" operA"  onclick="obj.edit(\'' + index + '\')">编辑</a> ';
                            c = '<a  id="look" class="operA"  onclick="obj.look(\'' + row.ID + '\')">详情</a> ';
                            d = '<a  id="add" data-id="98" class=" operA01"  onclick="obj.delOne(\'' + row.ID + '\')">删除</a> ';
                            return e + d + c;
                        }
                    }
                ]],
                columns: [[
                    {field: 'ID', title: 'ID', width: 40, align: 'center', sortable: true, hidden: true},
                    {field: 'sbID', title: '设备序号', width: 40, align: 'center', sortable: true, hidden: true},
                    {
                        field: 'project',
                        title: '项目名称',
                        width: 120,
                        align: 'center',
                        tooltip: true,
                        formatter: function (value, row, index) {
                            if (row.project) {
                                return row.project.projectName;
                            } else {
                                return "获取不到该项目名称！";
                            }
                        }
                    },
                    {field: 'sbName', title: '设备名称', width: 100, align: 'center', sortable: true, tooltip: true,},
                    {field: 'sbClass', title: '设备型号', width: 100, align: 'center', sortable: true, tooltip: true,},
                    {field: 'sbsl', title: '设备数量', width: 60, align: 'center'},
                    // {field: 'sbStatus', title: '设备类型', width: 100, align: 'center'},
                    {
                        field: 'bzStatus', title: '包装类型', width: 120, align: 'center',
                        formatter: function (value, row, index) {
                            if (row.sblx) {
                                return row.sblx.typeName;
                            } else {
                                return "获取不到该类型名称！";
                            }
                        }
                    },
                    {field: 'yyqk', title: '沿用情况', width: 60, align: 'center'},
                    {field: 'jyjbStatus', title: '检验级别', width: 60, align: 'center'},
                    // {field: 'jyjb', title: '检验级别', width: 100, align: 'center'},
                    {
                        field: 'xssyStatus', title: '型式试验', width: 60, align: 'center',
                        formatter: function (value, row, index) {
                            if (row.xuyao) {
                                return row.xuyao.typeName;
                            } else {
                                return "获取不到该类型名称！";
                            }
                        }
                    },
                    {field: 'gydw', title: '供应厂家', width: 100, align: 'center', tooltip: true,},
                    {field: 'azdw', title: '安装厂家', width: 100, align: 'center', tooltip: true,},
                    {field: 'fzrUser', title: '负责人', width: 100, align: 'center'},
                    {field: 'startDate', title: '总工办计划设备进场日期', width: 160, align: 'center'},
                    {field: 'completeDate', title: '总工办计划基础完成日期', width: 160, align: 'center'},
                    {field: 'approachDate', title: '总工办计划设备进场日期', width: 160, align: 'center'},
                    {field: 'sbDate', title: '操作时间', width: 160, align: 'center'},
                ]],
                // queryParams:{},
                fit: true,
                fitColumns: false,
                sortName: 'ID',
                sortOrder: 'desc',
                pagination: true,// 如果为true，则在DataGrid控件底部显示分页工具栏。
                striped: true,// 是否显示斑马线效果。
                collapsible: true,	//定义是否显示可折叠按钮。
                rownumbers: true,//如果为true，则显示一个行号列。
                nowrap: true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
                remoteSort: false,//定义从服务器对数据进行排序。
                toolbar: "#tb",
                loading: true,//显示载入状态。
                loadMsg: '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
                pageNumber: 1,// 在设置分页属性的时候初始化页码。
                pageSize: 20,// 在设置分页属性的时候初始化页面大小。
                pageList: [10, 20, 50, 100, 200],//在设置分页属性的时候 初始化页面大小选择列表。
                border: false,
                autoHighlightColumn: true,
                enableHeaderContextMenu: true,
                enableHeaderClickMenu: true,
                rowTooltip: false, //是否启用行数据的 tooltip 功能，若该属性为true，则设置在columns中的tooltip会自动失效
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

        function searchboxFuc(value, name) {
            g_searchType = name;
            g_searchValue = value;
            var projectIDSelect = $('#projectIDSelect').combobox("getValue");
            var startDate = $('#s_startDate').datebox('getValue');
            var endDate = $('#s_endDate').datebox('getValue');
            var projectID = $('#projectIDSelect').combobox("getValue", projectID);
            if ((startDate == '' && endDate != '') || (startDate != '' && endDate == '')) {
                $.messager.alert('温馨提示', '请准确输入操作开始/结束时间');
            } else if (startDate > endDate) {
                $.messager.alert('警告', '结束时间不能早于开始时间!');
            } else {
                $('#dg').datagrid('load', {
                    "projectID": projectID,
                    "searchType": name,
                    "searchValue": value,
                    "startDate": startDate,
                    "endDate": endDate
                });
            }
        }

        obj = {
            // 查询
            find: function () {
                $("#table").datagrid('load', {
                    user: $("#user").val(),
                    date: $.trim($("#dd").val())
                })
            },
            // 添加
            addBox: function () {
                window.parent.openTab("设备新增", "/swylsb/ylsbAdd.jsp", 'icon-shebei');
            },
            // 编辑
            edit: function (id) {
                // 清空表单内容
                $('#editForm').form('clear');
                var rows = $("#dg").datagrid("getRows");
                var row = rows[id];//index为行号
                // $('#parkAb').combobox('setValue',row.project.park.parkAb);
                $('#parkAb').combobox('setText', row.project.park.parkAb);
                $('#projectName').combobox('setValue', row.project.projectID);
                $('#projectName').combobox('setText', row.project.projectName);
                $('#sbStatus').combobox('setValue', row.sblx.typeID);
                $('#sbStatus').combobox('setText', row.sblx.typeName);
                $('#jyjbStatus').combobox('setValue', row.jyjb.typeID);
                $('#jyjbStatus').combobox('setText', row.jyjb.typeName);
                $('#xssyStatus').combobox('setValue', row.xuyao.typeID);
                $('#xssyStatus').combobox('setText', row.xuyao.typeName);
                $('#editForm').form('load', {
                    ID: row.ID,
                    // parkAb: row.project.park.parkAb,
                    // projectID: row.project.projectName,
                    sbID: row.sbID,
                    sbName: row.sbName,
                    sbClass: row.sbClass,
                    // sbStatus: row.sblx.typeName,
                    bzlx: row.bzlx,
                    yyqk: row.yyqk,
                    // jyjbStatus: row.jyjb.typeName,
                    gydw: row.gydw,
                    azdw: row.azdw,
                    // xssyStatus: row.xuyao.typeName,
                    sbsl: row.sbsl,
                    fzrUser: row.fzrUser,
                    startDate: row.startDate,
                    completeDate: row.completeDate,
                    approachDate: row.approachDate,
                    sbDate: row.sbDate
                });
                $("#editBox").dialog({
                    closed: false
                })
            },
            // 更新
            update: function () {
                $("#editForm").form('submit', {
                    url: '${pageContext.request.contextPath}/swylsb/updateSwylsb',
                    onSubmit: function () {
                        return $(this).form('validate')
                    },
                    success: function (result) {
                        var result = eval('(' + result + ')');
                        if (result.success) {
                            $.messager.alert("系统提示", "保存成功！");
                            $("#editBox").dialog("close");
                            $("#dg").datagrid("reload");
                        } else {
                            $.messager.alert("系统提示", "保存失败！");
                        }
                    }
                })
            },
            // 查阅
            look: function () {
                $("#lookTail").dialog({
                    closed: false
                })
            },
            // 求总
            sum: function () {
                $("#addForm").form('submit', {
                    url: "",
                    onSubmit: function () {
                        return $(this).form('validate')
                    },
                    success: function (data) {
                        var name = $("#name").val();
                        var pass = $("#pass").val();
                        var time = $("#time").datebox('getValue');
                        var part = $("#part01").combotree('getValue');
                        var id = parseInt(Math.random() * 100000);
                        $("#table").datagrid('insertRow', {
                            index: 1,
                            row: {
                                id: id,
                                name: name,
                                pass: pass,
                                time: time,
                                part: part
                            }
                        });
                        $("#addBox").dialog({
                            closed: true
                        });
                        $.messager.show({
                            title: '提示',
                            msg: '信息保存成功'
                        })
                    }
                })
            },
            // 删除多个
            del: function () {
                var rows = $("#table").datagrid("getSelections");
                if (rows.length > 0) {
                    $.messager.confirm('确定删除', '你确定要删除你选择的记录吗？', function (flg) {
                        if (flg) {
                            var ids = [];
                            for (i = 0; i < rows.length; i++) {
                                ids.push(rows[i].id);
                            }
                            var num = ids.length;
                            $.ajax({
                                type: 'post',
                                url: "",
                                data: {
                                    ids: ids.join(',')
                                },
                                beforesend: function () {
                                    $("#table").datagrid('loading');

                                },
                                success: function (data) {
                                    if (data) {
                                        $("#table").datagrid('loaded');
                                        $("#table").datagrid('load');
                                        $("#table").datagrid('unselectAll');
                                        $.messager.show({
                                            title: '提示',
                                            msg: num + '个用户被删除'
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
            },
            // 删除一个
            delOne: function (id) {
                $.messager.confirm('提示信息', '是否删除所选择记录', function (flg) {
                    if (flg) {
                        $.ajax({
                            type: 'post',
                            url: '${pageContext.request.contextPath}/swylsb/swylsbDelete',
                            data: {
                                ids: id
                            },
                            beforesend: function () {
                                $("#dg").datagrid('loading');
                            },
                            success: function (data) {
                                if (data) {
                                    $("#dg").datagrid("loaded");
                                    $("#dg").datagrid("load");
                                    $("#dg").datagrid("unselectRow");
                                    $.messager.alert("系统提示", "删除成功！");
                                } else {
                                    $.messager.alert("系统提示", "删除失败！");
                                }
                            }
                        })
                    }
                })
            }
        }

        /**
         * 重置
         */
        function resetSearch() {
            $('#searchForm').form('clear');
            $('#dg').datagrid('load', {
                "projectID": null,
                "searchType": '',
                "searchValue": '',
                "startDate": '',
                "endDate": ''
            });
        }
    </script>
</head>
<body style="margin: 1px">
<table id="dg" title="外购室外游乐设备总体情况跟进表"></table>
<div id="tb">
    <form id="searchForm">
        <div>
            <label>操作时间:</label>&nbsp;
            <input type="text" id="s_startDate" class="easyui-datebox" style="width:120px" editable="false"/> --
            <input type="text" id="s_endDate" class="easyui-datebox" style="width:120px" editable="false"/>
            <label>公园简称:</label>&nbsp;<input type="text" class="easyui-combobox" id="parkIDSelect" style="width:80px"/>
            <label>项目名称:</label>&nbsp;<input type="text" class="easyui-combobox" id="projectIDSelect"
                                             style="width:120px"/>
            <label>搜索类型:</label>&nbsp;
            <input id="searchboxInput" class="easyui-searchbox" style="width:250px"
                   data-options="searcher:searchboxFuc,prompt:'请输入搜索值',menu:'#mm'"></input>
            <div id="mm" style="width:120px">
                <div data-options="name:'sbName',iconCls:'icon-ok'">设备名称</div>
                <div data-options="name:'gydw'">供应单位</div>
                <div data-options="name:'sbClass'">设备型号</div>
            </div>
            <a href="javascript:resetSearch()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置</a>
            <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="obj.addBox()">新增</a>
        </div>
    </form>
</div>

<!--//新增弹出框-->
<div id="editBox" class="easyui-dialog" title="编辑" resizable="true" style="width: 900px;height: 400px" closed="true">
    <form id="editForm" method="post">
        <table class="TailTable">
            <tr align="center">
                <td colspan="6" style="text-align: center;font-weight: bold;font-size: 18px;">设备基本信息修改</td>
                <input id="ID" name="ID" type="hidden">
            </tr>
            <tr>
                <td class="TailLabel">乐园简称：</td>
                <td align="center" valign="middle"><input type="text" class="easyui-combobox" id="parkAb" name="parkAb"
                                                          data-options="required:true"></td>
                <td class="TailLabel">项目名称：</td>
                <td align="center" valign="middle"><input type="text" class="easyui-combobox" editable="false"
                                                          name="projectID" id="projectName"
                                                          data-options="required:true"></td>
                <td class="TailLabel">设备序号：</td>
                <td align="center" valign="middle"><input type="text" class="easyui-textbox easyui-validatebox"
                                                          name="sbID" id="sbID" data-options="required:true"></td>
            </tr>
            <tr>
                <td class="TailLabel">设备名称：</td>
                <td align="center" valign="middle"><input type="text" class="easyui-textbox easyui-validatebox"
                                                          name="sbName" id="sbName" data-options="required:true"></td>
                <td class="TailLabel">设备型号：</td>
                <td align="center" valign="middle"><input type="text" class="easyui-textbox easyui-validatebox"
                                                          id="sbClass" name="sbClass"/></td>
                <td class="TailLabel">设备类型：</td>
                <td align="center" valign="middle"><input type="text" id="sbStatus" name="sbStatus"
                                                          class="easyui-combobox easyui-validatebox" editable="false"
                                                          data-options="
                                                          valueField: 'typeID',
                                                          textField: 'typeName',
                                                          url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=sblx',
                                                          "/>
                </td>
            </tr>
            <tr>
                <td class="TailLabel">包装类型：</td>
                <td align="center" valign="middle"><input type="text" id="bzlx" name="bzlx"
                                                          class="easyui-textbox easyui-validatebox"></td>
                <td class="TailLabel">沿用情况：</td>
                <td align="center" valign="middle"><input type="text" id="yyqk" name="yyqk"
                                                          class="easyui-textbox easyui-validatebox"></td>
                <td class="TailLabel">检验级别：</td>
                <td align="center" valign="middle"><input type="text" id="jyjbStatus" name="jyjbStatus"
                                                          class="easyui-textbox easyui-combobox" editable="false"
                                                          data-options="
                                                          panelHeight:'auto',
                                                          valueField: 'typeID',
                                                          textField: 'typeName',
                                                          url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=jyjb',
                                                          onLoadSuccess: function(rec){
                                                               $(this).combobox('setValue',rec[0].typeID);
                                                          }"/>
                </td>
            </tr>
            <tr>
                <td class="TailLabel">供应单位：</td>
                <td align="center" valign="middle"><input type="text" id="gydw" name="gydw"
                                                          class="easyui-textbox easyui-validatebox">
                </td>
                <td class="TailLabel">安装单位：</td>
                <td align="center" valign="middle"><input type="text" id="azdw" name="azdw"
                                                          class="easyui-textbox easyui-validatebox">
                </td>
                <td class="TailLabel">型式试验：</td>
                <td align="center" valign="middle"><input type="text" id="xssyStatus" name="xssyStatus"
                                                          class="easyui-textbox easyui-combobox" editable="false"
                                                          data-options="
                                                          panelHeight:'auto',
                                                          valueField: 'typeID',
                                                          textField: 'typeName',
                                                          url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=xuyao',
                                                          onLoadSuccess: function(rec){
                                                               $(this).combobox('setValue',rec[0].typeID);
                                                          }"/>
                </td>
            </tr>
            <tr>
                <td class="TailLabel">设备数量：</td>
                <td align="center" valign="middle"><input type="text" id="sbsl" name="sbsl"
                                                          class="easyui-textbox easyui-validatebox"/></td>
                <td class="TailLabel"> 负责人：</td>
                <td align="center" valign="middle"><input type="text" id="fzrUser" name="fzrUser"
                                                          class="easyui-textbox easyui-validatebox"/></td>
                <td class="TailLabel"></td>
                <td align="center" valign="middle"></td>
            </tr>
            <tr>
                <td class="TailLabel">总工办计划基础开始日期：</td>
                <td align="center" valign="middle"><input type="text" id="startDate" name="startDate"
                                                          class="easyui-textbox easyui-datebox"/></td>
                <td class="TailLabel"> 总工办计划基础完成日期：</td>
                <td align="center" valign="middle"><input type="text" id="completeDate" name="completeDate"
                                                          class="easyui-textbox easyui-datebox"/></td>
                <td class="TailLabel">总工办计划设备进场日期</td>
                <td align="center" valign="middle"><input type="text" id="approachDate" name="approachDate"
                                                          class="easyui-textbox easyui-datebox"/></td>
            </tr>
        </table>
        <div class="forSubmint"><a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="obj.update()">提交</a>
        </div>
    </form>
</div>
</body>
</html>