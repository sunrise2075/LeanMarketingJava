<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>会员管理</title>
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
                        <c:if test="${login_admin.roleCode == 1}">
                            <div class="layui-inline">
                                <select name="identity" lay-filter="identity" id="selectIdentity">
                                    <option value="">请选择用户身份</option>
                                    <option value="1">普通会员</option>
                                    <option value="2">企业会员</option>
                                    <option value="3">代理商</option>
                                    <option value="4">经销商</option>
                                </select>
                            </div>

                            <div class="layui-inline">
                                <select name="memberLevel" lay-filter="memberLevel" id="selectMemberLevel">
                                    <option value="">请选择会员等级</option>
                                    <option value="1">普通会员</option>
                                    <option value="2">初级会员</option>
                                    <option value="3">中级会员</option>
                                    <option value="4">高级会员</option>
                                </select>
                            </div>


                            <div class="layui-inline">
                                <select name="dealerId" id="dealerId">

                                </select>
                            </div>

                            <div class="layui-inline">
                                <select name="province" id="province">

                                </select>
                            </div>
                        </c:if>


                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="nickname" id="nickname" placeholder="请输入用户名" autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>


                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="phone" id="phone" placeholder="请输入手机号" autocomplete="off"
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
                            <%--<button class="layui-btn" id="deletes">批量删除</button>--%>
                            <button class="layui-btn" id="export">导出用户</button>
                            <c:if test="${login_admin.roleCode == 1}">
                                <button class="layui-btn" id="updateUserLevel">修改会员等级</button>
                            </c:if>
                        </div>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="layui-form" lay-filter="layuiadmin-form-useradmin" id="updateUserLevelForm"
     style="padding: 10px 0 0 0;display: none">

    <div class="layui-form-item">
        <div class="layui-input-inline">
            <input type="hidden" id="id" name="id" autocomplete="off" class="layui-input" readonly="readonly">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">会员等级</label>
        <div class="layui-inline">
            <select name="memberLevel" lay-filter="level" id="level">
                <option value="">请选择会员等级</option>
                <option value="2">初级会员</option>
                <option value="3">中级会员</option>
                <option value="4">高级会员</option>
                <option value="5">企业初级会员</option>
                <option value="6">企业中级会员</option>
                <option value="7">企业高级会员</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="nameDiv">
        <label class="layui-form-label">姓名</label>
        <div class="layui-input-inline">
            <input type="text" name="phone" placeholder="请输入姓名" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="companyNameDiv">
        <label class="layui-form-label">企业名称</label>
        <div class="layui-input-inline">
            <input type="text" name="companyName" placeholder="请输入企业名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">过期时间</label>
        <div class="layui-input-inline">
            <input type="text" name="expirationTime" id="expirationTime" lay-verify="required" placeholder="请输入过期时间"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="updateUserLevel">立即提交</button>
        </div>
    </div>

</div>
</body>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/static/layuiadmin/area.js"></script>
<c:if test="${login_admin.roleCode == 1}">
    <script type="text/html" id="toolbar">
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i
                class="layui-icon layui-icon-delete"></i></a>
    </script>
</c:if>
<script>
    var html = "<option value=''>请选择代理省份</option>";
    for (var i = 0; i < provice.length; i++) {
        html = html + "<option value='" + provice[i]['name'] + "'>" + provice[i]['name'] + "</option>";

        document.getElementById("province").innerHTML = html;
    }
</script>
<script type="text/html" id="isBind">
    {{# if(d.isBind == 1) {d.isBind = '是'}   }}
    {{# if(d.isBind == 2) {d.isBind = '否'}   }}
    <span>{{d.isBind}}</span>
</script>

<script type="text/html" id="headPortrait">
    <img src="{{d.headPortrait}}" height="100%" width="100%">
</script>


<script type="text/html" id="memberLevel">
    {{# if(d.memberLevel == 1) {d.memberLevel = '普通'}   }}
    {{# if(d.memberLevel == 2) {d.memberLevel = '初级会员'}   }}
    {{# if(d.memberLevel == 3) {d.memberLevel = '中级会员'}   }}
    {{# if(d.memberLevel == 4) {d.memberLevel = '高级会员'}   }}
    {{# if(d.memberLevel == 5) {d.memberLevel = '初级企业会员'}   }}
    {{# if(d.memberLevel == 6) {d.memberLevel = '中级企业会员'}   }}
    {{# if(d.memberLevel == 7) {d.memberLevel = '高级企业会员'}   }}
    {{# if(d.memberLevel == 8) {d.memberLevel = '代理商'}   }}
    {{# if(d.memberLevel == 9) {d.memberLevel = '总监/职员'}   }}
    {{# if(d.memberLevel == 10) {d.memberLevel = '经销商'}   }}
    <span>{{d.memberLevel}}</span>
</script>

<script type="text/html" id="identity">
    {{# if(d.identity == 1) {d.identity = '会员'}   }}
    {{# if(d.identity == 2) {d.identity = '企业'}   }}
    {{# if(d.identity == 3) {d.identity = '代理商'}   }}
    {{# if(d.identity == 4) {d.identity = '经销商'}   }}
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

        //执行一个laydate实例
        laydate.render({
            elem: '#expirationTime',
            type: 'datetime'
        });

        //动态加载下拉框值
        $.ajax({
            url: '/dealer/listAll',
            type: "get",
            dataType: "json",
            success: function (result) {
                var data = result.data;

                var html = '<option value="">请选择经销商</option>';
                $.each(data, function (index, value) {
                    html += '<option value="' + value.id + '">' + value.name + '</option>';
                });
                $("#dealerId").html(html);
                form.render('select');
            }
        });


        var roleCode = "${login_admin.roleCode}";
        var registeredAddress = "";
        var superiorId = "";

        if (roleCode == 1) {   //企业管理员

        } else if (roleCode == 2) { //代理商管理员

            registeredAddress = "${login_admin.address}";

        } else if (roleCode == 3) { //企业管理员

        } else {                   //经销商管理员

            var userId = "${login_admin.userId}";

            if (userId == null) {
                superiorId = -1;  //不存在
            } else {
                superiorId = userId;
            }
        }

        table.render({
            elem: '#table',
            url: '/user/list?isBind=1&registeredAddress=' + registeredAddress + '&superiorId=' + superiorId,
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'wxid', title: '微信号', minWidth: 200},
                {field: 'nickname', title: '会员昵称', minWidth: 200},
                {field: 'phone', title: '会员手机号码', minWidth: 200},
                {field: 'headPortrait', title: '会员头像', templet: '#headPortrait', minWidth: 200},
                {field: 'isBind', title: '是否绑定手机', templet: '#isBind', minWidth: 200},
                {field: 'identity', title: '会员身份', templet: '#identity', minWidth: 200},
                {field: 'memberLevel', title: '会员等级', templet: '#memberLevel', minWidth: 200},
                {field: 'registeredAddress', title: '会员注册地区', minWidth: 200},
                {field: 'companyName', title: '公司名称', minWidth: 200},
                {field: 'agent_province', title: '代理省份', minWidth: 200},
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

        //监听select改变
        form.on('select(level)', function (data) {
            var value = data.value;
            if (value == 2 || value == 3 || value == 4) {

                //隐藏
                $("#companyNameDiv").hide();
                $("#nameDiv").hide();
            }
            if (value == 5 || value == 6 || value == 7) {
                //显示
                $("#companyNameDiv").show();
                $("#nameDiv").show();
            }
        });

        $('#export').on('click', function () {

            var identity = "";
            var memberLevel = "";
            var province = "";
            var dealerId = "";

            if (roleCode == 1) {
                identity = $('#selectIdentity option:selected').val();//选中的值
                memberLevel = $('#selectMemberLevel option:selected').val();
                province = $('#province option:selected').val();
                dealerId = $('#dealerId option:selected').val();
            }
            var nickname = $("#nickname").val();
            var phone = $("#phone").val();
            //获取搜索from表单里面的数据
            location.href = '/user/exportExcel?identity=' + identity + '&memberLevel=' + memberLevel + '&registeredAddress=' + registeredAddress + '&superiorId=' + superiorId + '&province=' + province + '&dealerId=' + dealerId + '&nickname=' + nickname + '&phone=' + phone;
        });

        var index = 0;
        //更新用户等级
        $('#updateUserLevel').on('click', function () {
            var checkStatus = table.checkStatus('table');
            var checkData = checkStatus.data; //得到选中的数据
            if (checkData.length != 1) {
                return layer.msg('请选择一行数据');
            }
            $("#id").val(checkData[0].id);
            //打开编辑窗口
            index = layer.open({
                type: 1,
                title: '修改会员等级',
                content: $('#updateUserLevelForm'),
                area: ['400px', '420px'],
                closeBtn: 1,
                end: function () {
                    window.location.reload();
                }
            });
        });

        //监听添加提交
        form.on('submit(updateUserLevel)', function (data) {
            var field = data.field;
            $.ajax({
                url: '/user/updateUserLevel',
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
                    url: '/user/updateUserDelete/' + ids,
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
                        url: '/user/updateUserDelete/' + data.id,
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
            }
        });

    });
</script>


</html>

