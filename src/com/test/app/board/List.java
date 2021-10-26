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

        if(column != null && search != null && !column.equals("") && !search.equals("")){
            isSearch = "y";
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("column", column);
        map.put("search", search);
        map.put("isSearch", isSearch);

        // 1. DB 작업 > DAO 위임 > select
        BoardDAO dao = new BoardDAO();

        // 2. ArrayList<BoardDTO> 반환하기
        ArrayList<BoardDTO> list = dao.list(map);

        // 2.5
        for (BoardDTO dto : list){
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
            if (subject.length() > 40){
                subject = subject.substring(0,30) + "...";
                dto.setSubject(subject);
            }
        }

        // 새로고침에 의한 조회수 증가 방지 티켓
        HttpSession session = req.getSession();

        session.setAttribute("read", "n");

        // 3. JSP 호출하기(2번 전달)
        req.setAttribute("list", list);
        req.setAttribute("map", map);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
        dispatcher.forward(req,resp);
    }


}
