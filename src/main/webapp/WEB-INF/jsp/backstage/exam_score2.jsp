<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>试题成绩管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/template.css" media="all">
</head>
<style>
    .layui-table-cell .layui-form-checkbox[lay-skin=primary], .layui-table-cell .layui-form-radio[lay-skin=primary] {
        top: 0px;
        vertical-align: middle;
    }
</style>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-form layui-card-header layuiadmin-card-header-auto">
                    <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-inline">--%>
                    <%--<div class="layui-input-inline">--%>
                    <%--<input type="text" name="examName" placeholder="请输入试题名称" autocomplete="off"--%>
                    <%--class="layui-input">--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-inline">--%>
                    <%--<div class="layui-input-inline">--%>
                    <%--<input type="text" name="userName" placeholder="请输入用户名称" autocomplete="off"--%>
                    <%--class="layui-input">--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-inline">--%>
                    <%--<button class="layui-btn layuiadmin-btn-useradmin" data-type="reload" lay-submit--%>
                    <%--lay-filter="search">--%>
                    <%--<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>--%>
                    <%--</button>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                </div>
                <div class="layui-card-body">
                    <table class="layui-hide" id="table" lay-filter="table">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/layui.js"></script>
<script type="text/html" id="toolbar">
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
            class="layui-icon layui-icon-edit"></i></a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i
            class="layui-icon layui-icon-delete"></i></a>
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

        table.render({
            elem: '#table',
            url: '/exam/score/list2',
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'subjectName', title: '试题名称', minWidth: 200},
                {field: 'userName', title: '用户名称', minWidth: 200},
                {field: 'score', title: '评估结果', minWidth: 200},
                {field: 'evaluate', title: '试题评价', minWidth: 200},
                {
                    field: 'createTime',
                    title: '创建时间',
                    templet: '<div>{{layui.util.toDateString(d.createTime, \'yyyy-MM-dd HH:mm:ss\')}}</div>'

                    , minWidth: 200
                },
                {
                    field: 'updateTime',
                    title: '更新时间',
                    templet: '<div>{{layui.util.toDateString(d.updateTime, \'yyyy-MM-dd HH:mm:ss\')}}</div>'

                    , minWidth: 200
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

        //监听搜索
//        form.on('submit(search)', function (data) {
//            var field = data.field;
//            //执行重载
//            table.reload('table', {
//                where: field
//            });
//        });

    });
</script>

</html>
