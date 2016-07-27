<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" lang="zh-CN">
<head>
 <title>${article.title} - ${webapp.name} - ${webapp.descript}</title>
 <jsp:include page="jscss.jsp" />
 <script src="${webapp.url}tpl/util.js" type="text/javascript"></script>
 <script type="text/javascript">
 	$(function(){
 	 	$("#blog_ly_js").html('<img src="${webapp.url}assets/img/load.gif" alt="加载中..." align="middle"/>');
 	});
 	
 	function echo(v) {
        $("#cancel-reply").css("display","inline");
 		$("#lyId").val(v);
 	};
 	function unecho() {
        $("#cancel-reply").css("display","none");
 		$("#lyId").val("");
 	}
 </script>
</head>

  <body class="home blog" id="hasfixed">

	<jsp:include page="top.jsp" />
    <section class="focus">
      <div class="central">
        <div class="toptip"><a href="${webapp.url}index">网站首页</a>&gt;<a href="<c:if test='${article.typeId > 0}'>${webapp.url}article/type/${article.typeId}</c:if>">${article.typeName}</a>&gt;${article.title}</div>
      </div>
    </section>
    
    <section class="central container">
      <div class="content-wrap">
        <div class="content">
        
	    <article class="article">
	    	<header class="article-header">
				<h1 class="article-title">${article.title}</h1>
				<p class="article-meta">
				<span class="spndate"><fmt:formatDate value="${article.createDatetime}" type="both"/></span><span class="spnname"><a href="${webapp.url}index">${article.userName }</a></span><span class="spncate"><a href="<c:if test='${article.typeId > 0}'>${webapp.url}article/type/${article.typeId}</c:if>" title="查看 ${article.typeName}中的全部文章" rel="category tag">${article.typeName}</a></span><span class="spncomm"><a href="${webapp.url}blog/${article.id}#comments" title="查看 ${article.title}的评论">${article.assess}条评论</a></span><span class="spnview"><span id="spn90"></span>${article.click}次浏览</span>                     
				</p>
			</header>
			<div class="db_post">
			</div>
			
		    <div class="article-entry">
		    ${article.content}
		    </div>
			<div class="article-footer">
				<div class="share"><div class="bdsharebuttonbox"><a href="#" class="bds_more" data-cmd="more"></a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a href="#" class="bds_tieba" data-cmd="tieba" title="分享到百度贴吧"></a><a href="#" class="bds_douban" data-cmd="douban" title="分享到豆瓣网"></a></div></div>
				<div class="article-tags">
				<span>标签</span>
                <c:forEach var="tag" items="${tags}">
                    <a href="${webapp.url}index?t=${tag}">${tag}</a>
                </c:forEach>
				</div>
				<p id="pre_next_blog_js">
				<!-- 上一个博客/下一个博客 -->
				<c:if test="${prev != null}">
					<a class="l" href="${webapp.url}article/${prev.id}" title="${prev.title}">上一篇：《${prev.title}》</a>
					</c:if>
					<c:if test="${next != null}">
					<a class="r" href="${webapp.url}article/${next.id}" title="${next.title}">下一篇：《${next.title}》</a>
				</c:if>
				</p>
				
			</div>
			<div class="db_post" style="margin:-15px 0 15px 0;"/>
		</article>
		
		<div id="respond">
		<script type="text/javascript">
			function validateInput(){
				saveReply();
				return true;
			}
		</script>
			<form id="frmSumbit" target="_self" method="post" action="${webapp.url}article/ly">
				<h3 class="base-tit">发表评论 <a rel="nofollow" id="cancel-reply" href="#divCommentPost" style="display:none;" onclick="unecho()">取消回复</a></h3>
				<div class="comt">
					<div class="comt-box">
						<textarea class="comt-area" name="content" id="txaArticle" rows="4" tabindex="5" onkeydown="if(event.ctrlKey&&event.keyCode==13){document.getElementById('submit').click();};"></textarea>
					</div>
		
					<div class="comt-ctrl">
						<a class="comt-addcode">&nbsp;&nbsp;</a>
						<input type="checkbox" name="chkRemember" checked="true" id="chkRemember" /><label for="chkRemember">记住我,下次回复时不用重新输入个人信息</label>
						
						<input class="comt-submit" type="submit" name="submit" id="submit" tabindex="5"  onclick="return validateInput();" value="发表评论" />
						<div class="comt-tips">
							<input type="hidden" name="articleId" id="inpId" value="${article.id}" />
							<input type="hidden" name="head_img" id="head_img_id" value="0"/>
							<input type="hidden" name="cate" id="inpArticle" value="blog" />
							<input type="hidden" name="inpRevID" id="inpRevID" value="" />
							<input type="hidden" name="lyId" id="lyId"/>
						</div>
					</div>
					<div class="comt-comterinfo" id="comment-author-info">
						<ul>
							<li><label for="author">昵称</label><input class="ipt" type="text" name="author" id="inpName" value="匿名" tabindex="1"><span>必填</span></li>
							<li><label for="email">邮箱</label><input class="ipt" type="mail" name="email" id="inpEmail" value="" tabindex="2"><span>保密，不会被公开</span></li>
							<li id="li_email_show" class="comt-comterinfo-url"><label for="url">网址</label><input class="ipt" type="url" name="url" id="inpHomePage" value="" tabindex="3"><span>可不填</span></li>
						</ul>
					</div>
				</div>
			</form>
		</div>

		<div id="postcomments">
			<h3 class="base-tit" id="comments">网友评论</h3>
			<ins style="display:none;" id="AjaxCommentBegin"></ins>
			<div id="blog_ly_js"></div>
		</div><ins style="display:none;" id="AjaxCommentEnd"></ins>
		 <script type="text/javascript">
		    loadReply();
		    function str(data) {
		    	var rs = "";
		    	for(var i = 0; i < data.length; i ++) {
		    		rs = rs + '<div class="comment" id="cmt' + data[i].id + '">';
		    		rs = rs + '<div class="c-avatar">';
		    		rs = rs + '<img class="avatar" src="${webapp.url}assets/tou/' + data[i].img + '" width="45" height="45" />';
		    		rs = rs + '</div>';
		    		rs = rs + '<div class="c-main" id="div-comment-' + data[i].id + '">';
		    		
		    		rs = rs + '<div class="c-meta"><span class="c-author"><a href="' + data[i].url + '" rel="nofollow" target="_blank">' + data[i].author + '</a></span><span class="c-time">' + data[i].createDatetime + '(' + data[i].ipName + ')</span>';
		    		rs = rs + '</div>';
		    		
		    		rs = rs + '<div class="msgarticle">' + data[i].content;
		    		rs = rs + str(data[i].lys);
		    		rs = rs + '<a style="display:none;" id="AjaxCommentEnd' + data[i].id + '"></a>';
		    		rs = rs + '</div>';
		    		
		    		rs = rs + '</div>';
		    		
                    rs = rs + '<div class="c-footer">';
                    rs = rs + '<a class="comment-reply-link" href="#frmSumbit" onclick="echo(' + data[i].id + ')">回复</a>';
                    rs = rs + '</div>';
                    
		    		rs = rs + '</div>';
		    	}
		    	return rs;
		    };
		    $(function(){
		    	 $.getJSON("${webapp.url}article/${article.id}/ly",function(data){
		    	        var apd0 = str(data);
		    	         $("#blog_ly_js").html(apd0);
		    	    });
		    });
		 </script>
		<div class="pagenav"></div>
		</div>

        </div>
      </div>
      
      <jsp:include page="right.jsp"/>
    </section>
    
    <section class="showlinks">
      <div class="central">
        <ul class="showlink">
        </ul>
      </div>
    </section>
    <jsp:include page="bottom.jsp"/>
  </body>
</html>