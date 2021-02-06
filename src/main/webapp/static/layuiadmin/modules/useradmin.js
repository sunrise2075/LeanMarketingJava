/*
* *

 @Name：layuiAdmin 用户管理 管理员管理 角色管理
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：LPPL

 */


layui.define(['table', 'form'], function (exports) {
    var $ = layui.jquery
        , table = layui.table
        , form = layui.form;

    table.render({
        elem: '#LAY-user-manage'
        , method: "post"
        , url: '../../CommonBackStore/storelistPager' //合作商户管理查询接口
        , headers: {token: window.localStorage.getItem("token")}
        , method: 'post'
        // ,url: layui.setter.base + 'json/useradmin/webuser.js' //模拟接口
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 60, title: 'ID', sort: true}
            , {field: 'store_name', title: '店铺名称', minWidth: 100}
            , {field: 'store_address', title: '店铺地址', minWidth: 100}
            , {field: 'link_man', title: '联系人', minWidth: 100}
            , {field: 'link_mobile', title: '联系方式'}
            , {field: 'email', title: '邮箱'}
            , {
                field: 'login_time',
                title: '最后登录',
                sort: true,
                templet: '<div>{{layui.util.toDateString(d.login_time, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
            }
            , {field: 'status', width: 80, templet: '#buttonTpl', title: '状态'}
            , {title: '操作', width: 100, align: 'center', fixed: 'right', toolbar: '#table-useradmin-business'}
        ]]
        , page: true
        , limit: 10
        , limits: [10, 15, 20, 25, 30]
        , height: 'full-220'
        , text: {
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
    });


    //监听工具条
    table.on('tool(LAY-user-manage)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                //执行 Ajax 后重载
                $.ajax({
                    url: '../../backAdmin/remove/' + data.id,
                    type: "post",
                    // data: {ids: data.id},
                    headers: {token: window.localStorage.getItem("token")},
                    dataType: "json",
                    success: function (result) {
                        if (result.status == 1) {
                            table.reload('LAY-user-manage');
                            layer.msg('已删除');
                        }
                        if (result.status == -1) {
                            layer.msg("删除失败");
                        } else if (result.status == 2001) {
                            layer.msg("登录信息失效，请重新登录！");
                            setTimeout(function () {//等待2s跳转
                                if (result.status == 2001) {
                                    top.location.href = "../../views/admin/";
                                }
                            }, 2000);
                        }
                    }
                });
                obj.del();
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            layer.open({//这里打开编辑框
                type: 2 //type 为2时打开新页面
                , title: '编辑合作商户'
                , content: '../addmanager/addbusiness.jsp'
                , maxmin: true
                , area: ['500px', '450px']
                , btn: ['确定', '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submitID = 'LAY-user-front-submit'//确认修改按钮id， lay-filter
                        , submit = layero.find('iframe').contents().find('#' + submitID);
                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {
                        var field = data.field; //获取提交的字段
                        if (!field.hasOwnProperty("status")) {//若不存在status则为禁止状态，给他设置值为2
                            field.status = 2;
                        }
                        //提交 Ajax 成功后，静态更新表格中的数据
                        $.ajax({
                            url: '../../backAdmin/update',
                            type: "post",
                            data: field,
                            headers: {token: window.localStorage.getItem("token")},
                            dataType: "json",
                            success: function (result) {
                                if (result.status == 1) {
                                    table.reload('LAY-user-manage');
                                    layer.msg('修改成功');
                                } else if (result.status == -1) {
                                    layer.msg("修改失败！");
                                } else if (result.status == 2001) {
                                    layer.msg("登录信息失效，请重新登录！");
                                    setTimeout(function () {//等待2s跳转
                                        if (result.status == 2001) {
                                            top.location.href = "../../manager/loginPage";
                                        }
                                    }, 2000);
                                }
                            }
                        });
                        table.reload('LAY-user-manage'); //数据刷新 table id
                        layer.close(index); //关闭弹层
                    });
                    submit.trigger('click');
                }
                , success: function (layero, index) {
                    //给iframe元素赋值
                    var othis = layero.find('iframe').contents().find("#layuiadmin-form-business").click();//iframe的id
                    othis.find('input[name="id"]').val(data.id);//使id不可更改
                    othis.find('input[name="store_name"]').val(data.store_name);
                    othis.find('input[name="store_address"]').val(data.store_address);
                    othis.find('input[name="lnglat"]').val(data.store_address);
                    othis.find('input[name="link_man"]').val(data.link_man);
                    othis.find('input[name="merchant_nickname"]').val(data.merchant_nickname);
                    othis.find('input[name="link_mobile"]').val(data.link_mobile);
                    othis.find('input[name="email"]').val(data.email);
                    if (data.status == 2) {//若不存在status则为禁止状态，给他设置值为2
                        othis.find('input[name="status"]').prop('checked', false);
                    } else othis.find('input[name="status"]').val(data.status);
                    form.render();
                }
            });
            //layer.full(full);
        }
    });

    //管理员管理
    table.render({
        elem: '#LAY-user-back-authority'
        , url: '../../manager/listPager' //模拟接口
        , headers: {token: window.localStorage.getItem("token")}
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 60, title: 'ID', sort: true}
            , {field: 'manager_name', title: '用户名'}
            , {field: 'manager_nickname', title: '昵称'}
            , {field: 'name', title: '角色名称'}
            , {field: 'email', title: '邮箱'}
            , {field: 'status', title: '状态', templet: '#buttonTpl', minWidth: 80, align: 'center'}
            , {
                field: 'login_time',
                title: '最后登录',
                templet: '<div>{{layui.util.toDateString(d.login_time, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
            }
            , {title: '操作', width: 100, align: 'center', fixed: 'right', toolbar: '#table-useradmin-authority'}
        ]]
        , page: true
        , limit: 10
        , limits: [10, 15, 20, 25, 30]
        , height: 'full-220'
        , text: {
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
    });


    //监听工具条
    table.on('tool(LAY-user-back-authority)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                //执行 Ajax 后重载
                $.ajax({
                    url: '../../manager/remove/' + data.id,
                    type: "post",
                    // data: {ids: data.id},
                    headers: {token: window.localStorage.getItem("token")},
                    dataType: "json",
                    success: function (result) {
                        if (result.status == 1) {
                            table.reload('LAY-user-back-authority');
                            layer.msg('已删除');
                        }
                        if (result.status == -1) {
                            layer.msg("删除失败");
                        } else if (result.status == 2001) {
                            layer.msg("登录信息失效，请重新登录！");
                            setTimeout(function () {//等待2s跳转
                                if (result.status == 2001) {
                                    top.location.href = "../../manager/loginPage";
                                }
                            }, 2000);
                        }
                    }
                });
                obj.del();
                layer.close(index);
            });
        } else if (obj.event === 'edit') {

            layer.open({
                type: 2
                , title: '编辑管理员'
                , content: '../addmanager/addauthoritymanager.jsp'
                , area: ['420px', '420px']
                , btn: ['确定', '取消']
                , yes: function (index, layero) {

                    var iframeWin = layero.find('iframe')[0].contentWindow;
                    // 重新渲染checkbox,select同理
                    iframeWin.layui.form.render();

                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submitID = 'LAY-user-authority'//确认修改按钮id， lay-filter
                        , submit = layero.find('iframe').contents().find('#' + submitID);
                    //监听提交
                    iframeWin.layui.form.on('submit(' + submitID + ')', function (data) {
                        var field = data.field; //获取提交的字段
                        if (!field.hasOwnProperty("status")) {//若不存在status则为禁止状态，给他设置值为2
                            field.status = 2;
                        }
                        //提交 Ajax 成功后，静态更新表格中的数据
                        $.ajax({
                            url: '../../manager/update',
                            type: "post",
                            data: field,
                            headers: {token: window.localStorage.getItem("token")},
                            dataType: "json",
                            success: function (result) {
                                if (result.status == 1) {
                                    table.reload('LAY-user-back-authority');
                                    layer.msg('修改成功');
                                } else if (result.status == -1) {
                                    layer.msg("修改失败！");
                                } else if (result.status == 2001) {
                                    layer.msg("登录信息失效，请重新登录！");
                                    setTimeout(function () {//等待2s跳转
                                        if (result.status == 2001) {
                                            top.location.href = "../../manager/loginPage";
                                        }
                                    }, 2000);

                                }
                            }
                        });
                        table.reload('LAY-user-back-authority'); //数据刷新 table id
                        layer.close(index); //关闭弹层
                    });
                    submit.trigger('click');
                }
                , success: function (layero, index) {
                    //给iframe元素赋值
                    //alert(data.name);

                    var othis = layero.find('iframe').contents().find("#layuiadmin-form-authority").click();//iframe的id
                    othis.find('input[name="id"]').val(data.id);//使id不可更改
                    othis.find('input[name="manager_name"]').val(data.manager_name);
                    othis.find('input[name="manager_nickname"]').val(data.manager_nickname);
                    othis.find('input[name="email"]').val(data.email);
                    othis.find("select").attr("id", data.name);
                    othis.find("select option[value='" + data.role_id + "']").attr("selected", "selected");
                    if (data.status == 2) {//若不存在status则为禁止状态，给他设置值为2
                        othis.find('input[name="status"]').prop('checked', false);
                    } else othis.find('input[name="status"]').val(data.status);
                    form.render();
                }
            })
        }
    });


    //角色组管理
    table.render({
        elem: '#LAY-user-back-role'
        , url: '../../manager/listPagerToRole' //模拟接口
        , headers: {token: window.localStorage.getItem("token")}
        , method: 'post'
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 60, title: 'ID', sort: true}
            , {field: 'name', width: 120, title: '角色名称'}
            /*, {field: 'manager_top_group', title: '父级'}
            , {field: 'manager_son_group', title: '名称'}*/
            , {
                field: 'authority', title: '权限', templet: function (d) {
                    var ids = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,17,";
                    if (d.codes != null) {
                        if (d.codes === ids) {
                            return "所有权限";
                        } else {
                            return d.authority;
                        }
                    } else {
                        return d.authority;
                    }

                }
            }
            , {field: 'status', width: 100, title: '状态', templet: '#buttonTpl', minWidth: 80, align: 'center'}
            , {title: '操作', width: 100, align: 'center', fixed: 'right', toolbar: '#table-useradmin-role'}
        ]]
        , text: {
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
    });

    //监听工具条
    table.on('tool(LAY-user-back-role)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                //执行 Ajax 后重载
                $.ajax({
                    url: '../../manager/removeRole/' + data.id,
                    type: "post",
                    // data: {ids: data.id},
                    headers: {token: window.localStorage.getItem("token")},
                    dataType: "json",
                    success: function (result) {
                        if (result.status == 1) {
                            table.reload('LAY-user-back-role');
                            layer.msg('已删除');
                        }
                        if (result.status == -1) {
                            layer.msg("删除失败");
                        } else if (result.status == 2001) {
                            layer.msg("登录信息失效，请重新登录！");
                            setTimeout(function () {//等待2s跳转
                                top.location.href = "../../manager/loginPage";
                            }, 2000);
                        }
                    }
                });
                obj.del();
                layer.close(index);
            });
        } else if (obj.event === 'edit') {

            layer.open({
                type: 2
                , title: '编辑角色'
                , content: '../addmanager/addrolemanager.jsp'
                , area: ['420px', '560px']
                , btn: ['确定', '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submitID = 'LAY-user-role-submit'//确认修改按钮id， lay-filter
                        , submit = layero.find('iframe').contents().find('#' + submitID);
                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {
                        var field = data.field; //获取提交的字段
                        if (!field.hasOwnProperty("status")) {//若不存在status则为禁止状态，给他设置值为2
                            field.status = 2;
                        }
                        //console.log("这里的数据肯定有问题")
                        console.log(field)
                        //提交 Ajax 成功后，静态更新表格中的数据
                        $.ajax({
                            url: '../../manager/updateRole',
                            type: "post",
                            data: field,
                            headers: {token: window.localStorage.getItem("token")},
                            dataType: "json",
                            success: function (result) {
                                if (result.status == 1) {
                                    table.reload('LAY-user-back-role');
                                    layer.msg('修改成功');
                                } else if (result.status == -1) {
                                    layer.msg("修改失败！");
                                } else if (result.status == 2001) {
                                    layer.msg("登录信息失效，请重新登录！");
                                    setTimeout(function () {//等待2s跳转
                                        if (result.status == 2001) {
                                            top.location.href = "../../manager/loginPage";
                                        }
                                    }, 2000);
                                }
                            }
                        });
                        table.reload('LAY-user-back-role'); //数据刷新 table id
                        layer.close(index); //关闭弹层
                    });
                    submit.trigger('click');
                }
                , success: function (layero, index) {

                    console.log(JSON.stringify(data))

                    //给iframe元素赋值
                    var othis = layero.find('iframe').contents().find("#layuiadmin-form-role").click();//iframe的id
                    othis.find('input[name="id"]').val(data.id);//使id不可更改
                    /*othis.find('select[name="manager_top_group"]').val(data.manager_top_group);
                    othis.find('input[name="manager_son_group"]').val(data.manager_son_group);
                    othis.find('input[name="authority_id"]').val(data.authority_id);*/
                    //得到codes值
                    othis.find('input[name="name"]').val(data.name);

                    var codes = data.codes;
                    //清空所有权限
                    if (codes != null) {
                        var code = codes.split(",");
                        for (var i = 0; i < code.length; i++) {

                            othis.find("#code" + code[i]).prop("checked", true);
                        }
                    } else {
                        if (data.name == "超级管理员") {
                            for (var i = 1; i < 18; i++) {
                                othis.find("#code" + i).prop("checked", true);
                            }
                        }
                    }
                    if (data.status == 2) {//若不存在status则为禁止状态，给他设置值为2
                        othis.find('input[name="status"]').prop('checked', false);
                    } else othis.find('input[name="status"]').val(data.status);
                    form.render();
                }
            })
        }
    });

    //会员管理
    table.render({
        elem: '#LAY-member-member'
        , url: '../../store/listPagerToUser' //模拟接口
        , method: 'post'
        , headers: {token: window.localStorage.getItem("token")}
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id_number', width: 60, title: 'ID', sort: true}
            , {field: 'state', title: '等级', templet: '#memberTpl', minWidth: 90}
            // , {field: 'nickname', title: '用户名', minWidth: 100}
            , {field: 'nickname', title: '昵称', minWidth: 100}
            , {field: 'wechat_open_id', title: '微信APPopenId', minWidth: 100}
            , {field: 'small_open_id', title: '小程序openId', minWidth: 100}
            , {field: 'phone', title: '手机号'}
            , {field: 'image', title: '头像', maxWidth: 80, minWidth: 80, align: 'center', templet: '#imgTpl'}
            , {field: 'binding_id', title: '绑定ID', minWidth: 50}
            , {
                field: 'login_time',
                title: '登录时间',
                templet: '<div>{{layui.util.toDateString(d.login_time, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
            }
            , {field: 'ip', title: '登录IP'}
            , {
                field: 'create_time',
                title: '加入时间',
                templet: '<div>{{layui.util.toDateString(d.create_time, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
            }
            , {title: '操作', align: 'center', fixed: 'right', toolbar: '#table-useradmin-member'}
        ]]
        , page: true
        , limit: 10
        , limits: [10, 15, 20, 25, 30]
        , height: 'full-220'
        , text: {
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
    });

    //监听工具条
    table.on('tool(LAY-member-member)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                //执行 Ajax 后重载
                $.ajax({
                    url: '../../store/removeUser/' + data.id,
                    type: "post",
                    // data: {ids: data.id},
                    headers: {token: window.localStorage.getItem("token")},
                    dataType: "json",
                    success: function (result) {
                        if (result.status == 1) {
                            table.reload('LAY-member-member');
                            layer.msg('已删除');
                        } else if (result.status == -1) {
                            layer.msg("删除失败");
                        } else if (result.status == 2001) {
                            layer.msg("登录信息失效，请重新登录！");
                            setTimeout(function () {//等待2s跳转
                                if (result.status == 2001) {
                                    top.location.href = "../../manager/loginPage";
                                }
                            }, 2000);
                        }
                    }
                });
                obj.del();
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            layer.open({//这里打开编辑框
                type: 2 //type 为2时打开新页面
                , title: '编辑会员'
                , content: '../addmanager/addmember.jsp'
                , maxmin: true
                , area: ['500px', '450px']
                , btn: ['确定', '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submitID = 'LAY-user-member'//确认修改按钮id， lay-filter
                        , submit = layero.find('iframe').contents().find('#' + submitID);
                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {
                        var field = data.field; //获取提交的字段
                        //提交 Ajax 成功后，静态更新表格中的数据
                        $.ajax({
                            url: '../../store/updateUser',
                            type: "post",
                            data: field,
                            headers: {token: window.localStorage.getItem("token")},
                            dataType: "json",
                            success: function (result) {
                                if (result.status == 1) {
                                    table.reload('LAY-member-member');
                                    layer.msg('修改成功');
                                } else if (result.status == -1) {
                                    layer.msg("修改失败！");
                                } else if (result.status == 2001) {
                                    layer.msg("登录信息失效，请重新登录！");
                                    setTimeout(function () {//等待2s跳转
                                        if (result.status == 2001) {
                                            top.location.href = "../../manager/loginPage";
                                        }
                                    }, 2000);
                                }
                            }
                        });
                        table.reload('LAY-member-member'); //数据刷新 table id
                        layer.close(index); //关闭弹层
                    });
                    submit.trigger('click');
                }
                , success: function (layero, index) {
                    //给iframe元素赋值
                    var othis = layero.find('iframe').contents().find("#layuiadmin-form-member").click();//iframe的id
                    othis.find('input[name="id"]').val(data.id);
                    othis.find('select[name="state"]').val(data.state);
                    othis.find('input[name="nickname"]').val(data.nickname);
                    othis.find('input[name="phone"]').val(data.phone);
                    form.render();
                }
            });
        }
    });

    //会员会费管理
    table.render({
        elem: '#LAY-dues-setting'
        , url: '../../store/listVipSetting' //模拟接口
        , headers: {token: window.localStorage.getItem("token")}
        , method: 'post'
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 60, title: 'ID', sort: true}
            , {field: 'vip_name', title: '套餐名称', minWidth: 100}
            , {field: 'vip_money', title: '费用：元/天', minWidth: 100}
            , {field: 'vip_deadline', title: '期限/天', minWidth: 100}
            , {field: 'status', width: 80, templet: '#buttonTpl', title: '状态'}
            , {title: '操作', align: 'center', fixed: 'right', toolbar: '#table-dues-manager'}
        ]]
        , page: true
        , limit: 10
        , limits: [10, 15, 20, 25, 30]
        , height: 'full-220'
        , text: {
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
    });

    //监听工具条
    table.on('tool(LAY-dues-setting)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                //执行 Ajax 后重载
                $.ajax({
                    url: '../../store/removeVipSetting/' + data.id,
                    type: "post",
                    // data: {ids: data.id},
                    headers: {token: window.localStorage.getItem("token")},
                    dataType: "json",
                    success: function (result) {
                        if (result.status == 1) {
                            table.reload('LAY-dues-setting');
                            layer.msg('已删除');
                        }
                        if (result.status == -1) {
                            layer.msg("删除失败");
                        } else if (result.status == 2001) {
                            layer.msg("登录信息失效，请重新登录！");
                            setTimeout(function () {//等待2s跳转
                                if (result.status == 2001) {
                                    top.location.href = "../../manager/loginPage";
                                }
                            }, 2000);
                        }
                    }
                });
                obj.del();
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            layer.open({//这里打开编辑框
                type: 2 //type 为2时打开新页面
                , title: '编辑会费设置'
                , content: '../addmanager/adddues.jsp'
                , maxmin: true
                , area: ['500px', '450px']
                , btn: ['确定', '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submitID = 'LAY-user-front-dues'//确认修改按钮id， lay-filter
                        , submit = layero.find('iframe').contents().find('#' + submitID);
                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {
                        var field = data.field; //获取提交的字段
                        if (!field.hasOwnProperty("status")) {//若不存在status则为禁止状态，给他设置值为2
                            field.status = 2;
                        }
                        //提交 Ajax 成功后，静态更新表格中的数据
                        $.ajax({
                            url: '../../store/updateVipSetting',
                            type: "post",
                            data: field,
                            headers: {token: window.localStorage.getItem("token")},
                            dataType: "json",
                            success: function (result) {
                                if (result.status == 1) {
                                    table.reload('LAY-dues-setting');
                                    layer.msg('修改成功');
                                } else if (result.status == -1) {
                                    layer.msg("修改失败！");
                                } else if (result.status == 2001) {
                                    layer.msg("登录信息失效，请重新登录！");
                                    setTimeout(function () {//等待2s跳转
                                        if (result.status == 2001) {
                                            top.location.href = "../../manager/loginPage";
                                        }
                                    }, 2000);
                                }
                            }
                        });
                        table.reload('LAY-dues-setting'); //数据刷新 table id
                        layer.close(index); //关闭弹层
                    });
                    submit.trigger('click');
                }
                , success: function (layero, index) {
                    //给iframe元素赋值
                    var othis = layero.find('iframe').contents().find("#layuiadmin-form-dues").click();//iframe的id
                    othis.find('input[name="id"]').val(data.id);//使id不可更改
                    othis.find('input[name="vip_name"]').val(data.vip_name);
                    othis.find('input[name="vip_money"]').val(data.vip_money);
                    othis.find('input[name="vip_deadline"]').val(data.vip_deadline);
                    if (data.status == 2) {//若不存在status则为禁止状态，给他设置值为2
                        othis.find('input[name="status"]').prop('checked', false);
                    } else othis.find('input[name="status"]').val(data.status);
                    form.render();
                }
            });
        }
    });

    //申请上架商品权限审核
    table.render({
        elem: '#test-table-operate'
        , url: '../../backAdmin/' //合作商户管理查询接口
        , headers: {token: window.localStorage.getItem("token")}
        , method: 'post'
        // ,url: layui.setter.base + 'json/useradmin/webuser.js' //模拟接口
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 60, title: 'ID', sort: true}
            , {field: 'store_name', title: '店铺名称', minWidth: 100}
            , {field: 'store_address', title: '店铺地址', minWidth: 100}
            , {field: 'link_man', title: '联系人', minWidth: 100}
            , {field: 'link_mobile', title: '联系方式'}
            , {field: 'email', title: '邮箱'}
            , {
                field: 'apply_time',
                title: '申请时间',
                sort: true,
                templet: '<div>{{layui.util.toDateString(d.apply_time, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
            }
            , {field: 'status', width: 80, templet: '#buttonTpl', title: '状态'}
            , {title: '操作', width: 100, align: 'center', fixed: 'right', toolbar: '#table-useradmin-business'}
        ]]
        , page: true
        , limit: 10
        , limits: [10, 15, 20, 25, 30]
        , height: 'full-220'
        , text: {
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
    });

    //监听工具条
    table.on('tool(LAY-user-store)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                //执行 Ajax 后重载
                $.ajax({
                    url: '../../store/remove/' + data.id,
                    type: "post",
                    // data: {ids: data.id},
                    headers: {token: window.localStorage.getItem("token")},
                    dataType: "json",
                    success: function (result) {
                        if (result.status == 1) {
                            table.reload('LAY-audit-store');
                            layer.msg('已删除');
                        }
                        if (result.status == -1) {
                            layer.msg("删除失败");
                        } else if (result.status == 2001) {
                            layer.msg("登录信息失效，请重新登录！");
                            setTimeout(function () {//等待2s跳转
                                if (result.status == 2001) {
                                    top.location.href = "../../manager/loginPage";
                                }
                            }, 2000);
                        }
                    }
                });
                obj.del();
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            layer.open({//这里打开编辑框
                type: 2 //type 为2时打开新页面
                , title: '编辑合作商户'
                , content: '../addmanager/addbusiness.jsp'
                , maxmin: true
                , area: ['500px', '450px']
                , btn: ['确定', '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submitID = 'LAY-user-front-submit'//确认修改按钮id， lay-filter
                        , submit = layero.find('iframe').contents().find('#' + submitID);
                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {
                        var field = data.field; //获取提交的字段
                        // console.log(field);
                        //提交 Ajax 成功后，静态更新表格中的数据
                        $.ajax({
                            url: '../../store/update',
                            type: "post",
                            data: field,
                            headers: {token: window.localStorage.getItem("token")},
                            dataType: "json",
                            success: function (result) {
                                if (result.status == 1) {
                                    table.reload('LAY-user-manage');
                                    layer.msg('修改成功');
                                } else if (result.status == -1) {
                                    layer.msg("修改失败！");
                                } else if (result.status == 2001) {
                                    layer.msg("登录信息失效，请重新登录！");
                                    setTimeout(function () {//等待2s跳转
                                        if (result.status == 2001) {
                                            top.location.href = "../../manager/loginPage";
                                        }
                                    }, 2000);
                                }
                            }
                        });
                        table.reload('LAY-audit-store'); //数据刷新 table id
                        layer.close(index); //关闭弹层
                    });
                    submit.trigger('click');
                }
                , success: function (layero, index) {
                    //给iframe元素赋值
                    var othis = layero.find('iframe').contents().find("#layuiadmin-form-business").click();//iframe的id
                    othis.find('input[name="id"]').val(data.id);//使id不可更改
                    othis.find('input[name="store_name"]').val(data.store_name);
                    othis.find('input[name="store_address"]').val(data.store_address);
                    othis.find('input[name="link_man"]').val(data.link_man);
                    othis.find('input[name="link_mobile"]').val(data.link_mobile);
                    othis.find('input[name="email"]').val(data.email);
                    form.render();
                }
            });
        }
    });

    //导航管理
    table.render({
        elem: '#LAY-member-navigation'
        , url: '../../manager/listPagerToNavigation' //模拟接口
        , headers: {token: window.localStorage.getItem("token")}
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'num', width: 100, title: '序号', sort: true}
            , {field: 'picture', title: '图标', minWidth: 100, templet: '#imgTpl'}
            , {field: 'title', title: '标题', minWidth: 50}
            , {field: 'app_link', title: 'APP链接', minWidth: 100}
            , {field: 'applet_link', title: '小程序链接', minWidth: 50}
            , {field: 'status', width: 80, templet: '#buttonTpl', title: '状态'}
            , {title: '操作', width: 100, align: 'center', fixed: 'right', toolbar: '#table-useradmin-member'}
        ]]
        , page: true
        , limit: 10
        , limits: [10, 15, 20, 25, 30]
        , height: 'full-220'
        , text: {
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
    });

    //监听工具条
    table.on('tool(LAY-member-navigation)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                //执行 Ajax 后重载
                $.ajax({
                    url: '../../manager/removeNavigation/' + data.id,
                    type: "post",
                    // data: {ids: data.id},
                    headers: {token: window.localStorage.getItem("token")},
                    dataType: "json",
                    success: function (result) {
                        if (result.status == 1) {
                            table.reload('LAY-member-navigation');
                            layer.msg('已删除');
                        } else if (result.status == -1) {
                            layer.msg("删除失败");
                        } else if (result.status == 2001) {
                            layer.msg("登录信息失效，请重新登录！");
                            setTimeout(function () {//等待2s跳转
                                if (result.status == 2001) {
                                    top.location.href = "../../manager/loginPage";
                                }
                            }, 2000);
                        }
                    }
                });
                obj.del();
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            layer.open({//这里打开编辑框
                type: 2 //type 为2时打开新页面
                , title: '编辑导航'
                , content: '../addmanager/addnavigation.jsp'
                , maxmin: true
                , area: ['500px', '450px']
                , btn: ['确定', '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submitID = 'LAY-navigation-front-submit'//确认修改按钮id， lay-filter
                        , submit = layero.find('iframe').contents().find('#' + submitID);
                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {
                        var field = data.field; //获取提交的字段
                        if (!field.hasOwnProperty("status")) {//若不存在status则为禁止状态，给他设置值为2
                            field.status = 2;
                        }
                        //提交 Ajax 成功后，静态更新表格中的数据
                        $.ajax({
                            url: '../../manager/updateNavigation',
                            type: "post",
                            data: field,
                            headers: {token: window.localStorage.getItem("token")},
                            dataType: "json",
                            success: function (result) {
                                if (result.status == 1) {
                                    table.reload('LAY-member-navigation');
                                    layer.msg('修改成功');
                                } else if (result.status == -1) {
                                    layer.msg("修改失败！");
                                } else if (result.status == 2001) {
                                    layer.msg("登录信息失效，请重新登录！");
                                    setTimeout(function () {//等待2s跳转
                                        if (result.status == 2001) {
                                            top.location.href = "../../manager/loginPage";
                                        }
                                    }, 2000);
                                }
                            }
                        });
                        table.reload('LAY-member-navigation'); //数据刷新 table id
                        layer.close(index); //关闭弹层
                    });
                    submit.trigger('click');
                }
                , success: function (layero, index) {
                    //给iframe元素赋值
                    var othis = layero.find('iframe').contents().find("#layuiadmin-form-navigation").click();//iframe的id
                    othis.find('input[name="id"]').val(data.id);
                    othis.find('input[name="num"]').val(data.num);
                    othis.find('input[name="picture"]').val(data.picture);
                    othis.find('input[name="title"]').val(data.title);
                    othis.find('input[name="app_link"]').val(data.app_link);
                    othis.find('input[name="applet_link"]').val(data.applet_link);
                    if (data.status == 2) {//若不存在status则为禁止状态，给他设置值为2
                        othis.find('input[name="status"]').prop('checked', false);
                    } else othis.find('input[name="status"]').val(data.status);
                    form.render();
                }
            });
        }
    });

    //BANNER管理
    table.render({
        elem: '#LAY-banner-manager'
        , url: '../../manager/listPagerToBanner' //模拟接口
        , headers: {token: window.localStorage.getItem("token")}
        , method: 'post'
        , type: "post"
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 120, title: '序号', sort: true}
            , {field: 'banner_title', title: '图片标题'}
            , {field: 'banner_picture', title: '图片', maxWidth: 150, minWidth: 200, align: 'center', templet: '#imgTpl'}
            , {field: 'app_link', width: 80, title: 'APP链接'}
            , {field: 'applet_link', width: 80, title: '小程序链接'}
            , {
                field: 'create_time',
                title: '上传时间',
                templet: '<div>{{layui.util.toDateString(d.create_time, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
            }
            , {field: 'status', width: 80, templet: '#buttonTpl', title: '状态'}
            , {title: '操作', width: 100, align: 'center', fixed: 'right', toolbar: '#table-banner-manager'}
        ]]
        , page: true
        , limit: 10
        , limits: [10, 15, 20, 25, 30]
        , height: 'full-220'
        , text: {
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
    });

    //监听工具条
    table.on('tool(LAY-banner-manager)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                //执行 Ajax 后重载
                $.ajax({
                    url: '../../manager/removeBanner/' + data.id,
                    type: "post",
                    // data: {ids: data.id},
                    headers: {token: window.localStorage.getItem("token")},
                    dataType: "json",
                    success: function (result) {
                        if (result.status == 1) {
                            table.reload('LAY-banner-manager');
                            layer.msg('已删除');
                        } else if (result.status == -1) {
                            layer.msg("删除失败");
                        } else if (result.status == 2001) {
                            layer.msg("登录信息失效，请重新登录！");
                            setTimeout(function () {//等待2s跳转
                                if (result.status == 2001) {
                                    top.location.href = "../../manager/loginPage";
                                }
                            }, 2000);
                        }
                    }
                });
                obj.del();
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            layer.open({//这里打开编辑框
                type: 2 //type 为2时打开新页面
                , title: '编辑Banner'
                , content: './addbanner.jsp'
                , maxmin: true
                , area: ['500px', '450px']
                , btn: ['确定', '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submitID = 'LAY-banner-front-submit'//确认修改按钮id， lay-filter
                        , submit = layero.find('iframe').contents().find('#' + submitID);
                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {
                        var field = data.field; //获取提交的字段
                        if (!field.hasOwnProperty("status")) {//若不存在status则为禁止状态，给他设置值为2
                            field.status = 2;
                        }
                        //提交 Ajax 成功后，静态更新表格中的数据
                        $.ajax({
                            url: '../../manager/updateBanner',
                            type: "post",
                            data: field,
                            headers: {token: window.localStorage.getItem("token")},
                            dataType: "json",
                            success: function (result) {
                                if (result.status == 1) {
                                    table.reload('LAY-banner-manager');
                                    layer.msg('修改成功');
                                } else if (result.status == -1) {
                                    layer.msg("修改失败！");
                                } else if (result.status == 2001) {
                                    layer.msg("登录信息失效，请重新登录！");
                                    setTimeout(function () {//等待2s跳转
                                        if (result.status == 2001) {
                                            top.location.href = "../../manager/loginPage";
                                        }
                                    }, 2000);
                                }
                            }
                        });
                        table.reload('LAY-banner-manager'); //数据刷新 table id
                        layer.close(index); //关闭弹层
                    });
                    submit.trigger('click');
                }
                , success: function (layero, index) {
                    //给iframe元素赋值
                    var othis = layero.find('iframe').contents().find("#layuiadmin-form-banner").click();//iframe的id
                    othis.find('input[name="id"]').val(data.id);
                    othis.find('input[name="banner_title"]').val(data.banner_title);
                    othis.find('input[name="banner_picture"]').val(data.banner_picture);
                    othis.find('input[name="applet_link"]').val(data.applet_link);
                    othis.find('input[name="app_link"]').val(data.app_link);
                    if (data.status == 2) {//若不存在status则为禁止状态，给他设置值为2
                        othis.find('input[name="status"]').attr("checked", false);
                    } else othis.find('input[name="status"]').val(data.status);
                }
            });
        }
    });

    //单品收益情况
    table.render({
        elem: '#LAY-product-manager'
        , url: '../../manager/commodityProfit' //模拟接口
        , method: 'post'
        , headers: {token: window.localStorage.getItem("token")}
        , cols: [[
            {field: 'images', title: '商品', style: 'height:100px', align: 'center', templet: '#imgTpl'}
            , {field: 'commodity_name', title: '商品名称'}
            , {field: 'channelFatherName', title: '商品父分类'}
            , {field: 'channelChildName', title: '商品子分类'}
            , {field: 'count', width: 60, title: '订单数量'}
            , {field: 'n', title: '排队最后一单排层数'}
            , {field: 'commodityRebackNum', title: '已返现了几单'}
            , {field: 'totalMoney', title: '总订单金额'}
            , {field: 'commodityBackMoney', title: '商品已返现订单总收益'}
            , {field: 'commodity_id', title: '商品编号'}
            , {field: 'count', title: '标记', width: 200, templet: '#buttonTpl'}
            , {title: '操作', align: 'center', fixed: 'right', toolbar: '#table-product-manager', style: 'height:100px'}
        ]]
        , page: true
        , limit: 10
        , limits: [10, 15, 20, 25, 30]
        , height: 'full-220'
        , text: {
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
        , skin: 'nob'
        , size: 'lg'
    });

    //监听工具条
    table.on('tool(LAY-product-manager)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                //执行 Ajax 后重载
                $.ajax({
                    url: '../../manager/removeBanner/' + data.id,
                    type: "post",
                    // data: {ids: data.id},
                    headers: {token: window.localStorage.getItem("token")},
                    dataType: "json",
                    success: function (result) {
                        if (result.status == 1) {
                            table.reload('LAY-product-manager');
                            layer.msg('已删除');
                        } else if (result.status == -1) {
                            layer.msg("删除失败");
                        } else if (result.status == 2001) {
                            layer.msg("登录信息失效，请重新登录！");
                            setTimeout(function () {//等待2s跳转
                                if (result.status == 2001) {
                                    top.location.href = "../../manager/loginPage";
                                }
                            }, 2000);
                        }
                    }
                });
                obj.del();
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            window.location.href = "./productdetail.jsp?commodity_id=" + data.commodity_id + "&itemprofit=1";
        }
    });

    //员工管理
    table.render({
        elem: '#LAY-user-employee'
        , url: '../../commodity/employeeOperation' //合作商户管理查询接口
        , headers: {token: window.localStorage.getItem("token")}
        , method: 'post'
        // ,url: layui.setter.base + 'json/useradmin/webuser.js' //模拟接口
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 60, title: 'ID', sort: true}
            , {field: 'employee_name', title: '员工用户名', minWidth: 100}
            , {field: 'employee_email', title: '员工账号(邮箱)', minWidth: 100}
            , {field: 'employee_nickname', title: '员工昵称', minWidth: 100}
            , {
                field: 'login_time',
                title: '登录时间',
                sort: true,
                templet: '<div>{{layui.util.toDateString(d.login_time, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
            }
            , {field: 'status', width: 80, templet: '#buttonTpl', title: '状态'}
            , {title: '操作', width: 100, align: 'center', fixed: 'right', toolbar: '#table-useradmin-employee'}
        ]]
        , page: true
        , limit: 10
        , limits: [10, 15, 20, 25, 30]
        , height: 'full-220'
        , text: {
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
        , data: [{type: 5}]
    });

    //监听工具条
    table.on('tool(LAY-user-employee)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                //执行 Ajax 后重载
                $.ajax({
                    url: '../../commodity/employeeOperation',
                    type: "post",
                    data: {ids: data.id, type: 2},//删除
                    headers: {token: window.localStorage.getItem("token")},
                    dataType: "json",
                    success: function (result) {
                        if (result.status == 1) {
                            table.reload('LAY-user-employee');
                            layer.msg('已删除');
                        }
                        if (result.status == -1) {
                            layer.msg("删除失败");
                        } else if (result.status == 2001) {
                            layer.msg("登录信息失效，请重新登录！");
                            setTimeout(function () {//等待2s跳转
                                if (result.status == 2001) {
                                    top.location.href = "../../backstage/admin/index.jsp";
                                }
                            }, 2000);
                        }
                    }
                });
                obj.del();
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            var full = layer.open({//这里打开编辑框
                type: 2 //type 为2时打开新页面
                , title: '编辑员工'
                , content: '../addmanager/addemployee.jsp'
                , maxmin: false
                , area: ['500px', '450px']
                , btn: ['确定', '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submitID = 'LAY-user-front-submit'//确认修改按钮id， lay-filter
                        , submit = layero.find('iframe').contents().find('#' + submitID);
                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {
                        var field = data.field; //获取提交的字段
                        if (!field.hasOwnProperty("status")) {//若不存在status则为禁止状态，给他设置值为2
                            field.status = 2;
                        }
                        field.type = 3;
                        //提交 Ajax 成功后，静态更新表格中的数据
                        $.ajax({
                            url: '../../store/employeeOperation',
                            type: "post",
                            data: field,
                            headers: {token: window.localStorage.getItem("token")},
                            dataType: "json",
                            success: function (result) {
                                if (result.status == 1) {
                                    table.reload('LAY-user-employee');
                                    layer.msg('修改成功');
                                } else if (result.status == -1) {
                                    layer.msg("修改失败！");
                                } else if (result.status == 2001) {
                                    layer.msg("登录信息失效，请重新登录！");
                                    setTimeout(function () {//等待2s跳转
                                        if (result.status == 2001) {
                                            top.location.href = "../../backstage/admin/index.jsp";
                                        }
                                    }, 2000);
                                }
                            }
                        });
                        table.reload('LAY-user-employee'); //数据刷新 table id
                        layer.close(index); //关闭弹层
                    });
                    submit.trigger('click');
                }
                , success: function (layero, index) {
                    //给iframe元素赋值
                    var othis = layero.find('iframe').contents().find("#layuiadmin-form-business").click();//iframe的id
                    othis.find('input[name="id"]').val(data.id);//使id不可更改
                    othis.find('input[name="employee_name"]').val(data.employee_name);
                    othis.find('input[name="employee_email"]').val(data.employee_email);
                    othis.find('input[name="employee_nickname"]').val(data.employee_nickname);
                    othis.find('input[name="merchant_id"]').val(data.merchant_id);
                    othis.find('input[name="merchant_nickname"]').val(data.merchant_nickname);
                    if (data.status == 2) {//若不存在status则为禁止状态，给他设置值为2
                        othis.find('input[name="status"]').prop('checked', false);
                    } else othis.find('input[name="status"]').val(data.status);
                    form.render();
                }
            });
            // layer.full(full);最大化
        }
    });


    exports('useradmin', {})
});