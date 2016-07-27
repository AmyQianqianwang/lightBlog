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
    <form id ="J_Form" class="form-horizontal" action="${webapp.context}admin/article/edit.html" method="post">
      <h3>基本</h3>
      <div class="row">
          <div class="control-group span12">
               <label class="control-label">标题：</label>
               <div class="controls">
                    <input name="title" type="text" style="width: 300px;" value="${article.title}">
               </div>
          </div>
          <div class="control-group span12">
               <label class="control-label">分类：</label>
               <div class="controls" id="selected">
                    <select name="typeId">
                        <option value="0">无</option>
                    </select>
               </div>
          </div>
      </div>

       <div class="row">
          <div class="control-group span12">
               <label class="control-label">标签：</label>
               <div class="controls">
                    <input name="tags" type="text" style="width: 300px;" value="${article.tags}">
               </div>
          </div>
      </div>
      <hr/>
      <h3>内容</h3>
      <textarea name="content" id="editor" placeholder="这里输入内容" style="width: 100%;height: 120px;">${article.content}</textarea>
      <button type="submit" class="button button-primary">保存</button>
      <button type="reset" class="button">重置</button>
      <input type="hidden" name="id" value="${article.id}">
    </form>
  </div>
     <script type="text/javascript" src="${webapp.context}simditor/js/jquery.min.js"></script>
     
  <script type="text/javascript" src="${webapp.context}assets/js/bui-min.js"></script>
  <script type="text/javascript" src="${webapp.context}assets/js/config-min.js"></script>
<script type="text/javascript">
$.getJSON("${webapp.context}admin/article/type/list.json",
        function(data) {
                var text = "<select name=\"typeId\"><option value=\"0\">无</option>";
                $.each(data,function(id, item) {
                                    text = text
                                            + "<option value=\"" + item.id + "\"";
                                    if(item.id == "${article.typeId}") {
                                    	text = text + " selected=\"selected\"";
                                    }
                                    text = text + ">" + item.name + "</option>";
                                });
                text = text + "</select>";
                $("#selected").html(text);
        });
</script>
<script charset="utf-8" src="${website.url}/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="${website.url}/kindeditor/lang/zh_CN.js"></script>
        <script>
            KindEditor.ready(function(K) {
                K.create('#editor', {
                        uploadJson : '${webapp.context}upload-file.html',
                        fileManagerJson : 'file_manager_json',
                        allowFileManager : true
                });
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