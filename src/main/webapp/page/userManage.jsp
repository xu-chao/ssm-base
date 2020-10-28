<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户管理</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/MD5/MD5.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/autoPinyin.js"></script>
    <script type="text/javascript">
        var statusFlag;
        var sessionPid = ${sessionDept.dept.pid};
        var deptValue_session = ${sessionDept.dept.deptID };

        function formatAction(val, row) {
            statusFlag = row.status;
            if (statusFlag == 1) {
                statusFlag = 0;
                return "<a href='javascript:freezeUser(\"" + row.id + "\"," + statusFlag + ")'>冻结</a>";
            } else {
                statusFlag = 1;
                return "<a href='javascript:freezeUser(\"" + row.id + "\"," + statusFlag + ")'>解冻</a>";
            }
        }

        function freezeUser(id, status) {
            var data = {
                id: id,
                status: status
            }
            $.ajax({
                url: "${pageContext.request.contextPath}/user/freezeUser",
                type: "post",
                data: data,
                dataType: "json",
                success: function (result) {
                    if (result.success) {
                        if (status == 1) {
                            $.messager.alert('系统提示', '解冻成功', 'info', function () {
                                $('#dg').datagrid('reload');
                            });
                        } else {
                            $.messager.alert('系统提示', '冻结成功', 'info', function () {
                                $('#dg').datagrid('reload');
                            });
                        }
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
        }

        function searchUser() {
            $("#dg").datagrid('load', {
                "id": $("#s_id").val(),
                "deptPid": sessionPid,
            });
        }

        function deleteUser() {
            var selectRows = $("#dg").datagrid("getSelections");
            if (selectRows.length == 0) {
                $.messager.alert("系统提示", "请选择要删除的数据！");
                return;
            }
            var strIds = [];
            for (var i = 0; i < selectRows.length; i++) {
                strIds.push(selectRows[i].id);
            }
            var ids = strIds.join(",");
            $.messager.confirm("系统提示", "您确定要删除这<font color=red>" + selectRows.length + "</font>条数据吗?", function (r) {
                if (r) {
                    $.post("${pageContext.request.contextPath}/user/deleteUser.action", {ids: ids}, function (result) {
                        if (result.success) {
                            $.messager.alert("系统提示", "数据已经成功删除！");
                            $("#dg").datagrid("reload");
                        } else {
                            $.messager.alert("系统提示", "数据删除失败，请联系管理员！");
                        }
                    }, "json");
                }
            });
        }

        function openUserAddDiglog() {
            $("#dlg").dialog("open").dialog("setTitle", "添加用户信息");
            $("#flag").val(1);
            $("#id").attr("readonly", false);
        }

        function openUserModifyDiglog() {
            resetValue();

            var selectRows = $("#dg").datagrid("getSelections");
            if (selectRows.length != 1) {
                $.messager.alert("系统提示", "请选择一条要编辑的数据！");
                return;
            }

            var row = selectRows[0];
            if (row.userDept!==null){
                $('#dept_1').combotree('setValue', parseInt(row.userDept.deptID));
            }
            if (row.userPark!==null) $('#inputtable').combobox('setValue', row.userPark.parkID);
            $("#dlg").dialog("open").dialog("setTitle", "编辑用户信息");
            $("#fm").form("load", row);
            $("#flag").val(2);
            $("#id").attr("readonly", true);
        }


        function checkData() {
            if ($("#id").val() == '') {
                $.messager.alert("系统系统", "请输入用户名！");
                $("#id").focus();
                return;
            }
            var flag = $("#flag").val();
            if (flag == 1) {
                $.post("${pageContext.request.contextPath}/user/existUserID.action", {userID: $("#id").val()}, function (result) {
                    if (result.success) {
                        $.messager.alert("系统系统", "该用户名已存在，请更换下！");
                        $("#id").focus();
                    } else {
                        saveUser();
                    }
                }, "json");
            } else {
                updateUser();
            }
        }

        function updateUser() {
            var id = $('#id').val();
            var password = $('#password').val();
            var firstName = $('#firstName').val();
            var lastName = $('#lastName').val();
            var email = $('#email').val();
            var phone = $('#phone').val();
            var pictureID = $('#pictureID').val();
            var position = $('#position').val();
            var certificate = $('#certificate').val();
            var parkID = $('#inputtable').combobox('getValue')
            var deptID = $('#dept_1').combobox('getValue')
            password = passwordEncrying(password);
            var data = {
                "id" : id,
                "password" : password,
                "firstName" : firstName,
                "lastName" : lastName,
                "email" : email,
                "phone" : phone,
                "pictureID" : pictureID,
                "position" : position,
                "certificate" : certificate,
                "parkID" : parkID,
                "deptID" : deptID,
            }
            $.ajax({
                type: "POST",
                url:"${pageContext.request.contextPath}/user/updateUser",
                dataType:"json",
                contentType:"application/json",
                data:JSON.stringify(data),
                success:function(result){
                    if (result.success) {
                        $.messager.alert("系统系统", "保存成功！");
                        resetValue();
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");
                    } else {
                        $.messager.alert("系统提示",result.msg);
                        return;
                    }
                }
            });
        }

        function saveUser() {
            var id = $('#id').val();
            var password = $('#password').val();
            var firstName = $('#firstName').val();
            var lastName = $('#lastName').val();
            var email = $('#email').val();
            var phone = $('#phone').val();
            var pictureID = $('#pictureID').val();
            var position = $('#position').val();
            var certificate = $('#certificate').val();
            var parkID = $('#inputtable').combobox('getValue')
            var deptID = $('#dept_1').combobox('getValue')
            password = passwordEncrying(password);
            var data = {
                "id" : id,
                "password" : password,
                "firstName" : firstName,
                "lastName" : lastName,
                "email" : email,
                "phone" : phone,
                "pictureID" : pictureID,
                "position" : position,
                "certificate" : certificate,
                "parkID" : parkID,
                "deptID" : deptID,
            }
            $.ajax({
                type: "POST",
                url:"${pageContext.request.contextPath}/user/userSave",
                dataType:"json",
                contentType:"application/json",
                data:JSON.stringify(data),
                success:function(result){
                    if (result.success) {
                        $.messager.alert("系统系统", "保存成功！");
                        resetValue();
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");
                    } else {
                        $.messager.alert("系统提示",result.msg);
                        return;
                    }
                }
            });
        }

        function passwordEncrying(password){
            var str1 = $.md5(password);
            return str1;
        }

        function resetValue() {
            $("#id").val("");
            $("#password").val("");
            $("#firstName").val("");
            $("#lastName").val("");
            $("#email").val("");
            $('#phone').val("");
            $('#pictureID').val("");
            $('#position').val("");
            $('#certificate').val("");
            $('#inputtable').combobox('clear');
            $('#dept_1').combotree('clear');
        }

        function closeUserDialog() {
            $("#dlg").dialog("close");
            resetValue();
        }

        $(document).ready(function () {
            $('#dg').datagrid({
                remoteSort: false,
                nowrap: false,
                url: '${pageContext.request.contextPath}/user/userPage',
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'id', title: '用户名/工号', width: 100, align: 'center'},
                    {
                        field: 'password', title: '密码', width: 100, align: 'center',
                        formatter: function (value) {
                            return "*********";
                        }
                    },
                    {field: 'firstName', title: '姓', width: 100, align: 'center'},
                    {field: 'lastName', title: '名', width: 100, align: 'center'},
                    {field: 'email', title: '邮箱', width: 100, align: 'center'},
                    {
                        field: 'userDept', title: '所属部门', width: 100, align: 'center',
                        formatter: function (value) {
                            if (value==null) {
                                return "未分配部门"
                            }else {
                                return value.deptName;
                            }
                        }
                    },
                    {
                        field: 'userPark', title: '所属公园', width: 100, align: 'center',
                        formatter: function (value) {
                            if (value==null) {
                                return "未分配公园"
                            }else {
                                return value.parkName;
                            }
                        }
                    },
                    {field: 'status', title: '操作', width: 100, align: 'center', formatter: formatAction}
                ]],
                queryParams:{deptPid:sessionPid},
                pageSize: 20,
                sortName: 'operation_Time',
                sortOrder: 'desc',
            });

            // 自动补全
            $('#inputtable').combobox(
                {
                    prompt : '输入关键字后自动搜索',
                    url : '${pageContext.request.contextPath}/park/searchAllPark',// _url,_value已经在各自的js文件中定义
                    valueField : 'parkID',
                    textField : 'parkName',
                    panelHeight : 'auto',
                    panelMaxHeight : 150,
                    editable : true,
                    onLoadSuccess: function () { //加载完成后,val[0]写死设置选中第一项
                        var val = $(this).combobox("getData");
                        for (var item in val[0]) {
                            if (item == "parkID") {
                                $(this).combobox("select", val[0][item]);

                            }
                        }
                    }
                });

            $('#dept_1').combotree(
                {
                    // prompt: '输入关键字后自动搜索',
                    // value: deptValue_session,
                    panelHeight : '250',
                    required: true,
                    missingMessage: '部门名称不能为空!',
                    // mode: 'remote',
                    url: '${pageContext.request.contextPath}/dept/selectChild?deptID=' + sessionPid,
                    onSelect: function (record) {
                        var deptID = record.id;

                    }
                });
        })
    </script>
</head>
<body style="margin: 1px">
<table id="dg" title="用户管理"
       fitColumns="true" pagination="true" rownumbers="true"
       fit="true" toolbar="#tb">
</table>
<div id="tb">
    <div>
        <a href="javascript:openUserAddDiglog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
        <a href="javascript:openUserModifyDiglog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
        <a href="javascript:deleteUser()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
    </div>
    <div>
        &nbsp;用户名&nbsp;<input type="text" id="s_id" size="20" onkeydown="if(event.keyCode==13) searchUser()"/>
        <a href="javascript:searchUser()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 620px;height: 350px;padding: 10px 20px" closed="true"
     buttons="#dlg-buttons">

    <form id="fm" method="post">
        <table cellpadding="8px">
            <tr>
                <td>用户名：</td>
                <td>
                    <input type="text" id="id" name="id" class="easyui-validatebox" required="true"/>
                </td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>密码：</td>
                <td>
                    <input type="password" id="password" name="password" class="easyui-validatebox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>姓：</td>
                <td>
                    <input type="text" id="firstName" name="firstName" class="easyui-validatebox" required="true"/>
                </td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>名：</td>
                <td>
                    <input type="text" id="lastName" name="lastName" class="easyui-validatebox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>邮箱：</td>
                <td >
                    <input type="text" id="email" name="email" class="easyui-validatebox"
                           validType="email" required="true"/>
                    <input type="hidden" id="flag" name="flag"/>
                </td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>电话号码：</td>
                <td >
                    <input type="text" id="phone" name="phone" class="easyui-validatebox" required="true"/>
                </td>
            </tr>
            <tr>

                <td>头像：</td>
                <td >
                    <input type="text" id="pictureID" name="pictureID" class="easyui-validatebox" />
                </td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>地址：</td>
                <td >
                    <input type="text" id="position" name="position" class="easyui-validatebox" />
                </td>
            </tr>

            <tr>
                <td valign="top">身份证书：</td>
                <td>
                    <input type="text" id="certificate" name="certificate" class="easyui-validatebox" />
                </td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>所属公园：</td>
                <td><input name="parkID" class="easyui-combobox" id="inputtable" /></td>
            </tr>
            <tr>
                <td>部门：</td>
                <td>
                    <input id="dept_1" class="easyui-combotree"  required="true" data-options="panelHeight:'auto'">
                </td>
            </tr>
        </table>
    </form>

</div>

<div id="dlg-buttons">
    <a href="javascript:checkData()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeUserDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
</body>
</html>