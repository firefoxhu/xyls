layui.use(['form', 'layer', 'laydate', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    //新闻列表
    var tableIns = table.render({
        elem: '#newsList',
        url: '/admin/news/list',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limit: 20,
        limits: [10, 15, 20, 25],
        id: "newsListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'newsId', title: 'ID', width: 60, align: "center"},
            {field: 'newsTypeId', title: '类型', width: 60, align: "center"},
            {field: 'newsTitle', title: '文章标题', width: 350},
            {field: 'newsAuthor', title: '发布者', align: 'center'},
            {field: 'status', title: '发布状态', align: 'center', templet: "#newsStatus"},
            {field: 'newsViews', title: '浏览量', align: 'center'},
            {field: 'newsShowType', title: '展示方式', align: 'center', templet: "#newsShowType"},
            {
                field: 'newsIsTop', title: '是否置顶', align: 'center', templet: function (d) {
                    var checked = "checked";
                    if (d.newsIsTop == "0") checked = "";
                    return '<input type="checkbox" name="newsIsTop" lay-filter="newsIsTop" lay-skin="switch" lay-text="是|否" ' + checked + '>'
                }
            },
            {
                field: 'createTime', title: '发布时间', align: 'center', minWidth: 110, templet: function (d) {
                    return d.createTime.substring(0, 10);
                }
            },
            {title: '操作', width: 170, templet: '#newsListBar', fixed: "right", align: "center"}
        ]]
    });

    //是否置顶
    form.on('switch(newsIsTop)', function (data) {
        var index = layer.msg('修改中，请稍候', {icon: 16, time: false, shade: 0.8});
        setTimeout(function () {
            layer.close(index);
            if (data.elem.checked) {
                layer.msg("置顶成功！");
            } else {
                layer.msg("取消置顶成功！");
            }
        }, 500);
    })

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function () {
        table.reload("newsListTable", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                title: $(".searchVal").val()  //搜索的关键字
            }
        })
    });

    //添加文章
    function openPanel(news) {
        var title = "添加文章";
        var uriPage = "/admin/news/page/add?classId=2904e1433c9541939e36f8b45e1abbdc";

        if (news) {
            title = "修改文章";
            uriPage = "/admin/news/page/edit?classId=2904e1433c9541939e36f8b45e1abbdc&id=" + news.newsId;
        }


        var index = layui.layer.open({
            title: title,
            type: 2,
            content: uriPage,
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                if (news) {


                    // body.find(".newsTitle").val(news.newsTitle);
                    // body.find(".newSecondTitle").val(news.newsSecondTitle);
                    // body.find(".newsShowType").val(news.newsShowType);
                    // body.find(".author").val(news.newsAuthor);
                    // body.find(".releaseTime").val(news.createTime);
                    // body.find("#editor").val(news.newsContent);
                    // body.find(".releaseStatus input[name='release'][value='"+news.status+"']").prop("checked","checked");
                    // body.find("input[name='newsClsId'][value='"+news.newsClsId+"']").prop("checked","checked");
                    // body.find("input[name='newsType'][value='"+news.newsClsId+"']").prop("checked","checked");
                    // if(news.newsIsTop == "1"){
                    //  body.find("input[name='newsIsTop']").prop("checked","checked");
                    //     body.find("input[name='newsIsTop']").prop("value","1");
                    // }
                    //
                    // for(x in news.images)
                    //     body.find(".thumbImg")[x].prop("src",images[x]);
                    //
                    // form.render();
                }
                setTimeout(function () {
                    layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
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

    $(".addNews_btn").click(function () {
        openPanel();
    })

    //批量删除
    $(".delAll_btn").click(function () {
        var checkStatus = table.checkStatus('newsListTable'),
            data = checkStatus.data,
            newsId = [];
        if (data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].newsId);
            }
            layer.confirm('确定删除选中的文章？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/admin/news/remove", {ids: newsId.join(",")}, function (data) {
                    tableIns.reload();
                    layer.close(index);
                });
            });
        } else {
            layer.msg("请选择需要删除的文章");
        }
    });

    //列表操作
    table.on('tool(newsList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'edit') { //编辑
            openPanel(data);
        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此文章？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/admin/news/remove", {ids: data.newsId}, function (data) {
                    tableIns.reload();
                    layer.close(index);
                });
            });
        } else if (layEvent === 'look') { //预览
            layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问")
        }
    });

})