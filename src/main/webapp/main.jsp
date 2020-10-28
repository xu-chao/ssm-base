<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--<shiro:hasRole name="Administrator"></shiro:hasRole>--%>
<%--<shiro:hasRole name="ZoneAdmin"></shiro:hasRole>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>运管维流程系统-主页面</title>
    <link rel="icon" href="${pageContext.request.contextPath}/static/images/favicon.ico" type="image/ico"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/css/default.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/webfont/css/font-awesome.min.css">
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/MD5/MD5.js"></script>
    <script type="text/javascript">
        var pageContext;
        $(document).ready(function () {
            pageContext = $("#PageContext").val();
        })
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/main.js"></script>

    <%--拖拽功能--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>
    <link href="${pageContext.request.contextPath}/static/css/jeasyui.extensions.tabs.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.tabs.dndTab.js"></script>

    <%--<link href="http://www.easyui-extlib.com/Scripts/jquery-easyui-extensions/base/jeasyui.extensions.base.css" rel="stylesheet" />--%>
    <%--<script src="http://www.easyui-extlib.com/Scripts/jquery-easyui-extensions/tabs/jeasyui.extensions.tabs.operateTab.js"></script>--%>

    <script type="text/javascript">
        var url;
        //菜单 刷新功能
        $(function () {
            $("#tabupdate").click(
                function () {
                    //刷新当前tab
                    var currTab = self.parent.$('#tabs').tabs('getSelected'); //获得当前tab
                    var url = $(currTab.panel('options').content).attr('src');
                    self.parent.$('#tabs').tabs('update', {
                        tab: currTab,
                        options: {
                            content: createFrame(url)
                        }
                    });
                }
            )
        })

        window.onload = function () {
            $('#loading-mask').fadeOut();
        }

        function openTab(text, url, iconCls) {
            debugger
            if ($("#tabs").tabs("exists", text)) {
                $("#tabs").tabs("select", text);
                $("#tabs").tabs("enableDnd");
            } else {
                var content = "<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='${pageContext.request.contextPath}/page/" + url + "'></iframe>";
                $("#tabs").tabs("add", {
                    title: text,
                    iconCls: iconCls,
                    closable: true,
                    content: content
                });
                $("#tabs").tabs("enableDnd");
            }
        }


        function openPasswordModifyDialog() {
            url = "${pageContext.request.contextPath}/user/modifyPassword?id=${sessionScope.userID}";
            $("#dlg").dialog("open").dialog("setTitle", "修改密码");
        }

        function modifyPassword() {

            var userID = $('#userID').val();
            var oldPassword = $('#oldPassword').val();
            var newPassword = $('#newPassword').val();
            var rePassword = $('#newPassword2').val();

            oldPassword = passwordEncrying(userID, oldPassword);
            newPassword = passwordEncrying(userID, newPassword);
            rePassword = passwordEncrying(userID, rePassword);
            var data = {
                "oldPassword": oldPassword,
                "newPassword": newPassword,
                "rePassword": rePassword
            }

            <%--$("#fm").form("submit", {--%>
            <%--    url: "${pageContext.request.contextPath}/user/modifyPassword",--%>
            <%--    dataType:"json",--%>
            <%--    contentType:"application/json",--%>
            <%--    data:JSON.stringify(data),--%>
            <%--    onSubmit: function () {--%>
            <%--        if (!$(this).form("validate")) {--%>
            <%--            return false;--%>
            <%--        }--%>
            <%--        if (oldPassword != '${sessionInfo.user.password}') {--%>
            <%--            $.messager.alert("系统系统", "用户原密码输入错误！");--%>
            <%--            return false;--%>
            <%--        }--%>
            <%--        if (newPassword != rePassword) {--%>
            <%--            $.messager.alert("系统系统", "确认密码输入错误！");--%>
            <%--            return false;--%>
            <%--        }--%>
            <%--        return true;--%>
            <%--    },--%>
            <%--    success: function (result) {--%>
            <%--        var result = eval('(' + result + ')');--%>
            <%--        if (result.success) {--%>
            <%--            $.messager.alert("系统系统", "密码修改成功，下一次登录生效！");--%>
            <%--            resetValue();--%>
            <%--            $("#dlg").dialog("close");--%>
            <%--        } else {--%>
            <%--            $.messager.alert("系统系统", "密码修改失败，请联系管理员！");--%>
            <%--            return;--%>
            <%--        }--%>
            <%--    }--%>
            <%--});--%>
            // 将数据通过 AJAX 发送到后端
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/user/modifyPassword",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function (response) {
                    // 接收并处理后端返回的响应e'd'
                    if (response.result == "error") {
                        var errorMessage;
                        if (response.errorMsg == "passwordError") {
                            errorMessage = "用户原密码输入错误";
                            $.messager.alert("系统系统", errorMessage);
                        } else if (response.errorMsg == "passwordUnmatched") {
                            errorMessage = "确认密码输入错误";
                            $.messager.alert("系统系统", errorMessage);
                        }

                        $("#oldPassword").val("");
                        $("#newPassword").val("");
                        $("#newPassword2").val("");
                    } else {
                        $.messager.alert("系统系统", "密码修改成功，下一次登录生效！");
                        resetValue();
                        $("#dlg").dialog("close");
                    }

                },
                error: function (response) {
                    $.messager.alert("系统系统", "密码修改失败，请联系管理员！");
                    return;
                }
            });
        }

        // 密码加密模块
        function passwordEncrying(userID, password) {
            var str1 = $.md5(password);
            return str1;
        }

        function resetValue() {
            $("#oldPassword").val("");
            $("#newPassword").val("");
            $("#newPassword2").val("");
        }

        function closePasswordModifyDialog() {
            resetValue();
            $("#dlg").dialog("close");
        }

        function logout() {
            $.messager.confirm("系统提示", "您确定要退出系统吗?", function (r) {
                if (r) {
                    $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/user/logout",
                        dataType: "json",
                        contentType: "application/json",
                        success: function (result) {
                            console.log(result.result);
                            // 刷新
                            window.location.reload(true);
                        }, error: function (result) {
                            console.log(result.result);
                            // do log
                        }
                    })
                }
            });
        }

        //提交单号 实现关闭当前tab 并刷新前一个tab
        function refreshTabData(title, refreshGridFunc) {
            if ($("#tabs").tabs('exists', title)) {
                $("#tabs").tabs('close', title);
                $('#tabs').tabs('select', title);
                typeof refreshGridFunc === 'function' && refreshGridFunc.call();
            }
        }

        // $(function () {
        //     // 拖拽菜单
        //     // $("#tabs").tabs("enableDnd");
        //     $("#tabs").tabs("refresh", "首页");
        // })
    </script>

    <!-- 新 API 方法， 十分简洁 -->
    <%--<script src="https://v1.hitokoto.cn/?encode=js&select=%23hitokoto" defer></script>--%>
</head>
<body class="easyui-layout">
<noscript>
    <div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
        <img src="static/images/noscript.gif" alt='抱歉，请开启脚本支持！'/>
    </div>
</noscript>
<div id="loading-mask"
     style="position:absolute;top:0px; left:0px; width:100%; height:100%; background:#D2E0F2; z-index:20000">
    <div id="pageloading"
         style="position:absolute; top:50%; left:50%; margin:-120px 0px 0px -120px; text-align:center;  border:2px solid #8DB2E3; width:200px; height:55px;  font-size:14px;padding:10px; font-weight:bold; background:#fff; color:#15428B;">
        <img src="static/images/loading.gif" align="absmiddle"/> 正在加载中,请稍候...
    </div>
</div>
<%--<div region="north" style="height: 40px;background-color: #E0ECFF">--%>
<%--    <table style="padding: 5px;" width="100%">--%>
<%--        <tr>--%>
<%--            &lt;%&ndash;<td width="50%" valign="bottom" align="left">&ndash;%&gt;--%>
<%--            &lt;%&ndash;    <p id="hitokoto">D 获取中...</p>&ndash;%&gt;--%>
<%--            &lt;%&ndash;</td>&ndash;%&gt;--%>
<%--            <td width="50%">--%>
<%--                <img src=""/>--%>
<%--            </td>--%>
<%--            <td valign="bottom" align="right" width="50%">--%>
<%--                <font size="3">&nbsp;&nbsp;<strong>欢迎：</strong>(${sessionInfo.user.firstName }${sessionInfo.user.lastName })【${sessionInfo.group.name}】</font>--%>
<%--                <a href="javascript:logout()">安全退出</a>--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--    </table>--%>
<%--</div>--%>
<div id="easyui-header" data-options="region:'north'" style="height: 8%">
<%--            <div class="logo-content">--%>
<%--                <span class="easyui-linkbutton" data-options="iconCls:'icon-logo'"></span>--%>
<%--                <span class="project-name">ERP</span>--%>
<%--            </div>--%>
<%--    <div class="topLogin"><img src="${pageContext.request.contextPath}/static/images/logo.png"/>--%>
<%--        <div class="topLongRight">ERP管理平台</div>--%>
<%--    </div>--%>
<%--    <div class="quick-link-content">--%>
<%--        <a class="easyui-linkbutton" plain="true">--%>
<%--            <p style="margin-top: 10px"><img src="${pageContext.request.contextPath}/static/images/home1.png"/></p>--%>
<%--            <span class="quick-item-name">系统首页</span>&ndash;%&gt;--%>
<%--        </a>--%>
<%--        <a class="easyui-linkbutton" plain="true">--%>
<%--            <p style="margin-top: 10px"><img src="${pageContext.request.contextPath}/static/images/personal3.png"/></p>--%>
<%--            <p style="margin-top: 5px">个人资料</p>--%>
<%--        </a>--%>
<%--        <a class="easyui-linkbutton" plain="true">--%>
<%--            <p style="margin-top: 10px"><img src="${pageContext.request.contextPath}/static/images/analysis.png"/></p>--%>
<%--            <p style="margin-top: 5px">统计分析</p>--%>
<%--        </a>--%>

<%--    </div>--%>
    <div class="user-info">
        <a href="javascript:void(0)" class="easyui-menubutton"
           data-options="plain:true,menu:'#user-info-mm',iconCls:'icon-user'"><font
                size="3">(${sessionInfo.user.firstName }${sessionInfo.user.lastName })</font></a>
        <div id="user-info-mm" style="width:150px;" align="right">
            <div>【${sessionInfo.group.name}】</div>
            <div class="menu-sep"></div>
            <div>我的资料</div>
            <div>更改密码</div>
            <div class="menu-sep"></div>
            <div iconCls="icon-exit" onclick="logout()">安全退出</div>
        </div>
    </div>
</div>
<div region="center">
    <div id="tabs" class="easyui-tabs" data-options="fit:true,border:false">
        <div title="首页" data-options="iconCls:'icon-home'" style="padding:20px;overflow:hidden; color:red;">
            <iframe frameborder="0" width="100%" height="100%" name="frameName" src="page/home.jsp" scrolling="auto"
                    id="ifDiv"></iframe>
        </div>

    </div>
</div>
<div id="west" region="west" style="width: 200px;" title="导航菜单" split="true">
    <div id="nav">
    </div>
</div>
<div region="south" style="height: 26px;padding: 5px" align="center">
    <div id="timeShow">
        <script type="text/javascript">
            setInterval("document.getElementById('timeShow').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());", 1000);
        </script>
    </div>
    华强方特智能技术有限公司
</div>

<div id="dlg" class="easyui-dialog" style="width: 400px;height: 250px;padding: 10px 20px" closed="true"
     buttons="#dlg-buttons">

    <form id="fm" method="post">
        <table cellpadding="8px">
            <tr>
                <td>用户名：</td>
                <td>
                    <input type="text" id="userId" name="userId" readonly="readonly"
                           value="${sessionInfo.user.id }" style="width: 200px"/>
                </td>
            </tr>
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
                    <input id="PageContext" type="hidden" value="${pageContext.request.contextPath}"/>
                </td>
            </tr>
        </table>
    </form>

</div>

<div id="dlg-buttons">
    <a href="javascript:modifyPassword()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closePasswordModifyDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

<div id="mm" class="easyui-menu" style="width:200px;">
    <div id="tabupdate">刷新</div>
    <div class="menu-sep"></div>
    <div id="close">关闭</div>
    <div id="closeall">全部关闭</div>
    <div id="closeother">除此之外全部关闭</div>
    <div class="menu-sep"></div>
    <div id="closeright">当前页右侧全部关闭</div>
    <div id="closeleft">当前页左侧全部关闭</div>
</div>

</body>
</html>