<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>拼课记录管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/template.css" media="all">
</head>
<style>
    .layui-table-cell .layui-form-checkbox[lay-skin=primary], .layui-table-cell .layui-form-radio[lay-skin=primary] {
        top: 0px;
        vertical-align: middle;
    }

    .layui-table-body {
        position: relative;
        overflow: auto;
        margin-right: -1px;
        height: 505px;
    }
</style>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">

            <div class="layui-card">

                <div class="layui-card-body" style="height: 100%;">
                    <table class="layui-hide" id="table" lay-filter="table">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/layui.js"></script>

<script type="text/html" id="getLessonImg">
    <img src="{{d.lessonImg}}" height="100%" width="100%">
</script>

<script type="text/html" id="getUserImg">
    <img src="{{d.userImg}}" height="100%" width="100%">
</script>

<script type="text/html" id="getIsLeader">
    {{# if(d.isLeader == 1) {d.isLeader = '是'}   }}
    {{# if(d.isLeader == 2) {d.isLeader = '否'}   }}
    <span>{{d.isLeader}}</span>
</script>

<script type="text/html" id="getStatus">
    {{# if(d.status == 1) {d.status = '拼团中'}   }}
    {{# if(d.status == 2) {d.status = '拼团成功'}   }}
    {{# if(d.status == 3) {d.status = '拼团失败'}   }}
    <span>{{d.status}}</span>
</script>

<script>

    layui.config({
        base: '${pageContext.request.contextPath}/static/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'table', 'laydate'], function () {
        var $ = layui.jquery,
            table = layui.table,
            layer = layui.layer,
            form = layui.form;


        var url = "/lesson/collage/record/list?recordNum=" + sessionStorage.getItem("recordNum");

        table.render({
            elem: '#table',
            url: url,
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'lessonName', title: '课程名称', minWidth: 200},
                {field: 'lessonImg', title: '课程封面图', minWidth: 200, templet: '#getLessonImg'},
                {field: 'price', title: '支付金额', minWidth: 200},
                {field: 'userName', title: '购买人', minWidth: 200},
                {field: 'userImg', title: '购买人头像', minWidth: 200, templet: '#getUserImg'},
                {field: 'isLeader', title: '是否团长', templet: '#getIsLeader', minWidth: 200},
                {
                    field: 'beginTime',
                    title: '开课时间',
                    minWidth: 200,
                    templet: '<div>{{layui.util.toDateString(d.beginTime, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
                },
                {field: 'address', title: '开课地址', minWidth: 200},
                {field: 'status', title: '拼团状态', minWidth: 200, templet: '#getStatus'},
                {
                    field: 'createTime',
                    minWidth: 200,
                    title: '创建时间',
                    templet: '<div>{{layui.util.toDateString(d.createTime, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
                },
                {
                    field: 'updateTime',
                    minWidth: 200,
                    title: '更新时间',
                    templet: '<div>{{layui.util.toDateString(d.updateTime, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
                },
            ]]
            , page: true
            , limit: 10
            , limits: [10, 15, 20, 25, 30]
            , height: 'full-220'
            , text: {
                none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
            }
        });
    });
</script>


</html>


