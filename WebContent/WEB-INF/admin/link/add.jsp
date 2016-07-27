<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
 <head>
  <title>新建文章分类</title>
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
    <form id ="J_Form" class="form-horizontal" action="${webapp.context}admin/link/add.html" method="post">
      <h3>基本信息</h3>
      <div class="row">
          <div class="control-group span12">
                <label class="control-label"><s>*</s>UID：</label>
            <div class="controls">
                <input name="uid" type="text" class="control-text" data-rules="{required:true}">
            </div>
          </div>
      </div>
      <div class="row">
	      <div class="control-group span12">
	            <label class="control-label"><s>*</s>名称：</label>
	        <div class="controls">
	            <input name="name" type="text" class="control-text" data-rules="{required:true}">
	        </div>
	      </div>
      </div>
      <div class="row">
          <div class="control-group span12">
                <label class="control-label"><s>*</s>网址：</label>
            <div class="controls">
                <input name="url" type="text" class="control-text" data-rules="{required:true}">
            </div>
          </div>
      </div>
      <div class="row">
        <div class="control-group span12">
          <label class="control-label">是否停用：</label>
          <div class="controls">
            <select name="isHalt">
              <option value="F">否</option>
              <option value="T">是</option>
            </select>
          </div>
        </div>
      </div>
      <div class="row">
          <div class="control-group span12">
                <label class="control-label"><s>*</s>备注：</label>
            <div class="controls control-row-auto">
                <textarea name="remark" class="control-row4 input-large" data-rules="{required:true}"></textarea>
            </div>
          </div>
      </div>
      <hr/>
      <button type="submit" class="button button-primary">保存</button>
      <button type="reset" class="button">重置</button>
    </form>
  </div>
  <script type="text/javascript" src="${webapp.context}assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="${webapp.context}assets/js/bui-min.js"></script>
  <script type="text/javascript" src="${webapp.context}assets/js/config-min.js"></script>
  <script type="text/javascript">
    BUI.use('${webapp.context}assets/js/common/page');
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