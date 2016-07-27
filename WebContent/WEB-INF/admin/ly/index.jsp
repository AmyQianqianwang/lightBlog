<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
 <head>
  <title>搜索表单</title>
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
              id : 'articleDetail' + v,
              title : '文章信息',
              text : v,
              href : '${webapp.context}admin/article/detail.html?id=' + v
            });
          }},
          {title:'内容',dataIndex:'content',width:300},
          {title:'作者',dataIndex:'author',width:80},
          {title:'邮箱',dataIndex:'email',width:120},
          {title:'网址',dataIndex:'url',width:120},
          {title:'文章名称',dataIndex:'articleTitle',width:200},
          {title:'停用',dataIndex:'isHalt',width:40,renderer:BUI.Grid.Format.enumRenderer(enumObj)},
          {title:'创建时间',dataIndex:'createDatetime',width:70,renderer:BUI.Grid.Format.dateRenderer},
          {title:'修改时间',dataIndex:'modifyDatetime',width:70,renderer:BUI.Grid.Format.dateRenderer},
          {title:'操作',dataIndex:'',width:80,renderer : function(value,obj){
              var delStr = '<span class="grid-command btn-del" title="停用">停用</span>';//页面操作不需要使用Search.createLink
        	  if(obj.isHalt == "T") {
        		  delStr = '<span class="grid-command btn-rec" title="恢复">恢复</span>';
        	  }
            return delStr;
          }}
        ],
      store = Search.createStore('${webapp.context}admin/ly/page.json'),
      gridCfg = Search.createGridCfg(columns,{
      });
 
    var  search = new Search({
        store : store,
        gridCfg : gridCfg
      }),
      grid = search.get('grid');
    
    function delItem(item,isHalt){
      var info = '确定停用？';
      var ids = {};
      ids['id'] = item.id;
      ids['isHalt'] = isHalt;
      if(isHalt == 'F') {
    	  info = '确定恢复？';
      };
      if(ids){
        BUI.Message.Confirm(info,function(){
          $.ajax({
            url : '${webapp.context}admin/ly/halt.html',
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
        delItem(record,'T');
      }else if(sender.hasClass('btn-rec')) {
          var record = ev.record;
          delItem(record,'F'); 
      }
    });
    
  });
</script>
<body>
</html>