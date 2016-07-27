<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
 <head>
  <title> 搜索表单</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="${webapp.context}assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="${webapp.context}assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="${webapp.context}assets/css/page-min.css" rel="stylesheet" type="text/css" />
 </head>
 <body>
  
  <div class="container">
 
    <form id="searchForm" class="form-horizontal">
      <div class="row">
        <div class="control-group span8">
          <div class="controls">
            <input type="text" class="control-text" name="q">
            <button  type="button" id="btnSearch" class="button button-primary">搜索</button>
          </div>
        </div>
      </div>
    </form>
 
    <div class="search-grid-container">
      <div id="grid"></div>
    </div>
  </div>
  <script type="text/javascript" src="${webapp.context}assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="${webapp.context}assets/js/bui-min.js"></script>
  <script type="text/javascript" src="${webapp.context}assets/js/config-min.js"></script>
<script type="text/javascript">
  BUI.use(['${webapp.context}assets/js/common/search','${webapp.context}assets/js/common/page'],function (Search) {
    
    var enumObj = {"T":"是","F":"否"},
      columns = [
          {title:'ID',dataIndex:'id',width:80,renderer:function(v){
            return Search.createLink({
              id : 'detail' + v,
              title : '用户信息',
              text : v,
              href : '${webapp.context}admin/user/detail.html?id=' + v
            });
          }},
          {title:'UID',dataIndex:'uid',width:200},
          {title:'昵称',dataIndex:'nick',width:200},
          {title:'是否停用',dataIndex:'isHalt',width:80,renderer:BUI.Grid.Format.enumRenderer(enumObj)},
          {title:'创建时间',dataIndex:'createDatetime',width:100,renderer:BUI.Grid.Format.dateRenderer},
          {title:'修改时间',dataIndex:'modifyDatetime',width:100,renderer:BUI.Grid.Format.dateRenderer},
          {title:'操作',dataIndex:'',width:200,renderer : function(value,obj){
        	var authStr = Search.createLink({ //链接使用 此方式
                id : 'userAuth' + obj.id,
                title : '用户权限',
                text : '权限',
                href : '${webapp.context}admin/user/auth.html?id=' + obj.id
              }),
            editStr =  Search.createLink({ //链接使用 此方式
                id : 'edit' + obj.id,
                title : '编辑用户',
                text : '编辑',
                href : '${webapp.context}admin/user/edit.html?id=' + obj.id
              }),
              delStr = '<span class="grid-command btn-del" title="删除用户">删除</span>';//页面操作不需要使用Search.createLink
            return authStr + editStr + delStr;
          }}
        ],
      store = Search.createStore('${webapp.context}admin/user/page.json'),
      gridCfg = Search.createGridCfg(columns,{
        tbar : {
          items : [
            {text : '<i class="icon-plus"></i>新建',btnCls : 'button button-small',handler:editDialog},
            {text : '<i class="icon-edit"></i>编辑',btnCls : 'button button-small',handler:function(){alert('编辑');}},
            {text : '<i class="icon-remove"></i>删除',btnCls : 'button button-small',handler : delFunction}
          ]
        },
        plugins : [BUI.Grid.Plugins.CheckSelection] // 插件形式引入多选表格
      });
 
    var  search = new Search({
        store : store,
        gridCfg : gridCfg
      }),
      grid = search.get('grid');
    //删除操作
    function delFunction(){
      var selections = grid.getSelection();
      delItems(selections);
    }
 
    function delItems(items){
      var ids = {};
      var i = 0;
      BUI.each(items,function(item){
        ids['id[' + i + ']'] = item.id;
        i++;
      });
 
      if(ids){
        BUI.Message.Confirm('确认要删除选中的记录么？',function(){
          $.ajax({
            url : '${webapp.context}admin/user/delete.html',
            dataType : 'json',
            data : ids,
            success : function(data){
              if(data.err == "0"){ //删除成功
                search.load();
              }else{ //删除失败
                BUI.Message.Alert(data.msg);
              }
            }
        });
        },'question');
      }
    }
 
    //监听事件，删除一条记录
    grid.on('cellclick',function(ev){
      var sender = $(ev.domTarget); //点击的Dom
      if(sender.hasClass('btn-del')){
        var record = ev.record;
        delItems([record]);
      }
    });
    
  });
  function editDialog() {
	  top.topManager.openPage({
	    id : 'addUser',
	    href : '${webapp.context}admin/user/add.html',
	    title : '新建用户'
	  });
  };
</script>
<body>
</html>