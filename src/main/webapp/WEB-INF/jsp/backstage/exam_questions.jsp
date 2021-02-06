<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>评估管理</title>
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
                                <input type="text" name="examName" placeholder="请输入试题名称" autocomplete="off"
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
        <label class="layui-form-label">分类名称</label>
        <div class="layui-input-inline">
            <select id="categoryId1" lay-filter="categoryId1">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">试题名称</label>
        <div class="layui-input-inline">
            <select name="subjectId" id="subjectId1" lay-filter="subjectId1">

            </select>
        </div>
    </div>

    <%--<div class="layui-form-item">--%>
    <%--<label class="layui-form-label">试题分数</label>--%>
    <%--<div class="layui-input-inline">--%>
    <%--<input type="text" name="score" lay-verify="required" placeholder="请输入试题分数" autocomplete="off" class="layui-input">--%>
    <%--</div>--%>
    <%--</div>--%>

    <div class="layui-form-item">
        <label class="layui-form-label">试题编码</label>
        <div class="layui-input-inline">
            <select name="code" id="code1" lay-verify="required">

            </select>
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">题目描述</label>
        <div class="layui-input-block">
            <textarea name="questions" placeholder="请输入试题题目描述" class="layui-textarea"></textarea>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">题目选项</label>
        <div class="layui-input-inline">
            <input type="button" id="addOptions1" class="layui-btn" value="添加试题题目选项">
        </div>
    </div>

    <div class="layui-form-item" id="spots">
        <%--<div class="layui-inline">--%>
        <%--<label class="layui-form-label"></label>--%>
        <%--<div class="layui-input-inline">--%>
        <%--<input type="text" name="option[0]"  placeholder="试题题目选项" autocomplete="off" class="layui-input">--%>
        <%--</div>--%>
        <%--</div>--%>
    </div>

    <%--<div class="layui-form-item" style="display: none">--%>
    <%--<label class="layui-form-label">题目选项</label>--%>
    <%--<div class="layui-input-inline">--%>
    <%--<input type="text" name="options" id="op1"  autocomplete="off" class="layui-input">--%>
    <%--</div>--%>
    <%--</div>--%>

    <div class="layui-form-item" id="showAnswerDiv" style="display: none;">
        <label class="layui-form-label">试题答案</label>
        <div class="layui-input-inline">
            <input type="text" name="answer" placeholder="请输入试题标题" autocomplete="off"
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
            <select id="categoryId2" lay-filter="categoryId2">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">试题名称</label>
        <div class="layui-input-inline">
            <select name="subjectId" id="subjectId2" lay-filter="subjectId2">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">试题编码</label>
        <div class="layui-input-inline">
            <select name="code" id="code2" lay-verify="required">

            </select>
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">题目描述</label>
        <div class="layui-input-block">
            <textarea name="questions" id="questions" placeholder="请输入试题题目描述" class="layui-textarea"></textarea>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">题目选项</label>
        <div class="layui-input-inline">
            <input type="button" id="addOptions2" class="layui-btn" value="添加试题题目选项">
        </div>
    </div>

    <div class="layui-form-item" id="spots2">
        <%--<div class="layui-inline">--%>
        <%--<label class="layui-form-label"></label>--%>
        <%--<div class="layui-input-inline">--%>
        <%--</div>--%>
        <%--</div>--%>
    </div>

    <%--<div class="layui-form-item" style="display: none">--%>
    <%--<label class="layui-form-label">题目选项</label>--%>
    <%--<div class="layui-input-inline">--%>
    <%--<input type="text" name="options" id="op2"  autocomplete="off" class="layui-input">--%>
    <%--</div>--%>
    <%--</div>--%>


    <div class="layui-form-item" id="showAnswerDiv2" style="display: none">
        <label class="layui-form-label">试题答案</label>
        <div class="layui-input-inline">
            <input type="text" name="answer" id="answer" placeholder="请输入试题标题" autocomplete="off"
                   class="layui-input">
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
<script>
    var count1 = 0;
    var count2 = 0;
    var html1 = "";
    var html2 = "";
    var examAbilityList;

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
            element = layui.element;
        table.render({
            elem: '#table',
            url: '/exam/questions/list',
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'subjectName', title: '试题名称', minWidth: 200},
//                {field: 'score', title: '试题分数',minWidth:200},
                {field: 'code', title: '第几道题', minWidth: 200},
                {field: 'questions', title: '试题题目描述', minWidth: 200},
                {field: 'options', title: '试题选项', minWidth: 200},
                {field: 'answer', title: '试题答案', minWidth: 200},
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
                form.render('select');
            }
        });


        //新增试题选项窗口
        $('#addOptions1').on('click', function () {


            var value = ($("#subjectId1").val());

            //试题名称没有选中的话,直接返回
            if (value == '' || value == null) {

                layer.msg("请选择试题");
                return;

            } else {

                var dataType = $("#subjectId1").find("option:selected").attr("data-type");

                if (dataType == 1) {  //说明是心理类

                    $("#showAnswerDiv").hide();

//                    $.each(examAbilityList, function (index, value) {
//                        html1 += '<option  value="' + value.id + '">' + value.name + '</option>';
//                    });

                    $('#spots').append(
                        '<div class=layui-inline id=optionDiv' + count1 + '>' +
                        '<div class="layui-inline">' +
                        '<label class="layui-form-label"></label>' +
                        '<div class="layui-input-inline"> ' +
                        '<input type="text" name="examOptions[' + count1 + '].name" placeholder="试题选项" autocomplete="off" class="layui-input"> ' +
                        '</div>' +
                        '</div>' +
                        '<div class="layui-inline">' +
                        '<label class="layui-form-label"></label>' +
                        '<div class="layui-input-inline"> ' +
                        '<select name="examOptions[' + count1 + '].abilityId" >' +
                        '<option  value="">请选择评估能力</option>' +
                        html1 +
                        '</select>' +
                        '</div>' +
                        '</div>' +
                        '<div class="layui-inline">' +
                        '<label class="layui-form-label"></label>' +
                        '<div class="layui-input-inline"> ' +
                        '<input type="text" name="examOptions[' + count1 + '].score"  placeholder="选项分数" autocomplete="off" class="layui-input"> ' +
                        '</div> ' +
                        '</div>' +
                        '<input type="button" id="deleteBuys' + count1 + '" onclick="deleteBuy(' + count1 + ')" class="layui-btn layui-btn-danger" value="移除">' +
                        '</div>'
                    );
                    form.render('select');
                }
//                else {
//
//                    $('#spots').append(
//                        '<div class=layui-inline id=optionDiv' + count1 + '>' +
//                        '<div class="layui-inline">' +
//                        '<label class="layui-form-label"></label>' +
//                        '<div class="layui-input-inline"> ' +
//                        '<input type="text" name="examOptions[' + count1 + '].name"  placeholder="请输入试题选项" autocomplete="off" class="layui-input"> ' +
//                        '</div>' +
//                        '<input type="button" id="deleteBuys' + count1 + '" onclick="deleteBuy(' + count1 + ')" class="layui-btn layui-btn-danger" value="移除">' +
//                        '</div>' +
//                        '</div>'
//                    );
//                    $("#showAnswerDiv").show();
//                }
            }
            count1++;
        });


        //编辑产品窗口，触发购买方式触发增加事件
        $('#addOptions2').on('click', function () {


            var value = ($("#subjectId2").val());

            //试题名称没有选中的话,直接返回
            if (value == '' || value == null) {


                layer.msg("请选择试题");
                return;

            } else {

                var dataType = $("#subjectId2").find("option:selected").attr("data-type");

                if (dataType == 1) {  //说明是心理类

                    $("#showAnswerDiv2").hide();
                    console.log(html1);

                    $('#spots2').append(
                        '<div class=layui-inline id=optionDiv' + count2 + '>' +
                        '<div class="layui-inline">' +
                        '<label class="layui-form-label"></label>' +
                        '<div class="layui-input-inline"> ' +
                        '<input type="text" name="examOptions[' + count2 + '].name" placeholder="试题选项" autocomplete="off" class="layui-input"> ' +
                        '</div>' +
                        '</div>' +
                        '<div class="layui-inline">' +
                        '<label class="layui-form-label"></label>' +
                        '<div class="layui-input-inline"> ' +
                        '<select name="examOptions[' + count2 + '].abilityId" >' +
                        '<option  value="">请选择评估能力</option>' +
                        html1 +
                        '</select>' +
                        '</div>' +
                        '</div>' +
                        '<div class="layui-inline">' +
                        '<label class="layui-form-label"></label>' +
                        '<div class="layui-input-inline"> ' +
                        '<input type="text" name="examOptions[' + count2 + '].score"  placeholder="选项分数" autocomplete="off" class="layui-input"> ' +
                        '</div> ' +
                        '<input type="button" id="deleteBuys' + count2 + '" onclick="deleteBuy(' + count2 + ')" class="layui-btn layui-btn-danger" value="移除">' +
                        '</div>' +
                        '</div>'
                    );

                    form.render('select');
                }
//                else {
//
//
//                    $('#spots2').append(
//                        '<div class=layui-inline id=optionDiv' + count2 + '>' +
//                        '<div class="layui-inline">' +
//                        '<label class="layui-form-label"></label>' +
//                        '<div class="layui-input-inline"> ' +
//                        '<input type="text" name="examOptions[' + count2 + '].name"  placeholder="请输入试题选项" autocomplete="off" class="layui-input"> ' +
//                        '</div>' +
//                        '<input type="button" id="deleteBuys' + count2 + '" onclick="deleteBuy(' + count2 + ')" class="layui-btn layui-btn-danger" value="移除">' +
//                        '</div>' +
//                        '</div>'
//                    );
//                    $("#showAnswerDiv2").show();
//                }
            }
            count2++;
        });


        //监听select改变
        form.on('select(categoryId1)', function (data) {


            var value = data.value;
            //动态加载下拉框值
            $.ajax({
                url: '/exam/subject/select?categoryId=' + value,

                type: "get",
                dataType: "json",
                success: function (result) {
                    data = result.data;
                    var html = '<option value="">请选择试题名称</option>';
                    $.each(data, function (index, value) {
                        html += '<option data-type="' + value.type + '" value="' + value.id + '">' + value.name + '</option>';
                    });
                    $("#subjectId1").html(html);
                    form.render('select');
                }
            });
        });


        //监听select(subjectId1)改变
        form.on('select(subjectId1)', function (data) {
            var value = data.value;
            $('#spots').html("");
            $.ajax({
                url: '/exam/questions/getExamCode?subjectId=' + value,
                type: "get",
                dataType: "json",
                success: function (result) {
                    var codes = result.data.code;
                    if (codes != '' && codes != null) {
                        var code = codes.split(",");
                        var html = '<option value="">请选择第几道题</option>';
                        for (var i = 0; i < code.length; i++) {
                            html += '<option data-type="' + value.type + '" value="' + code[i] + '">第' + code[i] + '道题</option>';
                        }
                        $("#code1").html(html);
                        form.render('select');
                    }
                    examAbilityList = result.data.examAbilityList;
                    html1 = "";
                    $.each(examAbilityList, function (index, value) {
                        html1 += '<option  value="' + value.id + '">' + value.name + '</option>';
                    });
                }
            });
        });


        //触发增加事件
        $('#add').on('click', function () {
            //打开编辑窗口
            layer.open({
                type: 1,
                title: '添加评估答案信息',
                content: $('#addForm'),
                area: ['1200px', '480px'],
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
                url: '/exam/questions/add',
                type: "post",
                data: field,
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code = 200) {
                        //提示信息
                        layer.msg('添加成功');
                        //刷新数据
                        table.reload('table'); //数据刷新 table id
                        //关闭弹窗
                        layer.closeAll();

                    } else {
                        layer.msg(result.message);
                        //刷新数据
                        table.reload('table'); //数据刷新 table id
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
                    url: '/exam/questions/deletes/' + ids,
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
                        url: '/exam/questions/deletes/' + data.id,
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
                layer.open({
                    type: 1,
                    title: '编辑评估答案信息',
                    content: $('#updateForm'),
                    area: ['1200px', '800px'],
                    closeBtn: 1,
                    end: function () {
                        window.location.reload();
                    }
                });

                $.ajax({
                    url: '/exam/questions/' + data.id,
                    type: "get",
                    success: function (result) {
                        var examCategory = result.data.examCategory;
                        var examQuestions = result.data.examQuestions;
                        var examSubject = result.data.examSubject;
                        var examOptions = examQuestions.examOptions;
                        examAbilityList = result.data.examAbilityList;

                        //赋值
                        $("#id").val(examQuestions.id);
                        $("#questions").val(examQuestions.questions);
                        $("#options").val(examQuestions.options);
                        $("#score").val(examQuestions.score);
                        $("#answer").val(examQuestions.answer);
                        $("#subjectId2").html('<option value="' + examQuestions.subjectId + '">' + examQuestions.subjectName + '</option>');
                        $("#code2").html('<option value="' + examQuestions.code + '">第' + examQuestions.code + '道题</option>');
                        $("#categoryId2").find("option[value = '" + examCategory.id + "']").attr("selected", "selected");
                        form.render();

                        if (examSubject.type == 1) {  //心理类

                            var abilityId;

                            $("#showAnswerDiv2").hide();
                            for (count2 = 0; count2 < examOptions.length; count2++) {

                                abilityId = examOptions[count2].abilityId;
                                $.each(examAbilityList, function (index, value) {
                                    if (abilityId == value.id) {

                                        html1 += '<option selected  value="' + value.id + '">' + value.name + '</option>';

                                    } else {
                                        html1 += '<option  value="' + value.id + '">' + value.name + '</option>';
                                    }
                                });

                                $('#spots2').append(
                                    '<div class=layui-inline id=optionDiv' + count2 + '>' +
                                    '<div class="layui-inline">' +
                                    '<label class="layui-form-label"></label>' +
                                    '<div class="layui-input-inline"> ' +
                                    '<input type="text" name="examOptions[' + count2 + '].name" value= "' + examOptions[count2].name + '" placeholder="试题选项" autocomplete="off" class="layui-input"> ' +
                                    '</div>' +
                                    '</div>' +
                                    '<div class="layui-inline">' +
                                    '<label class="layui-form-label"></label>' +
                                    '<div class="layui-input-inline"> ' +
                                    '<select name="examOptions[' + count2 + '].abilityId" >' +
                                    '<option  value="">请选择评估能力</option>' +
                                    html1 +
                                    '</select>' +
                                    '</div>' +
                                    '</div>' +
                                    '<div class="layui-inline">' +
                                    '<label class="layui-form-label"></label>' +
                                    '<div class="layui-input-inline"> ' +
                                    '<input type="text" name="examOptions[' + count2 + '].score" value= "' + examOptions[count2].score + '" placeholder="试题分数" autocomplete="off" class="layui-input"> ' +
                                    '</div>' +
                                    '</div>' +
                                    '<input type="button" id="deleteBuys' + count2 + '" onclick="deleteBuy(' + count2 + ')" class="layui-btn layui-btn-danger" value="移除">' +
                                    '</div>'
                                );
                                form.render('select');
                            }
                        }
//                        else {
//
//                            $("#showAnswerDiv2").show();
//                            for (count2 = 0; count2 < examOptions.length; count2++) {
//
//                                $('#spots2').append(
//                                    '<div class=layui-inline id=optionDiv' + count2 + '>' +
//                                    '<div class="layui-inline">' +
//                                    '<label class="layui-form-label"></label>' +
//                                    '<div class="layui-input-inline"> ' +
//                                    '<input type="text" name="examOptions[' + count2 + '].name" value= "' + examOptions[count2].name + '"  placeholder="试题选项" autocomplete="off" class="layui-input"> ' +
//                                    '</div>' +
//                                    '<input type="button" id="deleteBuys' + count2 + '" onclick="deleteBuy(' + count2 + ')" class="layui-btn layui-btn-danger" value="移除">' +
//                                    '</div>' +
//                                    '</div>'
//                                );
//                            }
//                        }
//                        form.render('select');
                    }
                });

                var subjectId = data.subjectId;
                form.on('select(categoryId2)', function (data) {
                    var value = data.value;
                    //动态加载下拉框值
                    $.ajax({
                        url: '/exam/subject/select?categoryId=' + value + '&subjectId=' + subjectId,
                        type: "get",
                        dataType: "json",
                        success: function (result) {
                            data = result.data;
                            var html = '<option value="">请选择试题名称</option>';
                            $.each(data, function (index, value) {
                                html += '<option data-type="' + value.type + '" value="' +
                                    '' + value.id + '">' + value.name + '</option>';
                            });
                            $("#subjectId2").html(html);
                            form.render('select');
                        }
                    });
                });
                //监听select(subjectId2)改变
                var code = data.code;
                form.on('select(subjectId2)', function (data) {
                    var value = data.value;
                    $('#spots2').html("");
                    $.ajax({
                        url: '/exam/questions/getExamCode?subjectId=' + value + '&code=' + code,
                        type: "get",
                        dataType: "json",
                        success: function (result) {
                            var codes = result.data.code;
                            if (codes != '' && codes != null) {
                                var code = codes.split(",");
                                var html = '<option value="">请选择第几道题</option>';
                                for (var i = 0; i < code.length; i++) {
                                    html += '<option value="' + code[i] + '">第' + code[i] + '道题</option>';
                                }
                                $("#code2").html(html);
                                form.render('select');
                            }
                            examAbilityList = result.data.examAbilityList;
                            html1 = "";
                            $.each(examAbilityList, function (index, value) {
                                html1 += '<option  value="' + value.id + '">' + value.name + '</option>';
                            });
                        }
                    });
                });

                //监听更新提交
                form.on('submit(updateSubmit)', function (data) {

                    var field = data.field;

                    $.ajax({
                        url: '/exam/questions/update',
                        type: "post",
                        data: field,
                        dataType: "json",
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

    function deleteBuy(buysNum1) {
        document.getElementById('optionDiv' + buysNum1).remove();
    }
</script>

</html>
