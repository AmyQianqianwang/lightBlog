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
            <li class="nav-item dl-selected"><div class="nav-item-inner nav-order">业务管理</div></li>
            <li class="nav-item dl-selected"><div class="nav-item-inner nav-order">内容管理</div></li>
            <li class="nav-item dl-selected"><div class="nav-item-inner nav-user">账号管理</div></li>
        </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten">

    </ul>
</div>
<script type="text/javascript" src="${webapp.url}assets/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="${webapp.url}assets/js/bui-min.js"></script>
<script type="text/javascript" src="${webapp.url}assets/js/common/main-min.js"></script>
<script type="text/javascript" src="${webapp.url}assets/js/config-min.js"></script>
<script>
    BUI.use('common/main',function(){
        var config = [{id:'1',menu:[{text:'系统管理',items:[{id:'12',text:'机构管理',href:'${webapp.context}admin/node/index.html'},{id:'3',text:'权限管理',href:'${webapp.context}admin/user/auth/index.html'},{id:'4',text:'用户管理',href:'${webapp.context}admin/user/index.html'},{id:'6',text:'菜单管理',href:'${webapp.context}admin/menu/index.html'}]}]},
                      {id:'7',homePage : '9',menu:[{text:'业务管理',items:[{id:'9',text:'查询业务',href:'${webapp.context}admin/node/index.html'}]}]},
                      {id:'13',homePage : '14',menu:[{text:'内容管理',items:[{id:'14',text:'文章管理',href:'${webapp.context}admin/article/index.html'}]}]},
                      {id:'10',menu:[{text:'账号管理',items:[{id:'11',text:'修改密码',href:'${webapp.context}admin/me/edit.html'}]}]}
                     ];
        new PageUtil.MainPage({
            modulesConfig : config
        });
    });
</script>
</body>
</html>