layui.use(['form','layer','jquery','laydate','upload'],function(){
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        $ = layui.jquery;

    var   shopThumbnail=[];

    var goOut= $(".shopGoOut");
    goOut.val(goOut.attr("data-value"));

    var province = $(".shopProvince");
    province.val(province.attr("data-value"));

    var city = $(".shopCity");
    city.val(city.attr("data-value"));

    var area = $(".shopArea");
    area.val(area.attr("data-value"));

    var imgBox = $(".imgBox");
    var images  = imgBox.attr("data-value").split(",");
    var thumbImgs = $(".thumbImg");

    $.each(images,function(index,dom){
        thumbImgs.eq(index).attr("src",images[index])
        shopThumbnail.push(images[index])
    });

    form.render();


    $(".restImg").click(function(){
        shopThumbnail = [];

        $.each(thumbImgs,function(){
            $(this).attr("src","");
        });
    });


    upload.render({
        elem: '.thumbBox'
        ,url: '/file/upload'
        ,multiple: true
        ,allDone: function(obj){
            for(x in shopThumbnail){
                var prevDoms=$(".thumbImg");
                if(prevDoms.eq(x).attr("src")==""){
                    prevDoms.eq(x).attr("src", shopThumbnail[x]);
                }
            }
        }
        ,done: function(res, index, upload){ //每个文件提交一次触发一次。详见“请求成功的回调”
            if(res.code=="OK"){
                shopThumbnail.push(res.data.fileName);
            }

        }
    });




    form.on("submit(editShop)",function(data){

        //截取文章内容中的一部分文字放入文章摘要
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});

        var params={
            name:$(".shopName").val(),
            telephone:$(".shopTelephone").val(),
            description:$(".shopDescription").val(),
            positionX:$(".shopX").val(),
            positionY:$(".shopY").val(),
            province:$(".shopProvince").val(),
            city:$(".shopCity").val(),
            area:$(".shopArea").val(),
            street:$(".shopStreet").val(),
            goOut:$(".shopGoOut").val(),
            picture:null,
            id: $(".shopId").val()
        };

        if(shopThumbnail.length !=3){
            layer.msg("请选择栏目缩略图！");
            return false;
        }

        params.picture = shopThumbnail.join(",");



        // 实际使用时的提交信息
        $.post("/admin/shop/edit",params,function(res){

            if(res.code == "S00000"){
                setTimeout(function(){
                    top.layer.close(index);
                    top.layer.msg("修改成功！");
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