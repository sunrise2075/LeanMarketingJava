<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人信息</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/template.css" media="all">
</head>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">

        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body">

                    <form class="layui-form" action="" lay-filter="component-form-element">
                        <div class="layui-row layui-col-space10 layui-form-item">
                            <div class="layadmin-homepage-panel layadmin-homepage-shadow">
                                <div class="layui-card text-center">
                                    <div class="layadmin-homepage-pad-ver" style="position: relative">
                                        <img class="layadmin-homepage-pad-img" name="model" id="headPortrait"
                                             src="${login_admin.headPortrait}" width="96" height="96">
                                    </div>
                                    <div class="layui-form-item" style="display: none">
                                        <label class="layui-form-label"></label>
                                        <button type="button" class="layui-btn" id="uploadFile">
                                            <i class="layui-icon">&#xe67c;</i>修改二维码
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-col-lg6">
                                <label class="layui-form-label">用户名：</label>
                                <div class="layui-input-block">
                                    <input type="hidden" id="newHeadPortrait" name="headPortrait"
                                           value="${login_admin.headPortrait}" autocomplete="off" class="layui-input">
                                    <input type="hidden" name="id" value="${login_admin.id}" autocomplete="off"
                                           class="layui-input">
                                    <input type="text" name="username" value="${login_admin.username}"
                                           lay-verify="required" placeholder="请输入用户名" autocomplete="off"
                                           class="layui-input">
                                </div>
                            </div>
                            <div class="layui-col-lg6">
                                <label class="layui-form-label">Email：</label>
                                <div class="layui-input-block">
                                    <input type="text" name="mail" value="${login_admin.mail}" lay-verify="email"
                                           placeholder="请输入Email" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-col-lg6">
                                <label class="layui-form-label">手机号：</label>
                                <div class="layui-input-block">
                                    <input type="text" name="phone" value="${login_admin.phone}" lay-verify="required"
                                           placeholder="请输入手机号" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-col-lg6">
                                <label class="layui-form-label">密码：</label>
                                <div class="layui-input-block">
                                    <input type="password" name="password" placeholder="不修改密码请留空" autocomplete="off"
                                           class="layui-input">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="updateSubmit">立即提交</button>
                                <%--<button type="reset" class="layui-btn layui-btn-primary">重置</button>--%>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/lay/modules/layim.js"></script>
<script>
    layui.config({
        base: '${pageContext.request.contextPath}/static/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'table', 'laydate'], function () {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.jquery,
            data = form.data,
            upload = layui.upload;

        //管理员头像点击之后
        $("#headPortrait").on('click', function () {
            document.getElementById("uploadFile").click();
        });

        var uploadInst = upload.render({
            elem: '#uploadFile',
            url: '/upload/uploadFile',
            accept: 'file',
            before: function (obj) {
                layer.load(2, {shade: false});
            },
            done: function (res) {
                if (res.msg = 'ok') {
                    $("#headPortrait").attr("src", res.data);
                    $("#newHeadPortrait").val(res.data);
                }
                layer.closeAll("loading");
            }
        });

        //监听提交
        form.on('submit(updateSubmit)', function (data) {
            var field = data.field;
            $.ajax({
                url: '/administrator/update',
                type: "post",
                data: field,
                dataType: "json",
                success: function (result) {
                    if (result.code == 200) {
                        layer.msg('更新成功');
                    } else {
                        layer.msg(result.message);
                    }
                }
            });
            return false;
        });
    });
</script>
</html>
