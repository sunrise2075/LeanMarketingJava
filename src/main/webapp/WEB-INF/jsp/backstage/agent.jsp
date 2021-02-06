<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>代理商管理</title>
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
                                <input type="text" name="name" placeholder="请输入代理商名称" autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="phone" placeholder="请输入代理商手机号" autocomplete="off"
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
                            <button class="layui-btn" id="deletes">批量删除</button>
                            <button class="layui-btn" id="add">添加</button>
                        </div>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<%--添加表单--%>
<div class="layui-form" lay-filter="layuiadmin-form-useradmin" id="addForm" style="padding: 10px 0 0 0;display: none">

    <div class="layui-form-item">
        <label class="layui-form-label">代理商名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" lay-verify="required" lay-verify="name" placeholder="请输入代理商名称"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">手机号码</label>
        <div class="layui-input-inline">
            <input type="text" name="phone" lay-verify="phone" placeholder="请输入手机号码" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">代理省份</label>
        <div class="layui-input-inline">
            <select name="province" lay-verify="required" class="province" id="p1">
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">过期时间</label>
        <div class="layui-input-inline">
            <input type="text" name="expirationTime" id="expirationTime1" lay-verify="required" placeholder="请输入过期时间"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="addSubmit">立即提交</button>
            <%--<button type="reset" class="layui-btn layui-btn-primary" id="reset1">重置</button>--%>
        </div>
    </div>
</div>
<%--更新表单--%>
<div class="layui-form" id="updateForm" style="padding: 10px 0 0 0;display: none">

    <div class="layui-form-item">
        <div class="layui-input-inline">
            <input type="hidden" id="id" name="id" autocomplete="off" class="layui-input" readonly="readonly">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">代理商名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" id="name" lay-verify="required" placeholder="请输入代理商名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">手机号码</label>
        <div class="layui-input-inline">
            <input type="text" name="phone" id="phone" lay-verify="phone" placeholder="请输入手机号码" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">代理省份</label>
        <div class="layui-input-inline">
            <select name="province" lay-verify="required" class="province" id="province">
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">过期时间</label>
        <div class="layui-input-inline">
            <input type="text" name="expirationTime" id="expirationTime2" lay-verify="required" placeholder="请输入过期时间"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="updateSubmit">立即提交</button>
            <%--<button type="reset" class="layui-btn layui-btn-primary">重置</button>--%>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/layuiadmin/area.js"></script>
<script>
    var html = "<option value=''>请选择代理省份</option>";
    for (var i = 0; i < provice.length; i++) {
        html = html + "<option value='" + provice[i]['name'] + "'>" + provice[i]['name'] + "</option>";
        document.getElementById("p1").innerHTML = html;
        document.getElementById("province").innerHTML = html;
    }
</script>
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
            laydate = layui.laydate,
            form = layui.form;

        table.render({
            elem: '#table',
            url: '/agent/list',
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
//                {field: 'id', title: 'ID' },
                {field: 'name', title: '代理商名称', minWidth: 200},
                {field: 'province', title: '代理省份', minWidth: 200},
                {field: 'phone', title: '手机号码', minWidth: 200},
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
                {title: '操作', width: 100, align: 'center', fixed: 'right', toolbar: '#toolbar'}
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
        form.on('submit(search)', function (data) {
            var field = data.field;
            //执行重载
            table.reload('table', {
                where: field
            });
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#expirationTime1',
            type: 'datetime'
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#expirationTime2',
            type: 'datetime'
        });

        var index = 0;
        //触发增加事件
        $('#add').on('click', function () {

            //打开编辑窗口
            index = layer.open({
                type: 1,
                title: '添加代理商信息',
                content: $('#addForm'),
                area: ['400px', '420px'],
                closeBtn: 1,
                end: function () {
                    window.location.reload();
                }
            });
        });

        //监听添加提交
        form.on('submit(addSubmit)', function (data) {
            var field = data.field;

            $.ajax({
                url: '/agent/add',
                type: "post",
                data: field,
                dataType: "json",
                async: false,
                success: function (result) {

                    layer.msg(result.message, {
                        icon: 1,
                        time: 1000,
                        end: function () {
                            //关闭弹窗
                            layer.close(index);
                        }
                    });
                }
            });
        });

        //触发批量删除事件
        $('#deletes').on('click', function () {
            var checkStatus = table.checkStatus('table');
            var checkData = checkStatus.data; //得到选中的数据
            if (checkData.length === 0) {
                return layer.msg('请选择数据');
            }
            layer.confirm('确定要删除信息', function (index) {
                var ids = '';
                for (var i = 0; i < checkData.length; i++) {
                    ids += checkData[i].id + ",";
                }
                ids = ids.substring(0, ids.length - 1);
                $.ajax({
                    url: '/agent/deletes/' + ids,
                    type: "get",
                    success: function (result) {
                        if (result.code == 200) {
                            layer.msg('已删除', {time: 1000});
                            table.reload('table');
                        } else {
                            layer.msg('删除失败', {time: 1000});
                        }
                    }
                });
                layer.close(index);
            });
        });

        //监听工具条
        table.on('tool(table)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'del') {  //删除
                layer.confirm('真的删除行么', function (index) {
                    //向服务端发送删除指令
                    $.ajax({
                        url: '/agent/deletes/' + data.id,
                        type: "get",
                        success: function (result) {
                            if (result.code == 200) {
                                layer.msg('已删除');
                                table.reload('table');
                            } else {
                                layer.msg('删除失败');
                            }
                        }
                    });
                    layer.close(index);
                });
            } else if (layEvent === 'edit') { //编辑
                var index2 = 0;                //打开编辑窗口
                index2 = layer.open({
                    type: 1,
                    title: '编辑代理商信息',
                    content: $('#updateForm'),
                    area: ['400px', '420px'],
                    closeBtn: 1,
                    end: function () {
                        table.reload('table');
                    }
                });
                //赋值
                $("#id").val(data.id);
                $("#name").val(data.name);
                $("#expirationTime2").val(getDay(data.expirationTime));
                $("#phone").val(data.phone);
                $("#province").find("option[value = '" + data.province + "']").attr("selected", "selected");
                form.render('select');
                //监听更新提交
                form.on('submit(updateSubmit)', function (data) {
                    var field = data.field;
                    $.ajax({
                        url: '/agent/update',
                        type: "post",
                        data: field,
                        dataType: "json",
                        async: false,
                        success: function (result) {
                            layer.msg(result.message, {
                                icon: 1,
                                time: 1000,
                                end: function () {
                                    //关闭弹窗
                                    layer.close(index2);
                                }
                            });
                        }
                    });
                    return false;
                });
            }
        });
    });

    function getDay(time) {
        var date = new Date();
        date.setTime(time);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        h = h < 10 ? ('0' + h) : h;
        var minute = date.getMinutes();
        var second = date.getSeconds();
        minute = minute < 10 ? ('0' + minute) : minute;
        second = second < 10 ? ('0' + second) : second;
        return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
    }
</script>


</html>

