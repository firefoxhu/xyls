layui.use(['form','layer','jquery','laydate','upload'],function(){
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        $ = layui.jquery;

    var sexValue= $(".userSex").attr("data-value");
    $.each($("input[type=radio]"),function () {
        if(this.value === sexValue){
            this.checked = true;
        }
    })

    var province = $(".province");
    province.val(province.attr("data-value"));

    var city = $(".city");
    city.val(city.attr("data-value"));

    var area = $(".area");
    area.val(area.attr("data-value"));

    var status = $(".status");
    status.val(status.attr("data-value"));

    var roles = $(".roleBox").attr("data-value");

    if(roles != ""){
        roles = roles.split(",");

        $.each($("input[type=checkbox]"),function () {

            for(x in roles){
                console.log(x)
                if(this.value === roles[x]){
                    this.checked = true
                }
            }

        })
    }

    form.render();

    upload.render({
        elem: '.thumbBox'
        ,url: '/file/upload'
        ,multiple: true
        ,allDone: function(obj){
        }
        ,done: function(res, index, upload){ //每个文件提交一次触发一次。详见“请求成功的回调”
            if(res.code=="OK"){
                var prevDoms=$(".thumbImg");
                prevDoms.attr("src", res.data.fileName);
                layer.msg("上传成功！");
            }

        }
    });


    form.on("submit(editUser)",function(data){

        //截取文章内容中的一部分文字放入文章摘要
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});

        var params={
            userId:$(".userId").val(),
            userName:$(".userName").val(),
            userNickName:$(".userNickName").val(),
            phone:$(".userPhone").val(),
            sex:$('input[type=radio]:checked').val(),
            userCardNumber:$(".userCardNumber").val(),
            userProvince:$(".province").val(),
            userCity:$(".city").val(),
            userArea:$(".area").val(),
            userStreet:$(".street").val(),
            userPersonalSignature:$(".description").val(),
            userHeaderImage:$(".thumbImg").attr("src"),
            roles:null,
            status:$(".status").val(),
            email:$(".userEmail").val()
        };
        var roleIds = [];
        $("input[type=checkbox]:checked").each(function(){
            roleIds.push($(this).val());//向数组中添加元素
        });

        params.roles = roleIds.join(",");


        // 实际使用时的提交信息
        $.post("/admin/user/edit",params,function(res){

            if(res.code == "S00000"){
                setTimeout(function(){
                    top.layer.close(index);
                    top.layer.msg("修改添加成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                },500);
            }else {
                layer.alert("修改异常！"+res.code+"-"+res.msg);

            }
        });


        return false;
    });

})