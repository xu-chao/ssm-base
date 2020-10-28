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
    <script type="text/javascript">
        window.top["reload_ylsbDetail"] = function () {
            $("#dg").datagrid("reload");
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
                url: '${pageContext.request.contextPath}/swylsb/swylsbPage',
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'ID', title: 'ID', width: 100, align: 'center', sortable: true},
                    {
                        field: 'project',
                        title: '乐园简称',
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
                    {field: 'sbID', title: '设备序号', width: 100, align: 'center', sortable: true},
                    {
                        field: 'project',
                        title: '项目名称',
                        width: 80,
                        align: 'center',
                        formatter: function (value, row, index) {
                            if (row.project) {
                                return row.project.projectName;
                            } else {
                                return "获取不到该项目名称！";
                            }
                        }
                    },
                    {field: 'sbName', title: '设备名称', width: 100, align: 'center', sortable: true},
                    {field: 'gydw', title: '供应商', width: 100, align: 'center'},
                    {field: 'sbsl', title: '设备数量', width: 100, align: 'center'},
                    {
                        field: 'jyjbStatus',
                        title: '检验级别',
                        width: 100,
                        align: 'center',
                        formatter: function (value, row, index) {
                            if (row.jyjb) {
                                return row.jyjb.typeName;
                            } else {
                                return "获取不到该检验级别！";
                            }
                        }
                    }, {
                        field: 'azStatusType',
                        title: '基础条件',
                        width: 100,
                        align: 'center',
                        formatter: function (value, row, index) {
                            if (row.swylsbJd) {
                                return row.swylsbJd.azStatusType.typeName;
                            } else {
                                return "获取不到该基础条件！";
                            }
                        }
                    },
                    {
                        field: 'sbzt',
                        title: '设备总体情况简述',
                        width: 100,
                        align: 'center',
                        formatter: function (value, row, index) {
                            if (row.swylsbXq) {
                                return row.swylsbXq.sbzt;
                            } else {
                                return "获取不到该情况简述！";
                            }
                        }
                    },{
                        field: 'ztbz',
                        title: '备注情况',
                        width: 100,
                        align: 'center',
                        formatter: function (value, row, index) {
                            if (row.swylsbXq) {
                                return row.swylsbXq.ztbz;
                            } else {
                                return "获取不到该基础条件！";
                            }
                        }
                    },{
                        field: 'wtfk',
                        title: '问题反馈',
                        width: 100,
                        align: 'center',
                        formatter: function (value, row, index) {
                            if (row.swylsbXq) {
                                return row.swylsbXq.wtfk;
                            } else {
                                return "获取不到该基础条件！";
                            }
                        }
                    },{
                        field: 'yjsm',
                        title: '预警',
                        width: 100,
                        align: 'center',
                        formatter: function (value, row, index) {
                            if (row.swylsbXq) {
                                return row.swylsbXq.yjsm;
                            } else {
                                return "获取不到该基础条件！";
                            }
                        }
                    },
                    {
                        field: 'operation',
                        title: '操作',
                        width: 100,
                        align: 'center',
                        formatter: function (value, row, index) {
                            e = '<a  id="add" data-id="98" class=" operA"  onclick="obj.edit(\'' + row.id + '\')">编辑</a> ';
                            c = '<a  id="look"   onclick="obj.look(\'' + row.id + '\')">详情</a> ';
                            d = '<a  id="add" data-id="98" class=" operA01"  onclick="obj.delOne(\'' + row.id + '\')">删除</a> ';
                            return e + d + c;
                        }
                    }
                ]],
                // queryParams:{},
                pageSize: 20,
                sortName: 'operation_Time',
                sortOrder: 'desc',
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
            if ((startDate == '' && endDate != '') || (startDate != '' && endDate == '')) {
                $.messager.alert('温馨提示', '请准确输入操作开始/结束时间');
            } else if (startDate > endDate) {
                $.messager.alert('警告', '结束时间不能早于开始时间!');
            } else {
                $('#dg').datagrid('load', {
                    "searchType": name,
                    "searchValue": value,
                    "projectID": projectIDSelect,
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
                $('#editForm').form('load', {
                    id: id,
                    name: row.name,
                    pass: row.pass,
                    time: data[index].time
                });
                $("#part01").combotree('setValue', data[index].part);
            },
            look: function () {
                $("#lookTail").dialog({
                    closed: false

                })

            },
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


                        })
                        $("#addBox").dialog({
                            closed: true

                        })
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

            //删除一个
            delOne: function (id) {
                id = $("#table").datagrid('getSelected').id;
                $.messager.confirm('提示信息', '是否删除所选择记录', function (flg) {
                    if (flg) {
                        $.ajax({
                            type: 'post',
                            url: '',
                            data: {
                                ID: id
                            },
                            beforesend: function () {
                                $("#table").datagrid('loading');

                            },
                            success: function (data) {
                                if (data) {
                                    $("#table").datagrid("loaded");
                                    $("#table").datagrid("load");
                                    $("#table").datagrid("unselectRow");
                                    $.messager.show({
                                        title: '提示信息',
                                        msg: "信息删除成功"
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


            }
        }

        /**
         * 重置
         */
        function resetSearch() {
            $('#searchForm').form('clear');
        }
    </script>
</head>
<body style="margin: 1px">
<table id="dg" title="室外游乐设备总体情况" fit="true" fitColumns="true" pagination="true" rownumbers="true"
       toolbar="#tb"></table>
<div id="tb">
    <div>
        <label>操作时间:</label>&nbsp;
        <input type="text" id="s_startDate" class="easyui-datebox" style="width:150px" editable="false"/> --
        <input type="text" id="s_endDate" class="easyui-datebox" style="width:150px" editable="false"/>
        <label>公园简称:</label>&nbsp;<input type="text" class="easyui-combobox" id="parkIDSelect" style="width:180px"/>
        <label>项目名称:</label>&nbsp;<input type="text" class="easyui-combobox" id="projectIDSelect" style="width:180px"/>
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
</div>


<!--//新增弹出框-->
    <div id="editBox" class="easyui-dialog" resizable="true" style="width: 900px;height: 600px" closed="true">
    <form id="editForm" method="post">
        <table class="TailTable">
            <tr align="center">
                <td colspan="6" style="text-align: center;font-weight: bold;font-size: 18px;">设备基本信息修改</td>
            </tr>
            <tr>
                <td class="TailLabel">乐园简称：</td>
                <td align="center" valign="middle"><input type="text" class="easyui-combobox TailInput"
                                                          name="name" id="parkIDSelect"
                                                          data-options="required:true"></td>
                <td class="TailLabel">项目名称：</td>
                <td align="center" valign="middle"><input type="text" class="easyui-combobox TailInput" editable="false"
                                                          name="project.projectID" id="projectIDSelect"
                                                          data-options="required:true"></td>
                <td class="TailLabel">设备序号：</td>
                <td align="center" valign="middle"><input type="text" class="TailInput"
                                                          name="name"
                                                          data-options="required:true"></td>
            </tr>
            <tr>
                <td class="TailLabel">设备名称：</td>
                <td align="center" valign="middle"><input type="text" class="TailInput"
                                                          name="name"
                                                          data-options="required:true"></td>
                <td class="TailLabel">设备型号：</td>
                <td align="center" valign="middle"><input type="text" class="easyui-validatebox TailInput"
                                                          id="dateBox"/></td>
                <td class="TailLabel">设备类型：</td>
                <td align="center" valign="middle"><input type="text" id="sbStatus" name="sbStatus"
                                                          class="easyui-combobox TailInput" editable="false"
                /></td>

            </tr>
            <tr>
                <td class="TailLabel">包装类型：</td>
                <td align="center" valign="middle"><input type="text" class="easyui-validatebox TailInput"></td>
                <td class="TailLabel">沿用情况：</td>
                <td align="center" valign="middle"><input type="text" name="yyqk" class="easyui-validatebox TailInput">
                </td>
                <td class="TailLabel">检验级别：</td>
                <td align="center" valign="middle"><input type="text" name="jyjbStatus"
                                                          class="easyui-combobox TailInput" editable="false"
                ></td>

            </tr>
            <tr>
                <td class="TailLabel">供应单位：</td>
                <td align="center" valign="middle"><input type="text" name="gydw" class="easyui-validatebox TailInput">
                </td>
                <td class="TailLabel">安装单位：</td>
                <td align="center" valign="middle"><input type="text" name="azdw" class="easyui-validatebox TailInput">
                </td>
                <td class="TailLabel">型式试验：</td>
                <td align="center" valign="middle"><input type="text" name="xssyStatus"
                                                          class="easyui-combobox TailInput" editable="false"
                >
                </td>

            </tr>
            <tr>
                <td class="TailLabel">设备数量：</td>
                <td align="center" valign="middle"><input type="text" name="sbsl" class="easyui-validatebox TailInput"
                /></td>
                <td class="TailLabel"> 负责人：</td>
                <td align="center" valign="middle"><input type="text" name="fzrUser"
                                                          class="easyui-validatebox TailInput"/></td>
                <td class="TailLabel"></td>
                <td align="center" valign="middle"></td>

            </tr>
            <tr>
                <td class="TailLabel">总工办计划基础开始日期：</td>
                <td align="center" valign="middle"><input type="text" name="startDate"
                                                          class="easyui-datebox TailInput"/></td>
                <td class="TailLabel"> 总工办计划基础完成日期：</td>
                <td align="center" valign="middle"><input type="text" name="completeDate"
                                                          class="easyui-datebox TailInput"/></td>
                <td class="TailLabel">总工办计划设备进场日期</td>
                <td align="center" valign="middle"><input type="text" name="approachDate"
                                                          class="easyui-datebox TailInput"/></td>

            </tr>

        </table>
        <div class="forSubmint"><a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="obj.sum()">提交</a></div>

    </form>
</div>
</body>
</html>