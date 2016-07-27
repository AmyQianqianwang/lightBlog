<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
 <head>
  <title>编辑文章分类</title>
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
    <form id ="J_Form" class="form-horizontal" action="${webapp.context}admin/link/edit.html" method="post">
      <h3>名称</h3>
      <div class="row">
          <div class="control-group span12">
                <label class="control-label"><s>*</s>UID：</label>
            <div class="controls">
                <input name="uid" type="text" class="control-text" data-rules="{required:true}" value="${link.uid}">
            </div>
          </div>
      </div>
      <div class="row">
          <div class="control-group span12">
                <label class="control-label"><s>*</s>名称：</label>
            <div class="controls">
                <input name="name" type="text" class="control-text" data-rules="{required:true}" value="${link.name}">
            </div>
          </div>
      </div>
      <div class="row">
          <div class="control-group span12">
                <label class="control-label"><s>*</s>网址：</label>
            <div class="controls">
                <input name="url" type="text" class="control-text" data-rules="{required:true}" value="${link.url}">
            </div>
          </div>
      </div>
      <div class="row">
        <div class="control-group span12">
          <label class="control-label">是否停用：</label>
          <div class="controls">
            <select name="isHalt">
              <option value="F" <c:if test="${link.isHalt=='F' }">selected="selected"</c:if>>否</option>
              <option value="T" <c:if test="${link.isHalt=='T' }">selected="selected"</c:if>>是</option>
            </select>
          </div>
        </div>
      </div>
      <div class="row">
          <div class="control-group span12">
                <label class="control-label"><s>*</s>备注：</label>
            <div class="controls control-row-auto">
                <textarea name="remark" class="control-row4 input-large" data-rules="{required:true}">${link.remark}</textarea>
            </div>
          </div>
      </div>
      <hr/>
      <button type="submit" class="button button-primary">保存</button>
      <button type="reset" class="button">重置</button>
      <input type="hidden" name="id" value="${link.id}">
    </form>
  </div>
  <script type="text/javascript" src="${webapp.context}assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="${webapp.context}assets/js/bui-min.js"></script>
  <script type="text/javascript" src="${webapp.context}assets/js/config-min.js"></script>
  
<script type="text/javascript">
var editor = new Simditor({
      textarea: $('#editor'),
      upload:{
            url: '${webapp.context}upload-file.html',
            params: null,
            fileKey: 'upload_file',
            connectionCount: 3,
            leaveConfirm: '正在上传文件，如果离开上传会自动取消'}
    });
</script>
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