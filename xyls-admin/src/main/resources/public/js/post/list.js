layui.use(['form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    var aliasPrefix = 'post';

    //新闻列表
    var tableIns = table.render({
        elem: '#'+aliasPrefix+'List',
        url : '/admin/'+aliasPrefix+'/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 20,
        limits : [10,15,20,25],
        id : aliasPrefix+"ListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'pid', title: 'ID', width:60, align:"center"},
            {field: 'content', title: '内容', width:350, align:"center"},
            {field: 'concat', title: '联系方式', width:120},
            {field: 'pic', title: '图片地址', align:'center'},
            {field: 'ip', title: '地址', align:'center'},
            {field: 'top', title: '顶置',  align:'center',templet:function(d){
                var checked="checked";if(d.top == "0")checked="";
                return '<input type="checkbox" name="postTop" lay-filter="postTop" lay-skin="switch" lay-text="顶置|否" '+checked+'>'
            }},
            {field: 'status', title: '状态', align:'center',templet:function(d){
                var checked="checked";if(d.status == "0")checked="";
                return '<input type="checkbox" name="status" lay-filter="status" lay-skin="switch" lay-text="正常|禁用" '+checked+'>'
            }},
            {field: 'createTime', title: '发布时间', align:'center', minWidth:90, templet:function(d){
                return d.createTime.substring(0,10);
            }}
        ]]
    });

    //是否禁用
    form.on('switch(status)', function(data){
        var checkStatus = table.checkStatus(aliasPrefix+'ListTable'),
            row = checkStatus.data;
        var index = layer.msg('修改中，请稍候',{icon: 16,time:false,shade:0.8});

        $.ajax({
            url:'/admin/post/status',
            data:{
                postId: row[0].pid
            },
            type: 'post',
            success:function (res) {
                layer.close(index);

                if(data.elem.checked){
                    layer.msg("操作成功！");
                }else{
                    layer.msg("操作成功！");
                }
            }
        })
    })

    //是否置顶
    form.on('switch(postTop)', function(data){
        var checkStatus = table.checkStatus(aliasPrefix+'ListTable'),
            row = checkStatus.data;

        var index = layer.msg('修改中，请稍候',{icon: 16,time:false,shade:0.8});

        $.ajax({
            url:'/admin/post/top',
            data:{
                postId: row[0].pid
            },
            type: 'post',
            success:function (res) {

                layer.close(index);

                if(data.elem.checked){
                    layer.msg("置顶成功！");
                }else{
                    layer.msg("取消置顶成功！");
                }
            }
        })
    })

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        table.reload(aliasPrefix+"ListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                title: $(".searchVal").val()  //搜索的关键字
            }
        })
    });

    //列表操作
    table.on('tool('+aliasPrefix+'List)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            layer.alert("编辑");
        } else if(layEvent === 'del'){ //删除
            layer.alert("删除");
        } else if(layEvent === 'look'){ //预览
            layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问");
        }
    });

})