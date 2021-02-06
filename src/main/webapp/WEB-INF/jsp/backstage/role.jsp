<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/template.css" media="all">
</head>
<style>
    .layui-form-checkbox span {
        padding: 0 10px;
        height: auto !important;
        font-size: 14px;
        border-radius: 2px 0 0 2px;
        background-color: #d2d2d2;
        color: #fff;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

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
</div>
<%--添加表单--%>
<div class="layui-form" lay-filter="layuiadmin-form-useradmin" id="addForm" style="padding: 10px 0 0 0;display: none">

    <div class="layui-form-item">
        <label class="layui-form-label">角色名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" lay-verify="required" lay-verify="name" placeholder="请输入代理商名称"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">标识</label>
        <div class="layui-input-inline">
            <input type="text" name="uniqueCode" lay-verify="required" placeholder="角色唯一标识" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">权限范围</label>
        <input type="hidden" name="codes" id="codes">
        <div class="layui-input-block" id="authorityList">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="addSubmit">立即提交</button>
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
        <label class="layui-form-label">角色名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" id="name" lay-verify="required" lay-verify="name" placeholder="请输入角色名称"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">标识</label>
        <div class="layui-input-inline">
            <input type="text" name="uniqueCode" id="uniqueCode" lay-verify="required" readonly="readonly"
                   placeholder="角色唯一标识" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">权限范围</label>
        <input type="hidden" name="codes" id="codes2">
        <div class="layui-input-block" id="authorityList2">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">状态</label>
        <div class="layui-input-block">
            <input type="radio" name="state" value="1" title="启用">
            <input type="radio" name="state" value="2" title="禁用">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="updateSubmit">立即提交</button>
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
<script type="text/html" id="state">
    {{# if(d.state == '1') {d.state = '启用'}   }}
    {{# if(d.state == '2') {d.state = '禁用'}   }}
    {{# if(d.state == null) {d.state = ''}   }}
    <span>{{d.state}}</span>
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
            form = layui.form,
            data = form.data;

        table.render({
            elem: '#table',
            url: '/role/list',
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'name', title: '角色名称', minWidth: 200},
                {field: 'uniqueCode', title: '唯一标识', minWidth: 200},
                {field: 'state', title: '状态', templet: '#state', minWidth: 200},
                {field: 'authority', title: '拥有权限'},
                {
                    field: 'createTime',
                    title: '创建时间',
                    minWidth: 200,
                    templet: '<div>{{layui.util.toDateString(d.createTime, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
                },
                {
                    field: 'updateTime',
                    title: '更新时间',
                    minWidth: 200,
                    templet: '<div>{{layui.util.toDateString(d.updateTime, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
                },
                {title: '操作', align: 'center', fixed: 'right', toolbar: '#toolbar'}
            ]]
            , page: true
            , limit: 10
            , limits: [10, 15, 20, 25, 30]
            , height: 'full-220'
            , text: {
                none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
            }
        });

        //动态加载权限
        $.ajax({
            url: '/authority/listAll',
            type: "get",
            success: function (result) {
                data = result.data;
                var html = '';
                var html2 = "";
                $.each(data, function (index, value) {//获取所有key值
                    html += "<input type='checkbox' name='code' lay-skin='primary'  title='" + value.name + "' value='" + value.code + "' lay-filter='filter' id='code" + value.code + "'/>";
                });
                $.each(data, function (index, value) {//获取所有key值
                    html2 += "<input type='checkbox' name='code' lay-skin='primary'  title='" + value.name + "' value='" + value.code + "' lay-filter='filter' id='code2" + value.code + "'/>";
                });
                $('#authorityList').html(html);
                $('#authorityList2').html(html2);
                form.render();
            }
        });


        var index = 0;
        //触发增加事件
        $('#add').on('click', function () {

            //打开编辑窗口
            index = layer.open({
                type: 1,
                title: '添加角色信息',
                content: $('#addForm'),
                area: ['420px', '350px'],
                closeBtn: 1,
                end: function () {
                    window.location.reload();
                }
            });
        });

        //监听添加提交
        form.on('submit(addSubmit)', function (data) {
            var field = data.field;
            var str = "";
            $("input:checkbox[name='code']:checked").each(function (i) {

                var val = $(this).val();
                str = str + val + ",";

            });
            field.codes = str.substring(0, str.length - 1);
            $.ajax({
                url: '/role/add',
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
                    url: '/role/deletes/' + ids,
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
                        url: '/role/deletes/' + data.id,
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
                    title: '编辑角色信息',
                    content: $('#updateForm'),
                    area: ['420px', '350px'],
                    closeBtn: 1,
                    end: function () {
                        window.location.reload();
                    }
                });
                //赋值
                $("#id").val(data.id);
                $("#name").val(data.name);
                $("#uniqueCode").val(data.uniqueCode);
                $("input:radio[value='" + data.state + "']").attr('checked', 'true');

                var codes = data.codes;
                if (codes != null) {
                    var code = codes.split(",");
                    for (var i = 0; i < code.length; i++) {

                        $("#code2" + code[i]).prop('checked', true);
                        $("#code2" + code[i]).addClass('layui-form-checked');
                    }
                }
                form.render();
                //监听更新提交
                form.on('submit(updateSubmit)', function (data) {
                    var field = data.field;
                    var str = "";
                    $("input:checkbox[name='code']:checked").each(function (i) {

                        var val = $(this).val();
                        str = str + val + ",";

                    });
                    field.codes = str.substring(0, str.length - 1);
                    $.ajax({
                        url: '/role/update',
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
                });
            }
        });


    });
</script>


</html>

