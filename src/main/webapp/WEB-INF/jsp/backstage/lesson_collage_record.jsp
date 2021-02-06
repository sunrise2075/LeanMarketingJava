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
</style>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">

            <div class="layui-card">
                <div class="layui-form layui-card-header layuiadmin-card-header-auto">
                    <div class="layui-form-item">

                        <div class="layui-inline">
                            <select name="status" id="status">
                                <option value="">拼团状态</option>
                                <option value="1">拼团中</option>
                                <option value="2">拼团成功</option>
                                <option value="2">拼团失败</option>
                            </select>
                        </div>

                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="lessonName" id="lessonName" placeholder="请输入课程名称"
                                       autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="userName" id="userName" placeholder="请输入购买人名称"
                                       autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="address" id="address" placeholder="请输入开课地址" autocomplete="off"
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
                            <%--<button class="layui-btn" id="export">导出</button>--%>
                        </div>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="layui-form" id="editForm" style="padding: 10px 0 0 0;display: none">

    <div class="layui-form-item">
        <div class="layui-input-inline">
            <input type="hidden" id="id" name="id" autocomplete="off" class="layui-input" readonly="readonly">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">地址</label>
        <div class="layui-input-inline">
            <input type="text" name="address" autocomplete="off" placeholder="请输入地址" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="editSubmit">确定</button>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/layui.js"></script>

<script type="text/html" id="toolbar">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="edit">编辑开课地址</a>
</script>

<script type="text/html" id="getLessonImg">
    <img src="{{d.lessonImg}}" height="100%" width="100%">
</script>

<script type="text/html" id="getUserImg">
    <img src="{{d.userImg}}" height="100%" width="100%">
</script>

<script type="text/html" id="getIsLeader">
    {{# if(d.isLeader == 1) {d.isLeader = '<a class="layui-btn layui-btn-normal layui-btn-xs"
                                              lay-event="get">查看成员信息</a>'}   }}
    {{# if(d.isLeader == 2) {d.isLeader = ''}   }}
    <span>{{d.isLeader}}</span>
</script>

<script type="text/html" id="getIsNeedProvideAddress">
    {{# if(d.isNeedProvideAddress == 2) {d.isNeedProvideAddress = '<a class="layui-btn layui-btn-normal layui-btn-xs"
                                                                      lay-event="edit">编辑开课地址</a>'}   }}
    {{# if(d.isNeedProvideAddress == 1) {d.isNeedProvideAddress = ''}   }}
    {{# if(d.isNeedProvideAddress == null) {d.isNeedProvideAddress = ''}   }}
    <span>{{d.isNeedProvideAddress}}</span>
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
            laydate = layui.laydate,
            form = layui.form;

        //监听搜索
        form.on('submit(search)', function (data) {
            var field = data.field;
            //执行重载
            table.reload('table', {
                where: field
            });
        });

        var url = "/lesson/collage/record/list?isLeader=1";

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
                {field: 'isNeedProvideAddress', title: '是否提供地址', templet: '#getIsNeedProvideAddress', minWidth: 200},
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
                //{title: '操作', align: 'center', fixed: 'right', toolbar: '#toolbar',minWidth:200}
            ]]
            , page: true
            , limit: 10
            , limits: [10, 15, 20, 25, 30]
            , height: 'full-220'
            , text: {
                none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
            }
        });

        //监听工具条
        table.on('tool(table)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'get') {

                sessionStorage.setItem("recordNum", data.recordNum);

                layer.open({
                    type: 2,
                    title: "团员信息",
                    content: ['https://pc.leanmarketing.cn/page/lesson_collage_record2', 'no'],
                    area: ['1200px', '700px'],
                });

            } else if (layEvent === 'edit') { //编辑
                //打开编辑窗口
                layer.open({
                    type: 1,
                    title: '编辑开课地址',
                    content: $('#editForm'),
                    area: ['400px', '250px'],
                    closeBtn: 1,
                    end: function () {
                        window.location.reload();
                    }
                });

                $("#id").val(data.id);

                var recordNum = data.recordNum;

                //监听更新提交
                form.on('submit(editSubmit)', function (data) {
                    var field = data.field;
                    field.recordNum = recordNum;
                    field.isLeader = null;
                    $.ajax({
                        url: '/lesson/collage/record/updateAndSendMessage',
                        type: "post",
                        data: field,
                        dataType: "json",
                        success: function (result) {
                            layer.msg(result.message);
                            layer.closeAll();
                        }
                    });
                });
            }
        });

        $('#export').on('click', function () {

            var lessonName = $("#lessonName").val();
            var userName = $("#userName").val();
            var address = $("#address").val();

            location.href = '/lesson/collage/record/export?lessonName=' + lessonName + '&userName=' + userName + '&address=' + address;
        });
    });
</script>


</html>


