<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
 <head>
  <title>编辑用信息</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
       <link href="${webapp.context}assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="${webapp.context}assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="${webapp.context}assets/css/page-min.css" rel="stylesheet" type="text/css" />
   <style type="text/css">
    code {
      padding: 0px 4px;
      color: #d14;
      background-color: #f7f7f9;
      border: 1px solid #e1e1e8;
    }
   </style>
 </head>
 <body>
  
  <div class="container">
    <form id ="J_Form" class="form-horizontal" action="${webapp.context}admin/user/auth.html" method="post">
      <h3>基本信息</h3>
      <div class="row">
        <div class="control-group span12">
          <label class="control-label"><s>*</s>昵称：</label>
          <div class="controls">
            <label>${user.nick}</label>
          </div>
        </div>
        <div class="control-group span12">
          <label class="control-label"><s>*</s>UID：</label>
          <div class="controls">
            <label>${user.uid}</label>
          </div>
        </div>
      </div>
      <hr/>
      <h3>权限</h3>
      <div class="row">
        <div class="control-group span24">
			  <div class="bui-form-group controls">
			    <c:forEach items="${userAuthes}" var="userAuth">
                    <label class="checkbox"><input name="auth" type="checkbox" value="${userAuth.id}" ${userAuth.checked}/>${userAuth.descript}</label>    			    
			    </c:forEach>
			  </div>
        </div>
      </div>
      <hr/>
      <div class="row">
        <div class="form-actions offset3">
          <button type="submit" class="button button-primary">保存</button>
          <button type="reset" class="button">重置</button>
        </div>
      </div>
      <input type="hidden" name="id" value="${user.id}">
    </form>
  </div>
  <script type="text/javascript" src="${webapp.context}assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="${webapp.context}assets/js/bui-min.js"></script>
  <script type="text/javascript" src="${webapp.context}assets/js/config-min.js"></script>
  <script type="text/javascript">
    BUI.use('common/page');
  </script>
  <script type="text/javascript">
    BUI.use('bui/form',function (Form) {
      var form = new Form.HForm({
        srcNode : '#J_Form'
      }).render();
    });
  </script>

<body>
</html>  