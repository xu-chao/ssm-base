<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>运管维系统-登录界面</title>
    <link rel="icon" href="${pageContext.request.contextPath}/static/images/favicon.ico" type="image/ico"/>
    <link href="${pageContext.request.contextPath}/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/static/h-ui/css/H-ui.login.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/static/h-ui/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/Validate/css/validate.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/MD5/MD5.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/Validate/js/validate.js"></script>
    <script>
        var userID;
        var password;
        var checkCode;
        var pageContext = "${pageContext.request.contextPath}";
        $(function () {
            refreshCheckCode();
        });

        //重置按钮
        function resetValue() {
            $("#userID").val("");
            $("#password").val("");
        }

        // 刷新图形验证码
        function refreshCheckCode() {
            $('#checkCodeImg').click(function () {
                var timestamp = new Date().getTime();
                //被CheckCodeController类拦截
                $(this).attr("src", "checkCode/" + timestamp)
            })
        }

        // 登陆信息加密模块
        function infoEncrypt(userID, password, checkCode) {
            var str1 = $.md5(password);
            var str2 = $.md5(str1 + userID);
            var str3 = $.md5(str2 + checkCode.toUpperCase());
            return str3;
        }
        // 登录按钮
        function submitDate(x, y, tokenId) {
            userID = $('#userID').val();
            password = $('#password').val();
            x = x.toString(),
                y = y.toString(),
                checkCode = x + y;

            // 加密
            password = infoEncrypt(userID, password, checkCode);

            var data = {
                "id": userID,
                "password": password,
                "tokenIn": tokenId,
                "X": x,
                "Y": y,
            }

            //JSON.stringify(data)序列化
            $.ajax({
                type: "POST",
                url: "user/login",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function (response) {
                    // 分析返回的 JSON 数据
                    if (response.result == 'error') {
                        var errorMessage;
                        if (response.msg == "unknownAccount") {
                            errorMessage = "用户名错误";
                            Huimodal_alert(errorMessage, 2000);
                        } else if (response.msg == "incorrectCredentials") {
                            errorMessage = "密码或验证码错误";
                            Huimodal_alert(errorMessage, 2000);
                            $('#password').val("");
                        } else if (response.msg == "帐号无角色") {
                            errorMessage = "该帐号未分配角色";
                            Huimodal_alert(errorMessage, 2000);
                            $('#password').val("");
                        } else if (response.msg == "帐号无部门") {
                            errorMessage = "该帐号未分配部门";
                            Huimodal_alert(errorMessage, 2000);
                            $('#password').val("");
                        } else {
                            errorMessage = "服务器错误";
                            Huimodal_alert(errorMessage, 2000);
                            $('#password').val("");
                        }
                        $(".hkinnerWrap").addClass("red").removeClass("green");
                        setTimeout(function () {
                            $(".hkinnerWrap").removeClass("red green");
                            $(".v_rightBtn").css("left", 0);
                            $(".imgBtn").css("left", 0);
                        }, 500)
                        validateImageInit();
                        // //更新验证码
                        // $('#checkCodeImg').attr("src", "checkCode/" + new Date().getTime());
                        // $('#checkCode').val("");
                    } else {
                        // 页面跳转
                        window.location.href = "main";
                        //errorMessage = "登录成功";
                    }
                    // Huimodal_alert(errorMessage,2000);
                },
                error: function (data) {
                    // 处理错误
                    Huimodal_alert("系统异常", 2000);
                }
            });
        }
    </script>
</head>
<body>

<div class="header" style="padding: 0;">
    <h2 style="color: white; width: 400px; height: 60px; line-height: 60px; margin: 0 0 0 30px; padding: 0;">运管控系统</h2>
</div>
<div class="loginWraper">
    <div class="loginBox">
        <form id="fm" action="" class="form form-horizontal" method="post">
            <div class="row cl">
                <label class="form-label col-3"><i class="Hui-iconfont">&#xe60d;</i></label>
                <div class="formControls col-8">
                    <input id="userID" name="userID" type="number" placeholder="工号"
                           class="easyui-validatebox input-text size-L" required="true" style="width: 300px;">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-3"><i class="Hui-iconfont">&#xe60e;</i></label>
                <div class="formControls col-8">
                    <input id="password" name="password" type="password" placeholder="密码"
                           class="easyui-validatebox input-text size-L" required="true" style="width: 300px;">
                </div>
            </div>
            <div class="row cl" >
                <label class="form-label col-3"><i class="Hui-iconfont">&#xe6d7;</i></label>
                <div class="comImageValidate rightValidate col-8">
                    <div class="hkinnerWrap" style="height: 36px;position: relative">
                        <span class="v_rightBtn "><em class="notSel Hui-iconfont">&gt;&gt;</em></span>
                        <%--		<span class="huakuai"  style="font-size: 12px;line-height: 33px;color: #A9A9A9;">向右滑动滑块填充拼图</span>--%>
                        <input type="hidden" name="validX"/>
                    </div>
                    <div class="imgBg">
                        <div class="imgBtn">
                            <img/>
                        </div>
                        <span class="refresh Hui-iconfont">&#xe68f;</span>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="formControls col-8 col-offset-3">
<%--                    <a href="javascript:submitData()" id="submitBtn" type="button"--%>
<%--                       class="btn btn-success radius size-L">&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;</a>--%>
<%--                    &emsp;&emsp;&emsp;&emsp;--%>
                    <a href="javascript:resetValue()" id="resetBtn" type="button"
                       class="btn btn-info radius size-L">&nbsp;重&nbsp;&nbsp;&nbsp;&nbsp;置&nbsp;</a>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="footer">Copyright @ hqft</div>
</body>
</html>