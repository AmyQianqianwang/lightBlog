<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>后台管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="${webapp.url}assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="${webapp.url}assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="${webapp.url}assets/css/main-min.css" rel="stylesheet" type="text/css" />
</head>
<script type="text/javascript" src="${webapp.url}assets/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript">
//防掉线
function online(){
    $.get("${webapp.url}admin/online.json", function(rs){
      });
    t=setTimeout("online()",1200000);
}
online();
</script>
<body>
<div class="header">
    <div class="dl-title">
        <!--<img src="/chinapost/Public/assets/img/top.png">-->
    </div>
    <div class="dl-log">欢迎您，<span class="dl-log-user">${webapp.user.uid}</span><a href="${webapp.context}admin/logout.html?fr=${webapp.uri}" title="退出系统" class="dl-log-quit">[退出]</a>
    </div>
</div>
<div class="content">
    <div class="dl-main-nav">
        <div class="dl-inform"><div class="dl-inform-title"><s class="dl-inform-icon dl-up"></s></div></div>
        <ul id="J_Nav"  class="nav-list ks-clear">
            <li class="nav-item dl-selected"><div class="nav-item-inner nav-home">系统管理</div></li>
            <li class="nav-item dl-selected"><div class="nav-item-inner nav-order">内容管理</div></li>
            <li class="nav-item dl-selected"><div class="nav-item-inner nav-user">账号管理</div></li>
        </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten">

    </ul>
</div>
<script type="text/javascript" src="${webapp.url}assets/js/bui-min.js"></script>
<script type="text/javascript" src="${webapp.url}assets/js/common/main-min.js"></script>
<script type="text/javascript" src="${webapp.url}assets/js/config-min.js"></script>
<script type="text/javascript">
    BUI.use('${webapp.context}assets/js/common/main',function(){
        var config = [{id:'1',
        	menu:[{text:'系统管理',
        		items:[{id:'3',text:'权限管理',href:'${webapp.context}admin/user/auth/index.html'},
        		       {id:'4',text:'用户管理',href:'${webapp.context}admin/user/index.html'},
        		       {id:'5',text:'站点管理',href:'${webapp.context}admin/webapp/edit.html'}]}]},
                      {id:'13',homePage : '14',menu:[{text:'内容管理',items:[{id:'14',text:'文章管理',href:'${webapp.context}admin/article/index.html'},
                                                                         {id:'15',text:'文章分类',href:'${webapp.context}admin/article/type/index.html'},
                                                                         {id:'17',text:'留言管理',href:'${webapp.context}admin/ly/index.html'},
                                                                         {id:'16',text:'友情链接',href:'${webapp.context}admin/link/index.html'}]}]},
                      {id:'10',menu:[{text:'账号管理',items:[{id:'11',text:'修改密码',href:'${webapp.context}admin/me/edit.html'},
                                                         {id:'22',text:'修改昵称',href:'${webapp.context}admin/me/nick/edit.html'}]}]}
                     ];
        new PageUtil.MainPage({
            modulesConfig : config
        });
    });
</script>
</body>
</html>