<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" lang="zh-CN">
<head>
 <jsp:include page="jscss.jsp" />
 <title>文章分类 - ${webapp.name} - ${webapp.descript}</title>
</head>

  <body class="home blog" id="hasfixed">

	<jsp:include page="top.jsp" />
    
    <section class="focus">
      <div class="central">
        <div class="toptip"><a href="${webapp.url}index">网站首页</a>&gt;文章分类</div>
      </div>
    </section>
    
    <section class="central container">
      <div class="content-wrap">
        <div class="content">
	   <article class="article">
		<header class="article-header">
			<h1 class="article-title">文章分类</h1>
		</header>
		<p class="article-meta"></p>
		<div class="article-entry">
		<div class="links-box">
					<c:forEach var="articleType" items="${articleTypes}">
					<a href="${webapp.url}article/type/${articleType.id}">${articleType.name}(${articleType.size})</a>
					</c:forEach>
		</div>
		</div>
		</article>
		
        </div>
      </div>
		
		<!-- right 右边部分 START -->
    		<jsp:include page="right.jsp"/>
      	<!-- right 右边部分  END-->
		
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