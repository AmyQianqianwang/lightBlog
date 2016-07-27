<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%><%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt"
    uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0" />
<title>${webapp.domain} - ${webapp.name}</title>
<style type="text/css">
body {
    margin: 0;
    padding: 0;
    font-size: 14px;
    line-height: 1.231;
    color: #555;
    text-align: center;
    font-family: "微软雅黑", "黑体";
}
a {
    color: #999;
    text-decoration: none;
}
a:hover {
    color: #2E7BB8;
}
#container {
    width: 60%;
    height: 315px;
    margin: 50px auto;
    border: #666 solid 1px;
}
#container #title {
    overflow: hidden;
    padding-top: 30px;
}
#container #title h1 {
    font-size: 36px;
    text-align: center;
    color: #FF0000;
}
#content p {
    font-size: 18px;
}
#footer {
    width: 100%;
    padding: 20px 0px;
    font-size: 14px;
    color: #555;
    text-align: center;
}
</style>
</head>
<body>
    <!-- Copyright (c) blog.jiucheng.org -->
    <div id="container">
        <div id="title">
            <h1>${webapp.domain}</h1>
        </div>
        <div id="content">
            ${webapp.descript}
        </div>
    </div>
</body>
</html>