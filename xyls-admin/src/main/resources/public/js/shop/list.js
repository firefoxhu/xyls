layui.use(['form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    var aliasPrefix = 'shop';

    //新闻列表
    var tableIns = table.render({
        elem: '#'+aliasPrefix+'List',
        url : '/admin/'+aliasPrefix+'/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 20,
        limits : [10,15,20,25],
        id : aliasPrefix+'Table',
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: 'ID', width:60, align:"center"},
            {field: 'name', title: '名称', width:300, align:"center"},
            {field: 'telephone', title: '座机电话', width:200},
            {field: 'description', title: '描述', align:'center'},
            {field: 'goOut', title: '是否上门',  align:'center',templet:function (row) {
                    if(row.goOut === '0') return '上门服务'
                }
            },
            {field: 'status', title: '状态',  align:'center',templet:function (row) {
                    if(row.goOut === '0') return '正常'
                }
             },
            {field: 'createTime', title: '创建时间', align:'center', minWidth:110},
            {title: '操作', width:170, templet:'#'+aliasPrefix+'ListBar',fixed:"right",align:"center"}
        ]]
    });


    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
            table.reload(aliasPrefix+"Table",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    key: $(".searchVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的内容");
        }
    });

    //添加文章
    function openPanel(shop){
        var title="添加门店";
        var uriPage="/admin/"+aliasPrefix+"/page/add";

        if(shop){
            title="修改门店";
            uriPage="/admin/"+aliasPrefix+"/page/edit?id="+shop.id;
        }


        var index = layui.layer.open({
            title : title,
            type : 2,
            content : uriPage,
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回栏目列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        })
    }
    $(".addShop_btn").click(function(){
        openPanel();
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus(aliasPrefix+'Table'),
            data = checkStatus.data,
            newsClsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsClsId.push(data[i].id);
            }
            layer.confirm('确定删除选中的门店？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/admin/"+aliasPrefix+"/remove",{ids : newsClsId.join(",") },function(data){
                tableIns.reload();
                layer.close(index);
                });
            });
        }else{
            layer.msg("请选择需要删除的门店");
        }
    });

    //列表操作
    table.on('tool('+aliasPrefix+'List)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
             openPanel(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此门店？',{icon:3, title:'提示信息'},function(index){
                $.post("/admin/"+aliasPrefix+"/remove",{ ids: data.id },function(data){
                    tableIns.reload();
                    layer.close(index);
                });
            });
        } else if(layEvent === 'look'){ //预览
            layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问")
        }
    });

})