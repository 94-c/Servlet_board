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
        .searchbox {
            text-align: center;
        }

        .searchbox .form-control {
            display: inline-block;
            width: auto;
        }

        .searchbox #search {
            width: 250px;
        }

        .searchBar {
            margin: 10px;
            text-align: center;
        }
    </style>
</head>
<body>
<%@ include file="/inc/header.jsp" %>


<section class="main-section">
    <h1>Board<small>List</small></h1>
    <!-- body -->
    <!-- 상단에 검색내용, 갯수 출력하기 -->
    <c:if test="${map.isSearch == 'y'}">
        <div class="searchBar">
            '${ map.search }'으로 검색한 결과 ${ list.size() }개의 게시물이 있습니다.
        </div>
    </c:if>
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
                    <!--게시판 리스트 페이지에서 댓글수 출력-->
                    <c:if test="${dto.ccnt > 0}">
                        <span class="badge">${dto.ccnt}</span>
                    </c:if>
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
        <!--인증 티켓 소유자만 보이게 하기 -->
        <c:if test="${ not empty id}">
            <button type="button" class="btn btn-primary" onclick="location.href='/myapp/board/add.do';">글쓰기</button>
        </c:if>
        <button type="button" class="btn btn-default" onclick="location.reload();">새로고침</button>
    </div>

    <!-- 검색기능 추가하기 -->
    <div class="searchbox">

        <form method="GET" action="/myapp/board/list.do">
            <select name="column" id="column" class="form-control">
                <option value="subject">제목</option>
                <option value="content">내용</option>
                <option value="name">이름</option>
                <option value="all">제목+내용</option>
            </select>
            <input type="text" name="search" id="search" class="form-control" required  placeholder="검색어를 입력하세요."/>
            <input type="submit" value="검색하기" class="btn btn-default" />
        </form>

    </div>
</section>

<%@ include file="/inc/init.jsp" %>
</body>
<!-- script -->
<script>

    <c:if test="${map.isSearch == 'y'}">
    $( '#column' ).val('${ map.column }');
    $( '#search' ).val('${ map.search }');
    </c:if>

</script>
</html>