<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/layui/css/layui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/layui/css/login.css" media="all"/>
</head>
<body class="beg-login-bg" background="${pageContext.request.contextPath}/static/img/back_img.jpg">

<div class="beg-login-box">
    <header>
        <h1 style="color: royalblue">精益营销后台登录</h1>
    </header>

    <div class="beg-login-main">
        <form id="loginFrom" class="layui-form">
            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <i class="layui-icon">&#xe612;</i>
                </label>
                <input type="text" name="username" value="admin" lay-verify="required" autocomplete="off"
                       placeholder="请输入用户名（必填）" class="layui-input">
            </div>

            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <i class="layui-icon">&#xe642;</i>
                </label>
                <input type="password" name="password" value="123456" lay-verify="pass" placeholder="请输入密码（必填）"
                       autocomplete="off" class="layui-input">
            </div>

            <div class="layui-form-item">
                <div class="beg-pull-left beg-login-remember">
                    <button class="layui-btn" lay-submit lay-filter="login">登录</button>
                </div>
                <div class="beg-pull-right">
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
                <div class="beg-clear"></div>
            </div>
        </form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/layui.all.js"></script>
<script>
    layui.use(['form'], function () {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.jquery,
            data = form.data;

        //自定义验证规则
        form.verify({
            pass: [/(.+){6,12}$/, '密码必须6到12位']
        });

        //监听提交
        form.on('submit(login)', function (data) {
            $.ajax({
                url: "/administrator/login",      //异步请求链接
                type: "post",             //请求方式，一般是GET、POST
                data: data.field,          //异步请求数据
                dataType: "json",            //数据类型，json表示返回json数据，需要跨域请求时jsonp
                scriptCharset: "utf-8",      //数据编码格式
                async: false,                //异步返回，默认false
                cache: true,             //数据缓存，默认true
                success: function (result) {
                    if (result) {
                        window.location.href = "home.jsp";
                    } else {
                        layer.msg("账号或密码错误！");
                    }
                }
            });
            return false;
        });
    });
</script>
<script language="JavaScript">
    if (window != top) {
        top.location.href = location.href;
    }
</script>
</body>
</html>
