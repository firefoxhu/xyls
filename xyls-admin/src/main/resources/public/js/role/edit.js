layui.use(['form', 'layer', 'jquery', 'laydate', 'upload'], function () {
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        $ = layui.jquery;

    form.on("submit(editRole)", function (data) {

        //截取文章内容中的一部分文字放入文章摘要
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});

        var params = {
            roleId: $(".roleId").val(),
            roleName: $(".roleName").val(),
            description: $(".roleDescription").val()
        };

        // 实际使用时的提交信息
        $.post("/admin/role/edit", params, function (res) {

            if (res.code == "S00000") {
                setTimeout(function () {
                    top.layer.close(index);
                    top.layer.msg("修改添加成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                }, 500);
            } else {
                layer.alert("修改异常！" + res.code + "-" + res.msg);

            }
        });


        return false;
    });

})