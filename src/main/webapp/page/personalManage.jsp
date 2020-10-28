<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>个人中心</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/demo/demo.css">
    <style type="text/css">
        .table th {
            font-weight: bold;
        }

        .table th,
        .table td {
            padding: 8px;
            line-height: 20px;
        }

        .table td {
            text-align: center;
        }

        .table-border {
            border-top: 1px solid #ddd;
        }

        .table-border th,
        .table-border td {
            border-bottom: 1px solid #ddd;
        }

        .table-bordered {
            border: 1px solid #ddd;
            border-collapse: separate;
            *border-collapse: collapse;
            border-left: 0;
        }

        .table-bordered th,
        .table-bordered td {
            border-left: 1px solid #ddd;
        }

        .table-border.table-bordered {
            border-bottom: 0;
        }

        .table-striped tbody > tr:nth-child(odd) > td,
        .table-striped tbody > tr:nth-child(odd) > th {
            background-color: #f9f9f9;
        }
    </style>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.edatagrid.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/date.format/date.format.js"></script>
    <script type="text/javascript">
        // var firstNameFlag = true;
        // var lastNameFlag = true;
        // var emailFlag = true;
        var createTime;
        var lastLoginTime;
        var id = "${sessionInfo.userId}";

        $(function () {
            //设置导航栏
            $("#userDialog").dialog({
                title: "导航栏",
                width: 500,
                height: 400,
                fit: true,
                modal: false,
                noheader: true,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                draggable: true,
                closed: false,
                toolbar: [{
                    text: '资料编辑',
                    plain: true,
                    iconCls: 'icon-reload',
                    handler: function () {
                        openPersonalModifyDialog();
                    }
                }, '-', {
                    text: '修改密码',
                    plain: true,
                    iconCls: 'icon-password',
                    handler: function () {
                        openPasswordModifyDialog();
                    }
                }],
            });
            var createTime_session = '${sessionInfo.user.createTime }';
            var lastLoginTime_session = '${sessionInfo.user.lastLoginTime }';
            var createTime_GMT = dateToGMT(createTime_session);
            var lastLoginTime_GMT = dateToGMT(lastLoginTime_session);
            var createTime_now = new Date(createTime_GMT);
            var lastLoginTime_now = new Date(lastLoginTime_GMT);
            createTime = createTime_now.format("Y-m-d H:i:s");
            lastLoginTime = lastLoginTime_now.format("Y-m-d H:i:s");
            $('#createTime').text(createTime);
            $('#lastLoginTime').text(lastLoginTime);
        });

        <%--function editFirstName() {--%>
        <%--    if(firstNameFlag){--%>
        <%--        var obj = $("#firstName");--%>
        <%--        var oldVal = obj.text();--%>
        <%--        var input = "<input type='text' id='firstName' value='" + oldVal + "' />";--%>
        <%--        obj.text('');--%>
        <%--        obj.replaceWith(input);--%>
        <%--        $('#firstName').focus();--%>
        <%--        $('#firstName').blur(function(){--%>
        <%--            if(obj.val() != ''){--%>
        <%--                oldVal = obj.val();--%>
        <%--            }--%>
        <%--            obj.closest('td').text(oldVal);--%>
        <%--        });--%>
        <%--        firstNameFlag = false;--%>
        <%--    }else {--%>
        <%--        $.messager.alert('警告', '已经处于编辑状态！');--%>
        <%--    }--%>
        <%--};--%>

        <%--function saveFirstName() {--%>
        <%--    if(!firstNameFlag){--%>
        <%--        var obj = $("#firstName");--%>
        <%--        var oldVal = obj.val();--%>
        <%--        $.ajax({--%>
        <%--            url: '${pageContext.request.contextPath}/user/updateUser',--%>
        <%--            data: {id: id,firstName:oldVal},--%>
        <%--            dataType: 'json',--%>
        <%--            type: 'post',--%>
        <%--            success: function (result) {--%>
        <%--                console.log(result.success);--%>
        <%--                if(result.success){--%>
        <%--                    var output = "<span id='firstName'>" + oldVal + "</span>";--%>
        <%--                    obj.val('');--%>
        <%--                    obj.replaceWith(output);--%>
        <%--                    firstNameFlag = true;--%>
        <%--                    $.messager.alert("系统提示","保存成功！下次登录生效！");--%>
        <%--                }else {--%>
        <%--                    var output = "<span id='firstName'></span>";--%>
        <%--                    obj.val('');--%>
        <%--                    obj.replaceWith(output);--%>
        <%--                    firstNameFlag = true;--%>
        <%--                    $.messager.alert("系统提示","保存失败！");--%>
        <%--                    return;--%>
        <%--                }--%>
        <%--            }--%>
        <%--        });--%>
        <%--    }--%>
        <%--    else {--%>
        <%--       $.messager.alert('警告', '尚未编辑，不可保存！')--%>
        <%--    }--%>
        <%--};--%>

        <%--function editLastName() {--%>
        <%--    if(lastNameFlag){--%>
        <%--        var obj = $("#lastName");--%>
        <%--        var oldVal = obj.text();--%>
        <%--        var input = "<input type='text' id='lastName' value='" + oldVal + "' />";--%>
        <%--        obj.text('');--%>
        <%--        obj.replaceWith(input);--%>
        <%--        $('#lastName').focus();--%>
        <%--        $('#lastName').blur(function(){--%>
        <%--            if(obj.val() != ''){--%>
        <%--                oldVal = obj.val();--%>
        <%--            }--%>
        <%--            obj.closest('td').text(oldVal);--%>
        <%--        });--%>
        <%--        lastNameFlag = false;--%>
        <%--    }else {--%>
        <%--        $.messager.alert('警告', '已经处于编辑状态！');--%>
        <%--    }--%>
        <%--};--%>

        <%--function saveLastName() {--%>
        <%--    if(!lastNameFlag){--%>
        <%--        var obj = $("#lastName");--%>
        <%--        var oldVal = obj.val();--%>
        <%--        $.ajax({--%>
        <%--            url: '${pageContext.request.contextPath}/user/updateUser',--%>
        <%--            data: {id: id,lastName:oldVal},--%>
        <%--            dataType: 'json',--%>
        <%--            type: 'post',--%>
        <%--            success: function (result) {--%>
        <%--                console.log(result.success);--%>
        <%--                if(result.success){--%>
        <%--                    var output = "<span id='lastName'>" + oldVal + "</span>";--%>
        <%--                    obj.val('');--%>
        <%--                    obj.replaceWith(output);--%>
        <%--                    lastNameFlag = true;--%>
        <%--                    $.messager.alert("系统提示","保存成功！下次登录生效！");--%>
        <%--                }else {--%>
        <%--                    var output = "<span id='lastName'></span>";--%>
        <%--                    obj.val('');--%>
        <%--                    obj.replaceWith(output);--%>
        <%--                    lastNameFlag = true;--%>
        <%--                    $.messager.alert("系统提示","保存失败！");--%>
        <%--                    return;--%>
        <%--                }--%>
        <%--            }--%>
        <%--        });--%>
        <%--    }--%>
        <%--    else {--%>
        <%--        $.messager.alert('警告', '尚未编辑，不可保存！')--%>
        <%--    }--%>
        <%--};--%>

        <%--function editEmail() {--%>
        <%--    if(emailFlag){--%>
        <%--        var obj = $("#email");--%>
        <%--        var oldVal = obj.text();--%>
        <%--        var input = "<input type='text' id='email' value='" + oldVal + "' />";--%>
        <%--        obj.text('');--%>
        <%--        obj.replaceWith(input);--%>
        <%--        $('#email').focus();--%>
        <%--        $('#email').blur(function(){--%>
        <%--            if(obj.val() != ''){--%>
        <%--                oldVal = obj.val();--%>
        <%--            }--%>
        <%--            obj.closest('td').text(oldVal);--%>
        <%--        });--%>
        <%--        emailFlag = false;--%>
        <%--    }else {--%>
        <%--        $.messager.alert('警告', '已经处于编辑状态！');--%>
        <%--    }--%>
        <%--};--%>

        <%--function saveEmail() {--%>
        <%--    if(!emailFlag){--%>
        <%--        var obj = $("#email");--%>
        <%--        var oldVal = obj.val();--%>
        <%--        $.ajax({--%>
        <%--            url: '${pageContext.request.contextPath}/user/updateUser.action',--%>
        <%--            data: {id: id,email:oldVal},--%>
        <%--            dataType: 'json',--%>
        <%--            type: 'post',--%>
        <%--            success: function (result) {--%>
        <%--                console.log(result.success);--%>
        <%--                if(result.success){--%>
        <%--                    var output = "<span id='email'>" + oldVal + "</span>";--%>
        <%--                    obj.val('');--%>
        <%--                    obj.replaceWith(output);--%>
        <%--                    emailFlag = true;--%>
        <%--                    $.messager.alert("系统提示","保存成功！下次登录生效！");--%>
        <%--                }else {--%>
        <%--                    var output = "<span id='email'></span>";--%>
        <%--                    obj.val('');--%>
        <%--                    obj.replaceWith(output);--%>
        <%--                    emailFlag = true;--%>
        <%--                    $.messager.alert("系统提示","保存失败！");--%>
        <%--                    return;--%>
        <%--                }--%>
        <%--            }--%>
        <%--        });--%>
        <%--    } else {--%>
        <%--        $.messager.alert('警告', '尚未编辑，不可保存！')--%>
        <%--    }--%>
        <%--};--%>

        function openPasswordModifyDialog() {
            url = "${pageContext.request.contextPath}/user/modifyPassword?id=${sessionInfo.user.id}";
            $("#dlg").dialog("open").dialog("setTitle", "修改密码");
        }

        function modifyPassword() {
            $("#fm").form("submit", {
                url: url,
                onSubmit: function () {
                    var oldPassword = $("#oldPassword").val();
                    var newPassword = $("#newPassword").val();
                    var newPassword2 = $("#newPassword2").val();
                    if (!$(this).form("validate")) {
                        return false;
                    }
                    if (oldPassword != '${sessionInfo.user.password}') {
                        $.messager.alert("系统系统", "用户原密码输入错误！");
                        return false;
                    }
                    if (newPassword != newPassword2) {
                        $.messager.alert("系统系统", "确认密码输入错误！");
                        return false;
                    }
                    return true;
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统系统", "密码修改成功，下一次登录生效！");
                        resetValue();
                        $("#dlg").dialog("close");
                    } else {
                        $.messager.alert("系统系统", "密码修改失败，请联系管理员！");
                        return;
                    }
                }
            });
        }

        function resetValue() {
            $("#firstName").val("");
            $("#lastName").val("");
            $("#email").val("");
            $("#phone").val("");
        }

        function closePasswordModifyDialog() {
            resetValue();
            $("#dlg").dialog("close");
        }

        function openPersonalModifyDialog() {
            url = "";
            $("#dlgedit").dialog("open").dialog("setTitle", "个人资料修改--功能待完善");
        }

        function dateToGMT(strDate) {
            var dateStr = strDate.split(" ");
            var strGMT = dateStr[0] + " " + dateStr[1] + " " + dateStr[2] + " " + dateStr[5] + " " + dateStr[3] + " GMT+0800";
            var date = new Date(Date.parse(strGMT));
            return date;
        }

        function editUserInfo() {
            $("#userEdit").form("submit", {
                url: "${pageContext.request.contextPath}/user/updateUserInfo",
                onSubmit: function () {
                    if (!$(this).form("validate")) {
                        return false;
                    }
                    return true;
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统", "密码修改成功，下一次登录生效！");
                        resetValue();
                        $("#dlgedit").dialog("close");
                    } else {
                        $.messager.alert("系统", "密码修改失败，请联系管理员！");
                        return;
                    }
                }
            });
        }

        function editUserInfoClose() {
            $("#oldPassword").val("");
            $("#newPassword").val("");
            $("#newPassword2").val("");
            $("#dlgedit").dialog("close");
        }
    </script>
</head>
<body>
<div id="userDialog" style="padding: 20px;">
    <div style="width: 100%">
        <table id="systemTable" class="table table-border table-bordered table-striped" style="width: 100%">
            <tr>
                <td width="200">角色名称</td>
                <td width="350">
                    <span>${sessionInfo.group.name }</span>
                </td>
                <%--                <td width="100">--%>
                <%--                </td>--%>
            </tr>
            <tr>
                <td>姓</td>
                <td>
                    <span id="firstName">${sessionInfo.user.firstName }</span>
                </td>
                <%--                <td>--%>
                <%--                    <a href="javascript:editFirstName()" data-options="iconCls:'icon-edit'" class="easyui-linkbutton" >编辑</a>--%>
                <%--                    <a href="javascript:saveFirstName()" data-options="iconCls:'icon-save'" class="easyui-linkbutton" >保存</a>--%>
                <%--                </td>--%>
            </tr>
            <tr>
                <td>名</td>
                <td>
                    <span id="lastName">${sessionInfo.user.lastName }</span>
                </td>
                <%--                <td>--%>
                <%--                    <a href="javascript:editLastName()" data-options="iconCls:'icon-edit'" class="easyui-linkbutton" >编辑</a>--%>
                <%--                    <a href="javascript:saveLastName()" data-options="iconCls:'icon-save'" class="easyui-linkbutton" >保存</a>--%>
                <%--                </td>--%>
            </tr>
            <tr>
                <td>邮箱</td>
                <td>
                    <span id="email">${sessionInfo.user.email }</span>
                </td>
                <%--                <td>--%>
                <%--                    <a href="javascript:editEmail()" data-options="iconCls:'icon-edit'" class="easyui-linkbutton" >编辑</a>--%>
                <%--                    <a href="javascript:saveEmail()" data-options="iconCls:'icon-save'" class="easyui-linkbutton" >保存</a>--%>
                <%--                </td>--%>
            </tr>

            <tr>
                <td>创建时间</td>
                <td>
                    <span id="createTime"></span>
                    <%--                    <span>${sessionInfo.user.createTime }</span>--%>
                    <%--                    <span><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="createTime"/></span>--%>
                    <%--                    <c:set var="now" value="<%=new java.util.Date()%>" />--%>
                    <%--                    <p>日期格式化: <fmt:formatDate pattern="yyyy-MM-dd"--%>
                    <%--                                                  value="${now}" /></p>--%>
                </td>
                <%--                <td>--%>
                <%--                </td>--%>
            </tr>
            <tr>
                <td>最后登录时间</td>
                <td>
                    <span id="lastLoginTime"></span>
                    <%--                    <span>${sessionInfo.user.lastLoginTime }</span>--%>
                </td>
                <%--                <td>--%>
                <%--                </td>--%>
            </tr>
        </table>
    </div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 400px;height: 250px;padding: 10px 20px" closed="true"
     buttons="#dlg-buttons">
    <form id="fm" method="post">
        <table cellpadding="8px">
            <%--            <tr>--%>
            <%--                <td>用户名：</td>--%>
            <%--                <td>--%>
            <%--                    <input type="text" id="userId" name="userId" readonly="readonly"--%>
            <%--                           value="${sessionInfo.user.id }" style="width: 200px"/>--%>
            <%--                </td>--%>
            <%--            </tr>--%>
            <tr>
                <td>原密码：</td>
                <td>
                    <input type="password" id="oldPassword" name="oldPassword" class="easyui-validatebox"
                           required="true" style="width: 200px"/>
                </td>
            </tr>
            <tr>
                <td>新密码：</td>
                <td>
                    <input type="password" id="newPassword" name="newPassword" class="easyui-validatebox"
                           required="true" style="width: 200px"/>
                </td>
            </tr>
            <tr>
                <td>再次输入新密码：</td>
                <td>
                    <input type="password" id="newPassword2" name="newPassword2" class="easyui-validatebox"
                           required="true" style="width: 200px"/>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:modifyPassword()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closePasswordModifyDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

<div id="dlgedit" class="easyui-dialog" style="width: 400px;height: 300px;padding: 10px 20px" closed="true"
     buttons="#dlgedit-buttons">
    <form id="userEdit" method="post">
        <table cellpadding="8px">

            <tr>
                
                <td>姓：</td>
                <td>
                    <input type="text" name="id" hidden="true" value="${sessionInfo.user.id }"/>
                    <input type="text" name="firstName" class="easyui-validatebox" required="true"
                           value="${sessionInfo.user.firstName }" style="width: 200px"/>
                </td>
            </tr>
            <tr>
                <td>名：</td>
                <td>
                    <input type="text" name="lastName" class="easyui-validatebox" required="true"
                           value="${sessionInfo.user.lastName }" style="width: 200px"/>
                </td>
            </tr>
            <tr>
                <td>邮箱：</td>
                <td>
                    <input type="text" name="email" class="easyui-validatebox"
                           required="true"  style="width: 200px"/>
                </td>
            </tr>
            <tr>
                <td>手机：</td>
                <td>
                    <input type="text" name="phone" class="easyui-validatebox"
                           required="true"  style="width: 200px"/>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="dlgedit-buttons">
    <a href="javascript:editUserInfo()" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
    <a href="javascript:editUserInfoClose()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

</body>
</html>