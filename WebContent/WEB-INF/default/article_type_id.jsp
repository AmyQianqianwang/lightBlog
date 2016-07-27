<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://dragonyun.com/jsp/taglib" prefix="jc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" lang="zh-CN">
<head>
<jsp:include page="jscss.jsp" />
<title>${webapp.name} - ${webapp.descript}</title>
</head>
<body class="home blog" id="hasfixed">

	<jsp:include page="top.jsp" />

	<section class="focus">
	<div class="central">
	   <div class="toptip"><a href="${webapp.url}index">网站首页</a>&gt;${articleType.name}</div>
	</div>
	</section>

	<section class="central container">
	<div class="content-wrap">
		<div class="content">
			<c:forEach var="blog" items="${page.results}">
				<article class="excerpt  cate1 auth1">
				<h2>
					<a href="${webapp.url}article/${blog.id}" title="${blog.title}">${blog.title}</a>
				</h2>
				<div class="info">
					<span class="spndate"><fmt:formatDate
							value="${blog.createDatetime }" type="both" /></span><span class="spnname">${blog.userName }</span><span
						class="spncomm"><a
						href="${webapp.url}article/${blog.id}#comments"
						title="查看${blog.assess}的评论">${blog.assess}条评论</a></span><span
						class="spnview">${blog.click}次浏览</span>
				</div>
				<c:if test="${blog.logo != null}">
					<div>
						<img src="${blog.logo}" alt="." style="max-width: 720px;" />
					</div>
				</c:if>
				<div class="note">
					<p>${blog.content}</p>
					<p class="readmore">
						<a href="${webapp.url}article/${blog.id}" title="${blog.title}">阅读全文</a>
					</p>
				</div>
				</article>
			</c:forEach>
            <div class="paging">
                <a href="${webapp.url}${jc:page(page.url,1)}"><span class="page first-page">&laquo;</span></a>
                <c:forEach var="i" begin="${page.currentPage<=3?1:(page.currentPage-3)}" end="${page.currentPage}" step="1">
                <c:if test="${i != page.currentPage}">
                <a href="${webapp.url}${jc:page(page.url,i)}"><span class="page">${i}</span></a>
                </c:if>
                </c:forEach>
                <span class="page now-page">${page.currentPage}</span>
                <c:forEach var="i" begin="${page.currentPage}" end="${(page.totalPage-page.currentPage)>3?(page.currentPage+3):page.totalPage}" step="1">
                <c:if test="${i != page.currentPage}">
                <a href="${webapp.url}${jc:page(page.url,i)}"><span class="page">${i}</span></a>
                </c:if>
                </c:forEach>
                <a href="${webapp.url}${jc:page(page.url,page.totalPage)}"><span class="page last-page">&raquo;</span></a>
            </div>
		</div>
	</div>
	<jsp:include page="right.jsp" /> </section>
	<section class="showlinks">
	<div class="central">
		<ul class="showlink">
		</ul>
	</div>
	</section>
	<jsp:include page="bottom.jsp" />
</body>
</html>