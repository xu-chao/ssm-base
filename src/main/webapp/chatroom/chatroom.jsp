﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>消息通知</title>
    <link rel="icon" href="${pageContext.request.contextPath}/chatroom/static/img/chat.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chatroom/static/css/chatroom.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chatroom/static/css/common/layui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chatroom/static/css/common/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chatroom/static/css/common/fileinput.min.css">
</head>
<body>
<div class="qqBox">
    <div class="BoxHead">
        <div class="headImg">
            <img id="avatarUrl" src=""/>
        </div>
        <div id="username" class="internetName"></div>
<%--        <button class="close logout" onclick="logout()">&times;</button>--%>
    </div>
    <div class="context">
        <div class="conLeft">
            <ul>

            </ul>
        </div>
        <div class="conRight">
            <div class="Righthead">
                <div class="headName"></div>
            </div>
            <div class="RightCont">
                <ul class="newsList-temp"></ul>
                <ul class="newsList">

                </ul>
            </div>
            <div class="RightFoot">
                <div class="emjon">
                    <ul>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_01.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_02.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_03.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_04.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_05.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_06.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_07.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_08.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_09.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_10.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_11.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_12.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_13.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_14.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_15.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_16.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_17.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_18.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_19.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_20.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_21.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_22.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_23.png"></li>
                        <li><img src="${pageContext.request.contextPath}/chatroom/static/img/emoji/emoji_24.png"></li>
                    </ul>
                </div>
                <div class="footTop">
                    <ul>
                        <li class="ExP">
                            <img src="${pageContext.request.contextPath}/chatroom/static/img/emoji.jpg">
                        </li>
                        <li class="file-upload">
                            <a data-toggle="modal" data-target="#upload-modal" data-backdrop="static">
                                <img src="${pageContext.request.contextPath}/chatroom/static/img/upload.jpg">
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="inputBox">
                    <input id="toUserId" type="hidden">
                    <input id="toGroupId" type="hidden">
                    <textarea id="dope" style="width: 99%;height: 75px; border: none;outline: none;" name="" rows=""
                              cols=""></textarea>
                    <button title="按下回车可发送" class="sendBtn">发 送</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="upload-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" data-dismiss="modal">&times;</button>
                    <h3 class="modal-title text-primary">文件上传</h3>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">选择文件</label>
                            <div class="col-sm-9">
                                <input type="file" name="file"
                                       class="col-sm-9 myfile"/>
                                <p class="help-block">注意：文件大小不超过30M，有效期为7天</p>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button id="upload-cancel" class="btn btn-sm btn-muted" data-dismiss="modal">
                        <i class="glyphicon glyphicon-remove"></i> 取消
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="icon2">
        <input placeholder="好友名称" id="findName" type="text"/>
    </div>

    <div class="clear"></div>
    <input type="button" value="添加" onclick="find()"/>

</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/chatroom/static/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/chatroom/static/js/common/jquery.actual.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/chatroom/static/js/common/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/chatroom/static/js/common/fileinput.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/chatroom/static/js/common/zh.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/chatroom/static/js/chatroom.js"></script>
<script type="text/javascript">
    var userId;
    var userChat;
    var socket;
    var sentMessageMap;
    var sentMessageMap_Group;
    setUserInfo();
    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }
    if (window.WebSocket) {
        debugger
        socket = new WebSocket("ws://localhost:2222");
        socket.onmessage = function (event) {
            var json = JSON.parse(event.data);
            if (json.status == 200) {
                var type = json.data.type;
                console.log("收到一条新信息，类型为：" + type);
                switch (type) {
                    case "LOGIN":
                        ws.loginReceive();
                        break;
                    case "SINGLE_SENDING":
                        ws.singleReceive(json.data);
                        break;
                    case "GROUP_SENDING":
                        ws.groupReceive(json.data);
                        break;
                    case "FILE_MSG_SINGLE_SENDING":
                        ws.fileMsgSingleRecieve(json.data);
                        break;
                    case "FILE_MSG_GROUP_SENDING":
                        ws.fileMsgGroupRecieve(json.data);
                        break;
                    default:
                        console.log("不正确的类型！");
                }
            } else {
                alert(json.msg);
                console.log(json.msg);
            }
        };

        // 连接成功1秒后，将用户信息注册到服务器在线用户表 这里是直接发送给WebSocketServerHandler.handlerWebSocketFrame直接处理登入
        socket.onopen = setTimeout(function (event) {
            ws.login();
            console.log("WebSocket已成功连接！");
        }, 1000)

        socket.onclose = function (event) {
            console.log("WebSocket已关闭...");
        };
    } else {
        alert("您的浏览器不支持WebSocket！");
    }

    function find() {
        $.ajax({
            type: 'POST',
            url: 'chatroom/add_friend',
            dataType: 'json',
            data: {
                findName: $("#findName").val()
            },
            success: function (data) {
                if (data.status == 200) {
                    alert(data.msg);
                    window.location.reload();//刷新界面
                } else {
                    alert(data.msg);
                }
            }
        });
    }
</script>

</body>
</html>
