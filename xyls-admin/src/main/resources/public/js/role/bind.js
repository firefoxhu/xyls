layui.use(['form','layer','jquery'],function(){
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        $ = layui.jquery;


    //监听指定开关
    form.on('checkbox(checkboxFilter)', function(data){

        var id=$(this).attr("data-id");

        var url = "";

        var params ={
            roleId:$(".roleName").attr("data-id"),
            resourceIds:id
        }

        var title ="绑定"

        if(this.checked === true) {
            url ="/admin/role/bind/resource"
        }else {
            url ="/admin/role/unbind/resource"
            title ="解绑"
        }

        // 实际使用时的提交信息
        $.post(url,params,function(res){

            if(res.code == "S00000"){
                layer.msg(title+"成功！");
            }else {
                layer.alert("角色添加异常！"+res.code+"-"+res.msg);

            }
        });

    });


})