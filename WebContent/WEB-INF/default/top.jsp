<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt"
	uri="http://java.sun.com/jsp/jstl/fmt"%>
<header class="header">
	<div class="central">
		<h1 class="logo">
			<a href="${webapp.url}index"
				title="${webapp.name} - ${webapp.descript}">${webapp.name}</a>
		</h1>
		<ul class="nav">
			<li><a href="${webapp.url}index"
				<c:if test="${webapp.uri=='/index'}">class="current"</c:if>>首页</a></li>
			<li><a href="${webapp.url}article/type"
				<c:if test="${webapp.uri=='/article/type'}">class="current"</c:if>>分类</a></li>
			<li><a href="${webapp.url}tag"
				<c:if test="${top=='tag'}">class="current"</c:if>>标签</a></li>
			<li id="menu-page-76"><a href="${webapp.url}link"
				<c:if test="${webapp.uri=='/link'}">class="current"</c:if>>友链</a></li>
<!-- wqq下载 -->
			<li><a href="${webapp.url}download"
				<c:if test="${top=='download'}">class="current"</c:if>>下载</a></li>
<!-- wqq下载 -->				
		</ul>
		<ul class="header-menu">
			<li class="menu-follow"><a href="#"></a></li>
		</ul>
		<form method="get" class="search-form"
			action="${webapp.url}index">
			<input class="search-input" name="q" type="text"
				placeholder="输入关键字搜索" autofocus="" x-webkit-speech="" /> <input
				class="btn btn-primary search-submit" type="submit"
				value="搜索" />
		</form>
	</div>
</header>