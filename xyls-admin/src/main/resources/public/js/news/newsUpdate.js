layui.use(['form','layer','jquery','laydate','upload'],function(){
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        laydate = layui.laydate,
        $ = layui.jquery;

    var images=[];
    upload.render({
        elem: '#choseImage'
        ,url: '/file/upload'
        ,multiple: true
        ,allDone: function(obj){
            for(x in images){
                var prevDoms=$(".thumbImg");
                if(prevDoms.eq(x).attr("src")==""){
                    prevDoms.eq(x).attr("src", images[x]);
                }
            }
        }
        ,done: function(res, index, upload){ //每个文件提交一次触发一次。详见“请求成功的回调”

            console.log(res)
            if(res.code=="OK"){
                images.push(res.data.fileName);
            }

        }
    });

    $("#reUploadImage").click(function(){
        $(".thumbImg").each(function(){
            $(this).attr("src","");
        });
        images=[];
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


    //监听指定开关
    form.on('switch(newsIsTop)', function(data){
        if(data.elem.checked == true){
            data.elem.value="1";
        }else{
            data.elem.value="0";
        }
    });

    form.verify({
        newsTitle : function(val){
            if(val == ''){
                return "文章标题不能为空";
            }
        }
    })
    form.on("submit(updateNews)",function(data){

        //截取文章内容中的一部分文字放入文章摘要
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});

        var params={
            newsId:$("#newsId").val(),
            newsTitle:$(".newsTitle").val(),
            newsSecondTitle:$(".newSecondTitle").val(),
            newsClsId:$(".newsClsId").val(),
            newsShowType:$(".newsShowType").val(),
            newsAuthor:$(".author").val(),
            createTime:$(".releaseTime").val(),
            newsIsTop:$("input[name='newsIsTop']").val(),
            status:$(".releaseStatus input[name='release']:checked").val(),
            newsHomeThumbnail:null,
            newsTypeId:$(".newsTypeId").val(),
            newsContent:editor.html(),
            newsSource:$(".source").val()
        };



        var scaleImages=[];
        $(".thumbImg").each(function(){
            var uri=$(this).attr("src");
            if(uri !="")scaleImages.push(uri);
        });

        if(scaleImages.length <= 0){
            layer.msg("请选择缩略图！")
            return;
        }

        params.newsHomeThumbnail=scaleImages.join(",");



        // 实际使用时的提交信息
        $.post("/admin/news/edit",params,function(res){

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

    $.ajax({
        url:"/admin/news/"+$("#newsId").val()+"/preview",
        type:"get",
        success:function (res) {

            if(res.code == "S00000") {

                var  news=res.data;

                /**
                 * 初始化数据
                 * @type {Array}
                 */
                $(".newsTitle").val(news.newsTitle);
                $(".newSecondTitle").val(news.newsSecondTitle);

                //layui组件渲染需要注意
                if (news.newsIsTop == "1") {
                    $("input[name='newsIsTop']").attr({"checked": "checked", "value": "1"});
                }
                $(".newsShowType").val(news.newsShowType);


                $(".author").val(news.newsAuthor);
                $(".releaseTime").val(news.createTime);

                $(".releaseStatus input[name='release'][value='" + news.status + "']").attr("checked", "checked");

                $(".newsClsId").val(news.newsClsId);
                $(".newsTypeId").val(news.newsTypeId);


                var imagesPrev = news.newsHomeThumbnail.split(",");
                for (x in imagesPrev) {
                    $(".thumbImg").eq(x).attr("src", imagesPrev[x]);
                }

                $(".source").val(news.newsSource);

                editor.html( news.newsContent);

                form.render();
            }else{
                setTimeout(function () {
                    top.layer.close(index);
                    top.layer.msg(res.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                }, 500);

            }
        }
    });

});