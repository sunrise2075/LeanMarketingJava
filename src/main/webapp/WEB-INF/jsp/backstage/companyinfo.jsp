<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>公司信息管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/template.css" media="all">
    <link href="favicon.ico" rel="shortcut icon">
</head>
<body>
<div class="layui-card">
    <div><h1 style="text-align: center;margin-bottom: 20px;">公司信息</h1><br/></div>

    <div>
        <form class="layui-form" action="">

            <div class="layui-form-item" style="display: none">
                <label class="layui-form-label">公司 id</label>
                <div class="layui-input-block">
                    <input type="text" name="id" placeholder="公司 id" id="id" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item" style="text-align: center;">
                <label class="layui-form-label">公司名称</label>
                <div class="layui-input-block">
                    <input type="text" id="name" name="name" placeholder="公司名称" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">公司封面图</label>
                <div class="layui-input-block">
                    <img id="coverImg" src="" width="200px" height="200px"/>
                </div>
                <div class="layui-input-block" style="display: none">
                    <input type="text" name="coverImg" id="coverImg1" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label"></label>
                <button type="button" class="layui-btn" id="uploadPicture">
                    <i class="layui-icon">&#xe67c;</i>修改
                </button>
                <span style="color: red">请上传格式为 1920X500 的图片</span>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">PC首页图</label>
                <div class="layui-input-block">
                    <img id="pcImg" src="" width="200px" height="200px"/>
                </div>
                <div class="layui-input-block" style="display: none">
                    <input type="text" name="pcImg" id="pcImg1" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label"></label>
                <button type="button" class="layui-btn" id="uploadPicture2">
                    <i class="layui-icon">&#xe67c;</i>修改
                </button>
                <span style="color: red">请上传格式为 1920X500 的图片</span>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">公司logo图</label>
                <div class="layui-input-block">
                    <img id="logo" src="" width="200px" height="200px"/>
                </div>
                <div class="layui-input-block" style="display: none">
                    <input type="text" name="logo" id="logo1" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label"></label>
                <button type="button" class="layui-btn" id="uploadPicture3">
                    <i class="layui-icon">&#xe67c;</i>修改
                </button>
                <span style="color: red">请上传格式为 44*44 的图片</span>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">登录背景图</label>
                <div class="layui-input-block">
                    <img id="loginBgImg" src="" width="200px" height="200px"/>
                </div>
                <div class="layui-input-block" style="display: none">
                    <input type="text" name="logo" id="loginBgImg1" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label"></label>
                <button type="button" class="layui-btn" id="uploadPicture4">
                    <i class="layui-icon">&#xe67c;</i>修改
                </button>
                <span style="color: red">请上传格式为 1920*1020 的图片</span>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">公司简介</label>
                <div class="layui-input-block">
            <textarea name="introduction" id="introduction" placeholder="公司简介,最多可以输入100个字" maxlength="100"
                      lay-verify="required" class="layui-textarea"></textarea>
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">关于我们</label>
                <div id="aboutUs" class="layui-input-block">
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">用户条款</label>
                <div id="userItem" class="layui-input-block">
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">VIP权益</label>
                <div id="vipRights" class="layui-input-block">
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="updateSubmit">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/lay/modules/layim.js"></script>
<script src="${pageContext.request.contextPath}/static/layuiadmin/wangEditor.min.js"></script>
<script type="text/html" id="toolbar">
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
            class="layui-icon layui-icon-edit"></i></a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i
            class="layui-icon layui-icon-delete"></i></a>
</script>
<script>
    //上传图片到服务器的地址
    var url = 'https://pc.leanmarketing.cn/upload/ueditorUpload';
    //var url = 'http://localhost:10000/upload/ueditorUpload';
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

        // 富文本编辑器
        var E = window.wangEditor;
        var aboutUsEditor = new E('#aboutUs');
        var userItemEditor = new E('#userItem');
        var vipRightsEditor = new E('#vipRights');

        var xhr = new XMLHttpRequest();
        aboutUsEditor.customConfig.uploadImgServer = url;  // 上传图片到服务器
        aboutUsEditor.customConfig.uploadImgMaxSize = 10 * 1024 * 1024;
        aboutUsEditor.customConfig.uploadImgHeaders = {
            'Accept': 'application/json;charset=UTF-8',
            'X-Requested-With': 'XMLHttpRequest'
        };
        aboutUsEditor.customConfig.uploadImgHooks = {
            before: function (xhr, editor1, files) {
            },
            success: function (xhr, editor1, result) {
                return {
                    prevent: true,
                    msg: '上传成功~'
                }
            },
            fail: function (xhr, editor1, result) {
                return {
                    prevent: true,
                    msg: '图片插入错误，请重新上传图片！'
                }
            },
            error: function (xhr, editor1) {
                return {
                    prevent: true,
                    msg: '上传失败，请重新上传图片！'
                }
            },
            timeout: function (xhr, editor1) {
                return {
                    prevent: true,
                    msg: '上传超时，请重新上传图片！'
                }
            }
        };

        var xhr2 = new XMLHttpRequest();
        userItemEditor.customConfig.uploadImgServer = url;  // 上传图片到服务器
        userItemEditor.customConfig.uploadImgMaxSize = 10 * 1024 * 1024;
        userItemEditor.customConfig.uploadImgHeaders = {
            'Accept': 'application/json;charset=UTF-8',
            'X-Requested-With': 'XMLHttpRequest'
        };
        userItemEditor.customConfig.uploadImgHooks = {
            before: function (xhr, editor1, files) {
            },
            success: function (xhr, editor1, result) {
                r1.txt.append('<img src="' + result.data + '" style="max-width:100%;"/>');
                return {
                    prevent: true,
                    msg: '上传成功~'
                }
            },
            fail: function (xhr, editor1, result) {

                return {
                    prevent: true,
                    msg: '图片插入错误，请重新上传图片！'
                }
            },
            error: function (xhr, editor1) {
                return {
                    prevent: true,
                    msg: '上传失败，请重新上传图片！'
                }
            },
            timeout: function (xhr, editor1) {

                return {
                    prevent: true,
                    msg: '上传超时，请重新上传图片！'
                }
            }
        };

        var xhr3 = new XMLHttpRequest();
        vipRightsEditor.customConfig.uploadImgServer = url;  // 上传图片到服务器
        vipRightsEditor.customConfig.uploadImgMaxSize = 10 * 1024 * 1024;
        vipRightsEditor.customConfig.uploadImgHeaders = {
            'Accept': 'application/json;charset=UTF-8',
            'X-Requested-With': 'XMLHttpRequest'
        };
        vipRightsEditor.customConfig.uploadImgHooks = {
            before: function (xhr, editor1, files) {
            },
            success: function (xhr, editor1, result) {
                return {
                    prevent: true,
                    msg: '上传成功~'
                }
            },
            fail: function (xhr, editor1, result) {
                return {
                    prevent: true,
                    msg: '图片插入错误，请重新上传图片！'
                }
            },
            error: function (xhr, editor1) {
                return {
                    prevent: true,
                    msg: '上传失败，请重新上传图片！'
                }
            },
            timeout: function (xhr, editor1) {
                return {
                    prevent: true,
                    msg: '上传超时，请重新上传图片！'
                }
            }
        };

        aboutUsEditor.create();
        userItemEditor.create();
        vipRightsEditor.create();

        $.ajax({
            url: '/companyinfo/1',
            type: "get",
            success: function (result) {

                //赋值
                $("#id").val(result.data.id);
                $("#name").val(result.data.name);
                $("#introduction").val(result.data.introduction);
                aboutUsEditor.txt.html(result.data.aboutUs);
                userItemEditor.txt.html(result.data.userItem);
                vipRightsEditor.txt.html(result.data.vipRights);
                $("#coverImg").attr("src", result.data.coverImg);
                $("#coverImg1").val(result.data.coverImg);
                $("#pcImg").attr("src", result.data.pcImg);
                $("#pcImg1").val(result.data.pcImg);
                $("#logo").attr("src", result.data.logo);
                $("#logo1").val(result.data.logo);
                $("#loginBgImg").attr("src", result.data.loginBgImg);
                $("#loginBgImg1").val(result.data.loginBgImg);
            }
        });

        upload.render({
            elem: '#uploadPicture',
            url: '/upload/uploadFile',
            accept: 'file',
            done: function (res) {
                if (res.msg = 'ok') {
                    $("#coverImg").attr("src", res.data);
                    //赋值
                    $("#coverImg1").val(res.data);
                }
            }
        });

        upload.render({
            elem: '#uploadPicture2',
            url: '/upload/uploadFile',
            accept: 'file',
            done: function (res) {
                if (res.msg = 'ok') {
                    $("#pcImg").attr("src", res.data);
                    //赋值
                    $("#pcImg1").val(res.data);
                }
            }
        });

        upload.render({
            elem: '#uploadPicture3',
            url: '/upload/uploadFile',
            accept: 'file',
            done: function (res) {
                if (res.msg = 'ok') {
                    $("#logo").attr("src", res.data);
                    //赋值
                    $("#logo1").val(res.data);
                }
            }
        });

        upload.render({
            elem: '#uploadPicture4',
            url: '/upload/uploadFile',
            accept: 'file',
            done: function (res) {
                if (res.msg = 'ok') {
                    $("#loginBgImg").attr("src", res.data);
                    //赋值
                    $("#loginBgImg1").val(res.data);
                }
            }
        });

        //监听更新提交
        form.on('submit(updateSubmit)', function (data) {
            var field = data.field;
            data.field.aboutUs = aboutUsEditor.txt.html();
            data.field.userItem = userItemEditor.txt.html();
            data.field.vipRights = vipRightsEditor.txt.html();
            $.ajax({
                url: '/companyinfo/update',
                type: "post",
                data: field,
                dataType: "json",
                async: false,
                success: function (result) {
                    layer.msg('更新成功');

                }
            });
            return false;
        });
    });
</script>
</body>
</html>
