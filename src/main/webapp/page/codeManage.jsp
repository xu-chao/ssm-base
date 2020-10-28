<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>待办任务管理</title>
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
    <script type="text/javascript">
        function convert(rows) {
            function exists(rows, parentId) {
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].id == parentId) return true;
                }
                return false;
            }

            var nodes = [];
            // get the top level nodes
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                if (!exists(rows, row.parentId)) {
                    nodes.push({
                        id: row.id,
                        text: row.name
                    });
                }
            }

            var toDo = [];
            for (var i = 0; i < nodes.length; i++) {
                toDo.push(nodes[i]);
            }
            while (toDo.length) {
                var node = toDo.shift();	// the parent node
                // get the children nodes
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    if (row.parentId == node.id) {
                        var child = {id: row.id, text: row.name};
                        if (node.children) {
                            node.children.push(child);
                        } else {
                            node.children = [child];
                        }
                        toDo.push(child);
                    }
                }
            }
            return nodes;
        }

        $(function () {
            $('#tt').tree({
                url: '${pageContext.request.contextPath}/static/mock/mock_code.json',
                //dnd: true,
                loadFilter: function (rows) {
                    return convert(rows);
                }
            });
        });
    </script>
</head>
<body>
<%--<p>拥有权限</p>--%>
<%--<ul id="tt" style="width: 200px"></ul>--%>
<%--<div class="easyui-panel" style="width:200px;height:400px;padding:2px">--%>
<%--    <p align="center">拥有权限</p>--%>
<%--    <ul id="tt"></ul>--%>
<%--</div>--%>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west',border:false,title:'故障编码'" style="width: 200px; border-right-width: 1px;">
        <ul id="tt"></ul>
    </div>
    <div data-options="region:'center',border:false,title:'操作面板'" style="padding:20px;">
        <div style="border:1px solid red;padding:10px;">
            带层级的数据源格式：
            <pre>
[
  {"id":1,"parendId":0,"name":"东方神话"},
  {"id":2,"parendId":1,"name":"女娲补天"},
  {"id":3,"parentId":2,"name":"投影灯光系统"},
  {"id":4,"parentId":2,"name":"计软网络通信系统"},
  {"id":5,"parentId":2,"name":"车船系统"},
  {"id":6,"parentId":2,"name":"平台系统"},
  {"id":7,"parentId":2,"name":"特技系统"},
  {"id":8,"parentId":2,"name":"停车场系统"},
  {"id":9,"parentId":2,"name":"投影系统"},
  {"id":10,"parentId":3,"name":"音响系统"},
  {"id":11,"parentId":3,"name":"灯光系统"},
  {"id":12,"parentId":3,"name":"电视/LED屏"},
  {"id":13,"parentId":4,"name":"计算机/软件系统"},
  {"id":14,"parentId":4,"name":"网络通信系统"},
  {"id":15,"parentId":5,"name":"行车系统"},
  {"id":16,"parentId":5,"name":"车体结构灯光音响系统"},
  {"id":17,"parentId":5,"name":"旋转摇摆/液压系统"},
  {"id":18,"parentId":5,"name":"控制系统"},
  {"id":19,"parentId":5,"name":"动力系统"},
  {"id":20,"parentId":6,"name":"控制系统"},
  {"id":21,"parentId":6,"name":"传动系统"},
  {"id":22,"parentId":7,"name":"成品系统"},
  {"id":23,"parentId":7,"name":"气压系统"},
  {"id":24,"parentId":8,"name":"停车场系统"}
]
</pre>
        </div>
    </div>
</div>
</body>
</html>