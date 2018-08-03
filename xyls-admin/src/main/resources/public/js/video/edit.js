layui.use(['form','layer','jquery','laydate','upload'],function(){
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        laydate = layui.laydate,
        $ = layui.jquery;

    var pic = $(".thumbImg").attr("src");
    var url = $("#video").attr("data-url");

    //上传缩略图
    upload.render({
        elem: '.thumbBox',
        url: '/file/upload',
        method : "post",  //此处是为了演示之用，实际使用中请将此删除，默认用post方式提交
        done: function(res, index, upload){
            if(res.code=="OK") {
                $('.thumbImg').attr('src', res.data.fileName);
                $('.thumbBox').css("background", "#fff");
                pic = res.data.fileName
                layer.msg("缩略图修改成功！")
            }
        }
    });

    upload.render({
        elem: '#video'
        ,url: '/admin/video/upload'
        ,accept: 'video' //视频
        ,done: function(res){

            if(res.code=="OK"){
                url = res.data.fileName
                layer.msg("视频文件修改成功！")
            }

        }
    });



    //格式化时间
    function filterTime(val){
        if(val < 10){
            return "0" + val;
        }else{
            return val;
        }
    }
    //定时发布
    var time = new Date();
    var submitTime = time.getFullYear()+'-'+filterTime(time.getMonth()+1)+'-'+filterTime(time.getDate())+' '+filterTime(time.getHours())+':'+filterTime(time.getMinutes())+':'+filterTime(time.getSeconds());
    laydate.render({
        elem: '.releaseTime',
        type: 'datetime',
        trigger : "click",
        done : function(value, date, endDate){
            submitTime = value;
        }
    });


    var typeId = $(".newsTypeId").attr("data-category");
    $(".newsTypeId").val(typeId);
    $(".releaseStatus input[name='release'][value='" + $("#status").attr("data-status") + "']").attr("checked", "checked");
    form.render();



    form.on("submit(updateVideo)",function(data){

        //截取文章内容中的一部分文字放入文章摘要
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});

        var params={
            id:$("#videoId").val(),
            title:$(".title").val(),
            abstracts:$(".abstracts").val(),
            categoryId:$(".newsTypeId").val(),
            pic:pic,
            author:$(".author").val(),
            createTime:$(".releaseTime").val(),
            status:$(".releaseStatus input[name='release']:checked").val(),
            url:url,
            source:$(".source").val()
        };

        if(pic === null){
            layer.msg("请选择缩略图！")
            return;
        }
        params.pic =pic;

        if(pic === url){
            layer.msg("请上传视频文件！")
            return;
        }
        params.url = url



        // 实际使用时的提交信息
        $.post("/admin/video/edit",params,function(res){

            if(res.code == "S00000"){
                setTimeout(function(){
                    top.layer.close(index);
                    top.layer.msg(res.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                },500);
            }else {
                layer.alert("文章添加异常！"+res.code+"-"+res.msg);
            }


        })


        return false;
    })

    //预览
    form.on("submit(look)",function(){
        layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问");
        return false;
    });

});