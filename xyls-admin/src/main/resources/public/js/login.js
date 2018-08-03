layui.use(['form','layer','jquery'],function(){
    var form = layui.form,
        layer =  layui.layer,
        $ = layui.jquery;

    $(".loginBody .seraph").click(function(){
        layer.msg("功能正在开发中...",{
            time:5000
        });
    })

    $("#imageBtn").click(function(){
        this.src="/code/image?height=35";
    });

    //登录按钮
    form.on("submit(login)",function(data){
        var that=$(this);
        that.text("登录中...").attr("disabled","disabled").addClass("layui-disabled");
        setTimeout(function(){
            $.ajax({
                url:"/authentication/form?timer="+new Date().getTime(),
                type:"post",
                data:{
                    username:$("#username").val(),
                    password:$("#password").val(),
                    imageCode:$("#code").val(),
                    "remember-me":$("#remember-me")[0].checked
                },
                dataType:"json",
                success:function(res){
                    if(res.code == "T"){
                        layer.msg(res.message+"跳转主页中...");
                        setTimeout(function(){
                            layer.closeAll();
                            window.location.href="/admin/index";
                        },800);
                    }else {
                        layer.alert(res.message);
                        document.getElementById("imageBtn").src="/code/image?height=35";
                        that.removeAttr("disabled");
                        that.removeClass("layui-disabled");
                        that.text("登录")
                    }

                },
                error:function(res){
                    layer.alert(res);
                    document.getElementById("imageBtn").src="/code/image?height=35";
                    that.removeAttr("disabled");
                    that.removeClass("layui-disabled");
                    that.text("登录")
                }
            })
        },1000);
        return false;
    })

    //表单输入效果
    $(".loginBody .input-item").click(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function(){
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function(){
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() != ''){
            $(this).parent().addClass("layui-input-active");
        }else{
            $(this).parent().removeClass("layui-input-active");
        }
    })
})
