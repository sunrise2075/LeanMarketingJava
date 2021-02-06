<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>员工管理</title>
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
                <c:if test="${login_admin.roleCode == 1}">
                    <div class="layui-form layui-card-header layuiadmin-card-header-auto">
                        <div class="layui-form-item">

                            <div class="layui-inline">
                                <div class="layui-input-inline">
                                    <input type="text" name="companyName" placeholder="请输入企业名称" autocomplete="off"
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
                </c:if>
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
        <label class="layui-form-label">企业</label>
        <div class="layui-input-inline">
            <select id="companyId1" name="userId">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">员工名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" lay-verify="required" placeholder="请输入员工名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">员工身份</label>
        <div class="layui-input-inline">
            <select name="identity" lay-verify="required">
                <option value="">请选择员工身份</option>
                <option value="1">总监</option>
                <option value="2">职员</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">员工头像</label>
        <button type="button" class="layui-btn" id="uploadFile">
            <i class="layui-icon">&#xe67c;</i>上传图片
        </button>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="headPortrait" id="headPortrait1" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" style="display: none;" id="show1">
        <label class="layui-form-label">图片显示</label>
        <div class="layui-input-inline">
            <img src="" id="showImg1" width="100%" height="60%"/>
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
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="addSubmit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
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
        <label class="layui-form-label">企业</label>
        <div class="layui-input-inline">
            <select id="companyId2" name="userId">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">员工名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" id="name" lay-verify="required" placeholder="请输入员工名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <%--<div class="layui-form-item">--%>
    <%--<label class="layui-form-label">管理员密码</label>--%>
    <%--<div class="layui-input-inline">--%>
    <%--<input type="text" name="password"  placeholder="请修改管理员密码" autocomplete="off" class="layui-input">--%>
    <%--</div>--%>
    <%--</div>--%>

    <div class="layui-form-item">
        <label class="layui-form-label">员工身份</label>
        <div class="layui-input-inline">
            <select name="identity" id="identity" lay-verify="required">
                <option value="">请选择员工身份</option>
                <option value="1">总监</option>
                <option value="2">职员</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">员工头像头像</label>
        <button type="button" class="layui-btn" id="uploadFile2">
            <i class="layui-icon">&#xe67c;</i>修改图片
        </button>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="headPortrait" id="headPortrait2" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">员工头像</label>
        <div class="layui-input-inline">
            <img src="" id="showImg2" width="100%" height="60%"/>
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
        <label class="layui-form-label">状态</label>
        <div class="layui-input-block">
            <input type="radio" name="state" value="1" title="启用">
            <input type="radio" name="state" value="2" title="禁用">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="updateSubmit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</div>


</body>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/lay/modules/layim.js"></script>
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
<script type="text/html" id="identity2">
    {{# if(d.identity == '1') {d.identity = '总监'}   }}
    {{# if(d.identity == '2') {d.identity = '职员'}   }}
    {{# if(d.identity == null) {d.identity = ''}   }}
    <span>{{d.identity}}</span>
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
            data = form.data,
            upload = layui.upload;

        var userId = "${login_admin.userId}";

        table.render({
            elem: '#table',
            url: '/employee/list?userId=' + userId,
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'id', title: 'ID'},
                {field: 'companyName', title: '企业名称', minWidth: 200},
                {field: 'name', title: '员工名称', minWidth: 200},
                {
                    field: 'headPortrait', title: '员工头像',
                    templet: function (d) {
                        if (d.headPortrait != '') {
                            return '<div><img src=' + d.headPortrait + ' height="100%" width="100%"></div>'
                        } else {
                            return "";
                        }
                    }

                    , minWidth: 200
                },
                {field: 'identity', title: '员工身份', templet: '#identity2', minWidth: 200},
                {field: 'state', title: '状态', templet: '#state', minWidth: 200},
                {field: 'phone', title: '手机号码', minWidth: 200},
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

        var roleCode = "${login_admin.roleCode}";
        var userId = "";
        if (roleCode == 3) {
            userId = "${login_admin.userId}";
        }

        //动态加载下拉框值
        $.ajax({
            url: '/user/select?identity=2&id=' + userId,
            type: "get",
            dataType: "json",
            success: function (result) {
                data = result.data;

                var html = '<option value="">请选择企业</option>';
                if (data.length == 1) {

                    html += '<option selected value="' + data[0].id + '">' + data[0].companyName + '</option>';

                } else {
                    $.each(data, function (index, value) {
                        html += '<option value="' + value.id + '">' + value.companyName + '</option>';
                    });
                }

                $("#companyId1").html(html);
                $("#companyId2").html(html);
                form.render('select');
            }
        });


        upload.render({
            elem: '#uploadFile',
            url: '/upload/uploadFile',
            accept: 'file',
            before: function (obj) {
                layer.load(2, {shade: false});
            },
            done: function (res) {
                if (res.msg = 'ok') {
                    $("#show1").show();
                    $("#showImg1").attr("src", res.data);
                    $("#headPortrait1").val(res.data);
                }
                layer.closeAll("loading");
            }
        });

        upload.render({
            elem: '#uploadFile2',
            url: '/upload/uploadFile',
            accept: 'file',
            before: function (obj) {
                layer.load(2, {shade: false});
            },
            done: function (res) {
                if (res.msg = 'ok') {

                    $("#showImg2").attr("src", res.data);
                    $("#headPortrait2").val(res.data);
                }
                layer.closeAll("loading");
            }
        });
        var index = 0;
        //触发增加事件
        $('#add').on('click', function () {
            //打开编辑窗口
            index = layer.open({
                type: 1,
                title: '添加管理员信息',
                content: $('#addForm'),
                area: ['420px', '320px'],
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
                url: '/employee/add',
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
            return false;
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
                    url: '/employee/deletes/' + ids,
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
                        url: '/employee/deletes/' + data.id,
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
                //打开编辑窗口
                var index2 = 0;
                index2 = layer.open({
                    type: 1,
                    title: '编辑管理员信息',
                    content: $('#updateForm'),
                    area: ['420px', '320px'],
                    closeBtn: 1,
                    end: function () {
                        window.location.reload();
                    }
                });
                //赋值
                $("#id").val(data.id);
                $("#name").val(data.name);
                $("#headPortrait2").val(data.headPortrait);
                $("#mail").val(data.mail);
                $("#phone").val(data.phone);
                $("#showImg2").attr("src", data.headPortrait);
                $("input:radio[value='" + data.state + "']").attr('checked', 'true');
                $("#identity").find("option[value = '" + data.identity + "']").attr("selected", "selected");
                form.render('radio');
                form.render('select');
                //监听更新提交
                form.on('submit(updateSubmit)', function (data) {
                    var field = data.field;
                    $.ajax({
                        url: '/employee/update',
                        type: "post",
                        data: field,
                        dataType: "json",
                        async: false,
                        success: function (result) {

                            if (result.code == 200) {
                                layer.msg(result.message, {
                                    icon: 1,
                                    time: 1000,
                                    end: function () {
                                        //关闭弹窗
                                        layer.close(index2);
                                    }
                                });
                            } else {
                                layer.msg(result.message);
                            }

                        }
                    });
                    return false;
                });
            }
        });
    });
</script>

</html>
