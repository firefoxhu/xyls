layui.use(['form', 'layer', 'jquery', 'laydate', 'upload'], function () {
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        laydate = layui.laydate,
        $ = layui.jquery;


    //格式化时间
    function filterTime(val) {
        if (val < 10) {
            return "0" + val;
        } else {
            return val;
        }
    }

    //定时发布
    var time = new Date();
    var submitTime = time.getFullYear() + '-' + filterTime(time.getMonth() + 1) + '-' + filterTime(time.getDate()) + ' ' + filterTime(time.getHours()) + ':' + filterTime(time.getMinutes()) + ':' + filterTime(time.getSeconds());
    laydate.render({
        elem: '#release',
        type: 'datetime',
        trigger: "click",
        done: function (value, date, endDate) {
            submitTime = value;
        }
    });

    upload.render({
        elem: '.thumbBox'
        , url: '/file/upload'
        , multiple: true
        , allDone: function (obj) {
        }
        , done: function (res, index, upload) { //每个文件提交一次触发一次。详见“请求成功的回调”
            if (res.code == "OK") {
                var prevDoms = $(".thumbImg");
                prevDoms.attr("src", res.data.fileName);
                layer.msg("上传成功！");
            }

        }
    });

    form.on("submit(addUser)", function (data) {

        //截取文章内容中的一部分文字放入文章摘要
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});

        var params = {
            userName: $(".userName").val(),
            userNickName: $(".userNickName").val(),
            phone: $(".userPhone").val(),
            sex: $('input[type=radio]:checked').val(),
            userCardNumber: $(".userCardNumber").val(),
            userProvince: $(".province").val(),
            userCity: $(".city").val(),
            userArea: $(".area").val(),
            userStreet: $(".street").val(),
            userPersonalSignature: $(".description").val(),
            userHeaderImage: $(".thumbImg").attr("src"),
            roles: null,
            status: $(".status").val(),
            email: $(".userEmail").val(),
            userPassword: $(".userPassword").val(),
            reUserPassword: $(".reUserPassword").val(),
            passwordInvalid: $("#release").val()
        };
        var roleIds = [];
        $("input[type=checkbox]:checked").each(function () {
            roleIds.push($(this).val());//向数组中添加元素
        });

        params.roles = roleIds.join(",");


        // 实际使用时的提交信息
        $.post("/admin/user/add", params, function (res) {

            if (res.code == "S00000") {
                setTimeout(function () {
                    top.layer.close(index);
                    top.layer.msg("用户添加成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                }, 500);
            } else {
                top.layer.close(index);
                layer.alert("用户添加异常！" + res.code + "-" + res.msg);

            }
        });


        return false;
    });

})