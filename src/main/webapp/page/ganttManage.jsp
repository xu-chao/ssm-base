<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>待办任务管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/Gantt/css/style.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/Gantt/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/Gantt/css/prettify.css" />
    <style type="text/css">
        body {
            font-family: Helvetica, Arial, sans-serif;
            font-size: 13px;
            padding: 0 0 50px 0;
        }

        .contain {
            width: 800px;
            margin: 0 auto;
        }
    </style>
    <script src="${pageContext.request.contextPath}/static/Gantt/js/jquery-2.1.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/Gantt/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/Gantt/js/jquery.fn.gantt.js" charset ="GB2312"></script>
    <script src="${pageContext.request.contextPath}/static/Gantt/js/prettify.js"></script>
    <script>
        $(function () {
            "use strict";
            //初始化gantt
            $(".gantt").gantt({
                source: [
                    {
                        name: "task  1",
                        desc: "",
                        values: [{
                            from: "/Date(1320192000000)/",
                            to: "/Date(1320592000000)/",
                            label: "",
                            customClass: "ganttRed"
                        }]
                    }, {
                        name: "task  2",
                        desc: "这是描述",
                        values: [{
                            from: "/Date(1322611200000)/",
                            to: "/Date(1323302400000)/",
                            label: "",
                            customClass: "ganttRed"
                        }]
                    }, {
                        name: "task  3",
                        desc: "",
                        values: [{
                            from: "/Date(1323802400000)/",
                            to: "/Date(1325685200000)/",
                            label: "",
                            customClass: "ganttGreen"
                        }]
                    }, {
                        name: "task  4",
                        desc: "描述",
                        values: [{
                            from: "/Date(1325685200000)/",
                            to: "/Date(1325695200000)/",
                            label: "",
                            customClass: "ganttBlue"
                        }]
                    }, {
                        name: "task  5",
                        desc: "",
                        values: [{
                            from: "/Date(1326785200000)/",
                            to: "/Date(1325785200000)/",
                            label: "",
                            customClass: "ganttGreen"
                        }]
                    }, {
                        name: "task  6",
                        desc: "",
                        values: [{
                            from: "/Date(1328785200000)/",
                            to: "/Date(1328905200000)/",
                            label: "",
                            customClass: "ganttBlue"
                        }]
                    }, {
                        name: "task  7",
                        desc: "",
                        values: [{
                            from: "/Date(1330011200000)/",
                            to: "/Date(1336611200000)/",
                            label: "",
                            customClass: "ganttOrange"
                        }]
                    }, {
                        name: "task  8",
                        desc: "",
                        values: [{
                            from: "/Date(1336611200000)/",
                            to: "/Date(1338711200000)/",
                            label: "",
                            customClass: "ganttOrange"
                        }]
                    },
                    {
                        name: "more",
                        desc: "",
                        values: [
                            {
                                from: "/Date(1322611200000)/",
                                to: "/Date(1323302400000)/",
                                label: "",
                                customClass: "ganttBlue"
                            },
                            {
                                from: "/Date(1323802400000)/",
                                to: "/Date(1325685200000)/",
                                label: "",
                                customClass: "ganttOrange"
                            },
                            {
                                from: "/Date(1328785200000)/",
                                to: "/Date(1328905200000)/",
                                label: "",
                                customClass: "ganttGreen"
                            },

                        ]
                    }],
                navigate: 'scroll',//buttons  scroll
                scale: "weeks",// months  weeks days  hours
                maxScale: "months",
                minScale: "days",
                itemsPerPage: 10,
                onItemClick: function (data) {
                    alert("Item clicked - show some details");
                },
                onAddClick: function (dt, rowId) {
                    alert("Empty space clicked - add an item!");
                },
                onRender: function () {
                    if (window.console && typeof console.log === "function") {
                        console.log("chart rendered");
                    }
                }
            });

            //弹窗功能
            $(".gantt").popover({
                selector: ".bar",
                title: "测试",
                content: "这是一条测试内容",
                trigger: "hover",
                placement: "auto right"
            });
            //prettyPrint();
        });
    </script>
</head>
<body>
<div class="gantt_ot" style="width: auto; margin:0px auto;">
    <div class="gantt"></div>
</div>
</body>
</html>