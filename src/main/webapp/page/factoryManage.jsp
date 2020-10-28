<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="keywords" content="jquery,ui,easy,easyui,web">
    <meta name="description" content="easyui help you build your web page easily!">
    <title>jQuery EasyUI Demo</title>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/autoPinyin.js"></script>
    <script>
        var pageContext = "${pageContext.request.contextPath}";
        //根据用户部门获取用户的中心
        var deptValue_session = ${sessionDept.dept.deptID };
        var sessionPid = ${sessionDept.dept.pid};

        $(document).ready(function () {
            $('#dept_2').combotree(
                {
                    // prompt: '输入关键字后自动搜索',
                    // value: deptValue_session,
                    required: true,
                    missingMessage: '部门名称不能为空!',
                    url: '${pageContext.request.contextPath}/dept/selectChild?deptID=' + sessionPid,
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
                url: '${pageContext.request.contextPath}/equit/equitPage',
                title: "申请单管理",
                fit: true,
                fitColumns: true,
                rownumbers: true,
                toolbar: true,
                pagination: true,
                singleSelect: true,
                toolbar: "#tb",
                pageList: [5, 10, 20, 50],//可以设置每页记录条数的列表
                detailFormatter: function (index, row) {
                    return '<div class="ddv" style="padding:5px 0"></div>';
                },
                onExpandRow: function (index, row) {
                    var ddv = $(this).datagrid('getRowDetail', index).find('div.ddv');
                    ddv.panel({
                        border: false,
                        cache: true,
                        href: '${pageContext.request.contextPath}/equit/getEquitById?id=' + row.id,
                        onLoad: function () {
                            $('#dg').datagrid('fixDetailRowHeight', index);
                            $('#dg').datagrid('selectRow', index);
                            $('#dg').datagrid('getRowDetail', index).find('form').form('load', row);
                        }
                    });
                    $('#dg').datagrid('fixDetailRowHeight', index);
                },
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'id', title: '单号', width: 150, align: 'center'},
                    {field: 'state', title: '审核状态', width: 100, align: 'center'},
                    {field: 'processInstanceId', title: '流程实例Id', width: 100, align: 'center'},
                    {
                        field: 'action', title: '操作', width: 100, align: 'center',
                        formatter:
                            function (value, row, index) {
                                if (row.state == '未提交') {
                                    return "<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" onclick=\"$('#dlg3').dialog('open')\">提交申请</a>";
                                    // return "<a href='javascript:startApply(" + '"' + row.id + '"' + ")'>提交申请</a>";
                                } else if (row.state == '审核通过' || row.state == '审核未通过') {
                                    return "<a href='javascript:openListCommentDialog(" + row.processInstanceId + ")'>查看历史批注</a>";
                                }
                            }
                    }
                ]]
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
        // $(function () {
        //     var pager = $('#dg').datagrid('getPager');	// get the pager of datagrid
        //     pager.pagination({
        //
        //         buttons: [{
        //             text: "搜索申请单",
        //             iconCls: 'icon-search',
        //             handler: function () {
        //                 alert('search');
        //             }
        //         }, {
        //             text: "新增申请单",
        //             iconCls: 'icon-add',
        //             handler: function () {
        //                 $("#dlg").dialog("open").dialog("setTitle", "添加申请单信息");
        //             }
        //         }],
        //         onBeforeRefresh: function () {
        //             alert('before refresh');
        //             return true;
        //         }
        //     });
        // });
        function saveLeave() {
            $("#fm").form("submit", {
                url: '${pageContext.request.contextPath}/equit/addEquit',
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统系统", "保存成功！");
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");
                        resetValue();
                    } else {
                        $.messager.alert("系统系统", "保存失败！");
                        return;
                    }
                }
            });
        }

        function closeLeaveDialog() {
            $("#dlg").dialog("close");
            resetValue();
        }

        function resetValue() {
            $("#EID").val("");
            $("#EType").val("");
            $("#NID").val("");
            $("#cityIDSelect").val("");
            $("#parkIDSelect").val("");
            $("#projectIDSelect").val("");
            $("#ESysName").val("");
            $("#ECode").val("");
            $("#EProduct").val("");
            $("#ESpecs").val("");
            $("#EUnit").val("");
            $("#EMount").val("");
            $("#ENote").val("");
            $("#ERMount").val("");
            $("#ETemp").val("");
            $("#EPlan").val("");
            $("#EIsRun").val("");
            $("#EPType").val("");
            $("#ECompany").val("");
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
                url: "${pageContext.request.contextPath}/equit/startApply",
                data: userTask,
                dataType: 'json',
                type: 'post',
                success: function (rtn) {
                    if (rtn.success) {
                        $.messager.alert("系统提示", "运维申请提交成功，目前审核中，请耐心等待！");
                        $("#dg").datagrid("reload");
                        closeQuestionDialog();
                    } else {
                        $.messager.alert("系统提示", "运维申请提交失败，请联系管理员！");
                    }
                }
            });

        }

        function opensqd() {
            $("#dlg").dialog("open").dialog("setTitle", "添加申请单信息");
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
    <style type="text/css">
        table.gridtable {
            font-family: verdana, arial, sans-serif;
            font-size: 11px;
            color: #333333;
            border-width: 1px;
            border-color: #666666;
            border-collapse: collapse;
        }

        table.gridtable th {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #666666;
            background-color: #dedede;
        }

        table.gridtable td {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #666666;
            background-color: #ffffff;
        }
    </style>
</head>
<body>
<table id="dg" class="easyui-datagrid">
</table>
<div id="tb">
    <a href="javascript:opensqd()" class="easyui-linkbutton" iconCls="icon-add"
       plain="true">新增申请单</a>
</div>
<span>Item ID:</span>
<%--<input id="itemid" style="line-height:26px;border:1px solid #ccc">--%>
<%--<span>Product ID:</span>--%>
<%--<input id="productid" style="line-height:26px;border:1px solid #ccc">--%>
<%--<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">Search</a>--%>
<div id="dlg3" class="easyui-window" title="选择提交人" style="width: 360px;height: 180px;" data-options="closed:true"
     align="center">
    <form id="editForm">
        <table>
            <tr>
                <td>部门：</td>
                <td>
                    <input id="dept_2" class="easyui-combotree" style="width:200px;">
                </td>
            </tr>
            <tr>
                <div class="datagrid-toolbar"></div>
            </tr>
            <tr>
                <td>提交审批人：</td>
                <td>
                    <select id="QuestionAdviser_2"  type="text" name="factoryPurchase"
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
<div id="dlg" class="easyui-dialog" style="width: 620px;height: 480px;padding: 10px 20px" closed="true"
     buttons="#dlg-buttons">
    <form id="fm" method="post" enctype="multipart/form-data">
        <table style="margin:5px;height:70px;">
            <tr>
                <td>序号：</td>
                <td>
                    <input class="easyui-validatebox" size="50" maxlength="40" type="text" id="EID" name="EID"
                           data-options="required:true"/>
                </td>
            </tr>
            <%--            <tr>--%>
            <%--                <td>日期：</td>--%>
            <%--                <td>--%>
            <%--                    <input type="date" id="ApplicantData" name="ApplicantData" class="easyui-validatebox"--%>
            <%--                           data-options="required:true"/>--%>
            <%--                </td>--%>
            <%--            </tr>--%>
            <tr>
                <td>类型：</td>
                <td>
                    <input type="text" id="EType" name="EType" size="20" maxlength="20" class="easyui-validatebox"
                           data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>采购单号：</td>
                <td>
                    <input type="text" id="NID" name="NID" size="50" maxlength="40" class="easyui-validatebox"
                           data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>工程名称：</td>
                <td>
                    <input type="text" id="cityIDSelect" name="EPName" size="50" maxlength="40" class="easyui-combobox"
                           data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>项目名称：</td>
                <td>
                    <input type="text" id="parkIDSelect" name="EProjectName" size="50" maxlength="40"
                           class="easyui-combobox" data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>子项目名称：</td>
                <td>
                    <input type="text" id="projectIDSelect" name="ESubName" size="50" maxlength="40" class="easyui-combobox"
                           data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>系统名称：</td>
                <td>
                    <input type="text" id="ESysName" name="ESysName" size="50" maxlength="40" class="easyui-validatebox"
                           data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>存货编号：</td>
                <td>
                    <input type="text" id="ECode" name="ECode" size="50" maxlength="40" class="easyui-validatebox"
                           data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>物品名称：</td>
                <td>
                    <input type="text" id="EProduct" name="EProduct" size="50" maxlength="40" class="easyui-validatebox"
                           data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>规格型号\图号：</td>
                <td>
                    <input type="text" id="ESpecs" name="ESpecs" size="50" maxlength="40" class="easyui-validatebox"
                           data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>单位：</td>
                <td>
                    <input type="text" id="EUnit" name="EUnit" size="25" maxlength="20" class="easyui-validatebox"
                           data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>工艺数量：</td>
                <td>
                    <input type="number" id="EMount" name="EMount" size="50" maxlength="40" class="easyui-validatebox"
                           data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>备用：</td>
                <td>
                    <input type="text" id="ENote" name="ENote" size="50" maxlength="40" class="easyui-validatebox"
                           data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>实购数量：</td>
                <td>
                    <input type="number" id="ERMount" name="ERMount" size="50" maxlength="40" class="easyui-validatebox"
                           data-options="required:false"/>
                </td>
            </tr>
            <%--            <tr>--%>
            <%--                <td>申请人：</td>--%>
            <%--                <td>--%>
            <%--                    <input type="text" id="EApplicant" name="EApplicant" size="30" maxlength="20"--%>
            <%--                           class="easyui-validatebox" data-options="required:false"/>--%>
            <%--                </td>--%>
            <%--            </tr>--%>
            <tr>
                <td>临时备注：</td>
                <td>
                    <input type="text" id="ETemp" name="ETemp" size="50" maxlength="40" class="easyui-validatebox"
                           data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>计划备注：</td>
                <td>
                    <input type="text" id="EPlan" name="EPlan" size="50" maxlength="40" class="easyui-validatebox"
                           data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>发货标识：</td>
                <td>
                    <input type="text" id="EIsRun" name="EIsRun" size="30" maxlength="20" class="easyui-validatebox"
                           data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>加工类型：</td>
                <td>
                    <input type="text" id="EPType" name="EPType" size="30" maxlength="20" class="easyui-validatebox"
                           data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td valign="top">公司主体：</td>
                <td>
                    <%--                    <input type="hidden" name="userId" value="${currentMemberShip.userId}"/>--%>
                    <input type="hidden" name="state" value="未提交"/>
                    <input type="text" id="ECompany" name="ECompany" size="40" class="easyui-validatebox"
                           data-options="required:false"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:saveLeave()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeLeaveDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
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
</body>
</html>