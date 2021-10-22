package com.test.app.board;

import com.test.app.dao.BoardDAO;
import com.test.app.dto.BoardDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/editok.do")
public class EditOk extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. 데이터 가져오기
        String seq = req.getParameter("seq");
        String subject = req.getParameter("subject");
        String content = req.getParameter("content");
        String tag = req.getParameter("tag");

        // 2. DB 작업 > DAO 위임 > update
        BoardDAO dao = new BoardDAO();
        BoardDTO dto = new BoardDTO();

        dto.setSeq(seq);
        dto.setSubject(subject);
        dto.setContent(content);
        dto.setTag(tag);

        int result = dao.edit(dto);

        // 3. 결과 후 처리
        // 수정할 글번호(seq) 가지고 넘어가기
        if ( result == 1 ) {
            resp.sendRedirect("/myapp/board/view.do?seq=" + seq);
        } else {
            resp.sendRedirect("/myapp/board/edit.do?seq=" + seq);
        }

    }

}