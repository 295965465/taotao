<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	 <ul id="contentCategory" class="easyui-tree">  </ul>
</div>
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
$(function(){
    //在contentCategory中创建一个树
	$("#contentCategory").tree({
		url : '/content/category/list',
		animate: true,
		method : "GET",
        //右击鼠标事件
		onContextMenu: function(e,node){
		    //关闭鼠标默认的事件
            e.preventDefault();
            $(this).tree('select',node.target);
            //显示菜单栏
            $('#contentCategoryMenu').menu('show',{
                left: e.pageX,//在鼠标点击的位置显示
                top: e.pageY
            });
        },
        //在添加的节点编辑之后触发
        onAfterEdit : function(node){
		    //获取当前的这个树
        	var _tree = $(this);
        	if(node.id == 0){
        		// 新增节点
        		$.post("/content/category/create",{parentId:node.parentId,name:node.text},function(data){
        			if(data.status == 200){
        			    //更新节点
        				_tree.tree("update",{
            				target : node.target,
            				id : data.data.id
            			});
        			}else{
        				$.messager.alert('提示','创建'+node.text+' 分类失败!');
        			}
        		});
        	}else{
        		$.post("/content/category/update",{id:node.id,name:node.text});
        	}
        }
	});
});
//处理点击菜单的事件
function menuHandler(item){
    //获取树
	var tree = $("#contentCategory");
	//获取树中被选中的节点
	var node = tree.tree("getSelected");
	//===强语言判断,==弱语言判断
	if(item.name === "add"){
	    //在被点击的节点下面追加一个子节点
		tree.tree('append', {
            parent: (node?node.target:null),//被添加子节点的父节点的所在
            data: [{
                text: '新建分类',
                id : 0,
                parentId : node.id
            }]
        });
		//找到id为0的节点
		var _node = tree.tree('find',0);//根节点
        //找到节点后开始编辑
		tree.tree("select",_node.target).tree('beginEdit',_node.target);
	}else if(item.name === "rename"){
		tree.tree('beginEdit',node.target);
	}else if(item.name === "delete"){
		$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
			if(r){
				$.post("/content/category/delete/",{id:node.id},function(data){
				    if(data.status == 200){
                        tree.tree("remove",node.target);
                    }else{
                        $.messager.alert('提示','是父节点不能被删除');
                    }

				});
			}
		});
	}
}
</script>