<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://dragonyun.com/jsp/taglib" prefix="jc"%>
<c:if test="${page.totalRow > 0 }">
<div id="paging">
		<c:if test="${page.currentPage - page.barSize > 1 }">
		<a href="${jc:page(page.url,1)}"><span class="page first-page">1</span></a>
		<a href="${jc:page(page.url,(page.currentPage - 1))}"><span class="page first-page">${page.prev}</span></a>
		</c:if>
		<c:forEach begin="${(page.currentPage - page.barSize) <= 0 ? 1 : (page.currentPage - page.barSize) }" end="${page.currentPage - 1}" var="i">
		<a href="${jc:page(page.url,i)}"><span class="page">${i}</span></a>
		</c:forEach>
		<span class="page now-page">${page.currentPage}</span>
		<c:forEach begin="${page.currentPage + 1}" end="${(page.currentPage + page.barSize) < page.totalPage ? (page.currentPage + page.barSize) : page.totalPage}" var="i">
			<a href="${jc:page(page.url,i)}"><span class="page">${i}</span></a>
		</c:forEach>
		<c:if test="${page.currentPage + page.barSize < page.totalPage }">
			<a href="${jc:page(page.url,(page.currentPage + 1))}"><span class="page last-page">${page.next}</span></a>
			<a href="${jc:page(page.url,page.totalPage)}"><span class="page">${page.totalPage}</span></a>
		</c:if>
<%-- 		<span class="current">共${page.totalRow}条</span> --%>
</div>
</c:if>