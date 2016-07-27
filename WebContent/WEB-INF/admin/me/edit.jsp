<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${webapp.context}css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="${webapp.context}css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="${webapp.context}css/style.css" />
    <script type="text/javascript" src="${webapp.context}js/jquery.js"></script>
    <script type="text/javascript" src="${webapp.context}js/jquery.sorted.js"></script>
    <script type="text/javascript" src="${webapp.context}js/bootstrap.js"></script>
    <script type="text/javascript" src="${webapp.context}js/ckform.js"></script>
    <script type="text/javascript" src="${webapp.context}js/common.js"></script>
    <style type="text/css">
        body {
            padding-bottom: 40px;
        }
        .sidebar-nav {
            padding: 9px 0;
        }

        @media (max-width: 980px) {
            /* Enable use of floated navbar text */
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }


    </style>
</head>
<form action="${webapp.context}admin/me/edit.html" method="post">
<table class="table table-bordered table-hover definewidth m10">
    <tr>
        <td width="10%" class="tableleft">原密码</td>
        <td><input type="text" name="ypassword"/></td>
    </tr>
    <tr>
        <td class="tableleft">新密码</td>
        <td><input type="text" name="xpassword"/></td>
    </tr>
    <tr>
        <td class="tableleft"></td>
        <td>
            <button type="submit" class="btn btn-primary" type="button">保存</button><strong style="color: red;">  ${err}</strong>
        </td>
    </tr>
</table>
</form>
</body>
</html>