layui.use(['form', 'layer', 'laydate', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    var aliasPrefix = 'role';

    //新闻列表
    var tableIns = table.render({
        elem: '#' + aliasPrefix + 'List',
        url: '/admin/' + aliasPrefix + '/list',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limit: 20,
        limits: [10, 15, 20, 25],
        id: aliasPrefix + 'Table',
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'roleId', title: 'ID', width: 60, align: "center"},
            {field: 'roleName', title: '角色名称', width: 200, align: "center"},
            {field: 'description', title: '角色描述', width: 300, align: "center"},
            {field: 'createTime', title: '创建时间', align: 'center', minWidth: 110},
            {title: '操作', width: 200, templet: '#' + aliasPrefix + 'ListBar', fixed: "right", align: "center"}
        ]]
    });


    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function () {
        if ($(".searchVal").val() != '') {
            table.reload(aliasPrefix + "Table", {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    key: $(".searchVal").val()  //搜索的关键字
                }
            })
        } else {
            layer.msg("请输入搜索的内容");
        }
    });

    //添加文章
    function openPanel(role) {
        var title = "添加角色";
        var uriPage = "/admin/" + aliasPrefix + "/page/add";

        if (role) {
            title = "修改角色";
            uriPage = "/admin/" + aliasPrefix + "/page/edit?id=" + role.roleId;

            if (role.bind == true) {
                title = "角色资源绑定";
                uriPage = "/admin/" + aliasPrefix + "/page/bind?id=" + role.roleId;
            }

        }


        var index = layui.layer.open({
            title: title,
            type: 2,
            content: uriPage,
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回角色列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        })
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize", function () {
            layui.layer.full(index);
        })
    }

    $(".addRole_btn").click(function () {
        openPanel();
    })

    //批量删除
    $(".delAll_btn").click(function () {
        var checkStatus = table.checkStatus(aliasPrefix + 'Table'),
            data = checkStatus.data,
            roleIds = [];
        if (data.length > 0) {
            for (var i in data) {
                roleIds.push(data[i].roleId);
            }
            layer.confirm('确定删除选中的门店？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/admin/" + aliasPrefix + "/remove", {ids: roleIds.join(",")}, function (data) {
                    tableIns.reload();
                    layer.close(index);
                });
            });
        } else {
            layer.msg("请选择需要删除的门店");
        }
    });

    //列表操作
    table.on('tool(' + aliasPrefix + 'List)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'edit') { //编辑
            openPanel(data);
        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此门店？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/admin/" + aliasPrefix + "/remove", {ids: data.roleId}, function (data) {
                    tableIns.reload();
                    layer.close(index);
                });
            });
        } else if (layEvent === 'bind') { //预览
            data.bind = true
            openPanel(data);
        }
    });

})