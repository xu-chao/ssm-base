<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>Basic LinkButton - jQuery EasyUI Mobile Demo</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/metro/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/mobile.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.mobile.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
<div class="easyui-navpanel">
    <header>
        <div class="m-toolbar">
            <span class="m-title">Login to System</span>
        </div>
    </header>
    <div style="margin:20px auto;width:100px;height:100px;border-radius:100px;overflow:hidden">
        <img src="../images/login1.jpg" style="margin:0;width:100%;height:100%;">
    </div>
    <div style="padding:0 20px">
        <div style="margin-bottom:10px">
            <input class="easyui-textbox" data-options="prompt:'Type username',iconCls:'icon-man'" style="width:100%;height:38px">
        </div>
        <div>
            <input class="easyui-passwordbox" data-options="prompt:'Type password'" style="width:100%;height:38px">
        </div>
        <div style="text-align:center;margin-top:30px">
            <a href="#" class="easyui-linkbutton" style="width:100%;height:40px"><span style="font-size:16px">Login</span></a>
        </div>
        <div style="text-align:center;margin-top:30px">
            <a href="#" class="easyui-linkbutton" plain="true" outline="true" style="width:100px;height:35px"><span style="font-size:16px">Register</span></a>
        </div>
    </div>
</div>
</body>
</html>