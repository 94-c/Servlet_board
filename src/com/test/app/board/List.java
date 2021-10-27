package com.test.app.board;

import com.test.app.dao.BoardDAO;
import com.test.app.dto.BoardDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/board/list.do")
public class List extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 0. 검색 기능
        String column = req.getParameter("column");
        System.out.println("column" + column);

        String search = req.getParameter("search");
        String isSearch = "n";

        if (column != null && search != null && !column.equals("") && !search.equals("")) {
            isSearch = "y";
        }

        HashMap<String, String> map = new HashMap<String, String>();

        // 페이징 처리 -> 보고 싶은 페이지를 정하기 위한 처리
        int nowPage = 0;        // 현재 페이지번호
        int totalCount = 0;        // 총 게시물
        int pageSize = 10;        // 한 페이지당 출력할 게시물 수
        int totalPage = 0;        // 총 페이지 수
        int begin = 0;            // 가져올 게시물 시작 위치
        int end = 0;            // 가져올 게시물 끝 위치
        int n = 0;            // 페이지바 제작
        int loop = 0;            // 페이지바 제작
        int blockSize = 10;        // 페이지바 제작

        String page = req.getParameter("page");

        if (page == null || page.equals("")) {
            nowPage = 1;
        } else {
            nowPage = Integer.parseInt(page);
        }

        // 현재 페이지에서 가져올 시작 위치 구하기
        begin = ((nowPage - 1) * pageSize) + 1;
        // 게시물 끝 위치 찾기
        end = begin + pageSize - 1;

        map.put("begin", begin + "");
        map.put("end", end + "");
        map.put("column", column);
        map.put("search", search);
        map.put("isSearch", isSearch);

        // 1. DB 작업 > DAO 위임 > select
        BoardDAO dao = new BoardDAO();

        // 2. ArrayList<BoardDTO> 반환하기
        ArrayList<BoardDTO> list = dao.list(map);

        // 2.5
        for (BoardDTO dto : list) {
//            // 제목 검색시 검색어 강조하기
//            if (isSearch.equals("y") && column.equals("subject")){
//                subject = subject.replace(search, "<span style='color: tomato; background-color: yellow;'>"
//                        + search + "</span>");
//
//                dto.setSubject(subject);
//            }


            // 날짜 > 시, 분, 초 제거
            String regdate = dto.getRegdate();
            regdate = regdate.substring(0, 10);
            dto.setRegdate(regdate);

            String subject = dto.getSubject();

            // 무조건 글 제목과 내용에 들어있는 <script>태그는 비활성화 시키기!
            subject = subject.replace("<script", "&lt;script").replace("</script>", "&lt;/script&g");

            dto.setSubject(subject);

            // 제목이 길면 > 자르기
            if (subject.length() > 40) {
                subject = subject.substring(0, 30) + "...";
                dto.setSubject(subject);
            }
        }

        // 총 게시물 수 알아내기
        totalCount = dao.getTotalCount(map);

        // 총 페이지 수 알아내기
        totalPage = (int) Math.ceil((double) totalCount / pageSize);

        // 부트스트랩 이용해서 String 자료형에 담기
        String pagebar = "<nav>\r\n"
                + "<ul class=\"pagination\">";

        // while 루프 변수
        loop = 1;

        // 출력되는 페이지 변수
        n = ((nowPage - 1) / blockSize) * blockSize + 1;

        // 이전 10페이지
        if (n == 1) {
            pagebar += String.format(" <li class='disabled'><a href='#!' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li> ");
        } else {
            pagebar += String.format(" <li><a href='/myapp/board/list.do?page=%d'><span aria-hidden='true'>&laquo;</span></a></li> ", n - 1, blockSize);
        }


        // 페이지에 글이 아무것도 없을떄..
        if (totalPage == 0) {
            pagebar += " <li class='active'><a href='#!'>1</a></li> ";
        }

        while (!(loop > blockSize || n > totalPage)) {
            if (n == nowPage) {
                pagebar += String.format(" <li class='active'><a href='#!'>%d</a></li> ", n);
            } else {
                pagebar += String.format(" <li><a href='/myapp/board/list.do?page=%d'>%d</a></li> ", n, n);
            }

            loop++;
            n++;
        }

        // 다음 10페이지
        if (n > totalPage) {
            pagebar += String.format(" <li class='disabled'><a href='#!' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li> ");
        } else {
            pagebar += String.format(" <li><a href='/myapp/board/list.do?page=%d' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li> ", n);
        }


        pagebar += "</ul>\r\n"
                + "</nav>";

        // 새로고침에 의한 조회수 증가 방지 티켓
        HttpSession session = req.getSession();

        session.setAttribute("read", "n");

        // 3. JSP 호출하기(2번 전달)
        req.setAttribute("list", list);
        req.setAttribute("map", map);
        req.setAttribute("pagebar", pagebar);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
        dispatcher.forward(req, resp);
    }


}
