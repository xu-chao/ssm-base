<%--
  Created by IntelliJ IDEA.
  User: xuchao
  Date: 2019/8/13
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单权限分配管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
    //用于search.js自动补全
    var _url= "${pageContext.request.contextPath }/group/searchGroupName";
    var _url_page = "${pageContext.request.contextPath}/group/groupPage";
    var _url_rm = "${pageContext.request.contextPath}/group/findGroupMenuByGid";
    var _url_um = "${pageContext.request.contextPath}/group/updateGroupMenu";
    var _value = 'name';
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/groupSearch.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/groupMenuSet.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',title:'角色列表'"
    style="width: 600px;padding: 4px; background-color: #eee">
    <!--搜索区  -->
    <div data-options="region:'north',title:'查询'"
         style="padding: 4px; background-color: #eee; height: 40px">
        <form id="searchForm">
            <table cellpadding="5">
                <tr>
                    <td>角色名称：</td>
                    <td><input name="name" class="easyui-combobox"
                               id="inputtable" /></td>
                    <td><a class="easyui-linkbutton"
                           data-options="iconCls:'icon-search'" id="btnSearch">查询</a></td>
                    <td><a class="easyui-linkbutton"
                           data-options="iconCls:'icon-remove'" id="btnReset">重置</a></td>
                </tr>
            </table>
        </form>
    </div>
    <div style="height: 4px;"></div>
    <table id="grid"></table>
</div>
<div id="menuPanel" data-options="region:'east',title:'菜单列表',split:true"
     style="width: 500px;">
    <ul id="tree"></ul>
</div>
</body>
</html>
