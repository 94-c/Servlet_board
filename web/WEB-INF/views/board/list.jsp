<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>myapp</title>
    <%@ include file="/inc/asset.jsp" %>
    <style>
        .table th:nth-child(1) {
            width: 60px;
        }

        .table th:nth-child(2) {
            width: auto;
        }

        .table th:nth-child(3) {
            width: 80px;
        }

        .table th:nth-child(4) {
            width: 120px;
        }

        .table th:nth-child(5) {
            width: 60px;
        }

        .table td:nth-child(2) {
            text-align: left;
        }
    </style>
</head>
<body>
<%@ include file="/inc/header.jsp" %>

<section class="main-section">
    <h1>Board<small>List</small></h1>

    <table class="table table-bordered">
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>이름</th>
            <th>날짜</th>
            <th>조회수</th>
        </tr>

        <c:forEach items="${ list}" var="dto">
            <tr>
                <td class="text-center">${dto.seq}</td>
                <td class="text-center"><a href="/myapp/board/view.do?seq=${dto.seq}">${dto.subject}</a>
                    <c:if test="${dto.isnew < (2/24)}">
                        <span class="label label-danger">new</span>
                    </c:if>
                </td>
                <td class="text-center">${dto.name}</td>
                <td class="text-center">${dto.regdate}</td>
                <td class="text-center">${dto.readcount}</td>
            </tr>
        </c:forEach>
    </table>

    <div class="btns">
        <button type="button" class="btn btn-primary" onclick="location.href='/myapp/board/add.do';">글쓰기</button>
        <button type="button" class="btn btn-default" onclick="location.reload();">새로고침</button>
    </div>
</section>

<%@ include file="/inc/init.jsp" %>
</body>
</html>