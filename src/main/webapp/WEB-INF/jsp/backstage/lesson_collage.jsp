<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>课程管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/template.css" media="all">
</head>
<style>
    .layui-table-cell .layui-form-checkbox[lay-skin=primary], .layui-table-cell .layui-form-radio[lay-skin=primary] {
        top: 0px;
        vertical-align: middle;
    }

    .myLabel {
        width: 100px;
    }

    .myInput {
        width: 200px;
    }
</style>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <%--<div class="layui-form layui-card-header layuiadmin-card-header-auto">--%>
                <%--<div class="layui-form-item">--%>
                <%--<div class="layui-inline">--%>
                <%--<div class="layui-input-inline">--%>
                <%--<input type="text" name="name" placeholder="请输入代理商名称" autocomplete="off"--%>
                <%--class="layui-input myInput">--%>
                <%--</div>--%>
                <%--</div>--%>

                <%--<div class="layui-inline">--%>
                <%--<div class="layui-input-inline">--%>
                <%--<input type="text" name="phone" placeholder="请输入代理商手机号" autocomplete="off"--%>
                <%--class="layui-input myInput">--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-inline">--%>
                <%--<button class="layui-btn layuiadmin-btn-useradmin" data-type="reload" lay-submit--%>
                <%--lay-filter="search">--%>
                <%--<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>--%>
                <%--</button>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>
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
        <label class="layui-form-label myLabel">课程名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" lay-verify="required" maxlength="20" placeholder="请输入课程名称,最多输入20字"
                   autocomplete="off" class="layui-input myInput">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">课程标题</label>
        <div class="layui-input-inline">
            <input type="text" name="title" lay-verify="required" maxlength="50" placeholder="请输入课程标题,最多输入50字
                   autocomplete=" off" class="layui-input myInput">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">封面图</label>
        <button type="button" class="layui-btn" id="uploadFile1">
            <i class="layui-icon">&#xe67c;</i>上传
        </button>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="coverImg" id="coverImg1" autocomplete="off" class="layui-input myInput">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="showCover1">
        <label class="layui-form-label myLabel">封面图显示</label>
        <div class="layui-input-inline">
            <img src="" id="showCoverImg1" width="100%" height="20%"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">拼团成功数量</label>
        <div class="layui-input-inline">
            <input type="text" name="num" lay-verify="number" placeholder="请输入拼团成功数量"
                   autocomplete="off" class="layui-input myInput">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">价格</label>
        <div class="layui-input-inline">
            <input type="text" name="price" lay-verify="required" placeholder="请输入拼团价格"
                   autocomplete="off" class="layui-input myInput">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">省份</label>
        <div class="layui-input-inline">
            <select name="province" id="province1" lay-filter="province1">
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">市</label>
        <div class="layui-input-inline">
            <select name="city" id="city1" lay-filter="city1">
            </select>
        </div>
    </div>

    <div class="layui-form-item ">
        <label class="layui-form-label myLabel">区</label>
        <div class="layui-input-inline">
            <select name="area" id="area1">
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">拼团天数</label>
        <div class="layui-input-inline">
            <input type="text" name="days" lay-verify="number" placeholder="请输入拼团天数"
                   autocomplete="off" class="layui-input myInput">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">商品描述</label>
        <div id="contentEditor1" class="layui-input-block">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">是否提供地址</label>
        <div class="layui-input-inline">
            <select name="isProvideAddress" id="isProvideAddress1" lay-filter="isProvideAddress1">
                <option value="">请选择</option>
                <option value="1">是</option>
                <option value="2">否</option>
            </select>
        </div>
    </div>

    <%--<div class="layui-form-item" id="showBeginTimeDiv1" style="display: none">--%>
    <%--<label class="layui-form-label myLabel">开课时间</label>--%>
    <%--<div class="layui-input-inline">--%>
    <%--<input type="text" name="beginTime" id="beginTime1"  placeholder="请输入开课时间" autocomplete="off" class="layui-input myInput">--%>
    <%--</div>--%>
    <%--</div>--%>

    <div class="layui-form-item" id="showAddressDiv1" style="display: none">
        <label class="layui-form-label myLabel">地址</label>
        <div class="layui-input-inline">
            <input type="text" name="address" placeholder="请输入开课地址"
                   autocomplete="off" class="layui-input myInput">
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
            <input type="hidden" id="id" name="id" autocomplete="off" class="layui-input myInput" readonly="readonly">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">课程名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" lay-verify="required" id="name" maxlength="20" placeholder="请输入课程名称,最多输入20字"
                   autocomplete="off" class="layui-input myInput">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">课程标题</label>
        <div class="layui-input-inline myLabel">
            <input type="text" name="title" lay-verify="required" id="title" maxlength="50"
                   placeholder="请输入课程标题,最多输入50字"
                   autocomplete="off" class="layui-input myInput">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">封面图</label>
        <button type="button" class="layui-btn" id="uploadFile2">
            <i class="layui-icon">&#xe67c;</i>修改
        </button>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="coverImg" id="coverImg2" autocomplete="off" class="layui-input myInput">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">封面图显示</label>
        <div class="layui-input-inline">
            <img src="" id="showCoverImg2" width="100%" height="20%"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel" style="width: 100px">拼团成功数量</label>
        <div class="layui-input-inline">
            <input type="text" name="num" id="num" lay-verify="number" placeholder="请输入拼团成功数量"
                   autocomplete="off" class="layui-input myInput">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">价格</label>
        <div class="layui-input-inline">
            <input type="text" name="price" id="price" lay-verify="required" placeholder="请输入拼团价格"
                   autocomplete="off" class="layui-input myInput">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">省份</label>
        <div class="layui-input-inline">
            <select name="province" lay-verify="required" id="province2" lay-filter="province2">
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">市</label>
        <div class="layui-input-inline">
            <select name="city" lay-verify="required" id="city2" lay-filter="city2">
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">区</label>
        <div class="layui-input-inline">
            <select name="area" lay-verify="required" id="area2" lay-filter="area2">
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">拼团天数</label>
        <div class="layui-input-inline">
            <input type="text" name="days" id="days" lay-verify="number" placeholder="请输入拼团天数"
                   autocomplete="off" class="layui-input myInput">
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">课程详情</label>
        <div id="contentEditor2" class="layui-input-block">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">是否提供地址</label>
        <div class="layui-input-inline">
            <select name="isProvideAddress" id="isProvideAddress2" lay-filter="isProvideAddress2">
                <option value="">请选择</option>
                <option value="1">是</option>
                <option value="2">否</option>
            </select>
        </div>
    </div>

    <%--<div class="layui-form-item" id="showBeginTimeDiv2" style="display: none">--%>
    <%--<label class="layui-form-label myLabel">开课时间</label>--%>
    <%--<div class="layui-input-inline">--%>
    <%--<input type="text" name="beginTime" id="beginTime2"  placeholder="请输入开课时间" autocomplete="off" class="layui-input myInput">--%>
    <%--</div>--%>
    <%--</div>--%>

    <div class="layui-form-item" id="showAddressDiv2" style="display: none">
        <label class="layui-form-label myLabel">地址</label>
        <div class="layui-input-inline">
            <input type="text" name="address" id="address" placeholder="请输入开课地址"
                   autocomplete="off" class="layui-input myInput">
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
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/lay/modules/layim.js"></script>
<script src="${pageContext.request.contextPath}/static/layuiadmin/wangEditor.min.js"></script>
<script src="${pageContext.request.contextPath}/static/layuiadmin/area.js"></script>
<script type="text/html" id="toolbar">
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
            class="layui-icon layui-icon-edit"></i></a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i
            class="layui-icon layui-icon-delete"></i></a>
</script>

<script>
    var html = "<option value=''></option>";
    for (var i = 0; i < provice.length; i++) {
        html = html + "<option value='" + i + "'>" + provice[i]['name'] + "</option>";

        document.getElementById("province1").innerHTML = html;
        document.getElementById("province2").innerHTML = html;
    }
</script>

<script type="text/html" id="coverImg">
    <img src="{{d.coverImg}}" height="100%" width="100%">
</script>
<script type="text/html" id="isProvideAddress">
    {{# if(d.isProvideAddress == 1) {d.isProvideAddress = '是'}   }}
    {{# if(d.isProvideAddress == 2) {d.isProvideAddress = '否'}   }}
    {{# if(d.isProvideAddress == null) {d.isHide = ''}   }}
    <span>{{d.isProvideAddress}}</span>
</script>
<script>

    var url = 'https://pc.leanmarketing.cn/upload/ueditorUpload';

    layui.config({
        base: '${pageContext.request.contextPath}/static/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'table', 'laydate'], function () {
        var $ = layui.jquery,
            table = layui.table,
            layer = layui.layer,
            laydate = layui.laydate,
            upload = layui.upload,
            form = layui.form;


        // 富文本编辑器（1是新增，2是修改）
        var E = window.wangEditor;
        var editor1 = new E('#contentEditor1');

        var xhr = new XMLHttpRequest();
        editor1.customConfig.uploadImgServer = url;  // 上传图片到服务器
        editor1.customConfig.uploadImgMaxSize = 10 * 1024 * 1024;
        editor1.customConfig.uploadImgHeaders = {
            'Accept': 'application/json;charset=UTF-8',
            'X-Requested-With': 'XMLHttpRequest'
        };
        editor1.customConfig.uploadImgHooks = {
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

        var editor2 = new E('#contentEditor2');

        editor2.customConfig.uploadImgServer = url;  // 上传图片到服务器
        editor2.customConfig.uploadImgMaxSize = 10 * 1024 * 1024;
        editor2.customConfig.uploadImgHeaders = {
            'Accept': 'application/json;charset=UTF-8',
            'X-Requested-With': 'XMLHttpRequest'
        };
        editor2.customConfig.uploadImgHooks = {
            before: function (xhr, editor2, files) {

            },
            success: function (xhr, editor2, result) {

                return {
                    prevent: true,
                    msg: '上传成功~'
                }
            },
            fail: function (xhr, editor2, result) {
                return {
                    prevent: true,
                    msg: '图片插入错误，请重新上传图片！'
                }
            },
            error: function (xhr, editor2) {
                return {
                    prevent: true,
                    msg: '上传失败，请重新上传图片！'
                }
            },
            timeout: function (xhr, editor2) {

                return {
                    prevent: true,
                    msg: '上传超时，请重新上传图片！'
                }
            }
        };
        editor1.create();
        editor2.create();

        table.render({
            elem: '#table',
            url: '/lesson/collage/list',
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'name', title: '课程名称', minWidth: 200},
                {field: 'title', title: '课程标题', minWidth: 200},
                {field: 'cover_img', title: '封面图', templet: '#coverImg', minWidth: 200},
                {field: 'num', title: '拼团成功数量', minWidth: 200},
                {field: 'price', title: '价格', minWidth: 200},
                {field: 'province', title: '省', minWidth: 200},
                {field: 'city', title: '市', minWidth: 200},
                {field: 'area', title: '区', minWidth: 200},
                {field: 'content', title: '详情', minWidth: 200},
//                {
//                    field: 'beginTime',
//                    title: '开课时间',
//                    templet: '<div>{{layui.util.toDateString(d.beginTime, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
//                    ,minWidth:200
//                },
//                {
//                    field: 'endTime',
//                    title: '拼团结束时间',
//                    templet: '<div>{{layui.util.toDateString(d.endTime, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
//                    ,minWidth:200
//                },
                {field: 'days', title: '拼团天数', minWidth: 200},
                {field: 'isProvideAddress', title: '是否提供地址', minWidth: 200, templet: '#isProvideAddress'},
                {field: 'address', title: '地址', minWidth: 200},
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
                none: '暂无相关数据'
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
            elem: '#beginTime1',
            type: 'datetime'
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#beginTime2',
            type: 'datetime'
        });

        upload.render({
            elem: '#uploadFile1',
            url: '/upload/uploadFile',
            accept: 'file',
            before: function (obj) {
                layer.load(2, {shade: false});
            },
            done: function (res) {
                if (res.msg = 'ok') {
                    $("#showCover1").show();
                    $("#coverImg1").val(res.data);
                    $("#showCoverImg1").attr("src", res.data);
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

                    $("#coverImg2").val(res.data);
                    $("#showCoverImg2").attr("src", res.data);
                }
                layer.closeAll("loading");
            }
        });

        //监听select改变
        form.on('select(province1)', function (data) {
            var citys = provice[data.value]["city"];
            var html = "<option value='请选择城市'></option>";
            for (var j = 0; j < citys.length; j++) {
                html = html + "<option value='" + j + "'>" + citys[j].name + "</option>";
                document.getElementById("city1").innerHTML = html;
            }
            form.render('select');
        });

        form.on('select(province2)', function (data) {
            var citys = provice[data.value]["city"];
            var html = "<option value='请选择城市'></option>";
            for (var j = 0; j < citys.length; j++) {
                html = html + "<option value='" + j + "'>" + citys[j].name + "</option>";
                document.getElementById("city2").innerHTML = html;
            }
            form.render('select');
        });

        //监听select改变
        form.on('select(city1)', function (data) {
            //拿到
            var index = $('#province1 option:selected').val();
            var areas = provice[index]["city"][data.value].districtAndCounty;
            var html = "<option value='请选择区'></option>";
            for (var j = 0; j < areas.length; j++) {
                html = html + "<option value='" + j + "'>" + areas[j] + "</option>";
                document.getElementById("area1").innerHTML = html;
            }
            form.render('select');
        });

        form.on('select(city2)', function (data) {
            //拿到
            var index = $('#province2 option:selected').val();

            var areas = provice[index]["city"][data.value].districtAndCounty;

            var html = "<option value='请选择区'></option>";
            for (var j = 0; j < areas.length; j++) {
                html = html + "<option value='" + j + "'>" + areas[j] + "</option>";
                document.getElementById("area2").innerHTML = html;
            }
            form.render('select');
        });

        //监听select改变
        form.on('select(isProvideAddress1)', function (data) {
            var value = data.value;
            if (value == 1) {  //是
                //显示
                $("#showBeginTimeDiv1").show();
                $("#showAddressDiv1").show();
            }
            if (value == 2) {
                //隐藏
                $("#showBeginTimeDiv1").hide();
                $("#showAddressDiv1").hide();
            }
        });

        //监听select改变
        form.on('select(isProvideAddress2)', function (data) {
            var value = data.value;
            if (value == 1) {  //是
                //隐藏
                $("#showBeginTimeDiv2").show();
                $("#showAddressDiv2").show();
            }
            if (value == 2) { //收费
                //显示
                $("#showBeginTimeDiv2").hide();
                $("#showAddressDiv2").hide();
            }
        });

        var index = 0;
        //触发增加事件
        $('#add').on('click', function () {

            //打开编辑窗口
            index = layer.open({
                type: 1,
                title: '添加课程信息',
                content: $('#addForm'),
                area: ['800px', '800px'],
                closeBtn: 1,
                end: function () {
                    window.location.reload();
                }
            });
        });

        //监听添加提交
        form.on('submit(addSubmit)', function (data) {
            var field = data.field;
            var provinceSelect = document.getElementById("province1");
            var provinceIndex = provinceSelect.selectedIndex;
            data.field.province = provinceSelect.options[provinceIndex].text;

            var citySelect = document.getElementById("city1");
            var cityIndex = citySelect.selectedIndex;
            data.field.city = citySelect.options[cityIndex].text;

            var areaSelect = document.getElementById("area1");
            var areaIndex = areaSelect.selectedIndex;
            data.field.area = areaSelect.options[areaIndex].text;

            data.field.content = editor1.txt.html();

            $.ajax({
                url: '/lesson/collage/add',
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
                    url: '/lesson/collage/deletes/' + ids,
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
                        url: '/lesson/collage/deletes/' + data.id,
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
                    title: '编辑课程信息',
                    content: $('#updateForm'),
                    area: ['800px', '800px'],
                    closeBtn: 1,
                    end: function () {
                        table.reload('table');
                    }
                });
                //赋值
                $("#id").val(data.id);
                $("#name").val(data.name);
                $("#title").val(data.title);
                $("#showCoverImg2").attr("src", data.coverImg);
                $("#coverImg2").val(data.coverImg);
                $("#num").val(data.num);
                $("#price").val(data.price);
                editor2.txt.html(data.content);

                var provinceIndex = "";

                for (var p = 0; p < provice.length; p++) {

                    if (provice[p]['name'] == data.province) {
                        provinceIndex = p;
                    }
                }
                $("#province2").find("option[value = '" + provinceIndex + "']").attr("selected", "selected");

                $("#city2").html('<option value="' + data.city + '">' + data.city + '</option>');
                $("#area2").html('<option value="' + data.area + '">' + data.area + '</option>');

                $("#isProvideAddress2").find("option[value = '" + data.isProvideAddress + "']").attr("selected", "selected");

                $("#beginTime2").val(format(data.beginTime));

                function format(date) {
                    var time = new Date(date);
                    var y = time.getFullYear();
                    var m = time.getMonth() + 1;
                    var d = time.getDate();
                    var h = time.getHours();
                    var mm = time.getMinutes();
                    var s = time.getSeconds();
                    return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
                }

                function add0(m) {
                    return m < 10 ? '0' + m : m;
                }

                $("#days").val(data.days);
                $("#address").val(data.address);
                form.render();
                if (data.isProvideAddress == 1) {  //是
                    //显示
                    $("#showBeginTimeDiv2").show();
                    $("#showAddressDiv2").show();
                }
                if (data.isProvideAddress == 2) {
                    //隐藏
                    $("#showBeginTimeDiv2").hide();
                    $("#showAddressDiv2").hide();
                }
                form.render();
                //监听更新提交
                form.on('submit(updateSubmit)', function (data) {
                    var field = data.field;
                    var provinceSelect = document.getElementById("province2");
                    var provinceIndex = provinceSelect.selectedIndex;
                    data.field.province = provinceSelect.options[provinceIndex].text;

                    var citySelect = document.getElementById("city2");
                    var cityIndex = citySelect.selectedIndex;
                    data.field.city = citySelect.options[cityIndex].text;

                    var areaSelect = document.getElementById("area2");
                    var areaIndex = areaSelect.selectedIndex;
                    data.field.area = areaSelect.options[areaIndex].text;

                    data.field.content = editor2.txt.html();
                    $.ajax({
                        url: '/lesson/collage/update',
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
</script>


</html>

