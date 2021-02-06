<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员日志管理</title>
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
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name='startTime' class="layui-input" id="startTime"
                                       placeholder="开始时间">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name='endTime' class="layui-input" id="endTime" placeholder="结束时间">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <select name="operateType" lay-filter="LAY-user-adminrole-type">
                                <option value="">请选择操作类型</option>
                                <option value="1">添加操作</option>
                                <option value="2">删除操作</option>
                                <option value="3">修改操作</option>
                                <option value="4">其他操作</option>
                            </select>
                        </div>
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="operateName" name="" placeholder="请输入操作人名称" autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <button class="layui-btn layuiadmin-btn-useradmin" data-type="reload" lay-submit
                                    lay-filter="search">
                                <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="layui-card-body">
                    <table class="layui-hide" id="table" lay-filter="table">
                        <div style="padding-bottom: 10px;">

                        </div>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/layui.js"></script>
<script type="text/html" id="operateType">
    {{# if(d.operateType == 1) {d.operateType = '添加操作'}   }}
    {{# if(d.operateType == 2) {d.operateType = '删除操作'}   }}
    {{# if(d.operateType == 3) {d.operateType = '修改操作'}   }}
    {{# if(d.operateType == 4) {d.operateType = '其他操作'}   }}
    <span>{{d.operateType}}</span>
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
            laydate = layui.laydate,
            form = layui.form;

        table.render({
            elem: '#table',
            url: '/administrator/log/list',
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'id', title: 'ID'},
                {field: 'operateName', title: '操作人名称', minWidth: 200},
                {field: 'operateType', title: '操作类型', templet: '#operateType', minWidth: 200},
                {field: 'operateContent', title: '操作内容', minWidth: 200},
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

        //执行一个laydate实例
        laydate.render({
            elem: '#startTime' //指定元素
        });
        laydate.render({
            elem: '#endTime' //指定元素
        });

        //监听搜索
        form.on('submit(search)', function (data) {
            var field = data.field;
            //执行重载
            table.reload('table', {
                where: field
            });
        });
    });
</script>

</html>
