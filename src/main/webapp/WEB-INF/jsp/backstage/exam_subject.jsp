<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>试题分类管理</title>
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
                <div class="layui-form layui-card-header layuiadmin-card-header-auto">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <select name="categoryId" id="categoryId3">
                            </select>
                        </div>
                        <div class="layui-inline">
                            <select name="isFree">
                                <option value="">请选择是否免费</option>
                                <option value="1">免费</option>
                                <option value="2">收费</option>
                            </select>
                        </div>
                        <div class="layui-inline">
                            <select name="isHide">
                                <option value="">请选择是否隐藏</option>
                                <option value="1">显示</option>
                                <option value="2">隐藏</option>
                            </select>
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
    <%--添加表单--%>
    <div class="layui-form" lay-filter="layuiadmin-form-useradmin" id="addForm"
         style="padding: 10px 0 0 0;display: none">

        <div class="layui-form-item">
            <label class="layui-form-label">分类名称</label>
            <div class="layui-input-inline">
                <select name="categoryId" id="categoryId1">

                </select>
            </div>
        </div>

        <%--<div class="layui-form-item">--%>
        <%--<label class="layui-form-label">试题类型</label>--%>
        <%--<div class="layui-input-inline">--%>
        <%--<select name="type" id="type1">--%>
        <%--<option value="">请选择试题类型</option>--%>
        <%--<option value="1">心理类</option>--%>
        <%--<option value="2">其他</option>--%>
        <%--</select>--%>
        <%--</div>--%>
        <%--</div>--%>

        <div class="layui-form-item">
            <label class="layui-form-label">试题名称</label>
            <div class="layui-input-inline">
                <input type="text" name="name" lay-verify="required" placeholder="请输入试题名称" autocomplete="off"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">题目数量</label>
            <div class="layui-input-inline">
                <input type="text" name="questionNum" lay-verify="required" placeholder="请输入题目数量" autocomplete="off"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">是否分享</label>
            <div class="layui-input-inline">
                <select name="isShare" id="isShare1" lay-filter="isShare1">
                    <option value="">请选择</option>
                    <option value="1">是</option>
                    <option value="0">否</option>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">能力标签</label>
            <input type="hidden" name="abilitys" id="abilitys">
            <div class="layui-input-block" id="abilityList">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">标签</label>
            <input type="hidden" name="labels" id="labels">
            <div class="layui-input-block" id="labelList">
            </div>
        </div>

        <div class="layui-form-item" style="display: none" id="showExams">
            <%--<label class="layui-form-label">关联试题</label>--%>
            <%--<input type="hidden" name="exams" id="exams">--%>
            <%--<div class="layui-input-block" id="examList">--%>
            <%--</div>--%>
        </div>


        <div class="layui-form-item">
            <label class="layui-form-label">是否免费</label>
            <div class="layui-input-inline">
                <select name="isFree" id="isFree1" lay-filter="isFree1">
                    <option value="">请选择</option>
                    <option value="1">免费</option>
                    <option value="2">收费</option>
                </select>
            </div>
        </div>


        <div class="layui-form-item" style="display: none" id="showPrice1">
            <label class="layui-form-label">试题价格</label>
            <div class="layui-input-inline">
                <input type="text" name="price" id="price1" placeholder="请输入试题名称" autocomplete="off"
                       class="layui-input">
            </div>
        </div>


        <div class="layui-form-item" style="display: none" id="showCodes1">
            <label class="layui-form-label">是否免费</label>
            <input type="hidden" name="codes" id="codes1">
            <div class="layui-input-block">
                <input type='checkbox' name='code' lay-skin='primary' title="初级会员" value="2" lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' title="中级会员" value="3" lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' title="高级会员" value="4" lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' title="初级企业会员" value="5" lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' title="中级企业会员" value="6" lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' title="高级企业会员" value="7" lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' title="代理商" value="8" lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' title="企业总监" value="9" lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' title="企业职员" value="0" lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' title="经销商" value="1" lay-filter='filter'/>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">考试时长</label>
            <div class="layui-input-inline">
                <input type="text" name="examDuration" lay-verify="required" placeholder="请输入考试时长" autocomplete="off"
                       class="layui-input">
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
            <label class="layui-form-label">分类名称</label>
            <div class="layui-input-inline">
                <select name="categoryId" id="categoryId2">

                </select>
            </div>
        </div>

        <%--<div class="layui-form-item">--%>
        <%--<label class="layui-form-label">试题类型</label>--%>
        <%--<div class="layui-input-inline">--%>
        <%--<select name="type" id="type2">--%>
        <%--<option value="">请选择试题类型</option>--%>
        <%--<option value="1">心理类</option>--%>
        <%--<option value="2">其他</option>--%>
        <%--</select>--%>
        <%--</div>--%>
        <%--</div>--%>

        <div class="layui-form-item">
            <label class="layui-form-label">试题名称</label>
            <div class="layui-input-inline">
                <input type="text" name="name" id="name" lay-verify="required" placeholder="请输入试题名称" autocomplete="off"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">题目数量</label>
            <div class="layui-input-inline">
                <input type="text" name="questionNum" id="questionNum" lay-verify="required" placeholder="请输入题目数量"
                       autocomplete="off"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">是否分享</label>
            <div class="layui-input-inline">
                <select name="isShare" id="isShare2" lay-filter="isShare2">
                    <option value="">请选择</option>
                    <option value="1">是</option>
                    <option value="0">否</option>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">能力标签</label>
            <input type="hidden" name="abilitys" id="abilitys2">
            <div class="layui-input-block" id="abilityList2">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">标签</label>
            <input type="hidden" name="labels" id="labels2">
            <div class="layui-input-block" id="labelList2">
            </div>
        </div>

        <div class="layui-form-item" style="display: none" id="showExams2">
            <%--<label class="layui-form-label">关联视频</label>--%>
            <%--<input type="hidden" name="exams" id="exams2">--%>
            <%--<div class="layui-input-block" id="examList2">--%>
            <%--</div>--%>
        </div>


        <div class="layui-form-item">
            <label class="layui-form-label">是否免费</label>
            <div class="layui-input-inline">
                <select name="isFree" id="isFree2" lay-filter="isFree2">
                    <option value="">请选择</option>
                    <option value="1">免费</option>
                    <option value="2">收费</option>
                </select>
            </div>
        </div>


        <div class="layui-form-item" style="display: none" id="showPrice2">
            <label class="layui-form-label">试题价格</label>
            <div class="layui-input-inline">
                <input type="text" name="price" id="price2" placeholder="请输入试题价格" autocomplete="off"
                       class="layui-input">
            </div>
        </div>


        <div class="layui-form-item" style="display: none" id="showCodes2">
            <label class="layui-form-label">是否免费</label>
            <input type="hidden" name="codes" id="codes2">
            <div class="layui-input-block">
                <input type='checkbox' name='code' lay-skin='primary' id='code2' title="初级会员" value="2"
                       lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' id='code3' title="中级会员" value="3"
                       lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' id='code4' title="高级会员" value="4"
                       lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' id='code5' title="初级企业会员" value="5"
                       lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' id='code6' title="中级企业会员" value="6"
                       lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' id='code7' title="高级企业会员" value="7"
                       lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' id='code8' title="代理商" value="8"
                       lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' id="code9" title="企业总监" value="9"
                       lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' id="code0" title="企业职员" value="0"
                       lay-filter='filter'/>
                <input type='checkbox' name='code' lay-skin='primary' id='code1' title="经销商" value="1"
                       lay-filter='filter'/>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">考试时长</label>
            <div class="layui-input-inline">
                <input type="text" name="examDuration" id="examDuration" lay-verify="required" placeholder="请输入考试时长"
                       autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">状态</label>
            <div class="layui-input-block">
                <input type="radio" name="isHide" value="1" title="显示">
                <input type="radio" name="isHide" value="2" title="隐藏">
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
<script type="text/html" id="getType">
    {{# if(d.type == '1') {d.type = '心理类'}   }}
    {{# if(d.type == '2') {d.type = '其他'}   }}
    {{# if(d.type == null) {d.type = '其他'}   }}
    <span>{{d.type}}</span>
</script>
<script type="text/html" id="isFree">
    {{# if(d.isFree == '1') {d.isFree = '免费'}   }}
    {{# if(d.isFree == '2') {d.isFree = '收费'}   }}
    {{# if(d.isFree == null) {d.isFree = ''}   }}
    <span>{{d.isFree}}</span>
</script>
<script type="text/html" id="isShare">
    {{# if(d.isShare == '1') {d.isShare = '是'}   }}
    {{# if(d.isShare == '0') {d.isShare = '否'}   }}
    {{# if(d.isShare == null) {d.isShare = ''}   }}
    <span>{{d.isShare}}</span>
</script>
<script type="text/html" id="isHide">
    {{# if(d.isHide == '1') {d.isHide = '显示'}   }}
    {{# if(d.isHide == '2') {d.isHide = '隐藏'}   }}
    {{# if(d.isHide == null) {d.isHide = ''}   }}
    <span>{{d.isHide}}</span>
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
            url: '/exam/subject/list',
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'categoryName', title: '试题分类名称', minWidth: 200},
//                {field: 'type', title: '试题类型',minWidth:200,templet: '#getType'},
                {field: 'name', title: '试题名称', minWidth: 200},
//                {field: 'lables', title: '标签', minWidth:200},
                {field: 'questionNum', title: '题目数量', minWidth: 200},
                {field: 'isFree', title: '是否免费', templet: '#isFree', minWidth: 200},
                {field: 'isShare', title: '是否可以分享', templet: '#isShare', minWidth: 200},
                {field: 'price', title: '试题价格', minWidth: 200},
                {field: 'isHide', title: '是否隐藏', templet: '#isHide', minWidth: 200},
                {field: 'examDuration', title: '考试时长(分钟为单位)', width: 200},
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

        //获取所有能力标签
        $.ajax({
            url: '/exam/ability/listAll',
            type: "get",
            success: function (result) {
                data = result.data;
                var html = "";
                var html2 = "";
                $.each(data, function (index, value) {//获取所有key值
                    html += "<input type='checkbox' name='ability' lay-skin='primary'  title='" + value.name + "' value='" + value.id + "' lay-filter='ability' id='ability" + value.id + "'/>";
                });
                $.each(data, function (index, value) {//获取所有key值
                    html2 += "<input type='checkbox' name='ability' lay-skin='primary'  title='" + value.name + "' value='" + value.id + "' lay-filter='ability2' id='ability2" + value.id + "'/>";
                });
                $('#abilityList').html(html);
                $('#abilityList2').html(html2);
                form.render();
            }
        });

        //获取所有标签
        $.ajax({
            url: '/label/listAll',
            type: "get",
            success: function (result) {
                data = result.data;
                var html = "";
                var html2 = "";
                $.each(data, function (index, value) {//获取所有key值
                    html += "<input type='checkbox' name='label' lay-skin='primary'  title='" + value.name + "' value='" + value.id + "' lay-filter='label' id='label" + value.id + "'/>";
                });
                $.each(data, function (index, value) {//获取所有key值
                    html2 += "<input type='checkbox' name='label' lay-skin='primary'  title='" + value.name + "' value='" + value.id + "' lay-filter='label2' id='label2" + value.id + "'/>";
                });
                $('#labelList').html(html);
                $('#labelList2').html(html2);
                form.render();
            }
        });

        //监听复选框
        form.on('checkbox(label)', function (data) {

            var str = "";
            $("input:checkbox[name='label']:checked").each(function (i) {

                var val = $(this).val();
                str = str + val + ",";

            });
            if (str.length > 0) {
                str = str.substring(0, str.length - 1);

                //发送一个ajax请求
                //获取条件筛选的视频
                $.ajax({
                    url: '/exam/subject/getByLabels?labels=' + str,
                    type: "get",
                    success: function (result) {
                        var html = "";
                        var labels = str.split(",")
                        for (var i = 0; i < labels.length; i++) {

                            var html2 = "";
                            var list = result['list' + i];
                            $.each(list, function (index, value) {//获取所有key值
                                html2 += "<input type='checkbox' name='exam' lay-skin='primary'  title='" + value.name + "' value='" + value.id + "' lay-filter='exam' id='exam" + value.id + "'/>";
                            });

                            html = html + '<div class="layui-form-item">' +
                                '<label class="layui-form-label"></label>' +
                                '<div class="layui-input-block">' +
                                html2 +
                                '</div>' +
                                '</div>';
                        }



                        $('#showExams').html(html);
                        form.render();
                    }
                });
                $("#showExams").show();

            } else {
                $("#showExams").hide();
            }
        })

        //监听复选框
        form.on('checkbox(label2)', function (data) {

            var str = "";
            $("input:checkbox[name='label']:checked").each(function (i) {

                var val = $(this).val();
                str = str + val + ",";

            });
            if (str.length > 0) {
                str = str.substring(0, str.length - 1);

                //发送一个ajax请求
                //获取条件筛选的视频
                $.ajax({
                    url: '/exam/subject/getByLabels?labels=' + str,
                    type: "get",
                    success: function (result) {
                        var html = "";
                        var labels = str.split(",")
                        for (var i = 0; i < labels.length; i++) {

                            var html2 = "";
                            var list = result['list' + i];
                            $.each(list, function (index, value) {//获取所有key值
                                html2 += "<input type='checkbox' name='exam' lay-skin='primary'  title='" + value.name + "' value='" + value.id + "' lay-filter='exam2' id='exam2" + value.id + "'/>";
                            });
                            html = html + '<div class="layui-form-item">' +
                                '<label class="layui-form-label"></label>' +
                                '<div class="layui-input-block">' +
                                html2 +
                                '</div>' +
                                '</div>';
                        }

                        $('#showExams2').html(html);
                        form.render();
                    }
                });
                $("#showExams2").show();

            } else {
                $("#showExams2").hide();
            }
        });

        //动态加载下拉框值
        $.ajax({
            url: '/exam/category/listAll',
            type: "get",
            dataType: "json",
            success: function (result) {
                data = result.data;
                var html = '<option value="">请选择试题分类</option>';
                $.each(data, function (index, value) {
                    html += '<option value="' + value.id + '">' + value.name + '</option>';
                });
                $("#categoryId1").html(html);
                $("#categoryId2").html(html);
                $("#categoryId3").html(html);
                form.render('select');
            }
        });

        //触发增加事件
        $('#add').on('click', function () {
            //打开编辑窗口
            layer.open({
                type: 1,
                title: '添加试题信息',
                content: $('#addForm'),
                area: ['420px', '560px'],
                closeBtn: 1,
                end: function () {
                    window.location.reload();
                }
            });
        });
        //监听select改变
        form.on('select(isFree1)', function (data) {
            var value = data.value;
            if (value == 1) {  //免费
                //隐藏
                $("#showPrice1").hide();
                $("#showCodes1").hide();
            }
            if (value == 2) { //收费
                //显示
                $("#showPrice1").show();
                $("#showCodes1").show();
                $("#price1").val("");

            }
        });

        //监听添加提交
        form.on('submit(addSubmit)', function (data) {
            var field = data.field;
            field.type = 1;
            if (field.isFree == 1) {
                field.price = null;
            } else {
                var str = "[";
                $("input:checkbox[name='code']:checked").each(function () {

                    var val = $(this).val();
                    str = str + '"' + val + '",';

                });
                str = str.substring(0, str.length - 1);
                field.codes = str + "]";
            }

            var str = "";
            $("input:checkbox[name='label']:checked").each(function (i) {

                var val = $(this).val();
                str = str + val + ",";

            });
            if (str.length > 0) {
                field.labels = str.substring(0, str.length - 1);
            } else {
                field.labels = "";
            }
            var examStr = "";
            $("input:checkbox[name='exam']:checked").each(function (i) {

                var val = $(this).val();
                examStr = examStr + val + ",";

            });
            if (examStr.length > 0) {
                field.exams = examStr.substring(0, examStr.length - 1);
            } else {
                field.exams = "";
            }

            var abilityStr = "";
            var abilityNameStr = "";
            $("input:checkbox[name='ability']:checked").each(function (i) {


                var val = $(this).val();
                var abilityName = $(this)[0].title;
                abilityStr = abilityStr + val + ",";
                abilityNameStr = abilityNameStr + abilityName + ",";

            });
            if (abilityStr.length != 12) {
                layer.msg('请选择6个能力标签');
                return;
            }
            if (abilityStr.length > 0) {
                field.abilitys = abilityStr.substring(0, abilityStr.length - 1);
            } else {
                field.abilitys = "";
            }

            if (abilityNameStr.length > 0) {
                field.abilityNames = abilityNameStr.substring(0, abilityNameStr.length - 1);
            } else {
                field.abilityNames = "";
            }

            $.ajax({
                url: '/exam/subject/add',
                type: "post",
                data: field,
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code = 200) {
                        //提示信息
                        layer.msg('添加成功');
                        //刷新数据
                        table.reload('table');
                        //关闭弹窗
                        layer.closeAll();

                    } else {
                        layer.msg(result.message);
                        //刷新数据
                        table.reload('table');
                        //关闭弹窗
                        layer.closeAll();
                    }
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
                    url: '/exam/subject/deletes/' + ids,
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
                        url: '/exam/subject/deletes/' + data.id,
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
                    return false;
                });
            } else if (layEvent === 'edit') { //编辑
                //打开编辑窗口
                layer.open({
                    type: 1,
                    title: '编辑试题信息',
                    content: $('#updateForm'),
                    area: ['420px', '560px'],
                    closeBtn: 1,
                    end: function () {
                        window.location.reload();
                    }
                });
                //赋值
                $("#id").val(data.id);
                $("#name").val(data.name);
                $("#questionNum").val(data.questionNum);
                $("#examDuration").val(data.examDuration);
                if (data.isFree == 2) {
                    //显示
                    $("#showPrice2").show();
                    $("#showCodes2").show();
                    $("#price2").val(data.price);

                    var codes = data.codes;

                    if (codes != null) {
                        var code = JSON.parse(codes);
                        for (var i = 0; i < code.length; i++) {
                            $("#code" + code[i]).prop('checked', true);
                            $("#code" + code[i]).addClass('layui-form-checked');
                            form.render();
                        }
                    }


                } else {
                    //隐藏
                    $("#showPrice2").hide();
                    $("#showCodes2").hide();
                }

                $("input:radio[value='" + data.isHide + "']").attr('checked', 'true');
                $("#type2").find("option[value = '" + data.type + "']").attr("selected", "selected");
                $("#isFree2").find("option[value = '" + data.isFree + "']").attr("selected", "selected");
                $("#isShare2").find("option[value = '" + data.isShare + "']").attr("selected", "selected");
                $("#categoryId2").find("option[value = '" + data.categoryId + "']").attr("selected", "selected");

                var labels = data.labels;
                if (labels != null) {
                    var label = labels.split(",");
                    for (var i = 0; i < label.length; i++) {

                        $("#label2" + label[i]).prop('checked', true);
                        $("#label2" + label[i]).addClass('layui-form-checked');
                    }
                }
                form.render();
                var abilitys = data.abilitys;
                if (abilitys != null) {
                    var ability = abilitys.split(",");
                    for (var i = 0; i < ability.length; i++) {

                        $("#ability2" + ability[i]).prop('checked', true);
                        $("#ability2" + ability[i]).addClass('layui-form-checked');
                    }
                }
                form.render();

                var exams = data.exams;

                if (exams != null && exams.length > 0) {
                    //发送一个ajax请求
                    //获取条件筛选的视频
                    $.ajax({
                        url: '/exam/subject/getByLabels?labels=' + labels,
                        type: "get",
                        success: function (result) {
                            var html = "";
                            //var labels = labels.split(",");
                            for (var i = 0; i < Object.keys(result).length; i++) {

                                var html2 = "";
                                var list = result['list' + i];
                                $.each(list, function (index, value) {//获取所有key值
                                    html2 += "<input type='checkbox' name='exam' lay-skin='primary'  title='" + value.name + "' value='" + value.id + "' lay-filter='exam2' id='exam2" + value.id + "'/>";
                                });
                                html = html + '<div class="layui-form-item">' +
                                    '<label class="layui-form-label"></label>' +
                                    '<div class="layui-input-block">' +
                                    html2 +
                                    '</div>' +
                                    '</div>';
                            }

                            $('#showExams2').html(html);
                            form.render();
                            $("#showExams2").show();

                            var exam = exams.split(",");
                            for (var i = 0; i < exam.length; i++) {

                                $("#exam2" + exam[i]).prop('checked', true);
                                $("#exam2" + exam[i]).addClass('layui-form-checked');
                            }
                            form.render();
                            $("#showExams2").show();
                        }
                    });
                } else {
                    $("#showExams2").hide();
                }

                //监听select改变
                form.on('select(isFree2)', function (data) {
                    var value = data.value;

                    if (value == 1) {  //免费
                        //隐藏
                        $("#showPrice2").hide();
                        $("#showCodes2").hide();
                    }
                    if (value == 2) { //收费
                        //显示
                        $("#showPrice2").show();
                        $("#showCodes2").show();
                        $("#price2").val("");
                    }
                });
                //监听更新提交
                form.on('submit(updateSubmit)', function (data) {
                    var field = data.field;
                    field.type = 1;
                    if (field.isFree == 1) {
                        field.price = null;
                    } else {
                        var str = "[";
                        $("input:checkbox[name='code']:checked").each(function () {

                            var val = $(this).val();
                            str = str + '"' + val + '",';

                        });
                        str = str.substring(0, str.length - 1);
                        field.codes = str + "]";
                    }
                    var str = "";
                    $("input:checkbox[name='label']:checked").each(function (i) {

                        var val = $(this).val();
                        str = str + val + ",";

                    });
                    if (str.length > 0) {
                        field.labels = str.substring(0, str.length - 1);
                    } else {
                        field.labels = "";
                    }
                    var examStr = "";
                    $("input:checkbox[name='exam']:checked").each(function (i) {

                        var val = $(this).val();
                        examStr = examStr + val + ",";

                    });
                    if (examStr.length > 0) {
                        field.exams = examStr.substring(0, examStr.length - 1);
                    } else {
                        field.exams = "";
                    }

                    var abilityStr = "";
                    var abilityNameStr = "";
                    $("input:checkbox[name='ability']:checked").each(function (i) {

                        var val = $(this).val();
                        var abilityName = $(this)[0].title;
                        abilityStr = abilityStr + val + ",";
                        abilityNameStr = abilityNameStr + abilityName + ",";

                    });
                    if (abilityStr.length != 12) {
                        layer.msg('请选择6个能力标签');
                        return;
                    }
                    if (abilityStr.length > 0) {
                        field.abilitys = abilityStr.substring(0, abilityStr.length - 1);
                    } else {
                        field.abilitys = "";
                    }

                    if (abilityNameStr.length > 0) {
                        field.abilityNames = abilityNameStr.substring(0, abilityNameStr.length - 1);
                    } else {
                        field.abilityNames = "";
                    }
                    $.ajax({
                        url: '/exam/subject/update',
                        type: "post",
                        data: field,
                        dataType: "json",
                        async: false,
                        success: function (result) {
                            if (result.code == 200) {
                                layer.msg('更新成功');
                                //刷新数据
                                table.reload('table');
                                //关闭弹窗
                                layer.closeAll();
                            } else {
                                layer.msg(result.message);
                                //关闭弹窗
                                layer.closeAll();
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