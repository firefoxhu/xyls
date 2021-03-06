layui.use(['form', 'layer', 'jquery', 'laydate', 'upload'], function () {
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        $ = layui.jquery;

    var newsTypeThumbnail = null;


    //上传缩略图
    upload.render({
        elem: '.thumbBox',
        url: '/file/upload',
        method: "post",  //此处是为了演示之用，实际使用中请将此删除，默认用post方式提交
        done: function (res, index, upload) {
            $('.thumbImg').attr('src', res.data.fileName);
            $('.thumbBox').css("background", "#fff");
            newsTypeThumbnail = res.data.fileName;
        }
    });


    form.on("submit(addNewsType)", function (data) {

        //截取文章内容中的一部分文字放入文章摘要
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});

        var params = {
            newsTypeParentId: $(".newsTypeParentId").val(),
            newsTypeName: $(".newsTypeName").val(),
            newsTypeTitle: $(".newsTypeTitle").val(),
            newsTypeThumbnail: newsTypeThumbnail,
            newsTypeNavigationUrl: $(".newsTypeNavigationUrl").val()
        };

        if (newsTypeThumbnail == null) {
            layer.msg("请选择栏目缩略图！");
            return false;
        }


        // 实际使用时的提交信息
        $.post("/admin/newsType/add", params, function (res) {

            if (res.code == "S00000") {
                setTimeout(function () {
                    top.layer.close(index);
                    top.layer.msg("栏目添加成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                }, 500);
            } else {
                layer.alert("文章添加异常！" + res.code + "-" + res.msg);

            }
        });


        return false;
    });

})