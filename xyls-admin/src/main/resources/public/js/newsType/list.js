layui.use(['form', 'layer', 'laydate', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    //新闻列表
    var tableIns = table.render({
        elem: '#newsTypeList',
        url: '/admin/newsType/list',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limit: 20,
        limits: [10, 15, 20, 25],
        id: "newsTypeTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'newsTypeId', title: 'ID', width: 60, align: "center"},
            {field: 'newsTypeParentId', title: '父栏目', width: 60, align: "center"},
            {field: 'newsTypeName', title: '栏目名称', width: 350},
            {
                field: 'newsTypeThumbnail', title: '缩略图', align: 'center', templet: function (row) {
                    return "<img src='" + row.newsTypeThumbnail + "' style='width: 80px;height: 50px;' />";
                }
            },
            {field: 'newsTypeNavigationUrl', title: '导航地址', align: 'center'},
            {field: 'status', title: '状态', align: 'center'},
            {field: 'createTime', title: '创建时间', align: 'center', minWidth: 110,},
            {title: '操作', width: 170, templet: '#newsTypeListBar', fixed: "right", align: "center"}
        ]]
    });


    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function () {
        if ($(".searchVal").val() != '') {
            table.reload("newsTypeTable", {
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
    function openPanel(news) {
        var title = "添加栏目";
        var uriPage = "/admin/newsType/page/add";

        if (news) {
            title = "修改栏目";
            uriPage = "/admin/newsType/page/edit?id=" + news.newsTypeId;
        }


        var index = layui.layer.open({
            title: title,
            type: 2,
            content: uriPage,
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回栏目列表', '.layui-layer-setwin .layui-layer-close', {
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

    $(".addNewsType_btn").click(function () {
        openPanel();
    })

    //批量删除
    $(".delAll_btn").click(function () {
        var checkStatus = table.checkStatus('newsTypeTable'),
            data = checkStatus.data,
            newsClsId = [];
        if (data.length > 0) {
            for (var i in data) {
                newsClsId.push(data[i].newsTypeId);
            }
            layer.confirm('确定删除选中的栏目？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/admin/newsType/remove", {ids: newsClsId.join(",")}, function (data) {
                    tableIns.reload();
                    layer.close(index);
                });
            });
        } else {
            layer.msg("请选择需要删除的栏目");
        }
    });

    //列表操作
    table.on('tool(newsTypeList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'edit') { //编辑
            openPanel(data);
        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此栏目？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/admin/newsType/remove", {ids: data.newsTypeId}, function (data) {
                    tableIns.reload();
                    layer.close(index);
                });
            });
        } else if (layEvent === 'look') { //预览
            layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问")
        }
    });

})