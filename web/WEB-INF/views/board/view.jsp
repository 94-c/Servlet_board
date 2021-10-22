<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>myapp</title>
    <%@ include file="/inc/asset.jsp" %>
    <style>
        .main-section .table th { width: 120px; }
        .main-section .table td { width: 280px; text-align: left; }
        .main-section .table tr:nth-child(4) td { height: 300px; }
    </style>
</head>
<body>
<!-- view.jsp -->
<%@ include file="/inc/header.jsp" %>

<section class="main-section">

    <h1>Board <small>View</small></h1>

    <table class="table table-bordered">
        <tr>
            <th>번호</th>
            <td>${ dto.seq }</td>
            <th>이름</th>
            <td>${ dto.name }(${ dto.id })</td>
        </tr>
        <tr>
            <th>날짜</th>
            <td>${ dto.regdate }</td>
            <th>읽음</th>
            <td>${ dto.readcount }</td>
        </tr>
        <tr>
            <th>제목</th>
            <td colspan="3">${ dto.subject }</td>
        </tr>
        <tr>
            <th>내용</th>
            <td colspan="3">${ dto.content }</td>
        </tr>
    </table>

    <div class="btns">

        <button type="button" class="btn btn-primary"
                onclick="location.href='/myapp/board/edit.do?seq=${ dto.seq }';">수정하기</button>

        <button type="button" class="btn btn-primary"
                onclick="location.href='/myapp/board/del.do?seq=${ dto.seq }';">삭제하기</button>

        <button type="button" class="btn btn-default"
                onclick="location.href='/myapp/board/list.do';">돌아가기</button>

    </div>

</section>

<%@ include file="/inc/init.jsp" %>
</body>
</html>