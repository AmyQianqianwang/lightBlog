<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
 <head>
  <title>页面操作快捷方式</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
       <link href="${webapp.context}assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="${webapp.context}assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="${webapp.context}assets/css/page-min.css" rel="stylesheet" type="text/css" /> 
 </head>
 <body>
  
  <div class="container">
    <div class="row">
      <div class="span10">
        <div class="tips tips-large tips-success">
          <span class="x-icon x-icon-success"><i class="icon icon-ok icon-white"></i></span>
          <div class="tips-content">
            <h2>成功信息</h2>
            <p class="auxiliary-text">你可以继续操作以下内容：</p>
            <p>
              <a class="page-action" data-type="setTitle" title="继续编辑" href="${webapp.context}admin/me/nick/edit.html">继续编辑</a>
              <a class="page-action" data-type="close" title="关闭本页" href="#">关闭本页</a>
            </p>
          </div>
        </div>
      </div> 
    </div>
  </div>
  <script type="text/javascript" src="${webapp.context}assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="${webapp.context}assets/js/bui-min.js"></script>
  <script type="text/javascript" src="${webapp.context}assets/js/config-min.js"></script>
  <script type="text/javascript">
    BUI.use('${webapp.context}assets/js/common/page');
  </script>
<body>
</html>  