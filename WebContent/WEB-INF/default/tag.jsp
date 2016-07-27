<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" lang="zh-CN">
<head>
 <jsp:include page="jscss.jsp" />
 <title>标签 - ${webapp.name} - ${webapp.descript}</title>
</head>

  <body class="home blog" id="hasfixed">

	<jsp:include page="top.jsp" />
    
    <section class="focus">
      <div class="central">
        <div class="toptip"><a href="${webapp.url}index">网站首页</a>&gt;TagCloud</div>
      </div>
    </section>
    
    <section class="central container">
      <div class="content-wrap">
        <div class="content">
	   <article class="article">
		<header class="article-header">
			<h1 class="article-title">TagCloud</h1>
		</header>
		<p class="article-meta"></p>
		<div class="article-entry"><div class="tags-cloud">
					<c:forEach var="tag" items="${tags}">
						<a class="tag-name tag-name-size-0" title="0" href="${webapp.url}index?t=${tag.name}">${tag.name}(${tag.size})</a>
					</c:forEach>
		</div></div>
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