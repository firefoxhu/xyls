layui.use(['form', 'layer', 'laydate', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    var aliasPrefix = 'resource';

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
            {field: 'resourceId', title: 'ID', width: 60, align: "center"},
            {field: 'resourceName', title: '资源名称', width: 300, align: "center"},
            {field: 'resourceParentId', title: '父类菜单', align: 'center', minWidth: 110},
            {field: 'resourceType', title: '资源类型', align: 'center', minWidth: 110},
            {field: 'resourceUrl', title: '资源uri', align: 'center', minWidth: 110},
            {
                field: 'resourceIcon', title: '图片', align: 'center', minWidth: 110, templet: function (row) {
                    return "<i class='seraph " + row.resourceIcon + "'></i>"
                }
            },
            {field: 'resourceSpread', title: '是否展开', align: 'center', minWidth: 110},
            {field: 'createTime', title: '创建时间', align: 'center', minWidth: 110},
            {title: '操作', width: 170, templet: '#' + aliasPrefix + 'ListBar', fixed: "right", align: "center"}
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
    function openPanel(resource) {
        var title = "添加资源";
        var uriPage = "/admin/" + aliasPrefix + "/page/add";

        if (resource) {
            title = "修改资源";
            uriPage = "/admin/" + aliasPrefix + "/page/edit?id=" + resource.resourceId;
        }


        var index = layui.layer.open({
            title: title,
            type: 2,
            content: uriPage,
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回资源列表', '.layui-layer-setwin .layui-layer-close', {
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

    $(".addResource_btn").click(function () {
        openPanel();
    })

    //批量删除
    $(".delAll_btn").click(function () {
        var checkStatus = table.checkStatus(aliasPrefix + 'Table'),
            data = checkStatus.data,
            resourceIds = [];
        if (data.length > 0) {
            for (var i in data) {
                resourceIds.push(data[i].resourceId);
            }
            layer.confirm('确定删除选中的资源？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/admin/" + aliasPrefix + "/remove", {ids: resourceIds.join(",")}, function (data) {
                    tableIns.reload();
                    layer.close(index);
                });
            });
        } else {
            layer.msg("请选择需要删除的资源");
        }
    });

    //列表操作
    table.on('tool(' + aliasPrefix + 'List)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'edit') { //编辑
            openPanel(data);
        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此资源？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/admin/" + aliasPrefix + "/remove", {ids: data.resourceId}, function (data) {
                    tableIns.reload();
                    layer.close(index);
                });
            });
        } else if (layEvent === 'look') { //预览
            layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问")
        }
    });

})