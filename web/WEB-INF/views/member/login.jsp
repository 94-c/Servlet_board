<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>myapp</title>
    <%@ include file="/inc/asset.jsp" %>
</head>
<body>
<!-- login.jsp -->
<%@ include file="/inc/header.jsp" %>

<section class="main-section">

    <h1>User</h1>

    <div class="user-list">
        <button type="button" class="btn btn-default"
                onclick="location.href='/myapp/member/loginok.do?id=hong&pw=1111';">홍길동</button>
        <button type="button" class="btn btn-default"
                onclick="location.href='/myapp/member/loginok.do?id=kim&pw=1111';">최우우</button>
        <button type="button" class="btn btn-default"
                onclick="location.href='/myapp/member/loginok.do?id=admin&pw=1111';">관리자</button>
    </div>

</section>


<%@ include file="/inc/init.jsp" %>
<script>

</script>
</body>
</html>
