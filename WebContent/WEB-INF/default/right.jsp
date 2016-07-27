<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt"
	uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
    $(function() {
        $("#blog_type_js").html('<img src="${webapp.url}assets/img/load.gif" alt="加载中..." align="middle"/>');
        $("#blog_rnd_js").html('<img src="${webapp.url}assets/img/load.gif" alt="加载中..." align="middle"/>');
        $("#new_ly_list_js").html('<img src="${webapp.url}assets/img/load.gif" alt="加载中..." align="middle"/>');
        $("#index_link_list_js").html('<img src="${webapp.url}assets/img/load.gif" alt="加载中..." align="middle"/>');
    });
</script>
<aside class="sidebar">
	<div class="widget widget_divCatalog">
		<h3 class="widget_tit">文章分类</h3>
		<div id="blog_type_js"></div>
	</div>
	<div class="widget widget_divAboutKen">
		<h3 class="widget_tit">热门推荐</h3>
		<div id="blog_rnd_js"></div>
	</div>

	<div class="widget widget_divComments">
		<h3 class="widget_tit">最新留言</h3>
		<div id="new_ly_list_js"></div>
	</div>
	<div class="widget widget_divFavorites">
		<h3 class="widget_tit">热门标签</h3>
        <div id="tag_top_js"></div>
	</div>
	<div class="widget widget_divLinkage">
		<h3 class="widget_tit">友情链接</h3>
		<div id="index_link_list_js"></div>
	</div>
</aside>
<script type="text/javascript">
$(function() {
	$.getJSON("${webapp.url}article/type/json?timestamp=" + Date.parse(new Date()),function(data){
		var apd = "<ul>";
		 for (var i = 0; i < data.length; i++) {
			 apd = apd + '<li class="li-cate cate-' + data[i].id + '"><a href="${website.url}/article/type/' + data[i].id + '">' + data[i].name + '<span class="article-nums">(' + data[i].size + ')</span></a></li>';
	     }
		 apd = apd + "</ul>";
		 $("#blog_type_js").html(apd);
	});
	
   $.getJSON("${webapp.url}article/rnd/json?timestamp=" + Date.parse(new Date()),function(data){
        var apd1 = "<ul>";
         for (var i = 0; i < data.length; i++) {
        	 apd1 = apd1 + '<li style="text-overflow:ellipsis;">';
        	 apd1 = apd1 + '<a href="${webapp.url}article/' + data[i].id + '" title="' + data[i].title + '">' + data[i].title + '</a>';
             apd1 = apd1 + '</li>';
         }
         apd1 = apd1 + "</ul>";
         $("#blog_rnd_js").html(apd1);
    });
   
   $.getJSON("${webapp.url}ly/new/json?timestamp=" + Date.parse(new Date()),function(data){
       var apd2 = "<ul>";
        for (var i = 0; i < data.length; i++) {
            apd2 = apd2 + '<li style="text-overflow:ellipsis;">';
            apd2 = apd2 + '<a href="${webapp.url}article/' + data[i].articleId + '#comments" title="' + data[i].content + '">' + data[i].content + '</a>';
            apd2 = apd2 + '</li>';
        }
        apd2 = apd2 + "</ul>";
        $("#new_ly_list_js").html(apd2);
   });
   
   $.getJSON("${webapp.url}link/new/fr/json?timestamp=" + Date.parse(new Date()),function(data){
       var apd2 = "<ul>";
        for (var i = 0; i < data.length; i++) {
            apd2 = apd2 + '<li style="text-overflow:ellipsis;">';
            apd2 = apd2 + '<a href="${webapp.url}link/to/' + data[i].uid + '" title="' + data[i].remark + '" target="_blank">' + data[i].name + '</a>';
            apd2 = apd2 + '</li>';
        }
        apd2 = apd2 + "</ul>";
        $("#index_link_list_js").html(apd2);
   });
   
   $.getJSON("${webapp.url}tag/top/json?timestamp=" + Date.parse(new Date()),function(data){
       var apd4 = "<ul>";
        for (var i = 0; i < data.length; i++) {
            apd4 = apd4 + '<li style="text-overflow:ellipsis;">';
            apd4 = apd4 + '<a href="${webapp.url}index?t=' + data[i].name + '" title="' + data[i].name + '">' + data[i].name + '(' + data[i].size + ')</a>';
            apd4 = apd4 + '</li>';
        }
        apd4 = apd4 + "</ul>";
        $("#tag_top_js").html(apd4);
   });
});
</script>