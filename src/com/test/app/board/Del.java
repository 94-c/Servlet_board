package com.test.app.board;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/del.do")
public class Del extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 데이터 가져오기(seq)
        String seq = req.getParameter("seq");

        // 2. JSP 호출 + 글 번호(seq) 전달
        req.setAttribute("seq", seq);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/del.jsp");
        dispatcher.forward(req, resp);
    }
}
